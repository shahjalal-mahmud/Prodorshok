package com.example.prodorshok.ui.screens.onboarding

import android.content.Context
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.prodorshok.data.preferences.OnboardingPreference
import com.example.prodorshok.ui.components.common.ContinueToLoginButton
import kotlinx.coroutines.delay

@Composable
fun OnboardingScreen(navController: NavController) {
    val context = LocalContext.current
    var currentSlide by remember { mutableStateOf(0) }
    val totalSlides = 5

    // Automatically cycle through slides
    LaunchedEffect(Unit) {
        delay(3500)
        currentSlide = 1
        delay(3500)
        currentSlide = 2
        delay(3500)
        currentSlide = 3
        delay(3500)
        currentSlide = 4
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    ) {
        Column(modifier = Modifier.fillMaxSize()) {

            // Progress Bar at the top
            LinearProgressIndicator(
                progress = (currentSlide + 1).toFloat() / totalSlides,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(top = 16.dp),
                color = Color(0xFF3F51B5), // Your app's primary color
                trackColor = Color(0xFFE0E0E0) // Light gray background
            )

            // Main Box content
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
            ) {
                Crossfade(targetState = currentSlide) { slide ->
                    when (slide) {
                        0 -> Slide1Content()
                        1 -> Slide2Content()
                        2 -> Slide3Content()
                        3 -> Slide32Content()
                        4 -> Slide4Content()
                    }
                }

                if (currentSlide == 4) {
                    ContinueToLoginButton(
                        onClick = {
                            completeOnboarding(context, navController)
                        },
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(bottom = 32.dp)
                    )
                }
            }
        }
    }
}

private fun completeOnboarding(context: Context, navController: NavController) {
    OnboardingPreference.setOnboardingCompleted(context, true)
    navController.navigate("login") {
        popUpTo("splash") { inclusive = true }
    }
}