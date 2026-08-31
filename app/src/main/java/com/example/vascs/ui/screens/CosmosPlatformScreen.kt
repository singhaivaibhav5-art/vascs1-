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
import com.example.vascs.ui.viewmodel.CosmosViewModel

enum class CosmosModule(val title: String, val icon: ImageVector) {
    COSMOS_CORE("Cosmos Core", Icons.Default.Hub),
    BUSINESS_UNIVERSE("Business Universe", Icons.Default.Public),
    PLANETARY_TRADE("Planetary Trade", Icons.Default.FlightTakeoff),
    KNOWLEDGE_COSMOS("Knowledge Cosmos", Icons.Default.Psychology),
    ECONOMIC_TWIN("Economic Twin", Icons.Default.DeviceHub),
    MARKET_COSMOS("Market Cosmos", Icons.Default.TrendingUp),
    SUPPLY_GRID("Supply Grid", Icons.Default.LocalShipping),
    COSMOS_GOVERNANCE("Cosmos Governance", Icons.Default.Gavel),
    MARKETPLACE_COSMOS("Marketplace Cosmos", Icons.Default.Storefront),
    EXPANSION_COSMOS("Expansion Cosmos", Icons.Default.RocketLaunch),
    RISK_COSMOS("Risk Cosmos", Icons.Default.Shield),
    RESEARCH_COSMOS("Research Cosmos", Icons.Default.Science),
    REVENUE_COSMOS("Revenue Cosmos", Icons.Default.MonetizationOn),
    COSMOS_HEALTH("Cosmos Health", Icons.Default.HealthAndSafety),
    COMMAND_TOWER("Cosmos Command Tower", Icons.Default.Podcasts)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CosmosPlatformScreen(
    viewModel: CosmosViewModel,
    onBackClick: (() -> Unit)? = null
) {
    var selectedModule by remember { mutableStateOf(CosmosModule.COSMOS_CORE) }

    val cosmosCore by viewModel.cosmosCore.collectAsState()
    val tradeNetworks by viewModel.tradeNetworks.collectAsState()
    val globalRisk by viewModel.globalRisk.collectAsState()
    val economicTwins by viewModel.economicTwins.collectAsState()
    val marketIntelligence by viewModel.marketIntelligence.collectAsState()
    val supplyGrid by viewModel.supplyGrid.collectAsState()
    val cosmosHealth by viewModel.cosmosHealth.collectAsState()
    val cosmosIndex by viewModel.cosmosIndex.collectAsState()

    val nodes by viewModel.nodes.collectAsState()
    val routes by viewModel.routes.collectAsState()
    val reserves by viewModel.reserves.collectAsState()
    val governanceLogs by viewModel.governanceLogs.collectAsState()
    val models by viewModel.models.collectAsState()
    val indices by viewModel.indices.collectAsState()
    val simulations by viewModel.simulations.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "VASCS COSMOS",
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
                                    text = "CHECKPOINT 16.0",
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                        Text(
                            text = "Commerce Operating Universe • 190 Nations Unified",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                },
                navigationIcon = {
                    if (onBackClick != null) {
                        IconButton(
                            onClick = onBackClick,
                            modifier = Modifier.testTag("cosmos_back_button")
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
                                Icons.Default.Stars,
                                contentDescription = null,
                                modifier = Modifier.size(16.dp),
                                tint = MaterialTheme.colorScheme.onTertiaryContainer
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "Cosmos Index: ${"%.2f".format(cosmosIndex)}%",
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
            // Horizontal Navigation Ribbon with all 15 Modules
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f))
                    .padding(vertical = 8.dp, horizontal = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(CosmosModule.values()) { module ->
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
                        modifier = Modifier.testTag("cosmos_chip_${module.name.lowercase()}")
                    )
                }
            }

            // Module View Area
            Box(modifier = Modifier.fillMaxSize()) {
                when (selectedModule) {
                    CosmosModule.COSMOS_CORE -> CosmosCoreView(
                        cores = cosmosCore,
                        onTriggerSync = { viewModel.runCosmosCore() }
                    )
                    CosmosModule.BUSINESS_UNIVERSE -> BusinessUniverseView(
                        cores = cosmosCore,
                        indices = indices,
                        nodes = nodes
                    )
                    CosmosModule.PLANETARY_TRADE -> PlanetaryTradeView(
                        tradeNetworks = tradeNetworks,
                        routes = routes,
                        onOptimizeRoute = { viewModel.optimizePlanetaryRoute() }
                    )
                    CosmosModule.KNOWLEDGE_COSMOS -> KnowledgeCosmosView(
                        models = models,
                        onEvolve = { viewModel.evolveModelIteration() }
                    )
                    CosmosModule.ECONOMIC_TWIN -> EconomicTwinView(
                        twins = economicTwins,
                        simulations = simulations,
                        onBuildTwin = { viewModel.buildEconomicTwin() }
                    )
                    CosmosModule.MARKET_COSMOS -> MarketCosmosView(
                        markets = marketIntelligence,
                        onAnalyzeMarket = { viewModel.analyzeMarketCosmos() }
                    )
                    CosmosModule.SUPPLY_GRID -> SupplyGridView(
                        grids = supplyGrid,
                        onOptimizeGrid = { viewModel.optimizeSupplyGrid() }
                    )
                    CosmosModule.COSMOS_GOVERNANCE -> CosmosGovernanceView(
                        governanceLogs = governanceLogs,
                        onExecuteGovernance = { viewModel.executeAutonomousGovernance() }
                    )
                    CosmosModule.MARKETPLACE_COSMOS -> MarketplaceCosmosView()
                    CosmosModule.EXPANSION_COSMOS -> ExpansionCosmosView()
                    CosmosModule.RISK_COSMOS -> RiskCosmosView(
                        risks = globalRisk,
                        onAnalyzeRisk = { viewModel.analyzeGlobalRisk() }
                    )
                    CosmosModule.RESEARCH_COSMOS -> ResearchCosmosView(
                        simulations = simulations,
                        onRunSimulation = { viewModel.runPlanetarySimulation() }
                    )
                    CosmosModule.REVENUE_COSMOS -> RevenueCosmosView(reserves = reserves)
                    CosmosModule.COSMOS_HEALTH -> CosmosHealthView(
                        healthRecords = cosmosHealth,
                        onRecalculate = { viewModel.calculateCosmosHealth() }
                    )
                    CosmosModule.COMMAND_TOWER -> CosmosCommandTowerView(
                        cosmosIndex = cosmosIndex,
                        cores = cosmosCore,
                        routes = routes,
                        reserves = reserves,
                        governanceLogs = governanceLogs,
                        onRunFullSync = {
                            viewModel.runCosmosCore()
                            viewModel.calculateCosmosHealth()
                        }
                    )
                }
            }
        }
    }
}

