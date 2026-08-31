package com.example.vascs.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Table: ai_demand_requests
 * Captures historical data, regional context, festivals, and stock inputs for demand forecasting.
 */
@Entity(tableName = "ai_demand_requests")
data class AIDemandRequestEntity(
    @PrimaryKey(autoGenerate = true) val requestId: Long = 0,
    val productId: String = "",
    val productName: String = "Royal Banarasi Silk Saree",
    val sku: String = "SKU-SLK-BNR-01",
    val category: String = "Bridal Silk Sarees",
    val region: String = "Pan-India / North Zone",
    val dealerNetwork: String = "Tier 1 Wholesalers & Boutiques",
    val season: String = "Wedding & Festive Season (Q3/Q4)",
    val festivalCalendar: String = "Diwali, Durga Puja & Wedding Muhurats",
    val marketingCampaignData: String = "Social Media Video Blitz & Dealer WhatsApp Catalog",
    val currentInventory: Int = 120,
    val salesHistory30d: Int = 85,
    val salesHistory90d: Int = 240,
    val salesHistory1y: Int = 920,
    val unitPrice: Double = 14500.0,
    val leadTimeDays: Int = 18,
    val createdAt: Long = System.currentTimeMillis()
)

/**
 * Table: ai_demand_forecasts
 * Stores the complete 10-point AI-generated predictive demand matrix, reorder quantity, and risk assessment.
 */
@Entity(tableName = "ai_demand_forecasts")
data class AIDemandForecastEntity(
    @PrimaryKey(autoGenerate = true) val forecastId: Long = 0,
    val requestId: Long = 0,
    val productId: String = "",
    val productName: String = "Royal Banarasi Silk Saree",
    val sku: String = "SKU-SLK-BNR-01",
    val category: String = "Bridal Silk Sarees",
    val region: String = "Pan-India / North Zone",
    val currentInventory: Int = 120,
    val unitPrice: Double = 14500.0,
    
    // 10 Core AI Demand Forecast Outputs
    val forecast7dUnits: Int = 28,
    val forecast7dRevenue: Double = 406000.0,
    val forecast30dUnits: Int = 135,
    val forecast30dRevenue: Double = 1957500.0,
    val forecast90dUnits: Int = 460,
    val forecast90dRevenue: Double = 6670000.0,
    val forecast1yUnits: Int = 1680,
    val forecast1yRevenue: Double = 24360000.0,
    val reorderQuantity: Int = 95,
    val safetyStockRecommendation: Int = 45,
    val fastMovingPrediction: String = "High Velocity Superstar (Top 5% Category Flow)",
    val isFastMoving: Boolean = true,
    val slowMovingPrediction: String = "Low Stagnation Risk (High Seasonal Rotation)",
    val isSlowMoving: Boolean = false,
    val deadStockRisk: String = "Low (3% Risk Exposure)",
    val deadStockRiskScore: Int = 8, // 0 to 100 (lower is better)
    val growthOpportunityScore: Int = 92, // 0 to 100 (higher is better)
    
    // Analytics & Strategic Insights
    val expectedSalesUnits: Int = 135,
    val expectedRevenue: Double = 1957500.0,
    val growthProbability: Int = 88, // %
    val stockOutRiskProbability: Int = 74, // % chance of stocking out without replenishment
    val recommendedAction: String = "Trigger immediate weaver lot production to avoid festive stockout.",
    val aiRationale: String = "Historical wedding season surges combined with dealer advance orders project a 42% demand increase over baseline.",
    val seasonalPeakTiming: String = "Peak Demand Window: Oct 15 - Dec 20",
    val isFavorite: Boolean = false,
    val isApplied: Boolean = false,
    val isFallback: Boolean = false,
    val createdAt: Long = System.currentTimeMillis()
)

/**
 * Table: ai_demand_history
 * Maintains an audit trail of demand simulations, procurement triggers, and stocking adjustments.
 */
@Entity(tableName = "ai_demand_history")
data class AIDemandHistoryEntity(
    @PrimaryKey(autoGenerate = true) val historyId: Long = 0,
    val forecastId: Long = 0,
    val productName: String,
    val sku: String,
    val category: String,
    val currentInventory: Int,
    val forecast30dUnits: Int,
    val forecast90dUnits: Int,
    val reorderQuantity: Int,
    val safetyStock: Int,
    val deadStockRisk: String,
    val growthOpportunityScore: Int,
    val actionTaken: String = "Generated Forecast Simulation", // e.g., "Generated Forecast", "Created Purchase Order", "Safety Stock Applied", "Exported PDF Report"
    val timestamp: Long = System.currentTimeMillis()
)

/**
 * Table: ai_demand_models
 * Pre-configured statistical & ML seasonality models for saree categories and festivals.
 */
@Entity(tableName = "ai_demand_models")
data class AIDemandModelEntity(
    @PrimaryKey(autoGenerate = true) val modelId: Long = 0,
    val modelName: String,
    val category: String,
    val seasonalityMultiplier: Double = 1.35,
    val festivalSpikeMultiplier: Double = 1.65,
    val leadTimeBufferDays: Int = 7,
    val safetyStockFactor: Double = 0.30,
    val trendFactor: Double = 1.15,
    val description: String = "",
    val isActive: Boolean = true,
    val createdAt: Long = System.currentTimeMillis()
)
