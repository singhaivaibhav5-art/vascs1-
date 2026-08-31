package com.example.vascs.data.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "dealers",
    indices = [
        Index(value = ["dealerId"], unique = true),
        Index(value = ["firmName"]),
        Index(value = ["mobile"]),
        Index(value = ["city"]),
        Index(value = ["dealerType"]),
        Index(value = ["status"])
    ]
)
data class DealerEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val dealerId: String,
    val dealerName: String,
    val firmName: String,
    val mobile: String,
    val whatsapp: String = "",
    val email: String = "",
    val city: String,
    val state: String = "Gujarat",
    val gstNumber: String = "",
    val address: String = "",
    val status: String = "ACTIVE", // ACTIVE, INACTIVE, BLOCKED, PENDING
    val creditLimit: Double = 100000.0,
    val dealerType: String = "Retailer", // Retailer, Wholesaler, Distributor, Agent, Online Seller
    val createdDate: Long = System.currentTimeMillis()
)
