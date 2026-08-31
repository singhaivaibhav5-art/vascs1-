package com.example.vascs.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "brand_factory")
data class BrandFactoryEntity(
    @PrimaryKey(autoGenerate = true) val brandId: Long = 0,
    val brandName: String,
    val visualIdentity: String, // Minimalist Gold & Obsidian, Royal Royal Heritage Kanjivaram, Cyber Bio-Silk
    val logoConcept: String,
    val taglines: String,
    val brandStory: String,
    val aestheticScore: Double,
    val trademarkSafetyPct: Double,
    val timestamp: String = "2026-08-15 04:56"
)
