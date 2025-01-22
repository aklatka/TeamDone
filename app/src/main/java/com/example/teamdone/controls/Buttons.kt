package com.example.teamdone.controls

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.teamdone.R

@Composable
fun PrimaryButton(
    text: String = "",
    onClick: () -> Unit = {},
    loading: Boolean = false,
    enabled: Boolean = true,
    modifier: Modifier = Modifier
) {

    Button(
        onClick = onClick,
        shape = RoundedCornerShape(5),
        modifier = modifier
            .padding(0.dp),
        border = BorderStroke(1.dp, colorResource(R.color.primary)),
        colors = ButtonDefaults.buttonColors(colorResource(R.color.primary)),
        enabled = enabled
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            if(loading) {
                Box(
                    modifier = Modifier
                        .size(20.dp)
                ) {
                    CircularProgressIndicator(
                        color = Color.White,
                        strokeWidth = 3.dp
                    )
                }
            } else {
                Text(text)
            }
        }
    }
}

@Composable
fun SecondaryButton(
    text: String = "Click me",
    onClick: () -> Unit = {},
    enabled: Boolean = true,
    modifier: Modifier = Modifier
) {

    Button(
        onClick = onClick,
        shape = RoundedCornerShape(5),
        modifier = modifier
            .padding(0.dp),
        colors = ButtonDefaults.buttonColors(
            Color.Transparent
        ),
        border = BorderStroke(1.dp, colorResource(R.color.primary)),
        enabled = enabled
    ) {
        Text(text, color = colorResource(R.color.primary))
    }
}

@Composable
fun SocialLoginButton(
    text: String,
    icon: ImageVector,
    onClick: () -> Unit = {}
) {

    Button(
        onClick = onClick,
        shape = RoundedCornerShape(5),
        modifier = Modifier.fillMaxWidth()
            .padding(0.dp),
        colors = ButtonDefaults.buttonColors(
            Color.Transparent
        ),
        border = BorderStroke(1.dp, colorResource(R.color.black))
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                text,
                modifier = Modifier.size(20.dp).padding(2.dp),
                tint = Color.Black
            )
            Text(text, color = colorResource(R.color.black),
                fontSize = 15.sp,
                fontWeight = FontWeight.Normal
            )
        }
    }
}