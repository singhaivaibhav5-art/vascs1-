package com.example.vascs.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "autonomous_governance_logs")
data class AutonomousGovernanceLogEntity(
    @PrimaryKey(autoGenerate = true) val logId: Long = 0,
    val proposalTitle: String,
    val domain: String, // Capital Allocation, Global Trade, Tariff Optimization, Supply Expansion
    val aiDecisionSummary: String,
    val approvalRatingPct: Double = 99.8,
    val timestamp: String,
    val executionStatus: String = "EXECUTED_AUTONOMOUSLY"
)
