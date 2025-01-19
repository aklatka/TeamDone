package com.example.teamdone.components

import android.util.Size
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter

@Composable
fun ImageFromUrl(
    url: String,
    description: String = "",
    modifier: Modifier = Modifier,
    imageModifier: Modifier = Modifier.fillMaxSize(),
    contentScale: ContentScale = ContentScale.Fit,
) {

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier,
    ) {

        AsyncImage(
            model = url,
            contentDescription = description,
            modifier = imageModifier,
            contentScale = contentScale
        )
    }
}