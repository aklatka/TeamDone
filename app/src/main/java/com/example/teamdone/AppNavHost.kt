package com.example.teamdone

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.teamdone.screens.LoginScreen
import com.example.teamdone.screens.DrawerScreen
import com.example.teamdone.screens.SignupScreen

@Composable
fun AppNavHost(
    startDestination: String = NavigationItem.AuthLogin.route
) {
    val navController = rememberNavController()

    NavHost(
        modifier = Modifier,
        navController = navController,
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
            LoginScreen(navController)
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
            SignupScreen(navController)
        }
        composable(
            NavigationItem.Authorized.route
        ) {
            DrawerScreen(navController)
        }
    }
}