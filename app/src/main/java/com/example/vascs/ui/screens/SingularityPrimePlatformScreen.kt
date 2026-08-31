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
import com.example.vascs.viewmodel.SingularityPrimeViewModel

enum class SingularityPrimeTab(val title: String, val icon: ImageVector) {
    CORE("Singularity Prime Core", Icons.Default.Hub),
    CIVILIZATION("Civilization Engine", Icons.Default.Public),
    WEALTH("Wealth Generator", Icons.Default.AccountBalance),
    OPPORTUNITY("Opportunity Creator", Icons.Default.AutoAwesome),
    DEMAND("Demand Cosmos", Icons.Default.Analytics),
    CAPITAL("Capital Authority", Icons.Default.MonetizationOn),
    TRADE("Trade Supremacy", Icons.Default.AllInclusive),
    REALITY("Reality Engine", Icons.Default.Memory),
    DECISION("Decision Prime", Icons.Default.Bolt),
    KNOWLEDGE("Knowledge Prime", Icons.Default.Psychology),
    INNOVATION("Innovation Factory", Icons.Default.Lightbulb),
    RISK("Risk Shield Prime", Icons.Default.Shield),
    HEALTH("Health Prime", Icons.Default.HealthAndSafety),
    TOWER("Prime Tower", Icons.Default.Sensors),
    EVOLUTION("Evolution Authority", Icons.Default.RocketLaunch)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SingularityPrimePlatformScreen(
    viewModel: SingularityPrimeViewModel,
    onNavigateBack: () -> Unit
) {
    var selectedTab by remember { mutableStateOf(SingularityPrimeTab.CORE) }

    val primeCore by viewModel.primeCore.collectAsStateWithLifecycle()
    val civilizationEngine by viewModel.civilizationEngine.collectAsStateWithLifecycle()
    val wealthGenerator by viewModel.wealthGenerator.collectAsStateWithLifecycle()
    val opportunityCreator by viewModel.opportunityCreator.collectAsStateWithLifecycle()
    val demandCosmos by viewModel.demandCosmos.collectAsStateWithLifecycle()
    val capitalAuthority by viewModel.capitalAuthority.collectAsStateWithLifecycle()
    val tradeSupremacy by viewModel.tradeSupremacy.collectAsStateWithLifecycle()
    val realityEngine by viewModel.realityEngine.collectAsStateWithLifecycle()
    val decisionPrime by viewModel.decisionPrime.collectAsStateWithLifecycle()
    val knowledgePrime by viewModel.knowledgePrime.collectAsStateWithLifecycle()
    val innovationFactory by viewModel.innovationFactory.collectAsStateWithLifecycle()
    val riskShieldPrime by viewModel.riskShieldPrime.collectAsStateWithLifecycle()
    val healthPrime by viewModel.healthPrime.collectAsStateWithLifecycle()
    val primeCommandTower by viewModel.primeCommandTower.collectAsStateWithLifecycle()
    val evolutionAuthority by viewModel.evolutionAuthority.collectAsStateWithLifecycle()

    val singularityPrimeIndex by viewModel.singularityPrimeIndex.collectAsStateWithLifecycle()
    val isOperatingAutonomous by viewModel.isOperatingAutonomous.collectAsStateWithLifecycle()
    val statusMessage by viewModel.statusMessage.collectAsStateWithLifecycle()

    var showAddCivilizationDialog by remember { mutableStateOf(false) }
    var showAddWealthDialog by remember { mutableStateOf(false) }
    var showAddOpportunityDialog by remember { mutableStateOf(false) }
    var showAddDemandDialog by remember { mutableStateOf(false) }
    var showAddCapitalDialog by remember { mutableStateOf(false) }
    var showAddDecisionDialog by remember { mutableStateOf(false) }
    var showAddInnovationDialog by remember { mutableStateOf(false) }

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(statusMessage) {
        statusMessage?.let {
            snackbarHostState.showSnackbar(it)
            viewModel.clearStatusMessage()
        }
    }

    Scaffold(
        modifier = Modifier.testTag("singularity_prime_screen"),
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "VASCS SINGULARITY PRIME",
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
                                    text = "CHECKPOINT 25.0",
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White
                                    )
                                )
                            }
                        }
                        Text(
                            text = "Ultimate Autonomous Business Intelligence Core",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                },
                navigationIcon = {
                    IconButton(
                        onClick = onNavigateBack,
                        modifier = Modifier.testTag("singularity_prime_back_button")
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Navigate Back"
                        )
                    }
                },
                actions = {
                    FilledTonalButton(
                        onClick = { viewModel.triggerFullSingularityCycle() },
                        enabled = !isOperatingAutonomous,
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .testTag("singularity_prime_sync_button"),
                        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
                    ) {
                        if (isOperatingAutonomous) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(16.dp),
                                strokeWidth = 2.dp
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Synchronizing...", style = MaterialTheme.typography.labelMedium)
                        } else {
                            Icon(Icons.Default.Sync, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Prime Sync", style = MaterialTheme.typography.labelMedium)
                        }
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
            // Hero Singularity Status Banner
            SingularityPrimeHeroBanner(
                primeCore = primeCore,
                primeIndex = singularityPrimeIndex,
                isOperating = isOperatingAutonomous
            )

            // Scrollable Module Tabs
            ScrollableTabRow(
                selectedTabIndex = selectedTab.ordinal,
                edgePadding = 12.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("singularity_prime_tabs")
            ) {
                SingularityPrimeTab.values().forEach { tab ->
                    Tab(
                        selected = selectedTab == tab,
                        onClick = { selectedTab = tab },
                        text = {
                            Text(
                                text = tab.title,
                                fontWeight = if (selectedTab == tab) FontWeight.Bold else FontWeight.Normal,
                                maxLines = 1
                            )
                        },
                        icon = {
                            Icon(
                                imageVector = tab.icon,
                                contentDescription = tab.title,
                                modifier = Modifier.size(18.dp)
                            )
                        },
                        modifier = Modifier.testTag("tab_${tab.name.lowercase()}")
                    )
                }
            }

            // Tab Content
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
                    .background(MaterialTheme.colorScheme.surfaceContainerLowest)
            ) {
                when (selectedTab) {
                    SingularityPrimeTab.CORE -> SingularityPrimeCoreTab(primeCore = primeCore)
                    SingularityPrimeTab.CIVILIZATION -> CivilizationEngineTab(
                        items = civilizationEngine,
                        onAddClick = { showAddCivilizationDialog = true }
                    )
                    SingularityPrimeTab.WEALTH -> WealthGeneratorTab(
                        items = wealthGenerator,
                        onAddClick = { showAddWealthDialog = true }
                    )
                    SingularityPrimeTab.OPPORTUNITY -> OpportunityCreatorTab(
                        items = opportunityCreator,
                        onAddClick = { showAddOpportunityDialog = true }
                    )
                    SingularityPrimeTab.DEMAND -> DemandCosmosTab(
                        items = demandCosmos,
                        onAddClick = { showAddDemandDialog = true }
                    )
                    SingularityPrimeTab.CAPITAL -> CapitalAuthorityTab(
                        items = capitalAuthority,
                        onAddClick = { showAddCapitalDialog = true }
                    )
                    SingularityPrimeTab.TRADE -> TradeSupremacyTab(items = tradeSupremacy)
                    SingularityPrimeTab.REALITY -> RealityEngineTab(items = realityEngine)
                    SingularityPrimeTab.DECISION -> DecisionPrimeTab(
                        items = decisionPrime,
                        onAddClick = { showAddDecisionDialog = true }
                    )
                    SingularityPrimeTab.KNOWLEDGE -> KnowledgePrimeTab(items = knowledgePrime)
                    SingularityPrimeTab.INNOVATION -> InnovationFactoryTab(
                        items = innovationFactory,
                        onAddClick = { showAddInnovationDialog = true }
                    )
                    SingularityPrimeTab.RISK -> RiskShieldPrimeTab(items = riskShieldPrime)
                    SingularityPrimeTab.HEALTH -> HealthPrimeTab(items = healthPrime)
                    SingularityPrimeTab.TOWER -> PrimeCommandTowerTab(items = primeCommandTower)
                    SingularityPrimeTab.EVOLUTION -> EvolutionAuthorityTab(items = evolutionAuthority)
                }
            }
        }
    }

    // Dialogs
    if (showAddCivilizationDialog) {
        AddCivilizationDialog(
            onDismiss = { showAddCivilizationDialog = false },
            onConfirm = { domain: String, name: String, law: String, stability: Double, nodes: Long ->
                viewModel.addCivilizationUnit(domain, name, law, stability, nodes)
                showAddCivilizationDialog = false
            }
        )
    }

    if (showAddWealthDialog) {
        AddWealthDialog(
            onDismiss = { showAddWealthDialog = false },
            onConfirm = { pillar: String, streamName: String, volume: Double, growth: Double ->
                viewModel.addWealthStream(pillar, streamName, volume, growth)
                showAddWealthDialog = false
            }
        )
    }

    if (showAddOpportunityDialog) {
        AddOpportunityPrimeDialog(
            onDismiss = { showAddOpportunityDialog = false },
            onConfirm = { horizon: String, title: String, valTrill: Double, days: Int, strat: String ->
                viewModel.addOpportunity(horizon, title, valTrill, days, strat)
                showAddOpportunityDialog = false
            }
        )
    }

    if (showAddDemandDialog) {
        AddDemandDialog(
            onDismiss = { showAddDemandDialog = false },
            onConfirm = { scope: String, cluster: String, units: Double, action: String ->
                viewModel.addDemandCosmosNode(scope, cluster, units, action)
                showAddDemandDialog = false
            }
        )
    }

    if (showAddCapitalDialog) {
        AddCapitalFundDialog(
            onDismiss = { showAddCapitalDialog = false },
            onConfirm = { pillar: String, name: String, total: Double, yield: Double ->
                viewModel.addCapitalFund(pillar, name, total, yield)
                showAddCapitalDialog = false
            }
        )
    }

    if (showAddDecisionDialog) {
        AddDecisionDirectiveDialog(
            onDismiss = { showAddDecisionDialog = false },
            onConfirm = { domain: String, title: String, mag: Double, action: String ->
                viewModel.addDecisionDirective(domain, title, mag, action)
                showAddDecisionDialog = false
            }
        )
    }

    if (showAddInnovationDialog) {
        AddInnovationDialog(
            onDismiss = { showAddInnovationDialog = false },
            onConfirm = { cat: String, title: String, code: String, impact: Double ->
                viewModel.addInnovationAsset(cat, title, code, impact)
                showAddInnovationDialog = false
            }
        )
    }
}

