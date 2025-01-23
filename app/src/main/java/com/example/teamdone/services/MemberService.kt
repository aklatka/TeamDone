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
                                    user,
                                    member["inviteAccepted"] as Boolean,
                                    teamId,
                                    member["owner"] as Boolean
                                )
                            )
                        }
                    }
                    onSuccess(members)
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