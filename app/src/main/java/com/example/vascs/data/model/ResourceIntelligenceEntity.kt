package com.example.vascs.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "resource_intelligence")
data class ResourceIntelligenceEntity(
    @PrimaryKey(autoGenerate = true) val resourceId: Long = 0,
    val resourceCategory: String, // Capital, Inventory, Supply, Labor, Technology
    val resourceName: String,
    val allocatedCapacityUsdMillion: Double,
    val utilizationRatePct: Double,
    val optimizationGainPct: Double,
    val bottleneckRiskLevel: String, // Minimal, Balanced, Critical
    val recommendedActionPlan: String,
    val timestamp: String = "2026-08-16 03:15"
)
