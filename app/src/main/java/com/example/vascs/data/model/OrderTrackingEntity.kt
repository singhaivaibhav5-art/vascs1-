package com.example.vascs.data.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "order_tracking",
    indices = [
        Index(value = ["orderId"])
    ]
)
data class OrderTrackingEntity(
    @PrimaryKey(autoGenerate = true)
    val trackingId: Long = 0,
    val orderId: Long,
    val status: String,
    val message: String,
    val createdDate: String
)
