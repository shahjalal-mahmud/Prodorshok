package com.example.prodorshok.ui.screens.career_qna

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ShortAnswerQuestion(
    question: String,
    onSubmit: (String) -> Unit
) {
    var answer by remember { mutableStateOf("") }

    Column {
        Text(text = question, style = MaterialTheme.typography.bodyLarge)
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(
            value = answer,
            onValueChange = { answer = it },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Type your answer here...") }
        )
        Button(
            onClick = {
                if (answer.isNotBlank()) {
                    onSubmit(answer)
                    answer = ""
                }
            },
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Text("Submit")
        }
    }
}
