package com.example.vascs.data.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "omega_core",
    indices = [
        Index(value = ["systemStatus"])
    ]
)
data class OmegaCoreEntity(
    @PrimaryKey(autoGenerate = true) val coreId: Long = 0,
    val systemStatus: String = "ACTIVE_GOVERNANCE",
    val activeSubsystemsCount: Int = 18,
    val omegaIndex: Double = 99.8,
    val globalStrategyDirective: String,
    val lastSyncTimestamp: String
)
