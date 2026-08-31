package com.example.vascs.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cosmos_telemetry")
data class CosmosTelemetryEntity(
    @PrimaryKey(autoGenerate = true) val metricId: Long = 0,
    val metricKey: String,
    val planetaryMetricValue: String,
    val unit: String,
    val status: String = "OPTIMAL_SINGULARITY",
    val lastSyncTimestamp: String
)
