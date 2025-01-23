package com.example.teamdone.screens

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
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.teamdone.AuthorizedNavigationItem
import com.example.teamdone.R
import com.example.teamdone.components.items.TeamCard

@Composable
fun TeamTaskListScreen(
    navController: NavController,
    teamId: String
) {

    Scaffold(
        modifier = Modifier.padding(
            horizontal =  20.dp,
            vertical = 0.dp
        ),
        floatingActionButton = {
            ExtendedFloatingActionButton(
                text = {
                    Text("Dodaj", color = Color.White)
                },
                icon = {
                    Icon(Icons.Default.Add, contentDescription = "", tint = Color.White)
                },
                onClick = {
                    navController.navigate(AuthorizedNavigationItem.TeamAddTasks.route)
                },
                containerColor = colorResource(R.color.primary)
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier.padding(padding)
        ) {  }
    }
}