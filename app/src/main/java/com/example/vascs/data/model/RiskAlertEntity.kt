package com.example.vascs.data.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "risk_alerts",
    indices = [
        Index(value = ["riskCategory"])
    ]
)
data class RiskAlertEntity(
    @PrimaryKey(autoGenerate = true) val riskId: Long = 0,
    val riskCategory: String, // Sales Decline, Dealer Churn, Inventory Loss, Payment Risk, Business Slowdown
    val severityLevel: String = "High", // Critical, High, Medium, Low
    val title: String,
    val description: String,
    val mitigationPlan: String,
    val createdDate: String
)
