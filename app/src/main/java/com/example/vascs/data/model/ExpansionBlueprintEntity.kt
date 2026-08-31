package com.example.vascs.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "expansion_blueprints")
data class ExpansionBlueprintEntity(
    @PrimaryKey(autoGenerate = true) val blueprintId: Long = 0,
    val expansionName: String,
    val targetLevel: String, // City, State, Country, Global
    val geographicalTarget: String,
    val capitalRequiredInr: Double,
    val projectedRevenueInr: Double,
    val executionTimelineMonths: Int,
    val operationalMilestones: String,
    val status: String = "ACTIVE_EXECUTION"
)
