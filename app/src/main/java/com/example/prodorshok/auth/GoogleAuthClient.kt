package com.example.prodorshok.auth

import android.app.Activity
import android.content.Context
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.ComponentActivity
import androidx.activity.result.IntentSenderRequest
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.AuthCredential

class GoogleAuthClient(
    private val context: Context,
    private val activity: Activity
) {
    private val oneTapClient = Identity.getSignInClient(context)

    // Define the sign-in request with Google ID token
    private val signInRequest = BeginSignInRequest.builder()
        .setGoogleIdTokenRequestOptions(
            BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                .setSupported(true)
                .setServerClientId("833137499868-t0ia0qhtl68iu4rbaa222i5c6sq70rbr.apps.googleusercontent.com")
                .setFilterByAuthorizedAccounts(false)
                .build()
        )
        .setAutoSelectEnabled(true)
        .build()

    // Function to handle Google sign-in result
    fun startGoogleSignIn(
        onGoogleSignInSuccess: (AuthCredential) -> Unit,
        onGoogleSignInFailure: (String) -> Unit
    ) {
        // Ensure activity is a ComponentActivity
        if (activity is ComponentActivity) {
            // Create the launcher for handling Google sign-in result
            val googleSignInLauncher = activity.registerForActivityResult(
                ActivityResultContracts.StartIntentSenderForResult()
            ) { result: ActivityResult ->
                if (result.resultCode == Activity.RESULT_OK) {
                    try {
                        val credential = oneTapClient.getSignInCredentialFromIntent(result.data)
                        val idToken = credential.googleIdToken
                        if (idToken != null) {
                            val firebaseCredential = GoogleAuthProvider.getCredential(idToken, null)
                            onGoogleSignInSuccess(firebaseCredential)
                        } else {
                            onGoogleSignInFailure("No ID Token found")
                        }
                    } catch (e: Exception) {
                        onGoogleSignInFailure(e.localizedMessage ?: "Google Sign-In Failed")
                    }
                } else {
                    onGoogleSignInFailure("Google Sign-In was unsuccessful.")
                }
            }

            // Begin sign-in and launch the sign-in intent
            oneTapClient.beginSignIn(signInRequest)
                .addOnSuccessListener(activity) { result ->
                    val request = IntentSenderRequest.Builder(result.pendingIntent.intentSender).build()
                    googleSignInLauncher.launch(request)
                }
                .addOnFailureListener { e ->
                    Toast.makeText(context, e.localizedMessage, Toast.LENGTH_SHORT).show()
                }
        }
    }
}
