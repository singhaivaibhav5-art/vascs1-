package com.example.vascs.data.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "gst_reports",
    indices = [
        Index(value = ["reportPeriod"])
    ]
)
data class GstReportEntity(
    @PrimaryKey(autoGenerate = true) val reportId: Long = 0,
    val reportPeriod: String, // e.g. 2026-08
    val cgst: Double = 0.0,
    val sgst: Double = 0.0,
    val igst: Double = 0.0,
    val inputTax: Double = 0.0,
    val outputTax: Double = 0.0,
    val netTaxPayable: Double = 0.0
)
