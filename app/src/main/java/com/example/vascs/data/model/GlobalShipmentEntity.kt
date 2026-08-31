package com.example.vascs.data.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "global_shipments",
    indices = [
        Index(value = ["trackingNumber"], unique = true)
    ]
)
data class GlobalShipmentEntity(
    @PrimaryKey(autoGenerate = true) val shipmentId: Long = 0,
    val trackingNumber: String,
    val courierPartner: String, // DHL, FedEx, UPS, Aramex
    val originCountry: String,
    val destinationCountry: String,
    val status: String = "Dispatched", // Dispatched, In Customs, Out for Delivery, Delivered
    val estimatedDelivery: String
)
