package com.example.vascs.data.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "bank_book",
    indices = [
        Index(value = ["bankName"])
    ]
)
data class BankBookEntity(
    @PrimaryKey(autoGenerate = true) val bankTxnId: Long = 0,
    val bankName: String,
    val txnDate: String,
    val chequeNumber: String = "",
    val utrNumber: String = "",
    val debitAmount: Double = 0.0,
    val creditAmount: Double = 0.0,
    val balance: Double = 0.0
)
