package com.example.prodorshok.viewmodel.career

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.prodorshok.data.repository.OpenRouterRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CareerRoadmapViewModel(app: Application) : AndroidViewModel(app) {
    private val repo = OpenRouterRepository(app.applicationContext)

    private val _roadmapResponse = MutableStateFlow<String?>(null)
    val roadmapResponse: StateFlow<String?> = _roadmapResponse

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun requestRoadmapForCareer(careerName: String) {
        val prompt = """
        You are an expert career advisor.
        
        The user wants to become a "$careerName".
        
        Return a career roadmap as a **compact JSON array**, with each step like this:
        
        {
          "text": "Step title",
          "subSteps": [ ... ]
        }
        
        Only include:
        - "text": short instruction for the step
        - "subSteps": nested child steps (if any)
        
        ✅ Keep responses short and focused. Avoid long explanations.
        ✅ Use simple language (max 20 words per step).
        ✅ Only output the JSON array — no intro, no markdown, no explanation.
        
        Make sure it's valid JSON and fits within 1000 tokens.
        Tailor it for students in Bangladesh.
        """.trimIndent()

        _isLoading.value = true

        viewModelScope.launch {
            try {
                val response = repo.sendPrompt(prompt)
                _roadmapResponse.value = response ?: "⚠️ Could not generate a roadmap."
            } catch (e: Exception) {
                _roadmapResponse.value = "⚠️ An error occurred: ${e.localizedMessage}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}
