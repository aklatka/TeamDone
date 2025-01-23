package com.example.teamdone.services

import android.util.Log
import com.example.teamdone.data.Member
import com.example.teamdone.data.Team
import com.example.teamdone.data.User
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions

class TeamService private constructor() {
    private val firestore = FirebaseFirestore.getInstance()

    fun createTeam(
        name: String,
        description: String,
        users: List<User>,
        onSuccess: () -> Unit = {}
    ) {
        val currentUser = FirebaseAuth.getInstance().currentUser
        val tasks = mutableListOf<Task<DocumentReference>>()

        val teamData = hashMapOf(
            "name" to name,
            "description" to description
        )

        firestore
            .collection("teams")
            .add(teamData)
            .addOnSuccessListener { doc ->
                firestore
                    .collection("teams")
                    .document(doc.id)
                    .set(hashMapOf(
                        "id" to doc.id
                    ), SetOptions.merge())

                val members = users.map {
                    hashMapOf<String, Any>(
                        "userId" to it.uid,
                        "teamId" to doc.id,
                        "inviteAccepted" to false,
                        "inviteStatus" to Member.INVITE_STATUS_PENDING,
                        "owner" to false
                    )
                } as ArrayList

                currentUser?.let {
                    members.add(hashMapOf(
                        "userId" to it.uid,
                        "teamId" to doc.id,
                        "inviteAccepted" to true,
                        "inviteStatus" to Member.INVITE_STATUS_ACCEPTED,
                        "owner" to true
                    ))
                }

                val membersCollection = firestore.collection("members")

                for (member in members) {
                    val task = membersCollection.add(member)
                    tasks.add(task)
                }
            }

        Tasks.whenAllComplete(tasks).addOnCompleteListener {
            onSuccess()
        }
    }

    fun fetchAllTeams(
        onSuccess: (teams: List<Team>) -> Unit
    ) {
        val user = FirebaseAuth.getInstance().currentUser

        firestore
            .collection("members")
            .whereEqualTo("userId", user?.uid)
            .get()
            .addOnCompleteListener { task ->
                if(task.isSuccessful) {

                    val teamIds = task.result.documents.map { it["teamId"] }
                    Log.d("Team Service", "fetchAllTeams: $teamIds")

                    if(teamIds.isNotEmpty()) {
                        firestore
                            .collection("teams")
                            .whereIn(FieldPath.documentId(), teamIds)
                            .get()
                            .addOnSuccessListener { doc ->
                                if(!doc.isEmpty) {
                                    doc.documents.mapNotNull { Team.fromMap(it.data) }.let {
                                        onSuccess(it)
                                    }
                                }
                            }
                    }

                }
            }
            .addOnFailureListener {  }

    }

    companion object {

        @Volatile private var instance: TeamService? = null

        fun getInstance() =
            instance ?: synchronized(this) {
                TeamService().also {
                    instance = it
                }
            }
    }

}