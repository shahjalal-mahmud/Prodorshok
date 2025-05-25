package com.example.prodorshok.ui.screens.profile

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.prodorshok.data.model.UserProfile
import com.example.prodorshok.ui.components.common.CustomOutlinedTextField
import com.example.prodorshok.ui.components.forms.AcademicStageDropdown
import com.example.prodorshok.ui.components.forms.CareerGoalDropdown
import com.example.prodorshok.ui.components.forms.DistrictDropdown
import com.example.prodorshok.ui.components.forms.InterestDropdown
import com.example.prodorshok.ui.components.forms.SkillsDropdown

@Composable
fun ProfileForm(
    userProfile: UserProfile,
    onChange: (UserProfile) -> Unit,
    onSave: () -> Unit,
    showNameAndEmail: Boolean = false
) {
    var phoneError by remember { mutableStateOf<String?>(null) }

    // Convert comma-separated values to list safely
    val selectedSkills = userProfile.strengths?.split(", ")?.filter { it.isNotBlank() } ?: emptyList()
    val selectedInterests = userProfile.interests?.split(", ")?.filter { it.isNotBlank() } ?: emptyList()
    val selectedGoals = userProfile.careerGoal?.split(", ")?.filter { it.isNotBlank() } ?: emptyList()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        if (showNameAndEmail) {
            CustomOutlinedTextField(
                label = "Name",
                value = userProfile.name,
                onValueChange = { onChange(userProfile.copy(name = it)) }
            )
            CustomOutlinedTextField(
                label = "Email",
                value = userProfile.email,
                onValueChange = { onChange(userProfile.copy(email = it)) }
            )
        }

        CustomOutlinedTextField(
            label = "Phone Number",
            value = userProfile.phoneNumber,
            onValueChange = {
                onChange(userProfile.copy(phoneNumber = it))
                phoneError = if (isValidPhoneNumber(it)) null else "Invalid BD phone number"
            },
            isError = phoneError != null,
            errorMessage = phoneError
        )

        // ✅ Interests Dropdown
        InterestDropdown(
            selectedInterests = selectedInterests,
            onInterestSelected = { interest ->
                val updated = selectedInterests.toMutableList().apply { add(interest) }.distinct()
                onChange(userProfile.copy(interests = updated.joinToString(", ")))
            },
            onInterestDeselected = { interest ->
                val updated = selectedInterests.toMutableList().apply { remove(interest) }
                onChange(userProfile.copy(interests = updated.joinToString(", ")))
            }
        )

        // ✅ Skills Dropdown
        SkillsDropdown(
            selectedSkills = selectedSkills,
            onSkillSelected = { skill ->
                val updated = selectedSkills.toMutableList().apply { add(skill) }.distinct()
                onChange(userProfile.copy(strengths = updated.joinToString(", ")))
            },
            onSkillDeselected = { skill ->
                val updated = selectedSkills.toMutableList().apply { remove(skill) }
                onChange(userProfile.copy(strengths = updated.joinToString(", ")))
            }
        )

        // ✅ Academic Stage Dropdown
        AcademicStageDropdown(
            selectedStage = userProfile.academicStage,
            onStageSelected = { selected ->
                onChange(userProfile.copy(academicStage = selected))
            }
        )

        // ✅ Career Goal Dropdown
        CareerGoalDropdown(
            selectedGoals = selectedGoals,
            onGoalSelected = { goal ->
                val updated = selectedGoals.toMutableList().apply { add(goal) }.distinct()
                onChange(userProfile.copy(careerGoal = updated.joinToString(", ")))
            },
            onGoalDeselected = { goal ->
                val updated = selectedGoals.toMutableList().apply { remove(goal) }
                onChange(userProfile.copy(careerGoal = updated.joinToString(", ")))
            }
        )

        // ✅ District Dropdown
        DistrictDropdown(
            selectedDistrict = userProfile.districtZila,
            onDistrictSelected = { selected ->
                onChange(userProfile.copy(districtZila = selected))
            }
        )

        // ✅ Save Button
        Button(
            onClick = { if (phoneError == null) onSave() },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Save")
        }
    }
}

// ✅ Phone Number Validation
fun isValidPhoneNumber(phone: String): Boolean {
    return phone.matches(Regex("^(\\+8801|01)[3-9]\\d{8}$"))
}
