package com.example.vascs.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "market_cosmos")
data class MarketCosmosEntity(
    @PrimaryKey(autoGenerate = true) val marketId: Long = 0,
    val marketName: String,
    val consumerTrends: String,
    val demandPattern: String,
    val regionalGrowthPct: Double,
    val industryGrowthPct: Double,
    val opportunityScore: Double, // out of 100
    val marketPotentialBillionUsd: Double,
    val expansionPriority: String // TIER 1 - IMMEDIATE, TIER 2 - STRATEGIC, TIER 3 - SCALING
)
