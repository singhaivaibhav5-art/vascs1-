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
import com.example.vascs.ui.viewmodel.AscensionViewModel

enum class AscensionModule(val menuTitle: String, val icon: ImageVector) {
    ASCENSION_CORE("Ascension Core", Icons.Default.Public),
    CIVILIZATION_ENGINE("Civilization Engine", Icons.Default.AccountBalance),
    AI_GOVERNMENT("AI Government", Icons.Default.Gavel),
    RESOURCE_INTELLIGENCE("Resource Intelligence", Icons.Default.Hub),
    EXPANSION_NETWORK("Expansion Network", Icons.Default.Language),
    KNOWLEDGE_MATRIX("Knowledge Matrix", Icons.Default.Psychology),
    EVOLUTION_INTELLIGENCE("Evolution Intelligence", Icons.Default.AutoAwesome),
    TRADE_UNIVERSE("Trade Universe", Icons.Default.SwapCalls),
    DIGITAL_CIVILIZATION("Digital Civilization", Icons.Default.DeviceHub),
    PROSPERITY_ENGINE("Prosperity Engine", Icons.Default.MonetizationOn),
    CIVILIZATION_RESEARCH("Civilization Research", Icons.Default.Science),
    INNOVATION_UNIVERSE("Innovation Universe", Icons.Default.Lightbulb),
    DECISION_UNIVERSE("Decision Universe", Icons.Default.AccountTree),
    ASCENSION_HEALTH("Ascension Health", Icons.Default.HealthAndSafety),
    ASCENSION_TOWER("Ascension Tower", Icons.Default.Podcasts)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AscensionPlatformScreen(
    viewModel: AscensionViewModel,
    onBackClick: (() -> Unit)? = null
) {
    var selectedModule by remember { mutableStateOf(AscensionModule.ASCENSION_CORE) }
    var showAddCivDialog by remember { mutableStateOf(false) }
    var showAddTradeDialog by remember { mutableStateOf(false) }
    var showPolicyDialog by remember { mutableStateOf(false) }

    val civilizationList by viewModel.civilization.collectAsState()
    val resourceList by viewModel.resources.collectAsState()
    val tradeNetworkList by viewModel.tradeNetwork.collectAsState()
    val prosperityList by viewModel.prosperity.collectAsState()
    val innovationList by viewModel.innovation.collectAsState()
    val decisionList by viewModel.decisions.collectAsState()
    val healthList by viewModel.health.collectAsState()
    val coreList by viewModel.ascensionCores.collectAsState()
    val ascensionIndex by viewModel.ascensionIndex.collectAsState()
    val stabilityIndex by viewModel.stabilityIndex.collectAsState()
    val prosperityScore by viewModel.prosperityScore.collectAsState()
    val evolutionIndex by viewModel.evolutionIndex.collectAsState()
    val isSimulating by viewModel.isSimulating.collectAsState()
    val telemetryFeed by viewModel.telemetryFeed.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "VASCS ASCENSION",
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
                                    text = "CHECKPOINT 20.0",
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                        Text(
                            text = "Self-Governing Economic Universe • Universal AI Civilization",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                },
                navigationIcon = {
                    if (onBackClick != null) {
                        IconButton(
                            onClick = onBackClick,
                            modifier = Modifier.testTag("ascension_back_button")
                        ) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                        }
                    }
                },
                actions = {
                    IconButton(
                        onClick = { viewModel.runAscensionCore() },
                        modifier = Modifier.testTag("ascension_sync_button")
                    ) {
                        Icon(Icons.Default.Sync, contentDescription = "Sync Core")
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
                            text = if (isSimulating) "OPTIMIZING CIVILIZATION UNIVERSE..." else "UNIVERSE AUTONOMOUS • 12 CIVILIZATIONS",
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    Text(
                        text = "Ascension Index: ${String.format("%.2f", ascensionIndex)}%",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }

            // Horizontal Module Navigation Tabs
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                contentPadding = PaddingValues(horizontal = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(AscensionModule.entries) { module ->
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

            // Main Module Content
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
            ) {
                when (selectedModule) {
                    AscensionModule.ASCENSION_CORE -> AscensionCoreModuleView(viewModel, coreList, ascensionIndex, stabilityIndex)
                    AscensionModule.CIVILIZATION_ENGINE -> CivilizationEngineModuleView(viewModel, civilizationList) { showAddCivDialog = true }
                    AscensionModule.AI_GOVERNMENT -> AiGovernmentModuleView(viewModel, stabilityIndex) { showPolicyDialog = true }
                    AscensionModule.RESOURCE_INTELLIGENCE -> ResourceIntelligenceModuleView(viewModel, resourceList)
                    AscensionModule.EXPANSION_NETWORK -> ExpansionNetworkModuleView(viewModel, civilizationList, tradeNetworkList)
                    AscensionModule.KNOWLEDGE_MATRIX -> KnowledgeMatrixModuleView(viewModel)
                    AscensionModule.EVOLUTION_INTELLIGENCE -> EvolutionIntelligenceModuleView(viewModel, evolutionIndex)
                    AscensionModule.TRADE_UNIVERSE -> TradeUniverseModuleView(viewModel, tradeNetworkList) { showAddTradeDialog = true }
                    AscensionModule.DIGITAL_CIVILIZATION -> DigitalCivilizationModuleView(viewModel, civilizationList)
                    AscensionModule.PROSPERITY_ENGINE -> ProsperityEngineModuleView(viewModel, prosperityList, prosperityScore)
                    AscensionModule.CIVILIZATION_RESEARCH -> CivilizationResearchModuleView(viewModel)
                    AscensionModule.INNOVATION_UNIVERSE -> InnovationUniverseModuleView(viewModel, innovationList)
                    AscensionModule.DECISION_UNIVERSE -> DecisionUniverseModuleView(viewModel, decisionList)
                    AscensionModule.ASCENSION_HEALTH -> AscensionHealthModuleView(viewModel, healthList)
                    AscensionModule.ASCENSION_TOWER -> AscensionTowerModuleView(viewModel, ascensionIndex, stabilityIndex, prosperityScore, evolutionIndex, telemetryFeed)
                }
            }
        }
    }

    // Dialogs
    if (showAddCivDialog) {
        AddCivilizationDialog(
            onDismiss = { showAddCivDialog = false },
            onAdd = { name, zone, companies, industries, volume, autonomy ->
                viewModel.manageCivilization(name, zone, companies, industries, volume, autonomy)
                showAddCivDialog = false
            }
        )
    }

    if (showAddTradeDialog) {
        AddTradeRouteDialog(
            onDismiss = { showAddTradeDialog = false },
            onAdd = { origin, destination, industries, throughput ->
                viewModel.expandEconomy(origin, destination, industries, throughput)
                showAddTradeDialog = false
            }
        )
    }

    if (showPolicyDialog) {
        AddPolicyDialog(
            onDismiss = { showPolicyDialog = false },
            onEnact = { title, domain, delta ->
                viewModel.enactGovernmentPolicy(title, domain, delta)
                showPolicyDialog = false
            }
        )
    }
}

// -------------------------------------------------------------
// MODULE 1: ASCENSION CORE
// -------------------------------------------------------------
@Composable
fun AscensionCoreModuleView(
    viewModel: AscensionViewModel,
    cores: List<AscensionCoreEntity>,
    ascensionIndex: Double,
    stabilityIndex: Double
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
                            text = "ECONOMIC UNIVERSE CONTROLLER",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                        Icon(Icons.Default.Public, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Autonomous macroeconomic orchestrator continuously balancing trade flow, capital yield, and resource distribution across 12 sovereign economic civilizations.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.85f)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        MetricItem("Ascension Index", "${String.format("%.2f", ascensionIndex)}%")
                        MetricItem("Universe Stability", "${String.format("%.2f", stabilityIndex)}%")
                        MetricItem("Growth Multiplier", "8.8x")
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
                    text = "Universe Core Responsibilities",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Button(
                    onClick = { viewModel.runAscensionCore() },
                    modifier = Modifier.testTag("btn_run_ascension_core")
                ) {
                    Icon(Icons.Default.PlayArrow, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Re-Align Universe")
                }
            }
        }

