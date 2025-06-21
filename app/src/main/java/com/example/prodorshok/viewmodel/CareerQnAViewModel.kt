package com.example.prodorshok.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.prodorshok.data.model.UserProfile
import com.example.prodorshok.data.network.Message
import com.example.prodorshok.data.network.NetworkModule
import com.example.prodorshok.data.network.OpenRouterRequest
import com.example.prodorshok.ui.screens.career_qna.QnAItem
import com.example.prodorshok.ui.screens.career_qna.QuestionType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.json.JSONObject

class CareerQnAViewModel(app: Application) : AndroidViewModel(app) {

    private val service = NetworkModule.provideOpenRouterService(app.applicationContext)

    private val _questionHistory = MutableStateFlow<List<QnAItem>>(emptyList())
    val questionHistory: StateFlow<List<QnAItem>> = _questionHistory

    private val _currentQuestion = MutableStateFlow<QuestionType?>(null)
    val currentQuestion: StateFlow<QuestionType?> = _currentQuestion

    private val _careerSuggestions = MutableStateFlow<String?>(null)
    val careerSuggestions: StateFlow<String?> = _careerSuggestions

    private val conversation = mutableListOf<Message>()

    private var questionCount = 0
    private val minQuestions = 3  // Minimum questions before allowing suggestions
    private val maxQuestions = 5  // Maximum questions before forcing suggestions

    fun startConversationWithProfile(profile: UserProfile) {
        val prompt = """
    You are a career counselor AI. A student has shared the following profile:
    
    - Name: ${profile.name}
    - Academic Stage: ${profile.academicStage}
    - Interests: ${profile.interests}
    - Strengths: ${profile.strengths}
    - Career Goal: ${profile.careerGoal}
    - Location: ${profile.location}
    
    Ask exactly 5 short, interactive questions (one at a time) to better understand their ideal career path.
    Follow these rules strictly:
    
    1. Format every question as pure JSON (no markdown or ``` wrapping)
    2. Example format:
        {
          "question": "Your question here",
          "type": "question_type",
          "options": ["Option1", "Option2"] // if applicable
        }
    3. After receiving 5 answers, provide detailed career suggestions in plain text (no JSON)
    4. Never ask more than one question at a time
    5. Question types can be: yes_no, multiple_choice, checkbox, or short_answer
    
    First question:
""".trimIndent()

        // Reset conversation
        conversation.clear()
        questionCount = 0
        _careerSuggestions.value = null
        _questionHistory.value = emptyList()

        conversation.add(Message(role = "system", content = prompt))


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

        conversation.add(Message(role = "user", content = answer))

        fetchNextQuestion()
    }

    private fun fetchNextQuestion() {
        viewModelScope.launch {
            try {
                val request = OpenRouterRequest(
                    model = "deepseek/deepseek-chat-v3-0324",
                    messages = conversation
                )

                val response = service.getChatCompletion(request)
                val botMessage = response.choices.firstOrNull()?.message?.content ?: run {
                    Log.e("CareerQnA", "Empty response from API")
                    requestFinalSuggestions() // Fallback if empty response
                    return@launch
                }

                conversation.add(Message(role = "assistant", content = botMessage))

                val parsedQuestion = parseQuestionFromAI(botMessage)

                when {
                    // If we've reached max questions or can't parse a question, get suggestions
                    questionCount >= maxQuestions || parsedQuestion == null -> {
                        requestFinalSuggestions()
                    }
                    // Otherwise show next question
                    else -> {
                        _currentQuestion.value = parsedQuestion
                        questionCount++
                    }
                }
            } catch (e: Exception) {
                Log.e("CareerQnAViewModel", "Error fetching AI response", e)
                _currentQuestion.value = null
                _careerSuggestions.value = "Sorry, we encountered an error. Please try again later."
            }
        }
    }

    private fun showSuggestions(message: String) {
        _careerSuggestions.value = message
        _currentQuestion.value = null
    }

    private fun requestFinalSuggestions() {
        conversation.add(Message(
            role = "system",
            content = "Based on the previous answers, provide final career suggestions."
        ))
        fetchNextQuestion()  // This will now get suggestions
    }

    private fun parseQuestionFromAI(jsonString: String): QuestionType? {
        return try {
            val trimmed = jsonString.trim()
            val cleanJson = trimmed
                .removePrefix("```json")
                .removePrefix("```")
                .removeSuffix("```")
                .trim()

            val json = JSONObject(cleanJson)
            val question = json.getString("question")
            val type = json.getString("type").lowercase()
            val options = if (json.has("options")) {
                val array = json.getJSONArray("options")
                List(array.length()) { array.getString(it) }
            } else emptyList()

            when (type) {
                "yes_no", "yesno" -> QuestionType.YesNo(question)
                "multiple_choice", "multiple" -> QuestionType.MultipleChoice(question, options)
                "checkbox", "multiple_select" -> QuestionType.Checkbox(question, options)
                "short_answer", "short" -> QuestionType.ShortAnswer(question)
                else -> null
            }
        } catch (e: Exception) {
            Log.e("ParseAIQuestion", "Failed to parse: $jsonString", e)
            null
        }
    }
}
