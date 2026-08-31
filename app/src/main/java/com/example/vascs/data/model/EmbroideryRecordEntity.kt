package com.example.vascs.data.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "embroidery_records",
    indices = [
        Index(value = ["batchId"])
    ]
)
data class EmbroideryRecordEntity(
    @PrimaryKey(autoGenerate = true) val embroideryId: Long = 0,
    val batchId: Long,
    val designCode: String,
    val machineUsed: String,
    val operator: String,
    val workStatus: String = "Completed"
)
