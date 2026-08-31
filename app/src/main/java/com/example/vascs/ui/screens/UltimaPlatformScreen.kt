package com.example.vascs.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.vascs.data.model.*
import com.example.vascs.ui.viewmodel.UltimaViewModel

enum class UltimaTab(val title: String, val icon: ImageVector) {
    CORE("Ultima Core", Icons.Default.Hub),
    COMMERCE_CIVILIZATION("Commerce Civilization", Icons.Default.Public),
    WEALTH_UNIVERSE("Wealth Universe", Icons.Default.AccountBalance),
    FUTURE_OPPORTUNITIES("Future Opportunities", Icons.Default.AutoAwesome),
    DEMAND_UNIVERSE("Demand Universe", Icons.Default.Timeline),
    CAPITAL_AUTHORITY("Capital Authority", Icons.Default.MonetizationOn),
    TRADE_CIVILIZATION("Trade Civilization", Icons.Default.Language),
    REALITY_GRID("Reality Grid", Icons.Default.Layers),
    DECISION_AUTHORITY("Decision Authority", Icons.Default.Bolt),
    KNOWLEDGE_CIVILIZATION("Knowledge Civilization", Icons.Default.Psychology),
    INNOVATION_CIVILIZATION("Innovation Civilization", Icons.Default.Lightbulb),
    PROTECTION_GRID("Protection Grid", Icons.Default.Shield),
    HEALTH_CIVILIZATION("Health Civilization", Icons.Default.HealthAndSafety),
    COMMAND_TOWER("Command Tower", Icons.Default.CellTower),
    HARMONY_ENGINE("Harmony Engine", Icons.Default.AllInclusive)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UltimaPlatformScreen(
    viewModel: UltimaViewModel,
    onNavigateBack: () -> Unit
) {
    var selectedTab by remember { mutableStateOf(UltimaTab.CORE) }

    val ultimaCore by viewModel.ultimaCore.collectAsStateWithLifecycle()
    val commerceCivilization by viewModel.commerceCivilization.collectAsStateWithLifecycle()
    val wealthUniverse by viewModel.wealthUniverse.collectAsStateWithLifecycle()
    val futureOpportunities by viewModel.futureOpportunities.collectAsStateWithLifecycle()
    val demandUniverse by viewModel.demandUniverse.collectAsStateWithLifecycle()
    val capitalAuthority by viewModel.capitalAuthority.collectAsStateWithLifecycle()
    val tradeCivilization by viewModel.tradeCivilization.collectAsStateWithLifecycle()
    val realityGrid by viewModel.realityGrid.collectAsStateWithLifecycle()
    val decisionAuthority by viewModel.decisionAuthority.collectAsStateWithLifecycle()
    val knowledgeCivilization by viewModel.knowledgeCivilization.collectAsStateWithLifecycle()
    val innovationCivilization by viewModel.innovationCivilization.collectAsStateWithLifecycle()
    val protectionGrid by viewModel.protectionGrid.collectAsStateWithLifecycle()
    val healthCivilization by viewModel.healthCivilization.collectAsStateWithLifecycle()
    val ultimaTower by viewModel.ultimaTower.collectAsStateWithLifecycle()
    val universalHarmony by viewModel.universalHarmony.collectAsStateWithLifecycle()

    val ultimaIntelligenceIndex by viewModel.ultimaIntelligenceIndex.collectAsStateWithLifecycle()
    val isOperatingAutonomous by viewModel.isOperatingAutonomous.collectAsStateWithLifecycle()
    val statusMessage by viewModel.statusMessage.collectAsStateWithLifecycle()

    var showAddCommerceDialog by remember { mutableStateOf(false) }
    var showAddWealthDialog by remember { mutableStateOf(false) }
    var showAddOpportunityDialog by remember { mutableStateOf(false) }
    var showAddDemandDialog by remember { mutableStateOf(false) }
    var showAddCapitalDialog by remember { mutableStateOf(false) }
    var showAddTradeDialog by remember { mutableStateOf(false) }
    var showAddDecisionDialog by remember { mutableStateOf(false) }

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(statusMessage) {
        statusMessage?.let {
            snackbarHostState.showSnackbar(it)
            viewModel.clearStatusMessage()
        }
    }

    Scaffold(
        modifier = Modifier.testTag("ultima_platform_screen"),
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "VASCS ULTIMA",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Black,
                                    letterSpacing = 1.2.sp
                                )
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Surface(
                                shape = RoundedCornerShape(4.dp),
                                color = Color(0xFF6200EA)
                            ) {
                                Text(
                                    text = "CHECKPOINT 27.0",
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White
                                    )
                                )
                            }
                        }
                        Text(
                            text = "Final Universal Commerce Intelligence State",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                },
                navigationIcon = {
                    IconButton(
                        onClick = onNavigateBack,
                        modifier = Modifier.testTag("ultima_back_button")
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Navigate Back"
                        )
                    }
                },
                actions = {
                    FilledTonalButton(
                        onClick = { viewModel.triggerFullUltimaCycle() },
                        enabled = !isOperatingAutonomous,
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .testTag("ultima_sync_button"),
                        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
                    ) {
                        if (isOperatingAutonomous) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(16.dp),
                                strokeWidth = 2.dp,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Synchronizing...", fontSize = 12.sp)
                        } else {
                            Icon(
                                imageVector = Icons.Default.AllInclusive,
                                contentDescription = null,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Harmonize Ultima", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Header Banner with Ultima Vision
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.Transparent)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            Brush.horizontalGradient(
                                colors = listOf(
                                    Color(0xFF311B92),
                                    Color(0xFF6200EA),
                                    Color(0xFF00B0FF)
                                )
                            )
                        )
                        .padding(16.dp)
                ) {
                    Column {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = "ULTIMA VISION: MAXIMUM PROSPERITY & EVOLUTION",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        color = Color(0xFFFFD700),
                                        fontWeight = FontWeight.Black,
                                        letterSpacing = 1.sp
                                    )
                                )
                                Text(
                                    text = "One Unified Commerce Civilization",
                                    style = MaterialTheme.typography.titleLarge.copy(
                                        color = Color.White,
                                        fontWeight = FontWeight.Bold
                                    )
                                )
                            }
                            Surface(
                                shape = RoundedCornerShape(12.dp),
                                color = Color.White.copy(alpha = 0.2f),
                                border = BorderStroke(1.dp, Color.White.copy(alpha = 0.4f))
                            ) {
                                Column(
                                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        text = "ULTIMA INDEX",
                                        style = MaterialTheme.typography.labelSmall.copy(
                                            color = Color.White.copy(alpha = 0.8f),
                                            fontSize = 9.sp
                                        )
                                    )
                                    Text(
                                        text = String.format("%.1f", ultimaIntelligenceIndex),
                                        style = MaterialTheme.typography.titleMedium.copy(
                                            color = Color(0xFFFFD700),
                                            fontWeight = FontWeight.Black
                                        )
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            UltimaPillarTag("Maximum Intelligence")
                            UltimaPillarTag("Maximum Coordination")
                            UltimaPillarTag("Maximum Prosperity")
                            UltimaPillarTag("Maximum Autonomy")
                        }
                    }
                }
            }

            // Scrollable Module Tabs
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(UltimaTab.entries.toTypedArray()) { tab ->
                    val isSelected = selectedTab == tab
                    FilterChip(
                        selected = isSelected,
                        onClick = { selectedTab = tab },
                        label = {
                            Text(
                                text = tab.title,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                fontSize = 12.sp
                            )
                        },
                        leadingIcon = {
                            Icon(
                                imageVector = tab.icon,
                                contentDescription = null,
                                modifier = Modifier.size(16.dp)
                            )
                        },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = Color(0xFF6200EA),
                            selectedLabelColor = Color.White,
                            selectedLeadingIconColor = Color(0xFFFFD700)
                        ),
                        modifier = Modifier.testTag("ultima_tab_${tab.name.lowercase()}")
                    )
                }
            }

            HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp))

            // Main Tab Content
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
            ) {
                when (selectedTab) {
                    UltimaTab.CORE -> UltimaCoreTab(
                        core = ultimaCore,
                        onTriggerCycle = { viewModel.triggerFullUltimaCycle() }
                    )
                    UltimaTab.COMMERCE_CIVILIZATION -> CommerceCivilizationTab(
                        civilizations = commerceCivilization,
                        onAddClick = { showAddCommerceDialog = true }
                    )
                    UltimaTab.WEALTH_UNIVERSE -> WealthUniverseTab(
                        streams = wealthUniverse,
                        onAddClick = { showAddWealthDialog = true }
                    )
                    UltimaTab.FUTURE_OPPORTUNITIES -> FutureOpportunitiesTab(
                        opportunities = futureOpportunities,
                        onAddClick = { showAddOpportunityDialog = true }
                    )
                    UltimaTab.DEMAND_UNIVERSE -> DemandUniverseTab(
                        demands = demandUniverse,
                        onAddClick = { showAddDemandDialog = true }
                    )
                    UltimaTab.CAPITAL_AUTHORITY -> CapitalAuthorityTab(
                        capitals = capitalAuthority,
                        onAddClick = { showAddCapitalDialog = true }
                    )
                    UltimaTab.TRADE_CIVILIZATION -> TradeCivilizationTab(
                        routes = tradeCivilization,
                        onAddClick = { showAddTradeDialog = true }
                    )
                    UltimaTab.REALITY_GRID -> RealityGridTab(realities = realityGrid)
                    UltimaTab.DECISION_AUTHORITY -> DecisionAuthorityTab(
                        decisions = decisionAuthority,
                        onAddClick = { showAddDecisionDialog = true }
                    )
                    UltimaTab.KNOWLEDGE_CIVILIZATION -> KnowledgeCivilizationTab(knowledge = knowledgeCivilization)
                    UltimaTab.INNOVATION_CIVILIZATION -> InnovationCivilizationTab(innovations = innovationCivilization)
                    UltimaTab.PROTECTION_GRID -> ProtectionGridTab(protections = protectionGrid)
                    UltimaTab.HEALTH_CIVILIZATION -> HealthCivilizationTab(healths = healthCivilization)
                    UltimaTab.COMMAND_TOWER -> CommandTowerTab(towers = ultimaTower)
                    UltimaTab.HARMONY_ENGINE -> HarmonyEngineTab(
                        harmony = universalHarmony,
                        onHarmonizeClick = { viewModel.triggerFullUltimaCycle() }
                    )
                }
            }
        }
    }

    // Dialogs for Adding Items
    if (showAddCommerceDialog) {
        AddCommerceCivilizationDialog(
            onDismiss = { showAddCommerceDialog = false },
            onConfirm = {
                viewModel.addCommerceCivilization(it)
                showAddCommerceDialog = false
            }
        )
    }

    if (showAddWealthDialog) {
        AddWealthUniverseDialog(
            onDismiss = { showAddWealthDialog = false },
            onConfirm = {
                viewModel.addWealthUniverse(it)
                showAddWealthDialog = false
            }
        )
    }

    if (showAddOpportunityDialog) {
        AddFutureOpportunityDialog(
            onDismiss = { showAddOpportunityDialog = false },
            onConfirm = {
                viewModel.addFutureOpportunity(it)
                showAddOpportunityDialog = false
            }
        )
    }

    if (showAddDemandDialog) {
        AddDemandUniverseDialog(
            onDismiss = { showAddDemandDialog = false },
            onConfirm = {
                viewModel.addDemandUniverse(it)
                showAddDemandDialog = false
            }
        )
    }

    if (showAddCapitalDialog) {
        AddCapitalAuthorityDialog(
            onDismiss = { showAddCapitalDialog = false },
            onConfirm = {
                viewModel.addCapitalAuthority(it)
                showAddCapitalDialog = false
            }
        )
    }

    if (showAddTradeDialog) {
        AddTradeCivilizationDialog(
            onDismiss = { showAddTradeDialog = false },
            onConfirm = {
                viewModel.addTradeCivilization(it)
                showAddTradeDialog = false
            }
        )
    }

    if (showAddDecisionDialog) {
        AddDecisionAuthorityDialog(
            onDismiss = { showAddDecisionDialog = false },
            onConfirm = {
                viewModel.addDecisionAuthority(it)
                showAddDecisionDialog = false
            }
        )
    }
}

