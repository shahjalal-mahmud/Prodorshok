package com.example.prodorshok.ui.screens.auth

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.prodorshok.ui.components.auth.AuthTopMenu
import com.example.prodorshok.ui.components.auth.ContinueWithGoogleButton
import com.example.prodorshok.ui.components.auth.SignUpButton
import com.example.prodorshok.ui.components.common.AuthPromptText
import com.example.prodorshok.ui.components.common.AuthTitleText
import com.example.prodorshok.ui.components.common.ReusableAnimatedCard
import com.example.prodorshok.ui.components.common.RoundedInputField
import com.example.prodorshok.viewmodel.auth.AuthViewModel

@Composable
fun SignUpScreen(
    navController: NavController,
    startGoogleSignIn: () -> Unit,
    authViewModel: AuthViewModel = viewModel()
) {
    val context = LocalContext.current
    val name by authViewModel::name
    val email by authViewModel::email
    val password by authViewModel::password
    val confirmPassword by authViewModel::confirmPassword
    val isLoading by authViewModel::isLoading

    var fullName by remember { mutableStateOf("") }
    var agreeToTerms by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {

        var showTopCard by remember { mutableStateOf(false) }

        LaunchedEffect(Unit) {
            showTopCard = true
        }

        AnimatedVisibility(
            visible = showTopCard,
            enter = slideInVertically(
                initialOffsetY = { fullHeight -> -fullHeight }, // Slide from top
                animationSpec = tween(durationMillis = 800)
            )
        ) {
            ReusableAnimatedCard(
                visible = showTopCard,
                enterFromTop = true,
                cardColor = Color(0xFFFFCD4E),
                cardShape = RoundedCornerShape(bottomStart = 30.dp, bottomEnd = 30.dp),
                basePadding = 12.dp // Adjusted base padding
            ) {
                Column(
                    modifier = Modifier
                        .height(220.dp),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.Top
                ) {
                    // Reusable 3-dot menu
                    AuthTopMenu(
                        navController = navController,
                        onMenuItemClick = { menuItem ->
                            when (menuItem) {
                                "Need Help" -> navController.navigate("need_help")
                                "Contact Us" -> navController.navigate("contact_us")
                                "Give Feedback" -> navController.navigate("feedback")
                            }
                        },
                        iconTintColor = Color.Black
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Title Text
                    AuthTitleText(
                        text = "Let's Create \nYour \nAccount",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 36.dp),
                        textAlign = TextAlign.Start
                    )
                }
            }
        }

        // SignUp Form
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 250.dp, start = 32.dp, end = 32.dp),
            horizontalAlignment = Alignment.Start,
        ) {
            Spacer(modifier = Modifier.height(24.dp)) // Bottom padding between text and inputs

            // Input Fields for Full Name, Email, Password, Confirm Password
            RoundedInputField(
                value = fullName,
                onValueChange = { fullName = it },
                label = "Full Name",
                icon = Icons.Default.Person
            )

            RoundedInputField(
                value = email,
                onValueChange = { authViewModel.email = it },
                label = "Email Address",
                icon = Icons.Default.Email
            )

            RoundedInputField(
                value = password,
                onValueChange = { authViewModel.password = it },
                label = "Password",
                icon = Icons.Default.Lock,
                isPassword = true
            )

            RoundedInputField(
                value = confirmPassword,
                onValueChange = { authViewModel.confirmPassword = it },
                label = "Retype Password",
                icon = Icons.Default.Lock,
                isPassword = true
            )

            Spacer(modifier = Modifier.height(8.dp)) // Space before the Terms checkbox

            // Checkbox for Terms and Conditions
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(checked = agreeToTerms, onCheckedChange = { agreeToTerms = it })
                AuthPromptText(
                    prompt = "I agree to the ",
                    actionText = "Terms & Privacy",
                    onActionClick = { navController.navigate("terms") }
                )
            }

            Spacer(modifier = Modifier.height(16.dp)) // Space before the Sign-Up button

            // Sign Up Button
            SignUpButton(
                name = name,
                isLoading = isLoading,
                agreeToTerms = agreeToTerms,
                passwordsMatch = password == confirmPassword,
                onValidationFailed = { error ->
                    Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
                },
                onSignUpClick = { name ->
                    authViewModel.signUpUser(
                        name = name,  // Pass name to signUpUser
                        onSignUpSuccess = {
                            Toast.makeText(context, "Account Created!", Toast.LENGTH_SHORT).show()
                            navController.navigate("login") {
                                popUpTo("signup") { inclusive = true }
                            }
                        },
                        onSignUpFailure = { error ->
                            Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
                        }
                    )
                }
            )

            Spacer(modifier = Modifier.height(12.dp)) // Space before Continue with Google button

            // Continue with Google Button
            ContinueWithGoogleButton(
                onClick = { startGoogleSignIn() }
            )

            Spacer(modifier = Modifier.height(16.dp)) // Space before Login Text

            // Already have an account? Login text
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ) {
                AuthPromptText(
                    prompt = "Already have an account? ",
                    actionText = "Tap here to Login",
                    onActionClick = { navController.navigate("login") }
                )
            }

            // Loading indicator
            if (isLoading) {
                Spacer(modifier = Modifier.height(16.dp))
                CircularProgressIndicator()
            }
        }
    }
}
