package com.example.teamdone.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.teamdone.R
import com.example.teamdone.controls.PrimaryButton
import com.example.teamdone.controls.TextInput
import com.example.teamdone.data.User
import com.example.teamdone.states.AppViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions

@Composable
fun SettingsScreen(
    navController: NavHostController,
    user: User?,
    viewModel: AppViewModel = hiltViewModel()
) {
    var firstname by remember { mutableStateOf(user?.firstname ?: "") };
    var lastname by remember { mutableStateOf(user?.lastname ?: "") };

    var loading by remember { mutableStateOf(false) }

    val fbUser = FirebaseAuth.getInstance().currentUser
    val firestore = FirebaseFirestore.getInstance()

    val context = LocalContext.current

    fun submit() {
        if(fbUser == null) return;
        loading = true

        val userData = hashMapOf<String, Any>(
            "firstname" to firstname,
            "lastname" to lastname
        )

        firestore.collection("users")
            .document(fbUser.uid)
            .set(userData, SetOptions.merge())
            .addOnCompleteListener { task ->
                if(task.isSuccessful) {
                    loading = false

                    Toast.makeText(
                        context,
                        "Zmiany zostały zapisane!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    Column(
        modifier = Modifier.fillMaxHeight()
            .padding(20.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            TextInput(
                value = firstname,
                onValueChange = {firstname = it},
                label = stringResource(R.string.firstname),
            )
            TextInput(
                value = lastname,
                onValueChange = {lastname = it},
                label = stringResource(R.string.lastname),
            )
        }
        Column {
            PrimaryButton(
                text ="Zapisz zmiany",
                onClick = {
                    submit()
                },
                loading = loading,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}