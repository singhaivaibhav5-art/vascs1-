package com.example.vascs.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "nexus_analytics")
data class NexusAnalyticsEntity(
    @PrimaryKey(autoGenerate = true) val analyticsId: Long = 0,
    val dimension: String,
    val metricKey: String,
    val metricValue: Double,
    val metricUnit: String,
    val planetaryBenchmark: Double,
    val nexusIntelligenceIndex: Double = 99.99,
    val timestamp: String = "2026-08-15 04:40"
)
