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
import com.example.vascs.ui.viewmodel.TranscendenceViewModel

enum class TranscendenceModule(val menuLabel: String, val title: String, val icon: ImageVector) {
    CORE("Transcendence Core", "Transcendence Intelligence Controller", Icons.Default.AllInclusive),
    REALITY_COMMERCE("Reality Commerce", "Cross Reality Commerce Engine", Icons.Default.Language),
    ENTERPRISE_CREATOR("Enterprise Creator", "Autonomous Enterprise Creator", Icons.Default.DomainAdd),
    OPPORTUNITY_UNIVERSE("Opportunity Universe", "Universal Opportunity Universe", Icons.Default.Explore),
    DEMAND_NETWORK("Demand Network", "Transcendent Demand Network", Icons.Default.TrendingUp),
    CAPITAL_CIVILIZATION("Capital Civilization", "Autonomous Capital Civilization", Icons.Default.AccountBalance),
    DIGITAL_REALITY("Digital Reality", "Transcendence Digital Reality", Icons.Default.Layers),
    DECISION_COSMOS("Decision Cosmos", "Universal Decision Cosmos", Icons.Default.FactCheck),
    KNOWLEDGE_OCEAN("Knowledge Ocean", "Transcendence Knowledge Ocean", Icons.Default.Waves),
    EVOLUTION_ENGINE("Evolution Engine", "Universal Evolution Engine", Icons.Default.Autorenew),
    INNOVATION_MATRIX("Innovation Matrix", "Transcendence Innovation Matrix", Icons.Default.Lightbulb),
    RISK_INTELLIGENCE("Risk Intelligence", "Universal Risk Intelligence", Icons.Default.Shield),
    HEALTH_MATRIX("Health Matrix", "Transcendence Health Matrix", Icons.Default.HealthAndSafety),
    COMMAND_TOWER("Command Tower", "Transcendence Command Tower", Icons.Default.CellTower),
    EXPANSION_ENGINE("Expansion Engine", "Universal Expansion Engine", Icons.Default.Public)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TranscendencePlatformScreen(
    viewModel: TranscendenceViewModel,
    onBackClick: () -> Unit
) {
    var selectedModule by remember { mutableStateOf(TranscendenceModule.CORE) }

    val coreState by viewModel.transcendenceCore.collectAsState()
    val realityCommerceList by viewModel.realityCommerce.collectAsState()
    val enterpriseList by viewModel.enterpriseCreator.collectAsState()
    val opportunityList by viewModel.opportunities.collectAsState()
    val demandList by viewModel.demandNetwork.collectAsState()
    val capitalList by viewModel.capitalCivilization.collectAsState()
    val decisionList by viewModel.decisionCosmos.collectAsState()
    val knowledgeList by viewModel.knowledgeOcean.collectAsState()
    val evolutionList by viewModel.evolutionEngine.collectAsState()
    val realityTwinList by viewModel.realityTwins.collectAsState()
    val innovationList by viewModel.innovationMatrix.collectAsState()
    val riskList by viewModel.riskIntelligence.collectAsState()
    val healthList by viewModel.healthMatrix.collectAsState()
    val expansionList by viewModel.expansionEngine.collectAsState()
    val transcendenceIndex by viewModel.transcendenceIndex.collectAsState()

    val isOperating by viewModel.isOperating.collectAsState()
    val statusMessage by viewModel.statusMessage.collectAsState()
    val telemetryStream by viewModel.telemetryStream.collectAsState()

    var showCreateEnterpriseDialog by remember { mutableStateOf(false) }
    var showAddOpportunityDialog by remember { mutableStateOf(false) }
    var showAddRealityCommerceDialog by remember { mutableStateOf(false) }
    var showAddDemandDialog by remember { mutableStateOf(false) }
    var showAddCapitalDialog by remember { mutableStateOf(false) }

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
                                text = "VASCS TRANSCENDENCE 23.0",
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
                                    text = "BEYOND INTELLIGENCE",
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onPrimaryContainer
                                    )
                                )
                            }
                        }
                        Text(
                            text = "Beyond Business • Beyond Markets • Beyond Economies • Beyond Industries",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.outline
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick, modifier = Modifier.testTag("back_button")) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(
                        onClick = { viewModel.triggerUniversalSync() },
                        enabled = !isOperating,
                        modifier = Modifier.testTag("sync_action_button")
                    ) {
                        if (isOperating) {
                            CircularProgressIndicator(modifier = Modifier.size(20.dp), strokeWidth = 2.dp)
                        } else {
                            Icon(Icons.Default.Sync, contentDescription = "Universal Sync")
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(MaterialTheme.colorScheme.surface)
        ) {
            // Live Cosmic Telemetry Banner
            if (telemetryStream.isNotEmpty()) {
                Surface(
                    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .clip(CircleShape)
                                .background(Color(0xFF4CAF50))
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = telemetryStream.firstOrNull() ?: "Universal Telemetry Active",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontFamily = FontFamily.Monospace,
                                fontSize = 11.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            ),
                            maxLines = 1
                        )
                    }
                }
            }

            // Quick Stats Row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                TranscendenceHeaderMetric(
                    label = "Transcendence Index",
                    value = String.format("%.4f", transcendenceIndex),
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.weight(1f)
                )
                TranscendenceHeaderMetric(
                    label = "Realities Governed",
                    value = "${coreState?.realitiesGovernedCount ?: 1420}",
                    color = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier.weight(1f)
                )
                TranscendenceHeaderMetric(
                    label = "Coordination Rate",
                    value = "${String.format("%.3f", coreState?.universalCoordinationRatePct ?: 99.994)}%",
                    color = Color(0xFF2E7D32),
                    modifier = Modifier.weight(1f)
                )
            }

            // Horizontal Module Navigation Tabs
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp),
                contentPadding = PaddingValues(horizontal = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(TranscendenceModule.entries) { module ->
                    val isSelected = selectedModule == module
                    FilterChip(
                        selected = isSelected,
                        onClick = { selectedModule = module },
                        label = {
                            Text(
                                text = module.menuLabel,
                                style = MaterialTheme.typography.labelMedium.copy(
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                )
                            )
                        },
                        leadingIcon = {
                            Icon(
                                imageVector = module.icon,
                                contentDescription = null,
                                modifier = Modifier.size(16.dp)
                            )
                        },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = MaterialTheme.colorScheme.primary,
                            selectedLabelColor = MaterialTheme.colorScheme.onPrimary,
                            selectedLeadingIconColor = MaterialTheme.colorScheme.onPrimary
                        ),
                        modifier = Modifier.testTag("tab_${module.name.lowercase()}")
                    )
                }
            }

            Divider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))

            // Active Module Body
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                when (selectedModule) {
                    TranscendenceModule.CORE -> CoreModuleView(
                        core = coreState,
                        onRunCore = { viewModel.runTranscendenceCore() },
                        isOperating = isOperating
                    )
                    TranscendenceModule.REALITY_COMMERCE -> RealityCommerceView(
                        items = realityCommerceList,
                        onAnalyze = { viewModel.analyzeRealityCommerce() },
                        onAddClick = { showAddRealityCommerceDialog = true },
                        isOperating = isOperating
                    )
                    TranscendenceModule.ENTERPRISE_CREATOR -> EnterpriseCreatorView(
                        enterprises = enterpriseList,
                        onCreateClick = { showCreateEnterpriseDialog = true },
                        onRefresh = { viewModel.createEnterprise("Synthetix Entity", "Universal Weaving Hub", "Autonomous Conglomerate", 1450.0) },
                        isOperating = isOperating
                    )
                    TranscendenceModule.OPPORTUNITY_UNIVERSE -> OpportunityUniverseView(
                        opportunities = opportunityList,
                        onDiscover = { viewModel.discoverOpportunities() },
                        onAddClick = { showAddOpportunityDialog = true },
                        isOperating = isOperating
                    )
                    TranscendenceModule.DEMAND_NETWORK -> DemandNetworkView(
                        demandList = demandList,
                        onForecast = { viewModel.forecastDemandNetwork() },
                        onAddClick = { showAddDemandDialog = true },
                        isOperating = isOperating
                    )
                    TranscendenceModule.CAPITAL_CIVILIZATION -> CapitalCivilizationView(
                        capitalList = capitalList,
                        onManage = { viewModel.manageCapitalCivilization() },
                        onAddClick = { showAddCapitalDialog = true },
                        isOperating = isOperating
                    )
                    TranscendenceModule.DIGITAL_REALITY -> DigitalRealityView(
                        twins = realityTwinList,
                        onRefresh = { viewModel.triggerUniversalSync() }
                    )
                    TranscendenceModule.DECISION_COSMOS -> DecisionCosmosView(
                        decisions = decisionList,
                        onExecute = { viewModel.executeDecisionCosmos() },
                        isOperating = isOperating
                    )
                    TranscendenceModule.KNOWLEDGE_OCEAN -> KnowledgeOceanView(
                        knowledgeList = knowledgeList
                    )
                    TranscendenceModule.EVOLUTION_ENGINE -> EvolutionEngineView(
                        evolutionList = evolutionList,
                        onEvolve = { viewModel.evolveMarkets() },
                        isOperating = isOperating
                    )
                    TranscendenceModule.INNOVATION_MATRIX -> InnovationMatrixView(
                        innovations = innovationList
                    )
                    TranscendenceModule.RISK_INTELLIGENCE -> RiskIntelligenceView(
                        risks = riskList
                    )
                    TranscendenceModule.HEALTH_MATRIX -> HealthMatrixView(
                        healthList = healthList,
                        transcendenceIndex = transcendenceIndex,
                        onCalculate = { viewModel.calculateTranscendenceIndex() },
                        isOperating = isOperating
                    )
                    TranscendenceModule.COMMAND_TOWER -> CommandTowerView(
                        core = coreState,
                        telemetryStream = telemetryStream,
                        onTriggerDirective = { viewModel.runTranscendenceCore() }
                    )
                    TranscendenceModule.EXPANSION_ENGINE -> ExpansionEngineView(
                        expansions = expansionList,
                        onAccelerate = { viewModel.triggerUniversalSync() }
                    )
                }
            }
        }
    }

    // Dialogs
    if (showCreateEnterpriseDialog) {
        CreateEnterpriseDialog(
            onDismiss = { showCreateEnterpriseDialog = false },
            onCreate = { type, name, model, projection, ceo ->
                viewModel.createEnterprise(type, name, model, projection, ceo)
                showCreateEnterpriseDialog = false
            }
        )
    }

    if (showAddOpportunityDialog) {
        AddOpportunityDialog(
            onDismiss = { showAddOpportunityDialog = false },
            onAdd = { category, title, value, horizon, roadmap ->
                viewModel.addOpportunity(category, title, value, horizon, roadmap)
                showAddOpportunityDialog = false
            }
        )
    }

    if (showAddRealityCommerceDialog) {
        AddRealityCommerceDialog(
            onDismiss = { showAddRealityCommerceDialog = false },
            onAdd = { realm, nodes, volume, latency ->
                viewModel.addRealityCommerce(realm, nodes, volume, latency)
                showAddRealityCommerceDialog = false
            }
        )
    }

    if (showAddDemandDialog) {
        AddDemandDialog(
            onDismiss = { showAddDemandDialog = false },
            onAdd = { tier, sector, units, rev, catalyst ->
                viewModel.addDemandForecast(tier, sector, units, rev, catalyst)
                showAddDemandDialog = false
            }
        )
    }

    if (showAddCapitalDialog) {
        AddCapitalDialog(
            onDismiss = { showAddCapitalDialog = false },
            onAdd = { category, total, allocated, yieldPct, policy ->
                viewModel.addCapitalAllocation(category, total, allocated, yieldPct, policy)
                showAddCapitalDialog = false
            }
        )
    }
}

