package com.example.vascs.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "decision_universe")
data class DecisionUniverseEntity(
    @PrimaryKey(autoGenerate = true) val decisionId: Long = 0,
    val decisionCategory: String, // Investments, Expansion, Pricing, Resources, Growth
    val decisionTitle: String,
    val proposedAction: String,
    val expectedEconomicImpactUsdMillion: Double,
    val decisionAccuracyScore: Double,
    val confidenceIntervalPct: Double,
    val executionState: String, // Autonomous Enacted, Pending Multi-Agent Vote, Staged
    val timestamp: String = "2026-08-16 03:15"
)
