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
            
            Please generate a complete **step-by-step career roadmap** to become a $careerName. Include:
            - Academic requirements (by stage: high school, undergrad, postgrad)
            - Skill development steps
            - Recommended courses or tools
            - Certifications (if any)
            - Internships or job roles at different stages
            - Final goal path
            
            Present the roadmap in a clear TREE STRUCTURE like:
            
            Step 1: Do this
                └─ Then this
                    └─ Next this
            Step 2: ...
            
            Tailor the roadmap for students from Bangladesh where possible.
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
