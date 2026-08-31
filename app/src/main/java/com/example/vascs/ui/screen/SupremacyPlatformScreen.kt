package com.example.vascs.ui.screen

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.vascs.data.model.*
import com.example.vascs.ui.viewmodel.SupremacyViewModel
import java.util.Locale

enum class SupremacyModuleTab(val label: String, val icon: ImageVector) {
    CORE("Supremacy Core", Icons.Filled.Stars),
    GOVERNANCE("Civilization Governance", Icons.Filled.AccountBalance),
    ECONOMIC_COMMAND("Economic Command", Icons.Filled.AccountTree),
    OPPORTUNITY("Opportunity Supremacy", Icons.Filled.TravelExplore),
    EXPANSION("Expansion Network", Icons.Filled.Public),
    CAPITAL("Capital Matrix", Icons.Filled.MonetizationOn),
    TRADE("Trade Authority", Icons.Filled.LocalShipping),
    DIGITAL_CIVILIZATION("Digital Civilization", Icons.Filled.Hub),
    DECISION("Decision Authority", Icons.Filled.Psychology),
    KNOWLEDGE("Knowledge Grid", Icons.Filled.AutoStories),
    INNOVATION("Innovation Authority", Icons.Filled.Lightbulb),
    RISK_SHIELD("Risk Shield", Icons.Filled.Shield),
    HEALTH("Health Authority", Icons.Filled.HealthAndSafety),
    COMMAND_TOWER("Supremacy Tower", Icons.Filled.CellTower),
    SOVEREIGNTY("Sovereignty Engine", Icons.Filled.Diamond)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SupremacyPlatformScreen(
    viewModel: SupremacyViewModel,
    onNavigateBack: () -> Unit = {}
) {
    val supremacyCore by viewModel.supremacyCore.collectAsState()
    val governanceEngine by viewModel.governanceEngine.collectAsState()
    val economicCommand by viewModel.economicCommand.collectAsState()
    val supremeOpportunities by viewModel.supremeOpportunities.collectAsState()
    val expansionNetwork by viewModel.expansionNetwork.collectAsState()
    val capitalMatrix by viewModel.capitalMatrix.collectAsState()
    val tradeAuthority by viewModel.tradeAuthority.collectAsState()
    val digitalCivilization by viewModel.digitalCivilization.collectAsState()
    val decisionAuthority by viewModel.decisionAuthority.collectAsState()
    val knowledgeGrid by viewModel.knowledgeGrid.collectAsState()
    val innovationAuthority by viewModel.innovationAuthority.collectAsState()
    val riskShieldSupremacy by viewModel.riskShieldSupremacy.collectAsState()
    val healthAuthority by viewModel.healthAuthority.collectAsState()
    val supremacyCommandTower by viewModel.supremacyCommandTower.collectAsState()
    val sovereigntyEngine by viewModel.sovereigntyEngine.collectAsState()
    val supremacyIndex by viewModel.supremacyIndex.collectAsState()

    val isOperating by viewModel.isOperating.collectAsState()
    val statusMessage by viewModel.statusMessage.collectAsState()
    val telemetryStream by viewModel.telemetryStream.collectAsState()

    var selectedTab by remember { mutableStateOf(SupremacyModuleTab.CORE) }
    var showAddDialog by remember { mutableStateOf(false) }

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(statusMessage) {
        statusMessage?.let {
            snackbarHostState.showSnackbar(it, duration = SnackbarDuration.Short)
            viewModel.clearStatusMessage()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                "VASCS SUPREMACY",
                                style = MaterialTheme.typography.titleLarge.copy(
                                    fontWeight = FontWeight.Black,
                                    letterSpacing = 1.2.sp
                                )
                            )
                            Spacer(Modifier.width(8.dp))
                            Surface(
                                color = Color(0xFFF59E0B).copy(alpha = 0.2f),
                                shape = RoundedCornerShape(4.dp),
                                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFF59E0B))
                            ) {
                                Text(
                                    "CHECKPOINT 24.0",
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        color = Color(0xFFD97706),
                                        fontWeight = FontWeight.Bold
                                    )
                                )
                            }
                        }
                        Text(
                            "Universal Economic Sovereignty Platform",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(
                        onClick = { viewModel.runSupremacyCore() },
                        enabled = !isOperating,
                        modifier = Modifier.testTag("btn_sync_supremacy")
                    ) {
                        if (isOperating) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(20.dp),
                                strokeWidth = 2.dp,
                                color = Color(0xFFF59E0B)
                            )
                        } else {
                            Icon(Icons.Default.Refresh, contentDescription = "Sync Supremacy", tint = Color(0xFFF59E0B))
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .testTag("supremacy_platform_screen"),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Hero Banner: Universal Economic Sovereignty Architecture
            item {
                SupremacyHeroCard(
                    supremacyIndex = supremacyIndex,
                    core = supremacyCore,
                    isOperating = isOperating,
                    onExecuteAll = {
                        viewModel.runSupremacyCore()
                        viewModel.governCivilizations()
                        viewModel.controlEconomicCommand()
                        viewModel.discoverSupremeOpportunities()
                        viewModel.expandNetworks()
                        viewModel.manageCapitalMatrix()
                        viewModel.optimizeTradeAuthority()
                        viewModel.executeDecisionAuthority()
                        viewModel.calculateSupremacyIndex()
                        viewModel.runSovereigntyEngine()
                    }
                )
            }

            // Live Telemetry Marquee
            item {
                LiveTelemetryStreamCard(telemetryStream)
            }

            // Horizontal Tab Navigation for 15 Modules
            item {
                ScrollableTabRow(
                    selectedTabIndex = selectedTab.ordinal,
                    edgePadding = 0.dp,
                    containerColor = Color.Transparent,
                    divider = {}
                ) {
                    SupremacyModuleTab.values().forEach { tab ->
                        Tab(
                            selected = selectedTab == tab,
                            onClick = { selectedTab = tab },
                            text = {
                                Text(
                                    tab.label,
                                    fontWeight = if (selectedTab == tab) FontWeight.Bold else FontWeight.Normal,
                                    fontSize = 13.sp
                                )
                            },
                            icon = {
                                Icon(
                                    tab.icon,
                                    contentDescription = tab.label,
                                    modifier = Modifier.size(18.dp)
                                )
                            },
                            selectedContentColor = Color(0xFFD97706),
                            unselectedContentColor = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            // Content per selected tab
            when (selectedTab) {
                SupremacyModuleTab.CORE -> {
                    item {
                        SupremacyCoreView(
                            core = supremacyCore,
                            onRefresh = { viewModel.runSupremacyCore() },
                            isOperating = isOperating
                        )
                    }
                }
                SupremacyModuleTab.GOVERNANCE -> {
                    item {
                        CivilizationGovernanceView(
                            items = governanceEngine,
                            onGovernAll = { viewModel.governCivilizations() },
                            isOperating = isOperating
                        )
                    }
                }
                SupremacyModuleTab.ECONOMIC_COMMAND -> {
                    item {
                        EconomicCommandView(
                            commands = economicCommand,
                            onOptimize = { viewModel.controlEconomicCommand() },
                            isOperating = isOperating
                        )
                    }
                }
                SupremacyModuleTab.OPPORTUNITY -> {
                    item {
                        SupremeOpportunityView(
                            opportunities = supremeOpportunities,
                            onScan = { viewModel.discoverSupremeOpportunities() },
                            isOperating = isOperating
                        )
                    }
                }
                SupremacyModuleTab.EXPANSION -> {
                    item {
                        ExpansionNetworkView(
                            networks = expansionNetwork,
                            onExpand = { viewModel.expandNetworks() },
                            isOperating = isOperating
                        )
                    }
                }
                SupremacyModuleTab.CAPITAL -> {
                    item {
                        CapitalMatrixView(
                            matrix = capitalMatrix,
                            onRebalance = { viewModel.manageCapitalMatrix() },
                            isOperating = isOperating
                        )
                    }
                }
                SupremacyModuleTab.TRADE -> {
                    item {
                        TradeAuthorityView(
                            authorities = tradeAuthority,
                            onOptimize = { viewModel.optimizeTradeAuthority() },
                            isOperating = isOperating
                        )
                    }
                }
                SupremacyModuleTab.DIGITAL_CIVILIZATION -> {
                    item {
                        DigitalCivilizationView(
                            twins = digitalCivilization
                        )
                    }
                }
                SupremacyModuleTab.DECISION -> {
                    item {
                        DecisionAuthorityView(
                            decisions = decisionAuthority,
                            onExecute = { viewModel.executeDecisionAuthority() },
                            isOperating = isOperating
                        )
                    }
                }
                SupremacyModuleTab.KNOWLEDGE -> {
                    item {
                        KnowledgeGridView(
                            grid = knowledgeGrid
                        )
                    }
                }
                SupremacyModuleTab.INNOVATION -> {
                    item {
                        InnovationAuthorityView(
                            innovations = innovationAuthority
                        )
                    }
                }
                SupremacyModuleTab.RISK_SHIELD -> {
                    item {
                        RiskShieldView(
                            shields = riskShieldSupremacy
                        )
                    }
                }
                SupremacyModuleTab.HEALTH -> {
                    item {
                        HealthAuthorityView(
                            healthItems = healthAuthority,
                            onRecalculate = { viewModel.calculateSupremacyIndex() },
                            isOperating = isOperating
                        )
                    }
                }
                SupremacyModuleTab.COMMAND_TOWER -> {
                    item {
                        SupremacyCommandTowerView(
                            towers = supremacyCommandTower
                        )
                    }
                }
                SupremacyModuleTab.SOVEREIGNTY -> {
                    item {
                        SovereigntyEngineView(
                            pillars = sovereigntyEngine,
                            onEnforce = { viewModel.runSovereigntyEngine() },
                            isOperating = isOperating
                        )
                    }
                }
            }

            // Bottom Evolutionary Chain Badge
            item {
                EvolutionaryChainCard()
            }
        }
    }
}

// -------------------------------------------------------------
// HERO COMPONENT
// -------------------------------------------------------------
@Composable
fun SupremacyHeroCard(
    supremacyIndex: Double,
    core: SupremacyCoreEntity?,
    isOperating: Boolean,
    onExecuteAll: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("supremacy_hero_card"),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF1E1B18)
        ),
        border = androidx.compose.foundation.BorderStroke(
            1.5.dp,
            Brush.linearGradient(
                listOf(Color(0xFFF59E0B), Color(0xFFB45309), Color(0xFF451A03))
            )
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        "VASCS SUPREMACY",
                        style = MaterialTheme.typography.headlineSmall.copy(
                            fontWeight = FontWeight.Black,
                            letterSpacing = 1.5.sp,
                            color = Color(0xFFFBBF24)
                        )
                    )
                    Text(
                        "Universal Economic Sovereignty & Civilization Intelligence",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0xFFD1D5DB)
                    )
                }

                Surface(
                    shape = CircleShape,
                    color = Color(0xFFF59E0B).copy(alpha = 0.15f),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFF59E0B))
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .clip(CircleShape)
                                .background(Color(0xFF10B981))
                        )
                        Text(
                            String.format(Locale.US, "%.4f%%", supremacyIndex),
                            fontWeight = FontWeight.Black,
                            color = Color(0xFFFBBF24),
                            fontSize = 14.sp
                        )
                    }
                }
            }

            // 3 High-level Metric Pillars
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                HeroStatBox(
                    title = "Civilizations",
                    value = "${core?.civilizationsGovernedCount ?: 840}",
                    sub = "Governed Guilds",
                    modifier = Modifier.weight(1f),
                    accent = Color(0xFF38BDF8)
                )
                HeroStatBox(
                    title = "Prosperity Multiplier",
                    value = "${core?.autonomousProsperityMultiplier ?: 52.8}x",
                    sub = "Yield Multiplier",
                    modifier = Modifier.weight(1f),
                    accent = Color(0xFF34D399)
                )
                HeroStatBox(
                    title = "Sovereignty Score",
                    value = "${core?.economicSovereigntyScore?.toInt() ?: 100}/100",
                    sub = "Supreme Autonomy",
                    modifier = Modifier.weight(1f),
                    accent = Color(0xFFFBBF24)
                )
            }

            // Quick Autonomous Execution Button
            Button(
                onClick = onExecuteAll,
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("btn_execute_all_supremacy"),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFD97706)
                ),
                shape = RoundedCornerShape(12.dp),
                enabled = !isOperating
            ) {
                Icon(Icons.Filled.Bolt, contentDescription = null, tint = Color.White)
                Spacer(Modifier.width(8.dp))
                Text(
                    if (isOperating) "ORCHESTRATING CIVILIZATIONS..." else "AUTONOMOUSLY GOVERN & EXPAND ALL CIVILIZATIONS",
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    fontSize = 13.sp
                )
            }
        }
    }
}

