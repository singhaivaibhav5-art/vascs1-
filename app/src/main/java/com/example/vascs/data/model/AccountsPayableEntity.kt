package com.example.vascs.data.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "payables",
    indices = [
        Index(value = ["supplierId"])
    ]
)
data class AccountsPayableEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val supplierId: Long,
    val supplierName: String,
    val billAmount: Double,
    val paidAmount: Double,
    val pendingAmount: Double,
    val dueDate: String
)
