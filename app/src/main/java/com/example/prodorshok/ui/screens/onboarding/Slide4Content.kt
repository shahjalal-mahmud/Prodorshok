package com.example.prodorshok.ui.screens.onboarding

import androidx.compose.foundation.layout.Arrangement
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

@Composable
fun Slide4Content() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp, vertical = 24.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = "From Confusion to Clarity — With Prodorshok",
            fontSize = 22.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp, bottom = 24.dp),
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
