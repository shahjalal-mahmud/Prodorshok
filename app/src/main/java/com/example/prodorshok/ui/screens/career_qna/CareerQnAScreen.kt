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
import androidx.compose.foundation.lazy.itemsIndexed
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
    careerSuggestions: String?,
    isLoadingNextQuestion: Boolean,
    isFirstQuestionLoading: Boolean
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 64.dp, start = 24.dp, end = 24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(bottom = 24.dp)
    ) {
        // 📝 QnA history
        itemsIndexed(pastQnA) { index, item ->
            Column {
                Text(
                    text = "🤖 Q${index + 1}: ${item.question}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "🧑 ${item.answer}",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }

        // ⏳ Loading first question
        if (isFirstQuestionLoading && pastQnA.isEmpty()) {
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

        // ⏳ Loading next questions (Q2–Q5)
        if (isLoadingNextQuestion && pastQnA.isNotEmpty() && pastQnA.size < 5 && careerSuggestions == null) {
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator()
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Loading your next question...")
                }
            }
        }

        // ⏳ Loading final career suggestions
        if (pastQnA.size >= 5 && careerSuggestions == null && isLoadingNextQuestion) {
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator()
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Loading your career options...")
                }
            }
        }

        // ❓ Display current question
        if (currentQuestion != null && !isLoadingNextQuestion && careerSuggestions == null) {
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

        // 💡 Display career suggestions
        if (careerSuggestions != null) {
            item {
                when (val result =
                    AIResponseParser.parseResponse(careerSuggestions, isSuggestionPhase = true)) {
                    is AIResponseParser.ParseResult.Suggestions -> {
                        CareerSuggestionList(suggestions = result.suggestions) {
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
    }
}
