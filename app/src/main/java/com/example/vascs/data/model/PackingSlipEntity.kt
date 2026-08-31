package com.example.vascs.data.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "packing_slips",
    indices = [
        Index(value = ["orderId"]),
        Index(value = ["packingNumber"])
    ]
)
data class PackingSlipEntity(
    @PrimaryKey(autoGenerate = true)
    val packingId: Long = 0,
    val orderId: Long,
    val packingNumber: String,
    val boxNumber: String = "BOX-1",
    val totalBoxes: Int = 1,
    val totalItems: Int,
    val packedBy: String = "",
    val packedDate: String = "",
    val packingDate: String = "",
    val remarks: String = ""
)
