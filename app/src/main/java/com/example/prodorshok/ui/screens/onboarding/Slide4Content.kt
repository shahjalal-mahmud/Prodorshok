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
            .padding(horizontal = 24.dp, vertical = 16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top
    ) {
        // Title Text
        Text(
            text = "From Confusion to Clarity — With Prodorshok",
            fontSize = 22.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp), // Controlled spacing below title
            textAlign = TextAlign.Center
        )

        // Onboarding Content
        OnboardingBox(
            lottieFile = "lottie/career_path.lottie",
            text = "Discover the right career path",
            animationFirst = true
        )

        OnboardingBox(
            lottieFile = "lottie/unclear_paths.lottie",   // Need to replace this
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
