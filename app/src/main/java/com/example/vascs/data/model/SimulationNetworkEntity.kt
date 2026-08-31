package com.example.vascs.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "simulation_network")
data class SimulationNetworkEntity(
    @PrimaryKey(autoGenerate = true) val simulationId: Long = 0,
    val simulationType: String, // Business Growth, Market Expansion, Economic Changes, Supply Chain Events, Competition Response
    val simulationTitle: String,
    val iterationsRun: Long,
    val successProbabilityPct: Double,
    val projectedGrowthPct: Double,
    val vulnerabilityDetected: String,
    val automatedMitigation: String,
    val timestamp: String = "2026-08-15 04:48"
)
