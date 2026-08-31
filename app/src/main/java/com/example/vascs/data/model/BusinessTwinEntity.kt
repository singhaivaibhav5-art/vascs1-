package com.example.vascs.data.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "business_twin",
    indices = [
        Index(value = ["scenarioName"])
    ]
)
data class BusinessTwinEntity(
    @PrimaryKey(autoGenerate = true) val twinId: Long = 0,
    val scenarioName: String, // e.g. Price Increase +15%, New USA Branch, Product Expansion
    val simulatedRevenueGrowthPct: Double = 28.5,
    val simulatedProfitMarginPct: Double = 32.4,
    val riskLevel: String = "Low Risk",
    val recommendation: String = "Proceed with Phased Rollout across Tier-1 Cities",
    val simulationTimestamp: String
)
