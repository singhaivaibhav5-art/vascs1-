package com.example.vascs.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "export_queue")
data class ExportQueueEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val productId: String,
    val sku: String? = null,
    val exportType: String, // WHATSAPP_CARD, INSTAGRAM_POST, INSTAGRAM_STORY, FACEBOOK_POST, FACEBOOK_COVER, TELEGRAM_POST, CATALOGUE_CARD, CUSTOM
    val targetWidth: Int,
    val targetHeight: Int,
    val sourceImageUri: String,
    val outputImageUri: String? = null,
    val status: String = "QUEUED", // QUEUED, PROCESSING, SUCCESS, FAILED, CANCELLED
    val progress: Int = 0,
    val errorMessage: String? = null,
    val createdDate: Long = System.currentTimeMillis(),
    val updatedDate: Long = System.currentTimeMillis()
)