@Composable
fun HeroStatBox(
    title: String,
    value: String,
    sub: String,
    modifier: Modifier = Modifier,
    accent: Color
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        color = Color(0xFF27231E),
        border = androidx.compose.foundation.BorderStroke(1.dp, accent.copy(alpha = 0.4f))
    ) {
        Column(
            modifier = Modifier.padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            Text(title, style = MaterialTheme.typography.labelSmall, color = Color(0xFF9CA3AF))
            Text(value, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = accent)
            Text(sub, style = MaterialTheme.typography.labelSmall.copy(fontSize = 9.sp), color = Color(0xFF6B7280))
        }
    }
}

// -------------------------------------------------------------
// LIVE TELEMETRY CARD
// -------------------------------------------------------------
@Composable
fun LiveTelemetryStreamCard(telemetry: List<String>) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF13110E)
        ),
        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF374151))
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(6.dp)
                            .clip(CircleShape)
                            .background(Color(0xFF10B981))
                    )
                    Text(
                        "LIVE SUPREMACY TELEMETRY FEED",
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.sp,
                            color = Color(0xFFF59E0B)
                        )
                    )
                }
                Text(
                    "REAL-TIME",
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontFamily = FontFamily.Monospace,
                        color = Color(0xFF9CA3AF)
                    )
                )
            }

            val topMessage = telemetry.firstOrNull() ?: "Universal Sovereignty System in Steady State Equilibrium."
            Text(
                topMessage,
                style = MaterialTheme.typography.bodySmall.copy(
                    fontFamily = FontFamily.Monospace,
                    fontSize = 11.sp,
                    color = Color(0xFFE5E7EB)
                ),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

// -------------------------------------------------------------
// MODULE 1: SUPREMACY CORE
// -------------------------------------------------------------
@Composable
fun SupremacyCoreView(
    core: SupremacyCoreEntity?,
    onRefresh: () -> Unit,
    isOperating: Boolean
) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        SectionHeader(
            title = "Module 1: Supremacy Core",
            subtitle = "Universal Governance • Economic Coordination • Civilization Control • Infinite Optimization",
            actionLabel = "Synchronize Core",
            onAction = onRefresh,
            isOperating = isOperating
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "Output: Supremacy Intelligence Controller",
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.titleSmall,
                        color = Color(0xFFD97706)
                    )
                    Surface(
                        color = Color(0xFF10B981).copy(alpha = 0.15f),
                        shape = RoundedCornerShape(6.dp)
                    ) {
                        Text(
                            "ACTIVE",
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF10B981)
                            )
                        )
                    }
                }

                Text(
                    core?.supremacyStatus ?: "Universal Economic Sovereignty Active • Supreme Civilizations Governance",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold
                )

                Text(
                    core?.supremacyControllerTelemetry ?: "Unified Sovereign Controller coordinating 840 business civilizations, trillion-dollar liquidity rails, and autonomous prosperity loops seamlessly.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                HorizontalDivider()

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    MetricLabelValue("Infinite Coordination", "${core?.infiniteCoordinationRatePct ?: 99.999}%")
                    MetricLabelValue("Civilization Control Eff.", "${core?.civilizationControlEfficiencyPct ?: 99.998}%")
                    MetricLabelValue("Intelligence Index", "${core?.supremacyIntelligenceIndex ?: 99.9999}%")
                }
            }
        }
    }
}

