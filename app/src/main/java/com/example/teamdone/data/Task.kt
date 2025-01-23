package com.example.teamdone.data

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Flag
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.teamdone.ui.theme.ErrorColor
import com.example.teamdone.ui.theme.PrimaryColor
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

data class Task(
    val title: String,
    val description: String,
    val priority: TaskPriority
) {

}