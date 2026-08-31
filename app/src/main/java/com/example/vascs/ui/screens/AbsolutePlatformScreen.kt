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
import com.example.vascs.viewmodel.AbsoluteViewModel

enum class AbsoluteTab(val title: String, val icon: ImageVector) {
    CORE("Absolute Core", Icons.Default.Hub),
    ECONOMIC_OS("Economic OS", Icons.Default.Terminal),
    WEALTH_MATRIX("Wealth Matrix", Icons.Default.AccountBalance),
    OPPORTUNITY_GRID("Opportunity Grid", Icons.Default.GridView),
    DEMAND_MATRIX("Demand Matrix", Icons.Default.Timeline),
    CAPITAL_SUPREMACY("Capital Supremacy", Icons.Default.MonetizationOn),
    TRADE_NETWORK("Trade Network", Icons.Default.Language),
    REALITY_MATRIX("Reality Matrix", Icons.Default.Layers),
    DECISION_ENGINE("Decision Engine", Icons.Default.Bolt),
    KNOWLEDGE_MATRIX("Knowledge Matrix", Icons.Default.Psychology),
    INNOVATION_ENGINE("Innovation Engine", Icons.Default.Lightbulb),
    PROTECTION_SYSTEM("Protection System", Icons.Default.Shield),
    HEALTH_ENGINE("Health Engine", Icons.Default.HealthAndSafety),
    COMMAND_TOWER("Command Tower", Icons.Default.CellTower),
    UNITY_ENGINE("Unity Engine", Icons.Default.AllInclusive)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AbsolutePlatformScreen(
    viewModel: AbsoluteViewModel,
    onNavigateBack: () -> Unit
) {
    var selectedTab by remember { mutableStateOf(AbsoluteTab.CORE) }

    val absoluteCore by viewModel.absoluteCore.collectAsStateWithLifecycle()
    val economicOS by viewModel.economicOS.collectAsStateWithLifecycle()
    val wealthMatrix by viewModel.wealthMatrix.collectAsStateWithLifecycle()
    val opportunityGrid by viewModel.opportunityGrid.collectAsStateWithLifecycle()
    val demandMatrix by viewModel.demandMatrix.collectAsStateWithLifecycle()
    val capitalSupremacy by viewModel.capitalSupremacy.collectAsStateWithLifecycle()
    val tradeNetwork by viewModel.tradeNetwork.collectAsStateWithLifecycle()
    val realityMatrix by viewModel.realityMatrix.collectAsStateWithLifecycle()
    val decisionEngine by viewModel.decisionEngine.collectAsStateWithLifecycle()
    val knowledgeMatrix by viewModel.knowledgeMatrix.collectAsStateWithLifecycle()
    val innovationEngine by viewModel.innovationEngine.collectAsStateWithLifecycle()
    val protectionSystem by viewModel.protectionSystem.collectAsStateWithLifecycle()
    val healthEngine by viewModel.healthEngine.collectAsStateWithLifecycle()
    val absoluteCommandTower by viewModel.absoluteCommandTower.collectAsStateWithLifecycle()
    val unityEngine by viewModel.unityEngine.collectAsStateWithLifecycle()

    val absoluteIntelligenceIndex by viewModel.absoluteIntelligenceIndex.collectAsStateWithLifecycle()
    val isOperatingAutonomous by viewModel.isOperatingAutonomous.collectAsStateWithLifecycle()
    val statusMessage by viewModel.statusMessage.collectAsStateWithLifecycle()

    var showAddEconomicOSDialog by remember { mutableStateOf(false) }
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
        modifier = Modifier.testTag("absolute_platform_screen"),
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "VASCS ABSOLUTE",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Black,
                                    letterSpacing = 1.2.sp
                                )
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Surface(
                                shape = RoundedCornerShape(4.dp),
                                color = Color(0xFFD500F9)
                            ) {
                                Text(
                                    text = "CHECKPOINT 26.0",
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White
                                    )
                                )
                            }
                        }
                        Text(
                            text = "Ultimate Universal Intelligence Architecture",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                },
                navigationIcon = {
                    IconButton(
                        onClick = onNavigateBack,
                        modifier = Modifier.testTag("absolute_back_button")
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Navigate Back"
                        )
                    }
                },
                actions = {
                    FilledTonalButton(
                        onClick = { viewModel.triggerFullAbsoluteCycle() },
                        enabled = !isOperatingAutonomous,
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .testTag("absolute_sync_button"),
                        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
                    ) {
                        if (isOperatingAutonomous) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(16.dp),
                                strokeWidth = 2.dp
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Unifying...", style = MaterialTheme.typography.labelMedium)
                        } else {
                            Icon(
                                imageVector = Icons.Default.AllInclusive,
                                contentDescription = null,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Absolute Sync", style = MaterialTheme.typography.labelMedium)
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
            // Absolute Header Card
            AbsoluteHeaderCard(
                absoluteIndex = absoluteIntelligenceIndex,
                governedCivilizations = absoluteCore?.civilizationsGovernedCount ?: 1250,
                statusText = absoluteCore?.absoluteStatus ?: "Universal Intelligence Controller Active",
                isOperating = isOperatingAutonomous
            )

            // Horizontal Tab Row
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(AbsoluteTab.values()) { tab ->
                    val isSelected = selectedTab == tab
                    FilterChip(
                        selected = isSelected,
                        onClick = { selectedTab = tab },
                        label = { Text(tab.title, fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal) },
                        leadingIcon = {
                            Icon(
                                imageVector = tab.icon,
                                contentDescription = null,
                                modifier = Modifier.size(18.dp)
                            )
                        },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                            selectedLabelColor = MaterialTheme.colorScheme.onPrimaryContainer,
                            selectedLeadingIconColor = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    )
                }
            }

            HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))

            // Main Tab Content
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
            ) {
                when (selectedTab) {
                    AbsoluteTab.CORE -> AbsoluteCoreSection(
                        core = absoluteCore,
                        onTriggerAction = { viewModel.runAbsoluteCoreAction() }
                    )
                    AbsoluteTab.ECONOMIC_OS -> EconomicOSSection(
                        items = economicOS,
                        onAddUnit = { showAddEconomicOSDialog = true }
                    )
                    AbsoluteTab.WEALTH_MATRIX -> WealthMatrixSection(
                        items = wealthMatrix,
                        onCalculate = { viewModel.calculateWealthMatrixAction() },
                        onAddStream = { showAddWealthDialog = true }
                    )
                    AbsoluteTab.OPPORTUNITY_GRID -> OpportunityGridSection(
                        items = opportunityGrid,
                        onAddOpportunity = { showAddOpportunityDialog = true }
                    )
                    AbsoluteTab.DEMAND_MATRIX -> DemandMatrixSection(
                        items = demandMatrix,
                        onForecast = { viewModel.forecastDemandMatrixAction() },
                        onAddForecast = { showAddDemandDialog = true }
                    )
                    AbsoluteTab.CAPITAL_SUPREMACY -> CapitalSupremacySection(
                        items = capitalSupremacy,
                        onManage = { viewModel.manageCapitalSupremacyAction() },
                        onAddCapital = { showAddCapitalDialog = true }
                    )
                    AbsoluteTab.TRADE_NETWORK -> TradeNetworkSection(
                        items = tradeNetwork,
                        onOptimize = { viewModel.optimizeTradeNetworkAction() },
                        onAddRoute = { showAddTradeDialog = true }
                    )
                    AbsoluteTab.REALITY_MATRIX -> RealityMatrixSection(
                        items = realityMatrix
                    )
                    AbsoluteTab.DECISION_ENGINE -> DecisionEngineSection(
                        items = decisionEngine,
                        onAddPolicy = { showAddDecisionDialog = true }
                    )
                    AbsoluteTab.KNOWLEDGE_MATRIX -> KnowledgeMatrixSection(
                        items = knowledgeMatrix
                    )
                    AbsoluteTab.INNOVATION_ENGINE -> InnovationEngineSection(
                        items = innovationEngine
                    )
                    AbsoluteTab.PROTECTION_SYSTEM -> ProtectionSystemSection(
                        items = protectionSystem
                    )
                    AbsoluteTab.HEALTH_ENGINE -> HealthEngineSection(
                        items = healthEngine
                    )
                    AbsoluteTab.COMMAND_TOWER -> AbsoluteCommandTowerSection(
                        items = absoluteCommandTower
                    )
                    AbsoluteTab.UNITY_ENGINE -> UnityEngineSection(
                        items = unityEngine
                    )
                }
            }
        }
    }

    // Dialogs
    if (showAddEconomicOSDialog) {
        AddEconomicOSDialog(
            onDismiss = { showAddEconomicOSDialog = false },
            onConfirm = { domain, name, law, stability, nodes ->
                viewModel.addEconomicOSUnit(domain, name, law, stability, nodes)
                showAddEconomicOSDialog = false
            }
        )
    }

    if (showAddWealthDialog) {
        AddWealthMatrixDialog(
            onDismiss = { showAddWealthDialog = false },
            onConfirm = { pillar, stream, volume, growth ->
                viewModel.addWealthMatrixStream(pillar, stream, volume, growth)
                showAddWealthDialog = false
            }
        )
    }

    if (showAddOpportunityDialog) {
        AddOpportunityGridDialog(
            onDismiss = { showAddOpportunityDialog = false },
            onConfirm = { horizon, concept, value, days ->
                viewModel.addOpportunityGridItem(horizon, concept, value, days)
                showAddOpportunityDialog = false
            }
        )
    }

    if (showAddDemandDialog) {
        AddDemandMatrixDialog(
            onDismiss = { showAddDemandDialog = false },
            onConfirm = { span, cluster, units ->
                viewModel.addDemandMatrixForecast(span, cluster, units)
                showAddDemandDialog = false
            }
        )
    }

    if (showAddCapitalDialog) {
        AddCapitalSupremacyDialog(
            onDismiss = { showAddCapitalDialog = false },
            onConfirm = { sector, name, volume, yieldPct ->
                viewModel.addCapitalSupremacyPool(sector, name, volume, yieldPct)
                showAddCapitalDialog = false
            }
        )
    }

    if (showAddTradeDialog) {
        AddTradeRouteDialog(
            onDismiss = { showAddTradeDialog = false },
            onConfirm = { domain, name, throughput ->
                viewModel.addTradeRoute(domain, name, throughput)
                showAddTradeDialog = false
            }
        )
    }

    if (showAddDecisionDialog) {
        AddDecisionPolicyDialog(
            onDismiss = { showAddDecisionDialog = false },
            onConfirm = { type, title, impact ->
                viewModel.addDecisionPolicy(type, title, impact)
                showAddDecisionDialog = false
            }
        )
    }
}

