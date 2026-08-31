package com.example.vascs.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "market_matrix")
data class MarketMatrixEntity(
    @PrimaryKey(autoGenerate = true) val marketId: Long = 0,
    val marketName: String,
    val geographicRegion: String,
    val aggregateDemandIndex: Double,
    val supplyCapacityPct: Double,
    val consumerSentimentScore: Double,
    val marketSignalSummary: String,
    val emergingOpportunitiesCount: Int,
    val marketEfficiencyPct: Double,
    val timestamp: String = "2026-08-16 03:30"
)
