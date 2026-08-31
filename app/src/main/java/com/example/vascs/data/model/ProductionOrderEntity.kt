package com.example.vascs.data.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "production_orders",
    indices = [
        Index(value = ["productionNumber"], unique = true),
        Index(value = ["status"])
    ]
)
data class ProductionOrderEntity(
    @PrimaryKey(autoGenerate = true) val productionId: Long = 0,
    val productionNumber: String,
    val designName: String,
    val productionDate: String,
    val targetQty: Int,
    val status: String = "Draft"
)
