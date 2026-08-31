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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import com.example.vascs.ui.viewmodel.OmniverseViewModel

enum class OmniverseModule(val menuTitle: String, val icon: ImageVector) {
    OMNIVERSE_CORE("Omniverse Core", Icons.Default.AllInclusive),
    ECONOMY_NETWORK("Economy Network", Icons.Default.Public),
    MARKET_MATRIX("Market Matrix", Icons.Default.ViewModule),
    TRADE_GRID("Trade Grid", Icons.Default.SwapHoriz),
    KNOWLEDGE_FABRIC("Knowledge Fabric", Icons.Default.Psychology),
    INDUSTRY_MATRIX("Industry Matrix", Icons.Default.Domain),
    DIGITAL_REALITY("Digital Reality", Icons.Default.DeviceHub),
    OPPORTUNITY_UNIVERSE("Opportunity Universe", Icons.Default.Explore),
    RISK_UNIVERSE("Risk Universe", Icons.Default.Shield),
    REVENUE_UNIVERSE("Revenue Universe", Icons.Default.MonetizationOn),
    INNOVATION_UNIVERSE("Innovation Universe", Icons.Default.Lightbulb),
    DECISION_UNIVERSE("Decision Universe", Icons.Default.AccountTree),
    EVOLUTION_UNIVERSE("Evolution Universe", Icons.Default.AutoAwesome),
    OMNIVERSE_HEALTH("Omniverse Health", Icons.Default.HealthAndSafety),
    OMNIVERSE_TOWER("Omniverse Tower", Icons.Default.Podcasts)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OmniversePlatformScreen(
    viewModel: OmniverseViewModel,
    onBackClick: (() -> Unit)? = null
) {
    var selectedModule by remember { mutableStateOf(OmniverseModule.OMNIVERSE_CORE) }
    var showAddEconomyDialog by remember { mutableStateOf(false) }
    var showAddMarketDialog by remember { mutableStateOf(false) }
    var showAddTradeDialog by remember { mutableStateOf(false) }
    var showAddOpportunityDialog by remember { mutableStateOf(false) }

    val economiesList by viewModel.economies.collectAsState()
    val marketsList by viewModel.markets.collectAsState()
    val tradeGridList by viewModel.tradeGrid.collectAsState()
    val opportunitiesList by viewModel.opportunities.collectAsState()
    val innovationsList by viewModel.innovations.collectAsState()
    val risksList by viewModel.risks.collectAsState()
    val healthList by viewModel.health.collectAsState()
    val coresList by viewModel.cores.collectAsState()
    val knowledgeList by viewModel.knowledge.collectAsState()
    val industriesList by viewModel.industries.collectAsState()

    val omniverseIndex by viewModel.omniverseIndex.collectAsState()
    val realitySyncIndex by viewModel.realitySyncIndex.collectAsState()
    val wealthIndex by viewModel.wealthIndex.collectAsState()
    val evolutionIndex by viewModel.evolutionIndex.collectAsState()
    val decisionPrecisionScore by viewModel.decisionPrecisionScore.collectAsState()
    val isSimulating by viewModel.isSimulating.collectAsState()
    val telemetryFeed by viewModel.telemetryFeed.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "VASCS OMNIVERSE",
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
                                    text = "CHECKPOINT 21.0",
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                        Text(
                            text = "Universal Intelligence Fabric • One Intelligence, Infinite Realities",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                },
                navigationIcon = {
                    if (onBackClick != null) {
                        IconButton(
                            onClick = onBackClick,
                            modifier = Modifier.testTag("omniverse_back_button")
                        ) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                        }
                    }
                },
                actions = {
                    IconButton(
                        onClick = { viewModel.runOmniverseCore() },
                        modifier = Modifier.testTag("omniverse_sync_button")
                    ) {
                        Icon(Icons.Default.Sync, contentDescription = "Sync Fabric")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Omniverse Live Status Banner
            Surface(
                color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(10.dp)
                                .clip(CircleShape)
                                .background(if (isSimulating) MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.primary)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = if (isSimulating) "SYNCHRONIZING OMNIVERSE REALITIES..." else "FABRIC ACTIVE • 142 ECONOMIES",
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    Text(
                        text = "Omniverse Index: ${String.format("%.2f", omniverseIndex)}%",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }

            // Horizontal Module Navigation Tabs (15 Modules)
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                contentPadding = PaddingValues(horizontal = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(OmniverseModule.entries) { module ->
                    val isSelected = selectedModule == module
                    FilterChip(
                        selected = isSelected,
                        onClick = { selectedModule = module },
                        label = { Text(module.menuTitle, style = MaterialTheme.typography.labelMedium) },
                        leadingIcon = {
                            Icon(
                                imageVector = module.icon,
                                contentDescription = module.menuTitle,
                                modifier = Modifier.size(16.dp)
                            )
                        },
                        modifier = Modifier.testTag("tab_${module.name.lowercase()}"),
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                            selectedLabelColor = MaterialTheme.colorScheme.onPrimaryContainer,
                            selectedLeadingIconColor = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    )
                }
            }

            HorizontalDivider()

            // Main Module Content Container
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
            ) {
                when (selectedModule) {
                    OmniverseModule.OMNIVERSE_CORE -> OmniverseCoreModuleView(viewModel, coresList, omniverseIndex, realitySyncIndex)
                    OmniverseModule.ECONOMY_NETWORK -> EconomyNetworkModuleView(viewModel, economiesList) { showAddEconomyDialog = true }
                    OmniverseModule.MARKET_MATRIX -> MarketMatrixModuleView(viewModel, marketsList) { showAddMarketDialog = true }
                    OmniverseModule.TRADE_GRID -> TradeGridModuleView(viewModel, tradeGridList) { showAddTradeDialog = true }
                    OmniverseModule.KNOWLEDGE_FABRIC -> KnowledgeFabricModuleView(viewModel, knowledgeList)
                    OmniverseModule.INDUSTRY_MATRIX -> IndustryMatrixModuleView(viewModel, industriesList)
                    OmniverseModule.DIGITAL_REALITY -> DigitalRealityModuleView(viewModel, economiesList, industriesList)
                    OmniverseModule.OPPORTUNITY_UNIVERSE -> OpportunityUniverseModuleView(viewModel, opportunitiesList) { showAddOpportunityDialog = true }
                    OmniverseModule.RISK_UNIVERSE -> RiskUniverseModuleView(viewModel, risksList)
                    OmniverseModule.REVENUE_UNIVERSE -> RevenueUniverseModuleView(viewModel, wealthIndex)
                    OmniverseModule.INNOVATION_UNIVERSE -> InnovationUniverseModuleView(viewModel, innovationsList)
                    OmniverseModule.DECISION_UNIVERSE -> DecisionUniverseModuleView(viewModel, decisionPrecisionScore)
                    OmniverseModule.EVOLUTION_UNIVERSE -> EvolutionUniverseModuleView(viewModel, evolutionIndex)
                    OmniverseModule.OMNIVERSE_HEALTH -> OmniverseHealthModuleView(viewModel, healthList)
                    OmniverseModule.OMNIVERSE_TOWER -> OmniverseTowerModuleView(viewModel, omniverseIndex, realitySyncIndex, wealthIndex, evolutionIndex, telemetryFeed)
                }
            }
        }
    }