// -------------------------------------------------------------
// MODULE 1: COSMOS CORE
// -------------------------------------------------------------
@Composable
fun CosmosCoreView(
    cores: List<CosmosCoreEntity>,
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
                            text = "COSMOS CORE KERNEL",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Responsibilities: Global Synchronization • Universe Coordination • AI Supervision • Network Governance",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Functions: Monitor Everything • Connect Everything • Optimize Everything",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Button(
                        onClick = onTriggerSync,
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                        modifier = Modifier.testTag("cosmos_sync_button")
                    ) {
                        Icon(Icons.Default.Sync, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Trigger Global Quantum Synchronization")
                    }
                }
            }
        }

        item {
            Text(
                text = "Active Planetary Kernels",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold
            )
        }

        items(cores) { core ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(2.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = core.systemName,
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold
                        )
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = MaterialTheme.colorScheme.secondaryContainer
                        ) {
                            Text(
                                text = core.synchronizationStatus,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSecondaryContainer,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Coordination Scope: ${core.coordinationScope}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = "AI Supervision: ${core.aiSupervisionLevel} • Governance: ${core.networkGovernanceMode}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Nodes: ${core.activeNodesCount}",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Latency: ${core.latencyMs}ms",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            text = "TPS: ${core.throughputTps}",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.tertiary
                        )
                    }
                }
            }
        }
    }
}

// -------------------------------------------------------------
// MODULE 2: GLOBAL BUSINESS UNIVERSE
// -------------------------------------------------------------
@Composable
fun BusinessUniverseView(
    cores: List<CosmosCoreEntity>,
    indices: List<CosmicMarketIndexEntity>,
    nodes: List<CosmosNodeEntity>
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
                        text = "UNIFIED BUSINESS COSMOS",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Contains: Companies • Industries • Markets • Countries • Trade Networks",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Result: One Platform • One Intelligence • One Commerce Network • One Global Ecosystem",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }

        item {
            Text(
                text = "Planetary Compute Superclusters",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold
            )
        }

        items(nodes) { node ->
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(node.nodeName, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.bodyMedium)
                        Text("${node.computePowerPFLOPS} PFLOPS", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                    }
                    Text("Region: ${node.region} • Latency: ${node.latencyMs}ms", style = MaterialTheme.typography.bodySmall)
                }
            }
        }
    }
}

