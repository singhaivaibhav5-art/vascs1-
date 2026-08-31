package com.example.vascs.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "enterprise_network")
data class EnterpriseNetworkEntity(
    @PrimaryKey(autoGenerate = true) val networkId: Long = 0,
    val enterpriseName: String,
    val entityType: String, // Company, Branch, Factory, Warehouse, Dealer, Partner
    val regionOrCountry: String,
    val connectedBranchesCount: Int = 12,
    val connectedFactoriesCount: Int = 4,
    val connectedWarehousesCount: Int = 18,
    val connectedDealersCount: Int = 450,
    val connectedPartnersCount: Int = 85,
    val ecosystemHealthScore: Double = 99.8,
    val status: String = "ACTIVE_SYNCHRONIZED"
)
