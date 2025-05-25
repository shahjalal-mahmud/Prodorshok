package com.example.prodorshok.data.remote.firestore

import android.content.Context
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore

object ProfileRepository {

    fun saveUserProfile(
        context: Context,
        userId: String,
        userProfile: Map<String, String>,
        onSuccess: () -> Unit,
        onFailure: () -> Unit
    ) {
        FirebaseFirestore.getInstance()
            .collection("users")
            .document(userId)
            .set(userProfile)
            .addOnSuccessListener {
                Toast.makeText(context, "Profile Saved!", Toast.LENGTH_SHORT).show()
                onSuccess()
            }
            .addOnFailureListener {
                Toast.makeText(context, "Failed to save profile", Toast.LENGTH_SHORT).show()
                onFailure()
            }
    }
}