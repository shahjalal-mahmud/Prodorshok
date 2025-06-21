package com.example.prodorshok.data.network

data class Message(val role: String, val content: String)

data class OpenRouterRequest(
    val model: String = "deepseek/deepseek-chat-v3-0324", // ✅ Updated full model name
    val messages: List<Message>,
    val temperature: Double = 0.7,
    val max_tokens: Int = 1000
)

data class Choice(val message: Message)
data class OpenRouterResponse(val choices: List<Choice>)