package com.example.prodorshok.ui.screens.chat_ai

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ChatTopBar(onBack: () -> Unit) {
    Box(modifier = Modifier.fillMaxWidth().height(56.dp)) {
        IconButton(onClick = onBack, Modifier.align(Alignment.CenterStart)) {
            Icon(Icons.Filled.Menu, contentDescription = "Menu")
        }
        IconButton(onClick = { /* TODO: new chat */ }, Modifier.align(Alignment.CenterEnd)) {
            Icon(Icons.Filled.Add, contentDescription = "New Chat")
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.align(Alignment.Center)
        ) {
            Icon(
                Icons.Filled.Android,
                contentDescription = "Bot",
                modifier = Modifier.size(80.dp)
            )
            Text(
                text = "Hello..",
                style = MaterialTheme.typography.headlineSmall,
                color = Color.Black
            )
        }
    }
}
