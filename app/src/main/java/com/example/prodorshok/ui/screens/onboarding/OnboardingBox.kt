package com.example.prodorshok.ui.screens.onboarding

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.LottieComposition
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun OnboardingBox(
    lottieFile: String,
    text: String,
    animationFirst: Boolean
) {
    val composition by rememberLottieComposition(LottieCompositionSpec.Asset(lottieFile))
    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = LottieConstants.IterateForever
    )

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 180.dp, max = 250.dp)
            .padding(vertical = 12.dp, horizontal = 2.dp) // Reduced horizontal padding here
    ) {
        val boxHeight = maxHeight
        val boxWidth = maxWidth
        val spacing = if (boxWidth < 400.dp) 8.dp else 16.dp

        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(spacing)
        ) {
            if (animationFirst) {
                AnimationBox(
                    modifier = Modifier.weight(1f),
                    composition = composition,
                    progress = progress,
                    maxHeight = boxHeight
                )
                TextBox(
                    modifier = Modifier.weight(1f),
                    text = text,
                    maxWidth = boxWidth / 2 // roughly half width for text
                )
            } else {
                TextBox(
                    modifier = Modifier.weight(1f),
                    text = text,
                    maxWidth = boxWidth / 2
                )
                AnimationBox(
                    modifier = Modifier.weight(1f),
                    composition = composition,
                    progress = progress,
                    maxHeight = boxHeight
                )
            }
        }
    }
}

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun AnimationBox(
    modifier: Modifier = Modifier,
    composition: LottieComposition?,
    progress: Float,
    maxHeight: androidx.compose.ui.unit.Dp
) {
    BoxWithConstraints(
        modifier = modifier.fillMaxHeight(),
        contentAlignment = Alignment.Center
    ) {
        // Scale factor smaller on small screens
        val scaleFactor = when {
            maxHeight < 180.dp -> 1.0f
            maxHeight < 220.dp -> 1.15f
            else -> 1.3f
        }
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .scale(scaleFactor)
                .padding(4.dp),
            contentAlignment = Alignment.Center
        ) {
            LottieAnimation(
                composition = composition,
                progress = { progress },
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@Composable
fun TextBox(
    modifier: Modifier = Modifier,
    text: String,
    maxWidth: androidx.compose.ui.unit.Dp
) {
    Box(
        modifier = modifier
            .fillMaxHeight()
            .widthIn(max = maxWidth),
        contentAlignment = Alignment.Center
    ) {
        // Responsive font size based on maxWidth
        val fontSize = when {
            maxWidth < 180.dp -> 16.sp
            maxWidth < 250.dp -> 18.sp
            else -> 20.sp
        }
        Text(
            text = text,
            fontSize = fontSize,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 8.dp)
        )
    }
}
