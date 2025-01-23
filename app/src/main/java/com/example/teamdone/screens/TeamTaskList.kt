package com.example.teamdone.screens

import android.widget.Space
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.teamdone.AuthorizedNavigationItem
import com.example.teamdone.R
import com.example.teamdone.components.items.TaskItem
import com.example.teamdone.components.items.TeamCard
import com.example.teamdone.data.Task
import com.example.teamdone.services.TaskService

@Composable
fun TeamTaskListScreen(
    navController: NavController,
    teamId: String
) {
    var tasks: List<Task> by remember { mutableStateOf(arrayListOf()) }

    LaunchedEffect(teamId) {
        TaskService.getInstance()
            .fetchAllTasks(
                teamId
            ) {
                tasks = it
            }
    }

    Scaffold(
        modifier = Modifier.padding(
            horizontal =  20.dp,
            vertical = 0.dp
        ),
    ) { padding ->
        Column(
            modifier = Modifier.padding(padding),
        ) {
            Column(
                modifier = Modifier.padding(vertical = 20.dp)
            ) {
                Text("Twoje zadania", fontSize = 18.sp)
                HorizontalDivider()
            }
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                item {
                    Spacer(Modifier.height(20.dp))
                }
                tasks.forEach {
                    item {
                        TaskItem(it) {}
                    }
                }
                item {
                    Spacer(Modifier.height(100.dp))
                }
            }
        }
    }
}