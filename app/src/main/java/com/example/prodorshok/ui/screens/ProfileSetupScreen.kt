package com.example.prodorshok.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun ProfileSetupScreen(navController: NavController) {
    val interests = remember { mutableStateOf("") }
    val strengths = remember { mutableStateOf("") }
    val academicStage = remember { mutableStateOf("") }
    val careerGoal = remember { mutableStateOf("") }

    // Get the current user id from Firebase Authentication
    val userId = FirebaseAuth.getInstance().currentUser?.uid

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Set up your profile", style = MaterialTheme.typography.headlineSmall)

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = interests.value,
            onValueChange = { interests.value = it },
            label = { Text("Interests") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = strengths.value,
            onValueChange = { strengths.value = it },
            label = { Text("Strengths") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = academicStage.value,
            onValueChange = { academicStage.value = it },
            label = { Text("Academic Stage") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = careerGoal.value,
            onValueChange = { careerGoal.value = it },
            label = { Text("Career Goal") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(onClick = {
            if (userId != null) {
                // Save the data to Firestore
                val userProfile = mapOf(
                    "interests" to interests.value,
                    "strengths" to strengths.value,
                    "academicStage" to academicStage.value,
                    "careerGoal" to careerGoal.value
                )

                FirebaseFirestore.getInstance()
                    .collection("users")
                    .document(userId) // Using actual userId here
                    .set(userProfile)
                    .addOnSuccessListener {
                        Toast.makeText(navController.context, "Profile Saved!", Toast.LENGTH_SHORT).show()
                        navController.navigate("home")
                    }
                    .addOnFailureListener {
                        Toast.makeText(navController.context, "Failed to save profile", Toast.LENGTH_SHORT).show()
                    }
            } else {
                Toast.makeText(navController.context, "User is not authenticated", Toast.LENGTH_SHORT).show()
            }
        }) {
            Text("Save Profile")
        }
    }
}
