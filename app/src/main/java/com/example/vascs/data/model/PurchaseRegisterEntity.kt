package com.example.vascs.data.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "purchase_register",
    indices = [
        Index(value = ["purchaseNumber"], unique = true),
        Index(value = ["supplierName"])
    ]
)
data class PurchaseRegisterEntity(
    @PrimaryKey(autoGenerate = true) val purchaseId: Long = 0,
    val purchaseNumber: String,
    val purchaseType: String = "Raw Material", // Raw Material, Fabric, Packing Material, Asset Purchase, Expense Purchase
    val supplierName: String,
    val invoiceNumber: String,
    val invoiceDate: String,
    val taxableAmount: Double,
    val gstAmount: Double,
    val netAmount: Double
)
