package com.example.vascs.data.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "invoice_records",
    indices = [
        Index(value = ["invoiceNumber"]),
        Index(value = ["orderId"]),
        Index(value = ["dealerId"])
    ]
)
data class InvoiceRecordEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val invoiceNumber: String,
    val invoiceType: String = "Tax Invoice",
    val orderId: Long,
    val dealerId: Long,
    val invoiceDate: String,
    val taxableAmount: Double,
    val gstAmount: Double,
    val netAmount: Double
)
