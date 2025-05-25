package com.example.prodorshok.ui.components.auth

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun AuthInputFields(
    email: String,
    password: String,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit
) {
    var passwordVisible by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Email Input Field
        OutlinedTextField(
            value = email,
            onValueChange = onEmailChange,
            label = { Text("Email or Phone") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.Email,
                    contentDescription = "Email Icon",
                    modifier = Modifier.size(24.dp),
                    tint = Color(0xFF9B2FDA)
                )
            },
            modifier = Modifier.fillMaxWidth(0.9f),
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.DarkGray,
                focusedBorderColor = Color(0xFF9B2FDA),
                unfocusedBorderColor = Color(0xFF9B2FDA),
                cursorColor = Color(0xFF9B2FDA),
                focusedLabelColor = Color(0xFF9B2FDA),
                unfocusedLabelColor = Color.Gray
            )
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Password Input Field
        OutlinedTextField(
            value = password,
            onValueChange = onPasswordChange,
            label = { Text("Password") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.Lock,
                    contentDescription = "Password Icon",
                    modifier = Modifier.size(24.dp),
                    tint = Color(0xFF9B2FDA)
                )
            },
            trailingIcon = {
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(
                        imageVector = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                        contentDescription = if (passwordVisible) "Hide Password" else "Show Password",
                        tint = Color(0xFF9B2FDA)
                    )
                }
            },
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(0.9f),
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.DarkGray,
                focusedBorderColor = Color(0xFF9B2FDA),
                unfocusedBorderColor = Color(0xFF9B2FDA),
                cursorColor = Color(0xFF9B2FDA),
                focusedLabelColor = Color(0xFF9B2FDA),
                unfocusedLabelColor = Color.Gray
            )
        )
    }
}
