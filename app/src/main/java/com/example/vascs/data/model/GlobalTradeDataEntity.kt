package com.example.vascs.data.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "global_trade_data",
    indices = [
        Index(value = ["targetCountry"])
    ]
)
data class GlobalTradeDataEntity(
    @PrimaryKey(autoGenerate = true) val tradeDataId: Long = 0,
    val targetCountry: String,
    val tradeRoute: String,
    val demandScore: Int = 95,
    val tariffPct: Double = 2.5,
    val optimalCategory: String,
    val projectedVolumePcs: Int = 50000,
    val capturedDate: String
)
