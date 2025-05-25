package com.example.prodorshok.viewmodel.auth

import android.util.Log
import android.util.Patterns
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.prodorshok.data.model.UserProfile
import com.google.firebase.auth.*
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {

    var name by mutableStateOf("")
    var email by mutableStateOf("")
    var password by mutableStateOf("")
    var confirmPassword by mutableStateOf("")
    var isLoading by mutableStateOf(false)
    var errorMessage by mutableStateOf("")
    var successMessage by mutableStateOf("")

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()

    private fun isEmailValid(): Boolean =
        Patterns.EMAIL_ADDRESS.matcher(email).matches()

    fun loginUser(
        onLoginSuccess: (FirebaseUser) -> Unit,
        onLoginFailure: (String) -> Unit
    ) {
        if (email.isBlank() || password.isBlank()) {
            errorMessage = "Please fill all fields"
            onLoginFailure(errorMessage)
            return
        }

        if (!isEmailValid()) {
            errorMessage = "Invalid email format"
            onLoginFailure(errorMessage)
            return
        }

        viewModelScope.launch {
            isLoading = true
            try {
                val result = auth.signInWithEmailAndPassword(email, password).await()
                successMessage = "Login Successful!"
                result.user?.let(onLoginSuccess)
            } catch (e: Exception) {
                errorMessage = "Login Failed: ${e.localizedMessage}"
                onLoginFailure(errorMessage)
            } finally {
                isLoading = false
            }
        }
    }

    fun signUpUser(
        name: String,
        onSignUpSuccess: (FirebaseUser) -> Unit,
        onSignUpFailure: (String) -> Unit
    ) {
        if (email.isBlank() || password.isBlank() || confirmPassword.isBlank()) {
            errorMessage = "Please fill all fields"
            onSignUpFailure(errorMessage)
            return
        }

        if (!isEmailValid()) {
            errorMessage = "Invalid email format"
            onSignUpFailure(errorMessage)
            return
        }

        if (password != confirmPassword) {
            errorMessage = "Passwords do not match"
            onSignUpFailure(errorMessage)
            return
        }

        viewModelScope.launch {
            isLoading = true
            try {
                val result = auth.createUserWithEmailAndPassword(email, password).await()
                val user = result.user
                successMessage = "Account Created Successfully!"
                user?.let {
                    val userProfile = UserProfile(
                        name = name,
                        email = email
                    )
                    firestore.collection("users").document(user.uid)
                        .set(userProfile)
                        .await()
                    onSignUpSuccess(user)
                }
            } catch (e: Exception) {
                errorMessage = "Sign Up Failed: ${e.localizedMessage}"
                onSignUpFailure(errorMessage)
            } finally {
                isLoading = false
            }
        }
    }

    fun firebaseAuthWithGoogle(
        credential: AuthCredential,
        onLoginSuccess: (FirebaseUser) -> Unit,
        onLoginFailure: (String) -> Unit
    ) {
        viewModelScope.launch {
            isLoading = true
            try {
                val result = auth.signInWithCredential(credential).await()
                val user = result.user
                Log.d("GOOGLE_AUTH", "Google sign-in successful: ${user?.email}")

                user?.let {
                    // Create profile only if doesn't already exist
                    val userDocRef = firestore.collection("users").document(user.uid)
                    val snapshot = userDocRef.get().await()
                    if (!snapshot.exists()) {
                        val userProfile = UserProfile(
                            name = user.displayName ?: "",
                            email = user.email ?: "",
                            photoUrl = user.photoUrl?.toString() ?: ""
                        )
                        userDocRef.set(userProfile).await()
                    }

                    onLoginSuccess(user)
                }
            } catch (e: Exception) {
                val message = e.localizedMessage ?: "Unknown error"
                Log.e("GOOGLE_AUTH", "Google sign-in failed", e)
                errorMessage = "Google Sign-In Failed: $message"
                onLoginFailure(errorMessage)
            } finally {
                isLoading = false
            }
        }
    }

    fun sendPasswordReset(
        email: String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        if (email.isBlank()) {
            onFailure("Please enter your email")
            return
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            onFailure("Invalid email format")
            return
        }

        viewModelScope.launch {
            isLoading = true
            try {
                auth.sendPasswordResetEmail(email).await()
                onSuccess()
            } catch (e: Exception) {
                onFailure("Failed to send reset link: ${e.localizedMessage}")
            } finally {
                isLoading = false
            }
        }
    }

    fun logoutUser() {
        auth.signOut()
        successMessage = "Logged out successfully!"
    }

    fun getCurrentUser(): FirebaseUser? = auth.currentUser
}