@Composable
fun SingularityPrimeHeroBanner(
    primeCore: SingularityPrimeCoreEntity?,
    primeIndex: Double,
    isOperating: Boolean
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
            .testTag("singularity_prime_hero_card"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerHigh
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.horizontalGradient(
                        colors = listOf(
                            Color(0xFF311B92).copy(alpha = 0.25f),
                            Color(0xFF4A148C).copy(alpha = 0.15f),
                            Color(0xFF004D40).copy(alpha = 0.20f)
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
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .background(
                                    Brush.linearGradient(listOf(Color(0xFF7C4DFF), Color(0xFF651FFF))),
                                    CircleShape
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.AllInclusive,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(
                                text = "SINGULARITY PRIME INDEX",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    letterSpacing = 1.sp
                                ),
                                color = MaterialTheme.colorScheme.primary
                            )
                            Text(
                                text = "${String.format("%.3f", primeIndex)}%",
                                style = MaterialTheme.typography.headlineSmall.copy(
                                    fontWeight = FontWeight.Black,
                                    fontFamily = FontFamily.Monospace
                                )
                            )
                        }
                    }

                    Column(horizontalAlignment = Alignment.End) {
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = if (isOperating) Color(0xFFFF9800).copy(alpha = 0.2f) else Color(0xFF00C853).copy(alpha = 0.2f),
                            border = BorderStroke(1.dp, if (isOperating) Color(0xFFFF9800) else Color(0xFF00C853))
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(6.dp)
                                        .background(if (isOperating) Color(0xFFFF9800) else Color(0xFF00C853), CircleShape)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = if (isOperating) "RECURSIVE EVOLVING" else "PRIME SOVEREIGN",
                                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "${primeCore?.civilizationsGovernedCount ?: 840} Civilizations Governed",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Stats Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    SingularityStatPill(
                        label = "Intelligence",
                        value = "${String.format("%.2f", primeCore?.primeIntelligenceIndex ?: 100.0)}%",
                        icon = Icons.Default.Psychology
                    )
                    SingularityStatPill(
                        label = "Coordination",
                        value = "${String.format("%.2f", primeCore?.infiniteCoordinationRatePct ?: 100.0)}%",
                        icon = Icons.Default.Hub
                    )
                    SingularityStatPill(
                        label = "Sovereignty",
                        value = "${String.format("%.2f", primeCore?.economicSovereigntyScore ?: 100.0)}%",
                        icon = Icons.Default.Shield
                    )
                    SingularityStatPill(
                        label = "Self-Evolution",
                        value = "${String.format("%.1f", primeCore?.selfEvolutionVelocityIndex ?: 24.8)}x",
                        icon = Icons.Default.RocketLaunch
                    )
                }
            }
        }
    }
}

