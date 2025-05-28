package com.example.prodorshok.ui.screens.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.prodorshok.R
import com.example.prodorshok.ui.components.auth.ResetPasswordButton
import com.example.prodorshok.ui.components.common.AssetIcon
import com.example.prodorshok.ui.components.common.AuthPromptText
import com.example.prodorshok.ui.components.common.AuthSubtitleText
import com.example.prodorshok.ui.components.common.AuthTitleText
import com.example.prodorshok.ui.components.common.ReusableAnimatedCard
import com.example.prodorshok.ui.components.common.TopBackgroundImage
import com.example.prodorshok.viewmodel.auth.AuthViewModel

@Composable
fun ForgotPasswordScreen(
    navController: NavController,
    authViewModel: AuthViewModel = viewModel()
) {
    val context = LocalContext.current
    val email by authViewModel::email

    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp

    val cardHeight = when {
        screenHeight < 600 -> 400.dp
        else -> 450.dp
    }

    var showCard by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { showCard = true }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Top background image
        TopBackgroundImage(imageRes = R.drawable.top_wave_blue)

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .align(Alignment.TopCenter),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(112.dp))

            AssetIcon(
                filename = "forgot_password.png",
                modifier = Modifier.size(120.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            AuthTitleText("Forgot\nPassword?")

            Spacer(modifier = Modifier.height(8.dp))

            AuthSubtitleText("No worries, we'll send you\nreset instructions")
        }

        // Responsive Animated Card
        ReusableAnimatedCard(
            visible = showCard,
            enterFromTop = false,
            gradientBrush = Brush.verticalGradient(
                colors = listOf(
                    Color(0xFFFFD54F),
                    Color(0xFFFFCD4E),
                    Color(0xFFFFCD4E)
                )
            ),
            cardShape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp),
            basePadding = 24.dp,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .height(cardHeight)
        ) {
            OutlinedTextField(
                value = email,
                onValueChange = { authViewModel.email = it },
                label = { Text("Email") },
                leadingIcon = {
                    Icon(Icons.Default.Email, contentDescription = "Email Icon")
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(24.dp))

            ResetPasswordButton(
                email = email,
                isLoading = authViewModel.isLoading,
                context = context,
                navController = navController,
                authViewModel = authViewModel,
                currentSuccess = "Password reset email sent!",
                currentError = "Failed to send reset email."
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                AuthPromptText(
                    prompt = "Back to ",
                    actionText = "Login",
                    onActionClick = { navController.navigate("login") }
                )
            }
        }
    }
}