    // Interactive Dialogs
    if (showAddEconomyDialog) {
        AddEconomyDialog(
            onDismiss = { showAddEconomyDialog = false },
            onAdd = { name, scope, entities, gdp, growth, currency ->
                viewModel.addEconomy(name, scope, entities, gdp, growth, currency)
                showAddEconomyDialog = false
            }
        )
    }

    if (showAddMarketDialog) {
        AddMarketDialog(
            onDismiss = { showAddMarketDialog = false },
            onAdd = { name, region, demand, supply, sentiment, signal ->
                viewModel.addMarket(name, region, demand, supply, sentiment, signal)
                showAddMarketDialog = false
            }
        )
    }

    if (showAddTradeDialog) {
        AddTradeGridDialog(
            onDismiss = { showAddTradeDialog = false },
            onAdd = { title, tier, endpoints, throughput, efficiency ->
                viewModel.addTradeNode(title, tier, endpoints, throughput, efficiency)
                showAddTradeDialog = false
            }
        )
    }

    if (showAddOpportunityDialog) {
        AddOpportunityDialog(
            onDismiss = { showAddOpportunityDialog = false },
            onAdd = { title, category, value, months, prob, plan ->
                viewModel.addOpportunity(title, category, value, months, prob, plan)
                showAddOpportunityDialog = false
            }
        )
    }
}

