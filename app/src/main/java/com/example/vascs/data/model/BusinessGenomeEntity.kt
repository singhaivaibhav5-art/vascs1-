package com.example.vascs.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "business_genome")
data class BusinessGenomeEntity(
    @PrimaryKey(autoGenerate = true) val genomeId: Long = 0,
    val genomeType: String, // Business DNA, Growth DNA, Market DNA, Product DNA
    val sequenceCode: String,
    val geneticAttributes: String,
    val evolutionaryGeneration: Int,
    val fitnessScorePct: Double,
    val adaptiveTrait: String,
    val timestamp: String = "2026-08-15 04:56"
)
