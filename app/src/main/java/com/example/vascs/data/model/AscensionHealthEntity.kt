package com.example.vascs.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ascension_health")
data class AscensionHealthEntity(
    @PrimaryKey(autoGenerate = true) val healthId: Long = 0,
    val dimensionName: String, // Economic Health, Trade Health, Growth Health, Innovation Health, Civilization Health
    val score: Double,
    val targetThreshold: Double = 95.0,
    val status: String, // Optimal, Resilient, Exceptional
    val diagnosticSummary: String,
    val timestamp: String = "2026-08-16 03:15"
)
