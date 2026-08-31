package com.example.vascs.data.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "ai_decisions",
    indices = [
        Index(value = ["boardTopic"])
    ]
)
data class AiDecisionEntity(
    @PrimaryKey(autoGenerate = true) val decisionId: Long = 0,
    val boardTopic: String,
    val consensusDecision: String,
    val actionPlan: String,
    val riskLevel: String = "Low", // Low, Medium, High
    val createdDate: String
)