// ----------------------------------------------------
// HEADER CARD WITH ABSOLUTE VISION
// ----------------------------------------------------

@Composable
fun AbsoluteHeaderCard(
    absoluteIndex: Double,
    governedCivilizations: Int,
    statusText: String,
    isOperating: Boolean
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .testTag("absolute_header_card"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
        ),
        border = BorderStroke(1.dp, Brush.horizontalGradient(listOf(Color(0xFFD500F9), Color(0xFF651FFF), Color(0xFF00E5FF))))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "ABSOLUTE VISION",
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontWeight = FontWeight.Black,
                            letterSpacing = 1.5.sp,
                            color = Color(0xFFD500F9)
                        )
                    )
                    Text(
                        text = "One Intelligence • One Commerce Brain • One Economic Universe • One Autonomous Civilization • Absolute Coordination",
                        style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Medium),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }

                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = Color(0xFFD500F9).copy(alpha = 0.15f),
                    border = BorderStroke(1.dp, Color(0xFFD500F9))
                ) {
                    Column(
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = String.format("%.1f%%", absoluteIndex),
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Black,
                                color = Color(0xFFD500F9)
                            )
                        )
                        Text(
                            text = "Absolute Index",
                            style = MaterialTheme.typography.labelSmall.copy(fontSize = 9.sp),
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                AbsoluteMetricPill(
                    label = "Governed Realms",
                    value = "$governedCivilizations",
                    icon = Icons.Default.Public
                )
                AbsoluteMetricPill(
                    label = "Coordination",
                    value = "100.0%",
                    icon = Icons.Default.AllInclusive
                )
                AbsoluteMetricPill(
                    label = "Control Rate",
                    value = "100.0%",
                    icon = Icons.Default.Security
                )
                AbsoluteMetricPill(
                    label = "Universal Brain",
                    value = "Omnipresent",
                    icon = Icons.Default.Psychology
                )
            }
        }
    }
}

