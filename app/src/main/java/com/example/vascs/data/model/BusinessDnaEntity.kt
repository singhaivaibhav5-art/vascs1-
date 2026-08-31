package com.example.vascs.data.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "business_dna",
    indices = [
        Index(value = ["dnaCategory"], unique = true)
    ]
)
data class BusinessDnaEntity(
    @PrimaryKey(autoGenerate = true) val dnaId: Long = 0,
    val dnaCategory: String, // Profitability, Growth Velocity, Operational Efficiency, Dealer Network Health, Market Dominance
    val score: Double = 98.6, // Score out of 100
    val gradeLevel: String = "A+ Singularity Tier",
    val keyStrengths: String,
    val recommendedActions: String,
    val evaluatedDate: String
)
