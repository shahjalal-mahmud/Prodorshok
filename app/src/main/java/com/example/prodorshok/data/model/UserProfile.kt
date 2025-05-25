package com.example.prodorshok.data.model

data class UserProfile(
    val name: String = "",
    val email: String = "",
    val photoUrl: String = "",
    val interests: String = "",
    val strengths: String = "",
    val academicStage: String = "",
    val careerGoal: String = "",
    val location: String = "", // Optional, could reuse this
    val phoneNumber: String = "",
    val districtZila: String = ""  // New field
)

