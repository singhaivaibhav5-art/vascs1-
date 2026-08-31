package com.example.vascs.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "market_factory")
data class MarketFactoryEntity(
    @PrimaryKey(autoGenerate = true) val marketId: Long = 0,
    val targetMarketName: String,
    val consumerSegments: String,
    val marketStrategy: String,
    val estimatedTamUsdMillion: Double,
    val demandModelType: String,
    val penetrationVelocityScore: Double,
    val timestamp: String = "2026-08-15 04:56"
)
