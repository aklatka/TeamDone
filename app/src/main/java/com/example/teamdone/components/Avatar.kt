package com.example.teamdone.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun Avatar(
    url: String,
    size: Dp = 20.dp
) {

    ImageFromUrl(
        url,
        imageModifier = Modifier
            .size(size)
            .clip(CircleShape)
            .border(1.dp, Color.Black, CircleShape),
        contentScale = ContentScale.Crop
    )
}