package com.example.vascs.data.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "payment_records",
    indices = [
        Index(value = ["dealerId"]),
        Index(value = ["orderId"]),
        Index(value = ["paymentDate"])
    ]
)
data class PaymentRecordEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val orderId: Long,
    val dealerId: Long,
    val paymentDate: String,
    val paymentMode: String,
    val receivedAmount: Double,
    val pendingAmount: Double,
    val referenceNumber: String = ""
)
