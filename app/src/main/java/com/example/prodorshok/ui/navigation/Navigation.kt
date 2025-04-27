// Navigation.kt
package com.example.prodorshok.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.prodorshok.ui.screens.career_news.CareerNewsScreen
import com.example.prodorshok.ui.screens.courses.CoursesScreen
import com.example.prodorshok.ui.screens.mentorship.MentorshipScreen
import com.example.prodorshok.ui.screens.roadmap.RoadmapScreen
import com.example.prodorshok.ui.screens.skill_tracker.SkillTrackerScreen
import com.example.prodorshok.ui.screens.auth.ForgotPasswordScreen
import com.example.prodorshok.ui.screens.auth.LoginScreen
import com.example.prodorshok.ui.screens.auth.SignUpScreen
import com.example.prodorshok.ui.screens.dashboard.DashboardScreen
import com.example.prodorshok.ui.screens.home.HomeScreen
import com.example.prodorshok.ui.screens.profile.ProfilePage
import com.example.prodorshok.ui.screens.profile.ProfileSetupScreen
import com.example.prodorshok.ui.screens.splash.WelcomeScreen

@Composable
fun Navigation(startGoogleSignIn: () -> Unit) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "welcome") {
        composable("welcome") { WelcomeScreen(navController) }
        composable("login") {
            LoginScreen(navController, startGoogleSignIn) // Pass the startGoogleSignIn function here
        }
        composable("signup") {
            SignUpScreen(navController, startGoogleSignIn) }
        composable("forgot_password") { ForgotPasswordScreen(navController) }
        composable("home") { HomeScreen(navController) }
        composable("profile_setup") { ProfileSetupScreen(navController) }
        composable("dashboard") {
            DashboardScreen { route -> navController.navigate(route) }
        }
        composable("roadmap") { RoadmapScreen() }
        composable("mentorship") { MentorshipScreen() }
        composable("courses") { CoursesScreen() }
        composable("skills") { SkillTrackerScreen() }
        composable("news") { CareerNewsScreen() }
        composable("profile") { ProfilePage(navController) }
    }
}
