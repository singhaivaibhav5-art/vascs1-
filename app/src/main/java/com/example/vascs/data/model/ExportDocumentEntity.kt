package com.example.vascs.data.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "export_documents",
    indices = [
        Index(value = ["countryCode"])
    ]
)
data class ExportDocumentEntity(
    @PrimaryKey(autoGenerate = true) val documentId: Long = 0,
    val invoiceNumber: String,
    val countryCode: String,
    val documentType: String, // Commercial Invoice, Packing List, Certificate of Origin
    val issuedDate: String,
    val status: String = "Generated"
)