// -------------------------------------------------------------
// MODULE 1: TRANSCENDENCE CORE
// -------------------------------------------------------------
@Composable
fun CoreModuleView(
    core: TranscendenceCoreEntity?,
    onRunCore: () -> Unit,
    isOperating: Boolean
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                ),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Transcendence Intelligence Controller",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                        Icon(
                            imageVector = Icons.Default.AllInclusive,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = core?.transcendenceStatus ?: "Universal Transcendence Active • Sovereign Governance Matrix",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = core?.controllerTelemetry ?: "Synchronizing 1,420 Realities across Physical, Digital, Virtual, AI & Future Dimensions. Autonomous coordination optimal.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.85f)
                    )
                }
            }
        }

        item {
            Text(
                text = "CORE RESPONSIBILITIES & REALITY COORDINATION",
                style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.primary
            )
        }

        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                TranscendenceMetricCard(
                    title = "Universal Coordination",
                    value = "${String.format("%.4f", core?.universalCoordinationRatePct ?: 99.994)}%",
                    subtitle = "Zero latency cross-system sync",
                    modifier = Modifier.weight(1f)
                )
                TranscendenceMetricCard(
                    title = "Reality Sync Score",
                    value = "${String.format("%.4f", core?.realitySyncScore ?: 99.998)}%",
                    subtitle = "Continuous 1,420 realm bridge",
                    modifier = Modifier.weight(1f)
                )
            }
        }

        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                TranscendenceMetricCard(
                    title = "Infinite Governance",
                    value = "${String.format("%.4f", core?.infiniteGovernancePct ?: 99.996)}%",
                    subtitle = "Autonomous sovereign control",
                    modifier = Modifier.weight(1f)
                )
                TranscendenceMetricCard(
                    title = "Evolution Multiplier",
                    value = "${String.format("%.1f", core?.crossSystemEvolutionMultiplier ?: 34.8)}x",
                    subtitle = "Exponential adaptation velocity",
                    modifier = Modifier.weight(1f)
                )
            }
        }

        item {
            Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text(
                        text = "TRANSCENDENCE VISION MANDATE",
                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "✦ Beyond Business  •  ✦ Beyond Markets  •  ✦ Beyond Economies  •  ✦ Beyond Industries  •  ✦ Beyond Intelligence",
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium),
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        item {
            Button(
                onClick = onRunCore,
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("run_transcendence_core_button"),
                enabled = !isOperating,
                shape = RoundedCornerShape(10.dp)
            ) {
                Icon(Icons.Default.PlayArrow, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Synchronize Transcendence Core Controller")
            }
        }
    }
}

