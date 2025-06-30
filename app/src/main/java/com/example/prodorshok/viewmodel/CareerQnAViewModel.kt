package com.example.prodorshok.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.prodorshok.data.model.UserProfile
import com.example.prodorshok.data.network.Message
import com.example.prodorshok.data.network.NetworkModule
import com.example.prodorshok.data.network.OpenRouterRequest
import com.example.prodorshok.domain.careerqna.AIResponseParser
import com.example.prodorshok.domain.careerqna.CareerQnAUseCase
import com.example.prodorshok.domain.careerqna.MessageHistoryManager
import com.example.prodorshok.ui.screens.career_qna.QnAItem
import com.example.prodorshok.ui.screens.career_qna.QuestionType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException

class CareerQnAViewModel(app: Application) : AndroidViewModel(app) {

    private val service = NetworkModule.provideOpenRouterService(app.applicationContext)

    private val _questionHistory = MutableStateFlow<List<QnAItem>>(emptyList())
    val questionHistory: StateFlow<List<QnAItem>> = _questionHistory

    private val _currentQuestion = MutableStateFlow<QuestionType?>(null)
    val currentQuestion: StateFlow<QuestionType?> = _currentQuestion

    private val _careerSuggestions = MutableStateFlow<String?>(null)
    val careerSuggestions: StateFlow<String?> = _careerSuggestions

    private var questionCount = 0
    private val maxQuestions = 5

    private var waitingForSuggestions = false

    private val historyManager = MessageHistoryManager()

    fun startConversationWithProfile(profile: UserProfile) {
        val prompt = CareerQnAUseCase.buildInitialPrompt(profile)

        // Reset state
        historyManager.reset()
        questionCount = 0
        _careerSuggestions.value = null
        _questionHistory.value = emptyList()
        waitingForSuggestions = false

        historyManager.add(Message(role = "system", content = prompt))
        fetchNextQuestion()
    }

    fun submitAnswer(answer: String) {
        val lastQuestion = _currentQuestion.value ?: return

        val questionText = when (lastQuestion) {
            is QuestionType.YesNo -> lastQuestion.question
            is QuestionType.MultipleChoice -> lastQuestion.question
            is QuestionType.Checkbox -> lastQuestion.question
            is QuestionType.ShortAnswer -> lastQuestion.question
        }

        _questionHistory.value += QnAItem(question = questionText, answer = answer)
        historyManager.add(Message(role = "user", content = answer))

        questionCount++

        if (questionCount >= maxQuestions) {
            requestFinalSuggestions()
        } else {
            fetchNextQuestion()
        }
    }

    private fun fetchNextQuestion() {
        if (waitingForSuggestions) return

        viewModelScope.launch {
            try {
                val trimmedMessages = historyManager.getLast(10)
                val request = OpenRouterRequest(
                    model = "deepseek/deepseek-chat-v3-0324",
                    messages = trimmedMessages,
                    max_tokens = 1000
                )

                val response = service.getChatCompletion(request)
                val botMessage = response.choices.firstOrNull()?.message?.content ?: run {
                    _careerSuggestions.value = "Sorry, no response received. Please try again."
                    return@launch
                }

                historyManager.add(Message(role = "assistant", content = botMessage))

                when (val result = AIResponseParser.parseResponse(
                    response = botMessage,
                    isSuggestionPhase = waitingForSuggestions || questionCount >= maxQuestions
                )) {
                    is AIResponseParser.ParseResult.Question -> {
                        _currentQuestion.value = result.questionType
                    }
                    is AIResponseParser.ParseResult.Suggestions -> {
                        showSuggestions(result.suggestions.joinToString("\n- ", "- "))
                    }
                    AIResponseParser.ParseResult.Invalid -> {
                        _careerSuggestions.value = "Sorry, couldn't understand the response format."
                    }
                }

            } catch (e: Exception) {
                Log.e("CareerQnAViewModel", "Error fetching AI response", e)
                _currentQuestion.value = null
                _careerSuggestions.value = when {
                    e is HttpException && e.code() == 402 -> {
                        "⚠️ You've reached your free usage limit on OpenRouter. Try again later or reduce token size."
                    }
                    else -> {
                        "⚠️ Something went wrong. Please check your internet connection or try again."
                    }
                }
            }
        }
    }

    private fun showSuggestions(suggestions: String) {
        waitingForSuggestions = false
        _currentQuestion.value = null
        _careerSuggestions.value = suggestions
    }

    private fun requestFinalSuggestions() {
        if (waitingForSuggestions) return

        waitingForSuggestions = true
        _currentQuestion.value = null // Clear any current question

        // Add the final prompt
        historyManager.add(
            Message(
                role = "system",
                content = CareerQnAUseCase.buildFinalPrompt()
            )
        )

        viewModelScope.launch {
            try {
                val trimmedMessages = historyManager.getLast(10)
                val request = OpenRouterRequest(
                    model = "deepseek/deepseek-chat-v3-0324",
                    messages = trimmedMessages,
                    max_tokens = 1000
                )

                val response = service.getChatCompletion(request)
                val suggestions = response.choices.firstOrNull()?.message?.content ?: run {
                    _careerSuggestions.value = "Sorry, couldn't generate suggestions. Please try again."
                    return@launch
                }

                // Directly show suggestions without parsing
                showSuggestions(suggestions)
            } catch (e: Exception) {
                Log.e("CareerQnAViewModel", "Error fetching suggestions", e)
                _careerSuggestions.value = when {
                    e is HttpException && e.code() == 402 -> {
                        "⚠️ You've reached your free usage limit on OpenRouter. Try again later or reduce token size."
                    }
                    else -> {
                        "⚠️ Something went wrong. Please check your internet connection or try again."
                    }
                }
            } finally {
                waitingForSuggestions = false
            }
        }
    }
}
