package com.example.vascs.data.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "optimization_logs",
    indices = [
        Index(value = ["optimizationArea"])
    ]
)
data class OptimizationLogEntity(
    @PrimaryKey(autoGenerate = true) val logId: Long = 0,
    val optimizationArea: String, // Pricing, Campaign Timing, Dealer Allocation, Inventory Planning, Catalog Quality
    val originalState: String,
    val optimizedState: String,
    val gainPercentage: Double = 14.5,
    val timestamp: String
)
