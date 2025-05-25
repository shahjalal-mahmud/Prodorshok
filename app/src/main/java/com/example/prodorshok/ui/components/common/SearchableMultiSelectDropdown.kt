package com.example.prodorshok.ui.components.common

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchableMultiSelectDropdown(
    modifier: Modifier = Modifier,
    label: String,
    options: List<String>,
    selectedItems: List<String>,
    onItemSelected: (String) -> Unit,
    onItemDeselected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var searchText by remember { mutableStateOf("") }

    val filteredOptions = remember(searchText, options) {
        if (searchText.isBlank()) options
        else options.filter { it.contains(searchText, ignoreCase = true) }
    }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = modifier
    ) {
        TextField(
            value = searchText,
            onValueChange = {
                searchText = it
                expanded = true
            },
            label = { Text(label) },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth(),
            placeholder = {
                if (selectedItems.isNotEmpty()) {
                    Text(selectedItems.joinToString(", "))
                }
            },
            singleLine = true,
            colors = ExposedDropdownMenuDefaults.textFieldColors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                disabledContainerColor = Color.White,
                focusedIndicatorColor = Color(0xFF007DFF),
                unfocusedIndicatorColor = Color(0xFF007DFF)
            ),
            shape = RoundedCornerShape(8.dp)
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            filteredOptions.forEach { item ->
                DropdownMenuItem(
                    text = { Text(item) },
                    onClick = {
                        if (selectedItems.contains(item)) {
                            onItemDeselected(item)
                        } else {
                            onItemSelected(item)
                        }
                        // Reset search
                        searchText = ""
                        expanded = false
                    },
                    trailingIcon = {
                        if (selectedItems.contains(item)) {
                            Text("✓")
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}
