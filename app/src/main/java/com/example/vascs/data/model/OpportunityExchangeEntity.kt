package com.example.vascs.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "opportunity_exchange")
data class OpportunityExchangeEntity(
    @PrimaryKey(autoGenerate = true) val opportunityId: Long = 0,
    val opportunityCategory: String, // New Markets, New Products, New Industries, New Revenue Streams
    val title: String,
    val description: String,
    val potentialValueBillionUsd: Double,
    val opportunityRank: Int = 1,
    val confidenceScorePct: Double = 99.2,
    val executionReadinessScore: Double = 98.4,
    val strategicImpact: String
)
