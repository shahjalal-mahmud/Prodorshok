package com.example.prodorshok.ui.screens.chat_ai

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.prodorshok.data.model.UserProfile
import com.example.prodorshok.data.repository.OpenRouterRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ChatViewModel(app: Application) : AndroidViewModel(app) {
    private val repo = OpenRouterRepository(app.applicationContext)

    private val _messages = MutableStateFlow<List<Pair<Boolean, String>>>(emptyList())
    val messages: StateFlow<List<Pair<Boolean, String>>> = _messages

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun onUserMessage(text: String) {
        _messages.value = _messages.value + (true to text)
        _isLoading.value = true

        viewModelScope.launch {
            try {
                val botResponse = repo.sendPrompt(text)
                _messages.value = _messages.value + (
                        false to (botResponse ?: "⚠️ Sorry, something went wrong.")
                        )
            } catch (e: Exception) {
                _messages.value = _messages.value + (false to "⚠️ An error occurred.")
            } finally {
                _isLoading.value = false
            }
        }
    }
    fun sendProfileToAI(profile: UserProfile) {
        val prompt = """
        You are a professional career counselor.
        
        A student has provided their personal profile. Based on the details below, suggest 3–5 career paths that best match their personality, strengths, interests, and academic stage. For each suggested career path:
        - Explain why it is a good fit.
        - Mention how the student can start preparing for it.
        - Recommend one or two relevant skills or courses.

        Here is the student's profile:
        - Name: ${profile.name}
        - Academic Stage: ${profile.academicStage}
        - Interests: ${profile.interests}
        - Strengths: ${profile.strengths}
        - Career Goal (if any): ${profile.careerGoal}
        - Location: ${profile.location}

        Make sure the advice is practical, inspiring, and tailored to students in Bangladesh.
    """.trimIndent()

        _isLoading.value = true

        viewModelScope.launch {
            try {
                val botResponse = repo.sendPrompt(prompt)
                _messages.value = _messages.value + (
                        false to (botResponse ?: "⚠️ Sorry, something went wrong.")
                        )
            } catch (e: Exception) {
                _messages.value = _messages.value + (false to "⚠️ An error occurred.")
            } finally {
                _isLoading.value = false
            }
        }
    }
}
