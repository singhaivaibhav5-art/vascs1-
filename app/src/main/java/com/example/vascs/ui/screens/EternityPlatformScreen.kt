package com.example.vascs.ui.screens

import androidx.compose.animation.*
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.vascs.data.model.*
import com.example.vascs.ui.viewmodel.EternityViewModel

enum class EternityModule(val menuLabel: String, val title: String, val icon: ImageVector) {
    CORE("Eternity Core", "Eternity Intelligence Core", Icons.Default.AllInclusive),
    AI_EVOLUTION("AI Evolution Network", "Self-Evolving AI Network", Icons.Default.Psychology),
    WEALTH_UNIVERSE("Wealth Universe", "Infinite Wealth Engine", Icons.Default.AccountBalance),
    OPPORTUNITY_MATRIX("Opportunity Matrix", "Universal Opportunity Matrix", Icons.Default.Radar),
    DEMAND_UNIVERSE("Demand Universe", "Perpetual Demand Engine", Icons.Default.TrendingUp),
    CAPITAL_UNIVERSE("Capital Universe", "Universal Capital AI", Icons.Default.MonetizationOn),
    TRADE_INFINITY("Trade Infinity", "Infinite Trade Grid", Icons.Default.Public),
    DIGITAL_ETERNITY("Digital Eternity", "Eternity Digital Reality", Icons.Default.Public),
    DECISION_MATRIX("Decision Matrix", "Universal Decision Matrix", Icons.Default.FactCheck),
    KNOWLEDGE_ETERNITY("Knowledge Eternity", "Eternity Knowledge Engine", Icons.Default.AutoStories),
    INNOVATION_ETERNITY("Innovation Eternity", "Perpetual Innovation Engine", Icons.Default.Lightbulb),
    RISK_SHIELD("Risk Shield", "Universal Risk Shield", Icons.Default.Shield),
    ETERNITY_HEALTH("Eternity Health", "Eternity Health Engine", Icons.Default.HealthAndSafety),
    ETERNITY_TOWER("Eternity Tower", "Eternity Command Tower", Icons.Default.CellTower),
    CONTINUITY_ENGINE("Continuity Engine", "Universal Continuity Engine", Icons.Default.Autorenew)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EternityPlatformScreen(
    viewModel: EternityViewModel,
    onBackClick: () -> Unit
) {
    var selectedModule by remember { mutableStateOf(EternityModule.CORE) }

    val coreList by viewModel.eternityCore.collectAsState()
    val wealthList by viewModel.wealthEngine.collectAsState()
    val demandList by viewModel.demandEngine.collectAsState()
    val capitalList by viewModel.capitalEngine.collectAsState()
    val tradeList by viewModel.tradeGrid.collectAsState()
    val innovationList by viewModel.innovationEngine.collectAsState()
    val healthList by viewModel.healthEngine.collectAsState()
    val knowledgeList by viewModel.knowledgeFabric.collectAsState()
    val riskList by viewModel.riskShield.collectAsState()
    val eternityIndex by viewModel.eternityIndex.collectAsState()
    val isOperating by viewModel.isOperating.collectAsState()
    val statusMessage by viewModel.statusMessage.collectAsState()
    val telemetryStream by viewModel.telemetryStream.collectAsState()

    var showAddWealthDialog by remember { mutableStateOf(false) }
    var showAddDemandDialog by remember { mutableStateOf(false) }
    var showAddCapitalDialog by remember { mutableStateOf(false) }
    var showAddTradeDialog by remember { mutableStateOf(false) }

    val snackbarHostState = remember { SnackbarHostState() }
    LaunchedEffect(statusMessage) {
        statusMessage?.let {
            snackbarHostState.showSnackbar(it)
            viewModel.clearStatusMessage()
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "VASCS ETERNITY 22.0",
                                style = MaterialTheme.typography.titleLarge.copy(
                                    fontWeight = FontWeight.Black,
                                    letterSpacing = 1.sp
                                )
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Surface(
                                shape = RoundedCornerShape(6.dp),
                                color = MaterialTheme.colorScheme.primaryContainer
                            ) {
                                Text(
                                    text = "INFINITE INTELLIGENCE",
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onPrimaryContainer
                                    )
                                )
                            }
                        }
                        Text(
                            text = "Perpetual Commerce & Self-Sustaining Economic Intelligence",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.outline
                        )
                    }
                },
                navigationIcon = {
                    IconButton(
                        onClick = onBackClick,
                        modifier = Modifier.testTag("eternity_back_button")
                    ) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back to Dashboard")
                    }
                },
                actions = {
                    IconButton(
                        onClick = { viewModel.runEternityCore() },
                        enabled = !isOperating,
                        modifier = Modifier.testTag("eternity_sync_button")
                    ) {
                        Icon(Icons.Default.Sync, contentDescription = "Sync Eternity")
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
            // Live Status Banner
            EternityHeaderBanner(
                eternityIndex = eternityIndex,
                isOperating = isOperating,
                activeEconomies = coreList.firstOrNull()?.perpetualEconomiesCount ?: 284
            )

            // Module Navigation Ribbon
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(EternityModule.values()) { module ->
                    val isSelected = selectedModule == module
                    FilterChip(
                        selected = isSelected,
                        onClick = { selectedModule = module },
                        label = { Text(module.menuLabel, fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal) },
                        leadingIcon = {
                            Icon(
                                imageVector = module.icon,
                                contentDescription = null,
                                modifier = Modifier.size(18.dp)
                            )
                        },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = MaterialTheme.colorScheme.primary,
                            selectedLabelColor = MaterialTheme.colorScheme.onPrimary,
                            selectedLeadingIconColor = MaterialTheme.colorScheme.onPrimary
                        ),
                        modifier = Modifier.testTag("eternity_tab_${module.name.lowercase()}")
                    )
                }
            }

            // Main Module Content
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                when (selectedModule) {
                    EternityModule.CORE -> EternityCoreModuleView(
                        cores = coreList,
                        onRunCore = { viewModel.runEternityCore() }
                    )
                    EternityModule.AI_EVOLUTION -> AiEvolutionNetworkView()
                    EternityModule.WEALTH_UNIVERSE -> WealthUniverseModuleView(
                        wealthList = wealthList,
                        onRecalculate = { viewModel.calculateInfiniteWealth() },
                        onAddClick = { showAddWealthDialog = true }
                    )
                    EternityModule.OPPORTUNITY_MATRIX -> OpportunityMatrixModuleView()
                    EternityModule.DEMAND_UNIVERSE -> DemandUniverseModuleView(
                        demandList = demandList,
                        onForecast = { viewModel.forecastDemand() },
                        onAddClick = { showAddDemandDialog = true }
                    )
                    EternityModule.CAPITAL_UNIVERSE -> CapitalUniverseModuleView(
                        capitalList = capitalList,
                        onOptimize = { viewModel.manageCapital() },
                        onAddClick = { showAddCapitalDialog = true }
                    )
                    EternityModule.TRADE_INFINITY -> TradeInfinityModuleView(
                        tradeList = tradeList,
                        onOptimize = { viewModel.optimizeTrade() },
                        onAddClick = { showAddTradeDialog = true }
                    )
                    EternityModule.DIGITAL_ETERNITY -> DigitalEternityModuleView()
                    EternityModule.DECISION_MATRIX -> DecisionMatrixModuleView()
                    EternityModule.KNOWLEDGE_ETERNITY -> KnowledgeEternityModuleView(knowledgeList = knowledgeList)
                    EternityModule.INNOVATION_ETERNITY -> InnovationEternityModuleView(innovations = innovationList)
                    EternityModule.RISK_SHIELD -> RiskShieldModuleView(risks = riskList)
                    EternityModule.ETERNITY_HEALTH -> EternityHealthModuleView(
                        healthList = healthList,
                        onRecalculate = { viewModel.calculateEternityIndex() }
                    )
                    EternityModule.ETERNITY_TOWER -> EternityTowerModuleView(
                        telemetryStream = telemetryStream,
                        eternityIndex = eternityIndex
                    )
                    EternityModule.CONTINUITY_ENGINE -> ContinuityEngineModuleView()
                }
            }
        }
    }

    // Dialogs
    if (showAddWealthDialog) {
        AddWealthDialog(
            onDismiss = { showAddWealthDialog = false },
            onConfirm = { domain, assets, revenue, profit ->
                viewModel.addWealthDomain(domain, assets, revenue, profit)
                showAddWealthDialog = false
            }
        )
    }

    if (showAddDemandDialog) {
        AddDemandDialog(
            onDismiss = { showAddDemandDialog = false },
            onConfirm = { horizon, sector, units, rev, summary ->
                viewModel.addDemandProjection(horizon, sector, units, rev, summary)
                showAddDemandDialog = false
            }
        )
    }

    if (showAddCapitalDialog) {
        AddCapitalDialog(
            onDismiss = { showAddCapitalDialog = false },
            onConfirm = { cat, cap, dep, roi, plan ->
                viewModel.addCapitalAllocation(cat, cap, dep, roi, plan)
                showAddCapitalDialog = false
            }
        )
    }

    if (showAddTradeDialog) {
        AddTradeDialog(
            onDismiss = { showAddTradeDialog = false },
            onConfirm = { title, zones, cap, lag ->
                viewModel.addTradeCorridor(title, zones, cap, lag)
                showAddTradeDialog = false
            }
        )
    }
}