@Composable
fun UltimaPillarTag(text: String) {
    Surface(
        shape = RoundedCornerShape(6.dp),
        color = Color.White.copy(alpha = 0.15f)
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
            style = MaterialTheme.typography.labelSmall.copy(
                color = Color.White,
                fontSize = 10.sp,
                fontWeight = FontWeight.SemiBold
            )
        )
    }
}

// -----------------------------------------------------------------------------
// MODULE 1: ULTIMA CORE
// -----------------------------------------------------------------------------
@Composable
fun UltimaCoreTab(
    core: UltimaCoreEntity?,
    onTriggerCycle: () -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "MODULE 1: ULTIMA CORE",
                            style = MaterialTheme.typography.titleSmall.copy(
                                fontWeight = FontWeight.Black,
                                color = Color(0xFF6200EA)
                            )
                        )
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = Color(0xFF00C853)
                        ) {
                            Text(
                                text = "ACTIVE",
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = core?.ultimaStatus ?: "Ultima Universal Intelligence Controller Active",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = core?.universalControllerTelemetry ?: "Ultima Sovereign Unified Brain Operational - All 15 Commerce Civilizations Synchronized",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        MetricCard(
                            modifier = Modifier.weight(1f),
                            title = "Civilizations",
                            value = "${core?.civilizationsGovernedCount ?: 2500}",
                            color = Color(0xFF6200EA)
                        )
                        MetricCard(
                            modifier = Modifier.weight(1f),
                            title = "Universal Command",
                            value = "${core?.universalCommandRatePct ?: 100.0}%",
                            color = Color(0xFF00B0FF)
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        MetricCard(
                            modifier = Modifier.weight(1f),
                            title = "Infinite Coord",
                            value = "${core?.infiniteCoordinationScore ?: 100.0}/100",
                            color = Color(0xFFFF6D00)
                        )
                        MetricCard(
                            modifier = Modifier.weight(1f),
                            title = "Sync Rate",
                            value = "${core?.civilizationSyncRatePct ?: 100.0}%",
                            color = Color(0xFF00C853)
                        )
                    }
                }
            }
        }

        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "CORE RESPONSIBILITIES",
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    ResponsibilityRow("Universal Command", "Planetary multi-enterprise unified governance without central bottleneck.")
                    ResponsibilityRow("Infinite Coordination", "Instant sub-millisecond dispatch across all commerce, weavers, and logistics.")
                    ResponsibilityRow("Civilization Synchronization", "Harmonization of heritage weaving with futuristic digital commerce economies.")
                    ResponsibilityRow("Supreme Optimization", "Zero-friction value creation and compounding sovereign capital accumulation.")
                }
            }
        }

        item {
            Button(
                onClick = onTriggerCycle,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6200EA))
            ) {
                Icon(Icons.Default.Bolt, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Execute Supreme Ultima Intelligence Cycle", fontWeight = FontWeight.Bold)
            }
        }
    }
}