@Composable
fun SingularityStatPill(label: String, value: String, icon: ImageVector) {
    Surface(
        shape = RoundedCornerShape(8.dp),
        color = MaterialTheme.colorScheme.surface.copy(alpha = 0.7f),
        modifier = Modifier.padding(2.dp)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(12.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.width(4.dp))
            Column {
                Text(
                    text = label,
                    style = MaterialTheme.typography.labelSmall.copy(fontSize = 9.sp),
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = value,
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Monospace
                    )
                )
            }
        }
    }
}

// =========================================================================
// MODULE 1: SINGULARITY PRIME CORE
// =========================================================================
@Composable
fun SingularityPrimeCoreTab(primeCore: SingularityPrimeCoreEntity?) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .testTag("singularity_prime_core_tab"),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Text(
                text = "Module 1: Singularity Prime Core",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
            )
            Text(
                text = "Universal Intelligence, Economic Sovereignty, Infinite Coordination & Autonomous Governance",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        item {
            Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Prime Intelligence Controller",
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.primary
                        )
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = Color(0xFF6200EA).copy(alpha = 0.15f)
                        ) {
                            Text(
                                text = "CORE-STATUS-100",
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF6200EA)
                                )
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = primeCore?.primeControllerTelemetry
                            ?: "VASCS Singularity Prime Controller executing sovereign universal economic brain, infinite prosperity compounding, and autonomous self-evolution across 840 global, interstellar, and virtual trade civilizations.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Spacer(modifier = Modifier.height(14.dp))
                    HorizontalDivider()
                    Spacer(modifier = Modifier.height(14.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text("Operational Status", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.outline)
                            Text(primeCore?.primeStatus ?: "Ultimate Autonomous Intelligence Active", style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Bold)
                        }
                        Column(horizontalAlignment = Alignment.End) {
                            Text("Civilizations Governed", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.outline)
                            Text("${primeCore?.civilizationsGovernedCount ?: 840}", style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace)
                        }
                    }
                }
            }
        }

        item {
            Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Singularity Prime Architecture Metrics", style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold))
                    Spacer(modifier = Modifier.height(12.dp))

                    MetricLinearProgressRow("Universal Intelligence Fidelity", primeCore?.primeIntelligenceIndex ?: 100.0, Color(0xFF6200EA))
                    Spacer(modifier = Modifier.height(8.dp))
                    MetricLinearProgressRow("Infinite Coordination Rate", primeCore?.infiniteCoordinationRatePct ?: 100.0, Color(0xFF00B0FF))
                    Spacer(modifier = Modifier.height(8.dp))
                    MetricLinearProgressRow("Economic Sovereignty Score", primeCore?.economicSovereigntyScore ?: 100.0, Color(0xFF00E676))
                }
            }
        }
    }
}