// -------------------------------------------------------------
// MODULE 2: CIVILIZATION GOVERNANCE ENGINE
// -------------------------------------------------------------
@Composable
fun CivilizationGovernanceView(
    items: List<CivilizationGovernanceEntity>,
    onGovernAll: () -> Unit,
    isOperating: Boolean
) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        SectionHeader(
            title = "Module 2: Civilization Governance Engine",
            subtitle = "Governs Markets, Industries, Economies, Trade Networks, Innovation Systems",
            actionLabel = "Govern All",
            onAction = onGovernAll,
            isOperating = isOperating
        )

        items.forEach { gov ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f))
            ) {
                Column(
                    modifier = Modifier.padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            color = Color(0xFFF59E0B).copy(alpha = 0.2f),
                            shape = RoundedCornerShape(4.dp)
                        ) {
                            Text(
                                gov.domainCategory.uppercase(),
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFFD97706)
                                )
                            )
                        }
                        Text(
                            "Governance Index: ${gov.civilizationGovernanceIndex}%",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF10B981)
                        )
                    }

                    Text(gov.civilizationName, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleSmall)
                    Text(gov.governancePolicy, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            "Stability: ${gov.governanceStabilityPct}%",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            "Active Participants: ${gov.activeParticipantsCount / 1000000}M",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            gov.autonomousControlLevel,
                            style = MaterialTheme.typography.labelSmall,
                            color = Color(0xFF38BDF8)
                        )
                    }
                }
            }
        }
    }
}

