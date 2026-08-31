package com.example.vascs.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "quantum_health")
data class QuantumHealthEntity(
    @PrimaryKey(autoGenerate = true) val healthId: Long = 0,
    val businessHealthScore: Double,
    val marketHealthScore: Double,
    val aiHealthScore: Double,
    val economicHealthScore: Double,
    val growthHealthScore: Double,
    val quantumHealthIndex: Double,
    val quantumIntelligenceIndex: Double,
    val systemStatusSummary: String = "PREDICTIVE_EQUILIBRIUM_PEAK",
    val timestamp: String = "2026-08-15 04:48"
)
