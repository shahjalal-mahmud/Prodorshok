package com.example.prodorshok.ui.screens.career_roadmap

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.example.prodorshok.domain.model.CareerStep

@Composable
fun ExpandableStepItem(step: CareerStep, level: Int) {
    var expanded by remember { mutableStateOf(true) }

    val icon = getStepIcon(step.text)
    val shape = if (level == 0) MaterialTheme.shapes.medium else RoundedCornerShape(6.dp)
    val elevation = if (level == 0) 6.dp else 2.dp
    val background = if (level == 0) MaterialTheme.colorScheme.surfaceVariant else MaterialTheme.colorScheme.surface

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp, horizontal = if (level == 0) 4.dp else 12.dp)
            .clickable { expanded = !expanded },
        tonalElevation = elevation,
        shadowElevation = elevation,
        shape = shape,
        color = background
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(12.dp)
        ) {
            Icon(icon, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
            Spacer(modifier = Modifier.width(12.dp))
            Text(text = step.text, style = MaterialTheme.typography.bodyLarge)
        }
    }

    AnimatedVisibility(
        visible = expanded,
        enter = fadeIn() + expandVertically()
    ) {
        Column(modifier = Modifier.padding(start = 16.dp)) {
            step.subSteps.forEach {
                ExpandableStepItem(it, level + 1)
            }
        }
    }
}

fun getStepIcon(text: String): ImageVector {
    return when {
        text.contains("internship", true) -> Icons.Default.Work
        text.contains("course", true) || text.contains("learn", true) -> Icons.Default.School
        text.contains("build", true) || text.contains("project", true) -> Icons.Default.Build
        else -> Icons.Default.CheckCircle
    }
}
