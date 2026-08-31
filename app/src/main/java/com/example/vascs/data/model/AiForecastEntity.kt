package com.example.vascs.data.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "ai_forecasts",
    indices = [
        Index(value = ["forecastType"]),
        Index(value = ["category"])
    ]
)
data class AiForecastEntity(
    @PrimaryKey(autoGenerate = true) val forecastId: Long = 0,
    val forecastType: String = "Demand", // Sales, Inventory, Dealer, Customer, Revenue, Demand
    val period: String = "Q3-Q4 2026 Festive Season", // 7 Days, 30 Days, 90 Days, 1 Year
    val predictedValue: String = "",
    val confidenceScore: Double = 94.5,
    val generatedDate: String = "",
    // VASCS AI Brain Demand Forecast Extensions
    val category: String = "",
    val season: String = "",
    val salesHistorySummary: String = "",
    val demandPrediction: String = "", // High Demand, Steady Growth, Seasonal Surge, Moderate
    val reorderQuantity: Int = 0,
    val growthTrend: String = "", // +18% QoQ, +35% Festive Surge
    val growthPercentage: Double = 0.0,
    val stockoutRiskPct: Double = 0.0,
    val aiRationale: String = "",
    val createdAt: Long = System.currentTimeMillis()
)