@Composable
fun EternityHeaderBanner(
    eternityIndex: Double,
    isOperating: Boolean,
    activeEconomies: Int
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "ETERNITY INTELLIGENCE CONTROLLER",
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "Infinite Learning • Growth • Wealth • Evolution",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Black)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .clip(CircleShape)
                            .background(if (isOperating) Color(0xFFFFB300) else Color(0xFF00E676))
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = if (isOperating) "Recalculating Universal Vectors..." else "Self-Sustaining Across $activeEconomies Economies",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f)
                    )
                }
            }

            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = String.format("%.3f", eternityIndex),
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.Black,
                        fontFamily = FontFamily.Monospace,
                        color = MaterialTheme.colorScheme.primary
                    )
                )
                Text(
                    text = "Eternity Index",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f)
                )
            }
        }
    }
}

// -------------------------------------------------------------
// MODULE 1: ETERNITY CORE
// -------------------------------------------------------------
@Composable
fun EternityCoreModuleView(
    cores: List<EternityCoreEntity>,
    onRunCore: () -> Unit
) {
    val core = cores.firstOrNull() ?: EternityCoreEntity(
        perpetualStatus = "Perpetual Intelligence Active",
        perpetualEconomiesCount = 284,
        infiniteIntelligenceScore = 99.999,
        continuousLearningRatePct = 99.998,
        eternalGrowthMultiplier = 18.6,
        universalOptimizationPct = 99.997,
        perpetualContinuityScore = 99.999,
        controllerTelemetry = "VASCS Eternity Intelligence Controller autonomously operating perpetual learning and continuous growth across 284 economies."
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(bottom = 32.dp)
    ) {
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Eternity Intelligence Controller",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = core.controllerTelemetry,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Button(
                        onClick = onRunCore,
                        modifier = Modifier.fillMaxWidth().testTag("run_eternity_core_button")
                    ) {
                        Icon(Icons.Default.PlayArrow, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Execute Core Synchronization")
                    }
                }
            }
        }

        item {
            Text(
                text = "Core Perpetual Responsibilities",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
            )
        }

        item {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                MetricCard(
                    title = "Perpetual Intelligence",
                    value = "${core.infiniteIntelligenceScore}",
                    subtitle = "Score",
                    modifier = Modifier.weight(1f)
                )
                MetricCard(
                    title = "Continuous Learning",
                    value = "${core.continuousLearningRatePct}%",
                    subtitle = "Learning Rate",
                    modifier = Modifier.weight(1f)
                )
            }
        }

        item {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                MetricCard(
                    title = "Infinite Growth",
                    value = "${core.eternalGrowthMultiplier}x",
                    subtitle = "Multiplier",
                    modifier = Modifier.weight(1f)
                )
                MetricCard(
                    title = "Universal Optimization",
                    value = "${core.universalOptimizationPct}%",
                    subtitle = "Efficiency",
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

// -------------------------------------------------------------
// MODULE 2: SELF EVOLVING AI NETWORK
// -------------------------------------------------------------
@Composable
fun AiEvolutionNetworkView() {
    val capabilities = listOf(
        Pair("Self Learning", "Continuous deep-reinforcement learning synthesizing global trade flows in real-time."),
        Pair("Self Optimization", "Lossless architectural weight pruning improving inferencing velocity by 400x."),
        Pair("Self Improvement", "Autonomous code rewriting and recursive self-testing across all commerce pipelines."),
        Pair("Self Expansion", "Dynamic multi-modal node orchestration spanning physical, sovereign, and virtual domains.")
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(bottom = 32.dp)
    ) {
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.tertiaryContainer),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "AI EVOLUTION INDEX: 99.999",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Black, color = MaterialTheme.colorScheme.onTertiaryContainer)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Autonomous self-directed artificial neural mesh evolving without human intervention.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onTertiaryContainer.copy(alpha = 0.8f)
                    )
                }
            }
        }

        items(capabilities) { (cap, desc) ->
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                shape = RoundedCornerShape(12.dp)
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.AutoAwesome,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(28.dp)
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Text(text = cap, style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold))
                        Text(text = desc, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }
            }
        }
    }
}

