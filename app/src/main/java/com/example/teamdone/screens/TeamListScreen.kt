package com.example.teamdone.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateStartPadding
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.teamdone.AuthorizedNavigationItem
import com.example.teamdone.R
import com.example.teamdone.components.items.TeamCard
import com.example.teamdone.data.Member
import com.example.teamdone.data.Team
import com.example.teamdone.services.TeamService

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun TeamListScreen(
    navController: NavHostController
) {
    val bool = rememberSaveable { true }
    var teams: List<Team> by remember { mutableStateOf(arrayListOf()) }

    LaunchedEffect(key1 = bool) {
        TeamService.getInstance().fetchAllTeams {
            teams = it
        }
    }

    Scaffold(
        modifier = Modifier.padding(
            horizontal =  20.dp,
            vertical = 0.dp
        ),
        floatingActionButton = {
            ExtendedFloatingActionButton(
                text = {
                    Text("Utwórz nowy", color = Color.White)
                },
                icon = {
                    Icon(Icons.Default.Add, contentDescription = "", tint = Color.White)
                },
                onClick = {
                    navController.navigate(AuthorizedNavigationItem.NewTeam.route)
                },
                containerColor = colorResource(R.color.primary)
            )
        }
    ) { _ ->
        LazyColumn(
            modifier = Modifier
                .padding(
                    bottom = 50.dp
                )
                .fillMaxHeight(),
            verticalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            item {
                Spacer(Modifier.height(20.dp))
            }
            teams.forEach {
                item {
                    TeamCard(it)
                }
            }
            item {
                Spacer(Modifier.height(200.dp))
            }
        }
    }

}