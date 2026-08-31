package com.example.vascs.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ai_finance_requests")
data class AIFinanceRequestEntity(
    @PrimaryKey(autoGenerate = true)
    val requestId: Long = 0,
    val period: String = "Q3 FY2026",
    val totalSalesInr: Double = 12500000.0,
    val totalPurchasesInr: Double = 7200000.0,
    val totalExpensesInr: Double = 1800000.0,
    val inventoryValueInr: Double = 6500000.0,
    val dealerOutstandingInr: Double = 3400000.0,
    val accountsReceivableInr: Double = 4200000.0,
    val accountsPayableInr: Double = 2800000.0,
    val cashBalanceInr: Double = 850000.0,
    val bankBalanceInr: Double = 4600000.0,
    val grossProfitInr: Double = 5300000.0,
    val netProfitInr: Double = 3500000.0,
    val grossMarginPct: Double = 42.4,
    val netMarginPct: Double = 28.0,
    val timestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "ai_finance_reports")
data class AIFinanceReportEntity(
    @PrimaryKey(autoGenerate = true)
    val reportId: Long = 0,
    val requestId: Long = 0,
    val period: String,
    val totalRevenueInr: Double,
    val grossProfitInr: Double,
    val netProfitInr: Double,
    val grossMarginPct: Double,
    val netProfitMarginPct: Double,
    val operatingExpenseRatio: Double,
    val financialHealthScore: Int, // 0 - 100
    val businessGrowthScore: Int, // 0 - 100
    val roiScore: Double, // e.g. 28.4%
    val workingCapitalInr: Double,
    val cashRunwayMonths: Double,
    val outstandingRiskLevel: String, // LOW, MEDIUM, HIGH, CRITICAL
    val profitabilityAnalysisSummary: String,
    val expenseOptimizationSummary: String,
    val workingCapitalSummary: String,
    val roiAnalysisSummary: String,
    val executiveSummary: String,
    val isFavorite: Boolean = false,
    val timestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "ai_cashflow_forecasts")
data class AICashFlowForecastEntity(
    @PrimaryKey(autoGenerate = true)
    val forecastId: Long = 0,
    val reportId: Long = 0,
    val forecastPeriod: String, // "30_DAYS", "60_DAYS", "90_DAYS"
    val projectedInflowInr: Double,
    val projectedOutflowInr: Double,
    val netCashFlowInr: Double,
    val projectedClosingCashInr: Double,
    val cashFlowHealthStatus: String, // SURPLUS, BALANCED, DEFICIT_RISK, CRITICAL
    val riskAlertNotes: String,
    val dealerCollectionExpectedInr: Double,
    val supplierPayableDueInr: Double,
    val operatingExpenseDueInr: Double,
    val timestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "ai_financial_health")
data class AIFinancialHealthEntity(
    @PrimaryKey(autoGenerate = true)
    val healthId: Long = 0,
    val assessmentDate: String = "Aug 2026",
    val overallHealthScore: Int, // 0 - 100
    val liquidityScore: Int, // 0 - 100
    val solvencyScore: Int, // 0 - 100
    val profitabilityScore: Int, // 0 - 100
    val recoveryEfficiencyScore: Int, // 0 - 100
    val currentRatio: Double, // e.g. 2.15
    val quickRatio: Double, // e.g. 1.45
    val debtToEquityRatio: Double, // e.g. 0.35
    val daysSalesOutstanding: Int, // e.g. 38 days
    val cashFlowRiskAlert: String = "",
    val lowProfitAlert: String = "",
    val highExpenseAlert: String = "",
    val dealerRecoveryAlert: String = "",
    val workingCapitalAlert: String = "",
    val timestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "ai_finance_recommendations")
data class AIFinanceRecommendationEntity(
    @PrimaryKey(autoGenerate = true)
    val recommendationId: Long = 0,
    val reportId: Long = 0,
    val category: String, // COST_REDUCTION, REVENUE_EXPANSION, RECOVERY_ACCELERATION, WORKING_CAPITAL_OPTIMIZATION, TAX_PLANNING, CASHFLOW_BUFFER
    val priority: String, // CRITICAL, HIGH, MEDIUM, LOW
    val title: String,
    val actionPlan: String,
    val expectedFinancialImpactInr: Double,
    val impactDescription: String,
    val targetDeadlineDays: Int,
    val isApplied: Boolean = false,
    val timestamp: Long = System.currentTimeMillis()
)
