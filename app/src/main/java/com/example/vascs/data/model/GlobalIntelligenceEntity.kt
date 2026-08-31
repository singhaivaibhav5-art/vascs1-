package com.example.vascs.data.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "global_intelligence",
    indices = [
        Index(value = ["regionCountry"])
    ]
)
data class GlobalIntelligenceEntity(
    @PrimaryKey(autoGenerate = true) val intelligenceId: Long = 0,
    val regionCountry: String, // e.g. USA, UAE, UK, Singapore, Australia
    val marketPotentialScore: Int = 96,
    val recommendedCategory: String = "Bridal & Silk Sarees",
    val exportOpportunityInr: Double = 35000000.0,
    val tariffRiskLevel: String = "Low Risk",
    val capturedDate: String
)
