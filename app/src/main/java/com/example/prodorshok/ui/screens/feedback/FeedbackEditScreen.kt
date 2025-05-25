package com.example.prodorshok.ui.screens.feedback

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.prodorshok.ui.models.Feedback
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun FeedbackEditScreen(
    feedback: Feedback,
    onSave: (Feedback) -> Unit,
    onDelete: (String) -> Unit,
    onBack: () -> Unit
) {
    var editedFeedback by remember { mutableStateOf(feedback) }
    var showDeleteDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }

    // Feedback Edit Form
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Title input
        TextField(
            value = editedFeedback.title,
            onValueChange = { editedFeedback = editedFeedback.copy(title = it) },
            label = { Text("Feedback Title") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Message input
        TextField(
            value = editedFeedback.message,
            onValueChange = { editedFeedback = editedFeedback.copy(message = it) },
            label = { Text("Feedback Message") },
            modifier = Modifier.fillMaxWidth(),
            maxLines = 4
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Save button
        Button(
            onClick = {
                onSave(editedFeedback)
                onBack() // Navigate back after saving
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Save Feedback")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Delete button
        Button(
            onClick = { showDeleteDialog = true },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
        ) {
            Text("Delete Feedback", color = Color.White)
        }
    }

    // Delete Confirmation Dialog
    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("Confirm Deletion") },
            text = { Text("Are you sure you want to delete this feedback?") },
            confirmButton = {
                Button(
                    onClick = {
                        showDeleteDialog = false
                        onDelete(feedback.userId)
                        // Show Snackbar
                        CoroutineScope(Dispatchers.Main).launch {
                            snackbarHostState.showSnackbar("Feedback Deleted Successfully")
                        }
                    }
                ) {
                    Text("Delete")
                }
            },
            dismissButton = {
                Button(onClick = { showDeleteDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }

    // Snackbar
    SnackbarHost(hostState = snackbarHostState)

    // Delete Confirmation Dialog
    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("Confirm Deletion") },
            text = { Text("Are you sure you want to delete this feedback?") },
            confirmButton = {
                Button(
                    onClick = {
                        showDeleteDialog = false
                        onDelete(feedback.userId)
                        // Show Snackbar
                        CoroutineScope(Dispatchers.Main).launch {
                            snackbarHostState.showSnackbar("Feedback Deleted Successfully")
                        }
                    }
                ) {
                    Text("Delete")
                }
            },
            dismissButton = {
                Button(onClick = { showDeleteDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }

    // Snackbar
    SnackbarHost(hostState = snackbarHostState)
}
// Edit Feedback in Firebase
fun editFeedback(feedback: Feedback) {
    val db = FirebaseFirestore.getInstance()
    db.collection("feedbacks")
        .document(feedback.userId)
        .set(feedback)
        .addOnSuccessListener {
            // Handle success (Show Snackbar or navigate)
        }
        .addOnFailureListener {
            // Handle error
        }
}

// Delete Feedback from Firebase
fun deleteFeedback(feedbackId: String) {
    val db = FirebaseFirestore.getInstance()
    db.collection("feedbacks")
        .document(feedbackId)
        .delete()
        .addOnSuccessListener {
            // Handle success (Show Snackbar or navigate)
        }
        .addOnFailureListener {
            // Handle error
        }
}
