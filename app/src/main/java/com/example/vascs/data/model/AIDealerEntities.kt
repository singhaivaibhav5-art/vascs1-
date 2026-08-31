package com.example.vascs.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ai_dealer_requests")
data class AIDealerRequestEntity(
    @PrimaryKey(autoGenerate = true)
    val requestId: Long = 0,
    val dealerName: String,
    val dealerCategory: String, // Tier 1 Wholesaler, Multi-Brand Retailer, Boutique, Master Distributor
    val location: String, // City / Region / Zone
    val salesHistoryAnnual: Double, // Annual sales volume in ₹
    val salesHistoryQuarterly: Double, // Last quarter sales in ₹
    val orderFrequencyPerMonth: Double, // Average orders / month
    val paymentPerformance: String, // Excellent (0-7d), Good (8-15d), Moderate (16-30d), Delayed (>30d)
    val productPreferences: String, // Pure Silk, Organza Lehengas, Handloom Cotton, Men's Ethnic
    val growthTrendPercent: Double, // Year-over-Year Growth %
    val dealerRating: Double, // 1.0 - 5.0 Rating
    val customerReachCount: Int, // Estimated end-customer base
    val creditLimit: Double = 500000.0,
    val creditUsed: Double = 180000.0,
    val timestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "ai_dealer_recommendations")
data class AIDealerRecommendationEntity(
    @PrimaryKey(autoGenerate = true)
    val recommendationId: Long = 0,
    val requestId: Long = 0,
    val dealerName: String,
    val dealerCategory: String,
    val location: String,
    val classification: String, // TOP_PERFORMER, HIGH_GROWTH, EXPANSION, RECOVERY, RISK_WATCH
    val dealerPotentialScore: Int, // 0 - 100
    val dealerLoyaltyScore: Int, // 0 - 100
    val revenueContributionScore: Int, // 0 - 100
    val riskScore: Int, // 0 - 100
    val futureGrowthForecastPercent: Double, // Projected 1-year growth %
    val recommendedActions: String, // Strategic action steps
    val creditRecommendation: String, // Credit expansion / tightening note
    val exclusiveCatalogAccess: String, // Recommended product allocations
    val promotionalSupport: String, // Co-op ad / display incentives
    val rationale: String, // AI reasoning breakdown
    val isTopPerformer: Boolean = false,
    val isHighGrowth: Boolean = false,
    val isExpansionCandidate: Boolean = false,
    val isRecoveryTarget: Boolean = false,
    val isRiskAlert: Boolean = false,
    val isFavorite: Boolean = false,
    val isApplied: Boolean = false,
    val timestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "ai_dealer_scores")
data class AIDealerScoreEntity(
    @PrimaryKey(autoGenerate = true)
    val scoreId: Long = 0,
    val dealerName: String,
    val dealerCategory: String,
    val location: String,
    val overallScore: Double, // Aggregate index 0-100
    val salesScore: Double, // 0-100
    val growthScore: Double, // 0-100
    val paymentScore: Double, // 0-100
    val loyaltyScore: Double, // 0-100
    val reachScore: Double, // 0-100
    val rankingRank: Int = 1,
    val tierBadge: String = "Platinum Tier",
    val timestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "ai_dealer_growth_forecasts")
data class AIDealerGrowthForecastEntity(
    @PrimaryKey(autoGenerate = true)
    val forecastId: Long = 0,
    val dealerName: String,
    val baselineQuarterlyRevenue: Double,
    val projectedQ1Revenue: Double,
    val projectedQ2Revenue: Double,
    val projectedQ3Revenue: Double,
    val projectedQ4Revenue: Double,
    val annualProjectedRevenue: Double,
    val targetIncentiveBudget: Double,
    val recommendedProductMix: String,
    val timestamp: Long = System.currentTimeMillis()
)
