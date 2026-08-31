package com.example.vascs.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cosmos_analytics")
data class CosmosAnalyticsEntity(
    @PrimaryKey(autoGenerate = true) val analyticsId: Long = 0,
    val dimension: String,
    val metricKey: String,
    val metricValue: Double,
    val metricUnit: String,
    val planetaryBenchmark: Double,
    val anomalyDetected: Boolean = false,
    val cosmosIntelligenceScore: Double = 99.98,
    val timestamp: String = "2026-08-15 03:50"
)
