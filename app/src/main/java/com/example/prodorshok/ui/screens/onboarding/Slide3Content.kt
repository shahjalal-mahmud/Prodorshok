package com.example.prodorshok.ui.screens.onboarding

import android.annotation.SuppressLint
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.prodorshok.R
import com.example.prodorshok.ui.components.animation.PopInAnimation
import kotlinx.coroutines.delay

@SuppressLint("UnusedBoxWithConstraintsScope")
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun Slide3Content() {
    var visible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(300)
        visible = true
    }

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        val screenHeight = maxHeight
        val screenWidth = maxWidth

        // Lottie animation
        val composition by rememberLottieComposition(
            LottieCompositionSpec.Asset("lottie/unclear_paths.lottie")
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(screenHeight * 0.55f) // 55% of screen height
                .align(Alignment.Center)
        ) {
            LottieAnimation(
                composition = composition,
                iterations = LottieConstants.IterateForever,
                modifier = Modifier
                    .fillMaxSize()
                    .scale(1.8f) // Adjust zoom level
            )
        }

        // Text bubble above Lottie
        PopInAnimation(visible) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .width(screenWidth * 0.50f) // 50% of screen width
                    .height(screenHeight * 0.16f) // 16% of screen height
                    .align(Alignment.TopCenter)
                    .offset(
                        x = screenWidth * 0.22f,  // ~90dp based on width
                        y = screenHeight * 0.17f  // ~140dp based on height
                    )
            ) {
                Image(
                    painter = painterResource(id = R.drawable.text_bubble),
                    contentDescription = "Text Bubble",
                    modifier = Modifier.fillMaxSize()
                )
                Text(
                    text = "There’s no clear\nroadmap to follow",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontSize = 20.sp,
                        color = Color.Black
                    ),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.offset(y = (-10).dp)
                )
            }
        }
    }
}
