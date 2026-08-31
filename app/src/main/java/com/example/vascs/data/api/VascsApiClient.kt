package com.example.vascs.data.api

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.example.vascs.data.model.CatalogueGenerationJobEntity
import com.example.vascs.util.AiGenerationRequest
import com.example.vascs.util.ImageStorageManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL
import java.util.UUID

sealed class NetworkResult<out T> {
    data class Success<out T>(val data: T) : NetworkResult<T>()
    data class Error(val message: String, val code: Int? = null) : NetworkResult<Nothing>()
}

class VascsApiClient(private val context: Context) {

    // Cloud development server URL
    private var baseUrl: String = "https://ais-dev-7dn5pscte7moh3f6spnzaq-764875657870.asia-southeast1.run.app"

    fun isNetworkAvailable(): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
            ?: return true
        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }

    suspend fun submitGenerationJob(request: AiGenerationRequest): NetworkResult<CatalogueGenerationJobEntity> = withContext(Dispatchers.IO) {
        if (!isNetworkAvailable()) {
            return@withContext NetworkResult.Error("NO_NETWORK: No internet connection available.")
        }

        try {
            val base64Image = ImageStorageManager.encodeUriToBase64(context, request.sourceImageUri)

            val jsonPayload = JSONObject().apply {
                put("productId", request.productId)
                put("sourceImageUri", request.sourceImageUri)
                if (!base64Image.isNullOrBlank()) {
                    put("sourceImageBase64", base64Image)
                }
                put("style", request.style.name)
                put("modelId", request.modelId)
                put("backgroundStyle", request.backgroundStyle)
                put("pose", request.pose)
                put("resolution", request.resolution)
                put("prompt", request.customPrompt)
                put("negativePrompt", request.customNegativePrompt)
            }

            val url = URL("$baseUrl/api/v1/catalogue/generate")
            val conn = (url.openConnection() as HttpURLConnection).apply {
                requestMethod = "POST"
                setRequestProperty("Content-Type", "application/json")
                setRequestProperty("Accept", "application/json")
                doOutput = true
                connectTimeout = 15000
                readTimeout = 15000
            }

            val writer = OutputStreamWriter(conn.outputStream)
            writer.write(jsonPayload.toString())
            writer.flush()
            writer.close()

            val statusCode = conn.responseCode
            val stream = if (statusCode in 200..299) conn.inputStream else conn.errorStream
            val responseText = BufferedReader(InputStreamReader(stream)).use { it.readText() }
            conn.disconnect()

            if (statusCode in 200..299) {
                val jsonRes = JSONObject(responseText)
                val remoteJobId = jsonRes.optString("jobId")
                val localJobId = "job-${UUID.randomUUID()}"

                val initialJob = CatalogueGenerationJobEntity(
                    jobId = localJobId,
                    productId = request.productId,
                    sourceImageUri = request.sourceImageUri,
                    style = request.style.name,
                    modelId = request.modelId,
                    backgroundStyle = request.backgroundStyle,
                    pose = request.pose,
                    resolution = request.resolution,
                    prompt = request.customPrompt,
                    negativePrompt = request.customNegativePrompt,
                    status = "QUEUED",
                    progress = 10,
                    remoteJobId = remoteJobId,
                    createdAt = System.currentTimeMillis(),
                    updatedAt = System.currentTimeMillis()
                )
                return@withContext NetworkResult.Success(initialJob)
            } else {
                val errMsg = parseErrorMsg(responseText, statusCode)
                return@withContext NetworkResult.Error("HTTP $statusCode: $errMsg", statusCode)
            }
        } catch (e: Exception) {
            return@withContext NetworkResult.Error("Network error: ${e.localizedMessage ?: "Failed connecting to AI Backend server."}")
        }
    }

    suspend fun pollJobStatus(jobId: String, currentJob: CatalogueGenerationJobEntity): NetworkResult<CatalogueGenerationJobEntity> = withContext(Dispatchers.IO) {
        val remoteId = currentJob.remoteJobId ?: currentJob.jobId
        try {
            val url = URL("$baseUrl/api/v1/catalogue/jobs/$remoteId")
            val conn = (url.openConnection() as HttpURLConnection).apply {
                requestMethod = "GET"
                setRequestProperty("Accept", "application/json")
                connectTimeout = 10000
                readTimeout = 10000
            }

            val statusCode = conn.responseCode
            val stream = if (statusCode in 200..299) conn.inputStream else conn.errorStream
            val responseText = BufferedReader(InputStreamReader(stream)).use { it.readText() }
            conn.disconnect()

            if (statusCode in 200..299) {
                val json = JSONObject(responseText)
                val status = json.optString("status", "PROCESSING")
                val progress = json.optInt("progress", 50)
                val remoteResultUri = json.optString("resultImageUri", null)
                val errorMessage = json.optString("errorMessage", null)

                if (status == "SUCCESS" && !remoteResultUri.isNullOrBlank()) {
                    val downloadedBytes = downloadImageBytes(remoteResultUri)
                    val localUri = if (downloadedBytes != null) {
                        ImageStorageManager.saveDownloadedImage(context, downloadedBytes, currentJob.productId)
                    } else remoteResultUri

                    val completedJob = currentJob.copy(
                        status = "SUCCESS",
                        progress = 100,
                        resultImageUri = localUri,
                        errorMessage = null,
                        updatedAt = System.currentTimeMillis()
                    )
                    return@withContext NetworkResult.Success(completedJob)
                } else if (status == "FAILED") {
                    val failedJob = currentJob.copy(
                        status = "FAILED",
                        progress = 0,
                        errorMessage = errorMessage ?: "REAL AI GENERATION BLOCKED: AI Provider generation failed.",
                        updatedAt = System.currentTimeMillis()
                    )
                    return@withContext NetworkResult.Success(failedJob)
                } else {
                    val updatingJob = currentJob.copy(
                        status = status,
                        progress = progress,
                        errorMessage = errorMessage,
                        updatedAt = System.currentTimeMillis()
                    )
                    return@withContext NetworkResult.Success(updatingJob)
                }
            } else {
                return@withContext NetworkResult.Error("Status polling failed with HTTP $statusCode")
            }
        } catch (e: Exception) {
            return@withContext NetworkResult.Error("Polling error: ${e.localizedMessage}")
        }
    }

    suspend fun cancelJob(remoteJobId: String): Boolean = withContext(Dispatchers.IO) {
        try {
            val url = URL("$baseUrl/api/v1/catalogue/jobs/$remoteJobId/cancel")
            val conn = (url.openConnection() as HttpURLConnection).apply {
                requestMethod = "POST"
                connectTimeout = 5000
                readTimeout = 5000
            }
            val res = conn.responseCode in 200..299
            conn.disconnect()
            res
        } catch (e: Exception) {
            false
        }
    }

    private fun downloadImageBytes(imageUrlStr: String): ByteArray? {
        return try {
            val url = URL(imageUrlStr)
            val conn = url.openConnection() as HttpURLConnection
            conn.connectTimeout = 15000
            conn.readTimeout = 15000
            val bytes = conn.inputStream.readBytes()
            conn.disconnect()
            bytes
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    private fun parseErrorMsg(responseText: String, statusCode: Int): String {
        return try {
            val json = JSONObject(responseText)
            json.optString("errorMessage", json.optString("error", "Server returned HTTP $statusCode"))
        } catch (e: Exception) {
            "Server returned HTTP $statusCode"
        }
    }
}
