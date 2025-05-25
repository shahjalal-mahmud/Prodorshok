package com.example.prodorshok.data.repository

import android.content.Context
import com.example.prodorshok.data.network.Message
import com.example.prodorshok.data.network.NetworkModule
import com.example.prodorshok.data.network.OpenRouterRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class OpenRouterRepository(context: Context) {
    private val service = NetworkModule.provideOpenRouterService(context)

    suspend fun sendPrompt(prompt: String): String? = withContext(Dispatchers.IO) {
        try {
            val messages = listOf(
                Message("system", "You are a helpful AI assistant."),
                Message("user", prompt)
            )
            val request = OpenRouterRequest(messages = messages)
            val response = service.getChatCompletion(request)
            return@withContext response.choices.firstOrNull()?.message?.content
        } catch (e: Exception) {
            e.printStackTrace()
            return@withContext null
        }
    }
}
