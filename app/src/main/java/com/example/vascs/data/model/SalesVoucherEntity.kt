package com.example.vascs.data.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "sales_vouchers",
    indices = [
        Index(value = ["invoiceNo"]),
        Index(value = ["partyId"])
    ]
)
data class SalesVoucherEntity(
    @PrimaryKey(autoGenerate = true)
    val voucherId: Long = 0,
    val voucherNo: String,
    val partyId: Long,
    val partyName: String,
    val invoiceNo: String,
    val invoiceDate: String,
    val itemsJson: String = "",
    val amount: Double,
    val cgstAmount: Double = 0.0,
    val sgstAmount: Double = 0.0,
    val igstAmount: Double = 0.0,
    val gstAmount: Double,
    val netAmount: Double,
    val hsnCode: String = "5407",
    val status: String = "PAID" // PAID, UNPAID, PARTIAL
)
