package com.example.prodorshok.ui.screens.onboarding

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .padding(vertical = 12.dp, horizontal = 8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            if (animationFirst) {
                AnimationBox(
                    modifier = Modifier.weight(1f),
                    composition = composition,
                    progress = progress
                )
                TextBox(
                    modifier = Modifier.weight(1f),
                    text = text
                )
            } else {
                TextBox(
                    modifier = Modifier.weight(1f),
                    text = text
                )
                AnimationBox(
                    modifier = Modifier.weight(1f),
                    composition = composition,
                    progress = progress
                )
            }
        }
    }
}

@Composable
fun AnimationBox(
    modifier: Modifier = Modifier,
    composition: LottieComposition?,
    progress: Float
) {
    BoxWithConstraints(
        modifier = modifier.fillMaxHeight(),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .width(this.maxWidth)      // Use BoxWithConstraints scope directly
                .height(this.maxHeight)
                .scale(1.3f)               // Zoom safely inside bounds
                .padding(4.dp),
            contentAlignment = Alignment.Center
        ) {
            LottieAnimation(
                composition = composition,
                progress = { progress },   // Use lambda for compatibility
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@Composable
fun TextBox(
    modifier: Modifier = Modifier,
    text: String
) {
    Box(
        modifier = modifier
            .fillMaxHeight(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 8.dp)
        )
    }
}
