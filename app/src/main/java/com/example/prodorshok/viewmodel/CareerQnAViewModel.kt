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
    private val maxQuestions = 5  // Maximum questions before forcing suggestions

    private var waitingForSuggestions = false


    fun startConversationWithProfile(profile: UserProfile) {
        val prompt = """
        You are an AI career counselor. A student has shared the following profile:
        
        - Name: ${profile.name}
        - Academic Stage: ${profile.academicStage}
        - Interests: ${profile.interests}
        - Strengths: ${profile.strengths}
        - Career Goal: ${profile.careerGoal}
        - Location: ${profile.location}
        
        🔴 ABSOLUTE REQUIREMENTS (DO NOT VIOLATE THESE RULES):
        
        1. QUESTION PHASE (FIRST 5 MESSAGES):
           - You MUST ask EXACTLY 5 questions total
           - Each question MUST be in strict JSON format:
             {
               "question": "Your question here",
               "type": "question_type",
               "options": ["Option1", "Option2"] // if applicable
             }
           - Allowed types: yes_no, multiple_choice, checkbox, short_answer
           - NO additional text, explanations, or markdown formatting
        
        2. SUGGESTION PHASE (AFTER 5 ANSWERS):
           - You MUST return ONLY career suggestions
           - Format EXCLUSIVELY as either:
             • Plain list with dashes:
               - Career 1
               - Career 2
               - Career 3
             OR
             • JSON array:
               ["Career 1", "Career 2", "Career 3"]
           - NO other text, headers, or explanations
        
        🚫 STRICT PROHIBITIONS:
        - Never ask more than 5 questions
        - Never mix questions and suggestions
        - Never deviate from the specified formats
        - Never include markdown (```) or code blocks
        
        📌 FAILURE CONDITIONS:
        If you receive a system message after 5 questions, it means you failed to follow these rules.
        
        EXAMPLE QUESTION:
        {
          "question": "Do you prefer working indoors or outdoors?",
          "type": "multiple_choice",
          "options": ["Indoors", "Outdoors", "No preference"]
        }
        
        EXAMPLE SUGGESTIONS:
        - Software Developer
        - Data Analyst
        - IT Consultant
        
        Now begin by asking the FIRST question ONLY.
    """.trimIndent()

        // Reset conversation
        conversation.clear()
        questionCount = 0
        _careerSuggestions.value = null
        _questionHistory.value = emptyList()
        waitingForSuggestions = false

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

        questionCount++

        // 🟡 Call suggestion immediately after 5th answer
        if (questionCount >= maxQuestions) {
            requestFinalSuggestions()
        } else {
            fetchNextQuestion()
        }
    }

    private fun fetchNextQuestion() {
        // Prevent multiple concurrent requests
        if (waitingForSuggestions) return

        viewModelScope.launch {
            try {
                val trimmedMessages = conversation.takeLast(10) // Limit to last 10 messages
                val request = OpenRouterRequest(
                    model = "deepseek/deepseek-chat-v3-0324",
                    messages = trimmedMessages,
                    max_tokens = 1000 // Set a safe max_tokens limit
                )

                val response = service.getChatCompletion(request)
                val botMessage = response.choices.firstOrNull()?.message?.content ?: run {
                    Log.e("CareerQnA", "Empty response from API")
                    _careerSuggestions.value = "Sorry, no response received. Please try again."
                    return@launch
                }

                conversation.add(Message(role = "assistant", content = botMessage))

                // Try to parse the response
                val parsedQuestion = parseQuestionFromAI(botMessage)

                when {
                    parsedQuestion != null && questionCount < maxQuestions -> {
                        _currentQuestion.value = parsedQuestion
                    }
                    else -> {
                        showSuggestions(botMessage) // Covers both invalid JSON and actual suggestions
                    }
                }
            } catch (e: Exception) {
                Log.e("CareerQnAViewModel", "Error fetching AI response", e)

                _currentQuestion.value = null
                _careerSuggestions.value = when {
                    e is retrofit2.HttpException && e.code() == 402 -> {
                        "⚠️ You've reached your free usage limit on OpenRouter. Try again later or reduce token size."
                    }
                    else -> {
                        "⚠️ Something went wrong. Please check your internet connection or try again."
                    }
                }
            }
        }
    }

    private fun showSuggestions(message: String) {
        waitingForSuggestions = false
        _careerSuggestions.value = message
        _currentQuestion.value = null
    }

    private fun requestFinalSuggestions() {
        if (waitingForSuggestions) return

        waitingForSuggestions = true
        conversation.add(
            Message(
                role = "system",
                content = """
                IMPORTANT: The user has answered all 5 questions.
                Provide ONLY final career suggestions in this exact format:
                
                - Career Option 1
                - Career Option 2
                - Career Option 3
                
                OR
                
                ["Career Option 1", "Career Option 2", "Career Option 3"]
                
                Do NOT include any other text or explanations.
            """.trimIndent()
            )
        )
        fetchNextQuestion()
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
