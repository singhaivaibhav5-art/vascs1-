package com.example.vascs.data.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "business_twin_models",
    indices = [
        Index(value = ["scenarioName"])
    ]
)
data class BusinessTwinModelEntity(
    @PrimaryKey(autoGenerate = true) val twinId: Long = 0,
    val scenarioName: String, // e.g. "Increase Prices 10%", "Launch New Saree Collection", "Expand Dealer Network Tier-2"
    val expectedSalesInr: Double,
    val expectedProfitInr: Double,
    val riskLevel: String = "Low", // Low, Medium, High
    val growthMultiplier: Double = 1.25,
    val simulatedDate: String
)
