package com.example.teamdone.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun TextDivider(text: String) {

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        HorizontalDivider(
            color = Color.Gray,
            modifier = Modifier.weight(1f).height(1.dp)
        )
        Text(
            text,
            color = Color.Gray,
            modifier = Modifier.padding(5.dp, 5.dp)
        )
        HorizontalDivider(
            color = Color.Gray,
            modifier = Modifier.weight(1f).height(1.dp)
        )
    }
}