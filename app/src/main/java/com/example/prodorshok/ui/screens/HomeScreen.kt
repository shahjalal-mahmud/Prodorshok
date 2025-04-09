package com.example.prodorshok.ui.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.prodorshok.R
import com.google.firebase.auth.FirebaseAuth

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {
    val auth = FirebaseAuth.getInstance()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Prodorshok",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = Color.White
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color(0xFF5B86E5)
                ),
                actions = {
                    IconButton(onClick = {
                        auth.signOut()
                        Toast.makeText(navController.context, "You have logged out.", Toast.LENGTH_SHORT).show()
                        navController.navigate("login") {
                            popUpTo("login") { inclusive = true }
                        }
                    }) {
                        Icon(Icons.AutoMirrored.Filled.ExitToApp, contentDescription = "Logout", tint = Color.White)
                    }
                }
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color(0xFFEDECF2), Color(0xFFC0C8F2))
                    )
                )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Circular app logo with padding, shadow, and improved design
                Box(
                    modifier = Modifier
                        .size(120.dp)
                        .background(Color.White, CircleShape)
                        .padding(8.dp)
                        .shadow(8.dp, CircleShape) // Added shadow for better appearance
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.logo),
                        contentDescription = "App Logo",
                        modifier = Modifier.fillMaxSize()
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    "Welcome to Prodorshok!",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF3C3C3C)
                )

                Spacer(modifier = Modifier.height(32.dp))

                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    HomeButton(
                        text = "Go to Profile",
                        icon = Icons.Default.Person
                    ) {
                        navController.navigate("profile_setup") {
                            popUpTo("profile_setup") { inclusive = true }
                        }
                    }

                    HomeButton(
                        text = "Go to Dashboard",
                        icon = Icons.Default.Dashboard
                    ) {
                        navController.navigate("dashboard") {
                            popUpTo("dashboard") { inclusive = true }
                        }
                    }

                    HomeButton(
                        text = "Go to Profile Page",
                        icon = Icons.Default.Settings
                    ) {
                        navController.navigate("profile") {
                            popUpTo("profile") { inclusive = true }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun HomeButton(text: String, icon: androidx.compose.ui.graphics.vector.ImageVector, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF5B86E5)),
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 8.dp)
    ) {
        Icon(
            icon,
            contentDescription = null,
            modifier = Modifier.size(20.dp),
            tint = Color.White
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(text = text, color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Medium)
    }
}