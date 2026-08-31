package com.example.vascs.data.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "receivables",
    indices = [
        Index(value = ["dealerId"])
    ]
)
data class AccountsReceivableEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val dealerId: Long,
    val dealerName: String,
    val invoiceAmount: Double,
    val receivedAmount: Double,
    val pendingAmount: Double,
    val dueDate: String
)
