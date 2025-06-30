package com.example.prodorshok.domain.careerqna

import com.example.prodorshok.data.network.Message

class MessageHistoryManager {
    private val messages = mutableListOf<Message>()

    fun reset() {
        messages.clear()
    }

    fun add(message: Message) {
        messages.add(message)
    }

    fun getLast(count: Int): List<Message> {
        return messages.takeLast(count)
    }

    fun getAll(): List<Message> {
        return messages
    }
}