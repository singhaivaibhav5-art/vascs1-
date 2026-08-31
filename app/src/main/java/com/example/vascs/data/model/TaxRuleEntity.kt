package com.example.vascs.data.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "tax_rules",
    indices = [
        Index(value = ["countryCode"])
    ]
)
data class TaxRuleEntity(
    @PrimaryKey(autoGenerate = true) val ruleId: Long = 0,
    val countryCode: String,
    val taxType: String, // GST, VAT, Sales Tax, Duty
    val taxRate: Double,
    val description: String
)
