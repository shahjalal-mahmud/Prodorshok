package com.example.prodorshok.ui.screens.chat_ai

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

@Composable
fun ChatWithAiScreen(navController: NavController) {
    val vm: ChatViewModel = viewModel()
    val messages by vm.messages.collectAsState()
    val isLoading by vm.isLoading.collectAsState()
    var draft by remember { mutableStateOf("") }

    Surface(modifier = Modifier.fillMaxSize()) {
        Column {
            ChatTopBar(onBack = { navController.popBackStack() })

            MessageList(messages = messages, isTyping = isLoading, modifier = Modifier.weight(1f))

            ChatInputBar(
                text = draft,
                onTextChange = { draft = it },
                onSend = {
                    if (draft.isNotBlank()) {
                        vm.onUserMessage(draft)
                        draft = ""
                    }
                }
            )
        }
    }
}
