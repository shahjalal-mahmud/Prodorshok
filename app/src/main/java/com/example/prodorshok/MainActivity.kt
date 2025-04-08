package com.example.prodorshok

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import com.example.prodorshok.ui.screens.Navigation
import com.example.prodorshok.ui.theme.ProdorshokTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProdorshokTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    // Use Navigation composable for handling the navigation
                    Navigation()
                }
            }
        }
    }
}
