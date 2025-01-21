package com.example.teamdone.screens

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.teamdone.NavigationItem
import com.example.teamdone.components.AppDrawer
import com.example.teamdone.controls.PrimaryButton
import com.example.teamdone.states.AppViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth

@Composable
fun DashboardScreen(
    navController: NavHostController,
    viewModel: AppViewModel = hiltViewModel()
) {

    val user by remember { mutableStateOf(FirebaseAuth.getInstance().currentUser) }

    user?.let {
        Text(it.email.toString())
    }
}