package com.example.prodorshok.domain.logic

import android.util.Log
import com.example.prodorshok.domain.model.CareerStep
import org.json.JSONArray

object CareerRoadmapParser {

    fun parse(jsonText: String): List<CareerStep> {
        return try {
            val cleaned = extractValidJsonArray(jsonText)
            val jsonArray = JSONArray(cleaned)
            parseStepsFromJsonArray(jsonArray)
        } catch (e: Exception) {
            Log.e("CareerRoadmapParser", "Parsing error", e)
            listOf(CareerStep("⚠️ Could not parse roadmap. Please try again."))
        }
    }

    private fun extractValidJsonArray(raw: String): String {
        val start = raw.indexOf('[')
        val end = raw.lastIndexOf(']')
        return if (start != -1 && end != -1 && end > start) {
            raw.substring(start, end + 1)
        } else {
            raw // Fallback: try the raw string (may still be invalid)
        }
    }

    private fun parseStepsFromJsonArray(array: JSONArray): List<CareerStep> {
        val steps = mutableListOf<CareerStep>()
        for (i in 0 until array.length()) {
            val obj = array.optJSONObject(i) ?: continue
            val text = obj.optString("text", "Unnamed Step")
            val subSteps = if (obj.has("subSteps") && obj.get("subSteps") is JSONArray) {
                parseStepsFromJsonArray(obj.getJSONArray("subSteps"))
            } else {
                emptyList()
            }
            steps.add(CareerStep(text, subSteps))
        }
        return steps
    }
}
