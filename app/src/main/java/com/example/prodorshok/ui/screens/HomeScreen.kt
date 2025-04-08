package com.example.prodorshok.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth

@Composable
fun HomeScreen(navController: NavController) {
    // FirebaseAuth instance
    val auth = FirebaseAuth.getInstance()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Welcome to Prodorshok!", style = MaterialTheme.typography.headlineSmall)

        Spacer(modifier = Modifier.height(24.dp))

        // Log out button
        Button(
            onClick = {
                // Sign out the user
                auth.signOut()

                // Show a confirmation message
                Toast.makeText(navController.context, "You have logged out.", Toast.LENGTH_SHORT).show()

                // Navigate back to the login screen
                navController.navigate("login") {
                    // Optionally pop the back stack to prevent navigating back to the home screen
                    popUpTo("login") { inclusive = true }
                }
            }
        ) {
            Text("Log Out")
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Go to profile button (you can implement profile navigation later)
        Button(onClick = {
            // Handle Profile or Other Navigation
            navController.navigate("profile_setup"){
                popUpTo("profile_setup") { inclusive = true }
            }
        }) {
            Text("Go to Profile")
        }
    }
}
