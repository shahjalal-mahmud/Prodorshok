package com.example.prodorshok.ui.config

import androidx.compose.ui.graphics.Color

data class TopMenuConfig(
    val showTopBar: Boolean = true,
    val showBackButton: Boolean = true,
    val showMenuDots: Boolean = true,
    val iconTint: Color = Color.White,
    val backgroundColor: Color = Color.Transparent
)

// Mapping route names to top bar behavior
val topMenuConfigMap = mapOf(
    "splash" to TopMenuConfig(showTopBar = false),
    "onboarding" to TopMenuConfig(showTopBar = false),
    "signup" to TopMenuConfig(showTopBar = false),
    "login" to TopMenuConfig(
        showTopBar = true,
        showBackButton = false,
        showMenuDots = true
    ),
    "dashboard" to TopMenuConfig(showTopBar = false),
    "profile" to TopMenuConfig(showTopBar = false),
    "profile_setup" to TopMenuConfig(
        showTopBar = true,
        iconTint = Color.Black
    ),
    "chat_ai" to TopMenuConfig(showTopBar = false),
    "home" to TopMenuConfig(showTopBar = false),
    "mentorship" to TopMenuConfig(showTopBar = false),
    "courses" to TopMenuConfig(showTopBar = false),
    "skills" to TopMenuConfig(showTopBar = false),
    "news" to TopMenuConfig(showTopBar = false),
    "Talk with Mentor" to TopMenuConfig(showTopBar = false),
    "1:1 Sessions" to TopMenuConfig(showTopBar = false),
    "Mental Support" to TopMenuConfig(showTopBar = false),
    "Job Prep" to TopMenuConfig(showTopBar = false),
    "Learning Community" to TopMenuConfig(showTopBar = false),
    "Skill Analysis" to TopMenuConfig(showTopBar = false),
    "Resume Builder" to TopMenuConfig(showTopBar = false),
    "Premium" to TopMenuConfig(
        showTopBar = true,
        iconTint = Color.Black
    ),
    "terms" to TopMenuConfig(showTopBar = true, iconTint = Color.Black),
    "need_help" to TopMenuConfig(showTopBar = true),
    "contact_us" to TopMenuConfig(showTopBar = true),
    "feedback" to TopMenuConfig(showTopBar = true),
    "feedback_list" to TopMenuConfig(showTopBar = true),
    "career_qna" to TopMenuConfig(
        showTopBar = false,
        iconTint = Color.Black,
        backgroundColor = Color.Gray
    ),
    "roadmap" to TopMenuConfig(
        showTopBar = false,
        iconTint = Color.Black,
        backgroundColor = Color.Green
    )
    // Add other routes as needed
)
