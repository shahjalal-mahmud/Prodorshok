package com.example.prodorshok.ui.models

data class Feedback(
    val id: String = "", // Firestore document ID
    val userId: String = "",    // Default empty
    val userName: String = "",  // Default empty
    val title: String = "",     // Default empty
    val rating: Int = 0,        // Default 0
    val message: String = "",   // Default empty
    val timestamp: Long = System.currentTimeMillis(),
)
