package com.example.prodorshok.ui.screens.career_qna

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.json.JSONArray

@Composable
fun CareerSuggestionCard(suggestion: String) {
    // Try parsing JSON array fallback
    val suggestions = parseSuggestions(suggestion)

    if (suggestions.isEmpty()) return  // Avoid empty card

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
    ) {
        Text(
            text = "🎯 Suggested Careers",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold)
        )

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                suggestions.forEach { career ->
                    Text(
                        text = "• $career",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                }
            }
        }
    }
}

private fun parseSuggestions(input: String): List<String> {
    // 1. Try JSON array
    return try {
        val array = JSONArray(input.trim())
        List(array.length()) { array.getString(it).trim() }
    } catch (e: Exception) {
        // 2. Fallback to bullet-point format (- Career Name)
        input.lines()
            .map { it.trim() }
            .filter { it.startsWith("- ") }
            .map { it.removePrefix("- ").trim() }
    }
}
