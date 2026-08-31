package com.example.vascs.data.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "reputation_scores",
    indices = [
        Index(value = ["entityType"], unique = true)
    ]
)
data class ReputationScoreEntity(
    @PrimaryKey(autoGenerate = true) val scoreId: Long = 0,
    val entityType: String, // Manufacturer Score, Dealer Score, Supplier Score, Customer Score
    val entityName: String,
    val overallScore: Double = 98.4,
    val orderFulfillmentRate: Double = 99.1,
    val paymentTimelinessRate: Double = 97.8,
    val reviewRating: Double = 4.9
)
