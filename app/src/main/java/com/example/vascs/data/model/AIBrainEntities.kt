package com.example.vascs.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Table: ai_prompts
 * Stores generated / submitted prompts with their domains, context tags, and generation metadata.
 */
@Entity(tableName = "ai_prompts")
data class AIPromptEntity(
    @PrimaryKey(autoGenerate = true) val promptId: Long = 0,
    val featureType: String, // CATALOGUE, PRICING, DEMAND, DEALER, STRATEGY
    val inputPayload: String, // JSON or formatted text of input
    val systemPrompt: String,
    val userPrompt: String,
    val modelName: String = "gemini-2.5-flash",
    val tokensUsed: Int = 0,
    val latencyMs: Long = 0L,
    val status: String = "SUCCESS", // SUCCESS, FAILED, PENDING
    val createdAt: Long = System.currentTimeMillis()
)

/**
 * Table: ai_conversations
 * Stores multi-turn conversational interactions with the VASCS AI Brain.
 */
@Entity(tableName = "ai_conversations")
data class AIConversationEntity(
    @PrimaryKey(autoGenerate = true) val conversationId: Long = 0,
    val sessionTitle: String,
    val domain: String, // CATALOGUE, PRICING, FORECAST, DEALER, STRATEGY, GENERAL
    val role: String, // USER, MODEL, SYSTEM
    val messageContent: String,
    val structuredDataJson: String? = null,
    val timestamp: Long = System.currentTimeMillis()
)

/**
 * Table: ai_suggestions
 * Stores AI Catalogue suggestions and AI Pricing suggestions.
 */
@Entity(tableName = "ai_suggestions")
data class AISuggestionEntity(
    @PrimaryKey(autoGenerate = true) val suggestionId: Long = 0,
    val suggestionType: String, // CATALOGUE, PRICING, STRATEGY
    val referenceId: String? = null, // Product ID / SKU if applicable
    val productName: String,
    val category: String,
    val fabric: String? = null,
    val color: String? = null,
    val costPrice: Double = 0.0,
    // Catalogue Outputs
    val generatedTitle: String? = null,
    val generatedDescription: String? = null,
    val instagramCaption: String? = null,
    val whatsappCaption: String? = null,
    val seoKeywords: String? = null,
    // Pricing Outputs
    val retailPrice: Double = 0.0,
    val wholesalePrice: Double = 0.0,
    val dealerPrice: Double = 0.0,
    val suggestedMarginPct: Double = 0.0,
    val pricingRationale: String? = null,
    val confidenceScore: Double = 98.5,
    val createdAt: Long = System.currentTimeMillis()
)
