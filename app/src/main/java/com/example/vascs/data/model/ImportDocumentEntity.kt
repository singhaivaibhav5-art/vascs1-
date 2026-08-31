package com.example.vascs.data.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "import_documents",
    indices = [
        Index(value = ["countryCode"])
    ]
)
data class ImportDocumentEntity(
    @PrimaryKey(autoGenerate = true) val documentId: Long = 0,
    val poNumber: String,
    val countryCode: String,
    val vendorName: String,
    val importDuty: Double,
    val totalCost: Double,
    val status: String = "In Transit"
)
