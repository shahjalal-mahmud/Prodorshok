// ui/screens/DashboardScreen.kt

package com.example.prodorshok.ui.screens.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.prodorshok.R
import com.example.prodorshok.ui.components.dashboard.Feature
import com.example.prodorshok.ui.components.dashboard.FeatureGrid
import com.example.prodorshok.ui.components.dashboard.TopBar
import com.example.prodorshok.ui.components.dashboard.UpcomingEventCard

@Composable
fun DashboardScreen(
    userName: String,
    onNotificationClick: () -> Unit,
    onFeatureClick: (Feature) -> Unit,
    navController: NavController
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFFF5F7FA))
            .padding(16.dp)
    ) {
        TopBar(userName = userName, onBellClick = onNotificationClick)
        Spacer(Modifier.height(16.dp))
        UpcomingEventCard(
            date = "27 June, 2025",
            onClick = { /* TODO: navigate to events */ }
        )
        Spacer(Modifier.height(24.dp))
        FeatureGrid(
            features = listOf(
                Feature("Chat with AI", R.drawable.ic_chat_ai),
                Feature("Talk with Mentor", R.drawable.ic_mentor),
                Feature("1:1 Sessions", R.drawable.ic_session),
                Feature("Mental Support", R.drawable.ic_mental),
                Feature("Job Prep", R.drawable.ic_job),
                Feature("Learning Community", R.drawable.ic_community),
                Feature("Skill Analysis", R.drawable.ic_skill),
                Feature("Resume Builder", R.drawable.ic_resume),
            ),
            onFeatureClick = onFeatureClick
        )
    }
}

