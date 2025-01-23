package com.example.teamdone.components.items

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.teamdone.controls.ToggleButton
import com.example.teamdone.data.Task
import com.example.teamdone.data.TaskStatus
import com.example.teamdone.services.TaskService
import com.example.teamdone.ui.theme.SuccessColor

@Composable
fun TaskItem(
    task: Task,
    onTaskDone: (taskId: String) -> Unit
) {
    var status by remember { mutableStateOf(task.status) }
    var expanded by remember { mutableStateOf(false) }

    fun updateStatus(status: TaskStatus) {
        TaskService.getInstance()
            .updateTaskStatus(
                task.id,
                status
            )
    }

    Surface(
        modifier = Modifier
            .border(1.dp, Color.LightGray, RoundedCornerShape(5.dp))
            .shadow(4.dp, RoundedCornerShape(5.dp)),
        shape = RoundedCornerShape(5.dp)
    ) {
        Column(
            modifier = Modifier.padding(
                top = 15.dp,
                start = 15.dp,
                end = 15.dp,
                bottom = 5.dp
            )
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .clickable {
                        expanded = !expanded
                    }
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(task.title, fontSize = 20.sp, fontWeight = FontWeight.W400)
                    Icon(
                        imageVector = (
                                if(task.done) Icons.Default.Check else task.priority.icon
                                ),
                        tint = if(task.done) SuccessColor else task.priority.color,
                        contentDescription = task.priority.displayName
                    )
                }
            }
            Spacer(Modifier.height(10.dp))
            if(expanded) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    TaskStatus.entries.forEach {
                        ToggleButton(
                            it.displayName,
                            onClick = {
                                status = it
                                updateStatus(status)
                            },
                            checked = status == it
                        )
                    }
                }
            }
        }
    }
}