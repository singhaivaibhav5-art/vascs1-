package com.example.vascs.data.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "ai_activity_logs",
    indices = [
        Index(value = ["employeeRole"])
    ]
)
data class AiActivityLogEntity(
    @PrimaryKey(autoGenerate = true) val logId: Long = 0,
    val employeeRole: String,
    val actionName: String,
    val details: String,
    val timestamp: String
)
