package com.example.vascs.data.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "ledgers",
    indices = [
        Index(value = ["accountName"]),
        Index(value = ["partyId"])
    ]
)
data class LedgerEntity(
    @PrimaryKey(autoGenerate = true)
    val ledgerId: Long = 0,
    val partyId: Long = 0,
    val accountName: String, // Sales, Purchase, GST Output, GST Input, Cash, Bank, Expenses, Income, Party Name
    val transactionDate: String,
    val voucherType: String, // SALES, PURCHASE, RECEIPT, PAYMENT, JOURNAL, CONTRA
    val referenceNo: String,
    val debitAmount: Double = 0.0,
    val creditAmount: Double = 0.0,
    val balance: Double = 0.0,
    val narration: String = ""
)
