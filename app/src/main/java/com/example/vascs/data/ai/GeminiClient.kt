package com.example.vascs.data.ai

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL

/**
 * GeminiClient
 * Low-level HTTP client executing REST calls to Google's Gemini API endpoints.
 */
class GeminiClient(
    private val model: String = GeminiConfig.DEFAULT_MODEL
) {

    data class GeminiResponse(
        val isSuccessful: Boolean,
        val text: String?,
        val rawJson: String?,
        val errorMessage: String? = null,
        val latencyMs: Long = 0L,
        val promptTokens: Int = 0,
        val candidateTokens: Int = 0
    )

    suspend fun generateContent(
        systemInstruction: String?,
        userPrompt: String,
        settings: GeminiConfig.GenerationSettings = GeminiConfig.GenerationSettings()
    ): GeminiResponse = withContext(Dispatchers.IO) {
        val startTime = System.currentTimeMillis()
        val apiKey = GeminiConfig.getApiKey()

        if (apiKey.isBlank()) {
            return@withContext GeminiResponse(
                isSuccessful = false,
                text = null,
                rawJson = null,
                errorMessage = "Gemini API key is not configured. Falling back to local neural heuristics engine.",
                latencyMs = System.currentTimeMillis() - startTime
            )
        }

        val endpointUrl = "${GeminiConfig.BASE_URL}$model:generateContent?key=$apiKey"

        try {
            val url = URL(endpointUrl)
            val connection = (url.openConnection() as HttpURLConnection).apply {
                requestMethod = "POST"
                setRequestProperty("Content-Type", "application/json; charset=UTF-8")
                setRequestProperty("Accept", "application/json")
                connectTimeout = 20000
                readTimeout = 30000
                doInput = true
                doOutput = true
            }

            // Build request payload
            val rootJson = JSONObject()

            // System Instruction if provided
            if (!systemInstruction.isNullOrBlank()) {
                val sysPart = JSONObject().put("text", systemInstruction)
                val sysPartsArray = JSONArray().put(sysPart)
                val sysObj = JSONObject().put("parts", sysPartsArray)
                rootJson.put("system_instruction", sysObj)
            }

            // Contents
            val userPart = JSONObject().put("text", userPrompt)
            val userPartsArray = JSONArray().put(userPart)
            val contentObj = JSONObject().apply {
                put("role", "user")
                put("parts", userPartsArray)
            }
            rootJson.put("contents", JSONArray().put(contentObj))

            // Generation config
            val genConfig = JSONObject().apply {
                put("temperature", settings.temperature.toDouble())
                put("topK", settings.topK)
                put("topP", settings.topP.toDouble())
                put("maxOutputTokens", settings.maxOutputTokens)
                if (settings.responseMimeType.isNotBlank()) {
                    put("responseMimeType", settings.responseMimeType)
                }
            }
            rootJson.put("generationConfig", genConfig)

            // Send payload
            OutputStreamWriter(connection.outputStream, "UTF-8").use { writer ->
                writer.write(rootJson.toString())
                writer.flush()
            }

            val responseCode = connection.responseCode
            val isSuccess = responseCode in 200..299

            val inputStream = if (isSuccess) connection.inputStream else connection.errorStream
            val responseBody = BufferedReader(InputStreamReader(inputStream, "UTF-8")).use { it.readText() }
            val latency = System.currentTimeMillis() - startTime

            if (isSuccess) {
                val parsed = JSONObject(responseBody)
                val candidates = parsed.optJSONArray("candidates")
                var extractedText: String? = null
                if (candidates != null && candidates.length() > 0) {
                    val candidate = candidates.getJSONObject(0)
                    val content = candidate.optJSONObject("content")
                    val parts = content?.optJSONArray("parts")
                    if (parts != null && parts.length() > 0) {
                        extractedText = parts.getJSONObject(0).optString("text")
                    }
                }

                val usage = parsed.optJSONObject("usageMetadata")
                val promptTokens = usage?.optInt("promptTokenCount") ?: 0
                val candTokens = usage?.optInt("candidatesTokenCount") ?: 0

                GeminiResponse(
                    isSuccessful = true,
                    text = extractedText,
                    rawJson = responseBody,
                    latencyMs = latency,
                    promptTokens = promptTokens,
                    candidateTokens = candTokens
                )
            } else {
                GeminiResponse(
                    isSuccessful = false,
                    text = null,
                    rawJson = responseBody,
                    errorMessage = "Gemini API HTTP Error ($responseCode): $responseBody",
                    latencyMs = latency
                )
            }
        } catch (e: Exception) {
            GeminiResponse(
                isSuccessful = false,
                text = null,
                rawJson = null,
                errorMessage = "Network or Execution Exception: ${e.localizedMessage}",
                latencyMs = System.currentTimeMillis() - startTime
            )
        }
    }
}
