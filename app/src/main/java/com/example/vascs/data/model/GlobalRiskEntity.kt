package com.example.vascs.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "global_risk")
data class GlobalRiskEntity(
    @PrimaryKey(autoGenerate = true) val riskId: Long = 0,
    val regionOrDomain: String,
    val economicRiskScore: Double, // 0 - 100
    val politicalRiskScore: Double, // 0 - 100
    val supplyRiskScore: Double, // 0 - 100
    val marketRiskScore: Double, // 0 - 100
    val currencyRiskScore: Double, // 0 - 100
    val globalRiskIndex: Double, // 0 - 100 (Overall Risk)
    val mitigationAction: String,
    val riskRating: String = "LOW_RISK_OPTIMAL" // LOW_RISK_OPTIMAL, MONITORED, HEDGED
)