// -------------------------------------------------------------
// MODULE 1: OMNIVERSE CORE
// -------------------------------------------------------------
@Composable
fun OmniverseCoreModuleView(
    viewModel: OmniverseViewModel,
    cores: List<OmniverseCoreEntity>,
    omniverseIndex: Double,
    realitySyncIndex: Double
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
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "UNIVERSAL INTELLIGENCE CONTROLLER",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                        Icon(Icons.Default.AllInclusive, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Unified AI consciousness distributing intelligence, synchronizing reality states, and maintaining cross-system governance across 142 integrated economic layers.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.85f)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        OmniMetricItem("Omniverse Index", "${String.format("%.2f", omniverseIndex)}%")
                        OmniMetricItem("Reality Sync", "${String.format("%.2f", realitySyncIndex)}%")
                        OmniMetricItem("Evolution Velocity", "14.8x")
                    }
                }
            }
        }

        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Core Intelligence Responsibilities",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Button(
                    onClick = { viewModel.runOmniverseCore() },
                    modifier = Modifier.testTag("btn_run_omniverse_core")
                ) {
                    Icon(Icons.Default.PlayArrow, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Re-Align Controller")
                }
            }
        }

        items(listOf(
            Triple("Universal Coordination", "Seamless synchronization of 142 economies, 420 industries, and multi-tier supply networks.", Icons.Default.Public),
            Triple("Cross-System Governance", "Harmonized macroeconomic stability rules, automated trade protocols, and zero-leakage reserve balancing.", Icons.Default.Gavel),
            Triple("Intelligence Distribution", "Real-time edge neural inference delivered directly to smart looms, mobile apps, and retail counters.", Icons.Default.Hub),
            Triple("Reality Synchronization", "Continuous state alignment between physical production, warehouse inventories, and digital twins.", Icons.Default.Sync)
        )) { (title, desc, icon) ->
            Card(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        shape = CircleShape,
                        color = MaterialTheme.colorScheme.secondaryContainer,
                        modifier = Modifier.size(44.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(icon, contentDescription = null, tint = MaterialTheme.colorScheme.onSecondaryContainer)
                        }
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(title, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                        Text(desc, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }
            }
        }
    }
}

// -------------------------------------------------------------
// MODULE 2: MULTI-ECONOMY NETWORK
// -------------------------------------------------------------
@Composable
fun EconomyNetworkModuleView(
    viewModel: OmniverseViewModel,
    economies: List<EconomyNetworkEntity>,
    onAddEconomyClick: () -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Unified Economic Network",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Local • Regional • National • Global • Virtual",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    Button(onClick = { viewModel.analyzeEconomies() }) {
                        Text("Analyze All")
                    }
                    Button(onClick = onAddEconomyClick) {
                        Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(16.dp))
                    }
                }
            }
        }

        items(economies) { economy ->
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Top
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(economy.economyName, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                            Text(
                                text = "Scope: ${economy.economyScope} • Regime: ${economy.currencyRegime}",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = MaterialTheme.colorScheme.tertiaryContainer
                        ) {
                            Text(
                                text = "${economy.autonomyLevelPct}% Autonomy",
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        OmniMetricItem("Entities", "${economy.activeEntitiesCount}")
                        OmniMetricItem("Total GDP", "$${economy.totalGdpBillionUsd}B")
                        OmniMetricItem("Growth YoY", "+${economy.growthRateYoYPct}%")
                        OmniMetricItem("Network Interconn", "${economy.networkInterconnectednessScore}%")
                    }
                }
            }
        }
    }
}

