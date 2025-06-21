package com.example.prodorshok.ui.screens.career_qna

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun MultipleChoiceQuestion(
    question: String,
    options: List<String>,
    onAnswer: (String) -> Unit
) {
    Column {
        Text(text = question, style = MaterialTheme.typography.bodyLarge)
        Spacer(Modifier.height(8.dp))
        options.forEach { option ->
            Button(
                onClick = { onAnswer(option) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            ) {
                Text(option)
            }
        }
    }
}
