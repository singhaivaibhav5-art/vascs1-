package com.example.vascs.data.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "followups",
    indices = [
        Index(value = ["leadId"]),
        Index(value = ["status"])
    ]
)
data class FollowupEntity(
    @PrimaryKey(autoGenerate = true)
    val followupId: Long = 0,
    val leadId: Long,
    val customerName: String,
    val mobile: String,
    val reminderType: String, // 1 Day, 3 Days, 7 Days, 15 Days
    val dueDate: String,
    val notes: String = "",
    val status: String = "PENDING", // PENDING, COMPLETED, SKIPPED
    val createdDate: String
)