// -------------------------------------------------------------
// MODULE 3: UNIVERSAL ECONOMIC COMMAND
// -------------------------------------------------------------
@Composable
fun EconomicCommandView(
    commands: List<EconomicCommandEntity>,
    onOptimize: () -> Unit,
    isOperating: Boolean
) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        SectionHeader(
            title = "Module 3: Universal Economic Command",
            subtitle = "Manages Global Revenue, Global Capital, Global Resources, Global Trade",
            actionLabel = "Deploy Directives",
            onAction = onOptimize,
            isOperating = isOperating
        )

        commands.forEach { cmd ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f))
            ) {
                Column(
                    modifier = Modifier.padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(cmd.resourcePillar, fontWeight = FontWeight.Bold, color = Color(0xFFD97706))
                        Text(
                            "Index: ${cmd.economicPowerIndex}",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF10B981)
                        )
                    }

                    Text(cmd.commandSector, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.SemiBold)

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        MetricLabelValue("Total Value", "$${cmd.totalValueTrillionUsd}T")
                        MetricLabelValue("Directives", "${cmd.commandDirectivesCount}")
                        MetricLabelValue("Velocity", "${cmd.optimizationVelocityPct}%")
                    }

                    Text(
                        "Status: ${cmd.executionStatus}",
                        style = MaterialTheme.typography.labelSmall,
                        color = Color(0xFF38BDF8)
                    )
                }
            }
        }
    }
}

