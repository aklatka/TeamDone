package com.example.teamdone.components.items

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.example.teamdone.data.Task
import com.example.teamdone.services.TaskService
import com.example.teamdone.ui.theme.SuccessColor

@Composable
fun TaskItem(
    task: Task,
    onTaskDone: (taskId: String) -> Unit
) {
    var done by remember { mutableStateOf(task.done) }

    fun toggleDone() {
        done = !done
        TaskService
            .getInstance()
            .updateTaskDone(task.id, done)
    }

    Surface(modifier = Modifier.clickable {
        onTaskDone(task.id)
        toggleDone()
    }) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Checkbox(done, onCheckedChange = {toggleDone()})
            Spacer(modifier = Modifier.width(3.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(task.title,
                    style = TextStyle(
                        textDecoration = if(done) TextDecoration.LineThrough else TextDecoration.None
                    )
                )
                Icon(
                    imageVector = (
                        if(task.done) Icons.Default.Check else task.priority.icon
                    ),
                    tint = if(task.done) SuccessColor else task.priority.color,
                    contentDescription = task.priority.displayName
                )
            }
        }
    }
}