// -------------------------------------------------------------
// MODULE 3: UNIVERSAL MARKET MATRIX
// -------------------------------------------------------------
@Composable
fun MarketMatrixModuleView(
    viewModel: OmniverseViewModel,
    markets: List<MarketMatrixEntity>,
    onAddMarketClick: () -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Universal Market Index",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Demand • Supply • Consumer Sentiment • Signals",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    Button(onClick = { viewModel.synchronizeMarkets() }) {
                        Text("Synchronize")
                    }
                    Button(onClick = onAddMarketClick) {
                        Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(16.dp))
                    }
                }
            }
        }

        items(markets) { market ->
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(market.marketName, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                            Text(market.geographicRegion, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.primary)
                        }
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = MaterialTheme.colorScheme.primaryContainer
                        ) {
                            Text(
                                text = "${market.marketEfficiencyPct}% Eff",
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        OmniMetricItem("Demand Index", "${market.aggregateDemandIndex}")
                        OmniMetricItem("Supply Cap", "${market.supplyCapacityPct}%")
                        OmniMetricItem("Sentiment", "${market.consumerSentimentScore}")
                        OmniMetricItem("Opportunities", "${market.emergingOpportunitiesCount}")
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Surface(
                        color = MaterialTheme.colorScheme.surfaceVariant,
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Signal: ${market.marketSignalSummary}",
                            modifier = Modifier.padding(8.dp),
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }
}

// -------------------------------------------------------------
// MODULE 4: OMNIVERSE TRADE GRID
// -------------------------------------------------------------
@Composable
fun TradeGridModuleView(
    viewModel: OmniverseViewModel,
    grids: List<TradeGridEntity>,
    onAddTradeClick: () -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Omniverse Trade Grid",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Manufacturers • Suppliers • Distributors • Retailers • Consumers",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    Button(onClick = { viewModel.optimizeTradeGrid() }) {
                        Text("Optimize")
                    }
                    Button(onClick = onAddTradeClick) {
                        Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(16.dp))
                    }
                }
            }
        }

        items(grids) { grid ->
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(grid.tradeNodeTitle, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                            Text("Tier: ${grid.nodeTier} • Health: ${grid.gridHealthStatus}", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.primary)
                        }
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = MaterialTheme.colorScheme.secondaryContainer
                        ) {
                            Text(
                                text = "${grid.tradeEfficiencyScore}% Eff",
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        OmniMetricItem("Endpoints", "${grid.connectedEndpointsCount}")
                        OmniMetricItem("Throughput", "$${grid.volumeThroughputMillionUsd}M")
                        OmniMetricItem("Friction Lag", "${grid.frictionLagMs}ms")
                        OmniMetricItem("Tariff Opt", "${grid.tariffOptimizationPct}%")
                    }
                }
            }
        }
    }
}

