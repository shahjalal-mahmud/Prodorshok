package com.example.prodorshok.ui.navigation

sealed class Screen(val route: String) {
    object Dashboard          : Screen("dashboard")
    object Home               : Screen("home")
    object ChatWithAI         : Screen("chat_ai")
    object TalkWithMentor     : Screen("mentor_list")
    object OneOnOneSession    : Screen("one_on_one")
    object MentalSupport      : Screen("mental_support")
    object JobPrep            : Screen("job_prep")
    object LearningCommunity  : Screen("learning_community")
    object SkillAnalysis      : Screen("skill_analysis")
    object ResumeBuilder      : Screen("resume_builder")
    object Premium            : Screen("premium")
    object Profile            : Screen("profile")
    object CareerQnA          : Screen("career_qna")
}