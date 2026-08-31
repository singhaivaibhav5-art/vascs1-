package com.example.vascs.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Table: ai_catalogue_requests
 * Stores inbound user requests for AI Catalogue generation.
 */
@Entity(tableName = "ai_catalogue_requests")
data class AICatalogueRequestEntity(
    @PrimaryKey(autoGenerate = true) val requestId: Long = 0,
    val productName: String,
    val category: String,
    val fabric: String,
    val color: String,
    val price: Double,
    val designDetails: String = "",
    val occasion: String = "Bridal & Festive",
    val productImageUrl: String = "",
    val targetAudience: String = "Luxury Ethnic & B2B Dealers",
    val tone: String = "Royal & Heritage Luxury",
    val createdAt: Long = System.currentTimeMillis()
)

/**
 * Table: ai_catalogue_results
 * Stores the complete 10-point AI-generated catalogue output for omnichannel commerce.
 */
@Entity(tableName = "ai_catalogue_results")
data class AICatalogueResultEntity(
    @PrimaryKey(autoGenerate = true) val resultId: Long = 0,
    val requestId: Long = 0,
    // Original Product Info
    val productName: String,
    val category: String,
    val fabric: String,
    val color: String,
    val price: Double,
    val designDetails: String = "",
    val occasion: String = "",
    val productImageUrl: String = "",
    // 10 AI Generated Outputs
    val productTitle: String,
    val shortDescription: String,
    val longDescription: String,
    val seoDescription: String,
    val seoKeywords: String, // Comma or JSON array string
    val instagramCaption: String,
    val facebookCaption: String,
    val whatsappPromotionText: String,
    val dealerMarketingText: String,
    val premiumCatalogueContent: String,
    // Metadata
    val isFallback: Boolean = false,
    val isFavorite: Boolean = false,
    val createdAt: Long = System.currentTimeMillis()
)

/**
 * Table: ai_catalogue_templates
 * Stores industry-specific prompt and editorial templates for high-speed AI catalogue creation.
 */
@Entity(tableName = "ai_catalogue_templates")
data class AICatalogueTemplateEntity(
    @PrimaryKey(autoGenerate = true) val templateId: Long = 0,
    val templateName: String,
    val category: String,
    val tone: String,
    val headerTagline: String,
    val sampleFabric: String = "Pure Katan Silk",
    val sampleColor: String = "Crimson Red & Antique Gold",
    val sampleOccasion: String = "Royal Wedding Bridal",
    val sampleDesignDetails: String = "Intricate Kadwa Jangla weave with pure gold zari borders and heavy pallu.",
    val isCustom: Boolean = false,
    val createdAt: Long = System.currentTimeMillis()
)
