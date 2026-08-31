package com.example.vascs.data.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "capital_management",
    indices = [
        Index(value = ["allocationCategory"])
    ]
)
data class CapitalManagementEntity(
    @PrimaryKey(autoGenerate = true) val capitalId: Long = 0,
    val allocationCategory: String, // Investments, Expansion, Inventory, Marketing
    val allocatedBudgetInr: Double,
    val projectedRoiPct: Double = 34.5,
    val riskLevel: String = "Balanced Risk",
    val status: String = "Active Capital Deployment"
)