// -------------------------------------------------------------
// MODULE 5: UNIVERSAL KNOWLEDGE FABRIC
// -------------------------------------------------------------
@Composable
fun KnowledgeFabricModuleView(
    viewModel: OmniverseViewModel,
    knowledgeList: List<KnowledgeFabricEntity>
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
                        text = "UNIVERSAL INTELLIGENCE BASE",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Synthesizing 5,000+ years of textile heritage, macroeconomic frameworks, trade routes, patent databases, and quantum probability horizons.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.85f)
                    )
                }
            }
        }

        items(knowledgeList) { item ->
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(item.knowledgeTopic, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                            Text(item.domainCategory, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.primary)
                        }
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = MaterialTheme.colorScheme.tertiaryContainer
                        ) {
                            Text(
                                text = "${item.reasoningConfidencePct}% Conf",
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        OmniMetricItem("Indexed Nodes", "${item.indexedNodesCount / 1000000}M")
                        OmniMetricItem("Synthesis Depth", item.synthesisDepthLevel.take(16))
                        OmniMetricItem("Predictive Acc", "${item.predictiveAccuracyPct}%")
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Insight: ${item.actionableInsightsSummary}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

// -------------------------------------------------------------
// MODULE 6: CROSS INDUSTRY ENGINE
// -------------------------------------------------------------
@Composable
fun IndustryMatrixModuleView(
    viewModel: OmniverseViewModel,
    industries: List<IndustryMatrixEntity>
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
                        text = "CROSS INDUSTRY OPPORTUNITY MAP",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Unified cross-sector synergy analysis spanning Retail, Manufacturing, Healthcare, Agriculture, Technology, and Sovereign Finance.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.85f)
                    )
                }
            }
        }

        items(industries) { ind ->
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(ind.industrySector, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = MaterialTheme.colorScheme.primaryContainer
                        ) {
                            Text(
                                text = "${ind.aiIntegrationLevelPct}% AI Native",
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        OmniMetricItem("Active Clusters", "${ind.activeClustersCount}")
                        OmniMetricItem("Market Cap", "$${ind.sectoralMarketCapBillionUsd}B")
                        OmniMetricItem("Transform Vel", "+${ind.transformationVelocityPct}%")
                        OmniMetricItem("Synergy", "${ind.crossIndustrySynergyScore}%")
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Disruption Vector: ${ind.keyDisruptionVector}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

// -------------------------------------------------------------
// MODULE 7: OMNIVERSE DIGITAL REALITY
// -------------------------------------------------------------
@Composable
fun DigitalRealityModuleView(
    viewModel: OmniverseViewModel,
    economies: List<EconomyNetworkEntity>,
    industries: List<IndustryMatrixEntity>
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
                        text = "DIGITAL REALITY & TWIN ENGINE",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onTertiaryContainer
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Executing 1 Billion parallel Monte Carlo reality simulations across Business Twins, Industry Twins, Market Twins, and Economic Twins.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onTertiaryContainer.copy(alpha = 0.85f)
                    )
                }
            }
        }

        items(listOf(
            Pair("Surat Smart Weave Mega-Park Twin", "Business & Manufacturing Twin • 100% Real-Time Sensor Telemetry"),
            Pair("Global Handloom Liquidity Reserve Twin", "Economic & Sovereign Twin • Dynamic Stress Testing & Capital Yield"),
            Pair("North America Festive Saree Market Twin", "Market & Consumer Twin • Demand Horizon & Trend Prediction"),
            Pair("Bio-Silk Nanotech R&D Pipeline Twin", "Industry & Innovation Twin • Molecular Draping Simulation")
        )) { (twinName, twinDesc) ->
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text(twinName, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                    Text(twinDesc, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Spacer(modifier = Modifier.height(10.dp))
                    Button(
                        onClick = { viewModel.simulateRealityTwin(twinName) },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
                    ) {
                        Icon(Icons.Default.PlayArrow, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Simulate 1 Billion Realities")
                    }
                }
            }
        }
    }
}

