package com.example.vascs.data.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "sales_order_items",
    indices = [
        Index(value = ["orderId"]),
        Index(value = ["productId"]),
        Index(value = ["sku"])
    ]
)
data class SalesOrderItemEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val orderId: Long,
    val productId: Long,
    val sku: String,
    val qrNumber: String = "",
    val productName: String,
    val quantity: Int,
    val rate: Double,
    val gstPercent: Double,
    val amount: Double
)
