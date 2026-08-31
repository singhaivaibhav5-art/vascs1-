package com.example.vascs.data.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "dealer_orders",
    indices = [
        Index(value = ["orderId"], unique = true),
        Index(value = ["dealerId"]),
        Index(value = ["productId"]),
        Index(value = ["status"])
    ]
)
data class DealerOrderEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val orderId: String,
    val dealerId: String,
    val dealerName: String = "",
    val productId: Long,
    val productName: String = "",
    val qty: Int = 1,
    val rate: Double = 0.0,
    val amount: Double = 0.0,
    val status: String = "Pending", // Pending, Approved, Packed, Dispatched, Delivered, Cancelled
    val createdDate: Long = System.currentTimeMillis(),
    val notes: String = ""
)
