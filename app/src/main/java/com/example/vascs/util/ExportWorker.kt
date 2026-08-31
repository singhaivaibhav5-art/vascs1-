package com.example.vascs.util

import android.content.Context
import com.example.vascs.data.db.ExportQueueDao
import com.example.vascs.data.db.ProductDao
import com.example.vascs.data.model.ExportQueueEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ExportWorker(
    private val context: Context,
    private val exportQueueDao: ExportQueueDao,
    private val productDao: ProductDao
) {

    suspend fun processPendingJobs(): Int = withContext(Dispatchers.IO) {
        val pendingJobs = exportQueueDao.getPendingJobs()
        var processedCount = 0

        for (job in pendingJobs) {
            try {
                // Update status to PROCESSING
                exportQueueDao.updateStatus(job.id, "PROCESSING", 10)

                val product = productDao.getProductById(job.productId)
                if (product == null) {
                    exportQueueDao.updateStatus(job.id, "FAILED", 0, errorMsg = "Product not found: ${job.productId}")
                    continue
                }

                val exportType = try {
                    ExportType.valueOf(job.exportType)
                } catch (e: Exception) {
                    ExportType.WHATSAPP_CARD
                }

                exportQueueDao.updateStatus(job.id, "PROCESSING", 50)

                val outputUri = SocialExportEngine.generateAndSaveExport(
                    context = context,
                    product = product,
                    exportType = exportType,
                    sourceImageUri = job.sourceImageUri
                )

                if (outputUri != null) {
                    exportQueueDao.updateStatus(job.id, "SUCCESS", 100, outputUri = outputUri)
                    processedCount++
                } else {
                    exportQueueDao.updateStatus(job.id, "FAILED", 0, errorMsg = "Failed to render card canvas")
                }
            } catch (e: Exception) {
                exportQueueDao.updateStatus(job.id, "FAILED", 0, errorMsg = e.localizedMessage ?: "Unknown error")
            }
        }

        processedCount
    }
}
