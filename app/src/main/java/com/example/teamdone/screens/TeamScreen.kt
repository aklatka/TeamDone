package com.example.teamdone.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckBox
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.PeopleAlt
import androidx.compose.material.icons.outlined.CheckBox
import androidx.compose.material.icons.outlined.Dashboard
import androidx.compose.material.icons.outlined.PeopleAlt
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.teamdone.AuthorizedNavigationItem
import com.example.teamdone.components.TabBottomBar
import com.example.teamdone.components.TabItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TeamScreen(
    navController: NavHostController,
    teamId: String,
    name: String
) {
    val tabNavController = rememberNavController()

    var title by remember { mutableStateOf("") }
    var isBottomBarHidden by remember { mutableStateOf(false) }
    var isLocalBack by remember { mutableStateOf(true) }

    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier
                    .shadow(5.dp, shape = RoundedCornerShape(0.dp)),
                title = {
                    Column {
                        Text(name, fontSize = 18.sp)
                        Text(title, fontSize = 16.sp)
                    }
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            if(isLocalBack) {
                                tabNavController.popBackStack()
                            } else {
                                navController.popBackStack()
                            }
                        }
                    ) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Cofnij"
                        )
                    }
                }
            )
        },
        bottomBar = {
            if(!isBottomBarHidden) {
                TabBottomBar(
                    tabNavController,
                    arrayListOf(
                        TabItem(
                            "Pulpit",
                            AuthorizedNavigationItem.TeamDashboard.route,
                            Icons.Outlined.Dashboard,
                            Icons.Filled.Dashboard
                        ),
                        TabItem(
                            "Zadania",
                            AuthorizedNavigationItem.TeamTaskList.route,
                            Icons.Outlined.CheckBox,
                            Icons.Filled.CheckBox
                        ),
                        TabItem(
                            "Uczestnicy",
                            AuthorizedNavigationItem.TeamMemberList.route,
                            Icons.Outlined.PeopleAlt,
                            Icons.Filled.PeopleAlt
                        ),
                    )
                )
            }
        }
    ) { padding ->
        Surface(
            modifier = Modifier.padding(padding)
                .padding(horizontal = 0.dp, vertical = 0.dp)
        ) {
            NavHost(
                navController = tabNavController,
                startDestination = AuthorizedNavigationItem.TeamDashboard.route
            ) {
                composable(
                    AuthorizedNavigationItem.TeamDashboard.route
                ) {
                    title = "Pulpit"
                    isBottomBarHidden = false
                    isLocalBack = false
                    TeamDashboardScreen(tabNavController, teamId)
                }
                composable(
                    AuthorizedNavigationItem.TeamTaskList.route
                ) {
                    title = "Lista zadań"
                    isBottomBarHidden = false
                    isLocalBack = false
                    TeamTaskListScreen(tabNavController, teamId)
                }
                composable(
                    "${AuthorizedNavigationItem.TeamAddTasksForMember.route}/{memberId}"
                ) { backStackEntry ->
                    val memberId = backStackEntry.arguments?.getString("memberId")
                    title = "Nowe zadania"
                    isBottomBarHidden = true
                    isLocalBack = true

                    if(memberId == null) {
                        tabNavController.popBackStack()
                    } else {
                        TeamAddTasksScreen(tabNavController, teamId, memberId)
                    }
                }
                composable(
                    AuthorizedNavigationItem.TeamMemberList.route
                ) {
                    title = "Lista uczestników"
                    isBottomBarHidden = false
                    isLocalBack = false
                    TeamMemberListScreen(tabNavController, teamId)
                }
                composable(
                    AuthorizedNavigationItem.TeamAddMembers.route
                ) {

                }
            }
        }
    }
}