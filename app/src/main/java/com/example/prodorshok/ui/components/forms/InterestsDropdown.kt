package com.example.prodorshok.ui.components.forms

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import com.example.prodorshok.ui.components.common.SearchableMultiSelectDropdown

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InterestDropdown(
    selectedInterests: List<String>,
    onInterestSelected: (String) -> Unit,
    onInterestDeselected: (String) -> Unit
) {
    val interestOptions = listOf(
        "Tech & Programming", "Creative Design", "Business & Startups",
        "Marketing & Social Media", "Health & Medicine",
        "Studying Abroad", "Government Jobs", "YouTube / Content Creation",
        "Data Science & AI", "Freelancing", "Research & Higher Studies",
        "Public Speaking / Leadership"
    )

    SearchableMultiSelectDropdown(
        label = "Select Interests",
        options = interestOptions,
        selectedItems = selectedInterests,
        onItemSelected = { onInterestSelected(it) },
        onItemDeselected = { onInterestDeselected(it) }
    )
}