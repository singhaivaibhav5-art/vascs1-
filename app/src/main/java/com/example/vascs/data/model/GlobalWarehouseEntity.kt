package com.example.vascs.data.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "global_warehouses",
    indices = [
        Index(value = ["countryCode"])
    ]
)
data class GlobalWarehouseEntity(
    @PrimaryKey(autoGenerate = true) val warehouseId: Long = 0,
    val warehouseName: String,
    val countryCode: String,
    val city: String,
    val capacity: Int,
    val currentStock: Int = 0
)