@Composable
fun AbsoluteMetricPill(
    label: String,
    value: String,
    icon: ImageVector
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(8.dp))
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(14.dp),
            tint = Color(0xFFD500F9)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Column {
            Text(
                text = value,
                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold)
            )
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall.copy(fontSize = 8.sp),
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

// ----------------------------------------------------
// MODULE 1: ABSOLUTE CORE
// ----------------------------------------------------

@Composable
fun AbsoluteCoreSection(
    core: AbsoluteCoreEntity?,
    onTriggerAction: () -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "Absolute Intelligence Controller",
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                            )
                            Text(
                                text = "Core Module 1 • Sovereign Unified Brain",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        Button(
                            onClick = onTriggerAction,
                            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
                        ) {
                            Icon(Icons.Default.Bolt, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Run Core", style = MaterialTheme.typography.labelMedium)
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "CORE RESPONSIBILITIES",
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFD500F9)
                        )
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        CoreResponsibilityRow("Universal Control", "100.0% Autonomous Sovereign Directive", Icons.Default.Security)
                        CoreResponsibilityRow("Infinite Coordination", "Zero-Latency Real-Time Ecosystem Synchronization", Icons.Default.Hub)
                        CoreResponsibilityRow("Civilization Governance", "Holistic Economic, Cultural & Artisan Protection", Icons.Default.AccountBalance)
                        CoreResponsibilityRow("Autonomous Optimization", "Continuous Meta-Learning & Self-Rebalancing", Icons.Default.AutoAwesome)
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    HorizontalDivider()

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "CONTROLLER TELEMETRY",
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = core?.universalControllerTelemetry ?: "Absolute Sovereign Unified Brain Operational - Full Nexus-Cosmos-Omega Unification Active",
                        style = MaterialTheme.typography.bodySmall,
                        fontFamily = FontFamily.Monospace,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

@Composable
fun CoreResponsibilityRow(title: String, desc: String, icon: ImageVector) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f), RoundedCornerShape(8.dp))
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(imageVector = icon, contentDescription = null, tint = Color(0xFFD500F9), modifier = Modifier.size(20.dp))
        Spacer(modifier = Modifier.width(12.dp))
        Column {
            Text(text = title, style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold))
            Text(text = desc, style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp), color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}

