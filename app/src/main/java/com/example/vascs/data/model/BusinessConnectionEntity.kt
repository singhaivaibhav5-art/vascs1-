package com.example.vascs.data.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "business_connections",
    indices = [
        Index(value = ["connectionType"])
    ]
)
data class BusinessConnectionEntity(
    @PrimaryKey(autoGenerate = true) val connectionId: Long = 0,
    val partyA: String, // e.g. VASCS Central Hub
    val partyB: String, // e.g. Surat Weaving Mills
    val connectionType: String, // Manufacturer-Dealer, Dealer-Retailer, Supplier-Manufacturer, Exporter-Importer
    val status: String = "Connected",
    val proposalText: String = "Partnership agreement active with 30-day payment cycle",
    val createdDate: String
)
