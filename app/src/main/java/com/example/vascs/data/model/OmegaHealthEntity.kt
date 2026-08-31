package com.example.vascs.data.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "omega_health",
    indices = [
        Index(value = ["healthDomain"])
    ]
)
data class OmegaHealthEntity(
    @PrimaryKey(autoGenerate = true) val healthId: Long = 0,
    val healthDomain: String, // Commerce Health, Dealer Health, Market Health, Inventory Health, Finance Health
    val score: Double = 99.2,
    val statusGrade: String = "OPTIMAL_SINGULARITY",
    val riskFactor: String = "Zero Critical Vulnerabilities",
    val correctiveAction: String = "Maintain Autonomous Sentinel Directives",
    val evaluationTimestamp: String
)
