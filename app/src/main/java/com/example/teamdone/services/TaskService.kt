package com.example.teamdone.services

import android.util.Log
import co.yml.charts.ui.piechart.models.PieChartData
import com.example.teamdone.data.Task
import com.example.teamdone.data.TaskPriority
import com.example.teamdone.data.TaskStatus
import com.example.teamdone.ui.theme.ErrorColor
import com.example.teamdone.ui.theme.PrimaryColor
import com.example.teamdone.ui.theme.SuccessColor
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
            "status" to TaskStatus.BACKLOG
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

    fun updateTaskStatus(
        taskId: String,
        status: TaskStatus
    ) {
        firestore
            .collection("tasks")
            .document(taskId)
            .set(hashMapOf(
                "status" to status
            ), SetOptions.merge())
    }

    fun fetchTasksStatistics(
        teamId: String,
        onSuccess: (slices: List<HashMap<String, Any>>) -> Unit
    ) {
        val slices: ArrayList<HashMap<String, Any>> = arrayListOf()

        firestore
            .collection("tasks")
            .whereEqualTo("teamId", teamId)
            .get()
            .addOnCompleteListener { t ->
                if(t.isSuccessful) {
                    val completed = t.result.documents
                        .mapNotNull { TaskStatus.valueOf(it["status"].toString()) }
                        .filter { it == TaskStatus.COMPLETE }
                        .size
                    val inProgress = t.result.documents
                        .mapNotNull { TaskStatus.valueOf(it["status"].toString()) }
                        .filter { it == TaskStatus.IN_PROGRESS }
                        .size
                    val backlog = t.result.documents
                        .mapNotNull { TaskStatus.valueOf(it["status"].toString()) }
                        .filter { it == TaskStatus.BACKLOG }
                        .size

                    Log.d("Task", "fetchTasksStatistics: ${completed}")
                    Log.d("Task", "fetchTasksStatistics: ${inProgress}")
                    Log.d("Task", "fetchTasksStatistics: ${backlog}")

                    if(completed > 0) {
                        slices.add(hashMapOf(
                            "slice" to PieChartData.Slice("Ukończone", completed.toFloat(), SuccessColor),
                            "status" to TaskStatus.COMPLETE,
                            "count" to completed
                        ))
                    }
                    if(inProgress > 0) {
                        slices.add(hashMapOf(
                            "slice" to PieChartData.Slice("W trakcie", inProgress.toFloat(), PrimaryColor),
                            "status" to TaskStatus.IN_PROGRESS,
                            "count" to inProgress
                        ))
                    }
                    if(backlog > 0) {
                        slices.add(hashMapOf(
                            "slice" to PieChartData.Slice("Oczekujące", backlog.toFloat(), ErrorColor),
                            "status" to TaskStatus.BACKLOG,
                            "count" to backlog
                        ))
                    }
                    onSuccess(slices)
                }
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
