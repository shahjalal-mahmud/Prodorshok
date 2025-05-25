package com.example.prodorshok.ui.screens.premium

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController

@Composable
fun PremiumScreen(
    navController: NavHostController // optional if you need nav inside
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("Premium Packages\n(Basic, Silver, Gold, Platinum)")
    }
}