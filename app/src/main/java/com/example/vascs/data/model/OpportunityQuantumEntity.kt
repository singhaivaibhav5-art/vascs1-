package com.example.vascs.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "opportunity_quantum")
data class OpportunityQuantumEntity(
    @PrimaryKey(autoGenerate = true) val opportunityId: Long = 0,
    val detectionType: String, // Hidden Markets, Future Trends, Emerging Industries, New Revenue Sources
    val title: String,
    val opportunityProbabilityScorePct: Double,
    val estimatedEconomicValueBillionUsd: Double,
    val timeToManifestHorizonMonths: Int,
    val strategicReadinessPct: Double,
    val actionDirective: String,
    val timestamp: String = "2026-08-15 04:48"
)
