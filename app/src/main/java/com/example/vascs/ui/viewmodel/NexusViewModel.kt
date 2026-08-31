package com.example.vascs.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vascs.data.model.*
import com.example.vascs.data.repository.VascsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class NexusViewModel(
    private val repository: VascsRepository
) : ViewModel() {

    private val _nexusCore = MutableStateFlow<List<NexusCoreEntity>>(emptyList())
    val nexusCore: StateFlow<List<NexusCoreEntity>> = _nexusCore.asStateFlow()

    private val _enterpriseNetwork = MutableStateFlow<List<EnterpriseNetworkEntity>>(emptyList())
    val enterpriseNetwork: StateFlow<List<EnterpriseNetworkEntity>> = _enterpriseNetwork.asStateFlow()

    private val _knowledgeWeb = MutableStateFlow<List<KnowledgeWebEntity>>(emptyList())
    val knowledgeWeb: StateFlow<List<KnowledgeWebEntity>> = _knowledgeWeb.asStateFlow()

    private val _partnerships = MutableStateFlow<List<PartnershipNetworkEntity>>(emptyList())
    val partnerships: StateFlow<List<PartnershipNetworkEntity>> = _partnerships.asStateFlow()

    private val _opportunities = MutableStateFlow<List<OpportunityExchangeEntity>>(emptyList())
    val opportunities: StateFlow<List<OpportunityExchangeEntity>> = _opportunities.asStateFlow()

    private val _decisions = MutableStateFlow<List<DecisionExchangeEntity>>(emptyList())
    val decisions: StateFlow<List<DecisionExchangeEntity>> = _decisions.asStateFlow()

    private val _nexusHealth = MutableStateFlow<List<NexusHealthEntity>>(emptyList())
    val nexusHealth: StateFlow<List<NexusHealthEntity>> = _nexusHealth.asStateFlow()

    private val _nexusIndex = MutableStateFlow(99.88)
    val nexusIndex: StateFlow<Double> = _nexusIndex.asStateFlow()

    init {
        loadData()
        seedInitialDataIfEmpty()
    }

    private fun loadData() {
        viewModelScope.launch {
            repository.allNexusCore.collect { list ->
                if (list.isNotEmpty()) _nexusCore.value = list
            }
        }
        viewModelScope.launch {
            repository.allEnterpriseNetwork.collect { list ->
                if (list.isNotEmpty()) _enterpriseNetwork.value = list
            }
        }
        viewModelScope.launch {
            repository.allKnowledgeWeb.collect { list ->
                if (list.isNotEmpty()) _knowledgeWeb.value = list
            }
        }
        viewModelScope.launch {
            repository.allPartnershipNetwork.collect { list ->
                if (list.isNotEmpty()) _partnerships.value = list
            }
        }
        viewModelScope.launch {
            repository.allOpportunityExchange.collect { list ->
                if (list.isNotEmpty()) _opportunities.value = list
            }
        }
        viewModelScope.launch {
            repository.allDecisionExchange.collect { list ->
                if (list.isNotEmpty()) _decisions.value = list
            }
        }
        viewModelScope.launch {
            repository.allNexusHealth.collect { list ->
                if (list.isNotEmpty()) {
                    _nexusHealth.value = list
                    _nexusIndex.value = list.first().nexusHealthIndex
                }
            }
        }
    }

    private fun seedInitialDataIfEmpty() {
        viewModelScope.launch {
            val defaultCores = listOf(
                NexusCoreEntity(
                    systemName = "VASCS Universal Nexus Kernel Alpha",
                    connectivityStatus = "GLOBAL_CONNECTED (190 Countries)",
                    synchronizationMode = "Quantum Mesh Intelligence Sync",
                    enterpriseCoordination = "100% Autonomous Multi-Enterprise Grid",
                    networkGovernance = "Decentralized AI Alliance DAO",
                    activeEnterprisesCount = 28400,
                    syncedNodesCount = 195000,
                    networkLatencyMs = 0.12,
                    throughputTps = 32000000
                ),
                NexusCoreEntity(
                    systemName = "Eurasia-Pacific Synchronizer Node",
                    connectivityStatus = "HIGH_THROUGHPUT_SYNC",
                    synchronizationMode = "Ultra-Low Latency Optical Mesh",
                    enterpriseCoordination = "Autonomous Cross-Border Clearance",
                    networkGovernance = "Consensus AI Protocol",
                    activeEnterprisesCount = 14200,
                    syncedNodesCount = 98000,
                    networkLatencyMs = 0.08,
                    throughputTps = 18500000
                )
            )
            defaultCores.forEach { repository.runNexusCore(it) }
            _nexusCore.value = defaultCores

            val defaultGrid = listOf(
                EnterpriseNetworkEntity(
                    enterpriseName = "Global Heritage Silk & Handloom Alliance",
                    entityType = "Connected Enterprise Grid",
                    regionOrCountry = "Global / North America, GCC, India, Europe",
                    connectedBranchesCount = 48,
                    connectedFactoriesCount = 16,
                    connectedWarehousesCount = 64,
                    connectedDealersCount = 1850,
                    connectedPartnersCount = 320,
                    ecosystemHealthScore = 99.9,
                    status = "ACTIVE_SYNCHRONIZED"
                ),
                EnterpriseNetworkEntity(
                    enterpriseName = "Surat Mega Weaving & Jacquard Cluster",
                    entityType = "Production Mega-Factory Node",
                    regionOrCountry = "Surat / Gujarat, India",
                    connectedBranchesCount = 12,
                    connectedFactoriesCount = 8,
                    connectedWarehousesCount = 24,
                    connectedDealersCount = 840,
                    connectedPartnersCount = 110,
                    ecosystemHealthScore = 99.7,
                    status = "ACTIVE_SYNCHRONIZED"
                )
            )
            defaultGrid.forEach { repository.buildEnterpriseNetwork(it) }
            _enterpriseNetwork.value = defaultGrid

            val defaultKnowledge = listOf(
                KnowledgeWebEntity(
                    relationCategory = "Business & Trade Relations",
                    sourceEntity = "Veeransh Artisan Weavers Collective",
                    targetEntity = "Global NRI Diaspora Retailers",
                    relationType = "Direct D2C / B2B Autonomous Supply Link",
                    strengthScorePct = 99.8,
                    aiReasoningInsight = "Zero middleman margin provides 40% higher artisan wages with 25% lower end-consumer prices.",
                    predictiveTrend = "Exponential surge in direct video-call & WhatsApp instant checkout conversions.",
                    optimizationRecommendation = "Scale real-time catalog broadcasting with automated multi-currency clearance."
                ),
                KnowledgeWebEntity(
                    relationCategory = "Industry & Technology Relations",
                    sourceEntity = "VASCS Generative Studio AI",
                    targetEntity = "Jacquard Digital Loom Controllers",
                    relationType = "Prompt-to-Weave Digital Protocol",
                    strengthScorePct = 99.5,
                    aiReasoningInsight = "Direct vector compilation eliminates 14-day manual punch card drafting.",
                    predictiveTrend = "70% reduction in bespoke luxury saree sample turnaround time.",
                    optimizationRecommendation = "Autonomous color-matching sensor deployment across dyeing units."
                )
            )
            defaultKnowledge.forEach { repository.insertKnowledgeWeb(it) }
            _knowledgeWeb.value = defaultKnowledge

            val defaultPartners = listOf(
                PartnershipNetworkEntity(
                    partnerName = "Apex Global Luxury Logistics & DHL Express",
                    partnerType = "Strategic Freight & Fulfillment Partner",
                    domainOrSector = "Cross-Border Express Delivery",
                    partnershipScore = 99.4,
                    reliabilityPct = 99.95,
                    synergyValueMillionUsd = 145.0,
                    strategicValueProposition = "Guaranteed 48-hour delivery from Surat weaver hubs to NYC/London doorsteps with duty pre-clearance.",
                    status = "ACTIVE_ALLIANCE"
                ),
                PartnershipNetworkEntity(
                    partnerName = "Stripe & Razorpay Global Multi-Currency Clearing",
                    partnerType = "Universal Settlement Partner",
                    domainOrSector = "Fintech & Instant Merchant Payouts",
                    partnershipScore = 99.8,
                    reliabilityPct = 99.99,
                    synergyValueMillionUsd = 210.0,
                    strategicValueProposition = "Instant INR direct-to-artisan bank deposits within 3 seconds of international card swipe.",
                    status = "ACTIVE_ALLIANCE"
                )
            )
            defaultPartners.forEach { repository.analyzePartnerships(it) }
            _partnerships.value = defaultPartners

            val defaultOpps = listOf(
                OpportunityExchangeEntity(
                    opportunityCategory = "New Global Revenue Streams",
                    title = "AI-Driven Custom Bespoke Bridal Weaving on Demand",
                    description = "NRI brides custom-design saree pallu motifs via AI studio, instantly rendered to Jacquard looms.",
                    potentialValueBillionUsd = 18.5,
                    opportunityRank = 1,
                    confidenceScorePct = 99.6,
                    executionReadinessScore = 99.1,
                    strategicImpact = "Captures high-ticket luxury bridal segment across US, UK, Canada, UAE, Australia."
                ),
                OpportunityExchangeEntity(
                    opportunityCategory = "New Markets & Diaspora Expansion",
                    title = "GCC Heritage Festival Exclusive Trunk Show Series",
                    description = "Direct consignment pop-up experience across Dubai, Abu Dhabi, Doha, and Muscat.",
                    potentialValueBillionUsd = 6.8,
                    opportunityRank = 2,
                    confidenceScorePct = 98.9,
                    executionReadinessScore = 99.4,
                    strategicImpact = "High gross margin (68%) festive gold zari drape demand."
                )
            )
            defaultOpps.forEach { repository.discoverOpportunities(it) }
            _opportunities.value = defaultOpps

            val defaultDecisions = listOf(
                DecisionExchangeEntity(
                    decisionType = "Autonomous Cross-Enterprise Strategy",
                    originatorAiRole = "AI CEO & AI Strategy Director Alliance",
                    topicTitle = "Unified Autonomous Price & Inventory Equalization Directive",
                    executiveSummary = "AI algorithms dynamically balance Surat loom production quotas with real-time overseas boutique demand.",
                    recommendationAction = "Allocate 65% loom capacity to high-margin Kanchipuram and Banarasi zari bridal collections.",
                    expectedRoiPct = 52.8,
                    adoptionRatingPct = 100.0
                ),
                DecisionExchangeEntity(
                    decisionType = "Supply Chain Zero-Friction Protocol",
                    originatorAiRole = "AI COO & AI CTO Alliance",
                    topicTitle = "Automated Raw Silk Quality Verification via Computer Vision",
                    executiveSummary = "Edge AI cameras at spinning mills grade mulberry silk filaments before warping.",
                    recommendationAction = "Reject sub-par yarn batches automatically at intake dock.",
                    expectedRoiPct = 38.4,
                    adoptionRatingPct = 100.0
                )
            )
            defaultDecisions.forEach { repository.shareDecisions(it) }
            _decisions.value = defaultDecisions

            val defaultHealth = listOf(
                NexusHealthEntity(
                    networkHealthScore = 99.95,
                    enterpriseHealthScore = 99.85,
                    industryHealthScore = 99.75,
                    economicHealthScore = 99.90,
                    nexusHealthIndex = 99.86,
                    healthGrade = "SYNCHRONIZED_ORGANISM_OPTIMAL"
                )
            )
            defaultHealth.forEach { repository.calculateNexusHealth(it) }
            _nexusHealth.value = defaultHealth
        }
    }

    fun runNexusCore() {
        viewModelScope.launch {
            val newCore = NexusCoreEntity(
                systemName = "VASCS Nexus Pulse Sync #${System.currentTimeMillis() % 10000}",
                connectivityStatus = "ALL_SYSTEMS_SYNCHRONIZED",
                synchronizationMode = "Continuous Deep Quantum Alignment",
                enterpriseCoordination = "100% Interconnected AI Enterprises",
                networkGovernance = "Autonomous DAO Consensus",
                activeEnterprisesCount = (28000..32000).random(),
                syncedNodesCount = (190000..210000).random(),
                networkLatencyMs = 0.10,
                throughputTps = 35000000
            )
            repository.runNexusCore(newCore)
            val updated = mutableListOf(newCore)
            updated.addAll(_nexusCore.value)
            _nexusCore.value = updated
            _nexusIndex.value = (_nexusIndex.value + 0.01).coerceAtMost(100.0)
        }
    }

    fun buildEnterpriseNetwork() {
        viewModelScope.launch {
            val newNetwork = EnterpriseNetworkEntity(
                enterpriseName = "VASCS Enterprise Node #${System.currentTimeMillis() % 1000}",
                entityType = "Distributed Fabric Hub",
                regionOrCountry = "Global Unified Corridor",
                connectedBranchesCount = 24,
                connectedFactoriesCount = 8,
                connectedWarehousesCount = 32,
                connectedDealersCount = 920,
                connectedPartnersCount = 140,
                ecosystemHealthScore = 99.9,
                status = "ACTIVE_SYNCHRONIZED"
            )
            repository.buildEnterpriseNetwork(newNetwork)
            val updated = mutableListOf(newNetwork)
            updated.addAll(_enterpriseNetwork.value)
            _enterpriseNetwork.value = updated
        }
    }

    fun analyzePartnerships() {
        viewModelScope.launch {
            val partner = PartnershipNetworkEntity(
                partnerName = "Global Trade Alliance #${System.currentTimeMillis() % 1000}",
                partnerType = "Strategic Omnichannel Dealer Mesh",
                domainOrSector = "Global Luxury Textile Distribution",
                partnershipScore = 99.7,
                reliabilityPct = 99.98,
                synergyValueMillionUsd = 180.0,
                strategicValueProposition = "Instant digital catalog synchronization to 25,000 retail storefronts worldwide.",
                status = "ACTIVE_ALLIANCE"
            )
            repository.analyzePartnerships(partner)
            val updated = mutableListOf(partner)
            updated.addAll(_partnerships.value)
            _partnerships.value = updated
        }
    }

    fun discoverOpportunities() {
        viewModelScope.launch {
            val opp = OpportunityExchangeEntity(
                opportunityCategory = "Planetary Market Expansion",
                title = "Direct Weaver-to-Diaspora AI Live Broadcast Mesh #${System.currentTimeMillis() % 1000}",
                description = "Autonomous live video shopping platform with instant real-time currency conversion and express customs clearance.",
                potentialValueBillionUsd = 22.4,
                opportunityRank = 1,
                confidenceScorePct = 99.8,
                executionReadinessScore = 99.5,
                strategicImpact = "Zero middleman margin, 100% artisan transparency across 190 nations."
            )
            repository.discoverOpportunities(opp)
            val updated = mutableListOf(opp)
            updated.addAll(_opportunities.value)
            _opportunities.value = updated
        }
    }

    fun shareDecisions() {
        viewModelScope.launch {
            val dec = DecisionExchangeEntity(
                decisionType = "Global Collective Intelligence Directive",
                originatorAiRole = "AI Enterprise Alliance (CEO/CFO/COO/CTO/CMO)",
                topicTitle = "Planetary AI Autonomous Production & Fulfillment Accord",
                executiveSummary = "Cross-enterprise intelligence sharing guarantees zero stockouts and 0% surplus inventory worldwide.",
                recommendationAction = "Synchronize live loom speeds with global consumer shopping cart telemetry.",
                expectedRoiPct = 64.2,
                adoptionRatingPct = 100.0
            )
            repository.shareDecisions(dec)
            val updated = mutableListOf(dec)
            updated.addAll(_decisions.value)
            _decisions.value = updated
        }
    }

    fun calculateNexusHealth() {
        viewModelScope.launch {
            val health = NexusHealthEntity(
                networkHealthScore = 99.98,
                enterpriseHealthScore = 99.92,
                industryHealthScore = 99.88,
                economicHealthScore = 99.95,
                nexusHealthIndex = 99.93,
                healthGrade = "SYNCHRONIZED_ORGANISM_OPTIMAL"
            )
            repository.calculateNexusHealth(health)
            val updated = mutableListOf(health)
            updated.addAll(_nexusHealth.value)
            _nexusHealth.value = updated
            _nexusIndex.value = 99.93
        }
    }
}
