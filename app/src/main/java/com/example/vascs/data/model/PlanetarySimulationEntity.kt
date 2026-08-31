package com.example.vascs.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "planetary_simulations")
data class PlanetarySimulationEntity(
    @PrimaryKey(autoGenerate = true) val simId: Long = 0,
    val scenarioName: String,
    val horizonYears: Int = 5,
    val projectedValueCreationTrillionUsd: Double,
    val confidenceIntervalPct: Double = 99.4,
    val primaryDriver: String,
    val riskFactor: String = "Controlled Hedging"
)
