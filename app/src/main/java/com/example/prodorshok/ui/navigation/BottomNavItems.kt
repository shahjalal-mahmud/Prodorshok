package com.example.prodorshok.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChatBubbleOutline
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star

val bottomNavItems = listOf(
    BottomNavItem(Screen.Dashboard.route,    Icons.Default.Home,   "dashboard"),
    BottomNavItem(Screen.TalkWithMentor.route, Icons.Default.People, "Mentor"),
    BottomNavItem(Screen.ChatWithAI.route,   Icons.Default.ChatBubbleOutline,   "Chat"),
    BottomNavItem(Screen.Profile.route,      Icons.Default.Person, "Profile"),
    BottomNavItem(Screen.Premium.route,      Icons.Default.Star,   "Premium")
)