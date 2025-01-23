package com.example.teamdone.screens

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.teamdone.AuthorizedNavigationItem
import com.example.teamdone.components.AppDrawer
import com.example.teamdone.data.User
import com.example.teamdone.states.AppViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.net.URLDecoder
import java.nio.charset.StandardCharsets

@Composable
fun DrawerScreen(
    authNavController: NavHostController,
    viewModel: AppViewModel = hiltViewModel()
) {
    val navController = rememberNavController()

    val fbUser = FirebaseAuth.getInstance().currentUser
    val firestore = FirebaseFirestore.getInstance()

    var user by remember { mutableStateOf<User?>(null) }

    var title by remember { mutableStateOf("") }
    var isFormMode by remember { mutableStateOf(false) }
    var isTopBarHidden by remember { mutableStateOf(false) }

    fbUser?.let {
        firestore.collection("users")
            .document(it.uid)
            .addSnapshotListener { value, error ->
                user = User.fromMap(value?.data)
            }
    }

    AppDrawer(
        navController = navController,
        authNavController = authNavController,
        topBarTitle = title,
        formMode = isFormMode,
        topBarHidden = isTopBarHidden
    ) {
        NavHost(
            modifier = Modifier,
            navController = navController,
            startDestination = AuthorizedNavigationItem.Dashboard.route
        ) {
            composable(
                AuthorizedNavigationItem.Dashboard.route,
            ) {
                isFormMode = false
                isTopBarHidden = false

                DashboardScreen(navController)
            }
            composable(
                AuthorizedNavigationItem.TeamList.route,
            ) {
                isFormMode = false
                isTopBarHidden = false
                title = "Twoje zespoły"
                TeamListScreen(navController)
            }
            composable(
                AuthorizedNavigationItem.NewTeam.route
            ) {
                isFormMode = true
                isTopBarHidden = false
                title = "Nowy zespół"
                NewTeamScreen(navController)
            }
            composable(
                "${AuthorizedNavigationItem.Team.route}/{id}?title={title}"
            ) { backStackEntry ->
                val id = backStackEntry.arguments?.getString("id")
                val routeTitle = URLDecoder.decode(
                    backStackEntry.arguments?.getString("title"),
                    StandardCharsets.UTF_8.toString()
                )

                if(id == null) {
                    navController.popBackStack()
                } else {
                    isFormMode = true
                    isTopBarHidden = true
                    title = routeTitle ?: "Unknown"
                    TeamScreen(navController, id, routeTitle)
                }
            }
            composable(
                AuthorizedNavigationItem.Settings.route,
            ) {
                viewModel.ladCurrentUser()
                isFormMode = false
                isTopBarHidden = false

                SettingsScreen(navController, user)
            }
        }
    }

}