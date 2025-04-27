package com.example.prodorshok.ui.screens.auth

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
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
    authViewModel: AuthViewModel = viewModel()
) {
    val context = LocalContext.current
    val email by remember { authViewModel.email }
    val password by remember { authViewModel.password }
    val confirmPassword by remember { authViewModel.confirmPassword }
    val isLoading by remember { authViewModel.isLoading }

    var fullName by remember { mutableStateOf("") }
    var agreeToTerms by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {
        // Top Menu (Three Dots and Back Button)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp, start = 24.dp, end = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween, // Space between menu and back
            verticalAlignment = Alignment.Top
        ) {
            // Three-Dot Menu (Top Left)
            var expanded by remember { mutableStateOf(false) }

            Box {
                IconButton(
                    onClick = { expanded = true },
                    modifier = Modifier.size(40.dp)
                ) {
                    Icon(Icons.Default.MoreVert, contentDescription = "Menu", tint = Color.Black)
                }

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier.width(180.dp) // Optional: adjust width
                ) {
                    DropdownMenuItem(
                        text = { Text("Need Help?") },
                        onClick = { /* TODO */ }
                    )
                    DropdownMenuItem(
                        text = { Text("Contact Us") },
                        onClick = { /* TODO */ }
                    )
                    DropdownMenuItem(
                        text = { Text("Give Feedback") },
                        onClick = { /* TODO */ }
                    )
                    Divider()
                    DropdownMenuItem(
                        text = { Text("Settings") },
                        onClick = { /* TODO */ }
                    )
                    DropdownMenuItem(
                        text = { Text("Privacy Policy") },
                        onClick = { /* TODO */ }
                    )
                }
            }

            // Back Button (Now Top Right with Cross Icon)
            IconButton(
                onClick = { navController.popBackStack() },
                modifier = Modifier.size(40.dp)
            ) {
                Icon(Icons.Default.Close, contentDescription = "Close", tint = Color.Black)
            }
        }

        // SignUp Form
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 32.dp),
            horizontalAlignment = Alignment.Start,
        ) {
            Spacer(modifier = Modifier.height(64.dp))

            Text(
                text = "Let’s\nCreate\nYour\nAccount",
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                fontSize = 36.sp,
                lineHeight = 32.sp
            )

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
                onValueChange = { authViewModel.email.value = it },
                label = "Email Address",
                icon = Icons.Default.Email
            )

            RoundedInputField(
                value = password,
                onValueChange = { authViewModel.password.value = it },
                label = "Password",
                icon = Icons.Default.Lock,
                isPassword = true
            )

            RoundedInputField(
                value = confirmPassword,
                onValueChange = { authViewModel.confirmPassword.value = it },
                label = "Retype Password",
                icon = Icons.Default.Lock,
                isPassword = true
            )

            Spacer(modifier = Modifier.height(8.dp)) // Space before the Terms checkbox

            // Checkbox for Terms and Conditions
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(checked = agreeToTerms, onCheckedChange = { agreeToTerms = it })
                Text(text = "I agree to the ", color = Color.Gray)
                Text(text = "Terms & Privacy", color = Color.Black, style = MaterialTheme.typography.labelMedium)
            }

            Spacer(modifier = Modifier.height(16.dp)) // Space before the Sign-Up button

            // Sign Up Button
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
                            Toast.makeText(context, "Please agree to the Terms & Privacy", Toast.LENGTH_SHORT).show()
                            return@clickable
                        }
                        if (password != confirmPassword) {
                            Toast.makeText(context, "Passwords do not match", Toast.LENGTH_SHORT).show()
                            return@clickable
                        }
                        authViewModel.signUpUser(
                            onSignUpSuccess = {
                                Toast.makeText(context, "Account Created!", Toast.LENGTH_SHORT).show()
                                navController.navigate("login") {
                                    popUpTo("signup") { inclusive = true }
                                }
                            },
                            onSignUpFailure = { error -> Toast.makeText(context, error, Toast.LENGTH_SHORT).show() }
                        )
                    },
                contentAlignment = Alignment.Center
            ) {
                Text("Sign Up", color = Color.White)
            }

            Spacer(modifier = Modifier.height(12.dp)) // Space before Continue with Google button

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

            Spacer(modifier = Modifier.height(16.dp)) // Space before Login Text

            // Already have an account? Login text
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ) {
                Text(
                    text = "Already have an account? ",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
                Text(
                    text = "Tap here to Login",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.clickable {
                        navController.navigate("login")
                    }
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


