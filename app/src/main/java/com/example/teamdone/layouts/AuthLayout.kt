package com.example.teamdone.layouts

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.teamdone.R
import java.util.Locale

@Composable
fun AuthLayout(
    modifier: Modifier,
    signupMode: Boolean,
    actionsContent: @Composable (() -> Unit)? = null,
    content: @Composable () -> Unit,
) {

    Column(
        modifier = Modifier.padding(40.dp).fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            stringResource(R.string.app_name).lowercase(Locale.ROOT),
            fontWeight = FontWeight.Bold,
            fontSize = 35.sp,
            textAlign = TextAlign.Center,
            color = colorResource(R.color.primary),
        )
        Spacer(Modifier.height(10.dp))
        if(!signupMode) {
            Text(stringResource(
                if(signupMode) R.string.signup_subtitle
                else R.string.login_subtitle),
                textAlign = TextAlign.Center,
                fontSize = 20.sp
            )
        }
        Spacer(Modifier.height(20.dp))
        Column(
            modifier = Modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                content()
            }
            Column {
                actionsContent?.let {
                    it()
                }
            }
        }
    }
}