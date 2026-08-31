package com.example.vascs.data.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "currencies",
    indices = [
        Index(value = ["currencyCode"], unique = true)
    ]
)
data class CurrencyEntity(
    @PrimaryKey(autoGenerate = true) val currencyId: Long = 0,
    val currencyCode: String,
    val currencyName: String,
    val symbol: String
)
