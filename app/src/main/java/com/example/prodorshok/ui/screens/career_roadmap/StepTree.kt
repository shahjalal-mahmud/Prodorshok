package com.example.prodorshok.ui.screens.career_roadmap

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.prodorshok.domain.model.CareerStep

@Composable
fun StepTree(steps: List<CareerStep>, level: Int = 0) {
    Column(modifier = Modifier.padding(start = (level * 12).dp)) {
        steps.forEach { step ->
            ExpandableStepItem(step, level)
        }
    }
}
