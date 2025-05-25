package com.example.prodorshok.ui.screens.onboarding

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
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

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun Slide1Content() {
    var visible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(300)
        visible = true
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        // Lottie animation in background
        val composition by rememberLottieComposition(
            LottieCompositionSpec.Asset("lottie/confusion.lottie")
        )
        LottieAnimation(
            composition = composition,
            iterations = LottieConstants.IterateForever,
            modifier = Modifier
                .fillMaxWidth()
                .height(600.dp)
                .align(Alignment.Center)
        )

        // Text bubble positioned over Lottie (no gap)
        PopInAnimation(visible) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .width(190.dp)
                    .height(110.dp)
                    .align(Alignment.TopCenter)
                    .offset(x = 150.dp, y = 170.dp) // Adjust based on Lottie’s transparent top space
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
                    modifier = Modifier.offset(y = (-8).dp)  // <-- shift text 10dp upward
                )
            }
        }
    }
}