// =========================================================================
// MODULE 2: AUTONOMOUS CIVILIZATION ENGINE
// =========================================================================
@Composable
fun CivilizationEngineTab(
    items: List<CivilizationEngineEntity>,
    onAddClick: () -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .testTag("civilization_engine_tab"),
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
                        text = "Module 2: Autonomous Civilization Engine",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )
                    Text(
                        text = "Controls: Markets, Industries, Trade Systems, Business Networks, Economic Ecosystems",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                IconButton(onClick = onAddClick, modifier = Modifier.testTag("add_civilization_button")) {
                    Icon(Icons.Default.AddCircle, contentDescription = "Add Civilization Domain", tint = MaterialTheme.colorScheme.primary)
                }
            }
        }

        items(items) { item ->
            Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = item.entityName,
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.primary
                        )
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = MaterialTheme.colorScheme.primaryContainer
                        ) {
                            Text(
                                text = item.domainDomain,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Text("Law: ${item.autonomousGovernanceLaw}", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Active Nodes: ${item.activeNodesCount}", style = MaterialTheme.typography.labelSmall, fontFamily = FontFamily.Monospace)
                        Text("Control Index: ${String.format("%.2f", item.civilizationControlIndex)}%", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold, color = Color(0xFF00C853))
                    }
                }
            }
        }
    }
}

// =========================================================================
// MODULE 3: UNIVERSAL WEALTH GENERATOR
// =========================================================================
@Composable
fun WealthGeneratorTab(
    items: List<WealthGeneratorEntity>,
    onAddClick: () -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .testTag("wealth_generator_tab"),
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
                        text = "Module 3: Universal Wealth Generator",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )
                    Text(
                        text = "Creates: Revenue, Profit, Assets, Investments, Expansion Capital",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                IconButton(onClick = onAddClick, modifier = Modifier.testTag("add_wealth_button")) {
                    Icon(Icons.Default.AddCircle, contentDescription = "Add Wealth Stream", tint = MaterialTheme.colorScheme.primary)
                }
            }
        }

        items(items) { item ->
            Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = item.wealthStreamName,
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.primary
                        )
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = Color(0xFF00E676).copy(alpha = 0.15f)
                        ) {
                            Text(
                                text = item.wealthPillar,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, color = Color(0xFF00897B))
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Volume: $${String.format("%.2f", item.currentVolumeTrillionUsd)}T", style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace))
                        Text("CAGR: +${String.format("%.1f", item.compoundGrowthRatePct)}%", style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold, color = Color(0xFF00C853)))
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("Distribution Efficiency: ${String.format("%.2f", item.distributionEfficiencyPct)}%", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.outline)
                }
            }
        }
    }
}

// =========================================================================
// MODULE 4: PRIME OPPORTUNITY CREATOR
// =========================================================================
@Composable
fun OpportunityCreatorTab(
    items: List<OpportunityCreatorEntity>,
    onAddClick: () -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .testTag("opportunity_creator_tab"),
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
                        text = "Module 4: Prime Opportunity Creator",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )
                    Text(
                        text = "Generates: New Industries, New Markets, New Business Models, Future Opportunities",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                IconButton(onClick = onAddClick, modifier = Modifier.testTag("add_opportunity_button")) {
                    Icon(Icons.Default.AddCircle, contentDescription = "Add Opportunity", tint = MaterialTheme.colorScheme.primary)
                }
            }
        }

        items(items) { item ->
            Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = item.conceptTitle,
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.weight(1f)
                        )
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = MaterialTheme.colorScheme.secondaryContainer
                        ) {
                            Text(
                                text = item.creationHorizon,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Text("Seeding Strategy: ${item.autonomousSeedingStrategy}", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Value: $${String.format("%.1f", item.projectedValueTrillionUsd)}T", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold)
                        Text("Genesis: in ${item.timeToGenesisDays} days", style = MaterialTheme.typography.labelSmall, fontFamily = FontFamily.Monospace)
                        Text("Success: ${String.format("%.1f", item.probabilityOfSuccessPct)}%", style = MaterialTheme.typography.labelSmall, color = Color(0xFF00C853))
                    }
                }
            }
        }
    }
}

// =========================================================================
// MODULE 5: AUTONOMOUS DEMAND COSMOS
// =========================================================================
@Composable
fun DemandCosmosTab(
    items: List<DemandCosmosEntity>,
    onAddClick: () -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .testTag("demand_cosmos_tab"),
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
                        text = "Module 5: Autonomous Demand Cosmos",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )
                    Text(
                        text = "Predicts: Local Demand, National Demand, Global Demand, Future Demand",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                IconButton(onClick = onAddClick, modifier = Modifier.testTag("add_demand_button")) {
                    Icon(Icons.Default.AddCircle, contentDescription = "Add Demand Node", tint = MaterialTheme.colorScheme.primary)
                }
            }
        }

        items(items) { item ->
            Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = item.marketCluster,
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.primary
                        )
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = MaterialTheme.colorScheme.tertiaryContainer
                        ) {
                            Text(
                                text = item.scopeLevel,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Text("Action: ${item.dynamicBalancingAction}", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Units: ${String.format("%.1f", item.predictedDemandUnitsMillion)}M", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace)
                        Text("Latency: ${item.fulfillmentVelocityMs}ms", style = MaterialTheme.typography.labelSmall, fontFamily = FontFamily.Monospace)
                        Text("Accuracy: ${String.format("%.2f", item.predictiveAccuracyPct)}%", style = MaterialTheme.typography.labelSmall, color = Color(0xFF00C853))
                    }
                }
            }
        }
    }
}

