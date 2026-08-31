package com.example.vascs.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "receipt_vouchers")
data class ReceiptVoucherEntity(
    @PrimaryKey(autoGenerate = true)
    val receiptId: Long = 0,
    val receiptNo: String,
    val partyId: Long,
    val partyName: String,
    val receiptDate: String,
    val amount: Double,
    val paymentMode: String, // CASH, BANK, UPI, CHEQUE
    val referenceNo: String = "",
    val remarks: String = ""
)
