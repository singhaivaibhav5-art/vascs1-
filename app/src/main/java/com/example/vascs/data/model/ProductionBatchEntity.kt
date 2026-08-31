package com.example.vascs.data.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "production_batches",
    indices = [
        Index(value = ["batchCode"], unique = true),
        Index(value = ["productionId"]),
        Index(value = ["status"])
    ]
)
data class ProductionBatchEntity(
    @PrimaryKey(autoGenerate = true) val batchId: Long = 0,
    val batchCode: String,
    val productionId: Long,
    val fabricUsed: String,
    val colourUsed: String,
    val workerName: String,
    val machineName: String,
    val batchQty: Int,
    val status: String = "In Progress"
)