// ----------------------------------------------------
// MODULE 2: UNIVERSAL ECONOMIC OPERATING SYSTEM
// ----------------------------------------------------

@Composable
fun EconomicOSSection(
    items: List<EconomicOSEntity>,
    onAddUnit: () -> Unit
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
                        text = "Universal Economic Operating System",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )
                    Text(
                        text = "Controls: Markets • Industries • Trade Systems • Capital Systems • Innovation Systems",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                FilledTonalButton(
                    onClick = onAddUnit,
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Add OS", style = MaterialTheme.typography.labelMedium)
                }
            }
        }

        items(items) { os ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            shape = RoundedCornerShape(4.dp),
                            color = Color(0xFF651FFF).copy(alpha = 0.15f)
                        ) {
                            Text(
                                text = os.subsystemDomain.uppercase(),
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF651FFF)
                                )
                            )
                        }
                        Text(
                            text = "Stability: ${os.kernelStabilityPct}%",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF00C853)
                            )
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = os.operatingSystemName,
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "Law: ${os.governanceLaw}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Active Nodes: ${os.activeUnifiedNodesCount}",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            text = os.executionState,
                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                            color = Color(0xFFD500F9)
                        )
                    }
                }
            }
        }
    }
}

// ----------------------------------------------------
// MODULE 3: ABSOLUTE WEALTH MATRIX
// ----------------------------------------------------

@Composable
fun WealthMatrixSection(
    items: List<WealthMatrixEntity>,
    onCalculate: () -> Unit,
    onAddStream: () -> Unit
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
                        text = "Absolute Wealth Matrix",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )
                    Text(
                        text = "Controls: Revenue • Profit • Assets • Capital Growth • Economic Value",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    OutlinedButton(
                        onClick = onCalculate,
                        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 6.dp)
                    ) {
                        Text("Calculate", style = MaterialTheme.typography.labelMedium)
                    }
                    FilledTonalButton(
                        onClick = onAddStream,
                        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 6.dp)
                    ) {
                        Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(16.dp))
                        Text("Add", style = MaterialTheme.typography.labelMedium)
                    }
                }
            }
        }

        items(items) { w ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            shape = RoundedCornerShape(4.dp),
                            color = Color(0xFFFFD600).copy(alpha = 0.2f)
                        ) {
                            Text(
                                text = w.wealthPillar.uppercase(),
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.Black,
                                    color = Color(0xFFFFAB00)
                                )
                            )
                        }
                        Text(
                            text = "$${w.volumeTrillionUsd}T USD",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Black,
                                color = Color(0xFF00C853)
                            )
                        )
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = w.streamIdentifier,
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "CAGR: +${w.compoundGrowthRatePct}%",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            text = w.capitalAllocationStatus,
                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                            color = Color(0xFFD500F9)
                        )
                    }
                }
            }
        }
    }
}

// ----------------------------------------------------
// MODULE 4: UNIVERSAL OPPORTUNITY GRID
// ----------------------------------------------------

@Composable
fun OpportunityGridSection(
    items: List<OpportunityGridEntity>,
    onAddOpportunity: () -> Unit
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
                        text = "Universal Opportunity Grid",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )
                    Text(
                        text = "Discovers: Future Markets • Industries • Economies • Opportunities",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                FilledTonalButton(
                    onClick = onAddOpportunity,
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Add", style = MaterialTheme.typography.labelMedium)
                }
            }
        }

        items(items) { opp ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            shape = RoundedCornerShape(4.dp),
                            color = Color(0xFF00E5FF).copy(alpha = 0.15f)
                        ) {
                            Text(
                                text = opp.discoveryHorizon.uppercase(),
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF00B0FF)
                                )
                            )
                        }
                        Text(
                            text = "Genesis: ${opp.timeToGenesisDays}d",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = opp.opportunityConcept,
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Projected: $${opp.projectedValueTrillionUsd}T USD",
                            style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
                            color = Color(0xFF00C853)
                        )
                        Text(
                            text = "Prob: ${opp.realizationProbabilityPct}%",
                            style = MaterialTheme.typography.labelSmall,
                            color = Color(0xFFD500F9)
                        )
                    }
                }
            }
        }
    }
}

// ----------------------------------------------------
// MODULE 5: ABSOLUTE DEMAND MATRIX
// ----------------------------------------------------

