package com.example.vascs.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "knowledge_web")
data class KnowledgeWebEntity(
    @PrimaryKey(autoGenerate = true) val webId: Long = 0,
    val relationCategory: String, // Business Relations, Industry Relations, Country Relations, Trade Relations
    val sourceEntity: String,
    val targetEntity: String,
    val relationType: String,
    val strengthScorePct: Double = 99.4,
    val aiReasoningInsight: String,
    val predictiveTrend: String,
    val optimizationRecommendation: String
)
