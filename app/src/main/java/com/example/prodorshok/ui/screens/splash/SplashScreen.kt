package com.example.prodorshok.ui.screens.splash

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.prodorshok.data.preferences.OnboardingPreference
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController) {
    val auth = FirebaseAuth.getInstance()
    val context = LocalContext.current

    // Navigate after a short delay (to allow branding time without jank)
    LaunchedEffect(Unit) {
        delay(3000) // Shorter delay for faster user experience

        val onboardingCompleted = OnboardingPreference.hasCompletedOnboarding(context)

        if (!onboardingCompleted) {
            navController.navigate("onboarding") {
                popUpTo("splash") { inclusive = true }
            }
        } else if (auth.currentUser != null) {
            navController.navigate("home") {
                popUpTo("splash") { inclusive = true }
            }
        } else {
            navController.navigate("login") {
                popUpTo("splash") { inclusive = true }
            }
        }
    }

    // Updated UI layout with no background
    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        // Rocket Lottie at the bottom
        val composition by rememberLottieComposition(
            LottieCompositionSpec.Asset("lottie/rocket.lottie")
        )

        LottieAnimation(
            composition = composition,
            iterations = LottieConstants.IterateForever,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(bottom = 48.dp)
                .height(600.dp) // Adjust height based on your Lottie design
        )

        // Prodorshok text in the center
        FancyProdorshokText()
    }
}