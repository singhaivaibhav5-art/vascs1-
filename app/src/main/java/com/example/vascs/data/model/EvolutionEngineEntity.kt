package com.example.vascs.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "evolution_engine")
data class EvolutionEngineEntity(
    @PrimaryKey(autoGenerate = true) val evolutionId: Long = 0,
    val agentOrSubsystem: String,
    val evolutionaryCapability: String, // Learn, Adapt, Improve, Expand
    val evolutionScore: Double, // AI Evolution Score (e.g. 99.89)
    val learningIterationsCompleted: Long,
    val emergentBehaviorDiscovered: String,
    val autonomousSelfUpgradeAction: String,
    val timestamp: String = "2026-08-15 04:48"
)