// -----------------------------------------------------------------------------
// MODULE 2: COMMERCE CIVILIZATION
// -----------------------------------------------------------------------------
@Composable
fun CommerceCivilizationTab(
    civilizations: List<CommerceCivilizationEntity>,
    onAddClick: () -> Unit
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
                        text = "UNIVERSAL COMMERCE CIVILIZATION",
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Black)
                    )
                    Text(
                        text = "Controls: Businesses, Markets, Industries, Trade, Economic Networks",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                IconButton(onClick = onAddClick) {
                    Icon(Icons.Default.Add, contentDescription = "Add Civilization Node")
                }
            }
        }

        items(civilizations) { item ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = item.controlDomain,
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                        )
                        Surface(
                            shape = RoundedCornerShape(4.dp),
                            color = Color(0xFF6200EA).copy(alpha = 0.15f)
                        ) {
                            Text(
                                text = "Score: ${item.civilizationIntelligenceIndex}",
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF6200EA)
                                )
                            )
                        }
                    }

                    Text(
                        text = item.systemName,
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                        color = MaterialTheme.colorScheme.primary
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "Doctrine: ${item.governingDoctrine}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Nodes: ${item.activeNodesCount}",
                            style = MaterialTheme.typography.labelSmall
                        )
                        Text(
                            text = "Autonomy: ${item.autonomyLevelPct}%",
                            style = MaterialTheme.typography.labelSmall.copy(color = Color(0xFF00C853))
                        )
                    }
                }
            }
        }
    }
}

