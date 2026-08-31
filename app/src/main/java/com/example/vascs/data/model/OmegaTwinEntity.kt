package com.example.vascs.data.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "omega_twin",
    indices = [
        Index(value = ["replicaType"])
    ]
)
data class OmegaTwinEntity(
    @PrimaryKey(autoGenerate = true) val twinId: Long = 0,
    val replicaType: String, // Business Replica, Market Replica, Dealer Replica, Customer Replica
    val fidelityScorePct: Double = 99.7,
    val activeSimulationScenarios: Int = 142,
    val forecastedGrowthMultiplier: Double = 3.8,
    val strategicInsight: String,
    val lastSimulationTimestamp: String
)