// -------------------------------------------------------------
// MODULE 3: PLANETARY TRADE ENGINE
// -------------------------------------------------------------
@Composable
fun PlanetaryTradeView(
    tradeNetworks: List<TradeNetworksEntity>,
    routes: List<PlanetaryTradeRouteEntity>,
    onOptimizeRoute: () -> Unit
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
                        text = "PLANETARY TRADE ENGINE",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onTertiaryContainer
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Tracks: Global Imports • Global Exports • Trade Corridors • Trade Dependencies",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onTertiaryContainer
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Outputs: Best Global Opportunity • Best Trade Route • Best Trade Partner",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onTertiaryContainer
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Button(
                        onClick = onOptimizeRoute,
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiary),
                        modifier = Modifier.testTag("optimize_trade_corridor_button")
                    ) {
                        Icon(Icons.Default.Route, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Compute Optimal Zero-Tariff Route")
                    }
                }
            }
        }

        items(tradeNetworks) { net ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(2.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(net.networkName, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleSmall)
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = MaterialTheme.colorScheme.primaryContainer
                        ) {
                            Text(
                                text = "${net.efficiencyPct}% Efficiency",
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Text("Corridor: ${net.tradeCorridor}", style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.SemiBold)
                    Text("Dependencies: ${net.tradeDependencies}", style = MaterialTheme.typography.bodySmall)
                    Spacer(modifier = Modifier.height(6.dp))
                    Divider()
                    Spacer(modifier = Modifier.height(6.dp))
                    Text("🌟 Best Opportunity: ${net.bestOpportunity}", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
                    Text("🚀 Best Route: ${net.bestTradeRoute}", style = MaterialTheme.typography.bodySmall)
                    Text("🤝 Best Partner: ${net.bestTradePartner}", style = MaterialTheme.typography.bodySmall)
                }
            }
        }
    }
}

// -------------------------------------------------------------
// MODULE 4: COSMOS KNOWLEDGE NETWORK
// -------------------------------------------------------------
@Composable
fun KnowledgeCosmosView(
    models: List<SelfEvolvingModelEntity>,
    onEvolve: () -> Unit
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
                        text = "COSMOS KNOWLEDGE NETWORK",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Stores: Business Knowledge • Economic Knowledge • Industry Knowledge • Consumer Knowledge • Trade Knowledge",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "AI Uses: Learning • Reasoning • Forecasting • Decision Making",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Button(
                        onClick = onEvolve,
                        modifier = Modifier.testTag("evolve_ai_button")
                    ) {
                        Icon(Icons.Default.AutoAwesome, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Trigger Generational Self-Evolution")
                    }
                }
            }
        }

        items(models) { model ->
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(model.modelName, fontWeight = FontWeight.Bold)
                        Text("Gen ${model.evolutionaryGeneration}", color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
                    }
                    Text("Domain: ${model.coreDomain}", style = MaterialTheme.typography.bodySmall)
                    Text("Inference Accuracy: ${model.inferenceAccuracyPct}% • Optimizations/hr: ${model.autonomousOptimizationsPerHour}", style = MaterialTheme.typography.bodySmall)
                }
            }
        }
    }
}

