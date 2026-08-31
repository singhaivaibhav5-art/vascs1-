package com.example.vascs.data.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "predictions",
    indices = [
        Index(value = ["targetDomain"])
    ]
)
data class PredictionEntity(
    @PrimaryKey(autoGenerate = true) val predictionId: Long = 0,
    val targetDomain: String, // Product Demand, Dealer Demand, Season Demand, Customer Demand, Region Demand
    val periodHorizon: String, // 7 Days, 30 Days, 90 Days, 1 Year
    val predictionValue: String,
    val confidence: Double = 95.8,
    val stockActionSuggestion: String,
    val createdDate: String
)
