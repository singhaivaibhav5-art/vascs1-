package com.example.vascs.data.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "revenue_engine",
    indices = [
        Index(value = ["streamName"])
    ]
)
data class RevenueEngineEntity(
    @PrimaryKey(autoGenerate = true) val streamId: Long = 0,
    val streamName: String, // Direct Wholesale, Dealer Network, Global Exports, SaaS Subscriptions
    val currentRevenueInr: Double,
    val profitMarginPct: Double = 38.6,
    val growthRatePct: Double = 42.1,
    val optimizationDirective: String,
    val status: String = "Maximizing Yield"
)
