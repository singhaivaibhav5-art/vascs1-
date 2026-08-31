package com.example.vascs.data.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "customers",
    indices = [
        Index(value = ["mobile"], unique = true)
    ]
)
data class CustomerEntity(
    @PrimaryKey(autoGenerate = true) val customerId: Long = 0,
    val customerName: String,
    val mobile: String,
    val email: String = "",
    val address: String = "",
    val city: String = "",
    val loyaltyPoints: Int = 0,
    val status: String = "Active"
)
