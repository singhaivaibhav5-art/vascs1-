package com.example.vascs.data.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "currency_rates",
    indices = [
        Index(value = ["currencyCode"])
    ]
)
data class CurrencyRateEntity(
    @PrimaryKey(autoGenerate = true) val rateId: Long = 0,
    val currencyCode: String,
    val exchangeRate: Double,
    val effectiveDate: String,
    val source: String = "Global Exchange Feed"
)
