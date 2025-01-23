package com.example.teamdone.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Icon
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.teamdone.components.Stepper
import com.example.teamdone.components.items.UserItem
import com.example.teamdone.controls.TextInput
import com.example.teamdone.data.Member
import com.example.teamdone.data.Task
import com.example.teamdone.data.TaskPriority
import com.example.teamdone.services.MemberService
import com.example.teamdone.services.TaskService

@Composable
fun TeamAddTasksScreen(
    navController: NavController,
    teamId: String,
    memberId: String? = null,
) {
    var member: Member? by remember { mutableStateOf(null) }

    var title: String by remember { mutableStateOf("") }
    var description: String by remember { mutableStateOf("") }
    var priority: TaskPriority by remember { mutableStateOf(TaskPriority.LOW) }

    var loading by remember { mutableStateOf(false) }

    val context = LocalContext.current

    LaunchedEffect(memberId) {
        memberId?.let {
            MemberService.getInstance()
                .fetchMember(
                    it,
                    onSuccess = { m ->
                        member = m
                    }
                )
        }
    }

    fun reset() {
        title = ""
        description = ""
        priority = TaskPriority.LOW
    }

    fun submit() {
        memberId?.let {
            loading = true

            TaskService.getInstance()
                .createTask(
                    teamId,
                    it,
                    title,
                    description,
                    priority,
                    onSuccess = {
                        loading = false
                        Toast.makeText(
                            context,
                            "Pomyślnie dodano zadanie",
                            Toast.LENGTH_SHORT
                        ).show()
                        reset()
                    }
                )
        }
    }

    Stepper(
        steps = listOf(),
        finishButtonText = "Dodaj",
        onFinish = {
            submit()
        },
        loading = loading,
        header = {
            Column(

            ) {
                Text("Zadania dla", fontWeight = FontWeight.Medium)
                member?.let {
                    UserItem(it.user)
                }
            }
        }
    ) {
        TextInput(
            value = title,
            onValueChange = {title = it},
            label = "Tytuł",
        )
        TextInput(
            value = description,
            onValueChange = {description = it},
            label = "Opis",
            lines = 2
        )
        Spacer(Modifier.height(10.dp))
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            Text("Priorytet", fontSize = 15.sp)
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                TaskPriority.entries.forEach {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        modifier = Modifier
                            .clickable {
                                priority = it
                            }
                    ) {
                        RadioButton(
                            selected = (it.name == priority.name),
                            onClick = {
                                priority = it
                            }
                        )
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(it.displayName)
                            Icon(
                                imageVector = it.icon,
                                tint = it.color,
                                contentDescription = it.displayName
                            )
                        }
                    }
                }
            }
        }
    }
}