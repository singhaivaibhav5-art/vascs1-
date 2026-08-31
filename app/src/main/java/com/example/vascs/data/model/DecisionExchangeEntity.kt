package com.example.vascs.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "decision_exchange")
data class DecisionExchangeEntity(
    @PrimaryKey(autoGenerate = true) val decisionId: Long = 0,
    val decisionType: String, // Best Practices, Strategies, Forecasts, Recommendations
    val originatorAiRole: String, // AI CEO, AI CFO, AI COO, AI CTO, AI CMO, AI Strategy Director
    val topicTitle: String,
    val executiveSummary: String,
    val recommendationAction: String,
    val expectedRoiPct: Double = 44.5,
    val adoptionRatingPct: Double = 100.0,
    val timestamp: String = "2026-08-15 04:40"
)
