package com.example.teamdone.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.teamdone.components.Stepper
import com.example.teamdone.components.selectors.UserSelector
import com.example.teamdone.controls.TextInput
import com.example.teamdone.data.User
import com.example.teamdone.services.TeamService

@SuppressLint("MutableCollectionMutableState")
@Composable
fun NewTeamScreen(
    navController: NavHostController
) {
    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var users: List<User> by remember { mutableStateOf(arrayListOf()) }

    val steps = listOf("Opis zespołu", "Uczestnicy")

    fun submit() {
        TeamService.getInstance()
            .createTeam(
                name,
                description,
                users
            ) {
                navController.popBackStack()
            }
    }

    @Composable
    fun DetailsForm() {
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            TextInput(
                value = name,
                onValueChange = {name = it},
                label = "Nazwa",
            )
            TextInput(
                value = description,
                onValueChange = {description = it},
                label = "Opis",
                lines = 5
            )
        }
    }

    Stepper(
        steps = steps,
        onFinish = {
            submit()
        }
    ) { step ->
        when(step) {
            0 -> DetailsForm()
            1 -> UserSelector(
                onSelectedUsersChange = {
                    users = it
                },
                selectedUsers = users
            )
            else -> DetailsForm()
        }
    }
}