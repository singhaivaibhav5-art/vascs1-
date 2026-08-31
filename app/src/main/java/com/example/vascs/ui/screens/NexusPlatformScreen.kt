package com.example.vascs.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.vascs.data.model.*
import com.example.vascs.ui.viewmodel.NexusViewModel

enum class NexusModule(val title: String, val icon: ImageVector) {
    NEXUS_CORE("Nexus Core", Icons.Default.Hub),
    ENTERPRISE_GRID("Enterprise Grid", Icons.Default.Apartment),
    INTERCOMPANY_NETWORK("Intercompany Network", Icons.Default.Share),
    KNOWLEDGE_WEB("Knowledge Web", Icons.Default.AccountTree),
    BUSINESS_COORDINATION("Business Coordination", Icons.Default.Tune),
    AI_ALLIANCE("AI Alliance", Icons.Default.SmartToy),
    OPPORTUNITY_EXCHANGE("Opportunity Exchange", Icons.Default.ShowChart),
    TWIN_NETWORK("Twin Network", Icons.Default.DeviceHub),
    PARTNERSHIP_INTELLIGENCE("Partnership Intelligence", Icons.Default.Handshake),
    REVENUE_NEXUS("Revenue Nexus", Icons.Default.MonetizationOn),
    COLLABORATION_HUB("Collaboration Hub", Icons.Default.Groups),
    MARKET_NEXUS("Market Nexus", Icons.Default.Storefront),
    DECISION_EXCHANGE("Decision Exchange", Icons.Default.Psychology),
    NEXUS_HEALTH("Nexus Health", Icons.Default.HealthAndSafety),
    COMMAND_TOWER("Nexus Command Tower", Icons.Default.Podcasts)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NexusPlatformScreen(
    viewModel: NexusViewModel,
    onBackClick: (() -> Unit)? = null
) {
    var selectedModule by remember { mutableStateOf(NexusModule.NEXUS_CORE) }

    val nexusCore by viewModel.nexusCore.collectAsState()
    val enterpriseNetwork by viewModel.enterpriseNetwork.collectAsState()
    val knowledgeWeb by viewModel.knowledgeWeb.collectAsState()
    val partnerships by viewModel.partnerships.collectAsState()
    val opportunities by viewModel.opportunities.collectAsState()
    val decisions by viewModel.decisions.collectAsState()
    val nexusHealth by viewModel.nexusHealth.collectAsState()
    val nexusIndex by viewModel.nexusIndex.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "VASCS NEXUS",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Surface(
                                shape = RoundedCornerShape(12.dp),
                                color = MaterialTheme.colorScheme.primaryContainer
                            ) {
                                Text(
                                    text = "CHECKPOINT 17.0",
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                        Text(
                            text = "Universal Business Network • A Living Business Organism",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                },
                navigationIcon = {
                    if (onBackClick != null) {
                        IconButton(
                            onClick = onBackClick,
                            modifier = Modifier.testTag("nexus_back_button")
                        ) {
                            Icon(Icons.Default.ArrowBack, contentDescription = "Back to Dashboard")
                        }
                    }
                },
                actions = {
                    Surface(
                        shape = RoundedCornerShape(16.dp),
                        color = MaterialTheme.colorScheme.tertiaryContainer,
                        modifier = Modifier.padding(end = 12.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                        ) {
                            Icon(
                                Icons.Default.Bolt,
                                contentDescription = null,
                                modifier = Modifier.size(16.dp),
                                tint = MaterialTheme.colorScheme.onTertiaryContainer
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "Nexus Index: ${"%.2f".format(nexusIndex)}%",
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onTertiaryContainer
                            )
                        }
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            // Horizontal Navigation Ribbon for 15 Modules
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f))
                    .padding(vertical = 8.dp, horizontal = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(NexusModule.values()) { module ->
                    val isSelected = selectedModule == module
                    FilterChip(
                        selected = isSelected,
                        onClick = { selectedModule = module },
                        label = { Text(module.title, fontSize = 13.sp) },
                        leadingIcon = {
                            Icon(
                                imageVector = module.icon,
                                contentDescription = module.title,
                                modifier = Modifier.size(16.dp)
                            )
                        },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = MaterialTheme.colorScheme.primary,
                            selectedLabelColor = MaterialTheme.colorScheme.onPrimary,
                            selectedLeadingIconColor = MaterialTheme.colorScheme.onPrimary
                        ),
                        modifier = Modifier.testTag("nexus_chip_${module.name.lowercase()}")
                    )
                }
            }

            // Module View Area
            Box(modifier = Modifier.fillMaxSize()) {
                when (selectedModule) {
                    NexusModule.NEXUS_CORE -> NexusCoreView(
                        cores = nexusCore,
                        onTriggerSync = { viewModel.runNexusCore() }
                    )
                    NexusModule.ENTERPRISE_GRID -> EnterpriseGridView(
                        networks = enterpriseNetwork,
                        onBuildNetwork = { viewModel.buildEnterpriseNetwork() }
                    )
                    NexusModule.INTERCOMPANY_NETWORK -> IntercompanyNetworkView()
                    NexusModule.KNOWLEDGE_WEB -> KnowledgeWebView(
                        webs = knowledgeWeb
                    )
                    NexusModule.BUSINESS_COORDINATION -> BusinessCoordinationView()
                    NexusModule.AI_ALLIANCE -> AiAllianceView(
                        decisions = decisions,
                        onShareDecision = { viewModel.shareDecisions() }
                    )
                    NexusModule.OPPORTUNITY_EXCHANGE -> OpportunityExchangeView(
                        opportunities = opportunities,
                        onDiscoverOpportunity = { viewModel.discoverOpportunities() }
                    )
                    NexusModule.TWIN_NETWORK -> TwinNetworkView()
                    NexusModule.PARTNERSHIP_INTELLIGENCE -> PartnershipIntelligenceView(
                        partners = partnerships,
                        onAnalyzePartnerships = { viewModel.analyzePartnerships() }
                    )
                    NexusModule.REVENUE_NEXUS -> RevenueNexusView()
                    NexusModule.COLLABORATION_HUB -> CollaborationHubView()
                    NexusModule.MARKET_NEXUS -> MarketNexusView()
                    NexusModule.DECISION_EXCHANGE -> DecisionExchangeView(
                        decisions = decisions,
                        onShareDecision = { viewModel.shareDecisions() }
                    )
                    NexusModule.NEXUS_HEALTH -> NexusHealthView(
                        healthRecords = nexusHealth,
                        onRecalculate = { viewModel.calculateNexusHealth() }
                    )
                    NexusModule.COMMAND_TOWER -> NexusCommandTowerView(
                        nexusIndex = nexusIndex,
                        cores = nexusCore,
                        enterprises = enterpriseNetwork,
                        onExecuteFullSync = {
                            viewModel.runNexusCore()
                            viewModel.calculateNexusHealth()
                        }
                    )
                }
            }
        }
    }
}

