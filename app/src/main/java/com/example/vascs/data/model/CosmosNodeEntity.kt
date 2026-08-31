package com.example.vascs.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cosmos_nodes")
data class CosmosNodeEntity(
    @PrimaryKey(autoGenerate = true) val nodeId: Long = 0,
    val nodeName: String,
    val nodeType: String, // Planetary Core, Edge Relay, Regional Supercluster, Sovereign Gateway
    val region: String,
    val status: String = "ONLINE_SYNCHRONIZED",
    val computePowerPFLOPS: Double = 142.5,
    val throughputTps: Long = 2500000,
    val latencyMs: Double = 1.2
)