        items(listOf(
            Triple("Economic Governance", "Autonomous macro-policy enactment, zero-leakage capital redistribution, and monetary equilibrium.", Icons.Default.Gavel),
            Triple("Civilization Coordination", "Synchronized alignment of 12 regional federations, 420 industries, and multi-tier supplier nodes.", Icons.Default.AccountBalance),
            Triple("Growth Management", "Compound exponential scaling targeting +52.4% annual autonomous value expansion.", Icons.Default.TrendingUp),
            Triple("Resource Optimization", "Real-time AI reallocation of capital, inventories, air freight bays, and neural GPU clusters.", Icons.Default.Hub)
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
// MODULE 2: ECONOMIC CIVILIZATION ENGINE
// -------------------------------------------------------------
@Composable
fun CivilizationEngineModuleView(
    viewModel: AscensionViewModel,
    civilizations: List<EconomicCivilizationEntity>,
    onAddClick: () -> Unit
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
                        text = "Autonomous Economic Civilizations",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "${civilizations.size} Sovereign Guilds & Federations Active",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Button(
                    onClick = onAddClick,
                    modifier = Modifier.testTag("btn_add_civilization")
                ) {
                    Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Seed Civilization")
                }
            }
        }

        items(civilizations) { civ ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Top
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = civ.civilizationName,
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = civ.economicZone,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = MaterialTheme.colorScheme.tertiaryContainer
                        ) {
                            Text(
                                text = "${civ.autonomyLevelPct}% Autonomy",
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onTertiaryContainer
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        MetricItem("Companies", "${civ.managedCompaniesCount}")
                        MetricItem("Industries", "${civ.managedIndustriesCount}")
                        MetricItem("Trade Volume", "$${civ.totalTradeVolumeBillionUsd}B")
                        MetricItem("Growth", "+${civ.growthRatePct}%")
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Button(
                        onClick = { viewModel.simulateCivilizationTwin(civ.civilizationName) },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
                    ) {
                        Icon(Icons.Default.PlayArrow, contentDescription = null, modifier = Modifier.size(14.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Simulate 100M Macro Iterations")
                    }
                }
            }
        }
    }
}

// -------------------------------------------------------------
// MODULE 3: AI GOVERNMENT SYSTEM
// -------------------------------------------------------------
@Composable
fun AiGovernmentModuleView(
    viewModel: AscensionViewModel,
    stabilityIndex: Double,
    onEnactPolicyClick: () -> Unit
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
                        text = "AUTONOMOUS AI GOVERNMENT & REGULATION",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "AI governing matrix dynamically calculating and enforcing monetary rules, trade incentives, sustainable export laws, and anti-fragile supply policies.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.85f)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        MetricItem("Stability Index", "${String.format("%.2f", stabilityIndex)}%")
                        MetricItem("Enacted Rules", "428 Active")
                        MetricItem("Compliance", "100% Zero-Leak")
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
                    text = "Active Macro-Economic Policies",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Button(onClick = onEnactPolicyClick) {
                    Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Enact Policy")
                }
            }
        }

        items(listOf(
            Triple("Zero-Tariff Artisan Sovereign Corridor", "Eliminates all cross-border friction for authentic handloom sarees entering GCC & North American markets.", "+0.02% Stability"),
            Triple("Dynamic Reserve Liquidity Buffering", "Automatically allocates sovereign reserves when cocoon auction prices experience >10% spot volatility.", "+0.03% Stability"),
            Triple("Autonomous Patent Protection DAO", "Real-time AI monitoring preventing unauthorized cloning of heritage weave designs globally.", "+0.01% Stability"),
            Triple("Sustainable Bio-Fiber Growth Mandate", "Provides 15% automated tax credit for zero-chemical jacquard dye processing.", "+0.02% Stability")
        )) { (title, desc, impact) ->
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(title, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                        Text(impact, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(desc, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
        }
    }
}

// -------------------------------------------------------------
// MODULE 4: GLOBAL RESOURCE ENGINE
// -------------------------------------------------------------
@Composable
fun ResourceIntelligenceModuleView(
    viewModel: AscensionViewModel,
    resources: List<ResourceIntelligenceEntity>
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
                        text = "Resource Intelligence Engine",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Capital, Inventory, Supply, Labor, & AI Compute",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Button(onClick = { viewModel.optimizeResources() }) {
                    Icon(Icons.Default.Bolt, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Optimize All")
                }
            }
        }

        items(resources) { res ->
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(res.resourceName, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                            Text(res.resourceCategory, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.primary)
                        }
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = MaterialTheme.colorScheme.tertiaryContainer
                        ) {
                            Text(
                                text = "+${res.optimizationGainPct}% Gain",
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
                        MetricItem("Allocated Cap", "$${res.allocatedCapacityUsdMillion}M")
                        MetricItem("Utilization", "${res.utilizationRatePct}%")
                        MetricItem("Risk Level", res.bottleneckRiskLevel)
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Surface(
                        color = MaterialTheme.colorScheme.surfaceVariant,
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Action Plan: ${res.recommendedActionPlan}",
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
// MODULE 5: AUTONOMOUS EXPANSION NETWORK
// -------------------------------------------------------------
@Composable
fun ExpansionNetworkModuleView(
    viewModel: AscensionViewModel,
    civilizations: List<EconomicCivilizationEntity>,
    tradeRoutes: List<TradeUniverseEntity>
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
                        text = "AUTONOMOUS GLOBAL EXPANSION MATRIX",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onTertiaryContainer
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "AI autonomous expansion engine executing simultaneous market penetration into 100+ global diaspora cities with zero manual sales intervention.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onTertiaryContainer.copy(alpha = 0.85f)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        MetricItem("Expansion Success Rate", "99.85%")
                        MetricItem("New Cities Seeding", "148 Hubs")
                        MetricItem("Market Velocity", "4.8x Surge")
                    }
                }
            }
        }

        item {
            Text(
                text = "Target Expansion Corridors",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
        }

        items(listOf(
            Triple("GCC High-End Diaspora Hub (Dubai, Riyadh, Doha)", "Luxury Handloom Sarees with NFC Authenticity & 24h White-Glove Courier", "Success Prob: 99.8%"),
            Triple("North America Metro Corridor (NYC, Toronto, SF, Dallas)", "Omnichannel Saree Boutiques with Generative Virtual Try-On Mirrors", "Success Prob: 99.6%"),
            Triple("European Heritage Network (London, Paris, Frankfurt)", "Sustainable Bio-Silk Drapes with Zero-Carbon Traceability", "Success Prob: 99.2%"),
            Triple("Asia-Pacific Sovereign Corridor (Singapore, Sydney, Tokyo)", "AI-Curated Festive Collections directly dispatched from Surat looms", "Success Prob: 99.7%")
        )) { (corridor, plan, prob) ->
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(corridor, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
                        Text(prob, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(plan, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
        }
    }
}

// -------------------------------------------------------------
// MODULE 6: ASCENSION KNOWLEDGE MATRIX
// -------------------------------------------------------------
@Composable
fun KnowledgeMatrixModuleView(viewModel: AscensionViewModel) {
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
                        text = "UNIVERSAL KNOWLEDGE MATRIX",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Universal repository synthesizing 5,000 years of handloom heritage with real-time global economic data and future trajectory predictions.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.85f)
                    )
                }
            }
        }

        items(listOf(
            Triple("Historical Heritage Knowledge", "5,000+ Traditional Weave Motifs, Royal Zari Formulations, and Master Weaver Techniques catalogued.", Icons.Default.History),
            Triple("Current Real-Time Knowledge", "Live inventory, 142 currency pairs, freight telemetry, and sentiment indices across 84 markets.", Icons.Default.Speed),
            Triple("Future Trajectory Knowledge", "Predictive demand curves, micro-trend forecasting, and 50M simulated growth scenarios.", Icons.Default.Timeline),
            Triple("Evolutionary Self-Knowledge", "Continuous reinforcement learning from every order, dispatch, trade dispute, and price negotiation.", Icons.Default.AutoAwesome)
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
// MODULE 7: ECONOMIC EVOLUTION ENGINE
// -------------------------------------------------------------
@Composable
fun EvolutionIntelligenceModuleView(
    viewModel: AscensionViewModel,
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
                        text = "ECONOMIC EVOLUTION ENGINE",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Autonomous self-upgrading engine that evolves businesses, products, supply chains, and market structures into higher-order economic organisms.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.85f)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        MetricItem("Evolution Index", "${String.format("%.2f", evolutionIndex)}%")
                        MetricItem("Self-Upgrades", "12,450 Iterations")
                        MetricItem("Emergence Rate", "99.96%")
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Button(
                        onClick = { viewModel.triggerEvolution() },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(Icons.Default.AutoAwesome, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Trigger Evolutionary Leap")
                    }
                }
            }
        }

        item {
            Text(
                text = "Evolutionary Frontiers",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
        }

        items(listOf(
            Triple("Autonomous Loom Cluster Self-Configuration", "Jacquard looms auto-reprogram punch patterns based on London/Dubai trend spikes.", "Phase: 100% Deployed"),
            Triple("Self-Synthesizing Brand Micro-Franchises", "AI generates, logos, brands, catalogs, and launches 50 boutique lines in 24 hours.", "Phase: Active"),
            Triple("Zero-Human Liquidity Settlement Protocol", "Instant settlement across bank ledgers, crypto liquidity pools, and trade receivables.", "Phase: Active")
        )) { (title, desc, phase) ->
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(title, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
                        Text(phase, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(desc, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
        }
    }
}

// -------------------------------------------------------------
// MODULE 8: AUTONOMOUS TRADE NETWORK
// -------------------------------------------------------------
@Composable
fun TradeUniverseModuleView(
    viewModel: AscensionViewModel,
    tradeRoutes: List<TradeUniverseEntity>,
    onAddRouteClick: () -> Unit
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
                        text = "Autonomous Trade Universe",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "${tradeRoutes.size} Frictionless Corridors Active",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Button(onClick = onAddRouteClick) {
                    Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("New Trade Route")
                }
            }
        }

        items(tradeRoutes) { route ->
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "${route.originRegion} → ${route.destinationMarket}",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = route.connectedIndustries,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = MaterialTheme.colorScheme.primaryContainer
                        ) {
                            Text(
                                text = "${route.tradeEfficiencyScore}% Eff",
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
                        MetricItem("Throughput", "$${route.tradeThroughputUsdMillion}M")
                        MetricItem("Businesses", "${route.activeBusinessesCount}")
                        MetricItem("Tariff Opt", "${route.tariffOptimizationPct}%")
                        MetricItem("Status", route.routeHealthStatus.take(12))
                    }
                }
            }
        }
    }
}