@Composable
fun DemandMatrixSection(
    items: List<DemandMatrixEntity>,
    onForecast: () -> Unit,
    onAddForecast: () -> Unit
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
                        text = "Absolute Demand Matrix",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )
                    Text(
                        text = "Forecasts: Daily • Monthly • Yearly • Decade • Century Demand",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    OutlinedButton(
                        onClick = onForecast,
                        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 6.dp)
                    ) {
                        Text("Forecast", style = MaterialTheme.typography.labelMedium)
                    }
                    FilledTonalButton(
                        onClick = onAddForecast,
                        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 6.dp)
                    ) {
                        Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(16.dp))
                        Text("Add", style = MaterialTheme.typography.labelMedium)
                    }
                }
            }
        }

        items(items) { d ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            shape = RoundedCornerShape(4.dp),
                            color = Color(0xFFFF3D00).copy(alpha = 0.15f)
                        ) {
                            Text(
                                text = d.temporalSpan.uppercase(),
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFFFF3D00)
                                )
                            )
                        }
                        Text(
                            text = "${d.predictedDemandMillionUnits}M Units",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Black,
                                color = Color(0xFF651FFF)
                            )
                        )
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = d.marketCluster,
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Precision: ${d.fulfillmentPrecisionPct}% (Latency: ${d.predictiveLatencyMs}ms)",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "Action: ${d.autoBalancingAction}",
                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                        color = Color(0xFF00C853)
                    )
                }
            }
        }
    }
}

// ----------------------------------------------------
// MODULE 6: UNIVERSAL CAPITAL SUPREMACY
// ----------------------------------------------------

@Composable
fun CapitalSupremacySection(
    items: List<CapitalSupremacyEntity>,
    onManage: () -> Unit,
    onAddCapital: () -> Unit
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
                        text = "Universal Capital Supremacy",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )
                    Text(
                        text = "Manages: Investments • Assets • Funds • Expansion Capital • Innovation Capital",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    OutlinedButton(
                        onClick = onManage,
                        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 6.dp)
                    ) {
                        Text("Manage", style = MaterialTheme.typography.labelMedium)
                    }
                    FilledTonalButton(
                        onClick = onAddCapital,
                        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 6.dp)
                    ) {
                        Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(16.dp))
                        Text("Add", style = MaterialTheme.typography.labelMedium)
                    }
                }
            }
        }

        items(items) { c ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            shape = RoundedCornerShape(4.dp),
                            color = Color(0xFF00C853).copy(alpha = 0.15f)
                        ) {
                            Text(
                                text = c.capitalSector.uppercase(),
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF00C853)
                                )
                            )
                        }
                        Text(
                            text = "$${c.managedVolumeBillionUsd}B USD",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Black,
                                color = Color(0xFF00C853)
                            )
                        )
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = c.fundOrPoolName,
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Yield: +${c.annualizedYieldPct}% (Solvency: ${c.reserveSolvencyRatioPct}%)",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            text = c.deploymentMode,
                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                            color = Color(0xFFD500F9)
                        )
                    }
                }
            }
        }
    }
}

// ----------------------------------------------------
// MODULE 7: ABSOLUTE TRADE NETWORK
// ----------------------------------------------------

@Composable
fun TradeNetworkSection(
    items: List<TradeNetworkEntity>,
    onOptimize: () -> Unit,
    onAddRoute: () -> Unit
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
                        text = "Absolute Trade Network",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )
                    Text(
                        text = "Optimizes: Trade Routes • Distribution • Supply Chains • Commerce Networks",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    OutlinedButton(
                        onClick = onOptimize,
                        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 6.dp)
                    ) {
                        Text("Optimize", style = MaterialTheme.typography.labelMedium)
                    }
                    FilledTonalButton(
                        onClick = onAddRoute,
                        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 6.dp)
                    ) {
                        Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(16.dp))
                        Text("Add", style = MaterialTheme.typography.labelMedium)
                    }
                }
            }
        }

        items(items) { t ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            shape = RoundedCornerShape(4.dp),
                            color = Color(0xFF651FFF).copy(alpha = 0.15f)
                        ) {
                            Text(
                                text = t.optimizationDomain.uppercase(),
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF651FFF)
                                )
                            )
                        }
                        Text(
                            text = "$${t.throughputBillionUsdPerMonth}B / Mo",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Black,
                                color = Color(0xFF00E5FF)
                            )
                        )
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = t.routeMeshName,
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Latency: ${t.routingLatencyMs}ms (Clearance: ${t.seamlessClearanceRatePct}%)",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            text = t.routeProtectionStatus,
                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                            color = Color(0xFF00C853)
                        )
                    }
                }
            }
        }
    }
}

// ----------------------------------------------------
// MODULE 8: UNIVERSAL REALITY MATRIX
// ----------------------------------------------------

@Composable
fun RealityMatrixSection(
    items: List<RealityMatrixEntity>
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
                    text = "Universal Reality Matrix",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                )
                Text(
                    text = "Operates: Business Reality • Market Reality • Economic Reality • Civilization Reality",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        items(items) { r ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            shape = RoundedCornerShape(4.dp),
                            color = Color(0xFFD500F9).copy(alpha = 0.15f)
                        ) {
                            Text(
                                text = r.realityLayer.uppercase(),
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFFD500F9)
                                )
                            )
                        }
                        Text(
                            text = "Fidelity: ${r.simulationFidelityPct}%",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF00C853)
                            )
                        )
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = r.matrixDesignation,
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "Compute: ${r.computeOpsPerSecMillion}M ops/s (Coherence: ${r.quantumCoherenceRatePct}%)",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = r.synthesisAction,
                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                        color = Color(0xFF00B0FF)
                    )
                }
            }
        }
    }
}

