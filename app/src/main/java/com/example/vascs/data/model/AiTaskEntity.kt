package com.example.vascs.data.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "ai_tasks",
    indices = [
        Index(value = ["assignedRole"])
    ]
)
data class AiTaskEntity(
    @PrimaryKey(autoGenerate = true) val taskId: Long = 0,
    val assignedRole: String,
    val taskType: String = "Medium", // Critical, High, Medium, Low
    val title: String,
    val description: String,
    val status: String = "Pending", // Pending, In Progress, Completed
    val createdDate: String
)
