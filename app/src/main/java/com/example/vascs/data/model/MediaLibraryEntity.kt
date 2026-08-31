package com.example.vascs.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "media_library")
data class MediaLibraryEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val productId: String? = null,
    val sku: String? = null,
    val qrNumber: String? = null,
    val imageUri: String,
    val imageSource: String, // "AI", "CAMERA", "GALLERY", "RESIZED"
    val imageType: String, // "ORIGINAL", "AI_CATALOGUE", "RESIZED", "SOCIAL_EXPORT"
    val createdDate: Long = System.currentTimeMillis(),
    val updatedDate: Long = System.currentTimeMillis(),
    val width: Int = 0,
    val height: Int = 0,
    val isPrimary: Boolean = false,
    val status: String = "ACTIVE",
    val notes: String? = null
)