// -------------------------------------------------------------
// MODULE 3: INFINITE WEALTH ENGINE
// -------------------------------------------------------------
@Composable
fun WealthUniverseModuleView(
    wealthList: List<WealthUniverseEntity>,
    onRecalculate: () -> Unit,
    onAddClick: () -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(bottom = 32.dp)
    ) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Infinite Wealth Index: 99.995",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                )
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    FilledTonalButton(onClick = onRecalculate) {
                        Text("Recalculate")
                    }
                    Button(onClick = onAddClick) {
                        Icon(Icons.Default.Add, contentDescription = null)
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Add Domain")
                    }
                }
            }
        }

        items(wealthList) { wealth ->
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = wealth.wealthDomain,
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                            modifier = Modifier.weight(1f)
                        )
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = MaterialTheme.colorScheme.primary
                        ) {
                            Text(
                                text = "Eff: ${wealth.capitalEfficiencyPct}%",
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                style = MaterialTheme.typography.labelSmall.copy(color = MaterialTheme.colorScheme.onPrimary, fontWeight = FontWeight.Bold)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Column {
                            Text("Assets", style = MaterialTheme.typography.labelSmall)
                            Text("$${wealth.totalAssetsBillionUsd}B", style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold))
                        }
                        Column {
                            Text("Revenue", style = MaterialTheme.typography.labelSmall)
                            Text("$${wealth.cumulativeRevenueBillionUsd}B", style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold))
                        }
                        Column {
                            Text("Net Profit", style = MaterialTheme.typography.labelSmall)
                            Text("$${wealth.netProfitBillionUsd}B", style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold, color = Color(0xFF00C853)))
                        }
                        Column {
                            Text("Growth YoY", style = MaterialTheme.typography.labelSmall)
                            Text("+${wealth.capitalGrowthYoYPct}%", style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary))
                        }
                    }
                }
            }
        }
    }
}