// ----------------------------------------------------
// MODULE 9: ABSOLUTE DECISION ENGINE
// ----------------------------------------------------

@Composable
fun DecisionEngineSection(
    items: List<DecisionEngineEntity>,
    onAddPolicy: () -> Unit
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
                        text = "Absolute Decision Engine",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )
                    Text(
                        text = "Executes: Pricing • Expansion • Investment • Innovation • Resource Allocation Decisions",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                FilledTonalButton(
                    onClick = onAddPolicy,
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Add", style = MaterialTheme.typography.labelMedium)
                }
            }
        }

        items(items) { d ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            shape = RoundedCornerShape(4.dp),
                            color = Color(0xFFFF6D00).copy(alpha = 0.15f)
                        ) {
                            Text(
                                text = d.decisionType.uppercase(),
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFFFF6D00)
                                )
                            )
                        }
                        Text(
                            text = "Impact: $${d.economicImpactTrillionUsd}T",
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontWeight = FontWeight.Black,
                                color = Color(0xFF00C853)
                            )
                        )
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = d.policyTitle,
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Accuracy: ${d.decisionAccuracyIndex}% (Latency: ${d.executionLatencyMicrosec}µs)",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            text = d.autonomousDirective,
                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                            color = Color(0xFFD500F9)
                        )
                    }
                }
            }
        }
    }
}

// ----------------------------------------------------
// MODULE 10: UNIVERSAL KNOWLEDGE MATRIX
// ----------------------------------------------------

@Composable
fun KnowledgeMatrixSection(
    items: List<KnowledgeMatrixEntity>
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
                    text = "Universal Knowledge Matrix",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                )
                Text(
                    text = "Synthesizes: Past Knowledge • Present Knowledge • Future Knowledge • Evolution Knowledge",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        items(items) { k ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            shape = RoundedCornerShape(4.dp),
                            color = Color(0xFF651FFF).copy(alpha = 0.15f)
                        ) {
                            Text(
                                text = k.temporalSphere.uppercase(),
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF651FFF)
                                )
                            )
                        }
                        Text(
                            text = "${k.synthesizedDataVolumeYb} YB",
                            style = MaterialTheme.typography.titleSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF00E5FF)
                            )
                        )
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = k.corpusDomain,
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = k.executiveWisdomSynthesis,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

// ----------------------------------------------------
// MODULE 11: ABSOLUTE INNOVATION ENGINE
// ----------------------------------------------------

@Composable
fun InnovationEngineSection(
    items: List<InnovationEngineEntity>
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
                    text = "Absolute Innovation Engine",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                )
                Text(
                    text = "Generates: Products • Technologies • Patents • Business Systems",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        items(items) { inn ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            shape = RoundedCornerShape(4.dp),
                            color = Color(0xFFFFD600).copy(alpha = 0.2f)
                        ) {
                            Text(
                                text = inn.innovationCategory.uppercase(),
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFFFFAB00)
                                )
                            )
                        }
                        Text(
                            text = "${inn.commercialVelocityMultiplier}x Velocity",
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF00C853)
                            )
                        )
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = inn.breakthroughTitle,
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = inn.registryIdentifier,
                            style = MaterialTheme.typography.bodySmall,
                            fontFamily = FontFamily.Monospace,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            text = inn.deploymentStatus,
                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                            color = Color(0xFFD500F9)
                        )
                    }
                }
            }
        }
    }
}

// ----------------------------------------------------
// MODULE 12: UNIVERSAL PROTECTION SYSTEM
// ----------------------------------------------------

@Composable
fun ProtectionSystemSection(
    items: List<ProtectionSystemEntity>
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
                    text = "Universal Protection System",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                )
                Text(
                    text = "Protects: Capital • Markets • Trade • Innovation • Growth",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        items(items) { p ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            shape = RoundedCornerShape(4.dp),
                            color = Color(0xFF00C853).copy(alpha = 0.15f)
                        ) {
                            Text(
                                text = p.protectedFrontier.uppercase(),
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF00C853)
                                )
                            )
                        }
                        Text(
                            text = "Integrity: ${p.barrierIntegrityPct}%",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF00C853)
                            )
                        )
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "Nullified Threat: ${p.threatVectorNullified}",
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "Defense: ${p.defenseProtocol} (Mitigation: ${p.mitigationLatencyNanosec}ns)",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = p.fortressStatus,
                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                        color = Color(0xFFD500F9)
                    )
                }
            }
        }
    }
}

// ----------------------------------------------------
// MODULE 13: ABSOLUTE HEALTH ENGINE
// ----------------------------------------------------