// -------------------------------------------------------------
// MODULE 9: ASCENSION DIGITAL CIVILIZATION
// -------------------------------------------------------------
@Composable
fun DigitalCivilizationModuleView(
    viewModel: AscensionViewModel,
    civilizations: List<EconomicCivilizationEntity>
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
                        text = "DIGITAL CIVILIZATION TWINS",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Real-time, 1:1 digital twin simulation of global handloom ecosystems, supply routes, consumer behaviors, and sovereign liquidity pools.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        items(civilizations) { civ ->
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text(
                        text = "Digital Twin: ${civ.civilizationName}",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Zone: ${civ.economicZone}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        MetricItem("Fidelity", "99.98%")
                        MetricItem("Sim Speed", "100M iter/sec")
                        MetricItem("Status", "Real-Time Linked")
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Button(
                        onClick = { viewModel.simulateCivilizationTwin(civ.civilizationName) },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(Icons.Default.DeviceHub, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Simulate Twin Stress-Test")
                    }
                }
            }
        }
    }
}

// -------------------------------------------------------------
// MODULE 10: GLOBAL PROSPERITY ENGINE
// -------------------------------------------------------------
@Composable
fun ProsperityEngineModuleView(
    viewModel: AscensionViewModel,
    prosperityRecords: List<ProsperityEngineEntity>,
    prosperityScore: Double
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
                        text = "GLOBAL PROSPERITY & WEALTH ENGINE",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Tracking cumulative artisan wealth creation, capital multiplier generation, and fair equity distribution across all weaver clusters.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.85f)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        MetricItem("Prosperity Index", "${String.format("%.2f", prosperityScore)}%")
                        MetricItem("Total Wealth", "$12.2B USD")
                        MetricItem("Gini Index", "0.12 (Ultra-Fair)")
                    }
                }
            }
        }

        items(prosperityRecords) { record ->
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(record.economicDomain, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
                        Text("${record.prosperityIndex}%", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        MetricItem("Wealth", "$${record.cumulativeWealthUsdMillion}M")
                        MetricItem("Growth YoY", "+${record.annualGrowthRatePct}%")
                        MetricItem("Capital", "$${record.allocatedCapitalUsdMillion}M")
                        MetricItem("Value Created", "$${record.generatedEconomicValueUsdMillion}M")
                    }
                }
            }
        }
    }
}