// -------------------------------------------------------------
// MODULE 4: UNIVERSAL OPPORTUNITY MATRIX
// -------------------------------------------------------------
@Composable
fun OpportunityMatrixModuleView() {
    val matrixItems = listOf(
        Triple("New Markets", "Sub-Saharan & Latin American Luxury Artisan Expansion", 99.85),
        Triple("New Industries", "Robotic Micro-Loom Automated Crafting Ecosystem", 99.92),
        Triple("New Products", "Self-Cooling Climate-Adaptive Silk Wearables", 99.96),
        Triple("New Economies", "Virtual Fashion Sovereignty & Avatar Haute Couture", 99.99),
        Triple("Future Opportunities", "Decentralized Zero-Knowledge Provenance Passports", 99.94)
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(bottom = 32.dp)
    ) {
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "OPPORTUNITY DISCOVERY SCORE: 99.932",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Black, color = MaterialTheme.colorScheme.onSecondaryContainer)
                    )
                    Text(
                        text = "Scanning real-time global economic shifts to synthesize continuous growth vectors.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.8f)
                    )
                }
            }
        }

        items(matrixItems) { (category, title, score) ->
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                shape = RoundedCornerShape(12.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(text = category, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.primary)
                        Text(text = title, style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold))
                    }
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = MaterialTheme.colorScheme.primaryContainer
                    ) {
                        Text(
                            text = "$score",
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold)
                        )
                    }
                }
            }
        }
    }
}