@Composable
fun HealthEngineSection(
    items: List<AbsoluteHealthEngineEntity>
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
                    text = "Absolute Health Engine",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                )
                Text(
                    text = "Monitors: Business Health • Market Health • Trade Health • Economic Health",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        items(items) { h ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            shape = RoundedCornerShape(4.dp),
                            color = Color(0xFF00E5FF).copy(alpha = 0.15f)
                        ) {
                            Text(
                                text = h.diagnosticDomain.uppercase(),
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF00B0FF)
                                )
                            )
                        }
                        Text(
                            text = "Vitality: ${h.vitalityScore}%",
                            style = MaterialTheme.typography.titleSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF00C853)
                            )
                        )
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = h.diagnosticSynthesis,
                        style = MaterialTheme.typography.bodyMedium
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = h.systemicEquilibriumState,
                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                        color = Color(0xFFD500F9)
                    )
                }
            }
        }
    }
}

// ----------------------------------------------------
// MODULE 14: ABSOLUTE COMMAND TOWER
// ----------------------------------------------------

@Composable
fun AbsoluteCommandTowerSection(
    items: List<AbsoluteCommandTowerEntity>
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
                    text = "Absolute Command Tower",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                )
                Text(
                    text = "Governs: Economies • Industries • Markets • Trade Networks • Innovation Systems • AI Systems",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        items(items) { t ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            shape = RoundedCornerShape(4.dp),
                            color = Color(0xFFD500F9).copy(alpha = 0.15f)
                        ) {
                            Text(
                                text = t.governanceSector.uppercase(),
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFFD500F9)
                                )
                            )
                        }
                        Text(
                            text = "${t.throughputQPS} QPS",
                            style = MaterialTheme.typography.titleSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF00E5FF)
                            )
                        )
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "Tower ID: ${t.commandTowerId}",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.Monospace
                        )
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Channels: ${t.activeChannelsCount} (Telemetry: ${t.telemetryScore}%)",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            text = t.universalState,
                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                            color = Color(0xFF00C853)
                        )
                    }
                }
            }
        }
    }
}

// ----------------------------------------------------
// MODULE 15: UNIVERSAL UNITY ENGINE
// ----------------------------------------------------

@Composable
fun UnityEngineSection(
    items: List<UnityEngineEntity>
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
                    text = "Universal Unity Engine",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                )
                Text(
                    text = "Unifies: Business • Markets • Industries • Economies • Civilizations into One Living Organism",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        items(items) { u ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            shape = RoundedCornerShape(4.dp),
                            color = Color(0xFF651FFF).copy(alpha = 0.15f)
                        ) {
                            Text(
                                text = u.unificationTarget.uppercase(),
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF651FFF)
                                )
                            )
                        }
                        Text(
                            text = "Unity: ${u.universalUnityIndex}%",
                            style = MaterialTheme.typography.titleSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF00C853)
                            )
                        )
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = u.convergenceVector,
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = u.unificationBlueprint,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Cohesion: ${u.organismCohesionFactor}x",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            text = u.state,
                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                            color = Color(0xFFD500F9)
                        )
                    }
                }
            }
        }
    }
}

// ----------------------------------------------------
// DIALOGS FOR CREATING NEW ENTRIES
// ----------------------------------------------------

