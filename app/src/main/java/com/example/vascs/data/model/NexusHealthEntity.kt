package com.example.vascs.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "nexus_health")
data class NexusHealthEntity(
    @PrimaryKey(autoGenerate = true) val healthId: Long = 0,
    val networkHealthScore: Double = 99.9,
    val enterpriseHealthScore: Double = 99.8,
    val industryHealthScore: Double = 99.7,
    val economicHealthScore: Double = 99.9,
    val nexusHealthIndex: Double = 99.82, // Nexus Health Index
    val healthGrade: String = "SYNCHRONIZED_ORGANISM_OPTIMAL",
    val timestamp: String = "2026-08-15 04:40"
)
