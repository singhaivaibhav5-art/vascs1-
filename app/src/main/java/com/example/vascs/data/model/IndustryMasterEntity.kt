package com.example.vascs.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "industry_master")
data class IndustryMasterEntity(
    @PrimaryKey(autoGenerate = true) val industryId: Long = 0,
    val industryName: String,
    val sector: String,
    val marketCapTrillionUsd: Double = 1.2,
    val globalGrowthRatePct: Double = 8.5,
    val riskFactor: String = "Low",
    val automationIndex: Double = 94.2,
    val status: String = "ACTIVE"
)