// -------------------------------------------------------------
// MODULE 4: SUPREME OPPORTUNITY ENGINE
// -------------------------------------------------------------
@Composable
fun SupremeOpportunityView(
    opportunities: List<SupremeOpportunityEntity>,
    onScan: () -> Unit,
    isOperating: Boolean
) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        SectionHeader(
            title = "Module 4: Supreme Opportunity Engine",
            subtitle = "Discovers Future Markets, Future Industries, Future Technologies, Future Economies",
            actionLabel = "Scan Horizons",
            onAction = onScan,
            isOperating = isOperating
        )

        opportunities.forEach { opp ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f))
            ) {
                Column(
                    modifier = Modifier.padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Surface(
                            color = Color(0xFF8B5CF6).copy(alpha = 0.2f),
                            shape = RoundedCornerShape(4.dp)
                        ) {
                            Text(
                                opp.discoveryHorizon,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF8B5CF6)
                                )
                            )
                        }
                        Text(
                            "Score: ${opp.supremeOpportunityScore}",
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFFBBF24),
                            style = MaterialTheme.typography.labelSmall
                        )
                    }

                    Text(opp.opportunityTitle, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleSmall)
                    Text(opp.autonomousExecutionVector, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        MetricLabelValue("Addressable TAM", "$${opp.addressableMarketTrillionUsd}T")
                        MetricLabelValue("Maturity", "${opp.timeToMaturityMonths} mo")
                        MetricLabelValue("Capture Conf.", "${opp.captureConfidencePct}%")
                    }
                }
            }
        }
    }
}

// -------------------------------------------------------------
// MODULE 5: UNIVERSAL EXPANSION NETWORK
// -------------------------------------------------------------
@Composable
fun ExpansionNetworkView(
    networks: List<ExpansionNetworkEntity>,
    onExpand: () -> Unit,
    isOperating: Boolean
) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        SectionHeader(
            title = "Module 5: Universal Expansion Network",
            subtitle = "Expands Countries, Regions, Industries, Business Ecosystems",
            actionLabel = "Hyper-Scale",
            onAction = onExpand,
            isOperating = isOperating
        )

        networks.forEach { net ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f))
            ) {
                Column(
                    modifier = Modifier.padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(net.expansionVector, fontWeight = FontWeight.Bold, color = Color(0xFF38BDF8))
                        Text(
                            "Dominance Index: ${net.expansionDominanceIndex}",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF10B981)
                        )
                    }

                    Text(net.territoryOrSector, fontWeight = FontWeight.Bold)

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        MetricLabelValue("Market Share", "${net.sovereignMarketSharePct}%")
                        MetricLabelValue("Active Nodes", "${net.networkNodeDensity}")
                        MetricLabelValue("Growth Yield", "${net.autonomousGrowthYieldPct}%")
                    }
                }
            }
        }
    }
}

