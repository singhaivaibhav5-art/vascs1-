package com.example.vascs.data.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "manufacturers",
    indices = [
        Index(value = ["companyName"])
    ]
)
data class ManufacturerEntity(
    @PrimaryKey(autoGenerate = true) val manufacturerId: Long = 0,
    val companyName: String,
    val location: String, // e.g. Surat, Gujarat, India
    val productionUnitsCount: Int = 4,
    val monthlyCapacityPcs: Int = 250000,
    val mainCategories: String, // e.g. Silk Sarees, Printed Sarees, Lehenga
    val factoryRating: Double = 4.9,
    val globalVisibilityStatus: String = "Active Global"
)
