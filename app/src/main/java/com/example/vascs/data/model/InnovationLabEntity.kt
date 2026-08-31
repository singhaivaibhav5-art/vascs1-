package com.example.vascs.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "innovation_lab")
data class InnovationLabEntity(
    @PrimaryKey(autoGenerate = true) val innovationId: Long = 0,
    val innovationTitle: String,
    val category: String, // Inventions, Patents, Future Concepts, Breakthrough Tech
    val patentApplicationNumber: String,
    val conceptAbstract: String,
    val maturityStage: String, // Ideation, Prototype, Patent Filed, Production Ready
    val disruptiveImpactScore: Double,
    val commercialValuationUsdMillion: Double,
    val timestamp: String = "2026-08-15 04:56"
)
