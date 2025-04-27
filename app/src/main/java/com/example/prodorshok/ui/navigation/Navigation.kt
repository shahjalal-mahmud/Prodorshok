package com.example.prodorshok.ui.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.prodorshok.ui.components.AutoSmartBackHandler
import com.example.prodorshok.ui.screens.auth.ForgotPasswordScreen
import com.example.prodorshok.ui.screens.auth.LoginScreen
import com.example.prodorshok.ui.screens.auth.SignUpScreen
import com.example.prodorshok.ui.screens.career_news.CareerNewsScreen
import com.example.prodorshok.ui.screens.courses.CoursesScreen
import com.example.prodorshok.ui.screens.dashboard.DashboardScreen
import com.example.prodorshok.ui.screens.home.HomeScreen
import com.example.prodorshok.ui.screens.mentorship.MentorshipScreen
import com.example.prodorshok.ui.screens.profile.ProfilePage
import com.example.prodorshok.ui.screens.profile.ProfileSetupScreen
import com.example.prodorshok.ui.screens.roadmap.RoadmapScreen
import com.example.prodorshok.ui.screens.skill_tracker.SkillTrackerScreen
import com.example.prodorshok.ui.screens.splash.WelcomeScreen

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun Navigation(startGoogleSignIn: () -> Unit) {
    val navController = rememberNavController()

    // Manage back button behavior
    AutoSmartBackHandler(navController)

    NavHost(
        navController = navController,
        startDestination = "welcome"
    ) {
        animatedComposable("welcome") { WelcomeScreen(navController) }
        animatedComposable("login") { LoginScreen(navController, startGoogleSignIn) }
        animatedComposable("signup") { SignUpScreen(navController, startGoogleSignIn) }
        animatedComposable("forgot_password") { ForgotPasswordScreen(navController) }
        animatedComposable("home") { HomeScreen(navController) }
        animatedComposable("profile_setup") { ProfileSetupScreen(navController) }
        animatedComposable("dashboard") {
            DashboardScreen { route -> navController.navigate(route) }
        }
        animatedComposable("roadmap") { RoadmapScreen() }
        animatedComposable("mentorship") { MentorshipScreen() }
        animatedComposable("courses") { CoursesScreen() }
        animatedComposable("skills") { SkillTrackerScreen() }
        animatedComposable("news") { CareerNewsScreen() }
        animatedComposable("profile") { ProfilePage(navController) }
    }
}

// Helper Extension Function for Animations
@OptIn(ExperimentalAnimationApi::class)
private fun NavGraphBuilder.animatedComposable(
    route: String,
    content: @Composable () -> Unit
) {
    composable(
        route = route,
        enterTransition = {
            slideInHorizontally(
                initialOffsetX = { 1000 },
                animationSpec = tween(500)
            )
        },
        exitTransition = {
            slideOutHorizontally(
                targetOffsetX = { -1000 },
                animationSpec = tween(500)
            )
        },
        popEnterTransition = {
            slideInHorizontally(
                initialOffsetX = { -1000 },
                animationSpec = tween(500)
            )
        },
        popExitTransition = {
            slideOutHorizontally(
                targetOffsetX = { 1000 },
                animationSpec = tween(500)
            )
        }
    ) {
        content()
    }
}
