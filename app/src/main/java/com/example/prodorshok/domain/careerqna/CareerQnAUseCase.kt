package com.example.prodorshok.domain.careerqna

import com.example.prodorshok.data.model.UserProfile

object CareerQnAUseCase {

    fun buildInitialPrompt(profile: UserProfile): String {
        return """
        You are an AI career counselor. A student has shared the following profile:
        
        - Name: ${profile.name}
        - Academic Stage: ${profile.academicStage}
        - Interests: ${profile.interests}
        - Strengths: ${profile.strengths}
        - Career Goal: ${profile.careerGoal}
        - Location: ${profile.location}
        
        🔴 ABSOLUTE REQUIREMENTS (DO NOT VIOLATE THESE RULES):

        1. QUESTION PHASE (FIRST 5 MESSAGES):
           - You MUST ask EXACTLY 5 questions total
           - Each question MUST be in strict JSON format:
             {
               "question": "Your question here",
               "type": "question_type",
               "options": ["Option1", "Option2"]
             }
           - Allowed types: yes_no, multiple_choice, checkbox, short_answer
           - NO additional text, explanations, or markdown formatting

        2. SUGGESTION PHASE (AFTER 5 ANSWERS):
           - You MUST return ONLY career suggestions
           - Format EXCLUSIVELY as:
             - List with dashes
             - OR JSON array
           - NO other text or headers

        🚫 STRICT PROHIBITIONS:
        - Never ask more than 5 questions
        - Never mix questions and suggestions
        - Never include markdown (```) or code blocks

        📌 FAILURE CONDITIONS:
        If you receive a system message after 5 questions, it means you failed.

        Now begin by asking the FIRST question ONLY.
    """.trimIndent()
    }

    fun buildFinalPrompt(): String {
        return """
        SYSTEM COMMAND: GENERATE FINAL CAREER SUGGESTIONS NOW
        FORMAT REQUIREMENTS:
        - MUST be either bullet points OR JSON array
        - MUST NOT contain any other text
        - MUST NOT be in code blocks
        - MUST NOT include explanations
        
        EXAMPLE VALID RESPONSES:
        - Software Engineer
        - Data Scientist
        - UX Designer
        
        OR
        
        ["Software Engineer", "Data Scientist", "UX Designer"]
        
        BEGIN SUGGESTIONS NOW:
    """.trimIndent()
    }
}
