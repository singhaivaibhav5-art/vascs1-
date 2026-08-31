package com.example.vascs.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "payment_vouchers")
data class PaymentVoucherEntity(
    @PrimaryKey(autoGenerate = true)
    val paymentId: Long = 0,
    val paymentNo: String,
    val partyId: Long,
    val partyName: String,
    val paymentDate: String,
    val amount: Double,
    val paymentMode: String, // CASH, BANK, UPI, CHEQUE
    val referenceNo: String = "",
    val remarks: String = ""
)
