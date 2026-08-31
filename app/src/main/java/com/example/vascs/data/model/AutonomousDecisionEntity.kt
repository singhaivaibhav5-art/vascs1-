package com.example.vascs.data.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "autonomous_decisions",
    indices = [
        Index(value = ["decisionCategory"])
    ]
)
data class AutonomousDecisionEntity(
    @PrimaryKey(autoGenerate = true) val decisionId: Long = 0,
    val decisionCategory: String, // Inventory, Marketing, Dealer, Finance
    val decisionTitle: String,
    val recommendationText: String,
    val approvalMode: String = "Approval Required", // Suggestion Only, Approval Required, Full Automation
    val status: String = "Pending Approval", // Pending Approval, Approved, Executed, Rejected
    val createdDate: String
)