// -------------------------------------------------------------
// MODULE 1: NEXUS CORE
// -------------------------------------------------------------
@Composable
fun NexusCoreView(
    cores: List<NexusCoreEntity>,
    onTriggerSync: () -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Hub, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "NEXUS CORE KERNEL",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Responsibilities: Global Connectivity • Intelligence Synchronization • Enterprise Coordination • Network Governance",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Button(
                        onClick = onTriggerSync,
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                        modifier = Modifier.testTag("nexus_sync_button")
                    ) {
                        Icon(Icons.Default.Sync, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Trigger Universal Nexus Synchronization")
                    }
                }
            }
        }

        item {
            Text(
                text = "Active Synchronized Nexus Kernels",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold
            )
        }

        items(cores) { core ->
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(core.systemName, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleSmall)
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = MaterialTheme.colorScheme.secondaryContainer
                        ) {
                            Text(
                                text = core.connectivityStatus,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Text("Sync Mode: ${core.synchronizationMode}", style = MaterialTheme.typography.bodySmall)
                    Text("Coordination: ${core.enterpriseCoordination}", style = MaterialTheme.typography.bodySmall)
                    Text("Governance: ${core.networkGovernance}", style = MaterialTheme.typography.bodySmall)
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Enterprises: ${core.activeEnterprisesCount}", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold)
                        Text("Latency: ${core.networkLatencyMs}ms", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                        Text("TPS: ${core.throughputTps}", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.tertiary)
                    }
                }
            }
        }
    }
}