// -------------------------------------------------------------
// MODULE 5: GLOBAL ECONOMIC DIGITAL TWIN
// -------------------------------------------------------------
@Composable
fun EconomicTwinView(
    twins: List<EconomicTwinsEntity>,
    simulations: List<PlanetarySimulationEntity>,
    onBuildTwin: () -> Unit
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
                        text = "GLOBAL ECONOMIC DIGITAL TWIN",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Creates: Country Twins • Industry Twins • Trade Twins • Economy Twins",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Uses: Future Simulations • Economic Forecasting • Risk Prediction",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Button(
                        onClick = onBuildTwin,
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                        modifier = Modifier.testTag("build_twin_button")
                    ) {
                        Icon(Icons.Default.DeviceHub, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Instantiate High-Fidelity Twin")
                    }
                }
            }
        }

        items(twins) { twin ->
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(twin.entityName, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleSmall)
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = MaterialTheme.colorScheme.secondaryContainer
                        ) {
                            Text(
                                text = twin.twinType,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(twin.futureSimulationSummary, style = MaterialTheme.typography.bodySmall)
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Growth: +${twin.forecastedGrowthRatePct}%", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                        Text("Output: $${twin.economicForecastTrillionUsd}T", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.tertiary)
                        Text("Confidence: ${twin.accuracyConfidencePct}%", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

// -------------------------------------------------------------
// MODULE 6: COSMOS MARKET INTELLIGENCE
// -------------------------------------------------------------
@Composable
fun MarketCosmosView(
    markets: List<MarketCosmosEntity>,
    onAnalyzeMarket: () -> Unit
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
                        text = "COSMOS MARKET INTELLIGENCE",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Analyzes: Consumer Trends • Demand Patterns • Regional Growth • Industry Growth",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Output: Opportunity Score • Market Potential • Expansion Priority",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Button(
                        onClick = onAnalyzeMarket,
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                        modifier = Modifier.testTag("analyze_market_cosmos_button")
                    ) {
                        Icon(Icons.Default.Analytics, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Scan Planetary Market Opportunities")
                    }
                }
            }
        }

        items(markets) { m ->
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(m.marketName, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleSmall)
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = MaterialTheme.colorScheme.primaryContainer
                        ) {
                            Text(
                                text = "Score: ${m.opportunityScore}",
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Text("Trends: ${m.consumerTrends}", style = MaterialTheme.typography.bodySmall)
                    Text("Demand Pattern: ${m.demandPattern}", style = MaterialTheme.typography.bodySmall)
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Potential: $${m.marketPotentialBillionUsd}B", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.tertiary)
                        Text("Priority: ${m.expansionPriority}", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                    }
                }
            }
        }
    }
}

// -------------------------------------------------------------
// MODULE 7: GLOBAL SUPPLY GRID
// -------------------------------------------------------------
@Composable
fun SupplyGridView(
    grids: List<SupplyGridEntity>,
    onOptimizeGrid: () -> Unit
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
                        text = "GLOBAL SUPPLY GRID",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onTertiaryContainer
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Connects: Manufacturers • Suppliers • Warehouses • Transporters • Dealers",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onTertiaryContainer
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Purpose: Zero Friction Supply Chain (Friction: 0.01%)",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onTertiaryContainer
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Button(
                        onClick = onOptimizeGrid,
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiary),
                        modifier = Modifier.testTag("optimize_supply_grid_button")
                    ) {
                        Icon(Icons.Default.Speed, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Enforce Zero-Friction Dispatch")
                    }
                }
            }
        }

        items(grids) { grid ->
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(grid.hubName, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleSmall)
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("🏭 Mfrs: ${grid.connectedManufacturersCount}", style = MaterialTheme.typography.bodySmall)
                        Text("📦 Suppliers: ${grid.connectedSuppliersCount}", style = MaterialTheme.typography.bodySmall)
                        Text("🏬 Warehouses: ${grid.connectedWarehousesCount}", style = MaterialTheme.typography.bodySmall)
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("🚚 Transporters: ${grid.connectedTransportersCount}", style = MaterialTheme.typography.bodySmall)
                        Text("🤝 Dealers: ${grid.connectedDealersCount}", style = MaterialTheme.typography.bodySmall)
                        Text("⚡ Friction: ${grid.frictionScorePct}%", style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                    }
                }
            }
        }
    }
}

// -------------------------------------------------------------
// MODULE 8: COSMOS AI GOVERNANCE
// -------------------------------------------------------------
@Composable
fun CosmosGovernanceView(
    governanceLogs: List<AutonomousGovernanceLogEntity>,
    onExecuteGovernance: () -> Unit
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
                        text = "COSMOS AI GOVERNANCE",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Controls: Policies • Approvals • Automations • Risk Management",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Governance Levels: Manual ➔ Guided AI ➔ Autonomous AI ➔ Cosmos AI",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Button(
                        onClick = onExecuteGovernance,
                        modifier = Modifier.testTag("execute_governance_button")
                    ) {
                        Icon(Icons.Default.Gavel, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Authorize Planetary AI Directive")
                    }
                }
            }
        }

        items(governanceLogs) { log ->
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(log.proposalTitle, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.bodyMedium)
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = MaterialTheme.colorScheme.primaryContainer
                        ) {
                            Text(
                                text = "Approved: ${log.approvalRatingPct}%",
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(log.aiDecisionSummary, style = MaterialTheme.typography.bodySmall)
                }
            }
        }
    }
}

