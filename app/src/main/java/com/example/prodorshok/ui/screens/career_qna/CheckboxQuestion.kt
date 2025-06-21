package com.example.prodorshok.ui.screens.career_qna

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CheckboxQuestion(
    question: String,
    options: List<String>,
    onSubmit: (String) -> Unit
) {
    var selectedOptions by remember { mutableStateOf(setOf<String>()) }

    Column {
        Text(text = question, style = MaterialTheme.typography.bodyLarge)
        Spacer(Modifier.height(8.dp))
        options.forEach { option ->
            val isSelected = option in selectedOptions
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
                    .toggleable(
                        value = isSelected,
                        onValueChange = {
                            selectedOptions = if (isSelected)
                                selectedOptions - option
                            else
                                selectedOptions + option
                        }
                    )
            ) {
                Checkbox(checked = isSelected, onCheckedChange = null)
                Text(option)
            }
        }

        Button(
            onClick = {
                if (selectedOptions.isNotEmpty()) {
                    onSubmit(selectedOptions.joinToString(", "))
                    selectedOptions = emptySet()
                }
            },
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Text("Submit")
        }
    }
}
