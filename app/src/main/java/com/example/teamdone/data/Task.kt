package com.example.teamdone.data

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Flag
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.teamdone.ui.theme.ErrorColor
import com.example.teamdone.ui.theme.PrimaryColor
import com.example.teamdone.ui.theme.SuccessColor
import com.example.teamdone.ui.theme.WarningColor

enum class TaskPriority(
    val icon: ImageVector,
    val displayName: String,
    val color: Color
) {
    LOW(Icons.Filled.Flag, "Niski", PrimaryColor),
    MEDIUM(Icons.Filled.Flag, "Średni", WarningColor),
    HIGH(Icons.Filled.Flag, "Wysoki", ErrorColor),
}

enum class TaskStatus(
    val displayName: String,
    val color: Color
) {
    BACKLOG("Oczekujące", ErrorColor),
    IN_PROGRESS("W trakcie", PrimaryColor),
    COMPLETE("Ukończone", SuccessColor)
}

data class Task(
    val id: String,
    val title: String,
    val description: String,
    val priority: TaskPriority,
    val done: Boolean = false,
    val status: TaskStatus = TaskStatus.BACKLOG
) {

    companion object {

        fun fromMap(
            id: String,
            data: MutableMap<String, Any>
        ): Task {
            return Task(
                id = id,
                title = data["title"].toString(),
                description = data["description"].toString(),
                priority = TaskPriority.valueOf(data["priority"].toString()),
                done = data["done"] as Boolean,
                status = TaskStatus.valueOf(data["status"].toString())
            )
        }

    }

}