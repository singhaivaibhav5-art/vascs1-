package com.example.vascs.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "universal_marketplace")
data class UniversalMarketplaceEntity(
    @PrimaryKey(autoGenerate = true) val itemId: Long = 0,
    val itemName: String,
    val industry: String,
    val productType: String, // Physical, Digital, Service, Subscription, Bundle
    val sellerName: String,
    val basePriceInr: Double,
    val targetAudience: String, // B2B, B2C, D2C, Global Trade
    val crossBorderEligible: Boolean = true,
    val stockOrCapacity: String,
    val aiDemandRating: Double
)
