package com.example.vascs.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "wealth_engine")
data class WealthEngineEntity(
    @PrimaryKey(autoGenerate = true) val assetId: Long = 0,
    val assetName: String,
    val assetClass: String, // Autonomous Enterprise Equity, Smart Loom Real Estate, Patent Portfolio, Tokenized Yield Reserve
    val profitGeneratedUsdMillion: Double,
    val capitalAllocatedUsdMillion: Double,
    val currentEnterpriseValuationUsdMillion: Double,
    val annualizedRoiPct: Double,
    val wealthHealthScore: Double,
    val timestamp: String = "2026-08-15 04:56"
)
