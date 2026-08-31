package com.example.vascs.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cosmos_health")
data class CosmosHealthEntity(
    @PrimaryKey(autoGenerate = true) val healthId: Long = 0,
    val businessHealthScore: Double, // 0 - 100
    val marketHealthScore: Double, // 0 - 100
    val industryHealthScore: Double, // 0 - 100
    val tradeHealthScore: Double, // 0 - 100
    val economicHealthScore: Double, // 0 - 100
    val cosmosHealthIndex: Double, // Overall composite health score (0 - 100)
    val healthGrade: String = "APEX_OPTIMAL",
    val timestamp: String = "2026-08-15 03:50"
)
