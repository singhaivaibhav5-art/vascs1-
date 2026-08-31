package com.example.vascs.data.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "supply_chain_ai",
    indices = [
        Index(value = ["logisticsNode"])
    ]
)
data class SupplyChainAiEntity(
    @PrimaryKey(autoGenerate = true) val supplyChainId: Long = 0,
    val logisticsNode: String, // Procurement, Warehousing, Logistics, Distribution
    val efficiencyScorePct: Double = 98.2,
    val costReductionPct: Double = 18.5,
    val speedMetricHrs: Double = 12.0,
    val bottleneckAlert: String = "Zero Bottlenecks Detected",
    val status: String = "Autonomous Optimization Active"
)
