package com.example.vascs.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ai_inventory_requests")
data class AIInventoryRequestEntity(
    @PrimaryKey(autoGenerate = true)
    val requestId: Long = 0,
    val productName: String,
    val sku: String,
    val category: String,
    val warehouseLocation: String = "Varanasi Central Vault #1",
    val currentStock: Int,
    val allocatedStock: Int = 0,
    val incomingStock: Int = 0,
    val averageDailySales: Double = 3.5,
    val salesHistory30d: Int = 105,
    val salesHistory90d: Int = 320,
    val forecastDemand30d: Int = 135,
    val dealerPendingOrders: Int = 45,
    val unitCostPrice: Double = 14500.0,
    val unitSellingPrice: Double = 22000.0,
    val leadTimeDays: Int = 14,
    val storageCapacityUnits: Int = 500,
    val storageOccupiedUnits: Int = 320,
    val season: String = "Wedding Festive Q3/Q4",
    val festivalCalendar: String = "Diwali, Karva Chauth & Wedding Muhurats",
    val holdingCostPerUnitMonthly: Double = 120.0,
    val timestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "ai_inventory_forecasts")
data class AIInventoryForecastEntity(
    @PrimaryKey(autoGenerate = true)
    val forecastId: Long = 0,
    val requestId: Long = 0,
    val productName: String,
    val sku: String,
    val category: String,
    val warehouseLocation: String = "Varanasi Central Vault #1",
    val velocityClassification: String, // FAST_MOVING, MODERATE_MOVING, SLOW_MOVING, DEAD_STOCK
    val currentStock: Int,
    val reorderQuantity: Int,
    val reorderDate: String, // e.g. "2026-08-28"
    val safetyStockUnits: Int,
    val daysOfSupply: Int,
    val stockoutRiskDays: Int,
    val estimatedReorderCost: Double,
    val projectedHoldingCostMonthly: Double,
    val seasonalMultiplier: Double,
    val fastMovingScore: Int, // 0 - 100
    val deadStockRiskScore: Int, // 0 - 100
    val growthOpportunityScore: Int, // 0 - 100
    val aiOptimizationRationale: String,
    val isFastMoving: Boolean = false,
    val isSlowMoving: Boolean = false,
    val isDeadStock: Boolean = false,
    val isFavorite: Boolean = false,
    val timestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "ai_inventory_alerts")
data class AIInventoryAlertEntity(
    @PrimaryKey(autoGenerate = true)
    val alertId: Long = 0,
    val sku: String,
    val productName: String,
    val alertType: String, // LOW_STOCK, OUT_OF_STOCK, OVERSTOCK, DEAD_STOCK, CRITICAL_REORDER
    val severity: String, // CRITICAL, HIGH, MEDIUM, LOW
    val currentStock: Int,
    val threshold: Int,
    val message: String,
    val actionRequired: String,
    val estimatedImpactCost: Double = 0.0,
    val isResolved: Boolean = false,
    val timestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "ai_inventory_health")
data class AIInventoryHealthEntity(
    @PrimaryKey(autoGenerate = true)
    val healthId: Long = 0,
    val warehouseLocation: String = "Varanasi Central Vault #1",
    val overallHealthScore: Int, // 0 - 100
    val deadStockPercentage: Double,
    val fastMovingPercentage: Double,
    val slowMovingPercentage: Double,
    val stockTurnoverRatio: Double, // e.g. 5.4x per annum
    val warehouseUtilizationScore: Int, // 0 - 100 (percentage occupied vs optimal)
    val totalStockUnits: Int,
    val totalStockValueInr: Double,
    val deadStockValueInr: Double,
    val expectedReorderCostTotal: Double,
    val assessmentDate: String,
    val timestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "ai_inventory_recommendations")
data class AIInventoryRecommendationEntity(
    @PrimaryKey(autoGenerate = true)
    val recommendationId: Long = 0,
    val sku: String,
    val productName: String,
    val category: String,
    val recommendationType: String, // REORDER_ACCELERATE, PRICE_MARKDOWN, BUNDLE_PROMOTION, LIQUIDATION, WAREHOUSE_REALLOCATION, SAFETY_STOCK_ADJUST
    val priority: String, // CRITICAL, HIGH, MEDIUM, LOW
    val recommendedAction: String,
    val expectedImpact: String,
    val suggestedDiscountPct: Double = 0.0,
    val recommendedReorderQty: Int = 0,
    val estimatedCostSavingsInr: Double = 0.0,
    val isApplied: Boolean = false,
    val isFavorite: Boolean = false,
    val timestamp: Long = System.currentTimeMillis()
)
