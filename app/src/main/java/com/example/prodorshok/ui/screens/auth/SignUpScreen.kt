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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
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
import androidx.compose.ui.platform.LocalConfiguration
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

    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp

    val scrollState = rememberScrollState()

    Box(modifier = Modifier.fillMaxSize()) {
        var showTopCard by remember { mutableStateOf(false) }
        LaunchedEffect(Unit) { showTopCard = true }

        AnimatedVisibility(
            visible = showTopCard,
            enter = slideInVertically(
                initialOffsetY = { fullHeight -> -fullHeight },
                animationSpec = tween(durationMillis = 800)
            )
        ) {
            ReusableAnimatedCard(
                visible = showTopCard,
                enterFromTop = true,
                cardColor = Color(0xFFFFCD4E),
                cardShape = RoundedCornerShape(bottomStart = 30.dp, bottomEnd = 30.dp),
                basePadding = 12.dp
            ) {
                Column(
                    modifier = Modifier.height(if (screenHeight < 600) 180.dp else 220.dp),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.Top
                ) {
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

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = if (screenHeight < 600) 200.dp else 250.dp, start = 24.dp, end = 24.dp)
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.Start,
        ) {
            Spacer(modifier = Modifier.height(24.dp))

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

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = agreeToTerms,
                    onCheckedChange = { agreeToTerms = it },
                    colors = CheckboxDefaults.colors(
                        checkedColor = Color(0xFFFFCD4E)
                    )
                )

                Spacer(modifier = Modifier.width(8.dp)) // Spacing between checkbox and text

                AuthPromptText(
                    prompt = "I agree to the ",
                    actionText = "Terms & Privacy",
                    onActionClick = { navController.navigate("terms") }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

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
                        name = name,
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

            Spacer(modifier = Modifier.height(12.dp))

            ContinueWithGoogleButton(onClick = { startGoogleSignIn() })

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 12.dp),
                horizontalArrangement = Arrangement.Start
            ) {
                AuthPromptText(
                    prompt = "Already have an account? ",
                    actionText = "Tap here to Login",
                    onActionClick = { navController.navigate("login") }
                )
            }

            if (isLoading) {
                Spacer(modifier = Modifier.height(16.dp))
                CircularProgressIndicator()
            }

            Spacer(modifier = Modifier.height(48.dp)) // Extra space for bottom padding on small screens
        }
    }
}