// -------------------------------------------------------------
// MODULE 8: UNIVERSAL OPPORTUNITY ENGINE
// -------------------------------------------------------------
@Composable
fun OpportunityUniverseModuleView(
    viewModel: OmniverseViewModel,
    opportunities: List<OpportunityUniverseEntity>,
    onAddOppClick: () -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Opportunity Universe",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Hidden Markets • Future Trends • Emerging Industries",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    Button(onClick = { viewModel.generateOpportunities() }) {
                        Text("Scan Fabric")
                    }
                    Button(onClick = onAddOppClick) {
                        Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(16.dp))
                    }
                }
            }
        }

        items(opportunities) { opp ->
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(opp.opportunityTitle, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                            Text("Category: ${opp.opportunityCategory} • Stage: ${opp.executionStage}", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.primary)
                        }
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = MaterialTheme.colorScheme.primaryContainer
                        ) {
                            Text(
                                text = "${opp.captureProbabilityPct}% Prob",
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        OmniMetricItem("Addressable", "$${opp.addressableValueMillionUsd}M")
                        OmniMetricItem("Maturity", "${opp.timeToMaturityMonths} mo")
                        OmniMetricItem("Universe Score", "${opp.universeOpportunityScore}")
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Plan: ${opp.strategicActionPlan}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

// -------------------------------------------------------------
// MODULE 9: UNIVERSAL RISK MATRIX
// -------------------------------------------------------------
@Composable
fun RiskUniverseModuleView(
    viewModel: OmniverseViewModel,
    risks: List<OmniverseRiskEntity>
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
                        text = "UNIVERSAL RISK MATRIX & SHIELD",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Real-time automated hedging, anti-fragile supply resilience, and systemic liquidity protection across 5 key risk vectors.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        items(risks) { risk ->
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(risk.riskTitle, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                            Text(risk.riskDomain, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.primary)
                        }
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = MaterialTheme.colorScheme.secondaryContainer
                        ) {
                            Text(
                                text = "Severity: ${risk.severityLevel}",
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        OmniMetricItem("Exposure", "$${risk.exposureValueMillionUsd}M")
                        OmniMetricItem("Resilience Score", "${risk.riskResilienceScore}%")
                        OmniMetricItem("Status", "Auto-Mitigated")
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Mitigation: ${risk.automatedMitigationStrategy}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

// -------------------------------------------------------------
// MODULE 10: OMNIVERSE REVENUE ENGINE
// -------------------------------------------------------------
@Composable
fun RevenueUniverseModuleView(
    viewModel: OmniverseViewModel,
    wealthIndex: Double
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
                        text = "UNIVERSAL WEALTH & REVENUE ENGINE",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Universal tracking of multi-tier enterprise revenues, artisan guild wealth generation, sovereign assets, and capital valuations.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.85f)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        OmniMetricItem("Wealth Index", "${String.format("%.2f", wealthIndex)}%")
                        OmniMetricItem("Annual Revenue", "$14.8B USD")
                        OmniMetricItem("Net Profit Margin", "38.6%")
                    }
                }
            }
        }

        items(listOf(
            Triple("Direct Artisan Enterprise Guild Pool", "$6.42B USD Cumulative Wealth • +54.2% YoY Growth", Icons.Default.Groups),
            Triple("Sovereign Silk Reserve & Strategic Buffer", "$4.80B USD Liquid Assets • Zero-Intermediary Yield", Icons.Default.AccountBalance),
            Triple("Global Diaspora Retail Guild Network", "$3.58B USD Annual Throughput • 99.8% Payment Precision", Icons.Default.Storefront)
        )) { (title, desc, icon) ->
            Card(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(icon, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(36.dp))
                    Spacer(modifier = Modifier.width(12.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(title, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                        Text(desc, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }
            }
        }
    }
}

// -------------------------------------------------------------
// MODULE 11: OMNIVERSE INNOVATION NETWORK
// -------------------------------------------------------------
@Composable
fun InnovationUniverseModuleView(
    viewModel: OmniverseViewModel,
    innovations: List<OmniverseInnovationEntity>
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
                        text = "INNOVATION EXPANSION NETWORK",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Autonomous generation and global commercialization of breakthroughs in material science, AI economics, and photonics.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.85f)
                    )
                }
            }
        }

        items(innovations) { inn ->
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(inn.innovationTitle, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                            Text("Patent: ${inn.patentIdentifier} • ${inn.innovationClass}", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.primary)
                        }
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = MaterialTheme.colorScheme.tertiaryContainer
                        ) {
                            Text(
                                text = inn.deploymentStatus,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        OmniMetricItem("Potential Yield", "$${inn.potentialYieldMillionUsd}M")
                        OmniMetricItem("Expansion Index", "${inn.expansionIndex}%")
                    }
                }
            }
        }
    }
}

// -------------------------------------------------------------
// MODULE 12: UNIVERSAL DECISION ENGINE
// -------------------------------------------------------------
@Composable
fun DecisionUniverseModuleView(
    viewModel: OmniverseViewModel,
    precisionScore: Double
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
                        text = "UNIVERSAL DECISION ENGINE",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onTertiaryContainer
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Autonomous high-precision capital allocation, multi-currency pricing, and cross-border expansion decisions.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onTertiaryContainer.copy(alpha = 0.85f)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        OmniMetricItem("Decision Precision", "${String.format("%.2f", precisionScore)}%")
                        OmniMetricItem("Autonomous Executions", "1,840 Actions")
                        OmniMetricItem("Success Rate", "99.98%")
                    }
                }
            }
        }

        items(listOf(
            Triple("Automated Capital Transfer to Surat Weave Mega-Park", "Execute $120M liquidity allocation for robotic jacquard upgrade.", 120.0),
            Triple("Real-Time GCC Currency Auto-Hedging", "Lock 45M AED forward hedge against USD/INR spot movement.", 45.0),
            Triple("Global SkyCargo Dedicated Capacity Reservation", "Pre-book 600 metric tons air cargo capacity for upcoming Q4 festive peak.", 35.0)
        )) { (title, plan, impact) ->
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text(title, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                    Text(plan, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(
                        onClick = { viewModel.executeDecision(title, impact) },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(Icons.Default.Check, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Execute Decision ($$impact M Impact)")
                    }
                }
            }
        }
    }
}

