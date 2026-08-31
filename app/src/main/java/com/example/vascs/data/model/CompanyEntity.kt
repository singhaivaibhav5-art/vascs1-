package com.example.vascs.data.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "companies",
    indices = [
        Index(value = ["companyCode"], unique = true)
    ]
)
data class CompanyEntity(
    @PrimaryKey(autoGenerate = true) val companyId: Long = 0,
    val companyCode: String,
    val companyName: String,
    val ownerName: String,
    val mobile: String,
    val email: String,
    val gstin: String,
    val address: String,
    val status: String = "Active" // Active, Suspended, Expired
)
