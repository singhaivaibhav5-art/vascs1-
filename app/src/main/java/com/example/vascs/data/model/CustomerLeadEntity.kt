package com.example.vascs.data.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "customer_leads",
    indices = [
        Index(value = ["mobile"]),
        Index(value = ["status"])
    ]
)
data class CustomerLeadEntity(
    @PrimaryKey(autoGenerate = true)
    val leadId: Long = 0,
    val customerName: String,
    val mobile: String,
    val whatsapp: String = "",
    val city: String = "",
    val state: String = "",
    val source: String = "WhatsApp Studio",
    val interestedProduct: String = "",
    val status: String = "NEW", // NEW, CONTACTED, FOLLOWUP, QUOTATION_SENT, ORDER_CONFIRMED, LOST
    val remarks: String = "",
    val createdDate: String
)
