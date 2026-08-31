package com.example.vascs.data.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "competitor_intelligence",
    indices = [
        Index(value = ["competitorName"])
    ]
)
data class CompetitorIntelligenceEntity(
    @PrimaryKey(autoGenerate = true) val competitorId: Long = 0,
    val competitorName: String,
    val primaryRegion: String,
    val marketSharePct: Double = 12.4,
    val pricingIndex: String = "Premium Tier",
    val competitiveGapOpportunity: String,
    val aiThreatLevel: String = "Low Risk"
)
