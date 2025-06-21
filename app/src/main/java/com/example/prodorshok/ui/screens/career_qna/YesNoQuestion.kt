package com.example.prodorshok.ui.screens.career_qna

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun YesNoQuestion(
    question: String,
    onAnswer: (String) -> Unit
) {
    Column {
        Text(text = question, style = MaterialTheme.typography.bodyLarge)
        Spacer(Modifier.height(8.dp))
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Button(onClick = { onAnswer("Yes") }, modifier = Modifier.weight(1f)) {
                Text("Yes")
            }
            Button(onClick = { onAnswer("No") }, modifier = Modifier.weight(1f)) {
                Text("No")
            }
        }
    }
}
