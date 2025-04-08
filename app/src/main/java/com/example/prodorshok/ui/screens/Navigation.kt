// Navigation.kt
package com.example.prodorshok.ui.screens

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun Navigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "welcome") {
        composable("welcome") { WelcomeScreen(navController) }
        composable("login") { LoginScreen(navController) }
        composable("signup") { SignUpScreen(navController) }
        composable("forgot_password") { ForgotPasswordScreen(navController) }
        composable("home") { HomeScreen(navController) }
        composable("profile_setup") { ProfileSetupScreen(navController) }
        composable("dashboard") {
            DashboardScreen { route ->
                navController.navigate(route)
            }
        }
        composable("roadmap") { RoadmapScreen() }
        composable("mentorship") { MentorshipScreen() }
        composable("courses") { CoursesScreen() }
        composable("skills") { SkillTrackerScreen() }
        composable("news") { CareerNewsScreen() }

    }
}

