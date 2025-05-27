package com.example.prodorshok.ui.screens.onboarding

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
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
            .padding(vertical = 4.dp) // Reduced vertical padding between rows
    ) {
        val screenWidth = maxWidth

        val textFontSize = if (screenWidth < 360.dp) 14.sp else 18.sp

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            val animationModifier = Modifier
                .weight(1f)
                .padding(4.dp)

            val textModifier = Modifier
                .weight(1f)
                .padding(4.dp)

            if (animationFirst) {
                AnimationContent(composition, progress, animationModifier)
                TextContent(text, textFontSize, textModifier)
            } else {
                TextContent(text, textFontSize, textModifier)
                AnimationContent(composition, progress, animationModifier)
            }
        }
    }
}


        @SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
private fun AnimationContent(
    composition: LottieComposition?,
    progress: Float,
    modifier: Modifier = Modifier
) {
    BoxWithConstraints(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        val side = maxWidth.coerceAtMost(maxHeight) // Use the smallest to ensure square box

        Box(
            modifier = Modifier
                .width(side)
                .height(side), // Square box
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
private fun TextContent(
    text: String,
    fontSize: TextUnit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            fontSize = fontSize,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center
        )
    }
}
