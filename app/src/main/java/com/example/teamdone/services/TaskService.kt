package com.example.teamdone.services

import com.example.teamdone.data.Task
import com.example.teamdone.data.TaskPriority
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions

class TaskService private constructor() {
    private val firestore = FirebaseFirestore.getInstance()

    fun createTask(
        teamId: String,
        memberId: String,
        title: String,
        description: String,
        priority: TaskPriority,
        onSuccess: () -> Unit
    ) {
        val taskData = hashMapOf(
            "teamId" to teamId,
            "memberId" to memberId,
            "title" to title,
            "description" to description,
            "priority" to priority.name,
            "done" to false,
        )

        firestore
            .collection("tasks")
            .add(taskData)
            .addOnCompleteListener {
                onSuccess()
            }
    }

    fun fetchAllTasks(
        teamId: String,
        onSuccess: (tasks: List<Task>) -> Unit
    ) {
        val user = FirebaseAuth.getInstance().currentUser

        firestore
            .collection("members")
            .whereEqualTo("userId", user?.uid)
            .get()
            .addOnSuccessListener { doc ->
                val member = doc.documents[0]

                firestore
                    .collection("tasks")
                    .whereEqualTo("memberId", member.id)
                    .whereEqualTo("teamId", teamId)
                    .get()
                    .addOnCompleteListener {
                        val tasks = it.result.documents.mapNotNull { item ->
                            item.data?.let { it1 -> Task.fromMap(
                                item.id,
                                it1
                            ) }
                        }
                        onSuccess(tasks)
                    }
            }
    }

    fun updateTaskDone(
        taskId: String,
        done: Boolean
    ) {
        firestore
            .collection("tasks")
            .document(taskId)
            .set(hashMapOf(
                "done" to done
            ), SetOptions.merge())
    }

    companion object {

        @Volatile private var instance: TaskService? = null

        fun getInstance() =
            instance ?: synchronized(this) {
                TaskService().also {
                    instance = it
                }
            }
    }
}