// -------------------------------------------------------------
// MODULE 2: CROSS REALITY COMMERCE ENGINE
// -------------------------------------------------------------
@Composable
fun RealityCommerceView(
    items: List<RealityCommerceEntity>,
    onAnalyze: () -> Unit,
    onAddClick: () -> Unit,
    isOperating: Boolean
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Reality Commerce Engine",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )
                    Text(
                        text = "Physical • Digital • Virtual • AI • Future Markets",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.outline
                    )
                }
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedButton(onClick = onAddClick) {
                        Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Add Realm")
                    }
                    Button(onClick = onAnalyze, enabled = !isOperating) {
                        Text("Analyze Grid")
                    }
                }
            }
        }

        items(items) { item ->
            Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f))
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = item.marketRealm,
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.primary
                        )
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)
                        ) {
                            Text(
                                text = "Index: ${String.format("%.3f", item.realityCommerceIndex)}",
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Trade Volume: $${String.format("%.1f", item.tradeVolumeBillionUsd)}B", style = MaterialTheme.typography.bodySmall)
                        Text("Connected Nodes: ${item.connectedNodesCount}", style = MaterialTheme.typography.bodySmall)
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Friction Latency: ${item.crossRealityFrictionLatencyMs} ms", style = MaterialTheme.typography.bodySmall)
                        Text("Interoperability: ${String.format("%.2f", item.interoperabilityScore)}%", style = MaterialTheme.typography.bodySmall, color = Color(0xFF2E7D32))
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Status: ${item.realmStatus}",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.outline
                    )
                }
            }
        }
    }
}