// -----------------------------------------------------------------------------
// MODULE 3: WEALTH UNIVERSE
// -----------------------------------------------------------------------------
@Composable
fun WealthUniverseTab(
    streams: List<UltimaWealthUniverseEntity>,
    onAddClick: () -> Unit
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
                        text = "ULTIMA WEALTH UNIVERSE",
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Black)
                    )
                    Text(
                        text = "Pillars: Revenue, Profit, Assets, Capital, Economic Expansion",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                IconButton(onClick = onAddClick) {
                    Icon(Icons.Default.Add, contentDescription = "Add Wealth Stream")
                }
            }
        }

        items(streams) { stream ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = stream.wealthGeneratorPillar,
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                        )
                        Text(
                            text = "$${stream.generatedVolumeTrillionUsd}T",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Black,
                                color = Color(0xFF00C853)
                            )
                        )
                    }

                    Text(
                        text = stream.streamName,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Growth: +${stream.expansionGrowthRatePct}%",
                            style = MaterialTheme.typography.labelSmall.copy(color = Color(0xFF00B0FF))
                        )
                        Text(
                            text = "Multiplier: ${stream.velocityMultiplier}x",
                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold)
                        )
                    }
                }
            }
        }
    }
}

// -----------------------------------------------------------------------------
// MODULE 4: FUTURE OPPORTUNITIES
// -----------------------------------------------------------------------------
@Composable
fun FutureOpportunitiesTab(
    opportunities: List<FutureOpportunityEntity>,
    onAddClick: () -> Unit
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
                        text = "FUTURE OPPORTUNITY ENGINE",
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Black)
                    )
                    Text(
                        text = "Horizons: Future Markets, Industries, Technologies, Economies",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                IconButton(onClick = onAddClick) {
                    Icon(Icons.Default.Add, contentDescription = "Add Future Opportunity")
                }
            }
        }

        items(opportunities) { item ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            shape = RoundedCornerShape(4.dp),
                            color = Color(0xFFFF6D00).copy(alpha = 0.2f)
                        ) {
                            Text(
                                text = item.discoveryHorizon,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFFFF6D00)
                                )
                            )
                        }
                        Text(
                            text = "Value: $${item.projectedValueTrillionUsd}T",
                            style = MaterialTheme.typography.titleSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF00C853)
                            )
                        )
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = item.conceptTitle,
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "Genesis: in ${item.timeToGenesisDays} days | Certainty: ${item.realizationCertaintyPct}%",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