// -------------------------------------------------------------
// MODULE 13: OMNIVERSE EVOLUTION ENGINE
// -------------------------------------------------------------
@Composable
fun EvolutionUniverseModuleView(
    viewModel: OmniverseViewModel,
    evolutionIndex: Double
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
                        text = "EVOLUTION INTELLIGENCE INDEX",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Perpetual self-upgrading engine driving companies, industries, markets, economies, and civilizations to higher complexity and resilience.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.85f)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        OmniMetricItem("Evolution Index", "${String.format("%.3f", evolutionIndex)}%")
                        OmniMetricItem("Self-Upgrades", "18,400 Cycles")
                        OmniMetricItem("Emergence Status", "Omni-Sovereign")
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Button(
                        onClick = { viewModel.triggerEvolution() },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(Icons.Default.AutoAwesome, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Trigger Universal Evolution Cycle")
                    }
                }
            }
        }

        items(listOf(
            Pair("Autonomous Enterprise Organism", "Enterprises evolving into fully autonomous decentralized organisms."),
            Pair("Self-Configuring Loom Clusters", "Production lines auto-adapting patterns from live Paris runway feeds."),
            Pair("Zero-Human Cross-Border Clearance", "Instant AI customs and tax settlement across all trade corridors.")
        )) { (title, desc) ->
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text(title, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                    Text(desc, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
        }
    }
}

