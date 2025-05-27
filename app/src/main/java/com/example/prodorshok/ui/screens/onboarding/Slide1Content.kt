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
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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

@SuppressLint("UnusedBoxWithConstraintsScope") // We actually use maxWidth/maxHeight
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun Slide1Content() {
    var visible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(300)
        visible = true
    }

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
    ) {
        val screenHeight = maxHeight
        val screenWidth = maxWidth

        // Wrap everything in a white background Surface to override dark mode
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color.White
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
            ) {
                // Lottie animation (responsive)
                val composition by rememberLottieComposition(
                    LottieCompositionSpec.Asset("lottie/confusion.lottie")
                )
                LottieAnimation(
                    composition = composition,
                    iterations = LottieConstants.IterateForever,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(screenHeight * 0.6f)
                        .align(Alignment.Center)
                )

                // Text bubble
                PopInAnimation(visible) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .width(screenWidth * 0.45f)
                            .height(screenHeight * 0.13f)
                            .align(Alignment.TopStart)
                            .offset(
                                x = screenWidth * 0.4f,
                                y = screenHeight * 0.24f
                            )
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.text_bubble),
                            contentDescription = "Text Bubble",
                            modifier = Modifier.fillMaxSize()
                        )
                        Text(
                            text = "Lost in career\nconfusion",
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontSize = 20.sp,
                                color = Color.Black
                            ),
                            textAlign = TextAlign.Center,
                            modifier = Modifier.offset(y = (-8).dp)
                        )
                    }
                }
            }
        }
    }
}
