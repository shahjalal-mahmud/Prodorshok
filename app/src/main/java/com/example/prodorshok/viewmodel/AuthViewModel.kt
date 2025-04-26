package com.example.prodorshok.ui.viewmodel

import com.google.firebase.auth.FirebaseAuthException

private fun getFriendlyErrorMessage(e: Exception?): String {
    return when ((e as? FirebaseAuthException)?.errorCode) {
        "ERROR_INVALID_CREDENTIAL" -> "Invalid credentials. Please try again."
        "ERROR_ACCOUNT_EXISTS_WITH_DIFFERENT_CREDENTIAL" -> "Account exists with a different sign-in method."
        "ERROR_USER_DISABLED" -> "This account has been disabled."
        "ERROR_NETWORK_REQUEST_FAILED" -> "Network error. Please check your connection."
        else -> e?.localizedMessage ?: "Something went wrong during Google Sign-In."
    }
}
