package com.example.teamdone.screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Square
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import co.yml.charts.ui.piechart.models.PieChartData
import com.example.teamdone.components.DonutTaskChart
import com.example.teamdone.data.TaskStatus
import com.example.teamdone.services.TaskService

@Composable
fun TeamDashboardScreen(
    navController: NavHostController,
    teamId: String
) {
    var slices: List<HashMap<String, Any>> by remember { mutableStateOf(arrayListOf()) }
    var loading by remember { mutableStateOf(true) }

    LaunchedEffect(teamId) {
        loading = true
        TaskService.getInstance()
            .fetchTasksStatistics(
                teamId
            ) {
                slices = it
                Log.d("Slices", "TeamDashboardScreen: $slices")
                loading = false
            }
    }

    if(loading) {
        Column(
            modifier = Modifier.fillMaxHeight(0.5f)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator()
        }
    } else {
        LazyColumn(
            modifier = Modifier.padding(20.dp)
        ) {
            item {
                Text("Statystyki zadań", fontSize = 20.sp)
                Spacer(Modifier.height(10.dp))
                DonutTaskChart(slices.map { it["slice"] as PieChartData.Slice })
                Spacer(Modifier.height(10.dp))
                Column {
                    slices.forEach {
                        val status = TaskStatus.valueOf(it["status"].toString())

                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.padding(vertical = 20.dp)
                                .fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier.wrapContentWidth(),
                                horizontalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Square,
                                    contentDescription = "",
                                    tint = status.color
                                )
                                Text(status.displayName)
                            }
                            Text(it["count"].toString())
                        }
                    }
                }
            }
        }
    }
}