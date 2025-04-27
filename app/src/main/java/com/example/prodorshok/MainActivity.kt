package com.example.prodorshok

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import com.example.prodorshok.ui.navigation.Navigation
import com.example.prodorshok.ui.theme.ProdorshokTheme
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task

class MainActivity : ComponentActivity() {

    // Declare the GoogleSignInClient and ActivityResultLauncher
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var googleSignInLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize Google Sign-In client
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("833137499868-t0ia0qhtl68iu4rbaa222i5c6sq70rbr.apps.googleusercontent.com")
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        // Register the ActivityResultLauncher for Google Sign-In
        googleSignInLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val data: Intent? = result.data
                val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
                try {
                    // Attempt to get the signed-in Google Account
                    val account = task.getResult(ApiException::class.java)
                    // Handle the account (e.g., send to server or update UI)
                    // For example, you could pass this account info to your app or login screen.
                } catch (e: ApiException) {
                    // Handle sign-in error
                }
            } else {
                // Handle failure
            }
        }

        // Set content view with Navigation
        setContent {
            ProdorshokTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    // Pass the startGoogleSignIn function to the Navigation composable
                    Navigation(startGoogleSignIn = { startGoogleSignIn() })
                }
            }
        }
    }

    // Function to start Google Sign-In
    fun startGoogleSignIn() {
        val signInIntent: Intent = googleSignInClient.signInIntent
        googleSignInLauncher.launch(signInIntent)
    }
}
