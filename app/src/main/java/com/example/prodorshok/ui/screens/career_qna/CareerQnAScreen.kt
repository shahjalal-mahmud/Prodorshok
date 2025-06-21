package com.example.prodorshok.ui.screens.career_qna

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
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
    currentQuestion: QuestionType?,
    pastQnA: List<QnAItem>,
    onAnswer: (String) -> Unit,
    careerSuggestions: String?
) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(bottom = 24.dp)
        ) {
            // Past QnA items
            items(pastQnA) { item ->
                Column {
                    Text("🤖 ${item.question}", style = MaterialTheme.typography.bodyMedium)
                    Text("🧑 ${item.answer}", style = MaterialTheme.typography.bodySmall)
                }
            }

            // Current Question Input
            if (currentQuestion != null && careerSuggestions == null) {
                item {
                    when (currentQuestion) {
                        is QuestionType.YesNo -> YesNoQuestion(currentQuestion.question, onAnswer)
                        is QuestionType.MultipleChoice -> MultipleChoiceQuestion(
                            currentQuestion.question,
                            currentQuestion.options,
                            onAnswer
                        )
                        is QuestionType.Checkbox -> CheckboxQuestion(
                            currentQuestion.question,
                            currentQuestion.options,
                            onAnswer
                        )
                        is QuestionType.ShortAnswer -> ShortAnswerQuestion(
                            currentQuestion.question,
                            onAnswer
                        )
                    }
                }
            }

            // Career Suggestions
            if (careerSuggestions != null) {
                item {
                    CareerSuggestionCard(suggestion = careerSuggestions)
                }
            }

            // Fallback (initial loading / empty state)
            if (currentQuestion == null && careerSuggestions == null) {
                item {
                    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                        CircularProgressIndicator()
                        Text("Loading your first question...")
                    }
                }
            }
        }
    }
}