// -------------------------------------------------------------
// MODULE 5: PERPETUAL DEMAND ENGINE
// -------------------------------------------------------------
@Composable
fun DemandUniverseModuleView(
    demandList: List<DemandUniverseEntity>,
    onForecast: () -> Unit,
    onAddClick: () -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(bottom = 32.dp)
    ) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Future Demand Index: 99.96",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                )
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    FilledTonalButton(onClick = onForecast) {
                        Text("Forecast")
                    }
                    Button(onClick = onAddClick) {
                        Icon(Icons.Default.Add, contentDescription = null)
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Add")
                    }
                }
            }
        }

        items(demandList) { demand ->
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = demand.forecastHorizon,
                            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Black, color = MaterialTheme.colorScheme.primary)
                        )
                        Text(
                            text = "Conf: ${demand.demandConfidencePct}%",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.outline
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = demand.productSector,
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = demand.demandDriverSummary,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Units: ${demand.projectedUnitsDemand}", style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.SemiBold))
                        Text("Revenue: $${demand.projectedRevenueMillionUsd} Million", style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary))
                        Text("Spike: +${demand.seasonalGrowthSpikePct}%", style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold, color = Color(0xFF00C853)))
                    }
                }
            }
        }
    }
}

// -------------------------------------------------------------
// MODULE 6: UNIVERSAL CAPITAL AI
// -------------------------------------------------------------
@Composable
fun CapitalUniverseModuleView(
    capitalList: List<CapitalUniverseEntity>,
    onOptimize: () -> Unit,
    onAddClick: () -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(bottom = 32.dp)
    ) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Capital Efficiency Score: 99.98",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                )
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    FilledTonalButton(onClick = onOptimize) {
                        Text("Optimize")
                    }
                    Button(onClick = onAddClick) {
                        Icon(Icons.Default.Add, contentDescription = null)
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Allocate")
                    }
                }
            }
        }

        items(capitalList) { capital ->
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = capital.capitalCategory,
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
                        )
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = MaterialTheme.colorScheme.secondaryContainer
                        ) {
                            Text(
                                text = capital.liquidityHealthStatus,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = capital.automatedReinvestmentPlan,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text("Allocated", style = MaterialTheme.typography.labelSmall)
                            Text("$${capital.allocatedCapacityMillionUsd} Million", style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold))
                        }
                        Column {
                            Text("Deployed", style = MaterialTheme.typography.labelSmall)
                            Text("$${capital.deployedAmountMillionUsd} Million", style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold))
                        }
                        Column {
                            Text("Ann. ROI", style = MaterialTheme.typography.labelSmall)
                            Text("${capital.annualizedRoiPct}%", style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold, color = Color(0xFF00C853)))
                        }
                    }
                }
            }
        }
    }
}

// -------------------------------------------------------------
// MODULE 7: INFINITE TRADE GRID
// -------------------------------------------------------------
@Composable
fun TradeInfinityModuleView(
    tradeList: List<TradeInfinityEntity>,
    onOptimize: () -> Unit,
    onAddClick: () -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(bottom = 32.dp)
    ) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Trade Universe Index: 99.996",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                )
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    FilledTonalButton(onClick = onOptimize) {
                        Text("Optimize")
                    }
                    Button(onClick = onAddClick) {
                        Icon(Icons.Default.Add, contentDescription = null)
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Connect")
                    }
                }
            }
        }

        items(tradeList) { trade ->
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = trade.tradeCorridorTitle,
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = trade.connectedSovereignZones,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Capacity: $${trade.volumeCapacityBillionUsd}B", style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.SemiBold))
                        Text("Lag: ${trade.transactionLagMicroseconds}μs", style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold, color = Color(0xFF00C853)))
                        Text("Tariff Opt: ${trade.tariffOptimizationPct}%", style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold))
                    }
                }
            }
        }
    }
}

// -------------------------------------------------------------
// MODULE 8: ETERNITY DIGITAL REALITY
// -------------------------------------------------------------
@Composable
fun DigitalEternityModuleView() {
    val twins = listOf(
        Pair("Economic Twins", "10-Billion agent dynamic Monte Carlo macroeconomic simulation reproducing all global fiscal levers."),
        Pair("Business Twins", "Microsecond real-time simulation of all artisan looms, inventory pipelines, and retail order flows."),
        Pair("Market Twins", "Predictive demand and consumer sentiment simulations anticipating future fashion trends."),
        Pair("Civilization Twins", "Cross-civilization trade and resource allocation models maintaining perpetual planetary equilibrium.")
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(bottom = 32.dp)
    ) {
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "INFINITE SIMULATION ENGINE",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Black, color = MaterialTheme.colorScheme.primary)
                    )
                    Text(
                        text = "Simulating parallel economic scenarios with 99.999% fidelity before live market execution.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f)
                    )
                }
            }
        }

        items(twins) { (twin, desc) ->
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                shape = RoundedCornerShape(12.dp)
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.Layers,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(28.dp)
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Text(text = twin, style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold))
                        Text(text = desc, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }
            }
        }
    }
}

