package com.example.vascs.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Table: ai_pricing_requests
 * Stores user and system input parameters for AI Pricing optimization.
 */
@Entity(tableName = "ai_pricing_requests")
data class AIPricingRequestEntity(
    @PrimaryKey(autoGenerate = true) val requestId: Long = 0,
    val productName: String,
    val costPrice: Double,
    val category: String,
    val brand: String = "VASCS Heritage",
    val fabricType: String,
    val dealerCategory: String = "Tier 1 Wholesaler",
    val existingSellingPrice: Double = 0.0,
    val competitorPrice: Double = 0.0,
    val targetMargin: Double = 35.0,
    val region: String = "Pan-India",
    val marketType: String = "Wholesale Mandi",
    val createdAt: Long = System.currentTimeMillis()
)

/**
 * Table: ai_pricing_results
 * Stores the complete 10-point AI-generated pricing intelligence & channel matrix.
 */
@Entity(tableName = "ai_pricing_results")
data class AIPricingResultEntity(
    @PrimaryKey(autoGenerate = true) val resultId: Long = 0,
    val requestId: Long = 0,
    val productName: String = "VASCS Saree SKU",
    val costPrice: Double = 0.0,
    val category: String = "Bridal Silk Sarees",
    val brand: String = "VASCS Heritage",
    val fabricType: String = "Mulberry Silk",
    val dealerCategory: String = "Tier 1 Wholesaler",
    val existingSellingPrice: Double = 0.0,
    val competitorPrice: Double = 0.0,
    val targetMargin: Double = 35.0,
    val region: String = "Pan-India",
    val marketType: String = "Wholesale Mandi",
    // 10 Key AI Generated Pricing Recommendations
    val retailPrice: Double = 0.0,
    val wholesalePrice: Double = 0.0,
    val distributorPrice: Double = 0.0,
    val dealerPrice: Double = 0.0,
    val premiumPrice: Double = 0.0,
    val discountLimit: Double = 15.0, // in %
    val recommendedMargin: Double = 35.0, // in %
    val profitPercentage: Double = 50.0, // in %
    val marketCompetitivenessScore: Int = 85, // 1 - 100
    val priceConfidenceScore: Int = 90, // 1 - 100
    // Analytics & Strategic Insights
    val competitorDifference: Double = 0.0, // retailPrice - competitorPrice
    val priceStrength: String = "High-Margin Leader",
    val marketRank: String = "#1 Best Wholesale Margin",
    val aiRationale: String = "",
    val volumeBreakEvenUnits: Int = 100,
    val channelAdvice: String = "",
    val isFavorite: Boolean = false,
    val isApplied: Boolean = false,
    val isFallback: Boolean = false,
    val createdAt: Long = System.currentTimeMillis()
)

/**
 * Table: ai_pricing_history
 * Maintains an audit trail of pricing actions taken across the enterprise.
 */
@Entity(tableName = "ai_pricing_history")
data class AIPricingHistoryEntity(
    @PrimaryKey(autoGenerate = true) val historyId: Long = 0,
    val resultId: Long = 0,
    val productName: String,
    val category: String,
    val fabricType: String,
    val costPrice: Double,
    val retailPrice: Double,
    val wholesalePrice: Double,
    val distributorPrice: Double,
    val dealerPrice: Double,
    val profitPercentage: Double,
    val recommendedMargin: Double,
    val marketCompetitivenessScore: Int,
    val actionTaken: String = "Generated Recommendation", // e.g. "Applied to Catalogue", "Exported Quote", "Saved Preset", "Shared with Dealer"
    val timestamp: Long = System.currentTimeMillis()
)

/**
 * Table: ai_pricing_rules
 * Pre-configured margin rules, markups, and discount fences by fabric & category.
 */
@Entity(tableName = "ai_pricing_rules")
data class AIPricingRuleEntity(
    @PrimaryKey(autoGenerate = true) val ruleId: Long = 0,
    val ruleName: String,
    val category: String,
    val fabricType: String,
    val minMarginPercent: Double,
    val targetMarginPercent: Double,
    val maxDiscountPercent: Double,
    val wholesaleMultiplier: Double, // e.g. 1.25x
    val distributorMultiplier: Double, // e.g. 1.18x
    val dealerMultiplier: Double, // e.g. 1.35x
    val retailMultiplier: Double, // e.g. 1.65x
    val premiumMultiplier: Double, // e.g. 2.10x
    val description: String = "",
    val isActive: Boolean = true,
    val createdAt: Long = System.currentTimeMillis()
)
