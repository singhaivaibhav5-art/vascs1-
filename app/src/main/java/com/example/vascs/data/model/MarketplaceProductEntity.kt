package com.example.vascs.data.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "marketplace_products",
    indices = [
        Index(value = ["marketplaceType"])
    ]
)
data class MarketplaceProductEntity(
    @PrimaryKey(autoGenerate = true) val productId: Long = 0,
    val title: String,
    val marketplaceType: String, // B2B Marketplace, B2C Marketplace, Export Marketplace, Import Marketplace
    val wholesalePriceInr: Double,
    val minOrderQuantity: Int = 50,
    val sellerName: String,
    val category: String = "Sarees",
    val status: String = "Listed"
)
