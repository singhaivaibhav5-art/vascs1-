package com.example.vascs.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.vascs.data.model.*
import com.example.vascs.data.repository.VascsRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class SingularityPrimeOverallStats(
    val primeIntelligenceIndex: Double = 100.0,
    val civilizationsGovernedCount: Int = 840,
    val infiniteCoordinationRatePct: Double = 100.0,
    val economicSovereigntyScore: Double = 100.0,
    val totalManagedWealthTrillionUsd: Double = 126.0,
    val dailyAutonomousDecisions: Long = 450000000L,
    val totalThroughputQPS: Long = 23540000000L,
    val systemState: String = "Singularity Prime Absolute Sovereign"
)

class SingularityPrimeViewModel(
    private val repository: VascsRepository
) : ViewModel() {

    val primeCore: StateFlow<SingularityPrimeCoreEntity?> = repository.latestPrimeCore
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    val civilizationEngine: StateFlow<List<CivilizationEngineEntity>> = repository.allCivilizationEngine
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val wealthGenerator: StateFlow<List<WealthGeneratorEntity>> = repository.allWealthGenerator
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val opportunityCreator: StateFlow<List<OpportunityCreatorEntity>> = repository.allOpportunityCreator
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val demandCosmos: StateFlow<List<DemandCosmosEntity>> = repository.allDemandCosmos
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val capitalAuthority: StateFlow<List<CapitalAuthorityEntity>> = repository.allCapitalAuthority
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val tradeSupremacy: StateFlow<List<TradeSupremacyEntity>> = repository.allTradeSupremacy
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val realityEngine: StateFlow<List<RealityEngineEntity>> = repository.allRealityEngine
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val decisionPrime: StateFlow<List<DecisionPrimeEntity>> = repository.allDecisionPrime
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val knowledgePrime: StateFlow<List<KnowledgePrimeEntity>> = repository.allKnowledgePrime
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val innovationFactory: StateFlow<List<InnovationFactoryEntity>> = repository.allInnovationFactory
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val riskShieldPrime: StateFlow<List<RiskShieldPrimeEntity>> = repository.allRiskShieldPrime
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val healthPrime: StateFlow<List<HealthPrimeEntity>> = repository.allHealthPrime
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val primeCommandTower: StateFlow<List<PrimeCommandTowerEntity>> = repository.allPrimeCommandTower
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val evolutionAuthority: StateFlow<List<EvolutionAuthorityEntity>> = repository.allEvolutionAuthority
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _singularityPrimeIndex = MutableStateFlow(100.0)
    val singularityPrimeIndex: StateFlow<Double> = _singularityPrimeIndex.asStateFlow()

    private val _isOperatingAutonomous = MutableStateFlow(false)
    val isOperatingAutonomous: StateFlow<Boolean> = _isOperatingAutonomous.asStateFlow()

    private val _statusMessage = MutableStateFlow<String?>(null)
    val statusMessage: StateFlow<String?> = _statusMessage.asStateFlow()

    init {
        initializeBaselineData()
    }

    private fun initializeBaselineData() {
        viewModelScope.launch {
            repository.runSingularityPrime()
            repository.seedCivilizationEngine()
            repository.generateWealth()
            repository.seedOpportunityCreator()
            repository.predictDemandCosmos()
            repository.seedCapitalAuthority()
            repository.optimizeTradeSupremacy()
            repository.seedRealityEngine()
            repository.executePrimeDecisions()
            repository.seedKnowledgePrime()
            repository.seedInnovationFactory()
            repository.seedRiskShieldPrime()
            repository.seedHealthPrime()
            repository.calculatePrimeIndex()
            repository.seedEvolutionAuthority()
        }
    }

    fun triggerFullSingularityCycle() {
        viewModelScope.launch {
            _isOperatingAutonomous.value = true
            _statusMessage.value = "Initiating Universal Singularity Prime Autonomous Synchronization..."
            delay(600)

            repository.runSingularityPrime(
                SingularityPrimeCoreEntity(
                    primeStatus = "Ultimate Autonomous Business Intelligence Active",
                    civilizationsGovernedCount = 840 + (1..15).random(),
                    primeIntelligenceIndex = 100.0,
                    infiniteCoordinationRatePct = 100.0,
                    economicSovereigntyScore = 100.0,
                    selfEvolutionVelocityIndex = 25.4 + (0..10).random() * 0.1,
                    primeControllerTelemetry = "Singularity Prime recursive self-improvement cycle completed: 100% economic brain synergy verified across all 15 operational vectors."
                )
            )

            repository.generateWealth()
            repository.predictDemandCosmos()
            repository.optimizeTradeSupremacy()
            repository.executePrimeDecisions()
            repository.calculatePrimeIndex()

            _singularityPrimeIndex.value = 100.0
            _statusMessage.value = "Singularity Prime Cycle Executed: Absolute Universal Sovereignty Maintained."
            delay(1200)
            _isOperatingAutonomous.value = false
        }
    }

    fun addCivilizationUnit(domain: String, name: String, law: String, stabilityPct: Double, nodes: Long) {
        viewModelScope.launch {
            repository.insertCivilizationEngine(
                CivilizationEngineEntity(
                    domainDomain = domain,
                    entityName = name,
                    autonomousGovernanceLaw = law,
                    controlStabilityPct = stabilityPct,
                    civilizationControlIndex = 100.0,
                    activeNodesCount = nodes,
                    executionState = "Autonomous Prime Sovereignty"
                )
            )
            _statusMessage.value = "Added Civilization Unit: $name"
        }
    }

    fun addWealthStream(pillar: String, streamName: String, volumeTrillion: Double, growthRatePct: Double) {
        viewModelScope.launch {
            repository.insertWealthGenerator(
                WealthGeneratorEntity(
                    wealthPillar = pillar,
                    wealthStreamName = streamName,
                    currentVolumeTrillionUsd = volumeTrillion,
                    compoundGrowthRatePct = growthRatePct,
                    universalWealthIndex = 100.0,
                    distributionEfficiencyPct = 100.0,
                    allocationStatus = "Continuous Autonomous Compounding"
                )
            )
            _statusMessage.value = "Synthesized Wealth Stream: $streamName"
        }
    }

    fun addOpportunity(horizon: String, title: String, valueTrillion: Double, days: Int, strategy: String) {
        viewModelScope.launch {
            repository.insertOpportunityCreator(
                OpportunityCreatorEntity(
                    creationHorizon = horizon,
                    conceptTitle = title,
                    projectedValueTrillionUsd = valueTrillion,
                    timeToGenesisDays = days,
                    primeOpportunityIndex = 100.0,
                    probabilityOfSuccessPct = 99.9,
                    autonomousSeedingStrategy = strategy
                )
            )
            _statusMessage.value = "Seeded Prime Opportunity: $title"
        }
    }

    fun addDemandCosmosNode(scope: String, cluster: String, unitsMillion: Double, action: String) {
        viewModelScope.launch {
            repository.insertDemandCosmos(
                DemandCosmosEntity(
                    scopeLevel = scope,
                    marketCluster = cluster,
                    predictedDemandUnitsMillion = unitsMillion,
                    fulfillmentVelocityMs = 150.0,
                    demandCosmosIndex = 100.0,
                    predictiveAccuracyPct = 99.99,
                    dynamicBalancingAction = action
                )
            )
            _statusMessage.value = "Predicted Demand Cosmos Node: $cluster"
        }
    }

    fun addCapitalFund(pillar: String, fundName: String, totalBillion: Double, yieldPct: Double) {
        viewModelScope.launch {
            repository.insertCapitalAuthority(
                CapitalAuthorityEntity(
                    allocationPillar = pillar,
                    fundName = fundName,
                    totalUnderManagementBillionUsd = totalBillion,
                    targetYieldRatePct = yieldPct,
                    capitalAuthorityIndex = 100.0,
                    reserveSolvencyRatioPct = 100.0,
                    deploymentStatus = "Quantum Instant Allocation"
                )
            )
            _statusMessage.value = "Chartered Capital Fund: $fundName"
        }
    }

    fun addDecisionDirective(domain: String, title: String, magnitudeTrillion: Double, action: String) {
        viewModelScope.launch {
            repository.insertDecisionPrime(
                DecisionPrimeEntity(
                    executionDomain = domain,
                    decisionDirectiveTitle = title,
                    economicMagnitudeTrillionUsd = magnitudeTrillion,
                    decisionPrimeIndex = 100.0,
                    executionLatencyMicrosec = 5,
                    confidenceRatePct = 100.0,
                    algorithmicAction = action
                )
            )
            _statusMessage.value = "Executed Prime Decision: $title"
        }
    }

    fun addInnovationAsset(category: String, title: String, code: String, impact: Double) {
        viewModelScope.launch {
            repository.insertInnovationFactory(
                InnovationFactoryEntity(
                    creationCategory = category,
                    innovationTitle = title,
                    globalIdentifier = code,
                    commercializationPaceScore = 99.0,
                    innovationFactoryScore = 100.0,
                    civilizationImpactMultiplier = impact,
                    status = "Prime Autonomous Deployment"
                )
            )
            _statusMessage.value = "Forged Innovation Asset: $title"
        }
    }

    fun clearStatusMessage() {
        _statusMessage.value = null
    }

    class Factory(private val repository: VascsRepository) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(SingularityPrimeViewModel::class.java)) {
                return SingularityPrimeViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }
}
