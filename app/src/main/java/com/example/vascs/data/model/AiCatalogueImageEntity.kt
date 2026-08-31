package com.example.vascs.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ai_catalogue_images")
data class AiCatalogueImageEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val productId: Long,
    val sku: String,
    val qrNumber: String,
    val productName: String,
    val imageUri: String,
    val imageType: String,
    val imageSource: String,
    val createdDate: Long,
    val updatedDate: Long,
    val status: String,
    val width: Int,
    val height: Int,
    val isPrimary: Boolean = false,
    val notes: String = ""
)
