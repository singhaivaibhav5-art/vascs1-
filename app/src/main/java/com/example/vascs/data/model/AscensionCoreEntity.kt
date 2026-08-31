package com.example.vascs.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ascension_core")
data class AscensionCoreEntity(
    @PrimaryKey(autoGenerate = true) val coreId: Long = 0,
    val governanceStatus: String, // Autonomous Universe Active, Self-Balancing Macro Economy
    val civilizationCount: Int,
    val coordinatedEconomiesCount: Int,
    val globalResourceEfficiencyPct: Double,
    val universeStabilityIndex: Double,
    val activeEconomicPoliciesCount: Int,
    val growthMultiplier: Double,
    val controllerTelemetry: String,
    val timestamp: String = "2026-08-16 03:15"
)
