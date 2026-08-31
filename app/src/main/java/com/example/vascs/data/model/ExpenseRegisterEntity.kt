package com.example.vascs.data.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "expense_register",
    indices = [
        Index(value = ["expenseCategory"])
    ]
)
data class ExpenseRegisterEntity(
    @PrimaryKey(autoGenerate = true) val expenseId: Long = 0,
    val expenseDate: String,
    val expenseCategory: String, // Salary, Rent, Electricity, Courier, Internet, Marketing, Travel, Miscellaneous
    val amount: Double,
    val remarks: String = ""
)
