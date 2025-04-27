package com.example.prodorshok.viewmodel.auth

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class AuthViewModel : ViewModel() {

    var email = mutableStateOf("")
    var password = mutableStateOf("")
    var confirmPassword = mutableStateOf("")
    var isLoading = mutableStateOf(false)
    var errorMessage = mutableStateOf("")
    var successMessage = mutableStateOf("")

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    /**
     * Login with Email and Password
     */
    fun loginUser(
        onLoginSuccess: (FirebaseUser) -> Unit,
        onLoginFailure: (String) -> Unit
    ) {
        if (email.value.isEmpty() || password.value.isEmpty()) {
            errorMessage.value = "Please fill all fields"
            onLoginFailure(errorMessage.value)
            return
        }

        isLoading.value = true
        auth.signInWithEmailAndPassword(email.value, password.value)
            .addOnCompleteListener { task ->
                isLoading.value = false
                if (task.isSuccessful) {
                    successMessage.value = "Login Successful!"
                    task.result?.user?.let { user ->
                        onLoginSuccess(user)
                    }
                } else {
                    errorMessage.value = "Login Failed: ${task.exception?.localizedMessage}"
                    onLoginFailure(errorMessage.value)
                }
            }
    }

    /**
     * Signup (Register) with Email and Password
     */
    fun signUpUser(
        onSignUpSuccess: (FirebaseUser) -> Unit,
        onSignUpFailure: (String) -> Unit
    ) {
        if (email.value.isEmpty() || password.value.isEmpty() || confirmPassword.value.isEmpty()) {
            errorMessage.value = "Please fill all fields"
            onSignUpFailure(errorMessage.value)
            return
        }

        if (password.value != confirmPassword.value) {
            errorMessage.value = "Passwords do not match"
            onSignUpFailure(errorMessage.value)
            return
        }

        isLoading.value = true
        auth.createUserWithEmailAndPassword(email.value, password.value)
            .addOnCompleteListener { task ->
                isLoading.value = false
                if (task.isSuccessful) {
                    successMessage.value = "Account Created Successfully!"
                    task.result?.user?.let { user ->
                        onSignUpSuccess(user)
                    }
                } else {
                    errorMessage.value = "Sign Up Failed: ${task.exception?.localizedMessage}"
                    onSignUpFailure(errorMessage.value)
                }
            }
    }

    /**
     * Firebase Authentication with Google
     */
    fun firebaseAuthWithGoogle(
        credential: AuthCredential,
        onLoginSuccess: (FirebaseUser) -> Unit,
        onLoginFailure: (String) -> Unit
    ) {
        isLoading.value = true
        auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                isLoading.value = false
                if (task.isSuccessful) {
                    Log.d("GOOGLE_AUTH", "Sign-in successful: ${task.result?.user?.email}")
                    task.result?.user?.let { user ->
                        onLoginSuccess(user)
                    }
                } else {
                    val exception = task.exception
                    Log.e("GOOGLE_AUTH", "Google Sign-In failed", exception)
                    errorMessage.value = "Google Sign-In Failed: ${exception?.localizedMessage ?: "Unknown error"}"
                    onLoginFailure(errorMessage.value)
                }
            }
    }

    /**
     * Send Password Reset Email
     */
    fun sendPasswordReset(
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        if (email.value.isEmpty()) {
            errorMessage.value = "Please enter your email"
            onFailure(errorMessage.value)
            return
        }

        isLoading.value = true
        auth.sendPasswordResetEmail(email.value)
            .addOnCompleteListener { task ->
                isLoading.value = false
                if (task.isSuccessful) {
                    successMessage.value = "Reset link sent to ${email.value}"
                    onSuccess()
                } else {
                    errorMessage.value = "Failed to send reset link: ${task.exception?.localizedMessage}"
                    onFailure(errorMessage.value)
                }
            }
    }

    /**
     * Logout Current User
     */
    fun logoutUser() {
        auth.signOut()
        successMessage.value = "Logged out successfully!"
    }

    /**
     * Get Current User
     */
    fun getCurrentUser(): FirebaseUser? {
        return auth.currentUser
    }
}
