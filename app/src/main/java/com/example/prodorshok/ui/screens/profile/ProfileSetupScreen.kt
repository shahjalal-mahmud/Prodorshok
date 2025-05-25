package com.example.prodorshok.ui.screens.profile

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.prodorshok.data.model.UserProfile
import com.example.prodorshok.data.remote.firestore.ProfileRepository
import com.google.firebase.auth.FirebaseAuth

@Composable
fun ProfileSetupScreen(navController: NavController) {
    val context = LocalContext.current
    val userId = FirebaseAuth.getInstance().currentUser?.uid

    var profile by remember {
        mutableStateOf(UserProfile())
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Set up your profile", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(16.dp))

        ProfileForm(
            userProfile = profile,
            onChange = { profile = it },
            onSave = {
                if (userId != null) {
                    val userProfileMap = mapOf(
                        "interests" to profile.interests,
                        "strengths" to profile.strengths,
                        "academicStage" to profile.academicStage,
                        "careerGoal" to profile.careerGoal,
                        "location" to profile.location
                    )
                    ProfileRepository.saveUserProfile(
                        context = context,
                        userId = userId,
                        userProfile = userProfileMap,
                        onSuccess = { navController.navigate("dashboard") },
                        onFailure = { /* Handle error */ }
                    )
                } else {
                    Toast.makeText(context, "User is not authenticated", Toast.LENGTH_SHORT).show()
                }
            }
        )
    }
}
