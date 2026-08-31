package com.example.vascs.data.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "notifications",
    indices = [
        Index(value = ["targetUser"])
    ]
)
data class NotificationEntity(
    @PrimaryKey(autoGenerate = true) val notificationId: Long = 0,
    val targetUser: String,
    val channel: String = "Push Notification",
    val alertType: String = "Order Alert",
    val title: String,
    val message: String,
    val createdDate: String,
    val isRead: Boolean = false
)
