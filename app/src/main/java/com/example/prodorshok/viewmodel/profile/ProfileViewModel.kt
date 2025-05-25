package com.example.prodorshok.viewmodel.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.prodorshok.data.model.UserProfile
import com.example.prodorshok.data.repository.UserProfileRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProfileViewModel : ViewModel() {
    private val _userProfile = MutableStateFlow(
        UserProfile(
            name = "",
            email = FirebaseAuth.getInstance().currentUser?.email ?: "",
            photoUrl = "",
            interests = "",
            strengths = "",
            academicStage = "",
            careerGoal = "",
            location = "",
            phoneNumber = "",
            districtZila = ""
        )
    )
    val userProfile: StateFlow<UserProfile> = _userProfile

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun fetchUserProfile() {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return
        viewModelScope.launch {
            UserProfileRepository.fetchUserProfile(
                onSuccess = { data ->
                    _userProfile.value = _userProfile.value.copy(
                        name = data["name"] ?: "",
                        interests = data["interests"] ?: "",
                        strengths = data["strengths"] ?: "",
                        academicStage = data["academicStage"] ?: "",
                        careerGoal = data["careerGoal"] ?: "",
                        photoUrl = data["photoUrl"] ?: "",
                        location = data["location"] ?: "",
                        phoneNumber = data["phoneNumber"] ?: "",
                        districtZila = data["districtZila"] ?: ""
                    )
                    _isLoading.value = false
                },
                onFailure = {
                    _isLoading.value = false
                }
            )
        }
    }

    fun updateProfile(updated: UserProfile, onSuccess: () -> Unit, onFailure: () -> Unit) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return
        viewModelScope.launch {
            UserProfileRepository.updateUserProfile(
                data = mapOf(
                    "name" to updated.name,
                    "interests" to updated.interests,
                    "strengths" to updated.strengths,
                    "academicStage" to updated.academicStage,
                    "careerGoal" to updated.careerGoal,
                    "photoUrl" to updated.photoUrl,
                    "location" to updated.location,
                    "phoneNumber" to updated.phoneNumber,
                    "districtZila" to updated.districtZila
                ),
                onSuccess = {
                    _userProfile.value = updated
                    onSuccess()
                },
                onFailure = {
                    onFailure()
                }
            )
        }
    }

    fun setProfilePhoto(url: String) {
        _userProfile.value = _userProfile.value.copy(photoUrl = url)
    }

    fun setLocalProfile(updated: UserProfile) {
        _userProfile.value = updated
    }
}
