package com.example.vascs.data.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "ai_employees",
    indices = [
        Index(value = ["role"], unique = true)
    ]
)
data class AiEmployeeEntity(
    @PrimaryKey(autoGenerate = true) val employeeId: Long = 0,
    val role: String, // AI CEO, AI Sales Manager, AI Inventory Manager, AI Marketing Manager, AI Finance Manager, AI Dealer Manager, AI Customer Manager, AI Operations Manager
    val name: String,
    val status: String = "Active", // Active, Busy, Idle, Critical
    val healthScore: Int = 98,
    val lastAction: String = "Monitoring system metrics"
)
