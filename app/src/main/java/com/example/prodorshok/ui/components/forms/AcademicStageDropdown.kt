package com.example.prodorshok.ui.components.forms

import androidx.compose.runtime.Composable
import com.example.prodorshok.ui.components.common.SingleSelectDropdown

@Composable
fun AcademicStageDropdown(
    selectedStage: String,
    onStageSelected: (String) -> Unit
) {
    val stages = listOf(
        "HSC / College Student", "University Student (Undergrad)", "Recent Graduate",
        "Job Seeker", "Master’s/PhD Student", "Studying Abroad",
        "Diploma / Vocational Student", "Planning to Apply for University", "Taking a Gap Year"
    )

    SingleSelectDropdown(
        label = "Select Study Level",
        options = stages,
        selectedOption = selectedStage,
        onOptionSelected = onStageSelected
    )
}
