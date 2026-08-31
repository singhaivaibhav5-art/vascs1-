package com.example.vascs.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "knowledge_fabric")
data class KnowledgeFabricEntity(
    @PrimaryKey(autoGenerate = true) val fabricId: Long = 0,
    val domainCategory: String, // Business Knowledge, Economic Knowledge, Trade Knowledge, Innovation Knowledge, Future Knowledge
    val knowledgeTopic: String,
    val indexedNodesCount: Long,
    val synthesisDepthLevel: String,
    val reasoningConfidencePct: Double,
    val predictiveAccuracyPct: Double,
    val actionableInsightsSummary: String,
    val timestamp: String = "2026-08-16 03:30"
)

@Entity(tableName = "industry_matrix")
data class IndustryMatrixEntity(
    @PrimaryKey(autoGenerate = true) val industryId: Long = 0,
    val industrySector: String, // Retail, Manufacturing, Healthcare, Agriculture, Technology, Finance
    val activeClustersCount: Int,
    val sectoralMarketCapBillionUsd: Double,
    val transformationVelocityPct: Double,
    val crossIndustrySynergyScore: Double,
    val aiIntegrationLevelPct: Double,
    val keyDisruptionVector: String,
    val timestamp: String = "2026-08-16 03:30"
)

@Entity(tableName = "opportunity_universe")
data class OpportunityUniverseEntity(
    @PrimaryKey(autoGenerate = true) val opportunityId: Long = 0,
    val opportunityTitle: String,
    val opportunityCategory: String, // Hidden Market, Future Trend, Emerging Industry, Global Arb
    val addressableValueMillionUsd: Double,
    val timeToMaturityMonths: Int,
    val captureProbabilityPct: Double,
    val strategicActionPlan: String,
    val universeOpportunityScore: Double,
    val executionStage: String, // Detected, Validating, Autonomous Capital Allocated, Active Expansion
    val timestamp: String = "2026-08-16 03:30"
)

@Entity(tableName = "omniverse_health")
data class OmniverseHealthEntity(
    @PrimaryKey(autoGenerate = true) val healthId: Long = 0,
    val dimensionName: String, // Economy Health, Trade Health, Innovation Health, Growth Health, Civilization Health
    val score: Double,
    val benchmarkTarget: Double = 95.0,
    val status: String, // Optimal, Resilient, Exceptional
    val diagnosticSummary: String,
    val timestamp: String = "2026-08-16 03:30"
)

@Entity(tableName = "omniverse_risk")
data class OmniverseRiskEntity(
    @PrimaryKey(autoGenerate = true) val riskId: Long = 0,
    val riskDomain: String, // Economic Risk, Market Risk, Supply Risk, Technology Risk, Growth Risk
    val riskTitle: String,
    val severityLevel: String, // Minimal, Controlled, Elevated, Critical
    val exposureValueMillionUsd: Double,
    val automatedMitigationStrategy: String,
    val riskResilienceScore: Double,
    val timestamp: String = "2026-08-16 03:30"
)

@Entity(tableName = "omniverse_innovation")
data class OmniverseInnovationEntity(
    @PrimaryKey(autoGenerate = true) val innovationId: Long = 0,
    val innovationTitle: String,
    val innovationClass: String, // Technology, Patent, Business Model, Universal Standard
    val potentialYieldMillionUsd: Double,
    val expansionIndex: Double,
    val deploymentStatus: String,
    val patentIdentifier: String,
    val timestamp: String = "2026-08-16 03:30"
)
