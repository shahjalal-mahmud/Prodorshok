package com.example.prodorshok.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

object UserProfileRepository {
    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    fun getCurrentUserId(): String? = auth.currentUser?.uid

    fun fetchUserProfile(
        onSuccess: (Map<String, String?>) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        val userId = getCurrentUserId() ?: return

        db.collection("users").document(userId).get()
            .addOnSuccessListener { doc ->
                if (doc.exists()) {
                    onSuccess(
                        mapOf(
                            "name" to doc.getString("name"),
                            "interests" to doc.getString("interests"),
                            "strengths" to doc.getString("strengths"),
                            "academicStage" to doc.getString("academicStage"),
                            "careerGoal" to doc.getString("careerGoal"),
                            "location" to doc.getString("location"),
                            "phoneNumber" to doc.getString("phoneNumber"),
                            "districtZila" to doc.getString("districtZila")
                        )
                    )
                }
            }
            .addOnFailureListener(onFailure)
    }

    fun updateUserProfile(
        data: Map<String, String>,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        val userId = getCurrentUserId() ?: return

        db.collection("users").document(userId)
            .set(data)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { onFailure(it) }
    }
}
