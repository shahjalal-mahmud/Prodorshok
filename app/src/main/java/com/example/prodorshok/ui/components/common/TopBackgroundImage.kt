package com.example.prodorshok.ui.components.common

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun TopBackgroundImage(
    @DrawableRes imageRes: Int,
    imageHeight: Dp = 220.dp
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(imageHeight)
            .background(
                brush = Brush.verticalGradient(
                    listOf(
                        Color.White,
                        Color.White
                    )
                )
            )
    ) {
        Image(
            painter = painterResource(id = imageRes),
            contentDescription = "Top Background",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(imageHeight)
                .align(Alignment.TopCenter)
        )
    }
}