// -------------------------------------------------------------
// MODULE 2: ENTERPRISE NETWORK GRID
// -------------------------------------------------------------
@Composable
fun EnterpriseGridView(
    networks: List<EnterpriseNetworkEntity>,
    onBuildNetwork: () -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "ENTERPRISE NETWORK GRID",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Connects: Companies • Branches • Factories • Warehouses • Dealers • Partners",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Result: Connected Enterprise Ecosystem",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Button(
                        onClick = onBuildNetwork,
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                        modifier = Modifier.testTag("build_enterprise_network_button")
                    ) {
                        Icon(Icons.Default.AddBusiness, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Connect Enterprise Node")
                    }
                }
            }
        }

        items(networks) { net ->
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(net.enterpriseName, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleSmall)
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = MaterialTheme.colorScheme.primaryContainer
                        ) {
                            Text(
                                text = "${net.ecosystemHealthScore}% Score",
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Text("Type: ${net.entityType} • Region: ${net.regionOrCountry}", style = MaterialTheme.typography.bodySmall)
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("🏢 Branches: ${net.connectedBranchesCount}", style = MaterialTheme.typography.labelSmall)
                        Text("🏭 Factories: ${net.connectedFactoriesCount}", style = MaterialTheme.typography.labelSmall)
                        Text("📦 Warehouses: ${net.connectedWarehousesCount}", style = MaterialTheme.typography.labelSmall)
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("🤝 Dealers: ${net.connectedDealersCount}", style = MaterialTheme.typography.labelSmall)
                        Text("🌐 Partners: ${net.connectedPartnersCount}", style = MaterialTheme.typography.labelSmall)
                        Text("Status: ${net.status}", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

// -------------------------------------------------------------
// MODULE 3: INTERCOMPANY INTELLIGENCE
// -------------------------------------------------------------
@Composable
fun IntercompanyNetworkView() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.tertiaryContainer),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "INTERCOMPANY INTELLIGENCE",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onTertiaryContainer
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Features: Shared Knowledge • Shared Forecasts • Shared Analytics • Shared Opportunities",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onTertiaryContainer
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Output: Collective Business Intelligence",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onTertiaryContainer
                    )
                }
            }
        }

        item {
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Shared Intercompany Analytics Streams", fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("• Cross-Enterprise Zari & Silk Yarn Demand Aggregation Index", style = MaterialTheme.typography.bodySmall)
                    Text("• Multi-Regional Festive Shopping Trend Forecaster (US, UK, UAE, India)", style = MaterialTheme.typography.bodySmall)
                    Text("• Collaborative Bulk Freight & Container Consolidation Stream", style = MaterialTheme.typography.bodySmall)
                }
            }
        }
    }
}

// -------------------------------------------------------------
// MODULE 4: NEXUS KNOWLEDGE WEB
// -------------------------------------------------------------
@Composable
fun KnowledgeWebView(
    webs: List<KnowledgeWebEntity>
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "NEXUS KNOWLEDGE WEB",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Stores: Business Relations • Industry Relations • Country Relations • Trade Relations",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "AI Uses: Reasoning • Prediction • Optimization",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }

        items(webs) { web ->
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(web.relationCategory, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleSmall)
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = MaterialTheme.colorScheme.primaryContainer
                        ) {
                            Text(
                                text = "Strength: ${web.strengthScorePct}%",
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Text("${web.sourceEntity} ➔ ${web.targetEntity}", style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.SemiBold)
                    Text("Type: ${web.relationType}", style = MaterialTheme.typography.bodySmall)
                    Spacer(modifier = Modifier.height(6.dp))
                    Text("🧠 Reasoning: ${web.aiReasoningInsight}", style = MaterialTheme.typography.bodySmall)
                    Text("📈 Trend: ${web.predictiveTrend}", style = MaterialTheme.typography.bodySmall)
                    Text("⚡ Optimization: ${web.optimizationRecommendation}", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.primary)
                }
            }
        }
    }
}

// -------------------------------------------------------------
// MODULE 5: AUTONOMOUS BUSINESS COORDINATION
// -------------------------------------------------------------
@Composable
fun BusinessCoordinationView() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "AUTONOMOUS BUSINESS COORDINATION",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Coordinates: Inventory • Sales • Marketing • Supply Chain • Expansion",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Goal: Global Optimization (Zero Friction & Automated Rebalancing)",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }

        item {
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Active Autonomous Coordination Streams", fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("• Dynamic Weaver Production Allocation: 100% capacity utilized", style = MaterialTheme.typography.bodySmall)
                    Text("• Cross-Border Automated Sales Pipeline Routing via WhatsApp", style = MaterialTheme.typography.bodySmall)
                    Text("• Algorithmic Expansion Funding Allocation across high-performing dealer hubs", style = MaterialTheme.typography.bodySmall)
                }
            }
        }
    }
}

