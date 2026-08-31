package com.example.vascs.data.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "future_predictions",
    indices = [
        Index(value = ["timeHorizon"])
    ]
)
data class FuturePredictionEntity(
    @PrimaryKey(autoGenerate = true) val predictionId: Long = 0,
    val timeHorizon: String, // 30 Days, 90 Days, 1 Year, 3 Years, 5 Years
    val projectedRevenueInr: Double,
    val projectedProfitInr: Double,
    val predictedDemandUnits: Int = 150000,
    val estimatedMarketSharePct: Double = 18.5,
    val confidenceScore: Int = 96
)
