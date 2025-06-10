package com.example.prodorshok.ui.screens.auth

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
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
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.prodorshok.R
import com.example.prodorshok.ui.components.auth.AuthInputFields
import com.example.prodorshok.ui.components.auth.ContinueWithGoogleButton
import com.example.prodorshok.ui.components.auth.LoginButton
import com.example.prodorshok.ui.components.common.AuthPromptText
import com.example.prodorshok.ui.components.common.ReusableAnimatedCard
import com.example.prodorshok.ui.components.common.TopBackgroundImage
import com.example.prodorshok.ui.navigation.Screen
import com.example.prodorshok.viewmodel.auth.AuthViewModel

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun LoginScreen(
    navController: NavController,
    startGoogleSignIn: () -> Unit,
    authViewModel: AuthViewModel = viewModel()
) {
    val context = LocalContext.current

    val email by authViewModel::email
    val password by authViewModel::password
    val isLoading by authViewModel::isLoading

    var showCard by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { showCard = true }

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = Brush.verticalGradient(colors = listOf(Color.White, Color.White)))
    ) {
        val screenWidth = maxWidth
        val screenHeight = maxHeight

        // Responsive values
        val logoSize = when {
            screenWidth < 360.dp -> 100.dp
            screenWidth < 480.dp -> 140.dp
            else -> 180.dp
        }

        val horizontalPadding = when {
            screenWidth < 360.dp -> 12.dp
            screenWidth < 480.dp -> 16.dp
            else -> 24.dp
        }

        val verticalSpacing = when {
            screenHeight < 600.dp -> 8.dp
            else -> 16.dp
        }

        TopBackgroundImage(imageRes = R.drawable.top_wave_blue)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = horizontalPadding),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(top = verticalSpacing)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.prodorshok_logo),
                    contentDescription = "App Logo",
                    modifier = Modifier.size(logoSize)
                )

                Spacer(modifier = Modifier.height(verticalSpacing))

                Text(
                    text = "Your Career, Our Guidance",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontFamily = FontFamily(Font(R.font.rocatwo_regular)),
                        fontWeight = FontWeight.SemiBold,
                        color = Color.Black
                    )
                )

                Spacer(modifier = Modifier.height(verticalSpacing))

                AuthInputFields(
                    email = email,
                    password = password,
                    onEmailChange = { authViewModel.email = it },
                    onPasswordChange = { authViewModel.password = it }
                )
            }

            ReusableAnimatedCard(
                visible = showCard,
                enterFromTop = false,
                cardColor = Color(0xFFFFCD4E),
                cardShape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp),
                basePadding = horizontalPadding
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    TextButton(onClick = { navController.navigate("forgot_password") }) {
                        Text("Forgot Password?", color = Color.Black)
                    }

                    Spacer(modifier = Modifier.height(verticalSpacing))

                    LoginButton(
                        isLoading = isLoading,
                        onClick = {
                            authViewModel.loginUser(
                                onLoginSuccess = {
                                    navController.navigate(Screen.Dashboard.route) {
                                        popUpTo("login") { inclusive = true }
                                    }
                                },
                                onLoginFailure = { error ->
                                    Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
                                }
                            )
                        }
                    )

                    Spacer(modifier = Modifier.height(verticalSpacing))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Divider(modifier = Modifier.weight(1f), color = Color.Black)
                        Text(
                            text = "  or  ",
                            color = Color.Black,
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Divider(modifier = Modifier.weight(1f), color = Color.Black)
                    }

                    Spacer(modifier = Modifier.height(verticalSpacing))

                    ContinueWithGoogleButton(onClick = startGoogleSignIn)

                    Spacer(modifier = Modifier.height(verticalSpacing))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Start
                    ) {
                        AuthPromptText(onActionClick = { navController.navigate("signup") })
                    }
                }
            }
        }
    }
}
