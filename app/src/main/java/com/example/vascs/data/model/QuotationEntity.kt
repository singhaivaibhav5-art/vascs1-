package com.example.vascs.data.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "quotations",
    indices = [
        Index(value = ["quotationNo"]),
        Index(value = ["leadId"])
    ]
)
data class QuotationEntity(
    @PrimaryKey(autoGenerate = true)
    val quotationId: Long = 0,
    val quotationNo: String,
    val leadId: Long = 0,
    val customerName: String,
    val mobile: String,
    val productsJson: String, // Store product items as JSON string
    val totalQty: Int,
    val totalAmount: Double,
    val gstAmount: Double,
    val netAmount: Double,
    val validityDate: String,
    val createdDate: String,
    val status: String = "SENT"
)
