package com.example.vascs.data.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "market_intelligence",
    indices = [
        Index(value = ["trendType"])
    ]
)
data class MarketIntelligenceEntity(
    @PrimaryKey(autoGenerate = true) val intelligenceId: Long = 0,
    val trendType: String, // Fashion Trends, Saree Trends, Color Trends, Season Trends, Competitor Trends
    val trendTitle: String,
    val impactAssessment: String,
    val recommendedAction: String, // New Products, New Markets, New Dealers, New Campaigns
    val capturedDate: String
)
