package com.example.vascs.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "country_master")
data class CountryMasterEntity(
    @PrimaryKey(autoGenerate = true) val countryId: Long = 0,
    val countryName: String,
    val isoCode: String,
    val gdpBillionUsd: Double,
    val importEaseIndex: Double,
    val exportTariffPct: Double,
    val corporateTaxPct: Double,
    val easeOfBusinessRating: String,
    val primaryTradeOpportunities: String
)
