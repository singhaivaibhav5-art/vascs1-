package com.example.vascs.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "catalogue_generation_jobs")
data class CatalogueGenerationJobEntity(
    @PrimaryKey val jobId: String,
    val productId: String,
    val sourceImageId: String? = null,
    val sourceImageUri: String? = null,
    val style: String, // BRIDAL, FESTIVE, PARTY_WEAR, TRADITIONAL, OFFICE_WEAR
    val modelId: String? = "model-standard-01",
    val backgroundStyle: String? = "Showroom Studio",
    val pose: String? = "Standing Elegance",
    val resolution: String? = "1024x1536",
    val prompt: String? = null,
    val negativePrompt: String? = null,
    val status: String = "IDLE", // IDLE, QUEUED, PROCESSING, SUCCESS, FAILED, CANCELLED
    val progress: Int = 0,
    val remoteJobId: String? = null,
    val resultImageId: String? = null,
    val resultImageUri: String? = null,
    val errorMessage: String? = null,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)
