package com.example.prodorshok.data.preferences

import android.content.Context

// OnboardingPreference.kt
object OnboardingPreference {
    fun hasCompletedOnboarding(context: Context): Boolean {
        val prefs = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        return prefs.getBoolean("onboarding_completed", false)
    }

    fun setOnboardingCompleted(context: Context, completed: Boolean) {
        val prefs = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        prefs.edit().putBoolean("onboarding_completed", completed).apply()
    }
}
