package com.example.prodorshok.ui.screens.career_roadmap

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.prodorshok.viewmodel.career.CareerRoadmapViewModel

@Composable
fun CareerRoadmapScreen(viewModel: CareerRoadmapViewModel = viewModel()) {
    val roadmap by viewModel.roadmapResponse.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    var careerInput by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("🎯 Enter a Career Goal", style = MaterialTheme.typography.titleLarge)

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = careerInput,
            onValueChange = { careerInput = it },
            placeholder = { Text("e.g. Data Scientist, UI/UX Designer") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = { viewModel.requestRoadmapForCareer(careerInput) },
            enabled = careerInput.isNotBlank() && !isLoading,
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Generate Roadmap")
        }

        Spacer(modifier = Modifier.height(24.dp))

        if (isLoading) {
            LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
        } else if (!roadmap.isNullOrEmpty()) {
            Text(
                text = roadmap ?: "",
                modifier = Modifier.verticalScroll(rememberScrollState())
            )
        }
    }
}
