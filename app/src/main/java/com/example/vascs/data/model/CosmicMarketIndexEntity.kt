package com.example.vascs.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cosmic_market_indices")
data class CosmicMarketIndexEntity(
    @PrimaryKey(autoGenerate = true) val indexId: Long = 0,
    val indexName: String,
    val tickerSymbol: String,
    val currentValue: Double,
    val change24hPct: Double,
    val globalWeightPct: Double,
    val marketTrend: String = "BULLISH_EXPANSION"
)
