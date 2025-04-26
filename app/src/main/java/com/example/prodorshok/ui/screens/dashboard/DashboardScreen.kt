// ui/screens/DashboardScreen.kt

package com.example.prodorshok.ui.screens.dashboard

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DashboardScreen(onOptionClick: (String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Welcome to Prodorshok 🎓",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        DashboardOption(title = "🎯 Personalized Roadmap") {
            onOptionClick("roadmap")
        }

        DashboardOption(title = "👨‍🏫 Expert Mentorship") {
            onOptionClick("mentorship")
        }

        DashboardOption(title = "📚 Recommended Courses") {
            onOptionClick("courses")
        }

        DashboardOption(title = "🧠 Skill Tracker") {
            onOptionClick("skills")
        }

        DashboardOption(title = "📰 Career News / Tips") {
            onOptionClick("news")
        }
    }
}



@Composable
fun DashboardOption(title: String, onClick: () -> Unit) {
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
            .clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFEEF2FF)
        )
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(text = title, fontSize = 18.sp, fontWeight = FontWeight.Medium)
        }
    }
}
