package com.example.vascs.data.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "optimization_history",
    indices = [
        Index(value = ["optimizationDomain"])
    ]
)
data class OptimizationHistoryEntity(
    @PrimaryKey(autoGenerate = true) val historyId: Long = 0,
    val optimizationDomain: String, // Dynamic Pricing, Inventory Flow, Marketing ROI, Dealer Network
    val initialMetricValue: String,
    val optimizedMetricValue: String,
    val improvementPercentage: Double = 24.8,
    val status: String = "Active & Continuous",
    val appliedDate: String
)
