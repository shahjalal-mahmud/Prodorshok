package com.example.prodorshok.ui.screens.splash

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
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
import com.example.prodorshok.ui.navigation.Screen
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay

@SuppressLint("UnusedBoxWithConstraintsScope") // We actually use maxWidth/maxHeight
@Composable
fun SplashScreen(navController: NavController) {
    val auth = FirebaseAuth.getInstance()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        delay(2000)

        val onboardingCompleted = OnboardingPreference.hasCompletedOnboarding(context)
        val isLoggedIn = auth.currentUser != null

        when {
            !onboardingCompleted -> {
                navController.navigate("onboarding") {
                    popUpTo("splash") { inclusive = true }
                }
            }

            isLoggedIn -> {
                navController.navigate(Screen.Dashboard.route) {
                    popUpTo("splash") { inclusive = true }
                }
            }

            else -> {
                navController.navigate("login") {
                    popUpTo("splash") { inclusive = true }
                }
            }
        }
    }

    // Responsive layout
    BoxWithConstraints(
        modifier = Modifier.fillMaxSize()
    ) {
        val screenHeight = maxHeight

        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            // Lottie animation (responsive)
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
                    .height(screenHeight * 0.45f) // responsive height
            )

            // Centered App Name
            FancyProdorshokText()
        }
    }
}