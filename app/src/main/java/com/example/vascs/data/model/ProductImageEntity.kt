package com.example.vascs.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "product_images")
data class ProductImageEntity(
    @PrimaryKey val id: String,
    val productId: String,
    val uri: String,
    val localPath: String? = null,
    val fileName: String? = null,
    val imageType: String = "PRODUCT", // PRODUCT, PRIMARY, DETAIL, FABRIC, BORDER, PALLU, BLOUSE, CATALOGUE_GENERATED, AI_GENERATED, CAMERA_CAPTURE
    val isPrimary: Boolean = false,
    val sortOrder: Int = 0,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)
