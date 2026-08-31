package com.example.vascs.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sovereign_reserves")
data class SovereignReserveEntity(
    @PrimaryKey(autoGenerate = true) val reserveId: Long = 0,
    val reserveName: String,
    val assetClass: String, // Gold Zari Standard, Multi-Currency SDR, Carbon Token, AI Compute Units
    val totalReserveValueUsd: Double,
    val allocationPercentage: Double,
    val hedgeMultiplier: Double = 3.8,
    val riskRating: String = "AAA+ Risk-Free"
)