// -------------------------------------------------------------
// MODULE 11: AI CIVILIZATION RESEARCH LAB
// -------------------------------------------------------------
@Composable
fun CivilizationResearchModuleView(viewModel: AscensionViewModel) {
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
                        text = "CIVILIZATION RESEARCH & FORECAST LAB",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onTertiaryContainer
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Autonomous macroeconomic R&D simulating 2030-2050 textile trade models, bio-fiber materials, and global sovereign supply frameworks.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onTertiaryContainer.copy(alpha = 0.85f)
                    )
                }
            }
        }

        items(listOf(
            Triple("2030 Autonomous Handloom Civilization Blueprint", "Comprehensive roadmap for 10,000 smart-weaving hubs with zero human dispatch paperwork.", "Horizon: 2028-2030"),
            Triple("Quantum Photonic Fiber Weaving Standards", "Next-generation textiles featuring embedded temperature regulation & health monitoring.", "Horizon: 2027"),
            Triple("Universal Sovereign Silk Currency Standard", "Asset-backed trade currency tied to high-grade certified mulberry silk reserves.", "Horizon: 2029")
        )) { (title, desc, horizon) ->
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(title, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
                        Text(horizon, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(desc, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
        }
    }
}

// -------------------------------------------------------------
// MODULE 12: ASCENSION INNOVATION NETWORK
// -------------------------------------------------------------
@Composable
fun InnovationUniverseModuleView(
    viewModel: AscensionViewModel,
    innovations: List<InnovationUniverseEntity>
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
                        text = "Ascension Innovation Network",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "${innovations.size} Sovereign Patents & Models Active",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        items(innovations) { item ->
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(item.innovationTitle, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                            Text(item.patentIdentifier, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.primary)
                        }
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = MaterialTheme.colorScheme.secondaryContainer
                        ) {
                            Text(
                                text = "${item.innovationIndex}% Score",
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
                        MetricItem("Potential", "$${item.economicPotentialUsdMillion}M")
                        MetricItem("Stage", item.readinessStage.take(14))
                        MetricItem("Disruption", "+${item.disruptionFactorPct}%")
                    }
                }
            }
        }
    }
}

