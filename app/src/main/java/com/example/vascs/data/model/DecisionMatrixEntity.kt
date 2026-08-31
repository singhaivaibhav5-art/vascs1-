package com.example.vascs.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "decision_matrix")
data class DecisionMatrixEntity(
    @PrimaryKey(autoGenerate = true) val matrixId: Long = 0,
    val decisionTopic: String,
    val riskScore: Double, // 0 - 100
    val rewardScore: Double, // 0 - 100
    val timeToExecuteMonths: Double,
    val capitalRequiredMillionUsd: Double,
    val probabilityOfSuccessPct: Double,
    val compositeEfficiencyScore: Double,
    val bestDecisionRecommendation: String,
    val status: String = "OPTIMIZED_READY",
    val timestamp: String = "2026-08-15 04:48"
)
