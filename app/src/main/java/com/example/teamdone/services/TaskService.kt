package com.example.teamdone.services

import com.example.teamdone.data.TaskPriority
import com.google.firebase.firestore.FirebaseFirestore

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
