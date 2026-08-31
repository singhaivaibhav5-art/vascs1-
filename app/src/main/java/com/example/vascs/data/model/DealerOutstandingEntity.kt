package com.example.vascs.data.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "dealer_outstanding",
    indices = [
        Index(value = ["dealerId"])
    ]
)
data class DealerOutstandingEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val dealerId: Long,
    val dealerName: String,
    val totalSales: Double,
    val totalReceived: Double,
    val outstandingAmount: Double,
    val lastUpdated: String
)
