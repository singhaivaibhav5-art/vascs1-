package com.example.vascs.data.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "suppliers",
    indices = [
        Index(value = ["supplierType"])
    ]
)
data class SupplierEntity(
    @PrimaryKey(autoGenerate = true) val supplierId: Long = 0,
    val supplierName: String,
    val supplierType: String, // Fabric Suppliers, Packaging Suppliers, Label Suppliers, Accessory Suppliers
    val location: String,
    val costIndex: String = "Low Cost",
    val qualityRating: Double = 4.8,
    val aiRecommendationScore: Int = 96
)
