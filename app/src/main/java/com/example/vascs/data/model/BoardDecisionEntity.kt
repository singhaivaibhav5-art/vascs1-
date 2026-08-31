package com.example.vascs.data.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "board_decisions",
    indices = [
        Index(value = ["executiveRole"])
    ]
)
data class BoardDecisionEntity(
    @PrimaryKey(autoGenerate = true) val decisionId: Long = 0,
    val executiveRole: String, // AI CEO, AI CFO, AI COO, AI CMO, AI CTO, AI Sales Director
    val agendaTopic: String, // e.g. Global Logistics Expansion & Automated Capital Deployment
    val decisionVote: String = "Approved (Unanimous)", // Approved, Rejected, Conditional
    val rationale: String,
    val riskScore: Int = 12, // Lower is safer
    val executionPriority: String = "Critical High",
    val timestamp: String
)
