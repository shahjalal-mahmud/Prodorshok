package com.example.prodorshok.ui.screens.feedback

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.prodorshok.ui.models.Feedback
import com.example.prodorshok.ui.models.FeedbackStats
import com.example.prodorshok.ui.utils.loadFeedback
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeedbackListScreen(navController: NavController) {
    val feedbackList = remember { mutableStateListOf<Feedback>() }
    var isLoading by remember { mutableStateOf(true) }
    val currentUserId = "current_user_id"
    var editingFeedback by remember { mutableStateOf<Feedback?>(null) }

    val stats = remember(feedbackList) {
        val count = feedbackList.size
        val avg = if (count > 0) feedbackList.map { it.rating }.average() else 0.0
        FeedbackStats(count, avg)
    }

    fun loadData() {
        isLoading = true
        loadFeedback { feedback ->
            feedbackList.clear()
            feedbackList.addAll(feedback.reversed())
            isLoading = false
        }
    }

    LaunchedEffect(Unit) { loadData() }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("User Feedbacks", style = MaterialTheme.typography.titleLarge) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Box(modifier = Modifier.padding(padding).fillMaxSize()) {
            when {
                isLoading -> CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))

                editingFeedback != null -> FeedbackEditScreen(
                    feedback = editingFeedback!!,
                    onSave = {
                        editFeedback(it)
                        editingFeedback = null
                        loadData()
                    },
                    onDelete = {
                        deleteFeedback(it)
                        editingFeedback = null
                        loadData()
                    },
                    onBack = { editingFeedback = null }
                )

                else -> Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
                    Text(
                        text = "Total Feedbacks: ${stats.totalFeedback}",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = "Average Rating: ${"%.1f".format(stats.averageRating)} ★",
                        style = MaterialTheme.typography.titleMedium,
                        color = Color(0xFFFFC107)
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        itemsIndexed(feedbackList) { index, feedback ->
                            FeedbackCard(
                                feedback = feedback,
                                serialNumber = index + 1,
                                currentUserId = currentUserId,
                                onEdit = { editingFeedback = it },
                                onDelete = {
                                    deleteFeedback(it.userId)
                                    loadData()
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

// Format timestamp
fun Long.toHumanReadableDate(): String {
    return try {
        val date = Date(this)
        val format = SimpleDateFormat("d MMMM yyyy, h:mm a", Locale.getDefault())
        format.format(date)
    } catch (e: Exception) {
        this.toString()
    }
}
