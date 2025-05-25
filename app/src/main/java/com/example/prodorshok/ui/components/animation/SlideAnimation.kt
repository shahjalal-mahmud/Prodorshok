package com.example.prodorshok.ui.components.animation

import androidx.compose.animation.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun SlideTransition(
    targetState: Int,
    content: @Composable AnimatedContentScope.(Int) -> Unit
) {
    AnimatedContent(
        targetState = targetState,
        transitionSpec = {
            slideInHorizontally { fullWidth -> fullWidth } + fadeIn() togetherWith
                    slideOutHorizontally { fullWidth -> -fullWidth } + fadeOut()
        },
        content = content,
        modifier = Modifier
    )
}
