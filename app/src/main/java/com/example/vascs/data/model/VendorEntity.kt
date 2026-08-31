package com.example.vascs.data.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "vendors",
    indices = [
        Index(value = ["vendorCode"], unique = true)
    ]
)
data class VendorEntity(
    @PrimaryKey(autoGenerate = true) val vendorId: Long = 0,
    val vendorCode: String,
    val vendorName: String,
    val contactPerson: String,
    val mobile: String,
    val email: String = "",
    val materialType: String = "Yarn & Fabric",
    val status: String = "Active",
    val performanceScore: Double = 95.0
)
