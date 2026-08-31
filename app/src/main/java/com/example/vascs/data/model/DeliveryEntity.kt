package com.example.vascs.data.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "deliveries",
    indices = [
        Index(value = ["orderId"])
    ]
)
data class DeliveryEntity(
    @PrimaryKey(autoGenerate = true)
    val deliveryId: Long = 0,
    val orderId: Long,
    val deliveredDate: String,
    val receivedBy: String,
    val mobile: String,
    val remarks: String = "",
    val proofImageUri: String = ""
)
