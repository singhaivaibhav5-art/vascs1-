package com.example.vascs.data.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "expansion_plans",
    indices = [
        Index(value = ["targetRegion"])
    ]
)
data class ExpansionPlanEntity(
    @PrimaryKey(autoGenerate = true) val planId: Long = 0,
    val targetRegion: String, // e.g. North America (USA & Canada), Middle East (UAE & Saudi)
    val expansionType: String = "Exclusive Showrooms & Franchise Network",
    val requiredInvestmentInr: Double = 15000000.0,
    val expectedRevenueInr: Double = 65000000.0,
    val paybackPeriodMonths: Int = 14,
    val aiFeasibilityScore: Int = 98,
    val status: String = "Approved for Launch"
)
