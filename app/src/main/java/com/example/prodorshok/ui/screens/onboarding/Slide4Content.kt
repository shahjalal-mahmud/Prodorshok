package com.example.prodorshok.ui.screens.onboarding

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.prodorshok.R
import kotlinx.coroutines.delay

@Composable
fun Slide4Content() {
    var visible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        delay(300)
        visible = true
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        // Title (takes only as much vertical space as it needs)
        if (visible) {
            Text(
                text = "From Confusion to Clarity\n— With Prodorshok",
                fontSize = 24.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                textAlign = TextAlign.Center
            )
        }

        // This Box constrains the images & lets them expand to fill
        Box(
            modifier = Modifier
                .weight(1f)            // take all leftover space
                .fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                OnboardingImage(
                    modifier = Modifier.weight(1f),   // each image shares equally
                    imageRes = R.drawable.career_path,
                    text = "Discover the\nright career\npath",
                    boxAlignment = Alignment.TopEnd
                )
                OnboardingImage(
                    modifier = Modifier.weight(1f),
                    imageRes = R.drawable.personalized_roadmap,
                    text = "Get a\npersonalized\nroadmap",
                    boxAlignment = Alignment.TopStart
                )
                OnboardingImage(
                    modifier = Modifier.weight(1f),
                    imageRes = R.drawable.talk_to_mentors,
                    text = "Talk to\nmentors",
                    boxAlignment = Alignment.TopEnd,
                    padding = PaddingValues(top = 12.dp, end = 24.dp)
                )
            }
        }

        // Leave room for the Continue button at the bottom
        Spacer(modifier = Modifier.height(72.dp))
    }
}