package com.example.prodorshok.ui.screens

import android.util.Log
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.airbnb.lottie.compose.*
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay

@Composable
fun WelcomeScreen(navController: NavController) {
    val auth = FirebaseAuth.getInstance()
    val context = LocalContext.current

    // Animation setup
    val infiniteTransition = rememberInfiniteTransition(label = "logoScale")
    val scale by infiniteTransition.animateFloat(
        initialValue = 0.9f,
        targetValue = 1.1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = EaseInOutCubic),
            repeatMode = RepeatMode.Reverse
        ), label = "logoPulse"
    )

    // Lottie animation setup
    val composition by rememberLottieComposition(
        spec = LottieCompositionSpec.Url("https://lottie.host/1a71c4b5-9437-43b7-aaa4-0c626e88f124/YmDo3gdQzg.json")
    )
    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = LottieConstants.IterateForever
    )

    // Auto navigation
    LaunchedEffect(Unit) {
        delay(3000)
        if (auth.currentUser != null) {
            navController.navigate("home") {
                popUpTo("welcome") { inclusive = true }
            }
        } else {
            navController.navigate("login") {
                popUpTo("welcome") { inclusive = true }
            }
        }
    }

    // UI Layout
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(Color(0xFF004AAD), Color(0xFF00C6FF))
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Welcome to",
                color = Color.White,
                fontSize = 26.sp,
                fontWeight = FontWeight.Light
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Prodorshok",
                color = Color.White,
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.scale(scale)
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Lottie animation
            LottieAnimation(
                composition = composition,
                progress = { progress },
                modifier = Modifier.size(200.dp)
            )
        }
    }
}