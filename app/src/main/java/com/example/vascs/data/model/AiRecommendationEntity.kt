package com.example.vascs.data.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "ai_recommendations",
    indices = [
        Index(value = ["category"])
    ]
)
data class AiRecommendationEntity(
    @PrimaryKey(autoGenerate = true) val recommendationId: Long = 0,
    val category: String = "Dealer Network", // Growth, Risk, Cost, Expansion, Dealer Network
    val title: String = "",
    val impactScore: Int = 85,
    val actionText: String = "",
    val status: String = "Open", // Open, Applied, Dismissed
    // VASCS AI Brain Dealer Recommendation Extensions
    val targetLocation: String = "",
    val performanceTier: String = "ALL", // TOP, EXPANSION, RECOVERY
    val topDealersJson: String = "",
    val expansionDealersJson: String = "",
    val recoveryDealersJson: String = "",
    val strategicActionPlan: String = "",
    val projectedRevenueImpactBillionInr: Double = 0.0,
    val createdAt: Long = System.currentTimeMillis()
)