// -------------------------------------------------------------
// MODULE 13: AUTONOMOUS DECISION SYSTEM
// -------------------------------------------------------------
@Composable
fun DecisionUniverseModuleView(
    viewModel: AscensionViewModel,
    decisions: List<DecisionUniverseEntity>
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
                        text = "AUTONOMOUS MACRO-DECISION ENGINE",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Multi-agent autonomous consensus mechanism making billion-dollar investment, supply route, and dynamic pricing decisions in milliseconds.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.85f)
                    )
                }
            }
        }

        items(decisions) { dec ->
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(dec.decisionTitle, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                            Text(dec.decisionCategory, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.primary)
                        }
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = MaterialTheme.colorScheme.tertiaryContainer
                        ) {
                            Text(
                                text = "${dec.decisionAccuracyScore}% Acc",
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Text("Action: ${dec.proposedAction}", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        MetricItem("Impact", "$${dec.expectedEconomicImpactUsdMillion}M")
                        MetricItem("Confidence", "${dec.confidenceIntervalPct}%")
                        MetricItem("Status", dec.executionState)
                    }
                }
            }
        }
    }
}

// -------------------------------------------------------------
// MODULE 14: ASCENSION HEALTH ENGINE
// -------------------------------------------------------------
@Composable
fun AscensionHealthModuleView(
    viewModel: AscensionViewModel,
    healthList: List<AscensionHealthEntity>
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
                        text = "Ascension Health Engine",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "5 Universal Health Dimensions Scored",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Button(onClick = { viewModel.calculateAscensionIndex() }) {
                    Icon(Icons.Default.Refresh, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Recalculate Health")
                }
            }
        }

        items(healthList) { item ->
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(item.dimensionName, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = MaterialTheme.colorScheme.primaryContainer
                        ) {
                            Text(
                                text = "${item.score}%",
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    LinearProgressIndicator(
                        progress = { (item.score / 100.0).toFloat().coerceIn(0f, 1f) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(6.dp)
                            .clip(RoundedCornerShape(3.dp)),
                        color = MaterialTheme.colorScheme.primary,
                        trackColor = MaterialTheme.colorScheme.surfaceVariant
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(item.diagnosticSummary, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
        }
    }
}

// -------------------------------------------------------------
// MODULE 15: ASCENSION COMMAND TOWER
// -------------------------------------------------------------
@Composable
fun AscensionTowerModuleView(
    viewModel: AscensionViewModel,
    ascensionIndex: Double,
    stabilityIndex: Double,
    prosperityScore: Double,
    evolutionIndex: Double,
    telemetry: List<String>
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
                            text = "ASCENSION COMMAND TOWER",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                        Icon(Icons.Default.Podcasts, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Unified telemetry and planetary synchronization across all 15 Ascension modules, 12 civilizations, and 84 dynamic trade corridors.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.85f)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        MetricItem("Ascension Index", "${String.format("%.2f", ascensionIndex)}%")
                        MetricItem("Stability Index", "${String.format("%.2f", stabilityIndex)}%")
                        MetricItem("Prosperity Index", "${String.format("%.2f", prosperityScore)}%")
                        MetricItem("Evolution Index", "${String.format("%.2f", evolutionIndex)}%")
                    }
                }
            }
        }

        item {
            Text(
                text = "Live Ascension Telemetry Stream",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
        }

        items(telemetry) { log ->
            Surface(
                color = MaterialTheme.colorScheme.surfaceVariant,
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = log,
                    modifier = Modifier.padding(10.dp),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

// -------------------------------------------------------------
// HELPER COMPONENTS & DIALOGS
// -------------------------------------------------------------
@Composable
fun MetricItem(label: String, value: String) {
    Column {
        Text(text = label, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Text(text = value, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun AddCivilizationDialog(
    onDismiss: () -> Unit,
    onAdd: (String, String, Int, Int, Double, Double) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var zone by remember { mutableStateOf("Indo-Pacific & South Asia Hub") }
    var companies by remember { mutableStateOf("250") }
    var industries by remember { mutableStateOf("12") }
    var volume by remember { mutableStateOf("8.5") }
    var autonomy by remember { mutableStateOf("99.5") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Seed New Economic Civilization") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Civilization Name") },
                    placeholder = { Text("e.g. Indo-Gulf Silk Guild") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = zone,
                    onValueChange = { zone = it },
                    label = { Text("Economic Zone") },
                    modifier = Modifier.fillMaxWidth()
                )
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = companies,
                        onValueChange = { companies = it },
                        label = { Text("Companies") },
                        modifier = Modifier.weight(1f)
                    )
                    OutlinedTextField(
                        value = industries,
                        onValueChange = { industries = it },
                        label = { Text("Industries") },
                        modifier = Modifier.weight(1f)
                    )
                }
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = volume,
                        onValueChange = { volume = it },
                        label = { Text("Trade (B USD)") },
                        modifier = Modifier.weight(1f)
                    )
                    OutlinedTextField(
                        value = autonomy,
                        onValueChange = { autonomy = it },
                        label = { Text("Autonomy (%)") },
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
                            zone,
                            companies.toIntOrNull() ?: 200,
                            industries.toIntOrNull() ?: 10,
                            volume.toDoubleOrNull() ?: 5.0,
                            autonomy.toDoubleOrNull() ?: 99.0
                        )
                    }
                }
            ) {
                Text("Seed")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancel") }
        }
    )
}