// -------------------------------------------------------------
// MODULE 6: SUPREMACY CAPITAL MATRIX
// -------------------------------------------------------------
@Composable
fun CapitalMatrixView(
    matrix: List<CapitalMatrixEntity>,
    onRebalance: () -> Unit,
    isOperating: Boolean
) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        SectionHeader(
            title = "Module 6: Supremacy Capital Matrix",
            subtitle = "Controls Investments, Funds, Assets, Wealth Systems",
            actionLabel = "Quantum Rebalance",
            onAction = onRebalance,
            isOperating = isOperating
        )

        matrix.forEach { cap ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f))
            ) {
                Column(
                    modifier = Modifier.padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(cap.assetClass, fontWeight = FontWeight.Bold, color = Color(0xFFFBBF24))
                        Text(
                            "Dominance Score: ${cap.capitalDominanceScore}",
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF10B981),
                            style = MaterialTheme.typography.labelSmall
                        )
                    }

                    Text(cap.portfolioName, fontWeight = FontWeight.SemiBold)

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        MetricLabelValue("Assets Under Gov.", "$${cap.totalAssetsUnderGovernanceBillionUsd}B")
                        MetricLabelValue("CAGR Yield", "${cap.compoundedAnnualGrowthPct}%")
                        MetricLabelValue("Liquidity Ratio", "${cap.liquidityReserveRatioPct}%")
                    }

                    Text(
                        "Settlement: ${cap.autonomousRebalanceFrequency}",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

// -------------------------------------------------------------
// MODULE 7: UNIVERSAL TRADE AUTHORITY
// -------------------------------------------------------------
@Composable
fun TradeAuthorityView(
    authorities: List<TradeAuthorityEntity>,
    onOptimize: () -> Unit,
    isOperating: Boolean
) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        SectionHeader(
            title = "Module 7: Universal Trade Authority",
            subtitle = "Optimizes Supply Chains, Trade Routes, Global Distribution, Market Access",
            actionLabel = "Clear Corridors",
            onAction = onOptimize,
            isOperating = isOperating
        )

        authorities.forEach { trade ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f))
            ) {
                Column(
                    modifier = Modifier.padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(trade.authorityDimension, fontWeight = FontWeight.Bold, color = Color(0xFF34D399))
                        Text(
                            "Trade Index: ${trade.tradeAuthorityIndex}",
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF10B981),
                            style = MaterialTheme.typography.labelSmall
                        )
                    }

                    Text(trade.corridorName, fontWeight = FontWeight.SemiBold)

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        MetricLabelValue("Annual Flow", "$${trade.annualTradeFlowBillionUsd}B")
                        MetricLabelValue("Latency", "${trade.frictionZeroLatencyMs}ms")
                        MetricLabelValue("Clearance Eff.", "${trade.clearanceEfficiencyPct}%")
                    }

                    Text(
                        "Security: ${trade.tradeSecurityLevel}",
                        style = MaterialTheme.typography.labelSmall,
                        color = Color(0xFF38BDF8)
                    )
                }
            }
        }
    }
}

// -------------------------------------------------------------
// MODULE 8: SUPREMACY DIGITAL CIVILIZATION
// -------------------------------------------------------------
@Composable
fun DigitalCivilizationView(twins: List<DigitalCivilizationEntity>) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        SectionHeader(
            title = "Module 8: Supremacy Digital Civilization",
            subtitle = "Creates Economic Twins, Market Twins, Trade Twins, Civilization Twins",
            actionLabel = null,
            onAction = {},
            isOperating = false
        )

        twins.forEach { twin ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f))
            ) {
                Column(
                    modifier = Modifier.padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(twin.twinType, fontWeight = FontWeight.Bold, color = Color(0xFFF59E0B))
                        Text(
                            "Sim Index: ${twin.civilizationSimulationIndex}",
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF10B981),
                            style = MaterialTheme.typography.labelSmall
                        )
                    }

                    Text(twin.simulationUniverseName, fontWeight = FontWeight.SemiBold)
                    Text(twin.predictiveOutcomeSynthesis, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        MetricLabelValue("Fidelity", "${twin.simulationFidelityPct}%")
                        MetricLabelValue("Ticks/Sec", "${twin.ticksPerSecondMillion}M")
                        MetricLabelValue("Divergence", "${twin.divergenceProbabilityPct}%")
                    }
                }
            }
        }
    }
}

// -------------------------------------------------------------
// MODULE 9: UNIVERSAL DECISION AUTHORITY
// -------------------------------------------------------------
@Composable
fun DecisionAuthorityView(
    decisions: List<DecisionAuthorityEntity>,
    onExecute: () -> Unit,
    isOperating: Boolean
) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        SectionHeader(
            title = "Module 9: Universal Decision Authority",
            subtitle = "Executes Expansion, Pricing, Capital Allocation, Trade Decisions, Innovation Strategy",
            actionLabel = "Execute Directives",
            onAction = onExecute,
            isOperating = isOperating
        )

        decisions.forEach { dec ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f))
            ) {
                Column(
                    modifier = Modifier.padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(dec.decisionDomain, fontWeight = FontWeight.Bold, color = Color(0xFF8B5CF6))
                        Text(
                            "Index: ${dec.decisionAuthorityIndex}",
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF10B981),
                            style = MaterialTheme.typography.labelSmall
                        )
                    }

                    Text(dec.decisionTitle, fontWeight = FontWeight.Bold)
                    Text(dec.operationalDirective, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        MetricLabelValue("Impact", "$${dec.impactMagnitudeTrillionUsd}T")
                        MetricLabelValue("Confidence", "${dec.autonomousExecutionConfidencePct}%")
                        MetricLabelValue("Speed", "${dec.executionSpeedMilliseconds}ms")
                    }
                }
            }
        }
    }
}