// -------------------------------------------------------------
// MODULE 9: UNIVERSAL DECISION MATRIX
// -------------------------------------------------------------
@Composable
fun DecisionMatrixModuleView() {
    val decisions = listOf(
        Pair("Investments", "Autonomous redeployment of $1.8B surplus cash into sovereign bio-silk farms."),
        Pair("Expansion", "Automated commissioning of 12 new high-speed distribution nodes in Singapore & Dubai."),
        Pair("Pricing", "Sub-millisecond dynamic margin optimization protecting 62.4% gross profit margin."),
        Pair("Resources", "Real-time raw mulberry fiber balancing eliminating warehouse holding cost completely."),
        Pair("Innovation", "Immediate patent authorization and venture seeding for carbon-negative dye formulas.")
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(bottom = 32.dp)
    ) {
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "DECISION ACCURACY INDEX: 99.998",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                    )
                    Text(
                        text = "Real-time autonomous decision engine eliminating operational latency across all enterprise tiers.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        items(decisions) { (domain, action) ->
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                shape = RoundedCornerShape(12.dp)
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.CheckCircle,
                        contentDescription = null,
                        tint = Color(0xFF00C853),
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Text(text = domain, style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold))
                        Text(text = action, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }
            }
        }
    }
}

// -------------------------------------------------------------
// MODULE 10: ETERNITY KNOWLEDGE ENGINE
// -------------------------------------------------------------
@Composable
fun KnowledgeEternityModuleView(knowledgeList: List<KnowledgeEternityEntity>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(bottom = 32.dp)
    ) {
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "UNIVERSAL KNOWLEDGE SCORE: 99.998",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )
                    Text(
                        text = "Synthesizing timeless heritage wisdom with predictive machine intelligence.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        items(knowledgeList) { item ->
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = item.temporalHorizon,
                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Black, color = MaterialTheme.colorScheme.primary)
                        )
                        Text(
                            text = "${item.synthesizedDataPointsTrillion}T Points",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.outline
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = item.knowledgeDomain,
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = item.actionableWisdomSummary,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

// -------------------------------------------------------------
// MODULE 11: PERPETUAL INNOVATION ENGINE
// -------------------------------------------------------------
@Composable
fun InnovationEternityModuleView(innovations: List<EternityInnovationEntity>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(bottom = 32.dp)
    ) {
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "INNOVATION GROWTH INDEX: 99.997",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                    )
                    Text(
                        text = "Autonomous generation of disruptive textile technologies, patents, and business models.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        items(innovations) { item ->
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = item.innovationCategory,
                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                        )
                        Surface(
                            shape = RoundedCornerShape(4.dp),
                            color = MaterialTheme.colorScheme.primaryContainer
                        ) {
                            Text(
                                text = item.perpetualPatentCode,
                                modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp),
                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = item.innovationName,
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Yield: $${item.projectedYieldBillionUsd}B", style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.SemiBold))
                        Text("Velocity: ${item.deploymentVelocity}", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.outline)
                        Text("Index: ${item.innovationGrowthIndex}", style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary))
                    }
                }
            }
        }
    }
}

