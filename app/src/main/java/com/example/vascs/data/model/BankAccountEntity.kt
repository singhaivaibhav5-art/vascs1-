package com.example.vascs.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bank_accounts")
data class BankAccountEntity(
    @PrimaryKey(autoGenerate = true)
    val accountId: Long = 0,
    val bankName: String,
    val accountNumber: String,
    val ifscCode: String,
    val upiId: String = "",
    val openingBalance: Double = 0.0,
    val currentBalance: Double = 0.0,
    val isPrimary: Boolean = true
)
