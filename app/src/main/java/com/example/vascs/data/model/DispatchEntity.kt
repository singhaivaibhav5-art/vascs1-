package com.example.vascs.data.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "dispatches",
    indices = [
        Index(value = ["orderId"]),
        Index(value = ["dispatchNumber"]),
        Index(value = ["lrNumber"])
    ]
)
data class DispatchEntity(
    @PrimaryKey(autoGenerate = true)
    val dispatchId: Long = 0,
    val orderId: Long,
    val dispatchNumber: String,
    val transportName: String,
    val lrNumber: String,
    val vehicleNumber: String,
    val dispatchDate: String,
    val expectedDeliveryDate: String,
    val status: String = "DISPATCHED"
)