// -------------------------------------------------------------
// MODULE 12: UNIVERSAL RISK SHIELD
// -------------------------------------------------------------
@Composable
fun RiskShieldModuleView(risks: List<RiskShieldEntity>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(bottom = 32.dp)
    ) {
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "RISK PROTECTION INDEX: 99.999",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, color = Color(0xFF00C853))
                    )
                    Text(
                        text = "Perpetual automated defense shielding markets, revenue, capital, and operations.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        items(risks) { item ->
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Vector: ${item.protectedVector}",
                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                        )
                        Surface(
                            shape = RoundedCornerShape(4.dp),
                            color = Color(0xFFE8F5E9)
                        ) {
                            Text(
                                text = item.shieldStatus,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                style = MaterialTheme.typography.labelSmall.copy(color = Color(0xFF2E7D32), fontWeight = FontWeight.Bold)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = item.threatDescription,
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = item.automatedShieldProtocol,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

// -------------------------------------------------------------
// MODULE 13: ETERNITY HEALTH ENGINE
// -------------------------------------------------------------
@Composable
fun EternityHealthModuleView(
    healthList: List<EternityHealthEntity>,
    onRecalculate: () -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(bottom = 32.dp)
    ) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Eternity Health Index: 99.999",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                )
                Button(onClick = onRecalculate) {
                    Icon(Icons.Default.Refresh, contentDescription = null)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Recalculate")
                }
            }
        }

        items(healthList) { item ->
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = item.dimensionName,
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
                        )
                        Text(
                            text = "${item.score}%",
                            style = MaterialTheme.typography.titleSmall.copy(
                                fontWeight = FontWeight.Black,
                                color = Color(0xFF00C853)
                            )
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = item.diagnosticSummary,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    LinearProgressIndicator(
                        progress = { (item.score / 100.0).toFloat() },
                        modifier = Modifier.fillMaxWidth().height(6.dp).clip(RoundedCornerShape(3.dp)),
                        color = MaterialTheme.colorScheme.primary,
                        trackColor = MaterialTheme.colorScheme.surface
                    )
                }
            }
        }
    }
}

// -------------------------------------------------------------
// MODULE 14: ETERNITY COMMAND TOWER
// -------------------------------------------------------------
@Composable
fun EternityTowerModuleView(
    telemetryStream: List<String>,
    eternityIndex: Double
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(bottom = 32.dp)
    ) {
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "ETERNITY INTELLIGENCE INDEX",
                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                    )
                    Text(
                        text = String.format("%.3f / 100.0", eternityIndex),
                        style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Black)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Active Surveillance: AI Systems • Markets • Industries • Economies • Trade Networks • Innovation Systems",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.outline
                    )
                }
            }
        }

        item {
            Text(
                text = "Live Perpetual Telemetry Stream",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
            )
        }

        items(telemetryStream) { log ->
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = log,
                    modifier = Modifier.padding(12.dp),
                    style = MaterialTheme.typography.bodySmall.copy(fontFamily = FontFamily.Monospace)
                )
            }
        }
    }
}

// -------------------------------------------------------------
// MODULE 15: UNIVERSAL CONTINUITY ENGINE
// -------------------------------------------------------------
@Composable
fun ContinuityEngineModuleView() {
    val pillars = listOf(
        Triple("Continuous Learning", "Ingesting 140+ TB per hour of macroeconomic data with zero retraining interruption.", 99.998),
        Triple("Continuous Growth", "Automated expansion multiplying cross-border commerce volume at 18.6x trajectory.", 99.995),
        Triple("Continuous Expansion", "Self-provisioning cloud compute nodes and edge artisan hubs globally.", 99.999),
        Triple("Continuous Optimization", "Zero human dependency in capital routing, supply-chain leveling, and margin defense.", 99.997)
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(bottom = 32.dp)
    ) {
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "CONTINUITY SUCCESS INDEX: 99.999",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Black, color = MaterialTheme.colorScheme.primary)
                    )
                    Text(
                        text = "Guaranteeing perpetual self-sustaining operation without any single point of failure.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f)
                    )
                }
            }
        }

        items(pillars) { (title, desc, score) ->
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                shape = RoundedCornerShape(12.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(text = title, style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold))
                        Text(text = desc, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = MaterialTheme.colorScheme.primary
                    ) {
                        Text(
                            text = "$score",
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                            style = MaterialTheme.typography.labelSmall.copy(color = MaterialTheme.colorScheme.onPrimary, fontWeight = FontWeight.Bold)
                        )
                    }
                }
            }
        }
    }
}

// -------------------------------------------------------------
// HELPER COMPONENTS & DIALOGS
// -------------------------------------------------------------
@Composable
fun MetricCard(
    title: String,
    value: String,
    subtitle: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(text = title, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.outline)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = value, style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Black, color = MaterialTheme.colorScheme.primary))
            Text(text = subtitle, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}

