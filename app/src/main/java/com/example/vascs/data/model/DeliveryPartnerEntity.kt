package com.example.vascs.data.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "delivery_partners",
    indices = [
        Index(value = ["partnerCode"], unique = true)
    ]
)
data class DeliveryPartnerEntity(
    @PrimaryKey(autoGenerate = true) val partnerId: Long = 0,
    val partnerCode: String,
    val partnerName: String,
    val mobile: String,
    val vehicleNumber: String = "",
    val activeDeliveriesCount: Int = 0,
    val status: String = "Available"
)
