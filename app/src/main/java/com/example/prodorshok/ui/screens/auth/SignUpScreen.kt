package com.example.prodorshok.ui.screens.auth

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.prodorshok.ui.components.AssetIcon
import com.example.prodorshok.viewmodel.auth.AuthViewModel

@Composable
fun SignUpScreen(
    navController: NavController,
    startGoogleSignIn: () -> Unit,
    authViewModel: AuthViewModel = viewModel()) {
    val context = LocalContext.current

    val email by remember { authViewModel.email }
    val password by remember { authViewModel.password }
    val confirmPassword by remember { authViewModel.confirmPassword }
    val isLoading by remember { authViewModel.isLoading }

    var fullName by remember { mutableStateOf("") }
    var agreeToTerms by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {
        // Top Menu (Icon and Dropdown)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, start = 16.dp, end = 16.dp)
        ) {
            var expanded by remember { mutableStateOf(false) }

            // Back Button (cross-shaped icon)
            IconButton(
                onClick = { navController.navigate("login") },
                modifier = Modifier.align(Alignment.TopEnd)
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Back",
                    tint = Color.White
                )
            }

            // Dropdown Menu (Three-Dot Icon)
            Row(
                modifier = Modifier.align(Alignment.TopStart),
                horizontalArrangement = Arrangement.Start
            ) {
                IconButton(
                    onClick = { expanded = true },
                    modifier = Modifier.size(40.dp)
                    ) {
                    Icon(Icons.Default.MoreVert, contentDescription = "Menu", tint = Color.White)
                }

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    DropdownMenuItem(
                        text = { Text("Need Help?") },
                        onClick = { /* TODO: Add help functionality */ }
                    )
                    DropdownMenuItem(
                        text = { Text("Contact Us") },
                        onClick = { /* TODO: Add contact functionality */ }
                    )
                    DropdownMenuItem(
                        text = { Text("Give Feedback") },
                        onClick = { /* TODO: Add feedback functionality */ }
                    )
                }
            }
        }

        // Background and SignUp Form (Your existing layout below)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(230.dp)
                .clip(RoundedCornerShape(bottomStart = 44.dp, bottomEnd = 44.dp))
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color(0xFFA9C7C3), Color(0xFF4C8479))
                    )
                )
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 32.dp),
            horizontalAlignment = Alignment.Start,
        ) {
            Spacer(modifier = Modifier.height(64.dp)) // Push text downward from top

            Text(
                text = "Let’s\nCreate\nYour\nAccount",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 36.sp,
                lineHeight = 32.sp
            )

            Spacer(modifier = Modifier.height(24.dp)) // Bottom padding between text and inputs

            RoundedInputField(
                value = fullName,
                onValueChange = { fullName = it },
                label = "Full Name",
                icon = Icons.Default.Person
            )

            RoundedInputField(
                value = email,
                onValueChange = { authViewModel.email.value = it },
                label = "Email Address",
                icon = Icons.Default.Email
            )

            RoundedInputField(
                value = password,
                onValueChange = { authViewModel.password.value = it },
                label = "Password",
                icon = Icons.Default.Lock,
                isPassword = true // Enable password visibility toggle
            )

            RoundedInputField(
                value = confirmPassword,
                onValueChange = { authViewModel.confirmPassword.value = it },
                label = "Retype Password",
                icon = Icons.Default.Lock,
                isPassword = true // Enable password visibility toggle
            )

            Spacer(modifier = Modifier.height(4.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(checked = agreeToTerms, onCheckedChange = { agreeToTerms = it })
                Text(text = "I agree to the ", color = Color.Gray)
                Text(text = "Terms & Privacy", color = Color.Black, style = MaterialTheme.typography.labelMedium)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .clip(RoundedCornerShape(50))
                    .background(
                        Brush.horizontalGradient(
                            colors = listOf(Color(0xFFA9C7C3), Color(0xFF4C8479))
                        )
                    )
                    .clickable {
                        if (!agreeToTerms) {
                            Toast.makeText(
                                context,
                                "Please agree to the Terms & Privacy",
                                Toast.LENGTH_SHORT
                            ).show()
                            return@clickable
                        }
                        if (password != confirmPassword) {
                            Toast.makeText(context, "Passwords do not match", Toast.LENGTH_SHORT)
                                .show()
                            return@clickable
                        }
                        authViewModel.signUpUser(
                            onSignUpSuccess = {
                                Toast.makeText(context, "Account Created!", Toast.LENGTH_SHORT)
                                    .show()
                                navController.navigate("login") {
                                    popUpTo("signup") { inclusive = true }
                                }
                            },
                            onSignUpFailure = { error ->
                                Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
                            }
                        )
                    },
                contentAlignment = Alignment.Center
            ) {
                Text("Sign Up", color = Color.White)
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Continue with Google Button
            OutlinedButton(
                onClick = { startGoogleSignIn() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(25.dp),
                border = BorderStroke(1.dp, Color(0xFF4C8479)),
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
                            .size(40.dp)
                            .padding(end = 8.dp)
                    )
                    Text(
                        text = " Continue with Google",
                        color = Color.Black,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            TextButton(onClick = { navController.navigate("login") }) {
                Text(
                    text = "Have an account? Tap here to Login",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
            }

            if (isLoading) {
                Spacer(modifier = Modifier.height(16.dp))
                CircularProgressIndicator()
            }
        }
    }
}

@Composable
fun RoundedInputField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    icon: ImageVector,
    isPassword: Boolean = false
) {
    // State to control password visibility
    val passwordVisibility = remember { mutableStateOf(false) }

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        leadingIcon = { Icon(imageVector = icon, contentDescription = label) },
        // If isPassword is true, toggle between masked and visible text
        visualTransformation = if (isPassword && !passwordVisibility.value) PasswordVisualTransformation() else VisualTransformation.None,
        shape = RoundedCornerShape(50.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        trailingIcon = {
            if (isPassword) {
                IconButton(onClick = { passwordVisibility.value = !passwordVisibility.value }) {
                    Icon(
                        imageVector = if (passwordVisibility.value) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                        modifier = Modifier.size(24.dp),
                        contentDescription = if (passwordVisibility.value) "Hide Password" else "Show Password"
                    )
                }
            }
        },
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color(0xFF2B5F56),
            unfocusedBorderColor = Color(0xFF2B5F56)
        )
    )
}


