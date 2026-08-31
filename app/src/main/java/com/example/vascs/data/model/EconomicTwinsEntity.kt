package com.example.vascs.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "economic_twins")
data class EconomicTwinsEntity(
    @PrimaryKey(autoGenerate = true) val twinId: Long = 0,
    val twinType: String, // Country Twin, Industry Twin, Trade Twin, Economy Twin
    val entityName: String,
    val simulationHorizonYears: Int = 5,
    val forecastedGrowthRatePct: Double,
    val futureSimulationSummary: String,
    val economicForecastTrillionUsd: Double,
    val riskPredictionRating: String = "AAA+ Resilient",
    val accuracyConfidencePct: Double = 99.7
)
