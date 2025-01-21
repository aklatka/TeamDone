package com.example.teamdone.screens

import android.annotation.SuppressLint
import android.text.InputType
import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.teamdone.NavigationItem
import com.example.teamdone.R
import com.example.teamdone.components.ImageFromUrl
import com.example.teamdone.controls.PrimaryButton
import com.example.teamdone.controls.SecondaryButton
import com.example.teamdone.controls.TextInput
import com.example.teamdone.data.User
import com.example.teamdone.layouts.AuthLayout
import com.example.teamdone.services.AuthService

@SuppressLint("MutableCollectionMutableState")
@Composable
fun SignupScreen(
    navController: NavHostController
) {
    var firstname by remember { mutableStateOf("") };
    var lastname by remember { mutableStateOf("") };
    var email by remember { mutableStateOf("") };
    var password by remember { mutableStateOf("") };
    var passwordConfirm by remember { mutableStateOf("") };

    var loading by remember { mutableStateOf(false) }

    var user: User? by remember { mutableStateOf(null) }

    fun submit() {
        loading = true
        AuthService().createUser(
            firstname,
            lastname,
            email,
            password,
            onSuccess = {
                user = it
                loading = false
            },
            onFailure = {
                it.let {
//                    error = when(it) {
//                        is FirebaseAuthInvalidCredentialsException -> {
//                            errorCredentials
//                        }
//                        else -> {
//                            e.localizedMessage?.toString()
//                        }
//                    }
                    Log.w("Signup", "submit: ", it)
                    loading = false
                }
            }
        )
    }

    AuthLayout(
        modifier = Modifier,
        signupMode = true,
        actionsContent = {
            if(user == null) {
                Spacer(Modifier.height(30.dp))
                PrimaryButton(
                    stringResource(R.string.sign_up),
                    onClick = { submit() },
                    loading = loading
                )
                Spacer(Modifier.height(10.dp))
                SecondaryButton(
                    stringResource(R.string.already_have_account),
                    onClick = {
                        navController.navigate(NavigationItem.AuthLogin.route)
                    }
                )
            } else {
                PrimaryButton(
                    "Kontynuuj",
                    onClick = {
                        navController.navigate(NavigationItem.Dashboard.route) {
                            popUpTo(0)
                        }
                    }
                )
            }
        }
    ) {
        if(user == null) {
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
            TextInput(
                value = email,
                onValueChange = {email = it},
                label = stringResource(R.string.email),
                placeholder = stringResource(R.string.email_placeholder)
            )
            TextInput(
                value = password,
                onValueChange = {password = it},
                label = stringResource(R.string.password),
                inputType = InputType.TYPE_TEXT_VARIATION_PASSWORD,
                valueVisible = false
            )
            TextInput(
                value = passwordConfirm,
                onValueChange = {passwordConfirm = it},
                label = stringResource(R.string.password_confirm),
                inputType = InputType.TYPE_TEXT_VARIATION_PASSWORD,
                valueVisible = false
            )
        } else {

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                user?.let {
                    ImageFromUrl(
                        it.avatarUrl,
                        "Awatar użytkownika",
                        imageModifier = Modifier
                            .size(150.dp)
                            .clip(CircleShape)
                            .border(2.dp, Color.Black, CircleShape),
                        contentScale = ContentScale.Crop
                    )
                    Spacer(Modifier.height(20.dp))
                    Text(
                        it.firstname,
                        fontWeight = FontWeight.Medium,
                        fontSize = 30.sp,
                        color = Color.Black
                    )
                    Text(
                        "@${it.username}",
                        fontWeight = FontWeight.Normal,
                        fontSize = 20.sp,
                        color = Color.DarkGray
                    )
                }
            }

        }
    }
}