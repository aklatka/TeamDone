package com.example.teamdone.screens

import android.text.InputType
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.teamdone.NavigationItem
import com.example.teamdone.R
import com.example.teamdone.controls.PrimaryButton
import com.example.teamdone.controls.SecondaryButton
import com.example.teamdone.controls.TextInput
import com.example.teamdone.layouts.AuthLayout

@Composable
fun SignupScreen(
    navController: NavHostController
) {
    var firstname by remember { mutableStateOf("") };
    var lastname by remember { mutableStateOf("") };
    var email by remember { mutableStateOf("") };
    var password by remember { mutableStateOf("") };
    var passwordConfirm by remember { mutableStateOf("") };

    AuthLayout(
        modifier = Modifier,
        signupMode = false,
        actionsContent = {
            Spacer(Modifier.height(30.dp))
            PrimaryButton(stringResource(R.string.sign_up))
            Spacer(Modifier.height(10.dp))
            SecondaryButton(
                stringResource(R.string.already_have_account),
                onClick = {
                    navController.navigate(NavigationItem.AuthLogin.route)
                }
            )
        }
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
    }
}