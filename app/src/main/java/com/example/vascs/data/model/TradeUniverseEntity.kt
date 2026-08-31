package com.example.vascs.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "trade_universe")
data class TradeUniverseEntity(
    @PrimaryKey(autoGenerate = true) val routeId: Long = 0,
    val originRegion: String,
    val destinationMarket: String,
    val connectedIndustries: String,
    val activeBusinessesCount: Int,
    val tradeThroughputUsdMillion: Double,
    val tradeEfficiencyScore: Double,
    val tariffOptimizationPct: Double,
    val routeHealthStatus: String, // Frictionless, Hyper-Optimized, Flowing
    val timestamp: String = "2026-08-16 03:15"
)
