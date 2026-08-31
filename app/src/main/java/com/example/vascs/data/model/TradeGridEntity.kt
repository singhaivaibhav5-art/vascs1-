package com.example.vascs.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "trade_grid")
data class TradeGridEntity(
    @PrimaryKey(autoGenerate = true) val gridId: Long = 0,
    val tradeNodeTitle: String,
    val nodeTier: String, // Manufacturer, Supplier, Distributor, Retailer, Consumer
    val connectedEndpointsCount: Int,
    val volumeThroughputMillionUsd: Double,
    val frictionLagMs: Long,
    val tradeEfficiencyScore: Double,
    val tariffOptimizationPct: Double,
    val gridHealthStatus: String,
    val timestamp: String = "2026-08-16 03:30"
)
