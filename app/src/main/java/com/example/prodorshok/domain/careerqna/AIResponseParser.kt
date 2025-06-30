package com.example.prodorshok.domain.careerqna

import android.util.Log
import com.example.prodorshok.ui.screens.career_qna.QuestionType
import org.json.JSONArray
import org.json.JSONObject

object AIResponseParser {

    sealed class ParseResult {
        data class Question(val questionType: QuestionType) : ParseResult()
        data class Suggestions(val suggestions: List<String>) : ParseResult()
        object Invalid : ParseResult()
    }

    fun parseResponse(response: String, isSuggestionPhase: Boolean): ParseResult {
        return if (isSuggestionPhase) {
            parseSuggestions(response)
        } else {
            parseQuestion(response)?.let { ParseResult.Question(it) } ?: ParseResult.Invalid
        }
    }

    private fun parseQuestion(jsonString: String): QuestionType? {
        return try {
            val cleanJson = cleanJsonString(jsonString)
            val json = JSONObject(cleanJson)

            val question = json.getString("question")
            val type = json.getString("type").lowercase()
            val options = if (json.has("options")) {
                val array = json.getJSONArray("options")
                List(array.length()) { array.getString(it) }
            } else emptyList()

            when (type) {
                "yes_no", "yesno" -> QuestionType.YesNo(question)
                "multiple_choice", "multiple" -> QuestionType.MultipleChoice(question, options)
                "checkbox", "multiple_select" -> QuestionType.Checkbox(question, options)
                "short_answer", "short" -> QuestionType.ShortAnswer(question)
                else -> null
            }
        } catch (e: Exception) {
            Log.e("AIResponseParser", "Failed to parse question: $jsonString", e)
            null
        }
    }

    private fun parseSuggestions(response: String): ParseResult {
        return try {
            val cleanResponse = cleanJsonString(response)

            // Try parsing as JSON array first
            try {
                val jsonArray = JSONArray(cleanResponse)
                val suggestions = List(jsonArray.length()) { jsonArray.getString(it) }
                return ParseResult.Suggestions(suggestions)
            } catch (e: Exception) {
                // Not a JSON array, try bullet point format
            }

            // Try parsing bullet point format
            val bulletPointSuggestions = cleanResponse.split("\n")
                .map { it.trim() }
                .filter { it.startsWith("- ") }
                .map { it.removePrefix("- ").trim() }
                .filter { it.isNotBlank() }

            if (bulletPointSuggestions.isNotEmpty()) {
                return ParseResult.Suggestions(bulletPointSuggestions)
            }

            // Fallback - treat each non-empty line as suggestion
            val lineSuggestions = cleanResponse.split("\n")
                .map { it.trim() }
                .filter { it.isNotBlank() }

            if (lineSuggestions.isNotEmpty()) {
                return ParseResult.Suggestions(lineSuggestions)
            }

            ParseResult.Invalid
        } catch (e: Exception) {
            Log.e("AIResponseParser", "Failed to parse suggestions: $response", e)
            ParseResult.Invalid
        }
    }

    private fun cleanJsonString(input: String): String {
        return input.trim()
            .removePrefix("```json")
            .removePrefix("```")
            .removeSuffix("```")
            .trim()
    }
}