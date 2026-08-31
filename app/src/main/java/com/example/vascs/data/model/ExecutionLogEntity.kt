package com.example.vascs.data.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "execution_logs",
    indices = [
        Index(value = ["targetChannel"])
    ]
)
data class ExecutionLogEntity(
    @PrimaryKey(autoGenerate = true) val logId: Long = 0,
    val targetChannel: String, // Campaigns, Reports, Dealer Notifications, Customer Notifications, Stock Alerts
    val executionMode: String = "Semi Automatic", // Manual, Semi Automatic, Fully Automatic
    val executionSummary: String,
    val status: String = "Success",
    val timestamp: String
)