// -----------------------------------------------------------------------------
// MODULE 5: DEMAND UNIVERSE
// -----------------------------------------------------------------------------
@Composable
fun DemandUniverseTab(
    demands: List<UltimaDemandUniverseEntity>,
    onAddClick: () -> Unit
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
                        text = "ULTIMA DEMAND UNIVERSE",
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Black)
                    )
                    Text(
                        text = "Scopes: Local, National, Global, Future, Civilization Demand",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                IconButton(onClick = onAddClick) {
                    Icon(Icons.Default.Add, contentDescription = "Add Demand Scope")
                }
            }
        }

        items(demands) { demand ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = demand.forecastScope,
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                        )
                        Text(
                            text = "${demand.forecastedVolumeMillionUnits}M units",
                            style = MaterialTheme.typography.titleSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF6200EA)
                            )
                        )
                    }

                    Text(
                        text = demand.demandCluster,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Precision: ${demand.fulfillmentPrecisionPct}%",
                            style = MaterialTheme.typography.labelSmall.copy(color = Color(0xFF00C853))
                        )
                        Text(
                            text = "Latency: ${demand.predictiveLatencyMs}ms",
                            style = MaterialTheme.typography.labelSmall
                        )
                    }
                }
            }
        }
    }
}

// -----------------------------------------------------------------------------
// MODULE 6: CAPITAL AUTHORITY
// -----------------------------------------------------------------------------
@Composable
fun CapitalAuthorityTab(
    capitals: List<UltimaCapitalAuthorityEntity>,
    onAddClick: () -> Unit
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
                        text = "UNIVERSAL CAPITAL AUTHORITY",
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Black)
                    )
                    Text(
                        text = "Sectors: Investments, Assets, Expansion, Innovation, Civilization",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                IconButton(onClick = onAddClick) {
                    Icon(Icons.Default.Add, contentDescription = "Add Capital Fund")
                }
            }
        }

        items(capitals) { item ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = item.managementSector,
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                        )
                        Text(
                            text = "$${item.managedVolumeBillionUsd}B",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Black,
                                color = Color(0xFF00C853)
                            )
                        )
                    }

                    Text(
                        text = item.fundName,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Yield: +${item.annualizedYieldPct}%",
                            style = MaterialTheme.typography.labelSmall.copy(color = Color(0xFF00B0FF))
                        )
                        Text(
                            text = "Solvency: ${item.reserveSolvencyRatioPct}%",
                            style = MaterialTheme.typography.labelSmall.copy(color = Color(0xFF00C853))
                        )
                    }
                }
            }
        }
    }
}

// -----------------------------------------------------------------------------
// MODULE 7: TRADE CIVILIZATION
// -----------------------------------------------------------------------------
@Composable
fun TradeCivilizationTab(
    routes: List<TradeCivilizationEntity>,
    onAddClick: () -> Unit
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
                        text = "ULTIMA TRADE CIVILIZATION",
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Black)
                    )
                    Text(
                        text = "Corridors: Trade Routes, Supply Chains, Distribution, Commerce",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                IconButton(onClick = onAddClick) {
                    Icon(Icons.Default.Add, contentDescription = "Add Trade Route")
                }
            }
        }

        items(routes) { route ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = route.optimizationArea,
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                        )
                        Text(
                            text = "$${route.throughputBillionUsdPerMonth}B/mo",
                            style = MaterialTheme.typography.titleSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF00C853)
                            )
                        )
                    }

                    Text(
                        text = route.routeMeshName,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Score: ${route.tradeCivilizationScore}/100",
                            style = MaterialTheme.typography.labelSmall.copy(color = Color(0xFF6200EA))
                        )
                        Text(
                            text = "Clearance: ${route.seamlessClearanceRatePct}%",
                            style = MaterialTheme.typography.labelSmall.copy(color = Color(0xFF00C853))
                        )
                    }
                }
            }
        }
    }
}

// -----------------------------------------------------------------------------
// MODULE 8: REALITY GRID
// -----------------------------------------------------------------------------
@Composable
fun RealityGridTab(realities: List<UltimaRealityGridEntity>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Text(
                text = "UNIVERSAL REALITY GRID",
                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Black)
            )
            Text(
                text = "Dimensions: Business, Market, Economic, Civilization Realities",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        items(realities) { item ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text(
                        text = item.realityDimension,
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )
                    Text(
                        text = item.simulationName,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Fidelity: ${item.simulationFidelityPct}%",
                            style = MaterialTheme.typography.labelSmall.copy(color = Color(0xFF00C853))
                        )
                        Text(
                            text = "Compute: ${item.computeOpsPerSecMillion}M ops/s",
                            style = MaterialTheme.typography.labelSmall
                        )
                    }
                }
            }
        }
    }
}

