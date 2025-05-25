package com.example.prodorshok.ui.screens.profile

import androidx.compose.runtime.Composable
import com.example.prodorshok.data.model.UserProfile

@Composable
fun ProfileEditForm(
    userProfile: UserProfile,
    onProfileChange: (UserProfile) -> Unit,
    onSave: () -> Unit
) {
    ProfileForm(
        userProfile = userProfile,
        onChange = onProfileChange,
        onSave = onSave,
        showNameAndEmail = true
    )
}