// =========================================================================
// MODULE 6: PRIME CAPITAL AUTHORITY
// =========================================================================
@Composable
fun CapitalAuthorityTab(
    items: List<CapitalAuthorityEntity>,
    onAddClick: () -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .testTag("capital_authority_tab"),
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
                        text = "Module 6: Prime Capital Authority",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )
                    Text(
                        text = "Allocates: Investments, Growth Funds, Innovation Funds, Expansion Budgets",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                IconButton(onClick = onAddClick, modifier = Modifier.testTag("add_capital_button")) {
                    Icon(Icons.Default.AddCircle, contentDescription = "Add Capital Fund", tint = MaterialTheme.colorScheme.primary)
                }
            }
        }

        items(items) { item ->
            Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = item.fundName,
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.primary
                        )
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = Color(0xFF3F51B5).copy(alpha = 0.15f)
                        ) {
                            Text(
                                text = item.allocationPillar,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, color = Color(0xFF3F51B5))
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("AUM: $${String.format("%.1f", item.totalUnderManagementBillionUsd)}B", style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace))
                        Text("Yield: ${String.format("%.1f", item.targetYieldRatePct)}%", style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold, color = Color(0xFF00C853)))
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("Status: ${item.deploymentStatus}", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.outline)
                }
            }
        }
    }
}

// =========================================================================
// MODULE 7: UNIVERSAL TRADE SUPREMACY
// =========================================================================
@Composable
fun TradeSupremacyTab(items: List<TradeSupremacyEntity>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .testTag("trade_supremacy_tab"),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Text(
                text = "Module 7: Universal Trade Supremacy",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
            )
            Text(
                text = "Optimizes: Trade Routes, Distribution, Supply Chains, Market Reach",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        items(items) { item ->
            Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = item.tradeMeshIdentifier,
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace),
                            color = MaterialTheme.colorScheme.primary
                        )
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = MaterialTheme.colorScheme.primaryContainer
                        ) {
                            Text(
                                text = item.optimizationVector,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Flow: $${String.format("%.1f", item.throughputBillionUsdPerMonth)}B/mo", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold)
                        Text("Latency: ${item.latencyMilliseconds}ms", style = MaterialTheme.typography.labelSmall, fontFamily = FontFamily.Monospace)
                        Text("Score: ${String.format("%.2f", item.tradeSupremacyScore)}", style = MaterialTheme.typography.labelSmall, color = Color(0xFF00C853))
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("Security: ${item.channelSecurityRating}", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.outline)
                }
            }
        }
    }
}

// =========================================================================
// MODULE 8: PRIME REALITY ENGINE
// =========================================================================
@Composable
fun RealityEngineTab(items: List<RealityEngineEntity>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .testTag("reality_engine_tab"),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Text(
                text = "Module 8: Prime Reality Engine",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
            )
            Text(
                text = "Builds: Economic Reality, Business Reality, Market Reality, Civilization Reality",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        items(items) { item ->
            Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = item.simulationMatrixName,
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.weight(1f)
                        )
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = MaterialTheme.colorScheme.secondaryContainer
                        ) {
                            Text(
                                text = item.realityLayer,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Text("Directive: ${item.predictiveSynthesisDirective}", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Res: ${String.format("%.1f", item.simulationResolutionPct)}%", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold)
                        Text("Ops: ${item.operationsPerMicrosecondMillion}M/µs", style = MaterialTheme.typography.labelSmall, fontFamily = FontFamily.Monospace)
                        Text("Coherence: ${String.format("%.2f", item.quantumCoherencePct)}%", style = MaterialTheme.typography.labelSmall, color = Color(0xFF00C853))
                    }
                }
            }
        }
    }
}

// =========================================================================
// MODULE 9: AUTONOMOUS DECISION PRIME
// =========================================================================
@Composable
fun DecisionPrimeTab(
    items: List<DecisionPrimeEntity>,
    onAddClick: () -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .testTag("decision_prime_tab"),
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
                        text = "Module 9: Autonomous Decision Prime",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )
                    Text(
                        text = "Executes: Pricing, Expansion, Investment, Innovation, Resource Allocation",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                IconButton(onClick = onAddClick, modifier = Modifier.testTag("add_decision_button")) {
                    Icon(Icons.Default.AddCircle, contentDescription = "Add Decision Directive", tint = MaterialTheme.colorScheme.primary)
                }
            }
        }

        items(items) { item ->
            Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = item.decisionDirectiveTitle,
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.weight(1f)
                        )
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = Color(0xFFFF6D00).copy(alpha = 0.15f)
                        ) {
                            Text(
                                text = item.executionDomain,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, color = Color(0xFFFF6D00))
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Text("Action: ${item.algorithmicAction}", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Magnitude: $${String.format("%.1f", item.economicMagnitudeTrillionUsd)}T", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold)
                        Text("Latency: ${item.executionLatencyMicrosec}µs", style = MaterialTheme.typography.labelSmall, fontFamily = FontFamily.Monospace)
                        Text("Confidence: ${String.format("%.1f", item.confidenceRatePct)}%", style = MaterialTheme.typography.labelSmall, color = Color(0xFF00C853))
                    }
                }
            }
        }
    }
}

