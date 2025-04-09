package com.example.prodorshok.ui.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun ProfilePage(navController: NavController) {
    val user = FirebaseAuth.getInstance().currentUser
    val userId = user?.uid
    val userName = remember { mutableStateOf("") }
    val userEmail = remember { mutableStateOf(user?.email ?: "") }
    val userPhotoUrl = remember { mutableStateOf("") }
    val interests = remember { mutableStateOf("") }
    val strengths = remember { mutableStateOf("") }
    val academicStage = remember { mutableStateOf("") }
    val careerGoal = remember { mutableStateOf("") }
    val isEditing = remember { mutableStateOf(false) }

    // Fetch user profile data from Firestore when the screen is first displayed
    LaunchedEffect(userId) {
        if (userId != null) {
            FirebaseFirestore.getInstance()
                .collection("users")
                .document(userId)
                .get()
                .addOnSuccessListener { document ->
                    if (document.exists()) {
                        userName.value = document.getString("name") ?: ""
                        interests.value = document.getString("interests") ?: ""
                        strengths.value = document.getString("strengths") ?: ""
                        academicStage.value = document.getString("academicStage") ?: ""
                        careerGoal.value = document.getString("careerGoal") ?: ""
                    }
                }

            // Fetch user profile picture from Firebase Authentication
            user?.photoUrl?.let { url ->
                userPhotoUrl.value = url.toString()
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Display Profile Photo using Coil (Accompanist)
        val painter = rememberImagePainter(userPhotoUrl.value)
        Image(
            painter = painter,
            contentDescription = "User Profile Photo",
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
                .padding(8.dp),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Display User Name and Email
        Text("Name: ${userName.value}", style = MaterialTheme.typography.bodyLarge)
        Text("Email: ${userEmail.value}", style = MaterialTheme.typography.bodyMedium)

        Spacer(modifier = Modifier.height(16.dp))

        // Display additional information (Interests, Strengths, etc.)
        Text("Interests: ${interests.value}", style = MaterialTheme.typography.bodyMedium)
        Text("Strengths: ${strengths.value}", style = MaterialTheme.typography.bodyMedium)
        Text("Academic Stage: ${academicStage.value}", style = MaterialTheme.typography.bodyMedium)
        Text("Career Goal: ${careerGoal.value}", style = MaterialTheme.typography.bodyMedium)

        Spacer(modifier = Modifier.height(24.dp))

        // Edit Button
        Button(onClick = { isEditing.value = true }) {
            Text("Edit Profile")
        }

        // If editing, show the form for editing the details
        if (isEditing.value) {
            ProfileEditForm(
                userName = userName,
                interests = interests,
                strengths = strengths,
                academicStage = academicStage,
                careerGoal = careerGoal,
                onSave = {
                    // Save updated profile data to Firestore
                    if (userId != null) {
                        val updatedData = mapOf(
                            "name" to userName.value,
                            "interests" to interests.value,
                            "strengths" to strengths.value,
                            "academicStage" to academicStage.value,
                            "careerGoal" to careerGoal.value
                        )

                        FirebaseFirestore.getInstance()
                            .collection("users")
                            .document(userId)
                            .set(updatedData)
                            .addOnSuccessListener {
                                Toast.makeText(navController.context, "Profile Updated!", Toast.LENGTH_SHORT).show()
                                isEditing.value = false
                            }
                            .addOnFailureListener {
                                Toast.makeText(navController.context, "Failed to update profile", Toast.LENGTH_SHORT).show()
                            }
                    }
                }
            )
        }
    }
}

@Composable
fun ProfileEditForm(
    userName: MutableState<String>,
    interests: MutableState<String>,
    strengths: MutableState<String>,
    academicStage: MutableState<String>,
    careerGoal: MutableState<String>,
    onSave: () -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text("Edit Profile", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = userName.value,
            onValueChange = { userName.value = it },
            label = { Text("Name") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

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

        Button(onClick = { onSave() }) {
            Text("Save Profile")
        }
    }
}
