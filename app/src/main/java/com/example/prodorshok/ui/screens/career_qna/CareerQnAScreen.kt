package com.example.prodorshok.ui.screens.career_qna

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

data class QnAItem(
    val question: String,
    val answer: String
)

sealed class QuestionType {
    data class YesNo(val question: String) : QuestionType()
    data class MultipleChoice(val question: String, val options: List<String>) : QuestionType()
    data class Checkbox(val question: String, val options: List<String>) : QuestionType()
    data class ShortAnswer(val question: String) : QuestionType()
}

@Composable
fun CareerQnAScreen(
    navController: NavController,
    currentQuestion: QuestionType,
    pastQnA: List<QnAItem>,
    onAnswer: (String) -> Unit,
    careerSuggestions: String?
) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(bottom = 16.dp)
        ) {
            items(pastQnA) { item ->
                Column {
                    Text("🤖 ${item.question}", style = MaterialTheme.typography.bodyMedium)
                    Text("🧑 ${item.answer}", style = MaterialTheme.typography.bodySmall)
                }
            }
        }

        // Render the correct component
        currentQuestion?.let { question ->
            when (question) {
                is QuestionType.YesNo -> YesNoQuestion(question.question, onAnswer)
                is QuestionType.MultipleChoice -> MultipleChoiceQuestion(question.question, question.options, onAnswer)
                is QuestionType.Checkbox -> CheckboxQuestion(question.question, question.options, onAnswer)
                is QuestionType.ShortAnswer -> ShortAnswerQuestion(question.question, onAnswer)
            }
        }

        if (careerSuggestions != null) {
            CareerSuggestionCard(suggestion = careerSuggestions)
        }
    }
}