package com.example.vascs.data.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "dispatch_records",
    indices = [
        Index(value = ["orderId"]),
        Index(value = ["dispatchNumber"]),
        Index(value = ["trackingNumber"])
    ]
)
data class DispatchRecordEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val orderId: Long,
    val dispatchNumber: String,
    val dispatchDate: String,
    val courierName: String,
    val vehicleNumber: String,
    val trackingNumber: String,
    val packedBy: String,
    val dispatchStatus: String,
    val remarks: String = ""
)
