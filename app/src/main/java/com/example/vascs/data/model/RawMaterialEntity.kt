package com.example.vascs.data.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "raw_materials",
    indices = [
        Index(value = ["materialCode"], unique = true),
        Index(value = ["materialCategory"])
    ]
)
data class RawMaterialEntity(
    @PrimaryKey(autoGenerate = true) val materialId: Long = 0,
    val materialCode: String,
    val materialName: String,
    val materialCategory: String,
    val unit: String,
    val openingStock: Double,
    val currentStock: Double,
    val minimumStock: Double,
    val purchaseRate: Double,
    val supplierName: String,
    val status: String = "Active"
)
