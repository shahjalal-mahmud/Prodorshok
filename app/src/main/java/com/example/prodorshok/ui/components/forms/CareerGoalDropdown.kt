package com.example.prodorshok.ui.components.forms

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import com.example.prodorshok.ui.components.common.SearchableMultiSelectDropdown

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CareerGoalDropdown(
    selectedGoals: List<String>,
    onGoalSelected: (String) -> Unit,
    onGoalDeselected: (String) -> Unit
) {
    val GoalOptions = listOf(
        "Programming (C, Java, Python)", "Web Development (HTML, CSS, JS)", "App Development",
        "UI/UX Design (Figma, Adobe XD)", "Graphics Design (Photoshop, Illustrator)",
        "Data Analysis (Excel, SQL, Python)", "Digital Marketing", "Video Editing",
        "Communication/Public Speaking", "Content Writing", "Cybersecurity Basics",
        "Research & Report Writing", "Business / Entrepreneurship", "Finance & Accounting Basics",
        "Biology / Chemistry / Medical Studies"
    )

    SearchableMultiSelectDropdown(
        label = "Select Goals",
        options = GoalOptions,
        selectedItems = selectedGoals,
        onItemSelected = { onGoalSelected(it) },
        onItemDeselected = { onGoalDeselected(it) }
    )
}