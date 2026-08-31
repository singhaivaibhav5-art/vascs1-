package com.example.vascs.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "planetary_trade_routes")
data class PlanetaryTradeRouteEntity(
    @PrimaryKey(autoGenerate = true) val routeId: Long = 0,
    val routeCode: String,
    val originRegion: String,
    val destinationRegion: String,
    val tradeVolumeBillionUsd: Double,
    val tariffStatus: String = "ZERO_TARIFF_FTA",
    val efficiencyScorePct: Double = 99.4,
    val transitHours: Int = 18,
    val autonomousLogisticsStatus: String = "ACTIVE_CONVOY"
)