// -------------------------------------------------------------
// MODULE 10: SUPREMACY KNOWLEDGE GRID
// -------------------------------------------------------------
@Composable
fun KnowledgeGridView(grid: List<KnowledgeGridEntity>) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        SectionHeader(
            title = "Module 10: Supremacy Knowledge Grid",
            subtitle = "Stores Business Knowledge, Economic Knowledge, Innovation Knowledge, Future Intelligence",
            actionLabel = null,
            onAction = {},
            isOperating = false
        )

        grid.forEach { item ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f))
            ) {
                Column(
                    modifier = Modifier.padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(item.knowledgeDomain, fontWeight = FontWeight.Bold, color = Color(0xFF38BDF8))
                        Text(
                            "Score: ${item.knowledgeSupremacyScore}",
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFFBBF24),
                            style = MaterialTheme.typography.labelSmall
                        )
                    }

                    Text(item.knowledgeMatrixTopic, fontWeight = FontWeight.SemiBold)
                    Text(item.synthesisSummary, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        MetricLabelValue("Encoded Data", "${item.encodedZettabytes} ZB")
                        MetricLabelValue("Neural Fidelity", "${item.neuralFidelityPct}%")
                    }
                }
            }
        }
    }
}

// -------------------------------------------------------------
// MODULE 11: UNIVERSAL INNOVATION AUTHORITY
// -------------------------------------------------------------
@Composable
fun InnovationAuthorityView(innovations: List<InnovationAuthorityEntity>) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        SectionHeader(
            title = "Module 11: Universal Innovation Authority",
            subtitle = "Generates Products, Technologies, Patents, Business Models",
            actionLabel = null,
            onAction = {},
            isOperating = false
        )

        innovations.forEach { inno ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f))
            ) {
                Column(
                    modifier = Modifier.padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(inno.generationType, fontWeight = FontWeight.Bold, color = Color(0xFFF59E0B))
                        Text(
                            "Index: ${inno.innovationAuthorityIndex}",
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF10B981),
                            style = MaterialTheme.typography.labelSmall
                        )
                    }

                    Text(inno.innovationName, fontWeight = FontWeight.Bold)
                    Text("Patent ID: ${inno.patentIdentifier}", style = MaterialTheme.typography.labelSmall, color = Color(0xFF9CA3AF))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        MetricLabelValue("Commercialization", "${inno.commercializationVelocityPct}%")
                        MetricLabelValue("Disruption", "${inno.marketDisruptionMultiplier}x")
                    }

                    Text(
                        "Status: ${inno.deploymentStatus}",
                        style = MaterialTheme.typography.labelSmall,
                        color = Color(0xFF38BDF8)
                    )
                }
            }
        }
    }
}

// -------------------------------------------------------------
// MODULE 12: SUPREMACY RISK SHIELD
// -------------------------------------------------------------
@Composable
fun RiskShieldView(shields: List<RiskShieldSupremacyEntity>) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        SectionHeader(
            title = "Module 12: Supremacy Risk Shield",
            subtitle = "Protects Markets, Capital, Trade, Innovation, Expansion",
            actionLabel = null,
            onAction = {},
            isOperating = false
        )

        shields.forEach { shield ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f))
            ) {
                Column(
                    modifier = Modifier.padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Protected: ${shield.protectedSector}", fontWeight = FontWeight.Bold, color = Color(0xFF10B981))
                        Text(
                            "Protection: ${shield.riskProtectionScore}",
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF10B981),
                            style = MaterialTheme.typography.labelSmall
                        )
                    }

                    Text("Vulnerability Vector: ${shield.potentialVulnerabilityVector}", style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.SemiBold)
                    Text("Defense: ${shield.activeDefenseMechanism}", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        MetricLabelValue("Containment Latency", "${shield.containmentLatencyMicroseconds} μs")
                        MetricLabelValue("Shield Integrity", "${shield.shieldIntegrityPct}%")
                        MetricLabelValue("Barrier Status", shield.status)
                    }
                }
            }
        }
    }
}

// -------------------------------------------------------------
// MODULE 13: UNIVERSAL HEALTH AUTHORITY
// -------------------------------------------------------------
@Composable
fun HealthAuthorityView(
    healthItems: List<HealthAuthorityEntity>,
    onRecalculate: () -> Unit,
    isOperating: Boolean
) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        SectionHeader(
            title = "Module 13: Universal Health Authority",
            subtitle = "Monitors Business Health, Economic Health, Market Health, Growth Health",
            actionLabel = "Synthesize Index",
            onAction = onRecalculate,
            isOperating = isOperating
        )

        healthItems.forEach { health ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f))
            ) {
                Column(
                    modifier = Modifier.padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(health.monitorPillar, fontWeight = FontWeight.Bold, color = Color(0xFF38BDF8))
                        Text(
                            "Health Index: ${health.universalHealthIndex}%",
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF10B981),
                            style = MaterialTheme.typography.labelSmall
                        )
                    }

                    Text(health.diagnosticSynthesis, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        MetricLabelValue("Health Score", "${health.healthScore}%")
                        MetricLabelValue("Benchmark", "${health.targetBenchmark}%")
                        MetricLabelValue("State", health.state)
                    }
                }
            }
        }
    }
}

