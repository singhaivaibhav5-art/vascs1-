package com.example.vascs.data.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "fashion_trends",
    indices = [
        Index(value = ["trendCategory"])
    ]
)
data class FashionTrendEntity(
    @PrimaryKey(autoGenerate = true) val trendId: Long = 0,
    val trendCategory: String, // Color Trends, Fabric Trends, Design Trends, Regional Trends, Season Trends
    val trendName: String, // e.g. Royal Gold Zari Kanjeevaram
    val trajectory: String = "Emerging Trend", // Next Best Seller, Emerging Trend, Declining Trend
    val projectedGrowthPct: Double = 42.5,
    val primaryRegion: String = "Pan-India & NRI Global"
)
