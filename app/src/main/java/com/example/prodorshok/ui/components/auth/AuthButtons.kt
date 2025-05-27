package com.example.prodorshok.ui.components.auth

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.prodorshok.ui.components.common.AssetIcon
import com.example.prodorshok.viewmodel.auth.AuthViewModel

@Composable
fun AuthButton(
    text: String,
    isLoading: Boolean = false,
    gradientColors: List<Color>,
    gradientOrientation: GradientOrientation = GradientOrientation.HORIZONTAL,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp

    // Adjust button height and text size based on screen width
    val buttonHeight = when {
        screenWidth < 360 -> 44.dp
        screenWidth < 600 -> 50.dp
        else -> 60.dp
    }

    val fontSize = when {
        screenWidth < 360 -> MaterialTheme.typography.labelMedium.fontSize
        screenWidth < 600 -> MaterialTheme.typography.labelLarge.fontSize
        else -> MaterialTheme.typography.titleMedium.fontSize
    }

    val alpha = if (isLoading) 0.6f else 1f
    val brush = when (gradientOrientation) {
        GradientOrientation.HORIZONTAL -> Brush.horizontalGradient(colors = gradientColors)
        GradientOrientation.VERTICAL -> Brush.verticalGradient(colors = gradientColors)
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(buttonHeight)
            .clip(RoundedCornerShape(50.dp))
            .background(brush)
            .alpha(alpha)
            .clickable(enabled = enabled && !isLoading, onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                color = Color.White,
                strokeWidth = 2.dp,
                modifier = Modifier.size(buttonHeight * 0.4f)
            )
        } else {
            Text(
                text = text,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = fontSize
            )
        }
    }
}

enum class GradientOrientation {
    HORIZONTAL, VERTICAL
}

// Refactored SignUpButton to include name
@Composable
fun SignUpButton(
    name: String,  // Add name parameter here
    isLoading: Boolean = false,
    agreeToTerms: Boolean = false,
    passwordsMatch: Boolean = true,
    onValidationFailed: (String) -> Unit = {},
    onSignUpClick: (String) -> Unit // Accept name in the onClick
) {
    AuthButton(
        text = "Sign Up",
        isLoading = isLoading,
        gradientColors = listOf(Color(0xFFFFCD4E), Color(0xFFFFCD4E)),
        onClick = {
            if (!agreeToTerms) {
                onValidationFailed("Please agree to the Terms & Privacy")
                return@AuthButton
            }
            if (!passwordsMatch) {
                onValidationFailed("Passwords do not match")
                return@AuthButton
            }
            onSignUpClick(name)  // Pass name here
        }
    )
}

@Composable
fun LoginButton(
    isLoading: Boolean = false,
    onClick: () -> Unit
) {
    AuthButton(
        text = "Login",
        isLoading = isLoading,
        gradientColors = listOf(
            Color(0xFF1268f6),
            Color(0xFF1268f6),
            Color(0xFF1268f6)
        ),
        gradientOrientation = GradientOrientation.VERTICAL,
        onClick = onClick
    )
}

@Composable
fun ContinueWithGoogleButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp

    val buttonHeight = when {
        screenWidth < 360 -> 44.dp
        screenWidth < 600 -> 50.dp
        else -> 60.dp
    }

    val fontSize = when {
        screenWidth < 360 -> MaterialTheme.typography.bodySmall.fontSize
        screenWidth < 600 -> MaterialTheme.typography.bodyMedium.fontSize
        else -> MaterialTheme.typography.bodyLarge.fontSize
    }

    val iconSize = when {
        screenWidth < 360 -> 28.dp
        screenWidth < 600 -> 36.dp
        else -> 42.dp
    }

    OutlinedButton(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(buttonHeight),
        shape = RoundedCornerShape(25.dp),
        border = BorderStroke(1.dp, Color.Black),
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = Color.White,
            contentColor = Color.Black
        )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            AssetIcon(
                filename = "google_icon.png",
                modifier = Modifier
                    .size(iconSize)
                    .padding(end = 8.dp)
            )
            Text(
                text = "Continue with Google",
                color = Color.Black,
                fontSize = fontSize
            )
        }
    }
}


@Composable
fun ResetPasswordButton(
    email: String,
    isLoading: Boolean = false,
    context: android.content.Context,
    navController: NavController,
    authViewModel: AuthViewModel,
    currentSuccess: String,
    currentError: String
) {
    AuthButton(
        text = "Reset Password",
        isLoading = isLoading,
        gradientColors = listOf(Color(0xFF007DFF), Color(0xFF007DFF)),
        onClick = {
            authViewModel.sendPasswordReset(
                email = email,
                onSuccess = {
                    Toast.makeText(context, currentSuccess, Toast.LENGTH_SHORT).show()
                    navController.navigate("check_mail/$email")
                },
                onFailure = {
                    Toast.makeText(context, currentError, Toast.LENGTH_SHORT).show()
                }
            )
        }
    )
}