// -------------------------------------------------------------
// MODULE 14: SUPREMACY COMMAND TOWER
// -------------------------------------------------------------
@Composable
fun SupremacyCommandTowerView(towers: List<SupremacyCommandTowerEntity>) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        SectionHeader(
            title = "Module 14: Supremacy Command Tower",
            subtitle = "Monitors Economies, Industries, Markets, Trade Networks, Innovation Systems, AI Systems",
            actionLabel = null,
            onAction = {},
            isOperating = false
        )

        towers.forEach { tower ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f))
            ) {
                Column(
                    modifier = Modifier.padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Layer: ${tower.monitoredLayer}", fontWeight = FontWeight.Bold, color = Color(0xFFFBBF24))
                        Text(
                            "Score: ${tower.supremacyIntelligenceScore}",
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF10B981),
                            style = MaterialTheme.typography.labelSmall
                        )
                    }

                    Text(tower.nodeIdentifier, fontWeight = FontWeight.Bold)

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        MetricLabelValue("Active Channels", "${tower.activeTelemetryChannels}")
                        MetricLabelValue("Throughput", "${tower.throughputTransactionsPerSec / 1000}k TPS")
                    }

                    Text(
                        tower.globalStatus,
                        style = MaterialTheme.typography.labelSmall,
                        color = Color(0xFF38BDF8)
                    )
                }
            }
        }
    }
}

// -------------------------------------------------------------
// MODULE 15: UNIVERSAL SOVEREIGNTY ENGINE
// -------------------------------------------------------------
@Composable
fun SovereigntyEngineView(
    pillars: List<SovereigntyEngineEntity>,
    onEnforce: () -> Unit,
    isOperating: Boolean
) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        SectionHeader(
            title = "Module 15: Universal Sovereignty Engine",
            subtitle = "Ensures Economic Stability, Continuous Growth, Infinite Expansion, Universal Prosperity",
            actionLabel = "Enforce Sovereignty",
            onAction = onEnforce,
            isOperating = isOperating
        )

        pillars.forEach { pillar ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f))
            ) {
                Column(
                    modifier = Modifier.padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(pillar.guaranteePillar, fontWeight = FontWeight.Bold, color = Color(0xFFD97706))
                        Text(
                            "Sovereignty Index: ${pillar.universalSovereigntyIndex}%",
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF10B981),
                            style = MaterialTheme.typography.labelSmall
                        )
                    }

                    Text("Focus: ${pillar.metricFocus}", fontWeight = FontWeight.SemiBold)
                    Text(pillar.assuranceProtocolSummary, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        MetricLabelValue("Target Score", "${pillar.targetObjectiveScore}%")
                        MetricLabelValue("Stabilization Mult.", "${pillar.stabilizationMultiplier}x")
                        MetricLabelValue("Assurance", pillar.operationalState)
                    }
                }
            }
        }
    }
}

// -------------------------------------------------------------
// HELPER COMPONENTS
// -------------------------------------------------------------
@Composable
fun SectionHeader(
    title: String,
    subtitle: String,
    actionLabel: String?,
    onAction: () -> Unit,
    isOperating: Boolean
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(title, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
            Text(subtitle, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }

        if (actionLabel != null) {
            FilledTonalButton(
                onClick = onAction,
                enabled = !isOperating,
                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
            ) {
                Text(actionLabel, fontSize = 12.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun MetricLabelValue(label: String, value: String) {
    Column {
        Text(label, style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp), color = Color(0xFF9CA3AF))
        Text(value, style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold))
    }
}

@Composable
fun EvolutionaryChainCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF181512))
    ) {
        Column(
            modifier = Modifier.padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(
                "VASCS EVOLUTIONARY LINEAGE",
                style = MaterialTheme.typography.labelSmall.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFF59E0B),
                    letterSpacing = 1.sp
                )
            )
            Text(
                "ERP → AI ERP → AI Operating System → Autonomous Enterprise → Business Singularity → OMEGA → INFINITY → COSMOS → NEXUS → QUANTUM → GENESIS → ASCENSION → OMNIVERSE → ETERNITY → TRANSCENDENCE → SUPREMACY (24.0)",
                style = MaterialTheme.typography.bodySmall.copy(
                    fontFamily = FontFamily.Monospace,
                    fontSize = 10.sp,
                    color = Color(0xFF9CA3AF)
                )
            )
        }
    }
}
