package com.example.vascs.data.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "billing_records",
    indices = [
        Index(value = ["companyId"])
    ]
)
data class BillingRecordEntity(
    @PrimaryKey(autoGenerate = true) val billingId: Long = 0,
    val companyId: Long,
    val invoiceNumber: String,
    val amount: Double,
    val paymentStatus: String = "Paid", // Paid, Pending, Failed, Overdue
    val billingDate: String,
    val dueDate: String
)