// -------------------------------------------------------------
// MODULE 3: AUTONOMOUS ENTERPRISE CREATOR
// -------------------------------------------------------------
@Composable
fun EnterpriseCreatorView(
    enterprises: List<EnterpriseCreatorEntity>,
    onCreateClick: () -> Unit,
    onRefresh: () -> Unit,
    isOperating: Boolean
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Autonomous Enterprise Creator",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )
                    Text(
                        text = "Creates Companies, Brands, Products, Models & Revenue Systems",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.outline
                    )
                }
                Button(onClick = onCreateClick, modifier = Modifier.testTag("create_enterprise_button")) {
                    Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Create Entity")
                }
            }
        }

        items(enterprises) { ent ->
            Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.5f))
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = ent.entityName,
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = MaterialTheme.colorScheme.secondary
                        ) {
                            Text(
                                text = ent.createdEntityType,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSecondary)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Text("Model: ${ent.marketModel}", style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Medium)
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Autonomous Rev: $${String.format("%.1f", ent.autonomousRevenueProjectionMillionUsd)}M", style = MaterialTheme.typography.bodySmall, color = Color(0xFF2E7D32), fontWeight = FontWeight.Bold)
                        Text("Creation Score: ${String.format("%.2f", ent.enterpriseCreationScore)}", style = MaterialTheme.typography.bodySmall)
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Stage: ${ent.lifecycleStage}", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.outline)
                        Text("AI CEO: ${ent.autonomousCeoAgent}", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.primary)
                    }
                }
            }
        }
    }
}

// -------------------------------------------------------------
// MODULE 4: UNIVERSAL OPPORTUNITY UNIVERSE
// -------------------------------------------------------------
@Composable
fun OpportunityUniverseView(
    opportunities: List<TranscendenceOpportunityEntity>,
    onDiscover: () -> Unit,
    onAddClick: () -> Unit,
    isOperating: Boolean
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Universal Opportunity Universe",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )
                    Text(
                        text = "Emerging Markets • Future Industries • Untapped Demand • Innovation Spaces",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.outline
                    )
                }
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedButton(onClick = onAddClick) {
                        Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Add")
                    }
                    Button(onClick = onDiscover, enabled = !isOperating) {
                        Text("Discover")
                    }
                }
            }
        }

        items(opportunities) { opp ->
            Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.7f))
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = opp.opportunityTitle,
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.weight(1f)
                        )
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = MaterialTheme.colorScheme.primaryContainer
                        ) {
                            Text(
                                text = opp.spaceCategory,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onPrimaryContainer)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = opp.strategicRoadmap,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Cosmic Value: $${String.format("%.1f", opp.addressableCosmicValueMillionUsd)}M", style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Bold, color = Color(0xFF2E7D32))
                        Text("Probability: ${opp.captureProbabilityPct}%", style = MaterialTheme.typography.bodySmall)
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Horizon: ${opp.expansionHorizonMonths} Mo", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.outline)
                        Text("Status: ${opp.executionStage}", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.primary)
                    }
                }
            }
        }
    }
}

// -------------------------------------------------------------
// MODULE 5: TRANSCENDENT DEMAND NETWORK
// -------------------------------------------------------------
@Composable
fun DemandNetworkView(
    demandList: List<DemandNetworkEntity>,
    onForecast: () -> Unit,
    onAddClick: () -> Unit,
    isOperating: Boolean
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Transcendent Demand Network",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )
                    Text(
                        text = "Micro Demand • Macro Demand • Global Demand • Future Demand",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.outline
                    )
                }
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedButton(onClick = onAddClick) {
                        Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Forecast")
                    }
                    Button(onClick = onForecast, enabled = !isOperating) {
                        Text("Compute")
                    }
                }
            }
        }

        items(demandList) { demand ->
            Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.tertiaryContainer.copy(alpha = 0.4f))
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = demand.productOrSector,
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.onTertiaryContainer,
                            modifier = Modifier.weight(1f)
                        )
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = MaterialTheme.colorScheme.tertiary
                        ) {
                            Text(
                                text = demand.demandTier,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onTertiary)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = demand.demandCatalystSummary,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onTertiaryContainer.copy(alpha = 0.85f)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Projected Units: ${demand.forecastUnitsDemand}", style = MaterialTheme.typography.bodySmall)
                        Text("Gross Rev: $${String.format("%.1f", demand.projectedGrossRevenueMillionUsd)}M", style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Bold, color = Color(0xFF2E7D32))
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Resonance: ${demand.demandResonanceMultiplier}x", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.outline)
                        Text("Confidence: ${String.format("%.1f", demand.predictiveConfidencePct)}%", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.primary)
                    }
                }
            }
        }
    }
}