// -------------------------------------------------------------
// MODULE 6: AI ENTERPRISE ALLIANCE
// -------------------------------------------------------------
@Composable
fun AiAllianceView(
    decisions: List<DecisionExchangeEntity>,
    onShareDecision: () -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "AI ENTERPRISE ALLIANCE",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Members: AI CEO • AI CFO • AI COO • AI CTO • AI CMO • AI Strategy Director",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Purpose: Cross Enterprise Decision Making",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Button(
                        onClick = onShareDecision,
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                        modifier = Modifier.testTag("ai_alliance_decision_button")
                    ) {
                        Icon(Icons.Default.SmartToy, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Convene AI Alliance Decision Accord")
                    }
                }
            }
        }

        item {
            Text("Alliance Executive Council", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleSmall)
        }

        items(listOf(
            "AI CEO" to "Planetary Corporate Strategy & Global Growth Alignment",
            "AI CFO" to "Real-Time SDR Multi-Currency Liquidity & Zero-Loss Hedging",
            "AI COO" to "Autonomous Zero-Friction Production & Delivery Dispatch",
            "AI CTO" to "Quantum Mesh Infrastructure & Edge AI Vector Acceleration",
            "AI CMO" to "Hyper-Targeted Diaspora WhatsApp & Video Retargeting",
            "AI Strategy Director" to "Predictive Market Seeding & Cross-Border Alliances"
        )) { (role, roleDesc) ->
            Card(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Default.AccountCircle, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(role, fontWeight = FontWeight.Bold)
                        Text(roleDesc, style = MaterialTheme.typography.bodySmall)
                    }
                }
            }
        }
    }
}

// -------------------------------------------------------------
// MODULE 7: GLOBAL OPPORTUNITY EXCHANGE
// -------------------------------------------------------------
@Composable
fun OpportunityExchangeView(
    opportunities: List<OpportunityExchangeEntity>,
    onDiscoverOpportunity: () -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.tertiaryContainer),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "GLOBAL OPPORTUNITY EXCHANGE",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onTertiaryContainer
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Discovers: New Markets • New Products • New Industries • New Revenue Streams",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onTertiaryContainer
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Output: Opportunity Ranking Engine",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onTertiaryContainer
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Button(
                        onClick = onDiscoverOpportunity,
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiary),
                        modifier = Modifier.testTag("discover_opportunity_button")
                    ) {
                        Icon(Icons.Default.Explore, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Rank New Global Opportunities")
                    }
                }
            }
        }

        items(opportunities) { opp ->
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(opp.title, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleSmall)
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = MaterialTheme.colorScheme.primaryContainer
                        ) {
                            Text(
                                text = "Rank #${opp.opportunityRank}",
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("Category: ${opp.opportunityCategory}", style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.SemiBold)
                    Text(opp.description, style = MaterialTheme.typography.bodySmall)
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Value: $${opp.potentialValueBillionUsd}B", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                        Text("Confidence: ${opp.confidenceScorePct}%", fontWeight = FontWeight.Bold)
                        Text("Readiness: ${opp.executionReadinessScore}%", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.tertiary)
                    }
                }
            }
        }
    }
}

// -------------------------------------------------------------
// MODULE 8: NEXUS DIGITAL TWIN NETWORK
// -------------------------------------------------------------
@Composable
fun TwinNetworkView() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "NEXUS DIGITAL TWIN NETWORK",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Creates: Company Twin • Industry Twin • Country Twin • Global Twin",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Uses: Simulation • Forecasting • Risk Analysis",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }

        item {
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Living Twin Network Hierarchy", fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(6.dp))
                    Text("1. Global Commerce Living Twin: Multi-variable simulation of 190 nations.", style = MaterialTheme.typography.bodySmall)
                    Text("2. Country Commerce Twins: Real-time tariff, tax, and currency predictive flows.", style = MaterialTheme.typography.bodySmall)
                    Text("3. Industry Textile Twins: Raw silk cocoon harvesting to retail drape lifecycle.", style = MaterialTheme.typography.bodySmall)
                    Text("4. Company Enterprise Twins: Real-time financial liquidity and loom output mirrors.", style = MaterialTheme.typography.bodySmall)
                }
            }
        }
    }
}

