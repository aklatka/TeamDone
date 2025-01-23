package com.example.teamdone.screens

import android.text.InputType
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.teamdone.NavigationItem
import com.example.teamdone.R
import com.example.teamdone.components.TextDivider
import com.example.teamdone.controls.PrimaryButton
import com.example.teamdone.controls.SecondaryButton
import com.example.teamdone.controls.SocialLoginButton
import com.example.teamdone.controls.TextInput
import com.example.teamdone.layouts.AuthLayout
import com.example.teamdone.services.AuthService
import com.example.teamdone.states.AppViewModel
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Brands
import compose.icons.fontawesomeicons.brands.Github
import compose.icons.fontawesomeicons.brands.Google
import compose.icons.fontawesomeicons.brands.Microsoft

@Composable
fun LoginScreen(
    navController: NavHostController,
    viewModel: AppViewModel = hiltViewModel()
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var loading by remember { mutableStateOf(false) }
    var error: String? by remember { mutableStateOf(null) }

    val errorCredentials = stringResource(R.string.error_invalid_credentials)

    fun submit() {
        loading = true
        AuthService().loginUser(
            email,
            password,
            onSuccess = { user ->
                viewModel.setUser(user)
                loading = false
                viewModel.login()
                navController.navigate(NavigationItem.Authorized.route) {
                    popUpTo(0)
                }
            },
            onFailure = { e ->
                e.let {
                    error = when(e) {
                        is FirebaseAuthInvalidCredentialsException -> {
                            errorCredentials
                        }
                        else -> {
                            e.localizedMessage?.toString()
                        }
                    }
                    loading = false
                }
            }
        )
    }

    AuthLayout(
        modifier = Modifier,
        signupMode = false,
        actionsContent = {
            Spacer(Modifier.height(30.dp))
            PrimaryButton(
                stringResource(R.string.sign_in),
                onClick = {submit()},
                loading = loading,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(10.dp))
            SecondaryButton(
                stringResource(R.string.sign_up),
                onClick = {
                    navController.navigate(NavigationItem.AuthSignup.route)
                }
            )
        }
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            SocialLoginButton(
                stringResource(R.string.login_by_google),
                FontAwesomeIcons.Brands.Google
            )
            SocialLoginButton(
                stringResource(R.string.login_by_github),
                FontAwesomeIcons.Brands.Github
            )
            SocialLoginButton(
                stringResource(R.string.login_by_microsoft),
                FontAwesomeIcons.Brands.Microsoft
            )
        }
        TextDivider("lub")
        TextInput(
            value = email,
            onValueChange = {email = it},
            label = stringResource(R.string.email),
            placeholder = stringResource(R.string.email_placeholder),
            error = error
//            stringResource(R.string.error_invalid_credentials)
        )
        TextInput(
            value = password,
            onValueChange = {password = it},
            label = stringResource(R.string.password),
            inputType = InputType.TYPE_TEXT_VARIATION_PASSWORD,
            valueVisible = false
        )
    }
}