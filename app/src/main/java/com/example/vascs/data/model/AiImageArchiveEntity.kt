package com.example.vascs.data.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "ai_image_archive",
    indices = [
        Index(value = ["archiveId"], unique = true),
        Index(value = ["productId"]),
        Index(value = ["sku"]),
        Index(value = ["qrNumber"]),
        Index(value = ["isDeleted"])
    ]
)
data class AiImageArchiveEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val archiveId: String,
    val productId: Long = 0L,
    val sku: String = "",
    val qrNumber: String = "",
    val productName: String = "",
    val versionNumber: Int = 1,
    val imageUri: String,
    val thumbnailUri: String = "",
    val imageSource: String = "AI", // AI, CAMERA, GALLERY, RESIZED, IMPORTED
    val imageType: String = "CATALOGUE_MAIN",
    val prompt: String = "",
    val negativePrompt: String = "",
    val modelName: String = "Gemini / Nano Banana",
    val providerName: String = "NANO_BANANA", // NANO_BANANA, GOOGLE_AI_STUDIO, OOTDIFFUSION, COMFYUI, STABLE_DIFFUSION, MANUAL_IMPORT
    val generationDate: Long = System.currentTimeMillis(),
    val generationTime: String = "",
    val createdBy: String = "Admin System",
    val width: Int = 1080,
    val height: Int = 1080,
    val fileSize: Long = 0L,
    val status: String = "ACTIVE",
    val usageCount: Int = 0,
    val shareCount: Int = 0,
    val downloadCount: Int = 0,
    val coverAppliedCount: Int = 0,
    val isDeleted: Boolean = false,
    val notes: String = ""
)
