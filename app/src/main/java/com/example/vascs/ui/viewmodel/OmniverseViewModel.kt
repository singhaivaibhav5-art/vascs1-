package com.example.vascs.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.vascs.data.model.*
import com.example.vascs.data.repository.VascsRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class OmniverseViewModel(private val repository: VascsRepository) : ViewModel() {

    // 8 Required StateFlows as specified in Checkpoint 21.0
    val economies: StateFlow<List<EconomyNetworkEntity>> = repository.allEconomyNetworks
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val markets: StateFlow<List<MarketMatrixEntity>> = repository.allMarketMatrices
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val tradeGrid: StateFlow<List<TradeGridEntity>> = repository.allTradeGrids
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val opportunities: StateFlow<List<OpportunityUniverseEntity>> = repository.allOpportunityUniverses
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val innovations: StateFlow<List<OmniverseInnovationEntity>> = repository.allOmniverseInnovations
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val risks: StateFlow<List<OmniverseRiskEntity>> = repository.allOmniverseRisks
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val health: StateFlow<List<OmniverseHealthEntity>> = repository.allOmniverseHealth
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val cores: StateFlow<List<OmniverseCoreEntity>> = repository.allOmniverseCores
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val knowledge: StateFlow<List<KnowledgeFabricEntity>> = repository.allKnowledgeFabrics
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val industries: StateFlow<List<IndustryMatrixEntity>> = repository.allIndustryMatrices
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _omniverseIndex = MutableStateFlow(99.99)
    val omniverseIndex: StateFlow<Double> = _omniverseIndex.asStateFlow()

    private val _realitySyncIndex = MutableStateFlow(99.98)
    val realitySyncIndex: StateFlow<Double> = _realitySyncIndex.asStateFlow()

    private val _wealthIndex = MutableStateFlow(99.96)
    val wealthIndex: StateFlow<Double> = _wealthIndex.asStateFlow()

    private val _evolutionIndex = MutableStateFlow(99.99)
    val evolutionIndex: StateFlow<Double> = _evolutionIndex.asStateFlow()

    private val _decisionPrecisionScore = MutableStateFlow(99.97)
    val decisionPrecisionScore: StateFlow<Double> = _decisionPrecisionScore.asStateFlow()

    private val _isSimulating = MutableStateFlow(false)
    val isSimulating: StateFlow<Boolean> = _isSimulating.asStateFlow()

    private val _telemetryFeed = MutableStateFlow<List<String>>(
        listOf(
            "[OMNIVERSE CORE] Universal Intelligence Fabric synchronized across 142 Global & Virtual Economies.",
            "[REALITY SYNCHRONIZATION] 88 Digital Business & Industry Twins streaming at 99.98% Fidelity.",
            "[TRADE GRID] 14,200 Autonomous Trade Nodes active with 2ms sub-friction latency.",
            "[MARKET MATRIX] Real-time consumer sentiment & demand matrix aligned across 84 international hubs.",
            "[OPPORTUNITY UNIVERSE] 12 High-Yield Autonomous Opportunities detected with $2.87B addressable potential.",
            "[UNIVERSAL HEALTH] Omniverse Health Index scored at 99.99% across all 5 Macro Dimensions."
        )
    )
    val telemetryFeed: StateFlow<List<String>> = _telemetryFeed.asStateFlow()

    init {
        seedInitialOmniverseDataIfEmpty()
    }

    private fun seedInitialOmniverseDataIfEmpty() {
        viewModelScope.launch {
            if (cores.value.isEmpty()) {
                runOmniverseCore()
            }
            if (economies.value.isEmpty()) {
                analyzeEconomies()
            }
            if (markets.value.isEmpty()) {
                synchronizeMarkets()
            }
            if (tradeGrid.value.isEmpty()) {
                optimizeTradeGrid()
            }
            if (opportunities.value.isEmpty()) {
                generateOpportunities()
            }
            if (innovations.value.isEmpty()) {
                seedInnovations()
            }
            if (risks.value.isEmpty()) {
                seedRisks()
            }
            if (health.value.isEmpty()) {
                calculateOmniverseIndex()
            }
            if (knowledge.value.isEmpty()) {
                seedKnowledge()
            }
            if (industries.value.isEmpty()) {
                seedIndustries()
            }
        }
    }

    fun runOmniverseCore() {
        viewModelScope.launch {
            _isSimulating.value = true
            repository.runOmniverseCore(
                OmniverseCoreEntity(
                    consciousnessStatus = "Universal Intelligence Active & Self-Governing",
                    connectedEconomiesCount = 142,
                    synchronizedRealitiesCount = 88,
                    universalIntelligenceScore = 99.99,
                    realitySynchronizationPct = 99.98,
                    crossSystemGovernanceStabilityPct = 99.97,
                    infiniteEvolutionVelocity = 14.8,
                    controllerTelemetry = "VASCS Omniverse Controller orchestrating unified AI consciousness across physical & virtual business realities."
                )
            )
            appendLog("[OMNIVERSE CORE] Universal Intelligence Fabric re-synchronized. Stability: 99.99%, Velocity: 14.8x.")
            delay(400)
            _isSimulating.value = false
        }
    }

    fun analyzeEconomies() {
        viewModelScope.launch {
            _isSimulating.value = true
            repository.analyzeEconomies()
            appendLog("[ECONOMY NETWORK] 4 Macro-Economic Layers analyzed (Global, Regional, Local, Virtual). Total GDP: $68.9B USD.")
            delay(350)
            _isSimulating.value = false
        }
    }

    fun addEconomy(
        name: String,
        scope: String,
        entities: Int,
        gdpBillionUsd: Double,
        growthPct: Double,
        currency: String
    ) {
        viewModelScope.launch {
            _isSimulating.value = true
            val economy = EconomyNetworkEntity(
                economyName = name,
                economyScope = scope,
                activeEntitiesCount = entities,
                totalGdpBillionUsd = gdpBillionUsd,
                growthRateYoYPct = growthPct,
                autonomyLevelPct = 99.8,
                networkInterconnectednessScore = 99.94,
                currencyRegime = currency
            )
            repository.insertEconomy(economy)
            appendLog("[ECONOMY NETWORK] Economy '$name' ($scope) linked into Universal Intelligence Fabric.")
            delay(300)
            _isSimulating.value = false
        }
    }

    fun synchronizeMarkets() {
        viewModelScope.launch {
            _isSimulating.value = true
            repository.synchronizeMarkets()
            appendLog("[MARKET MATRIX] Global demand & consumer sentiment matrix synchronized across 3 Key Geographies.")
            delay(350)
            _isSimulating.value = false
        }
    }

    fun addMarket(
        name: String,
        region: String,
        demand: Double,
        supply: Double,
        sentiment: Double,
        signal: String
    ) {
        viewModelScope.launch {
            _isSimulating.value = true
            val market = MarketMatrixEntity(
                marketName = name,
                geographicRegion = region,
                aggregateDemandIndex = demand,
                supplyCapacityPct = supply,
                consumerSentimentScore = sentiment,
                marketSignalSummary = signal,
                emergingOpportunitiesCount = 34,
                marketEfficiencyPct = 99.91
            )
            repository.insertMarket(market)
            appendLog("[MARKET MATRIX] Market '$name' ($region) mapped into Universal Market Index.")
            delay(300)
            _isSimulating.value = false
        }
    }

    fun optimizeTradeGrid() {
        viewModelScope.launch {
            _isSimulating.value = true
            repository.optimizeTradeGrid()
            appendLog("[TRADE GRID] Omniverse Trade Grid optimized. Cross-tier latency: 2-4ms, Efficiency: 99.96%.")
            delay(350)
            _isSimulating.value = false
        }
    }

    fun addTradeNode(
        title: String,
        tier: String,
        endpoints: Int,
        throughputMillionUsd: Double,
        efficiency: Double
    ) {
        viewModelScope.launch {
            _isSimulating.value = true
            val grid = TradeGridEntity(
                tradeNodeTitle = title,
                nodeTier = tier,
                connectedEndpointsCount = endpoints,
                volumeThroughputMillionUsd = throughputMillionUsd,
                frictionLagMs = 3,
                tradeEfficiencyScore = efficiency,
                tariffOptimizationPct = 98.6,
                gridHealthStatus = "Autonomous Flow"
            )
            repository.insertTradeGrid(grid)
            appendLog("[TRADE GRID] Node '$title' ($tier) connected to Global Trade Grid.")
            delay(300)
            _isSimulating.value = false
        }
    }

    fun generateOpportunities() {
        viewModelScope.launch {
            _isSimulating.value = true
            repository.generateOpportunities()
            appendLog("[OPPORTUNITY UNIVERSE] 3 High-Yield Opportunities generated with $2.87B addressable potential.")
            delay(350)
            _isSimulating.value = false
        }
    }

    fun addOpportunity(
        title: String,
        category: String,
        valueMillionUsd: Double,
        timeMonths: Int,
        probPct: Double,
        plan: String
    ) {
        viewModelScope.launch {
            _isSimulating.value = true
            val opp = OpportunityUniverseEntity(
                opportunityTitle = title,
                opportunityCategory = category,
                addressableValueMillionUsd = valueMillionUsd,
                timeToMaturityMonths = timeMonths,
                captureProbabilityPct = probPct,
                strategicActionPlan = plan,
                universeOpportunityScore = 99.92,
                executionStage = "Autonomous Capital Allocated"
            )
            repository.insertOpportunity(opp)
            appendLog("[OPPORTUNITY UNIVERSE] Opportunity '$title' ($category) generated and staged.")
            delay(300)
            _isSimulating.value = false
        }
    }

    fun calculateOmniverseIndex() {
        viewModelScope.launch {
            _isSimulating.value = true
            repository.calculateOmniverseIndex()
            _omniverseIndex.value = 99.99
            _realitySyncIndex.value = 99.98
            appendLog("[OMNIVERSE HEALTH] Omniverse Intelligence Index assessed at 99.99%. All 5 Macro Dimensions Optimal.")
            delay(350)
            _isSimulating.value = false
        }
    }

    private fun seedInnovations() {
        viewModelScope.launch {
            val list = listOf(
                OmniverseInnovationEntity(
                    innovationTitle = "Quantum-Entangled Supply Ledger & Smart Contract Fabric",
                    innovationClass = "Universal Standard",
                    potentialYieldMillionUsd = 2400.0,
                    expansionIndex = 99.95,
                    deploymentStatus = "Global Deployment",
                    patentIdentifier = "VASCS-OMNI-PAT-001"
                ),
                OmniverseInnovationEntity(
                    innovationTitle = "Self-Weaving Photonic Nano-Zari Fiber Array",
                    innovationClass = "Technology Patent",
                    potentialYieldMillionUsd = 1850.0,
                    expansionIndex = 99.88,
                    deploymentStatus = "Commercial Scaling",
                    patentIdentifier = "VASCS-OMNI-PAT-002"
                ),
                OmniverseInnovationEntity(
                    innovationTitle = "Autonomous Cross-Reality Value Transfer Protocol",
                    innovationClass = "Business Model",
                    potentialYieldMillionUsd = 3100.0,
                    expansionIndex = 99.98,
                    deploymentStatus = "Active Deployment",
                    patentIdentifier = "VASCS-OMNI-PAT-003"
                )
            )
            repository.insertOmniverseInnovations(list)
        }
    }

    private fun seedRisks() {
        viewModelScope.launch {
            val list = listOf(
                OmniverseRiskEntity(
                    riskDomain = "Supply Risk",
                    riskTitle = "Monsoon Cocoon Yield Fluctuation across South Indian Silk Belts",
                    severityLevel = "Controlled",
                    exposureValueMillionUsd = 45.0,
                    automatedMitigationStrategy = "Autonomous forward-hedging via Varanasi sovereign raw buffer reserves.",
                    riskResilienceScore = 99.85
                ),
                OmniverseRiskEntity(
                    riskDomain = "Economic Risk",
                    riskTitle = "USD/INR & EUR Foreign Exchange Micro-Volatilities",
                    severityLevel = "Minimal",
                    exposureValueMillionUsd = 28.0,
                    automatedMitigationStrategy = "Zero-latency multi-currency dynamic auto-balancing and instant ledger conversion.",
                    riskResilienceScore = 99.96
                ),
                OmniverseRiskEntity(
                    riskDomain = "Market Risk",
                    riskTitle = "Sudden Global Festive Shift toward Bio-Organic Handlooms",
                    severityLevel = "Controlled",
                    exposureValueMillionUsd = 32.0,
                    automatedMitigationStrategy = "Instant re-allocation of 1,200 Surat jacquard looms to 100% organic herbal dyed yarns.",
                    riskResilienceScore = 99.92
                )
            )
            repository.insertRisks(list)
        }
    }

    private fun seedKnowledge() {
        viewModelScope.launch {
            val list = listOf(
                KnowledgeFabricEntity(
                    domainCategory = "Business Knowledge",
                    knowledgeTopic = "5,000-Year Heritage Weaving Wisdom & Global Luxury Positioning",
                    indexedNodesCount = 142000000L,
                    synthesisDepthLevel = "Deep Neural Cognition",
                    reasoningConfidencePct = 99.98,
                    predictiveAccuracyPct = 99.95,
                    actionableInsightsSummary = "Harmonize timeless artisanal motifs with contemporary diaspora couture demand curves."
                ),
                KnowledgeFabricEntity(
                    domainCategory = "Economic Knowledge",
                    knowledgeTopic = "Universal Macroeconomic Equilibrium & Multi-Tier Sovereign Reserve Dynamics",
                    indexedNodesCount = 890000000L,
                    synthesisDepthLevel = "Omniverse Continuum Synthesis",
                    reasoningConfidencePct = 99.99,
                    predictiveAccuracyPct = 99.97,
                    actionableInsightsSummary = "Autonomous liquidity pooling preventing capital bottlenecks across artisan guilds."
                ),
                KnowledgeFabricEntity(
                    domainCategory = "Future Knowledge",
                    knowledgeTopic = "Predictive Demand Horizons & 10-Year Global Textile Evolution",
                    indexedNodesCount = 450000000L,
                    synthesisDepthLevel = "Quantum Probability Manifold",
                    reasoningConfidencePct = 99.94,
                    predictiveAccuracyPct = 99.91,
                    actionableInsightsSummary = "Anticipate 4.8x surge in smart connected wearables with integrated NFC authentication."
                )
            )
            repository.insertKnowledgeList(list)
        }
    }

    private fun seedIndustries() {
        viewModelScope.launch {
            val list = listOf(
                IndustryMatrixEntity(
                    industrySector = "Retail & Luxury Fashion",
                    activeClustersCount = 4200,
                    sectoralMarketCapBillionUsd = 14.8,
                    transformationVelocityPct = 68.4,
                    crossIndustrySynergyScore = 99.88,
                    aiIntegrationLevelPct = 99.92,
                    keyDisruptionVector = "Generative 3D Virtual Try-On Mirrors & Loom-to-Wardrobe Logistics"
                ),
                IndustryMatrixEntity(
                    industrySector = "Manufacturing & Advanced Textiles",
                    activeClustersCount = 2800,
                    sectoralMarketCapBillionUsd = 19.5,
                    transformationVelocityPct = 74.2,
                    crossIndustrySynergyScore = 99.94,
                    aiIntegrationLevelPct = 99.85,
                    keyDisruptionVector = "Autonomous Robotic Jacquard Looms & Bio-Engineered Silk Nanotech"
                ),
                IndustryMatrixEntity(
                    industrySector = "Finance & Sovereign Liquidity",
                    activeClustersCount = 1400,
                    sectoralMarketCapBillionUsd = 22.0,
                    transformationVelocityPct = 82.0,
                    crossIndustrySynergyScore = 99.99,
                    aiIntegrationLevelPct = 100.0,
                    keyDisruptionVector = "Zero-Intermediary Liquidity Staking & Micro-Equity for Rural Artisans"
                ),
                IndustryMatrixEntity(
                    industrySector = "Technology & AI Compute",
                    activeClustersCount = 950,
                    sectoralMarketCapBillionUsd = 12.6,
                    transformationVelocityPct = 94.0,
                    crossIndustrySynergyScore = 99.96,
                    aiIntegrationLevelPct = 100.0,
                    keyDisruptionVector = "Edge AI Design Synthesis & Sub-Millisecond Global Order Routing"
                )
            )
            repository.insertIndustries(list)
        }
    }

    fun triggerEvolution() {
        viewModelScope.launch {
            _isSimulating.value = true
            _evolutionIndex.value = (_evolutionIndex.value + 0.005).coerceAtMost(99.999)
            appendLog("[EVOLUTION UNIVERSE] Universal Autonomous Evolution cycle completed. Upgraded 142 Economies & 6 Industries.")
            delay(400)
            _isSimulating.value = false
        }
    }

    fun executeDecision(title: String, impactMillionUsd: Double) {
        viewModelScope.launch {
            _isSimulating.value = true
            _decisionPrecisionScore.value = 99.98
            appendLog("[DECISION UNIVERSE] Autonomous Decision Executed: '$title' (Impact: $$impactMillionUsd M). Precision: 99.98%.")
            delay(350)
            _isSimulating.value = false
        }
    }

    fun simulateRealityTwin(twinName: String) {
        viewModelScope.launch {
            _isSimulating.value = true
            appendLog("[DIGITAL REALITY] Simulating 1,000,000,000 parallel universe states for Digital Reality Twin '$twinName'...")
            delay(600)
            appendLog("[DIGITAL REALITY] Reality Twin '$twinName' simulation verified: 99.99% convergence on optimal growth pathway.")
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

class OmniverseViewModelFactory(
    private val repository: VascsRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(OmniverseViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return OmniverseViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}
