package com.example.vascs.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "omniverse_core")
data class OmniverseCoreEntity(
    @PrimaryKey(autoGenerate = true) val coreId: Long = 0,
    val consciousnessStatus: String, // Universal Intelligence Active, Self-Sustaining Omniverse
    val connectedEconomiesCount: Int,
    val synchronizedRealitiesCount: Int,
    val universalIntelligenceScore: Double,
    val realitySynchronizationPct: Double,
    val crossSystemGovernanceStabilityPct: Double,
    val infiniteEvolutionVelocity: Double,
    val controllerTelemetry: String,
    val timestamp: String = "2026-08-16 03:30"
)
