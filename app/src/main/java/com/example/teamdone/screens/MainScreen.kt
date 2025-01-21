package com.example.teamdone.screens

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
import com.example.teamdone.NavigationItem
import com.example.teamdone.components.AppDrawer
import com.example.teamdone.data.User
import com.example.teamdone.states.AppViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun MainScreen(
    authNavController: NavHostController,
    viewModel: AppViewModel = hiltViewModel()
) {
    val navController = rememberNavController()

    val fbUser = FirebaseAuth.getInstance().currentUser
    val firestore = FirebaseFirestore.getInstance()

    var user by remember { mutableStateOf<User?>(null) }

    fbUser?.let {
        firestore.collection("users")
            .document(it.uid)
            .addSnapshotListener { value, error ->
                user = User.fromMap(value?.data)
            }
    }

    AppDrawer(
        navController = navController,
        authNavController = authNavController
    ) {
        NavHost(
            modifier = Modifier,
            navController = navController,
            startDestination = AuthorizedNavigationItem.Dashboard.route
        ) {
            composable(
                AuthorizedNavigationItem.Dashboard.route,
            ) {
                DashboardScreen(navController)
            }
            composable(
                AuthorizedNavigationItem.Settings.route,
            ) {
                viewModel.ladCurrentUser()

                SettingsScreen(navController, user)
            }
        }
    }

}