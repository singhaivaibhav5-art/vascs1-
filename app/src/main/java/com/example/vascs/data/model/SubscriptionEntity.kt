package com.example.vascs.data.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "subscriptions",
    indices = [
        Index(value = ["companyId"])
    ]
)
data class SubscriptionEntity(
    @PrimaryKey(autoGenerate = true) val subscriptionId: Long = 0,
    val companyId: Long,
    val planName: String, // Starter, Professional, Business, Enterprise
    val maxUsers: Int = 10,
    val maxProducts: Int = 5000,
    val maxBranches: Int = 5,
    val startDate: String,
    val endDate: String,
    val status: String = "Active" // Active, Expired, Cancelled
)