// -------------------------------------------------------------
// MODULE 6: AUTONOMOUS CAPITAL CIVILIZATION
// -------------------------------------------------------------
@Composable
fun CapitalCivilizationView(
    capitalList: List<CapitalCivilizationEntity>,
    onManage: () -> Unit,
    onAddClick: () -> Unit,
    isOperating: Boolean
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Autonomous Capital Civilization",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )
                    Text(
                        text = "Investments • Assets • Expansion • Innovation Funds",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.outline
                    )
                }
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedButton(onClick = onAddClick) {
                        Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Allocate")
                    }
                    Button(onClick = onManage, enabled = !isOperating) {
                        Text("Manage")
                    }
                }
            }
        }

        items(capitalList) { cap ->
            Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f))
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = cap.fundCategory,
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.primary
                        )
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = Color(0xFF2E7D32).copy(alpha = 0.15f)
                        ) {
                            Text(
                                text = "Yield: +${String.format("%.1f", cap.annualizedGrowthYieldPct)}%",
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, color = Color(0xFF2E7D32))
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Text("Policy: ${cap.autonomousGovernancePolicy}", style = MaterialTheme.typography.bodySmall)
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Managed: $${String.format("%.1f", cap.totalCapitalManagedMillionUsd)}M", style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Bold)
                        Text("Deployed: $${String.format("%.1f", cap.allocatedCapitalMillionUsd)}M", style = MaterialTheme.typography.bodySmall)
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Civilization Index: ${String.format("%.3f", cap.capitalCivilizationIndex)}", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.outline)
                        Text("Reserve Status: ${cap.liquidityReserveStatus}", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.primary)
                    }
                }
            }
        }
    }
}

// -------------------------------------------------------------
// MODULE 7: TRANSCENDENCE DIGITAL REALITY
// -------------------------------------------------------------
@Composable
fun DigitalRealityView(
    twins: List<TranscendenceRealityTwinEntity>,
    onRefresh: () -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Transcendence Digital Reality",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )
                    Text(
                        text = "Real-Time Physical & Virtual Cross-Reality Synchronization",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.outline
                    )
                }
                Button(onClick = onRefresh) {
                    Text("Sync Mesh")
                }
            }
        }

        items(twins) { twin ->
            Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = twin.twinName,
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.primary
                        )
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = MaterialTheme.colorScheme.primaryContainer
                        ) {
                            Text(
                                text = "${String.format("%.2f", twin.fidelityLevelPct)}% Fidelity",
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onPrimaryContainer)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Text("Domain: ${twin.twinType}", style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Medium)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("Ticks: ${twin.simulationTicksPerSec}/sec", style = MaterialTheme.typography.bodySmall, fontFamily = FontFamily.Monospace)
                    Spacer(modifier = Modifier.height(6.dp))
                    Text("Insight: ${twin.simulationHypothesisResult}", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
        }
    }
}

// -------------------------------------------------------------
// MODULE 8: UNIVERSAL DECISION COSMOS
// -------------------------------------------------------------
@Composable
fun DecisionCosmosView(
    decisions: List<DecisionCosmosEntity>,
    onExecute: () -> Unit,
    isOperating: Boolean
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Universal Decision Cosmos",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )
                    Text(
                        text = "Growth • Investment • Innovation • Expansion Decisions",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.outline
                    )
                }
                Button(onClick = onExecute, enabled = !isOperating) {
                    Text("Execute Autonomous")
                }
            }
        }

        items(decisions) { dec ->
            Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f))
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = dec.title,
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.weight(1f)
                        )
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = MaterialTheme.colorScheme.primaryContainer
                        ) {
                            Text(
                                text = dec.decisionType,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onPrimaryContainer)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Text("Impact: ${dec.impactScope}", style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Medium)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("Telemetry: ${dec.telemetryOutcome}", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Spacer(modifier = Modifier.height(6.dp))
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Confidence: ${String.format("%.2f", dec.autonomousExecutionConfidencePct)}%", style = MaterialTheme.typography.labelSmall, color = Color(0xFF2E7D32))
                        Text("Status: ${dec.executionStatus}", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.outline)
                    }
                }
            }
        }
    }
}

// -------------------------------------------------------------
// MODULE 9: TRANSCENDENCE KNOWLEDGE OCEAN
// -------------------------------------------------------------
@Composable
fun KnowledgeOceanView(
    knowledgeList: List<KnowledgeOceanEntity>
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Column {
                Text(
                    text = "Transcendence Knowledge Ocean",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                )
                Text(
                    text = "Past Knowledge • Present Knowledge • Future Knowledge • Infinite Knowledge",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.outline
                )
            }
        }

        items(knowledgeList) { know ->
            Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.7f))
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = know.knowledgeCategory,
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.primary
                        )
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)
                        ) {
                            Text(
                                text = "Index: ${String.format("%.3f", know.knowledgeOceanIndex)}%",
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(know.knowledgeTopic, style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(know.deepInsightSummary, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Synthesized: ${know.synthesizedExabytes} EB", style = MaterialTheme.typography.labelSmall, fontFamily = FontFamily.Monospace)
                        Text("Truth Confidence: ${String.format("%.2f", know.truthConfidencePct)}%", style = MaterialTheme.typography.labelSmall, color = Color(0xFF2E7D32))
                    }
                }
            }
        }
    }
}

