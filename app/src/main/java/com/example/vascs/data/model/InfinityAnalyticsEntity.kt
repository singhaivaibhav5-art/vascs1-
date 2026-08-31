package com.example.vascs.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "infinity_analytics")
data class InfinityAnalyticsEntity(
    @PrimaryKey(autoGenerate = true) val metricId: Long = 0,
    val dimension: String,
    val totalActiveCompanies: Int,
    val totalIndustriesManaged: Int,
    val totalCountriesConnected: Int,
    val globalRevenueTrillionInr: Double,
    val infinityIntelligenceIndex: Double,
    val strategicDirective: String,
    val timestamp: String
)
