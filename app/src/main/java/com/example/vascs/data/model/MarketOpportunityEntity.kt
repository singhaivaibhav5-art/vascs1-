package com.example.vascs.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "market_opportunities")
data class MarketOpportunityEntity(
    @PrimaryKey(autoGenerate = true) val opportunityId: Long = 0,
    val title: String,
    val targetIndustry: String,
    val targetRegion: String,
    val estimatedMarketCapInr: Double,
    val entryBarrier: String,
    val expectedRoiMultiplier: Double,
    val strategicActionPlan: String,
    val aiRating: String
)