// -------------------------------------------------------------
// MODULE 10: UNIVERSAL EVOLUTION ENGINE
// -------------------------------------------------------------
@Composable
fun EvolutionEngineView(
    evolutionList: List<TranscendenceEvolutionEntity>,
    onEvolve: () -> Unit,
    isOperating: Boolean
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Universal Evolution Engine",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )
                    Text(
                        text = "Evolves Businesses, Industries, Markets, Economies & Civilizations",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.outline
                    )
                }
                Button(onClick = onEvolve, enabled = !isOperating) {
                    Text("Accelerate")
                }
            }
        }

        items(evolutionList) { evo ->
            Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.5f))
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = evo.targetDimension,
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = MaterialTheme.colorScheme.secondary
                        ) {
                            Text(
                                text = evo.evolutionaryStatus,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSecondary)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Text("Entity: ${evo.entityEvolving}", style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Medium)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("Paradigm: ${evo.emergentParadigm}", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.85f))
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Adaptation Velocity: ${String.format("%.2f", evo.adaptationVelocityPct)}%", style = MaterialTheme.typography.labelSmall, color = Color(0xFF2E7D32))
                        Text("Intelligence Index: ${String.format("%.3f", evo.evolutionIntelligenceIndex)}", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.outline)
                    }
                }
            }
        }
    }
}

// -------------------------------------------------------------
// MODULE 11: TRANSCENDENCE INNOVATION MATRIX
// -------------------------------------------------------------
@Composable
fun InnovationMatrixView(
    innovations: List<TranscendenceInnovationEntity>
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Column {
                Text(
                    text = "Transcendence Innovation Matrix",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                )
                Text(
                    text = "Materials Science • Neural Manufacturing • Cross-Reality Rails",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.outline
                )
            }
        }

        items(innovations) { inn ->
            Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = inn.title,
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.weight(1f)
                        )
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = MaterialTheme.colorScheme.primaryContainer
                        ) {
                            Text(
                                text = inn.innovationCategory,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onPrimaryContainer)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Text("Ref: ${inn.patentOrCodeReference}", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Matrix Score: ${String.format("%.2f", inn.innovationMatrixScore)}", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.primary)
                        Text("Yield: $${String.format("%.1f", inn.commercialYieldPotentialMillionUsd)}M", style = MaterialTheme.typography.labelSmall, color = Color(0xFF2E7D32))
                    }
                }
            }
        }
    }
}

// -------------------------------------------------------------
// MODULE 12: UNIVERSAL RISK INTELLIGENCE
// -------------------------------------------------------------
@Composable
fun RiskIntelligenceView(
    risks: List<TranscendenceRiskEntity>
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Column {
                Text(
                    text = "Universal Risk Intelligence",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                )
                Text(
                    text = "Microsecond Threat Neutralization & Sovereign Resilience",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.outline
                )
            }
        }

        items(risks) { risk ->
            Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f))
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = risk.protectionDomain,
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.error
                        )
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = Color(0xFF2E7D32).copy(alpha = 0.15f)
                        ) {
                            Text(
                                text = "Index: ${String.format("%.3f", risk.riskIntelligenceIndex)}%",
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, color = Color(0xFF2E7D32))
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Text("Vector: ${risk.threatVector}", style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Medium)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("Mitigation: ${risk.mitigationProtocol}", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Containment Eff: ${risk.containmentEfficiencyPct}%",
                        style = MaterialTheme.typography.labelSmall,
                        fontFamily = FontFamily.Monospace,
                        color = MaterialTheme.colorScheme.outline
                    )
                }
            }
        }
    }
}

// -------------------------------------------------------------
// MODULE 13: TRANSCENDENCE HEALTH MATRIX
// -------------------------------------------------------------
@Composable
fun HealthMatrixView(
    healthList: List<TranscendenceHealthEntity>,
    transcendenceIndex: Double,
    onCalculate: () -> Unit,
    isOperating: Boolean
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "OVERALL TRANSCENDENCE HEALTH INDEX",
                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = String.format("%.5f / 100", transcendenceIndex),
                        style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Black),
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Universal Equilibrium • Zero Degradation Across All Dimensions",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.85f)
                    )
                }
            }
        }

        items(healthList) { health ->
            Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = health.dimensionName,
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.primary
                        )
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = Color(0xFF2E7D32).copy(alpha = 0.15f)
                        ) {
                            Text(
                                text = health.status,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, color = Color(0xFF2E7D32))
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(health.diagnosticAnalysis, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Score: ${String.format("%.3f", health.healthScore)}%", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold)
                        Text("Index: ${String.format("%.3f", health.transcendenceHealthIndex)}", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.outline)
                    }
                }
            }
        }

        item {
            Button(
                onClick = onCalculate,
                modifier = Modifier.fillMaxWidth(),
                enabled = !isOperating,
                shape = RoundedCornerShape(10.dp)
            ) {
                Text("Re-Evaluate Transcendence Health Matrix")
            }
        }
    }
}