@Composable
fun AddTradeRouteDialog(
    onDismiss: () -> Unit,
    onAdd: (String, String, String, Double) -> Unit
) {
    var origin by remember { mutableStateOf("Surat & Varanasi Mega Looms") }
    var destination by remember { mutableStateOf("GCC & North America Hubs") }
    var industries by remember { mutableStateOf("Heritage Silk, Handloom, Smart IoT Drapes") }
    var throughput by remember { mutableStateOf("450.0") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Establish Autonomous Trade Route") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = origin,
                    onValueChange = { origin = it },
                    label = { Text("Origin Region") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = destination,
                    onValueChange = { destination = it },
                    label = { Text("Destination Market") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = industries,
                    onValueChange = { industries = it },
                    label = { Text("Connected Industries") },
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
                    if (origin.isNotBlank() && destination.isNotBlank()) {
                        onAdd(origin, destination, industries, throughput.toDoubleOrNull() ?: 300.0)
                    }
                }
            ) {
                Text("Establish")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancel") }
        }
    )
}

@Composable
fun AddPolicyDialog(
    onDismiss: () -> Unit,
    onEnact: (String, String, Double) -> Unit
) {
    var title by remember { mutableStateOf("") }
    var domain by remember { mutableStateOf("Macro-Economic Stability") }
    var delta by remember { mutableStateOf("0.02") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Enact Macro-Economic Policy") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Policy Title") },
                    placeholder = { Text("e.g. Zero-Friction Silk Sourcing") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = domain,
                    onValueChange = { domain = it },
                    label = { Text("Policy Domain") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = delta,
                    onValueChange = { delta = it },
                    label = { Text("Stability Delta (%)") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (title.isNotBlank()) {
                        onEnact(title, domain, delta.toDoubleOrNull() ?: 0.01)
                    }
                }
            ) {
                Text("Enact")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancel") }
        }
    )
}
