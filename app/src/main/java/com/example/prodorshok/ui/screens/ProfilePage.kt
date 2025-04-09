@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.prodorshok.ui.screens

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun ProfilePage(navController: NavController) {
    val context = LocalContext.current
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
    val isLoading = remember { mutableStateOf(true) }

    // Launcher for image picker
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            userPhotoUrl.value = it.toString()
            Toast.makeText(context, "Profile picture updated (not uploaded to Firebase yet)", Toast.LENGTH_SHORT).show()
        }
    }

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
                    isLoading.value = false
                }
                .addOnFailureListener {
                    Toast.makeText(context, "Failed to load profile", Toast.LENGTH_SHORT).show()
                    isLoading.value = false
                }

            user.photoUrl?.let { url ->
                userPhotoUrl.value = url.toString()
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Profile") },
                actions = {
                    IconButton(onClick = {
                        FirebaseAuth.getInstance().signOut()
                        Toast.makeText(context, "Logged Out", Toast.LENGTH_SHORT).show()
                        // Navigate to the login screen and clear the back stack
                        navController.navigate("login") {
                            // Pop up to the root (login screen) to clear the back stack
                            popUpTo("login") { inclusive = true }
                        }
                    }) {
                        Icon(Icons.AutoMirrored.Filled.Logout, contentDescription = "Logout")
                    }
                }
            )
        }
    ) { paddingValues ->
        if (isLoading.value) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp)
                    .padding(paddingValues),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Profile Photo with Click
                Box(
                    contentAlignment = Alignment.Center
                ) {
                    AsyncImage(
                        model = ImageRequest.Builder(context)
                            .data(userPhotoUrl.value)
                            .crossfade(true)
                            .build(),
                        contentDescription = "Profile Picture",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(110.dp)
                            .clip(CircleShape)
                            .clickable { launcher.launch("image/*") }
                    )
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit Photo",
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .size(24.dp)
                            .background(Color.White, CircleShape)
                            .padding(2.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))
                Text(userName.value, style = MaterialTheme.typography.headlineSmall)
                Text(userEmail.value, style = MaterialTheme.typography.bodyMedium)

                Spacer(modifier = Modifier.height(24.dp))

                if (!isEditing.value) {
                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        ProfileField(icon = Icons.Default.Email, label = "Email", value = userEmail.value)
                        ProfileField(icon = Icons.Default.Star, label = "Interests", value = interests.value)
                        ProfileField(icon = Icons.Default.Bolt, label = "Strengths", value = strengths.value)
                        ProfileField(icon = Icons.Default.School, label = "Academic Stage", value = academicStage.value)
                        ProfileField(icon = Icons.Default.Flag, label = "Career Goal", value = careerGoal.value)

                        Spacer(modifier = Modifier.height(20.dp))

                        Button(onClick = { isEditing.value = true }) {
                            Icon(Icons.Default.Edit, contentDescription = null)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Edit Profile")
                        }
                    }
                } else {
                    ProfileEditForm(
                        userName = userName,
                        interests = interests,
                        strengths = strengths,
                        academicStage = academicStage,
                        careerGoal = careerGoal,
                        onSave = {
                            if (userId != null) {
                                val data = mapOf(
                                    "name" to userName.value,
                                    "interests" to interests.value,
                                    "strengths" to strengths.value,
                                    "academicStage" to academicStage.value,
                                    "careerGoal" to careerGoal.value
                                )
                                FirebaseFirestore.getInstance()
                                    .collection("users")
                                    .document(userId)
                                    .set(data)
                                    .addOnSuccessListener {
                                        Toast.makeText(context, "Profile Updated!", Toast.LENGTH_SHORT).show()
                                        isEditing.value = false
                                    }
                                    .addOnFailureListener {
                                        Toast.makeText(context, "Failed to update", Toast.LENGTH_SHORT).show()
                                    }
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun ProfileField(icon: ImageVector, label: String, value: String) {
    Surface(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        color = Color(0xFFE3F2FD), // Similar to welcome screen blue
        tonalElevation = 2.dp
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(text = label, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.primary)
                Text(text = value.ifEmpty { "Not provided" }, style = MaterialTheme.typography.bodyLarge)
            }
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
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Text("Edit Profile", style = MaterialTheme.typography.titleMedium)

        OutlinedTextField(
            value = userName.value,
            onValueChange = { userName.value = it },
            label = { Text("Name") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = interests.value,
            onValueChange = { interests.value = it },
            label = { Text("Interests") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = strengths.value,
            onValueChange = { strengths.value = it },
            label = { Text("Strengths") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = academicStage.value,
            onValueChange = { academicStage.value = it },
            label = { Text("Academic Stage") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = careerGoal.value,
            onValueChange = { careerGoal.value = it },
            label = { Text("Career Goal") },
            modifier = Modifier.fillMaxWidth()
        )

        Button(onClick = onSave, modifier = Modifier.align(Alignment.End)) {
            Text("Save")
        }
    }
}
