package com.example.prodorshok.ui.screens.chat_ai

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
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
}
