package com.example.vascs.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "supply_grid")
data class SupplyGridEntity(
    @PrimaryKey(autoGenerate = true) val gridId: Long = 0,
    val hubName: String,
    val connectedManufacturersCount: Int,
    val connectedSuppliersCount: Int,
    val connectedWarehousesCount: Int,
    val connectedTransportersCount: Int,
    val connectedDealersCount: Int,
    val frictionScorePct: Double = 0.02, // Zero Friction Goal
    val throughputCapacityUnits: Long = 5000000,
    val status: String = "ZERO_FRICTION_OPTIMAL"
)