// -------------------------------------------------------------
// MODULE 14: TRANSCENDENCE COMMAND TOWER
// -------------------------------------------------------------
@Composable
fun CommandTowerView(
    core: TranscendenceCoreEntity?,
    telemetryStream: List<String>,
    onTriggerDirective: () -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Transcendence Command Tower",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Sovereign Executive Hub for Cross-Reality Directives & Telemetry",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.outline
                    )
                }
            }
        }

        item {
            Text(
                text = "LIVE TELEMETRY STREAM",
                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.primary
            )
        }

        items(telemetryStream) { log ->
            Surface(
                shape = RoundedCornerShape(8.dp),
                color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = log,
                    modifier = Modifier.padding(10.dp),
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontFamily = FontFamily.Monospace,
                        fontSize = 11.sp
                    )
                )
            }
        }

        item {
            Button(
                onClick = onTriggerDirective,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp)
            ) {
                Icon(Icons.Default.Send, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Broadcast Planetary Strategic Directive")
            }
        }
    }
}

// -------------------------------------------------------------
// MODULE 15: UNIVERSAL EXPANSION ENGINE
// -------------------------------------------------------------
@Composable
fun ExpansionEngineView(
    expansions: List<TranscendenceExpansionEntity>,
    onAccelerate: () -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Universal Expansion Engine",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )
                    Text(
                        text = "Global Sovereign Trade Treaties & Autonomous Expansion",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.outline
                    )
                }
                Button(onClick = onAccelerate) {
                    Text("Expand")
                }
            }
        }

        items(expansions) { exp ->
            Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = exp.expansionDomain,
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.primary
                        )
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = MaterialTheme.colorScheme.primaryContainer
                        ) {
                            Text(
                                text = exp.expansionState,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onPrimaryContainer)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Text("Vector: ${exp.targetTerritoryOrVector}", style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Medium)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("Synergy: ${exp.synergyMultiplier}x", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Expansion Score: ${String.format("%.2f", exp.universalExpansionScore)}", style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Bold, color = Color(0xFF2E7D32))
                        Text("Velocity: ${String.format("%.2f", exp.expansionVelocityPct)}%", style = MaterialTheme.typography.bodySmall)
                    }
                }
            }
        }
    }
}

// -------------------------------------------------------------
// HELPER COMPOSABLES & DIALOGS
// -------------------------------------------------------------

@Composable
fun TranscendenceHeaderMetric(
    label: String,
    value: String,
    color: Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = value,
                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Black, color = color)
            )
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall.copy(fontSize = 9.sp),
                color = MaterialTheme.colorScheme.outline
            )
        }
    }
}

@Composable
fun TranscendenceMetricCard(
    title: String,
    value: String,
    subtitle: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(
                text = title,
                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = value,
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Black)
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = subtitle,
                style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
                color = MaterialTheme.colorScheme.outline
            )
        }
    }
}

@Composable
fun CreateEnterpriseDialog(
    onDismiss: () -> Unit,
    onCreate: (type: String, name: String, model: String, projection: Double, ceo: String) -> Unit
) {
    var entityType by remember { mutableStateOf("Company") }
    var name by remember { mutableStateOf("") }
    var model by remember { mutableStateOf("") }
    var projectionStr by remember { mutableStateOf("500.0") }
    var ceo by remember { mutableStateOf("Autonomous-CEO-AI") }

    val types = listOf("Company", "Brand", "Product", "Market Model", "Revenue System")

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Create Autonomous Enterprise") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text("Entity Type:", style = MaterialTheme.typography.labelMedium)
                LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    items(types) { t ->
                        FilterChip(
                            selected = entityType == t,
                            onClick = { entityType = t },
                            label = { Text(t, style = MaterialTheme.typography.labelSmall) }
                        )
                    }
                }
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Entity Name") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = model,
                    onValueChange = { model = it },
                    label = { Text("Market Model / Architecture") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = projectionStr,
                    onValueChange = { projectionStr = it },
                    label = { Text("Revenue Projection ($ Million USD)") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = ceo,
                    onValueChange = { ceo = it },
                    label = { Text("Autonomous CEO / Agent Name") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val proj = projectionStr.toDoubleOrNull() ?: 500.0
                    onCreate(entityType, name.ifBlank { "Autonomous Enterprise" }, model.ifBlank { "Decentralized Mesh" }, proj, ceo)
                }
            ) {
                Text("Create Entity")
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
    onAdd: (category: String, title: String, value: Double, horizon: Int, roadmap: String) -> Unit
) {
    var category by remember { mutableStateOf("Emerging Markets") }
    var title by remember { mutableStateOf("") }
    var valueStr by remember { mutableStateOf("2500.0") }
    var horizonStr by remember { mutableStateOf("12") }
    var roadmap by remember { mutableStateOf("") }

    val categories = listOf("Emerging Markets", "Future Industries", "Untapped Demand", "Innovation Spaces")

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Register Cosmic Opportunity") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text("Space Category:", style = MaterialTheme.typography.labelMedium)
                LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    items(categories) { cat ->
                        FilterChip(
                            selected = category == cat,
                            onClick = { category = cat },
                            label = { Text(cat, style = MaterialTheme.typography.labelSmall) }
                        )
                    }
                }
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Opportunity Title") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = valueStr,
                    onValueChange = { valueStr = it },
                    label = { Text("Addressable Value ($ Million USD)") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = horizonStr,
                    onValueChange = { horizonStr = it },
                    label = { Text("Expansion Horizon (Months)") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = roadmap,
                    onValueChange = { roadmap = it },
                    label = { Text("Strategic Execution Roadmap") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val v = valueStr.toDoubleOrNull() ?: 2500.0
                    val h = horizonStr.toIntOrNull() ?: 12
                    onAdd(category, title.ifBlank { "New Cosmic Space" }, v, h, roadmap.ifBlank { "Autonomous Expansion Strategy" })
                }
            ) {
                Text("Register")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancel") }
        }
    )
}