// -----------------------------------------------------------------------------
// MODULE 9: DECISION AUTHORITY
// -----------------------------------------------------------------------------
@Composable
fun DecisionAuthorityTab(
    decisions: List<UltimaDecisionAuthorityEntity>,
    onAddClick: () -> Unit
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
                        text = "ULTIMA DECISION AUTHORITY",
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Black)
                    )
                    Text(
                        text = "Directives: Pricing, Expansion, Investments, Innovation, Resources",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                IconButton(onClick = onAddClick) {
                    Icon(Icons.Default.Add, contentDescription = "Add Decision Directive")
                }
            }
        }

        items(decisions) { item ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            shape = RoundedCornerShape(4.dp),
                            color = Color(0xFF00B0FF).copy(alpha = 0.2f)
                        ) {
                            Text(
                                text = item.executionType,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF0091EA)
                                )
                            )
                        }
                        Text(
                            text = "+$${item.economicImpactTrillionUsd}T Impact",
                            style = MaterialTheme.typography.titleSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF00C853)
                            )
                        )
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = item.policyTitle,
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Latency: ${item.executionLatencyMicrosec}µs",
                            style = MaterialTheme.typography.labelSmall
                        )
                        Text(
                            text = "Confidence: ${item.confidenceRatePct}%",
                            style = MaterialTheme.typography.labelSmall.copy(color = Color(0xFF00C853))
                        )
                    }
                }
            }
        }
    }
}

// -----------------------------------------------------------------------------
// MODULE 10: KNOWLEDGE CIVILIZATION
// -----------------------------------------------------------------------------
@Composable
fun KnowledgeCivilizationTab(knowledge: List<KnowledgeCivilizationEntity>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Text(
                text = "UNIVERSAL KNOWLEDGE CIVILIZATION",
                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Black)
            )
            Text(
                text = "Spheres: Past, Present, Future Knowledge, Universal Intelligence",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        items(knowledge) { item ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text(
                        text = item.temporalSphere,
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )
                    Text(
                        text = item.corpusDomain,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = item.executiveWisdomSynthesis,
                        style = MaterialTheme.typography.bodySmall.copy(fontStyle = androidx.compose.ui.text.font.FontStyle.Italic)
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Volume: ${item.synthesizedDataVolumeYb} YB",
                            style = MaterialTheme.typography.labelSmall
                        )
                        Text(
                            text = "Integrity: ${item.synthesisIntegrityPct}%",
                            style = MaterialTheme.typography.labelSmall.copy(color = Color(0xFF00C853))
                        )
                    }
                }
            }
        }
    }
}

// -----------------------------------------------------------------------------
// MODULE 11: INNOVATION CIVILIZATION
// -----------------------------------------------------------------------------
@Composable
fun InnovationCivilizationTab(innovations: List<InnovationCivilizationEntity>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Text(
                text = "ULTIMA INNOVATION CIVILIZATION",
                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Black)
            )
            Text(
                text = "Breakthroughs: Products, Technologies, Patents, Economic Systems",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        items(innovations) { item ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            shape = RoundedCornerShape(4.dp),
                            color = Color(0xFFFFD700).copy(alpha = 0.2f)
                        ) {
                            Text(
                                text = item.creationCategory,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFFB8860B)
                                )
                            )
                        }
                        Text(
                            text = "${item.commercialVelocityMultiplier}x Velocity",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF00C853)
                            )
                        )
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = item.breakthroughTitle,
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "ID: ${item.registryIdentifier} | Impact: ${item.universalImpactFactor}/100",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

// -----------------------------------------------------------------------------
// MODULE 12: PROTECTION GRID
// -----------------------------------------------------------------------------
@Composable
fun ProtectionGridTab(protections: List<ProtectionGridEntity>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Text(
                text = "UNIVERSAL PROTECTION GRID",
                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Black)
            )
            Text(
                text = "Shields: Markets, Capital, Trade, Innovation, Expansion",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        items(protections) { item ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = item.protectedFrontier,
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                        )
                        Surface(
                            shape = RoundedCornerShape(4.dp),
                            color = Color(0xFF00C853).copy(alpha = 0.2f)
                        ) {
                            Text(
                                text = "100% SECURE",
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF00C853)
                                )
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "Neutralizes: ${item.threatVectorNullified}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "Protocol: ${item.defenseProtocol}",
                        style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.SemiBold)
                    )
                }
            }
        }
    }
}

// -----------------------------------------------------------------------------
// MODULE 13: HEALTH CIVILIZATION
// -----------------------------------------------------------------------------
@Composable
fun HealthCivilizationTab(healths: List<HealthCivilizationEntity>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Text(
                text = "ULTIMA HEALTH CIVILIZATION",
                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Black)
            )
            Text(
                text = "Domains: Business, Market, Trade, Economic, Civilization Health",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        items(healths) { item ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = item.diagnosticDomain,
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                        )
                        Text(
                            text = "Vitality: ${item.vitalityScore}%",
                            style = MaterialTheme.typography.titleSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF00C853)
                            )
                        )
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = item.diagnosticSynthesis,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "State: ${item.systemicEquilibriumState}",
                        style = MaterialTheme.typography.labelSmall.copy(color = Color(0xFF6200EA))
                    )
                }
            }
        }
    }
}

