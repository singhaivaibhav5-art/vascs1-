package com.example.vascs.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "economy_network")
data class EconomyNetworkEntity(
    @PrimaryKey(autoGenerate = true) val economyId: Long = 0,
    val economyName: String,
    val economyScope: String, // Local, Regional, National, Global, Virtual
    val activeEntitiesCount: Int,
    val totalGdpBillionUsd: Double,
    val growthRateYoYPct: Double,
    val autonomyLevelPct: Double,
    val networkInterconnectednessScore: Double,
    val currencyRegime: String,
    val timestamp: String = "2026-08-16 03:30"
)
