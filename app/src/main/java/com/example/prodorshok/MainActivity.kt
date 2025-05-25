package com.example.prodorshok

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.prodorshok.ui.components.auth.AuthTopMenu
import com.example.prodorshok.ui.config.TopMenuConfig
import com.example.prodorshok.ui.config.topMenuConfigMap
import com.example.prodorshok.ui.navigation.Navigation
import com.example.prodorshok.ui.theme.ProdorshokTheme
import com.example.prodorshok.viewmodel.auth.AuthViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider
import androidx.compose.runtime.getValue
import com.example.prodorshok.data.preferences.OnboardingPreference

class MainActivity : ComponentActivity() {

    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var googleSignInLauncher: ActivityResultLauncher<Intent>
    private val authViewModel: AuthViewModel by viewModels()

    private var startDestination: String = "splash"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Load onboarding state before Compose
        startDestination = if (OnboardingPreference.hasCompletedOnboarding(this)) {
            "login"
        } else {
            "splash"
        }

        setupGoogleSignIn()

        setContent {
            ProdorshokApp()
        }
    }

    private fun setupGoogleSignIn() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("833137499868-t0ia0qhtl68iu4rbaa222i5c6sq70rbr.apps.googleusercontent.com")
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        googleSignInLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val data: Intent? = result.data
                val task = GoogleSignIn.getSignedInAccountFromIntent(data)
                try {
                    val account = task.getResult(ApiException::class.java)
                    account?.idToken?.let { token ->
                        val credential = GoogleAuthProvider.getCredential(token, null)
                        authViewModel.firebaseAuthWithGoogle(
                            credential,
                            onLoginSuccess = { /* Show success */ },
                            onLoginFailure = { /* Show error */ }
                        )
                    }
                } catch (_: ApiException) {}
            }
        }
    }

    @Composable
    private fun ProdorshokApp() {
        ProdorshokTheme {
            val navController = rememberNavController()
            val currentRoute by navController.currentBackStackEntryAsState()
            val routeName = currentRoute?.destination?.route ?: ""
            val topBarConfig = topMenuConfigMap[routeName] ?: TopMenuConfig()

            Surface(color = MaterialTheme.colorScheme.background) {
                Box(Modifier.fillMaxSize()) {
                    Navigation(
                        startDestination = startDestination,
                        startGoogleSignIn = { startGoogleSignIn() },
                        authViewModel = authViewModel,
                        navController = navController
                    )

                    if (topBarConfig.showTopBar) {
                        AuthTopMenu(
                            navController = navController,
                            showBackButton = topBarConfig.showBackButton,
                            showMenuDots = topBarConfig.showMenuDots,
                            iconTintColor = topBarConfig.iconTint,
                            onMenuItemClick = { route -> navController.navigate(route) }
                        )
                    }
                }
            }
        }
    }

    private fun startGoogleSignIn() {
        val signInIntent = googleSignInClient.signInIntent
        googleSignInLauncher.launch(signInIntent)
    }
}