@Composable
fun AddRealityCommerceDialog(
    onDismiss: () -> Unit,
    onAdd: (realm: String, nodes: Int, volume: Double, latency: Double) -> Unit
) {
    var realm by remember { mutableStateOf("Physical Markets") }
    var nodesStr by remember { mutableStateOf("50000") }
    var volumeStr by remember { mutableStateOf("150.0") }
    var latencyStr by remember { mutableStateOf("0.25") }

    val realms = listOf("Physical Markets", "Digital Markets", "Virtual Markets", "AI Markets", "Future Markets")

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Connect Reality Commerce Realm") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text("Market Realm:", style = MaterialTheme.typography.labelMedium)
                LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    items(realms) { r ->
                        FilterChip(
                            selected = realm == r,
                            onClick = { realm = r },
                            label = { Text(r, style = MaterialTheme.typography.labelSmall) }
                        )
                    }
                }
                OutlinedTextField(
                    value = nodesStr,
                    onValueChange = { nodesStr = it },
                    label = { Text("Connected Nodes Count") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = volumeStr,
                    onValueChange = { volumeStr = it },
                    label = { Text("Trade Volume ($ Billion USD)") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = latencyStr,
                    onValueChange = { latencyStr = it },
                    label = { Text("Cross-Reality Latency (ms)") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val n = nodesStr.toIntOrNull() ?: 50000
                    val v = volumeStr.toDoubleOrNull() ?: 150.0
                    val l = latencyStr.toDoubleOrNull() ?: 0.25
                    onAdd(realm, n, v, l)
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

@Composable
private fun AddDemandDialog(
    onDismiss: () -> Unit,
    onAdd: (tier: String, sector: String, units: Long, rev: Double, catalyst: String) -> Unit
) {
    var tier by remember { mutableStateOf("Micro Demand") }
    var sector by remember { mutableStateOf("") }
    var unitsStr by remember { mutableStateOf("1000000") }
    var revStr by remember { mutableStateOf("250.0") }
    var catalyst by remember { mutableStateOf("") }

    val tiers = listOf("Micro Demand", "Macro Demand", "Global Demand", "Future Demand")

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Forecast Demand Vector") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text("Demand Tier:", style = MaterialTheme.typography.labelMedium)
                LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    items(tiers) { t ->
                        FilterChip(
                            selected = tier == t,
                            onClick = { tier = t },
                            label = { Text(t, style = MaterialTheme.typography.labelSmall) }
                        )
                    }
                }
                OutlinedTextField(
                    value = sector,
                    onValueChange = { sector = it },
                    label = { Text("Product / Sector Name") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = unitsStr,
                    onValueChange = { unitsStr = it },
                    label = { Text("Forecast Units") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = revStr,
                    onValueChange = { revStr = it },
                    label = { Text("Gross Revenue ($ Million USD)") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = catalyst,
                    onValueChange = { catalyst = it },
                    label = { Text("Demand Catalyst Summary") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val u = unitsStr.toLongOrNull() ?: 1000000L
                    val r = revStr.toDoubleOrNull() ?: 250.0
                    onAdd(tier, sector.ifBlank { "Silk & Bio-Polymer Textiles" }, u, r, catalyst.ifBlank { "Dynamic Market Expansion" })
                }
            ) {
                Text("Forecast")
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
    onAdd: (category: String, total: Double, allocated: Double, yieldPct: Double, policy: String) -> Unit
) {
    var category by remember { mutableStateOf("Investments") }
    var totalStr by remember { mutableStateOf("10000.0") }
    var allocStr by remember { mutableStateOf("8500.0") }
    var yieldStr by remember { mutableStateOf("45.0") }
    var policy by remember { mutableStateOf("") }

    val categories = listOf("Investments", "Assets", "Expansion Capital", "Innovation Funds")

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Allocate Capital Civilization Fund") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text("Fund Category:", style = MaterialTheme.typography.labelMedium)
                LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    items(categories) { cat ->
                        FilterChip(
                            selected = category == cat,
                            onClick = { category = cat },
                            label = { Text(cat, style = MaterialTheme.typography.labelSmall) }
                        )
                    }
                }
                OutlinedTextField(
                    value = totalStr,
                    onValueChange = { totalStr = it },
                    label = { Text("Total Capital ($ Million USD)") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = allocStr,
                    onValueChange = { allocStr = it },
                    label = { Text("Allocated Capital ($ Million USD)") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = yieldStr,
                    onValueChange = { yieldStr = it },
                    label = { Text("Growth Yield (%)") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = policy,
                    onValueChange = { policy = it },
                    label = { Text("Autonomous Policy") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val tot = totalStr.toDoubleOrNull() ?: 10000.0
                    val al = allocStr.toDoubleOrNull() ?: 8500.0
                    val y = yieldStr.toDoubleOrNull() ?: 45.0
                    onAdd(category, tot, al, y, policy.ifBlank { "Autonomous Yield Routing" })
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
