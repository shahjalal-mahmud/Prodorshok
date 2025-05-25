package com.example.prodorshok.ui.components.common

import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Icon
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest

@Composable
fun AssetIcon(filename: String, modifier: Modifier = Modifier.size(24.dp)) {
    val context = LocalContext.current

    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(context)
            .data("file:///android_asset/icons/$filename")
            .build()
    )

    Icon(
        painter = painter,
        contentDescription = null,
        modifier = modifier,
        tint = Color.Unspecified // 👈 This keeps original image color
    )
}

