package com.example.prodorshok.ui.components.common

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun ReusableAnimatedCard(
    visible: Boolean,
    enterFromTop: Boolean = false,
    modifier: Modifier = Modifier,
    cardShape: RoundedCornerShape = RoundedCornerShape(30.dp),
    cardColor: Color = Color(0xFFFFCD4E),
    gradientBrush: Brush? = null,
    basePadding: Dp = 24.dp,
    content: @Composable ColumnScope.() -> Unit
) {
    val config = LocalConfiguration.current
    val screenWidth = config.screenWidthDp
    val screenHeight = config.screenHeightDp

    val cardWidthFraction = when {
        screenWidth < 360 -> 0.95f
        screenWidth < 600 -> 0.85f
        else -> 0.7f
    }

    val cardMaxHeight = when {
        screenHeight < 600 -> (screenHeight * 0.5f).dp
        else -> (screenHeight * 0.6f).dp
    }

    val contentPadding = when {
        screenWidth < 360 -> (basePadding / 2)
        screenWidth < 600 -> basePadding
        else -> (basePadding.value * 1.5f).dp
    }

    AnimatedVisibility(
        visible = visible,
        enter = slideInVertically(
            initialOffsetY = { fullHeight -> if (enterFromTop) -fullHeight else fullHeight },
            animationSpec = tween(durationMillis = 800)
        ),
        modifier = modifier
    ) {
        Surface(
            modifier = Modifier
                .padding(contentPadding)
                .fillMaxWidth() // Use full width
                .heightIn(max = cardMaxHeight),
            shape = cardShape,
            shadowElevation = 8.dp,
            color = if (gradientBrush == null) cardColor else Color.Transparent
        ) {
            Column(
                modifier = Modifier
                    .then(
                        if (gradientBrush != null) Modifier.background(
                            brush = gradientBrush,
                            shape = cardShape
                        ) else Modifier
                    )
                    .padding(all = contentPadding)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                content = content
            )
        }
    }
}