package com.example.vascs.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "risk_quantum")
data class RiskQuantumEntity(
    @PrimaryKey(autoGenerate = true) val riskId: Long = 0,
    val riskCategory: String, // Business Risk, Market Risk, Economic Risk, Supply Risk
    val riskName: String,
    val probabilityPct: Double,
    val severityScorePct: Double,
    val potentialFinancialImpactMillionUsd: Double,
    val earlyWarningDetectionTrigger: String,
    val quantumAutomatedCountermeasure: String,
    val status: String = "PREEMPTIVELY_NEUTRALIZED",
    val timestamp: String = "2026-08-15 04:48"
)
