package com.example.prodorshok.ui.screens.onboarding

import android.content.Context
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
        Box(modifier = Modifier.fillMaxSize()) {

            // Smooth content change without slide effect
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


private fun completeOnboarding(context: Context, navController: NavController) {
    OnboardingPreference.setOnboardingCompleted(context, false)
    navController.navigate("login") {
        popUpTo("splash") { inclusive = true }
    }
}