// -------------------------------------------------------------
// MODULE 9: UNIVERSAL MARKETPLACE NETWORK
// -------------------------------------------------------------
@Composable
fun MarketplaceCosmosView() {
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
                        text = "UNIVERSAL MARKETPLACE NETWORK",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Supports: B2B • B2C • D2C • International Trade",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Scale: Unlimited Buyers • Unlimited Sellers • Instant Multi-Currency Clearing",
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
                    Text("Planetary B2B / D2C Channels", fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("• Direct Weaver-to-NRI Diaspora WhatsApp Catalog Broadcasts", style = MaterialTheme.typography.bodySmall)
                    Text("• 0% Middleman Margin Consignment Flow to US/UK Boutiques", style = MaterialTheme.typography.bodySmall)
                    Text("• Real-Time Dynamic Price Equalization Across 190 Countries", style = MaterialTheme.typography.bodySmall)
                }
            }
        }
    }
}

// -------------------------------------------------------------
// MODULE 10: COSMOS EXPANSION ENGINE
// -------------------------------------------------------------
@Composable
fun ExpansionCosmosView() {
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
                        text = "COSMOS EXPANSION ENGINE",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Plans: City Growth • State Growth • Country Growth • Global Growth",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Output: Expansion Blueprint • Investment Plan • Execution Plan",
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
                    Text("Global Expansion Blueprint (2026-2030)", fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(6.dp))
                    Text("1. Phase I: North American NRI Hubs (San Jose, Dallas, Edison, Toronto) - $120M CapEx", style = MaterialTheme.typography.bodySmall)
                    Text("2. Phase II: GCC & Middle East Luxury Hubs (Dubai, Riyadh, Doha) - $85M CapEx", style = MaterialTheme.typography.bodySmall)
                    Text("3. Phase III: Asia-Pacific & European Capital Expansion - $95M CapEx", style = MaterialTheme.typography.bodySmall)
                }
            }
        }
    }
}

// -------------------------------------------------------------
// MODULE 11: GLOBAL RISK INTELLIGENCE
// -------------------------------------------------------------
@Composable
fun RiskCosmosView(
    risks: List<GlobalRiskEntity>,
    onAnalyzeRisk: () -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.5f)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "GLOBAL RISK INTELLIGENCE",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onErrorContainer
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Tracks: Economic Risk • Political Risk • Supply Risk • Market Risk • Currency Risk",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onErrorContainer
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Output: Global Risk Index (0.76 - Minimum Risk)",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Button(
                        onClick = onAnalyzeRisk,
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                        modifier = Modifier.testTag("analyze_global_risk_button")
                    ) {
                        Icon(Icons.Default.Shield, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Recalculate Planetary Risk Index")
                    }
                }
            }
        }

        items(risks) { risk ->
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(risk.regionOrDomain, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleSmall)
                        Text("Risk Index: ${risk.globalRiskIndex}", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Text("Mitigation: ${risk.mitigationAction}", style = MaterialTheme.typography.bodySmall)
                    Spacer(modifier = Modifier.height(6.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Econ: ${risk.economicRiskScore}", style = MaterialTheme.typography.labelSmall)
                        Text("Pol: ${risk.politicalRiskScore}", style = MaterialTheme.typography.labelSmall)
                        Text("Supply: ${risk.supplyRiskScore}", style = MaterialTheme.typography.labelSmall)
                        Text("Currency: ${risk.currencyRiskScore}", style = MaterialTheme.typography.labelSmall)
                    }
                }
            }
        }
    }
}

