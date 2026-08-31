package com.example.vascs.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "partnership_network")
data class PartnershipNetworkEntity(
    @PrimaryKey(autoGenerate = true) val partnershipId: Long = 0,
    val partnerName: String,
    val partnerType: String, // Supplier, Distributor, Dealer, Strategic Partner
    val domainOrSector: String,
    val partnershipScore: Double = 98.6, // Partnership Score
    val reliabilityPct: Double = 99.9,
    val synergyValueMillionUsd: Double,
    val strategicValueProposition: String,
    val status: String = "ACTIVE_ALLIANCE"
)
