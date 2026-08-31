package com.example.vascs.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "self_evolving_models")
data class SelfEvolvingModelEntity(
    @PrimaryKey(autoGenerate = true) val modelId: Long = 0,
    val modelName: String,
    val coreDomain: String,
    val evolutionaryGeneration: Int = 42,
    val inferenceAccuracyPct: Double = 99.98,
    val autonomousOptimizationsPerHour: Int = 14500,
    val healthStatus: String = "SINGULARITY_REACHED"
)
