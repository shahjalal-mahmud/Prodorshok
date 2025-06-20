package com.example.prodorshok.ui.screens.chat_ai

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.prodorshok.viewmodel.profile.ProfileViewModel

@Composable
fun ChatWithAiScreen(navController: NavController) {
    val vm: ChatViewModel = viewModel()
    val profileViewModel: ProfileViewModel = viewModel()
    val messages by vm.messages.collectAsState()
    val isLoading by vm.isLoading.collectAsState()
    var draft by remember { mutableStateOf("") }

    val userProfile by profileViewModel.userProfile.collectAsState()

    LaunchedEffect(Unit) {
        profileViewModel.fetchUserProfile()
    }

    Surface(modifier = Modifier.fillMaxSize()) {
        Column {
            ChatTopBar(onBack = { navController.popBackStack() })

            // 🔹 Button above message list and input
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Button(
                    onClick = {
                        vm.sendProfileToAI(userProfile)
                    },
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Text("🎯 Get Career Suggestions")
                }
            }

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
