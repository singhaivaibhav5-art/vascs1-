package com.example.vascs.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "opportunity_generator")
data class OpportunityGeneratorEntity(
    @PrimaryKey(autoGenerate = true) val opportunityId: Long = 0,
    val marketGapTitle: String,
    val consumerProblem: String,
    val futureNeedHorizon: String, // 3-6 Months, 1-2 Years, Decade Shift
    val businessOpportunityConcept: String,
    val potentialRevenueUsdMillion: Double,
    val urgencyScorePct: Double,
    val status: String = "IDENTIFIED",
    val timestamp: String = "2026-08-15 04:56"
)
