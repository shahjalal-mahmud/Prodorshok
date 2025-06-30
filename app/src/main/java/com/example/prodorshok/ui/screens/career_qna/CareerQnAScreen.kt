package com.example.prodorshok.ui.screens.career_qna

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import com.example.prodorshok.domain.careerqna.AIResponseParser

@Composable
fun CareerQnAScreen(
    navController: NavController,
    currentQuestion: QuestionType?,
    pastQnA: List<QnAItem>,
    onAnswer: (String) -> Unit,
    careerSuggestions: String?
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(bottom = 24.dp)
        ) {

            // 🕘 History of QnA
            items(pastQnA) { item ->
                Column {
                    Text("🤖 ${item.question}", style = MaterialTheme.typography.bodyMedium)
                    Text("🧑 ${item.answer}", style = MaterialTheme.typography.bodySmall)
                }
            }

            // ❓ Render current question
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

            // 💡 Suggestions UI
            if (careerSuggestions != null) {
                item {
                    when (val result = AIResponseParser.parseResponse(careerSuggestions, isSuggestionPhase = true)) {
                        is AIResponseParser.ParseResult.Suggestions -> {
                            CareerSuggestionList(suggestions = result.suggestions) {
                                // TODO: Define what happens when a career card is clicked
                                println("Clicked: $it")
                            }
                        }
                        else -> {
                            Text(
                                text = "⚠️ Could not parse career suggestions.",
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier.padding(top = 16.dp)
                            )
                        }
                    }
                }
            }

            // ⏳ Initial loading
            if (currentQuestion == null && careerSuggestions == null) {
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 48.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CircularProgressIndicator()
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("Loading your first question...")
                    }
                }
            }
        }
    }
}
