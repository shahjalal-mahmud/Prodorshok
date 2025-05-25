@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.prodorshok.ui.screens.profile

import android.widget.Toast
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.example.prodorshok.ui.components.common.LoadingScreen
import com.example.prodorshok.ui.components.profile.ProfileContent
import com.example.prodorshok.viewmodel.profile.ProfileViewModel
import com.example.prodorshok.data.model.UserProfile
import com.google.firebase.auth.FirebaseAuth

@Composable
fun ProfilePage(
    navController: NavController,
    viewModel: ProfileViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val context = LocalContext.current
    val userProfile by viewModel.userProfile.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    var isEditing by remember { mutableStateOf(false) }

    // Only initialize when userProfile is ready
    var editableProfile by remember { mutableStateOf<UserProfile?>(null) }

    // 🔥 FIXED: This will now actually trigger profile loading
    LaunchedEffect(Unit) {
        viewModel.fetchUserProfile()
    }

    // Sync editableProfile only when not editing and data is loaded
    LaunchedEffect(userProfile, isLoading) {
        if (!isEditing && !isLoading) {
            editableProfile = userProfile.copy()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Profile") },
                actions = {
                    if (isEditing) {
                        TextButton(onClick = {
                            editableProfile = userProfile.copy()
                            isEditing = false
                        }) {
                            Text("Cancel")
                        }
                    } else {
                        IconButton(onClick = {
                            FirebaseAuth.getInstance().signOut()
                            Toast.makeText(context, "Logged Out", Toast.LENGTH_SHORT).show()
                            navController.navigate("login") {
                                popUpTo("login") { inclusive = true }
                            }
                        }) {
                            Icon(Icons.AutoMirrored.Filled.Logout, contentDescription = "Logout")
                        }
                    }
                }
            )
        }
    ) { paddingValues ->
        if (isLoading || editableProfile == null) {
            LoadingScreen(paddingValues)
        } else {
            if (isEditing) {
                ProfileEditForm(
                    userProfile = editableProfile!!,
                    onProfileChange = { editableProfile = it },
                    onSave = {
                        viewModel.updateProfile(
                            updated = editableProfile!!,
                            onSuccess = {
                                Toast.makeText(context, "Profile Updated!", Toast.LENGTH_SHORT).show()
                                isEditing = false
                            },
                            onFailure = {
                                Toast.makeText(context, "Failed to update", Toast.LENGTH_SHORT).show()
                            }
                        )
                    }
                )
            } else {
                ProfileContent(
                    userProfile = userProfile,
                    isEditing = false,
                    onEditToggle = {
                        editableProfile = userProfile.copy()
                        isEditing = true
                    },
                    onProfileChange = { viewModel.setLocalProfile(it) },
                    onSave = {}, // Not used in view mode
                    onPhotoClick = { uri -> viewModel.setProfilePhoto(uri) },
                    paddingValues = paddingValues
                )
            }
        }
    }
}
