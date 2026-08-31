package com.example.vascs.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "innovation_universe")
data class InnovationUniverseEntity(
    @PrimaryKey(autoGenerate = true) val innovationId: Long = 0,
    val innovationTitle: String,
    val innovationType: String, // Breakthrough Technology, Sovereign Patent, Autonomous Economic Model, Bio-Silk Nanotech
    val patentIdentifier: String,
    val economicPotentialUsdMillion: Double,
    val readinessStage: String, // Lab Prototype, Patent Enacted, Scaled Deployment, Global Standard
    val innovationIndex: Double,
    val disruptionFactorPct: Double,
    val timestamp: String = "2026-08-16 03:15"
)
