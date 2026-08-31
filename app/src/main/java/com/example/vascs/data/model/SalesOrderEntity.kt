package com.example.vascs.data.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "sales_orders",
    indices = [
        Index(value = ["orderNumber"]),
        Index(value = ["dealerId"]),
        Index(value = ["orderStatus"])
    ]
)
data class SalesOrderEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val orderNumber: String,
    val dealerId: Long,
    val dealerName: String,
    val orderDate: String,
    val orderStatus: String,
    val totalQty: Int,
    val totalAmount: Double,
    val gstAmount: Double,
    val netAmount: Double,
    val remarks: String = "",
    val createdDate: String,
    val updatedDate: String
)
