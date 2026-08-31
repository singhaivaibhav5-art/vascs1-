package com.example.vascs.data.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "party_masters",
    indices = [
        Index(value = ["partyName"]),
        Index(value = ["partyType"])
    ]
)
data class PartyMasterEntity(
    @PrimaryKey(autoGenerate = true)
    val partyId: Long = 0,
    val partyName: String,
    val partyType: String, // Customer, Dealer, Wholesaler, Distributor, Supplier, Agent
    val mobile: String,
    val whatsapp: String = "",
    val gstin: String = "",
    val pan: String = "",
    val address: String = "",
    val city: String = "",
    val state: String = "",
    val pincode: String = "",
    val creditLimit: Double = 0.0,
    val openingBalance: Double = 0.0,
    val status: String = "ACTIVE"
)
