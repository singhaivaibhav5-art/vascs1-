package com.example.vascs.data.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "cash_book",
    indices = [
        Index(value = ["voucherNumber"])
    ]
)
data class CashBookEntity(
    @PrimaryKey(autoGenerate = true) val cashTxnId: Long = 0,
    val txnDate: String,
    val voucherNumber: String,
    val particulars: String,
    val debitAmount: Double = 0.0,
    val creditAmount: Double = 0.0,
    val balance: Double = 0.0
)