@Composable
fun AddWealthDialog(
    onDismiss: () -> Unit,
    onConfirm: (domain: String, assets: Double, rev: Double, profit: Double) -> Unit
) {
    var domain by remember { mutableStateOf("") }
    var assets by remember { mutableStateOf("15.0") }
    var rev by remember { mutableStateOf("8.5") }
    var profit by remember { mutableStateOf("5.2") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add Wealth Domain") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = domain,
                    onValueChange = { domain = it },
                    label = { Text("Wealth Domain Title") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = assets,
                    onValueChange = { assets = it },
                    label = { Text("Assets ($ Billion)") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = rev,
                    onValueChange = { rev = it },
                    label = { Text("Revenue ($ Billion)") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = profit,
                    onValueChange = { profit = it },
                    label = { Text("Net Profit ($ Billion)") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (domain.isNotBlank()) {
                        onConfirm(domain, assets.toDoubleOrNull() ?: 10.0, rev.toDoubleOrNull() ?: 5.0, profit.toDoubleOrNull() ?: 3.0)
                    }
                }
            ) {
                Text("Add")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancel") }
        }
    )
}

@Composable
private fun AddDemandDialog(
    onDismiss: () -> Unit,
    onConfirm: (horizon: String, sector: String, units: Long, rev: Double, summary: String) -> Unit
) {
    var horizon by remember { mutableStateOf("Monthly Demand") }
    var sector by remember { mutableStateOf("") }
    var units by remember { mutableStateOf("250000") }
    var rev by remember { mutableStateOf("180.0") }
    var summary by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add Demand Forecast") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = horizon,
                    onValueChange = { horizon = it },
                    label = { Text("Horizon (Daily/Monthly/Yearly/Decade)") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = sector,
                    onValueChange = { sector = it },
                    label = { Text("Product Sector") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = units,
                    onValueChange = { units = it },
                    label = { Text("Projected Units") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = rev,
                    onValueChange = { rev = it },
                    label = { Text("Projected Revenue (Million USD)") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = summary,
                    onValueChange = { summary = it },
                    label = { Text("Demand Drivers") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (sector.isNotBlank()) {
                        onConfirm(horizon, sector, units.toLongOrNull() ?: 100000L, rev.toDoubleOrNull() ?: 50.0, summary)
                    }
                }
            ) {
                Text("Add")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancel") }
        }
    )
}

@Composable
private fun AddCapitalDialog(
    onDismiss: () -> Unit,
    onConfirm: (category: String, capacity: Double, deployed: Double, roi: Double, plan: String) -> Unit
) {
    var category by remember { mutableStateOf("") }
    var capacity by remember { mutableStateOf("1500.0") }
    var deployed by remember { mutableStateOf("1200.0") }
    var roi by remember { mutableStateOf("45.0") }
    var plan by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add Capital Allocation") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = category,
                    onValueChange = { category = it },
                    label = { Text("Capital Category") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = capacity,
                    onValueChange = { capacity = it },
                    label = { Text("Capacity (Million USD)") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = deployed,
                    onValueChange = { deployed = it },
                    label = { Text("Deployed (Million USD)") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = roi,
                    onValueChange = { roi = it },
                    label = { Text("Annualized ROI (%)") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = plan,
                    onValueChange = { plan = it },
                    label = { Text("Reinvestment Plan") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (category.isNotBlank()) {
                        onConfirm(category, capacity.toDoubleOrNull() ?: 1000.0, deployed.toDoubleOrNull() ?: 800.0, roi.toDoubleOrNull() ?: 30.0, plan)
                    }
                }
            ) {
                Text("Allocate")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancel") }
        }
    )
}

@Composable
fun AddTradeDialog(
    onDismiss: () -> Unit,
    onConfirm: (title: String, zones: String, capacity: Double, lag: Long) -> Unit
) {
    var title by remember { mutableStateOf("") }
    var zones by remember { mutableStateOf("") }
    var capacity by remember { mutableStateOf("35.0") }
    var lag by remember { mutableStateOf("150") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Connect Trade Corridor") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Corridor Title") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = zones,
                    onValueChange = { zones = it },
                    label = { Text("Connected Zones (e.g. India • US)") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = capacity,
                    onValueChange = { capacity = it },
                    label = { Text("Capacity ($ Billion)") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = lag,
                    onValueChange = { lag = it },
                    label = { Text("Lag (Microseconds)") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (title.isNotBlank()) {
                        onConfirm(title, zones, capacity.toDoubleOrNull() ?: 20.0, lag.toLongOrNull() ?: 200L)
                    }
                }
            ) {
                Text("Connect")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancel") }
        }
    )
}
