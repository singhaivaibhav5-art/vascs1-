package com.example.vascs.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "prosperity_engine")
data class ProsperityEngineEntity(
    @PrimaryKey(autoGenerate = true) val prosperityId: Long = 0,
    val economicDomain: String, // Global Artisan Wealth, Sovereign Reserve Equity, Autonomous Tech Pool, Smart Loom Guild Fund
    val cumulativeWealthUsdMillion: Double,
    val annualGrowthRatePct: Double,
    val allocatedCapitalUsdMillion: Double,
    val generatedEconomicValueUsdMillion: Double,
    val prosperityIndex: Double,
    val equityDistributionGiniIndex: Double,
    val timestamp: String = "2026-08-16 03:15"
)
