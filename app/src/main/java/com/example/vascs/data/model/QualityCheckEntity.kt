package com.example.vascs.data.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "quality_checks",
    indices = [
        Index(value = ["batchId"])
    ]
)
data class QualityCheckEntity(
    @PrimaryKey(autoGenerate = true) val qcId: Long = 0,
    val batchId: Long,
    val checkedBy: String,
    val checkedDate: String,
    val passQty: Int,
    val rejectQty: Int,
    val remarks: String = "",
    val qcResult: String = "PASS"
)
