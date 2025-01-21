package com.example.teamdone

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.teamdone.components.AppDrawer
import com.example.teamdone.screens.DashboardScreen
import com.example.teamdone.screens.LoginScreen
import com.example.teamdone.screens.MainScreen
import com.example.teamdone.screens.SignupScreen
import com.example.teamdone.states.AppViewModel

@Composable
fun AppNavHost(
    startDestination: String = NavigationItem.AuthLogin.route
) {
    val authNavController = rememberNavController()

    NavHost(
        modifier = Modifier,
        navController = authNavController,
        startDestination = startDestination
    ) {

        composable(
            NavigationItem.AuthLogin.route,
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    tween(500)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    tween(500)
                )
            }
        ) {
            LoginScreen(authNavController)
        }
        composable(
            NavigationItem.AuthSignup.route,
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    tween(500)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    tween(500)
                )
            }
        ) {
            SignupScreen(authNavController)
        }
        composable(
            NavigationItem.Main.route
        ) {
            MainScreen(authNavController)
        }
    }
}