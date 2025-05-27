package com.example.prodorshok.ui.screens.onboarding

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun Slide4Content() {
    BoxWithConstraints(
        modifier = Modifier.fillMaxSize()
    ) {
        val screenHeight = maxHeight
        val screenWidth = maxWidth

        val horizontalPadding = screenWidth * 0.06f
        val verticalPadding = screenHeight * 0.015f // Smaller vertical padding
        val titleFontSize = if (screenWidth < 360.dp) 18.sp else 22.sp
        val titleTopBottomPadding = screenHeight * 0.015f

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = horizontalPadding, vertical = verticalPadding)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                text = "From Confusion to Clarity — With Prodorshok",
                fontSize = titleFontSize,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = titleTopBottomPadding, bottom = titleTopBottomPadding),
                textAlign = TextAlign.Center
            )

            OnboardingBox(
                lottieFile = "lottie/career_path.lottie",
                text = "Discover the right career path",
                animationFirst = true
            )

            OnboardingBox(
                lottieFile = "lottie/unclear_paths.lottie",
                text = "Get a personalized roadmap",
                animationFirst = false
            )

            OnboardingBox(
                lottieFile = "lottie/talk_to_mentors.lottie",
                text = "Talk to mentors",
                animationFirst = true
            )
        }
    }
}
