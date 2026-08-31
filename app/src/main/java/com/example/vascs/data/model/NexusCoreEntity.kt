package com.example.vascs.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "nexus_core")
data class NexusCoreEntity(
    @PrimaryKey(autoGenerate = true) val coreId: Long = 0,
    val systemName: String,
    val connectivityStatus: String = "GLOBAL_CONNECTED (190 Countries)",
    val synchronizationMode: String = "Quantum Mesh Intelligence Sync",
    val enterpriseCoordination: String = "100% Autonomous Multi-Enterprise Grid",
    val networkGovernance: String = "Decentralized AI Alliance DAO",
    val activeEnterprisesCount: Int = 24500,
    val syncedNodesCount: Int = 185000,
    val networkLatencyMs: Double = 0.18,
    val throughputTps: Long = 28000000,
    val timestamp: String = "2026-08-15 04:40"
)
