package com.example.prodorshok.ui.screens.chat_ai
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun MessageList(
    messages: List<Pair<Boolean, String>>,
    isTyping: Boolean = false,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(vertical = 8.dp, horizontal = 16.dp)
    ) {
        items(messages) { (isUser, text) ->
            MessageBubble(isUser = isUser, text = text)
            Spacer(Modifier.height(8.dp))
        }

        if (isTyping) {
            item {
                TypingIndicatorBubble()
                Spacer(Modifier.height(8.dp))
            }
        }
    }
}