// -------------------------------------------------------------
// MODULE 9: GLOBAL PARTNERSHIP ENGINE
// -------------------------------------------------------------
@Composable
fun PartnershipIntelligenceView(
    partners: List<PartnershipNetworkEntity>,
    onAnalyzePartnerships: () -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "GLOBAL PARTNERSHIP ENGINE",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Analyzes: Suppliers • Distributors • Dealers • Strategic Partners",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Output: Partnership Score",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Button(
                        onClick = onAnalyzePartnerships,
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                        modifier = Modifier.testTag("analyze_partnerships_button")
                    ) {
                        Icon(Icons.Default.Handshake, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Calculate Global Partnership Scores")
                    }
                }
            }
        }

        items(partners) { p ->
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(p.partnerName, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleSmall)
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = MaterialTheme.colorScheme.primaryContainer
                        ) {
                            Text(
                                text = "Score: ${p.partnershipScore}",
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("Type: ${p.partnerType} • Sector: ${p.domainOrSector}", style = MaterialTheme.typography.bodySmall)
                    Text("Proposition: ${p.strategicValueProposition}", style = MaterialTheme.typography.bodySmall)
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Synergy: $${p.synergyValueMillionUsd}M", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                        Text("Reliability: ${p.reliabilityPct}%", fontWeight = FontWeight.Bold)
                        Text("Status: ${p.status}", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.tertiary)
                    }
                }
            }
        }
    }
}

// -------------------------------------------------------------
// MODULE 10: NEXUS REVENUE NETWORK
// -------------------------------------------------------------
@Composable
fun RevenueNexusView() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.tertiaryContainer),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "NEXUS REVENUE NETWORK",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onTertiaryContainer
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Tracks: Company Revenue • Industry Revenue • Country Revenue • Global Revenue",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onTertiaryContainer
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Output: Revenue Intelligence Matrix",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onTertiaryContainer
                    )
                }
            }
        }

        item {
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Revenue Intelligence Matrix Breakdown", fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(6.dp))
                    Text("• Company Revenue: $148.5M ARR (Annual Recurring Run Rate)", style = MaterialTheme.typography.bodySmall)
                    Text("• Industry Revenue Mesh: $12.4B Global Handloom & Luxury Drapes", style = MaterialTheme.typography.bodySmall)
                    Text("• Country Cross-Border Flows: $3.8B (US, UK, UAE, Australia corridor)", style = MaterialTheme.typography.bodySmall)
                    Text("• Global Network GMV: $42.6B Autonomous Ecosystem Clearing", style = MaterialTheme.typography.bodySmall)
                }
            }
        }
    }
}

// -------------------------------------------------------------
// MODULE 11: GLOBAL COLLABORATION CENTER
// -------------------------------------------------------------
@Composable
fun CollaborationHubView() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "GLOBAL COLLABORATION CENTER",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Supports: Projects • Teams • Enterprises • Industries",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Goal: Collective Growth",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }

        item {
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Cross-Border Collaborative Projects", fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(6.dp))
                    Text("• Project Kanchi-Silk: 2,400 weavers connected to 500 US NRI boutiques", style = MaterialTheme.typography.bodySmall)
                    Text("• Project Zari-Verify: Distributed spectrometer authentication team", style = MaterialTheme.typography.bodySmall)
                    Text("• Project Fast-Custom: 24-hour turnaround custom digital pallu printing", style = MaterialTheme.typography.bodySmall)
                }
            }
        }
    }
}

// -------------------------------------------------------------
// MODULE 12: NEXUS MARKET NETWORK
// -------------------------------------------------------------
@Composable
fun MarketNexusView() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "NEXUS MARKET NETWORK",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Monitors: Demand • Competition • Trends • Consumer Behavior",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Output: Market Opportunity Index (99.4%)",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }

        item {
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Real-Time Planetary Market Indices", fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(6.dp))
                    Text("• High-Zari Bridal Index: +38.4% demand growth in California / Texas", style = MaterialTheme.typography.bodySmall)
                    Text("• Pastel Organza Saree Trend: +54.2% demand growth in UK & Canada", style = MaterialTheme.typography.bodySmall)
                    Text("• Consumer Repeat Purchase Index: 88.6% loyalty rating", style = MaterialTheme.typography.bodySmall)
                }
            }
        }
    }
}

