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
import androidx.compose.foundation.layout.wrapContentHeight
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
import androidx.compose.ui.unit.sp
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
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp

    val email by authViewModel::email

    var showCard by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { showCard = true }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Top background image
        TopBackgroundImage(imageRes = R.drawable.top_wave_blue)

        // Top Column with image and texts
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = screenHeight * 0.22f,
                    start = screenWidth * 0.06f,
                    end = screenWidth * 0.06f
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AssetIcon(
                filename = "forgot_password.png",
                modifier = Modifier.size(screenWidth * 0.3f)
            )

            Spacer(modifier = Modifier.height(screenHeight * 0.02f))

            AuthTitleText(
                text = "Forgot\nPassword?",
                fontSize = (screenWidth.value * 0.065).sp
            )

            Spacer(modifier = Modifier.height(screenHeight * 0.01f))

            AuthSubtitleText(
                text = "No worries, we'll send you\nreset instructions",
                fontSize = (screenWidth.value * 0.042).sp
            )
        }

        // Bottom Animated Card
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
            basePadding = screenWidth * 0.06f,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .wrapContentHeight()
                .fillMaxWidth()
        ) {
            OutlinedTextField(
                value = email,
                onValueChange = { authViewModel.email = it },
                label = {
                    Text("Email", fontSize = (screenWidth.value * 0.04).sp)
                },
                leadingIcon = {
                    Icon(
                        Icons.Default.Email,
                        contentDescription = "Email Icon",
                        modifier = Modifier.size(screenWidth * 0.06f)
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(screenHeight * 0.03f))

            ResetPasswordButton(
                email = email,
                isLoading = authViewModel.isLoading,
                context = context,
                navController = navController,
                authViewModel = authViewModel,
                currentSuccess = "Password reset email sent!",
                currentError = "Failed to send reset email."
            )

            Spacer(modifier = Modifier.height(screenHeight * 0.015f))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                AuthPromptText(
                    prompt = "Back to ",
                    actionText = "Login",
                    onActionClick = { navController.navigate("login") },
                    fontSize = (screenWidth.value * 0.04).sp
                )
            }
        }
    }
}
