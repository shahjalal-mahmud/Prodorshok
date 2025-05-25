package com.example.prodorshok.ui.repository

import android.util.Log
import com.example.prodorshok.ui.models.Feedback
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.auth.FirebaseAuth

object FeedbackRepository {
    private val db = FirebaseFirestore.getInstance()
    private val feedbackRef = db.collection("feedback")

    fun addFeedback(rating: Int, message: String, onComplete: () -> Unit) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return
        val userName = FirebaseAuth.getInstance().currentUser?.displayName ?: "Anonymous"

        feedbackRef.get().addOnSuccessListener { snapshot ->
            val nextSerial = snapshot.size() + 1
            val feedback = Feedback(
                userId = userId,
                userName = userName,
                rating = rating,
                message = message,
                timestamp = System.currentTimeMillis()
            )
            feedbackRef.add(feedback)
                .addOnSuccessListener {
                    Log.d("Feedback", "Feedback added!")
                    onComplete()
                }
                .addOnFailureListener { e -> Log.e("Feedback", "Failed: ", e) }
        }
    }

    fun loadFeedback(onComplete: (List<Feedback>) -> Unit) {
        feedbackRef.orderBy("timestamp").get()
            .addOnSuccessListener { result ->
                val feedbackList = result.mapNotNull { it.toObject(Feedback::class.java) }
                onComplete(feedbackList)
            }
            .addOnFailureListener { e -> Log.e("Feedback", "Error loading: ", e) }
    }
}
