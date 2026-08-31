package com.example.vascs.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "global_economy")
data class GlobalEconomyEntity(
    @PrimaryKey(autoGenerate = true) val economyId: Long = 0,
    val indicatorName: String,
    val valueStr: String,
    val trendDirection: String,
    val inflationRatePct: Double,
    val interestRatePct: Double,
    val currencyPairVolatility: String,
    val globalTradeTrend: String,
    val aiEconomicForecast: String,
    val lastUpdated: String
)
