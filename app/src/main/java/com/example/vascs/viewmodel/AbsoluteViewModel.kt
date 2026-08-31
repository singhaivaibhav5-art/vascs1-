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

data class AbsoluteOverallStats(
    val absoluteIntelligenceIndex: Double = 100.0,
    val civilizationsGovernedCount: Int = 1250,
    val universalControlRatePct: Double = 100.0,
    val infiniteCoordinationIndex: Double = 100.0,
    val civilizationGovernanceScore: Double = 100.0,
    val totalManagedWealthTrillionUsd: Double = 364.9,
    val totalThroughputQPS: Long = 35280000000L,
    val systemicEquilibriumStatus: String = "One Autonomous Civilization Organism Active"
)

class AbsoluteViewModel(
    private val repository: VascsRepository
) : ViewModel() {

    val absoluteCore: StateFlow<AbsoluteCoreEntity?> = repository.latestAbsoluteCore
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    val economicOS: StateFlow<List<EconomicOSEntity>> = repository.allEconomicOS
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val wealthMatrix: StateFlow<List<WealthMatrixEntity>> = repository.allWealthMatrix
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val opportunityGrid: StateFlow<List<OpportunityGridEntity>> = repository.allOpportunityGrid
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val demandMatrix: StateFlow<List<DemandMatrixEntity>> = repository.allDemandMatrix
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val capitalSupremacy: StateFlow<List<CapitalSupremacyEntity>> = repository.allCapitalSupremacy
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val tradeNetwork: StateFlow<List<TradeNetworkEntity>> = repository.allTradeNetwork
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val realityMatrix: StateFlow<List<RealityMatrixEntity>> = repository.allRealityMatrix
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val decisionEngine: StateFlow<List<DecisionEngineEntity>> = repository.allDecisionEngine
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val knowledgeMatrix: StateFlow<List<KnowledgeMatrixEntity>> = repository.allKnowledgeMatrix
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val innovationEngine: StateFlow<List<InnovationEngineEntity>> = repository.allInnovationEngine
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val protectionSystem: StateFlow<List<ProtectionSystemEntity>> = repository.allProtectionSystem
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val healthEngine: StateFlow<List<AbsoluteHealthEngineEntity>> = repository.allHealthEngine
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val absoluteCommandTower: StateFlow<List<AbsoluteCommandTowerEntity>> = repository.allAbsoluteCommandTower
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val unityEngine: StateFlow<List<UnityEngineEntity>> = repository.allUnityEngine
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _absoluteIntelligenceIndex = MutableStateFlow(100.0)
    val absoluteIntelligenceIndex: StateFlow<Double> = _absoluteIntelligenceIndex.asStateFlow()

    private val _isOperatingAutonomous = MutableStateFlow(false)
    val isOperatingAutonomous: StateFlow<Boolean> = _isOperatingAutonomous.asStateFlow()

    private val _statusMessage = MutableStateFlow<String?>(null)
    val statusMessage: StateFlow<String?> = _statusMessage.asStateFlow()

    init {
        initializeBaselineData()
    }

    private fun initializeBaselineData() {
        viewModelScope.launch {
            repository.runAbsoluteCore()
            repository.seedEconomicOS()
            repository.calculateWealthMatrix()
            repository.seedOpportunityGrid()
            repository.forecastDemandMatrix()
            repository.manageCapitalSupremacy()
            repository.optimizeTradeNetwork()
            repository.seedRealityMatrix()
            repository.executeDecisionEngine()
            repository.seedKnowledgeMatrix()
            repository.seedInnovationEngine()
            repository.seedProtectionSystem()
            repository.seedHealthEngine()
            repository.seedAbsoluteCommandTower()
            repository.seedUnityEngine()
            _absoluteIntelligenceIndex.value = repository.calculateAbsoluteIndex()
        }
    }

    fun triggerFullAbsoluteCycle() {
        viewModelScope.launch {
            _isOperatingAutonomous.value = true
            _statusMessage.value = "Initiating VASCS ABSOLUTE Universal Intelligence Cycle..."
            delay(500)

            repository.runAbsoluteCore()
            repository.calculateWealthMatrix()
            repository.forecastDemandMatrix()
            repository.manageCapitalSupremacy()
            repository.optimizeTradeNetwork()
            repository.executeDecisionEngine()
            val score = repository.calculateAbsoluteIndex()
            _absoluteIntelligenceIndex.value = score

            _statusMessage.value = "VASCS ABSOLUTE Synchronization Complete: One Commerce Brain Operational (Index: 100.0%)."
            delay(1200)
            _isOperatingAutonomous.value = false
        }
    }

    fun runAbsoluteCoreAction() {
        viewModelScope.launch {
            repository.runAbsoluteCore()
            _statusMessage.value = "Absolute Core Synchronized: Universal Control Rate 100.0%"
        }
    }

    fun calculateWealthMatrixAction() {
        viewModelScope.launch {
            repository.calculateWealthMatrix()
            _statusMessage.value = "Absolute Wealth Matrix Re-calculated: Continuous Autonomous Compounding Active"
        }
    }

    fun forecastDemandMatrixAction() {
        viewModelScope.launch {
            repository.forecastDemandMatrix()
            _statusMessage.value = "Absolute Demand Matrix Forecasted: Temporal Horizons Realized"
        }
    }

    fun manageCapitalSupremacyAction() {
        viewModelScope.launch {
            repository.manageCapitalSupremacy()
            _statusMessage.value = "Capital Supremacy Managed: Instant Quantum Sovereign Deployment"
        }
    }

    fun optimizeTradeNetworkAction() {
        viewModelScope.launch {
            repository.optimizeTradeNetwork()
            _statusMessage.value = "Trade Network Optimized: Seamless Clearance Rate 100.0%"
        }
    }

    fun addEconomicOSUnit(domain: String, name: String, law: String, stabilityPct: Double, nodes: Long) {
        viewModelScope.launch {
            repository.insertEconomicOS(
                EconomicOSEntity(
                    subsystemDomain = domain,
                    operatingSystemName = name,
                    governanceLaw = law,
                    kernelStabilityPct = stabilityPct,
                    economicOSIndex = 100.0,
                    activeUnifiedNodesCount = nodes,
                    executionState = "Absolute Sovereign OS Active"
                )
            )
            _statusMessage.value = "Registered Economic OS Subsystem: $name"
        }
    }

    fun addWealthMatrixStream(pillar: String, stream: String, volumeTrillion: Double, growthPct: Double) {
        viewModelScope.launch {
            repository.insertWealthMatrix(
                WealthMatrixEntity(
                    wealthPillar = pillar,
                    streamIdentifier = stream,
                    volumeTrillionUsd = volumeTrillion,
                    compoundGrowthRatePct = growthPct,
                    absoluteWealthIndex = 100.0,
                    compoundingVelocity = 99.99,
                    capitalAllocationStatus = "Continuous Autonomous Compounding"
                )
            )
            _statusMessage.value = "Added Wealth Stream: $stream"
        }
    }

    fun addOpportunityGridItem(horizon: String, concept: String, valueTrillion: Double, days: Int) {
        viewModelScope.launch {
            repository.insertOpportunityGrid(
                OpportunityGridEntity(
                    discoveryHorizon = horizon,
                    opportunityConcept = concept,
                    projectedValueTrillionUsd = valueTrillion,
                    timeToGenesisDays = days,
                    opportunityGridScore = 100.0,
                    realizationProbabilityPct = 99.9,
                    autonomousCatalystStrategy = "Instant Autonomous Seeding Grid"
                )
            )
            _statusMessage.value = "Synthesized Grid Opportunity: $concept"
        }
    }

    fun addDemandMatrixForecast(span: String, cluster: String, unitsMillion: Double) {
        viewModelScope.launch {
            repository.insertDemandMatrix(
                DemandMatrixEntity(
                    temporalSpan = span,
                    marketCluster = cluster,
                    predictedDemandMillionUnits = unitsMillion,
                    fulfillmentPrecisionPct = 99.99,
                    demandMatrixIndex = 100.0,
                    predictiveLatencyMs = 0.02,
                    autoBalancingAction = "Real-Time Direct Loom Dispatch"
                )
            )
            _statusMessage.value = "Added Demand Horizon Forecast: $span - $cluster"
        }
    }

    fun addCapitalSupremacyPool(sector: String, poolName: String, volumeBillion: Double, yieldPct: Double) {
        viewModelScope.launch {
            repository.insertCapitalSupremacy(
                CapitalSupremacyEntity(
                    capitalSector = sector,
                    fundOrPoolName = poolName,
                    managedVolumeBillionUsd = volumeBillion,
                    annualizedYieldPct = yieldPct,
                    capitalSupremacyIndex = 100.0,
                    reserveSolvencyRatioPct = 100.0,
                    deploymentMode = "Instant Quantum Sovereign Deployment"
                )
            )
            _statusMessage.value = "Allocated Capital Pool: $poolName"
        }
    }

    fun addTradeRoute(domain: String, routeMeshName: String, throughputBillion: Double) {
        viewModelScope.launch {
            repository.insertTradeNetwork(
                TradeNetworkEntity(
                    optimizationDomain = domain,
                    routeMeshName = routeMeshName,
                    throughputBillionUsdPerMonth = throughputBillion,
                    routingLatencyMs = 0.25,
                    tradeNetworkScore = 100.0,
                    seamlessClearanceRatePct = 100.0,
                    routeProtectionStatus = "Absolute Shielded Commerce Mesh"
                )
            )
            _statusMessage.value = "Integrated Trade Network Mesh: $routeMeshName"
        }
    }

    fun addDecisionPolicy(decisionType: String, policyTitle: String, impactTrillion: Double) {
        viewModelScope.launch {
            repository.insertDecisionEngine(
                DecisionEngineEntity(
                    decisionType = decisionType,
                    policyTitle = policyTitle,
                    economicImpactTrillionUsd = impactTrillion,
                    decisionAccuracyIndex = 100.0,
                    executionLatencyMicrosec = 5L,
                    confidenceRatePct = 100.0,
                    autonomousDirective = "Immediate Universal Execution"
                )
            )
            _statusMessage.value = "Enacted Absolute Decision Policy: $policyTitle"
        }
    }

    fun clearStatusMessage() {
        _statusMessage.value = null
    }

    class Factory(private val repository: VascsRepository) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(AbsoluteViewModel::class.java)) {
                return AbsoluteViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
