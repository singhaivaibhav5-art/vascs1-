package com.example.vascs.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "future_engine")
data class FutureEngineEntity(
    @PrimaryKey(autoGenerate = true) val scenarioId: Long = 0,
    val futurePathName: String, // Future A, Future B, Future C, Future D, Future E
    val trajectoryDescription: String,
    val probabilityScorePct: Double,
    val revenueProjectionBillionUsd: Double,
    val growthForecastMultiplier: Double,
    val confidenceScorePct: Double,
    val riskFactorScore: Double,
    val strategicRecommendation: String,
    val isBestPath: Boolean = false,
    val timestamp: String = "2026-08-15 04:48"
)
