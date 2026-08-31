package com.example.vascs.data.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "dyeing_records",
    indices = [
        Index(value = ["batchId"])
    ]
)
data class DyeingRecordEntity(
    @PrimaryKey(autoGenerate = true) val dyeingId: Long = 0,
    val batchId: Long,
    val colourName: String,
    val shadeCode: String,
    val dyeDate: String,
    val operator: String
)
