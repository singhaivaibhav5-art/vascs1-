package com.example.vascs.data.ai

import com.example.vascs.BuildConfig

/**
 * GeminiConfig
 * Centralized configuration for Gemini AI Brain integration in VASCS ULTIMA.
 */
object GeminiConfig {
    const val DEFAULT_MODEL = "gemini-2.5-flash"
    const val PRO_MODEL = "gemini-2.5-pro"
    const val BASE_URL = "https://generativelanguage.googleapis.com/v1beta/models/"

    private var customApiKey: String? = null

    fun getApiKey(): String {
        if (!customApiKey.isNullOrBlank()) {
            return customApiKey!!
        }
        return try {
            // Read from BuildConfig if present
            val field = BuildConfig::class.java.getField("GEMINI_API_KEY")
            field.get(null) as? String ?: ""
        } catch (_: Exception) {
            ""
        }
    }

    fun setCustomApiKey(key: String) {
        customApiKey = key.trim()
    }

    fun isConfigured(): Boolean {
        return getApiKey().isNotBlank()
    }

    data class GenerationSettings(
        val temperature: Float = 0.4f,
        val topK: Int = 40,
        val topP: Float = 0.95f,
        val maxOutputTokens: Int = 2048,
        val responseMimeType: String = "application/json"
    )
}