// -------------------------------------------------------------
// MODULE 14: OMNIVERSE HEALTH SYSTEM
// -------------------------------------------------------------
@Composable
fun OmniverseHealthModuleView(
    viewModel: OmniverseViewModel,
    healthList: List<OmniverseHealthEntity>
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Omniverse Health Index",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Economy • Trade • Innovation • Growth • Civilization",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Button(onClick = { viewModel.calculateOmniverseIndex() }) {
                    Icon(Icons.Default.Refresh, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Recalculate")
                }
            }
        }

        items(healthList) { item ->
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(item.dimensionName, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = MaterialTheme.colorScheme.primaryContainer
                        ) {
                            Text(
                                text = "${item.score}% • ${item.status}",
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = item.diagnosticSummary,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

// -------------------------------------------------------------
// MODULE 15: OMNIVERSE COMMAND TOWER
// -------------------------------------------------------------
@Composable
fun OmniverseTowerModuleView(
    viewModel: OmniverseViewModel,
    omniverseIndex: Double,
    realitySync: Double,
    wealthIndex: Double,
    evolutionIndex: Double,
    telemetryFeed: List<String>
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
                        text = "OMNIVERSE INTELLIGENCE INDEX",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Central Command Tower streaming synchronized telemetry across all 142 economies, trade grids, digital twins, and autonomous growth engines.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.85f)
                    )
                    Spacer(modifier = Modifier.height(14.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        OmniMetricItem("Omniverse Index", "${String.format("%.2f", omniverseIndex)}%")
                        OmniMetricItem("Reality Sync", "${String.format("%.2f", realitySync)}%")
                        OmniMetricItem("Wealth Index", "${String.format("%.2f", wealthIndex)}%")
                        OmniMetricItem("Evolution", "${String.format("%.3f", evolutionIndex)}%")
                    }
                }
            }
        }

        item {
            Text(
                text = "Live Omniverse Telemetry Stream",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
        }

        items(telemetryFeed) { log ->
            Surface(
                color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.FiberManualRecord,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(10.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = log,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

// -------------------------------------------------------------
// HELPER COMPOSABLES & DIALOGS
// -------------------------------------------------------------
@Composable
fun OmniMetricItem(label: String, value: String) {
    Column {
        Text(label, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Text(value, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
    }
}

@Composable
fun AddEconomyDialog(
    onDismiss: () -> Unit,
    onAdd: (String, String, Int, Double, Double, String) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var scope by remember { mutableStateOf("Global") }
    var entities by remember { mutableStateOf("10000") }
    var gdp by remember { mutableStateOf("25.0") }
    var growth by remember { mutableStateOf("50.0") }
    var currency by remember { mutableStateOf("Autonomous Multi-Currency") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Link New Economy") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Economy Name") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = scope,
                    onValueChange = { scope = it },
                    label = { Text("Scope (Local/Regional/Global/Virtual)") },
                    modifier = Modifier.fillMaxWidth()
                )
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = entities,
                        onValueChange = { entities = it },
                        label = { Text("Entities") },
                        modifier = Modifier.weight(1f)
                    )
                    OutlinedTextField(
                        value = gdp,
                        onValueChange = { gdp = it },
                        label = { Text("GDP (B USD)") },
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (name.isNotBlank()) {
                        onAdd(
                            name,
                            scope,
                            entities.toIntOrNull() ?: 5000,
                            gdp.toDoubleOrNull() ?: 10.0,
                            growth.toDoubleOrNull() ?: 45.0,
                            currency
                        )
                    }
                }
            ) {
                Text("Link Economy")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancel") }
        }
    )
}

@Composable
fun AddMarketDialog(
    onDismiss: () -> Unit,
    onAdd: (String, String, Double, Double, Double, String) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var region by remember { mutableStateOf("Global Hub") }
    var demand by remember { mutableStateOf("98.0") }
    var supply by remember { mutableStateOf("96.0") }
    var sentiment by remember { mutableStateOf("99.0") }
    var signal by remember { mutableStateOf("Surging festive luxury demand") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Map New Market") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Market Name") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = region,
                    onValueChange = { region = it },
                    label = { Text("Region") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = signal,
                    onValueChange = { signal = it },
                    label = { Text("Market Signal") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (name.isNotBlank()) {
                        onAdd(
                            name,
                            region,
                            demand.toDoubleOrNull() ?: 98.0,
                            supply.toDoubleOrNull() ?: 96.0,
                            sentiment.toDoubleOrNull() ?: 99.0,
                            signal
                        )
                    }
                }
            ) {
                Text("Map Market")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancel") }
        }
    )
}

@Composable
fun AddTradeGridDialog(
    onDismiss: () -> Unit,
    onAdd: (String, String, Int, Double, Double) -> Unit
) {
    var title by remember { mutableStateOf("") }
    var tier by remember { mutableStateOf("Manufacturer") }
    var endpoints by remember { mutableStateOf("1200") }
    var throughput by remember { mutableStateOf("750.0") }
    var efficiency by remember { mutableStateOf("99.9") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add Trade Grid Node") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Node Title") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = tier,
                    onValueChange = { tier = it },
                    label = { Text("Tier (Manufacturer/Supplier/Retailer)") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = throughput,
                    onValueChange = { throughput = it },
                    label = { Text("Throughput (M USD)") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (title.isNotBlank()) {
                        onAdd(
                            title,
                            tier,
                            endpoints.toIntOrNull() ?: 500,
                            throughput.toDoubleOrNull() ?: 500.0,
                            efficiency.toDoubleOrNull() ?: 99.8
                        )
                    }
                }
            ) {
                Text("Connect Node")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancel") }
        }
    )
}

@Composable
fun AddOpportunityDialog(
    onDismiss: () -> Unit,
    onAdd: (String, String, Double, Int, Double, String) -> Unit
) {
    var title by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("Global Opportunities") }
    var value by remember { mutableStateOf("850.0") }
    var months by remember { mutableStateOf("3") }
    var prob by remember { mutableStateOf("99.0") }
    var plan by remember { mutableStateOf("Autonomous capital deployment and express scaling") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Generate Opportunity") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Opportunity Title") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = category,
                    onValueChange = { category = it },
                    label = { Text("Category") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = value,
                    onValueChange = { value = it },
                    label = { Text("Addressable Value (M USD)") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (title.isNotBlank()) {
                        onAdd(
                            title,
                            category,
                            value.toDoubleOrNull() ?: 500.0,
                            months.toIntOrNull() ?: 3,
                            prob.toDoubleOrNull() ?: 99.0,
                            plan
                        )
                    }
                }
            ) {
                Text("Stage Opportunity")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancel") }
        }
    )
}
