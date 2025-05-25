package com.example.prodorshok.ui.utils

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun Modifier.slideInFromTop(
    show: Boolean,
    offsetWhenHidden: Dp = (-60).dp,
    durationMillis: Int = 300
): Modifier {
    val offsetY by animateDpAsState(
        targetValue = if (show) 0.dp else offsetWhenHidden,
        animationSpec = tween(durationMillis),
        label = "slideInFromTop"
    )
    return this.offset(y = offsetY)
}
