package com.example.vascs.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "trade_networks")
data class TradeNetworksEntity(
    @PrimaryKey(autoGenerate = true) val networkId: Long = 0,
    val networkName: String,
    val tradeCorridor: String,
    val globalImportsBillionUsd: Double,
    val globalExportsBillionUsd: Double,
    val tradeDependencies: String, // Key raw materials, silk yarn, zari bullion, compute
    val bestOpportunity: String,
    val bestTradeRoute: String,
    val bestTradePartner: String,
    val efficiencyPct: Double = 99.8,
    val status: String = "ACTIVE_ZERO_TARIFF"
)