// -----------------------------------------------------------------------------
// MODULE 14: COMMAND TOWER
// -----------------------------------------------------------------------------
@Composable
fun CommandTowerTab(towers: List<UltimaTowerEntity>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Text(
                text = "ULTIMA COMMAND TOWER",
                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Black)
            )
            Text(
                text = "Sectors: Economies, Industries, Markets, Trade Networks, Innovation, AI",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        items(towers) { tower ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = tower.monitoredSector,
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                        )
                        Surface(
                            shape = RoundedCornerShape(4.dp),
                            color = Color(0xFF6200EA).copy(alpha = 0.2f)
                        ) {
                            Text(
                                text = tower.towerDesignation,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF6200EA)
                                )
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Channels: ${tower.activeChannelsCount}",
                            style = MaterialTheme.typography.labelSmall
                        )
                        Text(
                            text = "QPS: ${tower.throughputQPS / 1000000000L}B/s",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF00B0FF)
                            )
                        )
                    }
                }
            }
        }
    }
}

// -----------------------------------------------------------------------------
// MODULE 15: UNIVERSAL HARMONY ENGINE
// -----------------------------------------------------------------------------
@Composable
fun HarmonyEngineTab(
    harmony: List<UniversalHarmonyEngineEntity>,
    onHarmonizeClick: () -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "UNIVERSAL HARMONY ENGINE",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Black,
                            color = Color(0xFF6200EA)
                        )
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "The ultimate state of VASCS: every business, market, industry, economy, and intelligence system operates under a unified autonomous commerce civilization.",
                        style = MaterialTheme.typography.bodyMedium
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    Button(
                        onClick = onHarmonizeClick,
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6200EA))
                    ) {
                        Icon(Icons.Default.AllInclusive, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Harmonize Universal Civilization", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        items(harmony) { item ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = item.synchronizationTarget,
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                        )
                        Surface(
                            shape = RoundedCornerShape(4.dp),
                            color = Color(0xFF00C853).copy(alpha = 0.2f)
                        ) {
                            Text(
                                text = "Index: ${item.universalHarmonyIndex}",
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF00C853)
                                )
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = item.convergenceVector,
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                        color = MaterialTheme.colorScheme.primary
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = item.harmonyBlueprint,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

// -----------------------------------------------------------------------------
// HELPER COMPONENTS & DIALOGS
// -----------------------------------------------------------------------------
@Composable
fun MetricCard(
    modifier: Modifier = Modifier,
    title: String,
    value: String,
    color: Color
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = color.copy(alpha = 0.1f))
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.labelSmall.copy(
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontSize = 11.sp
                )
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = value,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Black,
                    color = color
                )
            )
        }
    }
}