@Composable
fun AddEconomicOSDialog(
    onDismiss: () -> Unit,
    onConfirm: (domain: String, name: String, law: String, stability: Double, nodes: Long) -> Unit
) {
    var domain by remember { mutableStateOf("Markets") }
    var name by remember { mutableStateOf("") }
    var law by remember { mutableStateOf("") }
    var stabilityStr by remember { mutableStateOf("100.0") }
    var nodesStr by remember { mutableStateOf("10000000") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Register Economic OS Subsystem") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = domain,
                    onValueChange = { domain = it },
                    label = { Text("Domain (Markets, Industries, Trade, Capital, Innovation)") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Operating System Name") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = law,
                    onValueChange = { law = it },
                    label = { Text("Governance Law") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = nodesStr,
                    onValueChange = { nodesStr = it },
                    label = { Text("Active Unified Nodes") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val stability = stabilityStr.toDoubleOrNull() ?: 100.0
                    val nodes = nodesStr.toLongOrNull() ?: 10000000L
                    if (name.isNotBlank() && law.isNotBlank()) {
                        onConfirm(domain, name, law, stability, nodes)
                    }
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
fun AddWealthMatrixDialog(
    onDismiss: () -> Unit,
    onConfirm: (pillar: String, stream: String, volume: Double, growth: Double) -> Unit
) {
    var pillar by remember { mutableStateOf("Revenue") }
    var stream by remember { mutableStateOf("") }
    var volumeStr by remember { mutableStateOf("25.0") }
    var growthStr by remember { mutableStateOf("35.0") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add Wealth Matrix Stream") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = pillar,
                    onValueChange = { pillar = it },
                    label = { Text("Pillar (Revenue, Profit, Assets, Capital Growth, Economic Value)") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = stream,
                    onValueChange = { stream = it },
                    label = { Text("Stream Identifier") },
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
                    label = { Text("CAGR Growth %") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val volume = volumeStr.toDoubleOrNull() ?: 25.0
                    val growth = growthStr.toDoubleOrNull() ?: 35.0
                    if (stream.isNotBlank()) {
                        onConfirm(pillar, stream, volume, growth)
                    }
                }
            ) {
                Text("Add Stream")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancel") }
        }
    )
}

@Composable
fun AddOpportunityGridDialog(
    onDismiss: () -> Unit,
    onConfirm: (horizon: String, concept: String, value: Double, days: Int) -> Unit
) {
    var horizon by remember { mutableStateOf("Future Markets") }
    var concept by remember { mutableStateOf("") }
    var valueStr by remember { mutableStateOf("15.0") }
    var daysStr by remember { mutableStateOf("14") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add Grid Opportunity") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = horizon,
                    onValueChange = { horizon = it },
                    label = { Text("Horizon (Future Markets/Industries/Economies/Opportunities)") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = concept,
                    onValueChange = { concept = it },
                    label = { Text("Opportunity Concept") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = valueStr,
                    onValueChange = { valueStr = it },
                    label = { Text("Projected Value (Trillion USD)") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = daysStr,
                    onValueChange = { daysStr = it },
                    label = { Text("Time to Genesis (Days)") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val value = valueStr.toDoubleOrNull() ?: 15.0
                    val days = daysStr.toIntOrNull() ?: 14
                    if (concept.isNotBlank()) {
                        onConfirm(horizon, concept, value, days)
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
fun AddDemandMatrixDialog(
    onDismiss: () -> Unit,
    onConfirm: (span: String, cluster: String, units: Double) -> Unit
) {
    var span by remember { mutableStateOf("Monthly Demand") }
    var cluster by remember { mutableStateOf("") }
    var unitsStr by remember { mutableStateOf("50.0") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add Demand Horizon Forecast") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = span,
                    onValueChange = { span = it },
                    label = { Text("Temporal Span (Daily, Monthly, Yearly, Decade, Century)") },
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
                    label = { Text("Predicted Demand (Million Units)") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val units = unitsStr.toDoubleOrNull() ?: 50.0
                    if (cluster.isNotBlank()) {
                        onConfirm(span, cluster, units)
                    }
                }
            ) {
                Text("Add Forecast")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancel") }
        }
    )
}

@Composable
fun AddCapitalSupremacyDialog(
    onDismiss: () -> Unit,
    onConfirm: (sector: String, name: String, volume: Double, yieldPct: Double) -> Unit
) {
    var sector by remember { mutableStateOf("Investments") }
    var name by remember { mutableStateOf("") }
    var volumeStr by remember { mutableStateOf("100.0") }
    var yieldStr by remember { mutableStateOf("30.0") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Allocate Capital Pool") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = sector,
                    onValueChange = { sector = it },
                    label = { Text("Capital Sector") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Fund / Reserve Name") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = volumeStr,
                    onValueChange = { volumeStr = it },
                    label = { Text("Managed Volume (Billion USD)") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = yieldStr,
                    onValueChange = { yieldStr = it },
                    label = { Text("Annualized Yield %") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val volume = volumeStr.toDoubleOrNull() ?: 100.0
                    val yieldPct = yieldStr.toDoubleOrNull() ?: 30.0
                    if (name.isNotBlank()) {
                        onConfirm(sector, name, volume, yieldPct)
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
fun AddTradeRouteDialog(
    onDismiss: () -> Unit,
    onConfirm: (domain: String, name: String, throughput: Double) -> Unit
) {
    var domain by remember { mutableStateOf("Trade Routes") }
    var name by remember { mutableStateOf("") }
    var throughputStr by remember { mutableStateOf("10.0") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Integrate Trade Mesh Route") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = domain,
                    onValueChange = { domain = it },
                    label = { Text("Optimization Domain") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Route Mesh Name") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = throughputStr,
                    onValueChange = { throughputStr = it },
                    label = { Text("Throughput (Billion USD / Month)") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val tp = throughputStr.toDoubleOrNull() ?: 10.0
                    if (name.isNotBlank()) {
                        onConfirm(domain, name, tp)
                    }
                }
            ) {
                Text("Integrate")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancel") }
        }
    )
}

@Composable
fun AddDecisionPolicyDialog(
    onDismiss: () -> Unit,
    onConfirm: (type: String, title: String, impact: Double) -> Unit
) {
    var type by remember { mutableStateOf("Pricing") }
    var title by remember { mutableStateOf("") }
    var impactStr by remember { mutableStateOf("2.0") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Enact Absolute Decision Policy") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = type,
                    onValueChange = { type = it },
                    label = { Text("Decision Type (Pricing, Expansion, Investment, Innovation, Resource Allocation)") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Policy Title") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = impactStr,
                    onValueChange = { impactStr = it },
                    label = { Text("Economic Impact (Trillion USD)") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val impact = impactStr.toDoubleOrNull() ?: 2.0
                    if (title.isNotBlank()) {
                        onConfirm(type, title, impact)
                    }
                }
            ) {
                Text("Enact Policy")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancel") }
        }
    )
}
