package com.example.vascs.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.vascs.data.model.*
import com.example.vascs.data.repository.VascsRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class AscensionViewModel(private val repository: VascsRepository) : ViewModel() {

    // 8 Required StateFlows as specified in Checkpoint 20.0
    val civilization: StateFlow<List<EconomicCivilizationEntity>> = repository.allCivilizations
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val resources: StateFlow<List<ResourceIntelligenceEntity>> = repository.allAscensionResources
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val tradeNetwork: StateFlow<List<TradeUniverseEntity>> = repository.allTradeUniverseRoutes
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val prosperity: StateFlow<List<ProsperityEngineEntity>> = repository.allProsperityRecords
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val innovation: StateFlow<List<InnovationUniverseEntity>> = repository.allAscensionInnovations
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val decisions: StateFlow<List<DecisionUniverseEntity>> = repository.allAscensionDecisions
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val health: StateFlow<List<AscensionHealthEntity>> = repository.allAscensionHealth
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val ascensionCores: StateFlow<List<AscensionCoreEntity>> = repository.allAscensionCores
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _ascensionIndex = MutableStateFlow(99.98)
    val ascensionIndex: StateFlow<Double> = _ascensionIndex.asStateFlow()

    private val _stabilityIndex = MutableStateFlow(99.95)
    val stabilityIndex: StateFlow<Double> = _stabilityIndex.asStateFlow()

    private val _prosperityScore = MutableStateFlow(99.92)
    val prosperityScore: StateFlow<Double> = _prosperityScore.asStateFlow()

    private val _evolutionIndex = MutableStateFlow(99.96)
    val evolutionIndex: StateFlow<Double> = _evolutionIndex.asStateFlow()

    private val _isSimulating = MutableStateFlow(false)
    val isSimulating: StateFlow<Boolean> = _isSimulating.asStateFlow()

    private val _telemetryFeed = MutableStateFlow<List<String>>(
        listOf(
            "[ASCENSION CORE] Economic Universe Controller active across 12 Sovereign Economic Civilizations.",
            "[CIVILIZATION ENGINE] Global Silk Federation & Indo-Pacific Luxury Consortium running at 99.94% Autonomy.",
            "[AI GOVERNMENT] 420 Macro-Stability policies dynamically balancing cross-border capital & inventory.",
            "[RESOURCE INTELLIGENCE] 1.25B USD Global Sovereign Reserve capacity optimized with +34.2% allocation gain.",
            "[TRADE UNIVERSE] 18 hyper-optimized frictionless trade corridors operating with zero tariff overhead.",
            "[PROSPERITY ENGINE] Cumulative artisan & guild wealth generation tracking at $4.85B USD (+48.6% YoY)."
        )
    )
    val telemetryFeed: StateFlow<List<String>> = _telemetryFeed.asStateFlow()

    init {
        seedInitialAscensionDataIfEmpty()
    }

    private fun seedInitialAscensionDataIfEmpty() {
        viewModelScope.launch {
            if (ascensionCores.value.isEmpty()) {
                runAscensionCore()
            }
            if (civilization.value.isEmpty()) {
                seedCivilizations()
            }
            if (resources.value.isEmpty()) {
                optimizeResources()
            }
            if (tradeNetwork.value.isEmpty()) {
                expandEconomy()
            }
            if (prosperity.value.isEmpty()) {
                calculateProsperity()
            }
            if (innovation.value.isEmpty()) {
                seedInnovations()
            }
            if (decisions.value.isEmpty()) {
                seedDecisions()
            }
            if (health.value.isEmpty()) {
                calculateAscensionIndex()
            }
        }
    }

    fun runAscensionCore() {
        viewModelScope.launch {
            _isSimulating.value = true
            repository.runAscensionCore(
                AscensionCoreEntity(
                    governanceStatus = "Self-Governing Universe Optimal",
                    civilizationCount = 12,
                    coordinatedEconomiesCount = 84,
                    globalResourceEfficiencyPct = 99.96,
                    universeStabilityIndex = 99.98,
                    activeEconomicPoliciesCount = 428,
                    growthMultiplier = 8.8,
                    controllerTelemetry = "VASCS Ascension Universal Controller synchronized across 12 Sovereign Economic Civilizations & 84 Dynamic Regional Hubs."
                )
            )
            appendLog("[ASCENSION CORE] Universal Controller re-calibrated. Stability: 99.98%, Efficiency: 99.96%.")
            delay(400)
            _isSimulating.value = false
        }
    }

    fun manageCivilization(
        name: String,
        zone: String,
        companies: Int,
        industries: Int,
        tradeBillionUsd: Double,
        autonomyPct: Double
    ) {
        viewModelScope.launch {
            _isSimulating.value = true
            val civ = EconomicCivilizationEntity(
                civilizationName = name,
                economicZone = zone,
                managedCompaniesCount = companies,
                managedIndustriesCount = industries,
                totalTradeVolumeBillionUsd = tradeBillionUsd,
                autonomyLevelPct = autonomyPct,
                civilizationStatus = "Fully Autonomous & Coordinated",
                growthRatePct = 42.5
            )
            repository.manageCivilization(civ)
            appendLog("[CIVILIZATION ENGINE] Civilization '$name' ($zone) integrated into Autonomous Universe.")
            delay(300)
            _isSimulating.value = false
        }
    }

    private fun seedCivilizations() {
        viewModelScope.launch {
            val list = listOf(
                EconomicCivilizationEntity(
                    civilizationName = "Global Silk & Heritage Federation",
                    economicZone = "Indo-Pacific & South Asia Hub",
                    managedCompaniesCount = 480,
                    managedIndustriesCount = 18,
                    totalTradeVolumeBillionUsd = 14.8,
                    autonomyLevelPct = 99.8,
                    civilizationStatus = "Autonomous Sovereign Organism",
                    growthRatePct = 52.4
                ),
                EconomicCivilizationEntity(
                    civilizationName = "Indo-Pacific Luxury Consortium",
                    economicZone = "ASEAN & East Asian Corridors",
                    managedCompaniesCount = 320,
                    managedIndustriesCount = 14,
                    totalTradeVolumeBillionUsd = 11.2,
                    autonomyLevelPct = 99.4,
                    civilizationStatus = "Self-Expanding Macro Network",
                    growthRatePct = 46.8
                ),
                EconomicCivilizationEntity(
                    civilizationName = "Euro-Atlantic Smart Textile Guild",
                    economicZone = "Western Europe & North America",
                    managedCompaniesCount = 290,
                    managedIndustriesCount = 12,
                    totalTradeVolumeBillionUsd = 9.6,
                    autonomyLevelPct = 99.2,
                    civilizationStatus = "Coordinated Autonomous Guild",
                    growthRatePct = 38.2
                ),
                EconomicCivilizationEntity(
                    civilizationName = "Pan-African Craft & Bio-Fiber DAO",
                    economicZone = "Sub-Saharan & North Africa",
                    managedCompaniesCount = 180,
                    managedIndustriesCount = 9,
                    totalTradeVolumeBillionUsd = 4.5,
                    autonomyLevelPct = 98.9,
                    civilizationStatus = "High-Velocity Expansion",
                    growthRatePct = 64.0
                )
            )
            repository.insertCivilizations(list)
        }
    }

    fun optimizeResources() {
        viewModelScope.launch {
            _isSimulating.value = true
            val list = listOf(
                ResourceIntelligenceEntity(
                    resourceCategory = "Capital & Liquidity Pool",
                    resourceName = "Global Sovereign Reserve Liquidity Grid",
                    allocatedCapacityUsdMillion = 1450.0,
                    utilizationRatePct = 97.4,
                    optimizationGainPct = 36.8,
                    bottleneckRiskLevel = "Minimal",
                    recommendedActionPlan = "Instant algorithmic yield re-allocation into high-demand automated Surat jacquard looms."
                ),
                ResourceIntelligenceEntity(
                    resourceCategory = "Inventory & Raw Material",
                    resourceName = "Sovereign Raw Silk Cocoon & Zari Reserve Buffer",
                    allocatedCapacityUsdMillion = 680.0,
                    utilizationRatePct = 94.2,
                    optimizationGainPct = 28.4,
                    bottleneckRiskLevel = "Minimal",
                    recommendedActionPlan = "Autonomous dynamic inventory forward-hedging against seasonal monsoon variations."
                ),
                ResourceIntelligenceEntity(
                    resourceCategory = "Supply & Logistics Fleet",
                    resourceName = "Autonomous Cross-Border Air & Sea Cargo Corridors",
                    allocatedCapacityUsdMillion = 890.0,
                    utilizationRatePct = 96.0,
                    optimizationGainPct = 41.5,
                    bottleneckRiskLevel = "Balanced",
                    recommendedActionPlan = "Smart route rerouting via Dubai and Frankfurt multimodal air hubs for 24h global dispatch."
                ),
                ResourceIntelligenceEntity(
                    resourceCategory = "Technology & AI Compute",
                    resourceName = "Distributed Quantum & Neural Generative Cluster",
                    allocatedCapacityUsdMillion = 420.0,
                    utilizationRatePct = 98.6,
                    optimizationGainPct = 54.0,
                    bottleneckRiskLevel = "Minimal",
                    recommendedActionPlan = "Auto-scale 8,192 tensor compute nodes for zero-shot 3D drape rendering & generative catalogue drops."
                )
            )
            repository.insertResources(list)
            appendLog("[RESOURCE INTELLIGENCE] 4 Sovereign Resource Dimensions optimized. Composite gain: +40.2%.")
            delay(400)
            _isSimulating.value = false
        }
    }

    fun expandEconomy(
        origin: String = "India (Surat/Varanasi Silk Clusters)",
        destination: String = "North America & GCC Luxury Sovereign Corridors",
        industries: String = "Heritage Handloom, Smart Weave IoT, Luxury Retail, Diaspora Fashion",
        throughputMillionUsd: Double = 940.0
    ) {
        viewModelScope.launch {
            _isSimulating.value = true
            val route = TradeUniverseEntity(
                originRegion = origin,
                destinationMarket = destination,
                connectedIndustries = industries,
                activeBusinessesCount = 1840,
                tradeThroughputUsdMillion = throughputMillionUsd,
                tradeEfficiencyScore = 99.92,
                tariffOptimizationPct = 96.4,
                routeHealthStatus = "Hyper-Optimized & Frictionless"
            )
            repository.expandEconomy(route)
            appendLog("[TRADE UNIVERSE] Trade Route from '$origin' to '$destination' established. Throughput: $$throughputMillionUsd M.")
            delay(300)
            _isSimulating.value = false
        }
    }

    fun calculateProsperity() {
        viewModelScope.launch {
            _isSimulating.value = true
            val list = listOf(
                ProsperityEngineEntity(
                    economicDomain = "Global Artisan Wealth & Smart Loom Enterprise Guilds",
                    cumulativeWealthUsdMillion = 5420.0,
                    annualGrowthRatePct = 52.4,
                    allocatedCapitalUsdMillion = 1100.0,
                    generatedEconomicValueUsdMillion = 7280.0,
                    prosperityIndex = 99.94,
                    equityDistributionGiniIndex = 0.12
                ),
                ProsperityEngineEntity(
                    economicDomain = "Autonomous Sovereign Reserve & Equity Fund",
                    cumulativeWealthUsdMillion = 3850.0,
                    annualGrowthRatePct = 44.0,
                    allocatedCapitalUsdMillion = 850.0,
                    generatedEconomicValueUsdMillion = 5490.0,
                    prosperityIndex = 99.91,
                    equityDistributionGiniIndex = 0.15
                ),
                ProsperityEngineEntity(
                    economicDomain = "Diaspora Commerce & Cross-Border Retail Pool",
                    cumulativeWealthUsdMillion = 2940.0,
                    annualGrowthRatePct = 58.6,
                    allocatedCapitalUsdMillion = 620.0,
                    generatedEconomicValueUsdMillion = 4120.0,
                    prosperityIndex = 99.89,
                    equityDistributionGiniIndex = 0.14
                )
            )
            repository.insertProsperities(list)
            _prosperityScore.value = 99.93
            appendLog("[PROSPERITY ENGINE] Global Wealth & Equity distribution recalculated. Prosperity Index: 99.93%.")
            delay(300)
            _isSimulating.value = false
        }
    }

    private fun seedInnovations() {
        viewModelScope.launch {
            val list = listOf(
                InnovationUniverseEntity(
                    innovationTitle = "Bio-Engineered Heritage Mulberry Silk Nanofibers",
                    innovationType = "Breakthrough Material Science",
                    patentIdentifier = "VASCS-PAT-2026-NANO-091",
                    economicPotentialUsdMillion = 1450.0,
                    readinessStage = "Global Standard Deployment",
                    innovationIndex = 99.88,
                    disruptionFactorPct = 88.5
                ),
                InnovationUniverseEntity(
                    innovationTitle = "Autonomous Zero-Lag Multi-Currency Settlement Grid",
                    innovationType = "Autonomous Economic Model",
                    patentIdentifier = "VASCS-PAT-2026-GRID-412",
                    economicPotentialUsdMillion = 2800.0,
                    readinessStage = "Scaled Deployment",
                    innovationIndex = 99.95,
                    disruptionFactorPct = 94.2
                ),
                InnovationUniverseEntity(
                    innovationTitle = "Photonic Smart Weave Optical Sensor Drapes",
                    innovationType = "Sovereign Hardware Patent",
                    patentIdentifier = "VASCS-PAT-2026-WEAVE-784",
                    economicPotentialUsdMillion = 920.0,
                    readinessStage = "Commercial Scaling",
                    innovationIndex = 99.76,
                    disruptionFactorPct = 79.4
                )
            )
            repository.insertInnovations(list)
        }
    }

    private fun seedDecisions() {
        viewModelScope.launch {
            val list = listOf(
                DecisionUniverseEntity(
                    decisionCategory = "Investments & Expansion",
                    decisionTitle = "Deploy $180M Capital into Surat & Varanasi 100% Autonomous Mega-Weave Parks",
                    proposedAction = "Instant capital transfer from Sovereign Reserve into smart-loom infrastructure",
                    expectedEconomicImpactUsdMillion = 640.0,
                    decisionAccuracyScore = 99.92,
                    confidenceIntervalPct = 99.4,
                    executionState = "Autonomous Enacted"
                ),
                DecisionUniverseEntity(
                    decisionCategory = "Dynamic Pricing & Currency Balancing",
                    decisionTitle = "Synchronize Real-Time Dynamic Export Tariffs for North American Boutiques",
                    proposedAction = "Zero-latency price auto-hedging against USD/INR fluctuations",
                    expectedEconomicImpactUsdMillion = 145.0,
                    decisionAccuracyScore = 99.96,
                    confidenceIntervalPct = 99.8,
                    executionState = "Autonomous Enacted"
                ),
                DecisionUniverseEntity(
                    decisionCategory = "Resource Allocation",
                    decisionTitle = "Pre-Charter Dedicated Emirates & Lufthansa SkyCargo Bays for Q4 Festive Peak",
                    proposedAction = "Lock 450 metric ton cargo capacity at 28% below spot rates",
                    expectedEconomicImpactUsdMillion = 85.0,
                    decisionAccuracyScore = 99.84,
                    confidenceIntervalPct = 98.9,
                    executionState = "Autonomous Enacted"
                )
            )
            repository.insertDecisions(list)
        }
    }

    fun calculateAscensionIndex() {
        viewModelScope.launch {
            _isSimulating.value = true
            val healthItems = listOf(
                AscensionHealthEntity(
                    dimensionName = "Economic Health",
                    score = 99.96,
                    status = "Optimal",
                    diagnosticSummary = "Autonomous monetary equilibrium with zero liquidity leakage across all 84 dynamic regional markets."
                ),
                AscensionHealthEntity(
                    dimensionName = "Trade Health",
                    score = 99.91,
                    status = "Resilient",
                    diagnosticSummary = "Zero-friction cross-border customs & tariff pre-clearance with 18 international trade corridors."
                ),
                AscensionHealthEntity(
                    dimensionName = "Growth Health",
                    score = 99.98,
                    status = "Exceptional",
                    diagnosticSummary = "Compound expansion velocity operating at +48.6% YoY with automated market seeding."
                ),
                AscensionHealthEntity(
                    dimensionName = "Innovation Health",
                    score = 99.94,
                    status = "Optimal",
                    diagnosticSummary = "Active patent portfolio generating $5.1B USD in commercialized nanotechnology & automated frameworks."
                ),
                AscensionHealthEntity(
                    dimensionName = "Civilization Health",
                    score = 99.97,
                    status = "Optimal",
                    diagnosticSummary = "Harmonious 12-Civilization governance coordination with 99.98% macroeconomic stability."
                )
            )
            repository.calculateAscensionIndex(healthItems)
            _ascensionIndex.value = 99.98
            _stabilityIndex.value = 99.95
            appendLog("[ASCENSION HEALTH] Universal Health Index assessed at 99.98%. All 5 Macro-Dimensions Optimal.")
            delay(400)
            _isSimulating.value = false
        }
    }

    fun enactGovernmentPolicy(title: String, domain: String, stabilityDelta: Double) {
        viewModelScope.launch {
            _isSimulating.value = true
            _stabilityIndex.value = (_stabilityIndex.value + stabilityDelta).coerceIn(90.0, 100.0)
            appendLog("[AI GOVERNMENT] Macro Policy Enacted: '$title' in domain $domain. Stability updated to ${_stabilityIndex.value}%.")
            delay(300)
            _isSimulating.value = false
        }
    }

    fun simulateCivilizationTwin(civilizationName: String) {
        viewModelScope.launch {
            _isSimulating.value = true
            appendLog("[DIGITAL CIVILIZATION] Running 100,000,000 Monte Carlo macro-economic iterations for '$civilizationName'...")
            delay(600)
            appendLog("[DIGITAL CIVILIZATION] Simulation Complete: Projected 3-year growth +380%, systemic failure risk: 0.001%.")
            _isSimulating.value = false
        }
    }

    fun triggerEvolution() {
        viewModelScope.launch {
            _isSimulating.value = true
            _evolutionIndex.value = (_evolutionIndex.value + 0.01).coerceAtMost(99.99)
            appendLog("[EVOLUTION INTELLIGENCE] AI Autonomous Evolutionary Cycle completed. New capabilities deployed to all 12 Civilizations.")
            delay(400)
            _isSimulating.value = false
        }
    }

    private fun appendLog(message: String) {
        val current = _telemetryFeed.value.toMutableList()
        if (current.size > 25) current.removeAt(current.size - 1)
        current.add(0, message)
        _telemetryFeed.value = current
    }
}

class AscensionViewModelFactory(
    private val repository: VascsRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AscensionViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AscensionViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}
