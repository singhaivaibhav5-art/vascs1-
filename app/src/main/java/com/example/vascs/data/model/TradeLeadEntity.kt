package com.example.vascs.data.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "trade_leads",
    indices = [
        Index(value = ["leadType"])
    ]
)
data class TradeLeadEntity(
    @PrimaryKey(autoGenerate = true) val leadId: Long = 0,
    val leadType: String, // Buy Leads, Sell Leads, Export Leads, Import Leads
    val title: String,
    val requirementDetails: String,
    val quantityRequired: Int = 1000,
    val targetPriceInr: Double,
    val aiLeadScore: Int = 94,
    val postedDate: String
)
