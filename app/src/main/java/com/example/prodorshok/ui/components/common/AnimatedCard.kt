package com.example.prodorshok.ui.components.common

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
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
    val screenWidth = LocalConfiguration.current.screenWidthDp

    // Adjust padding based on screen size
    val contentPadding = when {
        screenWidth < 360 -> PaddingValues(basePadding / 2)
        screenWidth < 600 -> PaddingValues(basePadding)
        else -> PaddingValues(basePadding * 1.5f)
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
            modifier = Modifier.fillMaxWidth(0.95f),
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
                    .padding(contentPadding)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                content = content
            )
        }
    }
}
