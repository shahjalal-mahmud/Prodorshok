package com.example.prodorshok.ui.viewmodel

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

    private val auth = FirebaseAuth.getInstance()

    fun loginUser(onLoginSuccess: (FirebaseUser) -> Unit, onLoginFailure: (String) -> Unit) {
        isLoading.value = true
        auth.signInWithEmailAndPassword(email.value, password.value)
            .addOnCompleteListener { task ->
                isLoading.value = false
                if (task.isSuccessful) {
                    successMessage.value = "Welcome!"
                    onLoginSuccess(task.result?.user!!)
                } else {
                    errorMessage.value = "Login Failed: ${task.exception?.message}"
                    onLoginFailure(errorMessage.value)
                }
            }
    }

    fun signUpUser(onSignUpSuccess: (FirebaseUser) -> Unit, onSignUpFailure: (String) -> Unit) {
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
                    successMessage.value = "Account Created!"
                    onSignUpSuccess(task.result?.user!!)
                } else {
                    errorMessage.value = "Sign Up Failed: ${task.exception?.message}"
                    onSignUpFailure(errorMessage.value)
                }
            }
    }

    fun firebaseAuthWithGoogle(
        credential: AuthCredential,
        onLoginSuccess: () -> Unit,
        onLoginFailure: (String) -> Unit
    ) {
        isLoading.value = true
        auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                isLoading.value = false
                if (task.isSuccessful) {
                    onLoginSuccess()
                } else {
                    onLoginFailure(task.exception?.message ?: "Unknown error")
                }
            }
    }

    fun sendPasswordReset(onSuccess: () -> Unit, onFailure: (String) -> Unit) {
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
                    successMessage.value = "Reset link sent to $email"
                    onSuccess()
                } else {
                    errorMessage.value = "Failed to send reset link: ${task.exception?.message}"
                    onFailure(errorMessage.value)
                }
            }
    }
}
