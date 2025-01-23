package com.example.teamdone.services

import com.example.teamdone.data.Member
import com.example.teamdone.data.User
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot

class MemberService private constructor() {
    private val firestore = FirebaseFirestore.getInstance()

    fun fetchAllForTeam(
        teamId: String,
        onSuccess: (members: List<Member>) -> Unit
    ) {
        val tasks = mutableListOf<Task<QuerySnapshot>>()
        val members: ArrayList<Member> = arrayListOf()

        firestore
            .collection("members")
            .whereEqualTo("teamId", teamId)
            .get()
            .addOnCompleteListener { doc ->
                val userIds = doc.result.documents.mapNotNull { it["userId"] }

                firestore
                    .collection("users")
                    .whereIn(FieldPath.documentId(), userIds)
                    .get()
                    .addOnSuccessListener { t ->

                    val users = t.documents.mapNotNull { User.fromMap(it.data) }

                    doc.result.documents.mapNotNull { member ->
                        val user = users.find { it.uid ==  member["userId"] }

                        user?.let {
                            members.add(
                                Member(
                                    member.id,
                                    user,
                                    member["inviteAccepted"] as Boolean,
                                    member["inviteStatus"].toString(),
                                    teamId,
                                    member["owner"] as Boolean,
                                )
                            )
                        }
                    }
                    onSuccess(members)
                }
            }
    }

    fun fetchMember(
        id: String,
        onSuccess: (members: Member) -> Unit,
        onFailure: () -> Unit = {}
    ) {

        firestore
            .collection("members")
            .document(id)
            .get()
            .addOnCompleteListener { task ->
                val member = task.result

                firestore
                    .collection("users")
                    .whereEqualTo(FieldPath.documentId(), member["userId"])
                    .get()
                    .addOnCompleteListener { t ->
                        val user = User.fromMap(t.result.documents[0].data)

                        user?.let {
                            onSuccess(
                                Member(
                                    member.id,
                                    user,
                                    member["inviteAccepted"] as Boolean,
                                    member["inviteStatus"].toString(),
                                    member["teamId"].toString(),
                                    member["owner"] as Boolean,
                                )
                            )
                        }
                    }
                    .addOnFailureListener {
                        onFailure()
                    }
            }
    }

    companion object {

        @Volatile private var instance: MemberService? = null

        fun getInstance() =
            instance ?: synchronized(this) {
                MemberService().also {
                    instance = it
                }
            }
    }

}