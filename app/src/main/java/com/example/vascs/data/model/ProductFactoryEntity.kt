package com.example.vascs.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "product_factory")
data class ProductFactoryEntity(
    @PrimaryKey(autoGenerate = true) val productId: Long = 0,
    val productName: String,
    val category: String,
    val variantsCount: Int,
    val packagingDesign: String,
    val unitCostInr: Double,
    val unitSellingPriceInr: Double,
    val targetMarginPct: Double,
    val positioningStatement: String,
    val marketReadinessPct: Double,
    val timestamp: String = "2026-08-15 04:56"
)
