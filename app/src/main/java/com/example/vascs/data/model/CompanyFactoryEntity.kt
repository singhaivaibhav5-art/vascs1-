package com.example.vascs.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "company_factory")
data class CompanyFactoryEntity(
    @PrimaryKey(autoGenerate = true) val companyId: Long = 0,
    val companyName: String,
    val companyStructure: String, // Autonomous DAO, Smart-Loom Syndicate, Digital D2C Global LLC, Micro-Franchise Guild
    val businessModel: String,
    val departments: String, // AI Core, Autonomous Supply Chain, Algorithmic Marketing, Smart Sourcing, Treasury
    val revenueStreams: String,
    val launchReadinessPct: Double,
    val estimatedAnnualRunRateUsdMillion: Double,
    val status: String = "READY_TO_LAUNCH",
    val timestamp: String = "2026-08-15 04:56"
)
