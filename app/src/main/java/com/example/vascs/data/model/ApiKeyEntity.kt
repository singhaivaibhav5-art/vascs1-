package com.example.vascs.data.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "api_keys",
    indices = [
        Index(value = ["companyId"])
    ]
)
data class ApiKeyEntity(
    @PrimaryKey(autoGenerate = true) val keyId: Long = 0,
    val companyId: Long,
    val apiKey: String,
    val clientName: String,
    val rateLimit: Int = 1000,
    val status: String = "Active",
    val createdDate: String
)
