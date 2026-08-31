package com.example.vascs.data.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "workers",
    indices = [
        Index(value = ["department"])
    ]
)
data class WorkerEntity(
    @PrimaryKey(autoGenerate = true) val workerId: Long = 0,
    val workerName: String,
    val mobile: String,
    val department: String,
    val salaryType: String,
    val ratePerPiece: Double
)
