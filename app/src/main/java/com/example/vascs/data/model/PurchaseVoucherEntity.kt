package com.example.vascs.data.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "purchase_vouchers",
    indices = [
        Index(value = ["voucherNo"]),
        Index(value = ["supplierId"])
    ]
)
data class PurchaseVoucherEntity(
    @PrimaryKey(autoGenerate = true)
    val voucherId: Long = 0,
    val voucherNo: String,
    val supplierId: Long,
    val supplierName: String,
    val billNo: String,
    val billDate: String,
    val itemsJson: String = "",
    val amount: Double,
    val cgstAmount: Double = 0.0,
    val sgstAmount: Double = 0.0,
    val igstAmount: Double = 0.0,
    val gstAmount: Double,
    val netAmount: Double,
    val hsnCode: String = "5407",
    val status: String = "PAID" // PAID, PENDING, UNPAID
)
