package com.example.teamdone.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.teamdone.AuthorizedNavigationItem

@Composable
fun TeamListScreen(
    navController: NavHostController
) {

    Scaffold(
        floatingActionButton = {
            ExtendedFloatingActionButton(
                text = {
                    Text("Utwórz nowy")
                },
                icon = {
                    Icon(Icons.Default.Add, contentDescription = "")
                },
                onClick = {
                    navController.navigate(AuthorizedNavigationItem.NewTeam.route)
                },
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier.padding(padding)
                .fillMaxHeight(),
        ) {
//            Column(
//                modifier = Modifier.fillMaxHeight(0.5f)
//            ) {
//                Text("Twoje zespoły", modifier = Modifier.padding(5.dp))
//                HorizontalDivider()
//                Column {
//
//                }
//            }
//            Column(
//                modifier = Modifier.fillMaxHeight(0.5f)
//            ) {
//                Text("Dzielone z tobą", modifier = Modifier.padding(5.dp))
//                HorizontalDivider()
//                Column {
//
//                }
//            }
        }
    }

}