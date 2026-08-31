package com.example.vascs.data.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "media_command_center",
    indices = [
        Index(value = ["mediaId"], unique = true),
        Index(value = ["productId"]),
        Index(value = ["sku"]),
        Index(value = ["qrNumber"]),
        Index(value = ["mediaType"]),
        Index(value = ["isArchived"]),
        Index(value = ["isDeleted"])
    ]
)
data class MediaCommandCenterEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val mediaId: String,
    val productId: Long = 0L,
    val productName: String = "",
    val sku: String = "",
    val qrNumber: String = "",
    val mediaType: String = "ORIGINAL", // ORIGINAL, CAMERA, GALLERY, AI, RESIZED, EXPORT, ARCHIVE
    val mediaSource: String = "MANUAL", // MANUAL, CAMERA, GALLERY, NANO_BANANA, GOOGLE_AI_STUDIO, OOTDIFFUSION, COMFYUI, IMPORT
    val versionNumber: Int = 1,
    val imageUri: String,
    val thumbnailUri: String = "",
    val createdDate: Long = System.currentTimeMillis(),
    val updatedDate: Long = System.currentTimeMillis(),
    val status: String = "ACTIVE",
    val width: Int = 1080,
    val height: Int = 1080,
    val fileSize: Long = 0L,
    val isPrimary: Boolean = false,
    val isArchived: Boolean = false,
    val isDeleted: Boolean = false,
    val shareCount: Int = 0,
    val downloadCount: Int = 0,
    val viewCount: Int = 0,
    val notes: String = ""
)
