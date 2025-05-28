package com.example.prodorshok.ui.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import com.example.prodorshok.ui.screens.auth.CheckYourMailScreen
import com.example.prodorshok.ui.screens.auth.ForgotPasswordScreen
import com.example.prodorshok.ui.screens.auth.LoginScreen
import com.example.prodorshok.ui.screens.auth.SignUpScreen
import com.example.prodorshok.ui.screens.career_guidance.CareerNewsScreen
import com.example.prodorshok.ui.screens.career_guidance.CoursesScreen
import com.example.prodorshok.ui.screens.career_guidance.MentorshipScreen
import com.example.prodorshok.ui.screens.career_guidance.RoadmapScreen
import com.example.prodorshok.ui.screens.career_guidance.SkillTrackerScreen
import com.example.prodorshok.ui.screens.chat_ai.ChatWithAiScreen
import com.example.prodorshok.ui.screens.contact_us.ContactUsScreen
import com.example.prodorshok.ui.screens.dashboard.DashboardScreen
import com.example.prodorshok.ui.screens.feedback.FeedbackListScreen
import com.example.prodorshok.ui.screens.feedback.FeedbackScreen
import com.example.prodorshok.ui.screens.home.HomeScreen
import com.example.prodorshok.ui.screens.job.JobPrepScreen
import com.example.prodorshok.ui.screens.mentors.MentalSupportScreen
import com.example.prodorshok.ui.screens.mentors.OneOnOneSessionScreen
import com.example.prodorshok.ui.screens.mentors.TalkWithMentorScreen
import com.example.prodorshok.ui.screens.need_help.NeedHelpScreen
import com.example.prodorshok.ui.screens.onboarding.OnboardingScreen
import com.example.prodorshok.ui.screens.premium.PremiumScreen
import com.example.prodorshok.ui.screens.profile.ProfilePage
import com.example.prodorshok.ui.screens.profile.ProfileSetupScreen
import com.example.prodorshok.ui.screens.splash.SplashScreen
import com.example.prodorshok.ui.screens.terms.TermsAndPrivacyScreen
import com.example.prodorshok.ui.utils.AutoSmartBackHandler
import com.example.prodorshok.viewmodel.auth.AuthViewModel

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun Navigation(
    startDestination: String,
    navController: NavHostController,
    startGoogleSignIn: () -> Unit,
    authViewModel: AuthViewModel
) {
    // Manage back button behavior
    AutoSmartBackHandler(navController)

    Scaffold(
        bottomBar = {
            val bottomBarRoutes = bottomNavItems.map { it.route }
            val currentRoute by navController.currentBackStackEntryAsState()
            val routeName = currentRoute?.destination?.route
            if (routeName in bottomBarRoutes) {
                BottomBar(navController)
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = startDestination,
            modifier = Modifier.padding(innerPadding)
        ) {
            animatedComposable("splash") { SplashScreen(navController) }
            animatedComposable("onboarding") { OnboardingScreen(navController) }
            animatedComposable("login") { LoginScreen(navController, startGoogleSignIn) }
            animatedComposable("signup") { SignUpScreen(navController, startGoogleSignIn) }
            animatedComposable("forgot_password") { ForgotPasswordScreen(navController) }
            animatedComposable("home") { HomeScreen(navController) }
            animatedComposable("profile_setup") { ProfileSetupScreen(navController) }
            animatedComposable(Screen.Dashboard.route) {
                DashboardScreen(
                    userName = authViewModel.getCurrentUser()?.displayName ?: "there",
                    onNotificationClick = { navController.navigate("notifications") },
                    onFeatureClick = { f ->
                        when (f.title) {
                            "Chat with AI" -> navController.navigate(Screen.ChatWithAI.route)
                            "Talk with Mentor" -> navController.navigate(Screen.TalkWithMentor.route)
                            "1:1 Sessions" -> navController.navigate(Screen.OneOnOneSession.route)
                            "Mental Support" -> navController.navigate(Screen.MentalSupport.route)
                            "Job Prep" -> navController.navigate(Screen.JobPrep.route)
                            "Learning Community" -> navController.navigate(Screen.LearningCommunity.route)
                            "Skill Analysis" -> navController.navigate(Screen.SkillAnalysis.route)
                            "Resume Builder" -> navController.navigate(Screen.ResumeBuilder.route)
                        }
                    },
                    navController = navController
                )
            }

            // Now plug in the real screens:
            animatedComposable(Screen.ChatWithAI.route) {
                ChatWithAiScreen(navController)
            }
            animatedComposable(Screen.TalkWithMentor.route) {
                TalkWithMentorScreen(navController)
            }
            animatedComposable(Screen.OneOnOneSession.route) {
                OneOnOneSessionScreen(navController)
            }
            animatedComposable(Screen.MentalSupport.route) {
                MentalSupportScreen(navController)
            }
            animatedComposable(Screen.JobPrep.route) {
                JobPrepScreen(navController)
            }
            animatedComposable(Screen.Premium.route) {
                PremiumScreen(navController)
            }
            animatedComposable("roadmap") { RoadmapScreen() }
            animatedComposable("mentorship") { MentorshipScreen() }
            animatedComposable("courses") { CoursesScreen() }
            animatedComposable("skills") { SkillTrackerScreen() }
            animatedComposable("news") { CareerNewsScreen() }
            animatedComposable("profile") { ProfilePage(navController) }
            animatedComposable("terms") { TermsAndPrivacyScreen(navController) }

            // These are for Top Menu
            animatedComposable("need_help") { NeedHelpScreen(navController) }
            animatedComposable("contact_us") {
                ContactUsScreen(onBackClick = { navController.popBackStack() })
            }
            animatedComposable("feedback") { FeedbackScreen(navController) }
            animatedComposable("feedback_list") { FeedbackListScreen(navController) }

            // Pass email param from route
            composable(
                route = "check_mail/{email}",
                arguments = listOf(navArgument("email") { type = NavType.StringType })
            ) { backStackEntry ->
                val email = backStackEntry.arguments?.getString("email") ?: ""
                CheckYourMailScreen(
                    navController = navController,
                    authViewModel = authViewModel,
                    email = email
                )
            }

        }
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
