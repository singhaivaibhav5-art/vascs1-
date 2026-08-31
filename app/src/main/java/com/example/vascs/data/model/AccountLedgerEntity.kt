package com.example.vascs.data.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "chart_of_accounts",
    indices = [
        Index(value = ["ledgerCode"], unique = true),
        Index(value = ["ledgerGroup"])
    ]
)
data class AccountLedgerEntity(
    @PrimaryKey(autoGenerate = true) val ledgerId: Long = 0,
    val ledgerCode: String,
    val ledgerName: String,
    val ledgerGroup: String, // Assets, Liabilities, Income, Expenses, Capital, Bank, Cash, Sundry Debtors, Sundry Creditors
    val openingBalance: Double = 0.0,
    val currentBalance: Double = 0.0,
    val status: String = "Active"
)
