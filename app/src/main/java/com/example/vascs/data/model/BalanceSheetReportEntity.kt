package com.example.vascs.data.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "balance_sheet_reports",
    indices = [
        Index(value = ["financialYear"])
    ]
)
data class BalanceSheetReportEntity(
    @PrimaryKey(autoGenerate = true) val reportId: Long = 0,
    val financialYear: String,
    val assets: Double = 0.0,
    val liabilities: Double = 0.0,
    val capital: Double = 0.0,
    val currentAssets: Double = 0.0,
    val currentLiabilities: Double = 0.0
)
