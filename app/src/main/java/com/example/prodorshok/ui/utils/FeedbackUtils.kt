package com.example.prodorshok.ui.utils

import android.util.Log
import com.example.prodorshok.ui.models.Feedback
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.auth.FirebaseAuth

// Function to add feedback to Firestore
fun addFeedback(rating: Int, message: String) {
    val user = FirebaseAuth.getInstance().currentUser
    val userId = user?.uid
    val userName = user?.displayName ?: user?.email ?: "Anonymous"

    if (userId != null) {
        val feedback = Feedback(
            userId = userId,
            userName = userName,
            rating = rating,
            message = message,
            timestamp = System.currentTimeMillis()
        )

        val feedbackRef = FirebaseFirestore.getInstance().collection("feedback")
        feedbackRef.add(feedback)
            .addOnSuccessListener {
                Log.d("Feedback", "Feedback added successfully!")
            }
            .addOnFailureListener { e ->
                Log.e("Feedback", "Error adding feedback: ", e)
            }
    }
}


// Function to load feedback from Firestore
fun loadFeedback(onComplete: (List<Feedback>) -> Unit) {
    val feedbackRef = FirebaseFirestore.getInstance().collection("feedback")
    feedbackRef.orderBy("timestamp")
        .get()
        .addOnSuccessListener { result ->
            val feedbackList = result.mapNotNull { document ->
                try {
                    document.toObject(Feedback::class.java)
                } catch (e: Exception) {
                    null  // Safely ignore bad documents
                }
            }
            onComplete(feedbackList)
        }
        .addOnFailureListener { e ->
            Log.e("Feedback", "Error getting feedback: ", e)
        }
}
