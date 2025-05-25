package com.example.prodorshok.ui.components.profile

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.prodorshok.data.model.UserProfile
import com.example.prodorshok.ui.components.common.ProfileField
import com.example.prodorshok.ui.screens.profile.ProfileEditForm

@Composable
fun ProfileContent(
    userProfile: UserProfile,
    isEditing: Boolean,
    onEditToggle: () -> Unit,
    onProfileChange: (UserProfile) -> Unit,
    onSave: () -> Unit,
    onPhotoClick: (String) -> Unit,
    paddingValues: PaddingValues
) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            onPhotoClick(it.toString())
            Toast.makeText(context, "Profile picture updated", Toast.LENGTH_SHORT).show()
        }
    }

    Column(
        modifier = Modifier
            .padding(paddingValues)
            .padding(24.dp)
            .fillMaxSize()
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ProfileImageSection(userProfile.photoUrl) { launcher.launch("image/*") }

        Spacer(modifier = Modifier.height(16.dp))
        Text(userProfile.name, style = MaterialTheme.typography.headlineSmall)
        Text(userProfile.email, style = MaterialTheme.typography.bodyMedium)
        Spacer(modifier = Modifier.height(24.dp))

        if (!isEditing) {
            ProfileDisplaySection(userProfile, onEditToggle)
        } else {
            ProfileEditForm(
                userProfile = userProfile,
                onProfileChange = onProfileChange,
                onSave = onSave
            )
        }
    }
}

@Composable
private fun ProfileImageSection(photoUrl: String?, onClick: () -> Unit) {
    Box(contentAlignment = Alignment.Center) {
        if (photoUrl.isNullOrEmpty()) {
            Icon(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = "Default Profile",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .size(110.dp)
                    .clip(CircleShape)
                    .clickable { onClick() }
            )
        } else {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(photoUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = "Profile Picture",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(110.dp)
                    .clip(CircleShape)
                    .clickable { onClick() }
            )
        }
        Icon(
            imageVector = Icons.Default.Edit,
            contentDescription = "Edit Photo",
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .size(24.dp)
                .background(Color.White, CircleShape)
                .padding(2.dp)
        )
    }
}

@Composable
private fun ProfileDisplaySection(userProfile: UserProfile, onEditToggle: () -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        ProfileField(icon = Icons.Default.Email, label = "Email", value = userProfile.email)
        ProfileField(icon = Icons.Default.Phone, label = "Phone Number", value = userProfile.phoneNumber)
        ProfileField(icon = Icons.Default.Star, label = "Interests", value = userProfile.interests)
        ProfileField(icon = Icons.Default.Bolt, label = "Strengths", value = userProfile.strengths)
        ProfileField(icon = Icons.Default.School, label = "Academic Stage", value = userProfile.academicStage)
        ProfileField(icon = Icons.Default.Flag, label = "Career Goal", value = userProfile.careerGoal)
        ProfileField(icon = Icons.Default.LocationOn, label = "District/Zila", value = userProfile.districtZila)

        Spacer(modifier = Modifier.height(20.dp))
        Button(onClick = onEditToggle) {
            Icon(Icons.Default.Edit, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Edit Profile")
        }
    }
}
