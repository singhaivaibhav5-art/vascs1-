package com.example.vascs.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "product_batches")
data class ProductBatchEntity(
    @PrimaryKey val id: String,
    val batchNumber: String,
    val batchName: String,
    val category: String = "Sarees",
    val brand: String = "VASCS",
    val description: String = "",
    val productIdsJson: String = "[]",
    val status: String = "DRAFT", // DRAFT, ACTIVE, COMPLETED, PROCESSING, PARTIAL, FAILED
    val totalProducts: Int = 0,
    val completedProducts: Int = 0,
    val pendingProducts: Int = 0,
    val failedProducts: Int = 0,
    val createdAt: String,
    val updatedAt: String
)