// -------------------------------------------------------------
// MODULE 13: AI DECISION EXCHANGE
// -------------------------------------------------------------
@Composable
fun DecisionExchangeView(
    decisions: List<DecisionExchangeEntity>,
    onShareDecision: () -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "AI DECISION EXCHANGE",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Shares: Best Practices • Strategies • Forecasts • Recommendations",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Result: Collective Intelligence Growth",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Button(
                        onClick = onShareDecision,
                        modifier = Modifier.testTag("share_ai_decision_button")
                    ) {
                        Icon(Icons.Default.Psychology, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Broadcast Collective Decision Accord")
                    }
                }
            }
        }

        items(decisions) { dec ->
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(dec.topicTitle, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleSmall)
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = MaterialTheme.colorScheme.primaryContainer
                        ) {
                            Text(
                                text = "ROI: +${dec.expectedRoiPct}%",
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("Originator: ${dec.originatorAiRole} • Type: ${dec.decisionType}", style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.SemiBold)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(dec.executiveSummary, style = MaterialTheme.typography.bodySmall)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("⚡ Recommendation: ${dec.recommendationAction}", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.primary)
                }
            }
        }
    }
}

// -------------------------------------------------------------
// MODULE 14: NEXUS HEALTH ENGINE
// -------------------------------------------------------------
@Composable
fun NexusHealthView(
    healthRecords: List<NexusHealthEntity>,
    onRecalculate: () -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "NEXUS HEALTH ENGINE",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Scores: Network Health • Enterprise Health • Industry Health • Economic Health",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Output: Nexus Health Index (99.86% - SYNCHRONIZED ORGANISM)",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Button(
                        onClick = onRecalculate,
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                        modifier = Modifier.testTag("recalc_nexus_health_button")
                    ) {
                        Icon(Icons.Default.HealthAndSafety, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Recalculate Living Organism Health")
                    }
                }
            }
        }

        items(healthRecords) { h ->
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Nexus Composite Health", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleSmall)
                        Text("${h.nexusHealthIndex}%", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Network: ${h.networkHealthScore}%", style = MaterialTheme.typography.bodySmall)
                        Text("Enterprise: ${h.enterpriseHealthScore}%", style = MaterialTheme.typography.bodySmall)
                        Text("Industry: ${h.industryHealthScore}%", style = MaterialTheme.typography.bodySmall)
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Economic: ${h.economicHealthScore}%", style = MaterialTheme.typography.bodySmall)
                        Text("Grade: ${h.healthGrade}", style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.tertiary)
                    }
                }
            }
        }
    }
}

// -------------------------------------------------------------
// MODULE 15: NEXUS COMMAND TOWER
// -------------------------------------------------------------
@Composable
fun NexusCommandTowerView(
    nexusIndex: Double,
    cores: List<NexusCoreEntity>,
    enterprises: List<EnterpriseNetworkEntity>,
    onExecuteFullSync: () -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Podcasts, contentDescription = null, tint = MaterialTheme.colorScheme.onPrimary)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "NEXUS COMMAND TOWER",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Monitors: Companies • Industries • Countries • Markets • Trade Networks • AI Systems",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.9f)
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = MaterialTheme.colorScheme.surface
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text(
                                text = "FINAL SCORE",
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Text(
                                text = "Nexus Intelligence Index: ${"%.2f".format(nexusIndex)}%",
                                style = MaterialTheme.typography.headlineMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(14.dp))
                    Button(
                        onClick = onExecuteFullSync,
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondaryContainer),
                        modifier = Modifier.testTag("full_nexus_sync_button")
                    ) {
                        Icon(
                            Icons.Default.Bolt,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            "Synchronize Universal Business Network",
                            color = MaterialTheme.colorScheme.onSecondaryContainer,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }

        item {
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "MASTER EVOLUTION PATH",
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.titleSmall
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "VASCS ➔ ERP ➔ SaaS ➔ Marketplace ➔ AI OS ➔ Autonomous Enterprise ➔ Commerce Universe ➔ Business Singularity ➔ OMEGA ➔ INFINITY ➔ COSMOS ➔ NEXUS (ACTIVE) ➔ NEXT: 18.0 QUANTUM",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}
