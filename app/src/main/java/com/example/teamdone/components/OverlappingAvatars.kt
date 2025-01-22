package com.example.teamdone.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times
import androidx.compose.ui.zIndex
import coil.compose.rememberAsyncImagePainter

@Composable
fun OverlappingAvatars(
    urls: List<String>,
    maxVisible: Int = 3,
    avatarSize: Dp = 25.dp,
    overlap: Dp = 16.dp
) {
    val visibleAvatars = urls.take(maxVisible)
    val extraCount = urls.size - maxVisible

    Row(
        modifier = Modifier.wrapContentWidth()
    ) {
        visibleAvatars.forEachIndexed {idx, url ->
            Image(
                painter = rememberAsyncImagePainter(url),
                contentDescription = "Avatar $idx",
                modifier = Modifier
                    .size(avatarSize)
                    .zIndex(idx.toFloat())
                    .offset(x = (-idx * overlap))
                    .clip(CircleShape)
                    .border(2.dp, Color.White, CircleShape),
                contentScale = ContentScale.Crop
            )
        }
        if (extraCount > 0) {
            Box(
                modifier = Modifier
                    .size(avatarSize)
                    .offset(x = (-visibleAvatars.size * overlap))
                    .clip(CircleShape)
                    .background(Color.Gray)
                    .border(2.dp, Color.White, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "+$extraCount",
                    color = Color.White,
                    style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold)
                )
            }
        }
    }
}