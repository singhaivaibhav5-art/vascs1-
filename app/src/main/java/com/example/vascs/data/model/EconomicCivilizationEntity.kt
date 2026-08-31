package com.example.vascs.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "economic_civilization")
data class EconomicCivilizationEntity(
    @PrimaryKey(autoGenerate = true) val civilizationId: Long = 0,
    val civilizationName: String,
    val economicZone: String, // Global Silk Federation, Indo-Pacific Luxury Consortium, Euro-Atlantic Smart Guild, Pan-African Craft DAO
    val managedCompaniesCount: Int,
    val managedIndustriesCount: Int,
    val totalTradeVolumeBillionUsd: Double,
    val autonomyLevelPct: Double,
    val civilizationStatus: String, // Fully Autonomous, Self-Expanding, Coordinated
    val growthRatePct: Double,
    val timestamp: String = "2026-08-16 03:15"
)