@Composable
fun ResponsibilityRow(title: String, description: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.Top
    ) {
        Icon(
            imageVector = Icons.Default.CheckCircle,
            contentDescription = null,
            tint = Color(0xFF00C853),
            modifier = Modifier.size(16.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
            )
            Text(
                text = description,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun AddCommerceCivilizationDialog(
    onDismiss: () -> Unit,
    onConfirm: (CommerceCivilizationEntity) -> Unit
) {
    var domain by remember { mutableStateOf("Global Markets") }
    var systemName by remember { mutableStateOf("Autonomous Trade Ecosystem") }
    var doctrine by remember { mutableStateOf("Universal Equilibrium") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add Commerce Civilization Domain") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = domain,
                    onValueChange = { domain = it },
                    label = { Text("Control Domain") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = systemName,
                    onValueChange = { systemName = it },
                    label = { Text("System Name") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = doctrine,
                    onValueChange = { doctrine = it },
                    label = { Text("Governing Doctrine") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    onConfirm(
                        CommerceCivilizationEntity(
                            controlDomain = domain,
                            systemName = systemName,
                            governingDoctrine = doctrine
                        )
                    )
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
fun AddWealthUniverseDialog(
    onDismiss: () -> Unit,
    onConfirm: (UltimaWealthUniverseEntity) -> Unit
) {
    var pillar by remember { mutableStateOf("Revenue") }
    var streamName by remember { mutableStateOf("Autonomous Global Export Stream") }
    var volume by remember { mutableStateOf("50.0") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add Wealth Universe Stream") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = pillar,
                    onValueChange = { pillar = it },
                    label = { Text("Wealth Pillar") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = streamName,
                    onValueChange = { streamName = it },
                    label = { Text("Stream Name") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = volume,
                    onValueChange = { volume = it },
                    label = { Text("Volume (Trillion USD)") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    onConfirm(
                        UltimaWealthUniverseEntity(
                            wealthGeneratorPillar = pillar,
                            streamName = streamName,
                            generatedVolumeTrillionUsd = volume.toDoubleOrNull() ?: 50.0
                        )
                    )
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
fun AddFutureOpportunityDialog(
    onDismiss: () -> Unit,
    onConfirm: (FutureOpportunityEntity) -> Unit
) {
    var horizon by remember { mutableStateOf("Future Markets") }
    var title by remember { mutableStateOf("Quantum Silk Weaving Network") }
    var value by remember { mutableStateOf("25.0") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add Future Opportunity") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = horizon,
                    onValueChange = { horizon = it },
                    label = { Text("Discovery Horizon") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Concept Title") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = value,
                    onValueChange = { value = it },
                    label = { Text("Value (Trillion USD)") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    onConfirm(
                        FutureOpportunityEntity(
                            discoveryHorizon = horizon,
                            conceptTitle = title,
                            projectedValueTrillionUsd = value.toDoubleOrNull() ?: 25.0
                        )
                    )
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
fun AddDemandUniverseDialog(
    onDismiss: () -> Unit,
    onConfirm: (UltimaDemandUniverseEntity) -> Unit
) {
    var scope by remember { mutableStateOf("Global Demand") }
    var cluster by remember { mutableStateOf("Worldwide Saree Luxury Connoisseurs") }
    var volume by remember { mutableStateOf("30.0") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add Demand Scope") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = scope,
                    onValueChange = { scope = it },
                    label = { Text("Forecast Scope") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = cluster,
                    onValueChange = { cluster = it },
                    label = { Text("Demand Cluster") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = volume,
                    onValueChange = { volume = it },
                    label = { Text("Forecast Volume (M units)") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    onConfirm(
                        UltimaDemandUniverseEntity(
                            forecastScope = scope,
                            demandCluster = cluster,
                            forecastedVolumeMillionUnits = volume.toDoubleOrNull() ?: 30.0
                        )
                    )
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
fun AddCapitalAuthorityDialog(
    onDismiss: () -> Unit,
    onConfirm: (UltimaCapitalAuthorityEntity) -> Unit
) {
    var sector by remember { mutableStateOf("Investments") }
    var fund by remember { mutableStateOf("Ultima Sovereign Liquidity Fund") }
    var volume by remember { mutableStateOf("150.0") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add Capital Authority Fund") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = sector,
                    onValueChange = { sector = it },
                    label = { Text("Management Sector") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = fund,
                    onValueChange = { fund = it },
                    label = { Text("Fund Name") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = volume,
                    onValueChange = { volume = it },
                    label = { Text("Managed Volume (Billion USD)") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    onConfirm(
                        UltimaCapitalAuthorityEntity(
                            managementSector = sector,
                            fundName = fund,
                            managedVolumeBillionUsd = volume.toDoubleOrNull() ?: 150.0
                        )
                    )
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
fun AddTradeCivilizationDialog(
    onDismiss: () -> Unit,
    onConfirm: (TradeCivilizationEntity) -> Unit
) {
    var area by remember { mutableStateOf("Trade Routes") }
    var meshName by remember { mutableStateOf("Direct Weaver-to-Global Express Corridor") }
    var throughput by remember { mutableStateOf("20.0") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add Trade Civilization Route") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = area,
                    onValueChange = { area = it },
                    label = { Text("Optimization Area") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = meshName,
                    onValueChange = { meshName = it },
                    label = { Text("Route Mesh Name") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = throughput,
                    onValueChange = { throughput = it },
                    label = { Text("Throughput (Billion USD/Month)") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    onConfirm(
                        TradeCivilizationEntity(
                            optimizationArea = area,
                            routeMeshName = meshName,
                            throughputBillionUsdPerMonth = throughput.toDoubleOrNull() ?: 20.0
                        )
                    )
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
fun AddDecisionAuthorityDialog(
    onDismiss: () -> Unit,
    onConfirm: (UltimaDecisionAuthorityEntity) -> Unit
) {
    var executionType by remember { mutableStateOf("Pricing") }
    var policyTitle by remember { mutableStateOf("Planetary Dynamic Saree Tariff Optimization") }
    var impact by remember { mutableStateOf("5.0") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add Decision Directive") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = executionType,
                    onValueChange = { executionType = it },
                    label = { Text("Execution Type") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = policyTitle,
                    onValueChange = { policyTitle = it },
                    label = { Text("Policy Title") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = impact,
                    onValueChange = { impact = it },
                    label = { Text("Impact (Trillion USD)") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    onConfirm(
                        UltimaDecisionAuthorityEntity(
                            executionType = executionType,
                            policyTitle = policyTitle,
                            economicImpactTrillionUsd = impact.toDoubleOrNull() ?: 5.0
                        )
                    )
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