// -------------------------------------------------------------
// MODULE 12: COSMOS RESEARCH LAB
// -------------------------------------------------------------
@Composable
fun ResearchCosmosView(
    simulations: List<PlanetarySimulationEntity>,
    onRunSimulation: () -> Unit
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
                        text = "COSMOS RESEARCH LAB",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Researches: Products • Markets • Industries • Technologies • Future Opportunities",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Generates: Research Reports • Innovation Reports • Future Scenarios",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Button(
                        onClick = onRunSimulation,
                        modifier = Modifier.testTag("run_cosmos_research_button")
                    ) {
                        Icon(Icons.Default.Science, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Synthesize Future Research Scenario")
                    }
                }
            }
        }

        items(simulations) { sim ->
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text(sim.scenarioName, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleSmall)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("Driver: ${sim.primaryDriver}", style = MaterialTheme.typography.bodySmall)
                    Spacer(modifier = Modifier.height(6.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Value: +$${sim.projectedValueCreationTrillionUsd}T", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                        Text("Confidence: ${sim.confidenceIntervalPct}%", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

// -------------------------------------------------------------
// MODULE 13: COSMOS REVENUE ENGINE
// -------------------------------------------------------------
@Composable
fun RevenueCosmosView(
    reserves: List<SovereignReserveEntity>
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
                        text = "COSMOS REVENUE ENGINE",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onTertiaryContainer
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Analyzes: Revenue Sources • Profit Sources • Market Revenue • Global Revenue",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onTertiaryContainer
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Output: Revenue Growth Intelligence (Real-Time Yield: +34.2%)",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onTertiaryContainer
                    )
                }
            }
        }

        items(reserves) { res ->
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(res.reserveName, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleSmall)
                        Text("$${"%.1f".format(res.totalReserveValueUsd / 1000000.0)}M", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("Class: ${res.assetClass} • Allocation: ${res.allocationPercentage}% • Rating: ${res.riskRating}", style = MaterialTheme.typography.bodySmall)
                }
            }
        }
    }
}

// -------------------------------------------------------------
// MODULE 14: COSMOS HEALTH INDEX
// -------------------------------------------------------------
@Composable
fun CosmosHealthView(
    healthRecords: List<CosmosHealthEntity>,
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
                        text = "COSMOS HEALTH INDEX",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Scores: Business Health • Market Health • Industry Health • Trade Health • Economic Health",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Final Score: Cosmos Health Index: 99.78% (APEX OPTIMAL)",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Button(
                        onClick = onRecalculate,
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                        modifier = Modifier.testTag("recalc_health_button")
                    ) {
                        Icon(Icons.Default.HealthAndSafety, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Recalculate Planetary Health Metrics")
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
                        Text("Cosmos Composite Health", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleSmall)
                        Text("${h.cosmosHealthIndex}%", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Business: ${h.businessHealthScore}%", style = MaterialTheme.typography.bodySmall)
                        Text("Market: ${h.marketHealthScore}%", style = MaterialTheme.typography.bodySmall)
                        Text("Industry: ${h.industryHealthScore}%", style = MaterialTheme.typography.bodySmall)
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Trade: ${h.tradeHealthScore}%", style = MaterialTheme.typography.bodySmall)
                        Text("Economic: ${h.economicHealthScore}%", style = MaterialTheme.typography.bodySmall)
                        Text("Grade: ${h.healthGrade}", style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.tertiary)
                    }
                }
            }
        }
    }
}

// -------------------------------------------------------------
// MODULE 15: COSMOS COMMAND TOWER
// -------------------------------------------------------------
@Composable
fun CosmosCommandTowerView(
    cosmosIndex: Double,
    cores: List<CosmosCoreEntity>,
    routes: List<PlanetaryTradeRouteEntity>,
    reserves: List<SovereignReserveEntity>,
    governanceLogs: List<AutonomousGovernanceLogEntity>,
    onRunFullSync: () -> Unit
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
                            text = "COSMOS COMMAND TOWER",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Monitors: Companies • Industries • Countries • Markets • Trade Networks • Supply Chains • Economies",
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
                                text = "OVERALL SCORE",
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Text(
                                text = "Cosmos Intelligence Index: ${"%.2f".format(cosmosIndex)}%",
                                style = MaterialTheme.typography.headlineMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(14.dp))
                    Button(
                        onClick = onRunFullSync,
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondaryContainer),
                        modifier = Modifier.testTag("full_planetary_sync_button")
                    ) {
                        Icon(
                            Icons.Default.RocketLaunch,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            "Execute Planetary Commerce Singularity",
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
                        text = "MASTER ROADMAP TRAJECTORY",
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.titleSmall
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "VASCS ➔ ERP ➔ SaaS ➔ Marketplace ➔ AI OS ➔ Autonomous Enterprise ➔ Commerce Universe ➔ Business Singularity ➔ OMEGA ➔ INFINITY ➔ COSMOS (ACTIVE) ➔ NEXT: NEXUS (17.0)",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}