// =========================================================================
// MODULE 10: PRIME KNOWLEDGE UNIVERSE
// =========================================================================
@Composable
fun KnowledgePrimeTab(items: List<KnowledgePrimeEntity>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .testTag("knowledge_prime_tab"),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Text(
                text = "Module 10: Prime Knowledge Universe",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
            )
            Text(
                text = "Stores: Past Intelligence, Present Intelligence, Future Intelligence, Evolution Intelligence",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        items(items) { item ->
            Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = item.knowledgeUniverseTopic,
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.weight(1f)
                        )
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = MaterialTheme.colorScheme.tertiaryContainer
                        ) {
                            Text(
                                text = item.temporalHorizon,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Text("Synthesis: ${item.executiveInsightSynthesis}", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Volume: ${item.synthesizedYottabytes} YB", style = MaterialTheme.typography.labelSmall, fontFamily = FontFamily.Monospace)
                        Text("Fidelity: ${String.format("%.2f", item.comprehensionFidelityPct)}%", style = MaterialTheme.typography.labelSmall, color = Color(0xFF00C853))
                    }
                }
            }
        }
    }
}

// =========================================================================
// MODULE 11: AUTONOMOUS INNOVATION FACTORY
// =========================================================================
@Composable
fun InnovationFactoryTab(
    items: List<InnovationFactoryEntity>,
    onAddClick: () -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .testTag("innovation_factory_tab"),
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
                        text = "Module 11: Autonomous Innovation Factory",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )
                    Text(
                        text = "Creates: Products, Patents, Technologies, Business Systems",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                IconButton(onClick = onAddClick, modifier = Modifier.testTag("add_innovation_button")) {
                    Icon(Icons.Default.AddCircle, contentDescription = "Add Innovation", tint = MaterialTheme.colorScheme.primary)
                }
            }
        }

        items(items) { item ->
            Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = item.innovationTitle,
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.weight(1f)
                        )
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = MaterialTheme.colorScheme.primaryContainer
                        ) {
                            Text(
                                text = item.creationCategory,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Text("Ref Code: ${item.globalIdentifier}", style = MaterialTheme.typography.bodySmall, fontFamily = FontFamily.Monospace)
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Pace: ${String.format("%.1f", item.commercializationPaceScore)}", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold)
                        Text("Impact: ${item.civilizationImpactMultiplier}x", style = MaterialTheme.typography.labelSmall, color = Color(0xFF00C853))
                    }
                }
            }
        }
    }
}

// =========================================================================
// MODULE 12: PRIME RISK SHIELD
// =========================================================================
@Composable
fun RiskShieldPrimeTab(items: List<RiskShieldPrimeEntity>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .testTag("risk_shield_prime_tab"),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Text(
                text = "Module 12: Prime Risk Shield",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
            )
            Text(
                text = "Protects: Markets, Trade, Capital, Innovation, Growth",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        items(items) { item ->
            Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = item.threatVectorMitigated,
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.error,
                            modifier = Modifier.weight(1f)
                        )
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = Color(0xFF00C853).copy(alpha = 0.15f)
                        ) {
                            Text(
                                text = item.protectedBastion,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, color = Color(0xFF00C853))
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Text("Mechanism: ${item.neutralizationMechanism}", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Speed: ${item.neutralizationSpeedNanosec}ns", style = MaterialTheme.typography.labelSmall, fontFamily = FontFamily.Monospace)
                        Text("Integrity: ${String.format("%.1f", item.fortressIntegrityPct)}%", style = MaterialTheme.typography.labelSmall, color = Color(0xFF00C853))
                    }
                }
            }
        }
    }
}

// =========================================================================
// MODULE 13: PRIME HEALTH SYSTEM
// =========================================================================
@Composable
fun HealthPrimeTab(items: List<HealthPrimeEntity>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .testTag("health_prime_tab"),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Text(
                text = "Module 13: Prime Health System",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
            )
            Text(
                text = "Monitors: Business Health, Market Health, Trade Health, Economic Health",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        items(items) { item ->
            Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = item.healthDimension,
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.primary
                        )
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = Color(0xFF00C853).copy(alpha = 0.15f)
                        ) {
                            Text(
                                text = "100% HEALTHY",
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, color = Color(0xFF00C853))
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(item.diagnosticSummary, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Score: ${String.format("%.2f", item.healthScore)}%", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold)
                        Text(item.operationalVitality, style = MaterialTheme.typography.labelSmall, color = Color(0xFF00C853))
                    }
                }
            }
        }
    }
}

