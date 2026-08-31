package com.example.vascs.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "market_quantum")
data class MarketQuantumEntity(
    @PrimaryKey(autoGenerate = true) val predictionId: Long = 0,
    val marketDimension: String, // Consumer Intent, Market Signals, Trend Acceleration, Demand Shifts
    val sectorOrRegion: String,
    val marketPredictionIndexPct: Double,
    val intentVelocityScore: Double,
    val forecastedDemandSurgeMultiplier: Double,
    val predictiveSignalInsight: String,
    val autoAllocationRule: String,
    val timestamp: String = "2026-08-15 04:48"
)
