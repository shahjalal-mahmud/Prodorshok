package com.example.prodorshok.ui.components.common

import android.annotation.SuppressLint
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun TopBackgroundImage(
    @DrawableRes imageRes: Int,
    imageHeight: Dp = 220.dp
) {
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxWidth()
            .height(imageHeight)
            .background(Brush.verticalGradient(listOf(Color.White, Color.White)))
    ) {
        val dynamicHeight = if (maxWidth < 360.dp) 160.dp else imageHeight

        Image(
            painter = painterResource(id = imageRes),
            contentDescription = "Top Background",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(dynamicHeight)
                .align(Alignment.TopCenter)
        )
    }
}
