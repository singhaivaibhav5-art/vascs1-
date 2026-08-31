package com.example.vascs.data.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "tracking_records",
    indices = [
        Index(value = ["dispatchId"]),
        Index(value = ["trackingNumber"])
    ]
)
data class TrackingRecordEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val dispatchId: Long,
    val trackingNumber: String,
    val courierName: String,
    val currentStatus: String,
    val lastUpdated: String
)
