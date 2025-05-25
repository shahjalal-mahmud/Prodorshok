package com.example.prodorshok.ui.components.forms

import androidx.compose.runtime.Composable
import com.example.prodorshok.ui.components.common.SingleSelectDropdown

@Composable
fun DistrictDropdown(
    selectedDistrict: String,
    onDistrictSelected: (String) -> Unit
) {
    val districts = listOf(
        "Barishal", "Chattogram", "Dhaka", "Khulna",
        "Mymensingh", "Rajshahi", "Rangpur", "Sylhet"
    )

    SingleSelectDropdown(
        label = "Select District",
        options = districts,
        selectedOption = selectedDistrict,
        onOptionSelected = onDistrictSelected
    )
}
