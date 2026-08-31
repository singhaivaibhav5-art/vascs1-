package com.example.vascs.data.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "fabric_stock",
    indices = [
        Index(value = ["fabricCode"], unique = true),
        Index(value = ["colour"])
    ]
)
data class FabricStockEntity(
    @PrimaryKey(autoGenerate = true) val fabricId: Long = 0,
    val fabricCode: String,
    val fabricName: String,
    val colour: String,
    val gsm: Int,
    val width: Double,
    val meterAvailable: Double,
    val ratePerMeter: Double
)