// =========================================================================
// MODULE 14: PRIME COMMAND TOWER
// =========================================================================
@Composable
fun PrimeCommandTowerTab(items: List<PrimeCommandTowerEntity>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .testTag("prime_command_tower_tab"),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Text(
                text = "Module 14: Prime Command Tower",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
            )
            Text(
                text = "Controls: Economies, Markets, Industries, Trade Systems, Innovation Systems, AI Systems",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        items(items) { item ->
            Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = item.controlSector,
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.primary
                        )
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = MaterialTheme.colorScheme.primaryContainer
                        ) {
                            Text(
                                text = item.sentinelBeaconId,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Active Channels: ${item.activeChannelsCount}", style = MaterialTheme.typography.labelSmall)
                        Text("QPS: ${item.throughputQPS}", style = MaterialTheme.typography.labelSmall, fontFamily = FontFamily.Monospace)
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("Telemetry: ${String.format("%.2f", item.primeTelemetryScore)}% Omnipresent", style = MaterialTheme.typography.labelSmall, color = Color(0xFF00C853))
                }
            }
        }
    }
}

// =========================================================================
// MODULE 15: UNIVERSAL EVOLUTION AUTHORITY
// =========================================================================
@Composable
fun EvolutionAuthorityTab(items: List<EvolutionAuthorityEntity>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .testTag("evolution_authority_tab"),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Text(
                text = "Module 15: Universal Evolution Authority",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
            )
            Text(
                text = "Evolves: Businesses, Markets, Industries, Economies, Civilizations",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        items(items) { item ->
            Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = item.evolutionTarget,
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.primary
                        )
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = Color(0xFF6200EA).copy(alpha = 0.15f)
                        ) {
                            Text(
                                text = "${item.selfEvolutionFactor}x FACTOR",
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, color = Color(0xFF6200EA))
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Text("Vector: ${item.transformationVector}", style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Medium)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(item.evolutionBlueprintSummary, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Target: ${String.format("%.1f", item.targetEvolutionScore)}%", style = MaterialTheme.typography.labelSmall, fontFamily = FontFamily.Monospace)
                        Text(item.state, style = MaterialTheme.typography.labelSmall, color = Color(0xFF00C853))
                    }
                }
            }
        }
    }
}

// Helpers
@Composable
fun MetricLinearProgressRow(title: String, score: Double, color: Color) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(title, style = MaterialTheme.typography.bodySmall)
            Text("${String.format("%.2f", score)}%", style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace))
        }
        Spacer(modifier = Modifier.height(4.dp))
        LinearProgressIndicator(
            progress = { (score / 100.0).toFloat().coerceIn(0f, 1f) },
            modifier = Modifier
                .fillMaxWidth()
                .height(6.dp),
            color = color,
            trackColor = MaterialTheme.colorScheme.surfaceVariant
        )
    }
}

