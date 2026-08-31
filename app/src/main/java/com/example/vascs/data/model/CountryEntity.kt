package com.example.vascs.data.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "countries",
    indices = [
        Index(value = ["countryCode"], unique = true)
    ]
)
data class CountryEntity(
    @PrimaryKey(autoGenerate = true) val countryId: Long = 0,
    val countryCode: String,
    val countryName: String,
    val currencyCode: String,
    val taxSystem: String, // GST, VAT, Sales Tax
    val timezone: String,
    val language: String
)
