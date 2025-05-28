package com.example.prodorshok.ui.components.animation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.runtime.Composable

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun PopInAnimation(
    visible: Boolean,
    delayMillis: Int = 0,
    content: @Composable () -> Unit
) {
    AnimatedVisibility(
        visible = visible,
        enter = scaleIn(
            animationSpec = tween(durationMillis = 10, delayMillis = delayMillis)
        ) + fadeIn(
            animationSpec = tween(durationMillis = 10, delayMillis = delayMillis)
        )
    ) {
        content()
    }
}
