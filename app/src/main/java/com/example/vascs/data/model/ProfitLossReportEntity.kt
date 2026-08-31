package com.example.vascs.data.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "profit_loss_reports",
    indices = [
        Index(value = ["period"])
    ]
)
data class ProfitLossReportEntity(
    @PrimaryKey(autoGenerate = true) val reportId: Long = 0,
    val period: String,
    val sales: Double = 0.0,
    val purchase: Double = 0.0,
    val grossProfit: Double = 0.0,
    val expenses: Double = 0.0,
    val netProfit: Double = 0.0
)