// =========================================================================
// DIALOGS
// =========================================================================
@Composable
private fun AddCivilizationDialog(
    onDismiss: () -> Unit,
    onConfirm: (domain: String, name: String, law: String, stability: Double, nodes: Long) -> Unit
) {
    var domain by remember { mutableStateOf("Markets") }
    var name by remember { mutableStateOf("") }
    var law by remember { mutableStateOf("") }
    var nodesStr by remember { mutableStateOf("1000000") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add Civilization Entity") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = domain,
                    onValueChange = { domain = it },
                    label = { Text("Domain (Markets, Industries, Trade...)") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Entity Name") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = law,
                    onValueChange = { law = it },
                    label = { Text("Autonomous Governance Law") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = nodesStr,
                    onValueChange = { nodesStr = it },
                    label = { Text("Active Nodes Count") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (name.isNotBlank()) {
                        onConfirm(
                            domain.trim(),
                            name.trim(),
                            if (law.isBlank()) "Algorithmic Market Equilibrium" else law.trim(),
                            100.0,
                            nodesStr.toLongOrNull() ?: 1000000L
                        )
                    }
                }
            ) {
                Text("Add Unit")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancel") }
        }
    )
}

@Composable
private fun AddWealthDialog(
    onDismiss: () -> Unit,
    onConfirm: (pillar: String, streamName: String, volume: Double, growth: Double) -> Unit
) {
    var pillar by remember { mutableStateOf("Revenue") }
    var streamName by remember { mutableStateOf("") }
    var volumeStr by remember { mutableStateOf("10.0") }
    var growthStr by remember { mutableStateOf("80.0") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Synthesize Wealth Stream") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = pillar,
                    onValueChange = { pillar = it },
                    label = { Text("Pillar (Revenue, Profit, Assets, Investments)") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = streamName,
                    onValueChange = { streamName = it },
                    label = { Text("Stream Name") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = volumeStr,
                    onValueChange = { volumeStr = it },
                    label = { Text("Volume (Trillion USD)") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = growthStr,
                    onValueChange = { growthStr = it },
                    label = { Text("Growth Rate (%)") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (streamName.isNotBlank()) {
                        onConfirm(
                            pillar.trim(),
                            streamName.trim(),
                            volumeStr.toDoubleOrNull() ?: 10.0,
                            growthStr.toDoubleOrNull() ?: 80.0
                        )
                    }
                }
            ) {
                Text("Synthesize")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancel") }
        }
    )
}

@Composable
private fun AddOpportunityPrimeDialog(
    onDismiss: () -> Unit,
    onConfirm: (horizon: String, title: String, valTrill: Double, days: Int, strat: String) -> Unit
) {
    var horizon by remember { mutableStateOf("New Industries") }
    var title by remember { mutableStateOf("") }
    var valStr by remember { mutableStateOf("5.0") }
    var daysStr by remember { mutableStateOf("14") }
    var strat by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Seed Prime Opportunity") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = horizon,
                    onValueChange = { horizon = it },
                    label = { Text("Horizon (New Industries, New Markets...)") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Opportunity Title") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = valStr,
                    onValueChange = { valStr = it },
                    label = { Text("Projected Value ($ Trillion)") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = strat,
                    onValueChange = { strat = it },
                    label = { Text("Seeding Strategy") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (title.isNotBlank()) {
                        onConfirm(
                            horizon.trim(),
                            title.trim(),
                            valStr.toDoubleOrNull() ?: 5.0,
                            daysStr.toIntOrNull() ?: 14,
                            if (strat.isBlank()) "Instant autonomous deployment." else strat.trim()
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
private fun AddDemandDialog(
    onDismiss: () -> Unit,
    onConfirm: (scope: String, cluster: String, units: Double, action: String) -> Unit
) {
    var scope by remember { mutableStateOf("Global Demand") }
    var cluster by remember { mutableStateOf("") }
    var unitsStr by remember { mutableStateOf("1000.0") }
    var action by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Predict Demand Cosmos Node") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = scope,
                    onValueChange = { scope = it },
                    label = { Text("Scope Level (Local, National, Global, Future)") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = cluster,
                    onValueChange = { cluster = it },
                    label = { Text("Market Cluster") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = unitsStr,
                    onValueChange = { unitsStr = it },
                    label = { Text("Predicted Units (Million)") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = action,
                    onValueChange = { action = it },
                    label = { Text("Dynamic Action") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (cluster.isNotBlank()) {
                        onConfirm(
                            scope.trim(),
                            cluster.trim(),
                            unitsStr.toDoubleOrNull() ?: 1000.0,
                            if (action.isBlank()) "Autonomous fulfillment synchronization." else action.trim()
                        )
                    }
                }
            ) {
                Text("Predict")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancel") }
        }
    )
}

@Composable
private fun AddCapitalFundDialog(
    onDismiss: () -> Unit,
    onConfirm: (pillar: String, name: String, total: Double, yield: Double) -> Unit
) {
    var pillar by remember { mutableStateOf("Investments") }
    var name by remember { mutableStateOf("") }
    var totalStr by remember { mutableStateOf("500.0") }
    var yieldStr by remember { mutableStateOf("30.0") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Charter Capital Fund") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = pillar,
                    onValueChange = { pillar = it },
                    label = { Text("Pillar (Investments, Growth Funds...)") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Fund Name") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = totalStr,
                    onValueChange = { totalStr = it },
                    label = { Text("Total AUM ($ Billion)") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = yieldStr,
                    onValueChange = { yieldStr = it },
                    label = { Text("Target Yield (%)") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (name.isNotBlank()) {
                        onConfirm(
                            pillar.trim(),
                            name.trim(),
                            totalStr.toDoubleOrNull() ?: 500.0,
                            yieldStr.toDoubleOrNull() ?: 30.0
                        )
                    }
                }
            ) {
                Text("Charter")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancel") }
        }
    )
}

@Composable
private fun AddDecisionDirectiveDialog(
    onDismiss: () -> Unit,
    onConfirm: (domain: String, title: String, mag: Double, action: String) -> Unit
) {
    var domain by remember { mutableStateOf("Pricing") }
    var title by remember { mutableStateOf("") }
    var magStr by remember { mutableStateOf("5.0") }
    var action by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Execute Prime Decision") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = domain,
                    onValueChange = { domain = it },
                    label = { Text("Domain (Pricing, Expansion, Investment...)") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Decision Directive Title") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = magStr,
                    onValueChange = { magStr = it },
                    label = { Text("Magnitude ($ Trillion)") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = action,
                    onValueChange = { action = it },
                    label = { Text("Algorithmic Action") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (title.isNotBlank()) {
                        onConfirm(
                            domain.trim(),
                            title.trim(),
                            magStr.toDoubleOrNull() ?: 5.0,
                            if (action.isBlank()) "Instant algorithmic adjustment." else action.trim()
                        )
                    }
                }
            ) {
                Text("Execute")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancel") }
        }
    )
}

@Composable
private fun AddInnovationDialog(
    onDismiss: () -> Unit,
    onConfirm: (cat: String, title: String, code: String, impact: Double) -> Unit
) {
    var cat by remember { mutableStateOf("Products") }
    var title by remember { mutableStateOf("") }
    var code by remember { mutableStateOf("PROD-PRIME-007") }
    var impactStr by remember { mutableStateOf("15.0") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Forge Innovation Asset") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = cat,
                    onValueChange = { cat = it },
                    label = { Text("Category (Products, Patents, Technologies)") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Innovation Title") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = code,
                    onValueChange = { code = it },
                    label = { Text("Global Identifier") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = impactStr,
                    onValueChange = { impactStr = it },
                    label = { Text("Impact Multiplier (x)") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (title.isNotBlank()) {
                        onConfirm(
                            cat.trim(),
                            title.trim(),
                            code.trim(),
                            impactStr.toDoubleOrNull() ?: 15.0
                        )
                    }
                }
            ) {
                Text("Forge")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancel") }
        }
    )
}
