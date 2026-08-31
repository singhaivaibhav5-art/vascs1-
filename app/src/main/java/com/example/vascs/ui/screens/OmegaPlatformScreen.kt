package com.example.vascs.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.vascs.ui.viewmodel.OmegaViewModel

enum class OmegaModule(val title: String, val icon: ImageVector) {
    OMEGA_CORE("Omega Core", Icons.Default.Settings),
    COMMERCE_GRID("Commerce Grid", Icons.Default.Share),
    TRADE_INTELLIGENCE("Trade Intelligence", Icons.Default.Public),
    GOVERNANCE_CENTER("Governance Center", Icons.Default.CheckCircle),
    DEMAND_INTELLIGENCE("Demand Intelligence", Icons.Default.TrendingUp),
    CAPITAL_ENGINE("Capital Engine", Icons.Default.MonetizationOn),
    COMPETITOR_INTELLIGENCE("Competitor Intelligence", Icons.Default.Search),
    SUPPLY_CHAIN_AI("Supply Chain AI", Icons.Default.ShoppingCart),
    OMEGA_TWIN("Omega Twin", Icons.Default.Build),
    REVENUE_INTELLIGENCE("Revenue Intelligence", Icons.Default.AccountBalance),
    STRATEGY_CENTER("Strategy Center", Icons.Default.Star),
    KNOWLEDGE_UNIVERSE("Knowledge Universe", Icons.Default.Info),
    EXECUTION_CENTER("Execution Center", Icons.Default.PlayArrow),
    OMEGA_HEALTH("Omega Health", Icons.Default.Favorite),
    COMMAND_TOWER("Omega Command Tower", Icons.Default.Home)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OmegaPlatformScreen(
    viewModel: OmegaViewModel,
    onBackClick: (() -> Unit)? = null
) {
    var selectedModule by remember { mutableStateOf(OmegaModule.COMMAND_TOWER) }
    val omegaCore by viewModel.omegaCore.collectAsState()
    val tradeIntel by viewModel.tradeIntelligence.collectAsState()
    val competitors by viewModel.competitors.collectAsState()
    val supplyChain by viewModel.supplyChain.collectAsState()
    val capitalEngine by viewModel.capitalEngine.collectAsState()
    val revenueEngine by viewModel.revenueEngine.collectAsState()
    val omegaHealth by viewModel.omegaHealth.collectAsState()
    val omegaIndex by viewModel.omegaIndex.collectAsState()
    val twinScenarios by viewModel.omegaTwinScenarios.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "VASCS OMEGA",
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
                                    text = "CHECKPOINT 14.0",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                    color = MaterialTheme.colorScheme.onPrimaryContainer
                                )
                            }
                        }
                        Text(
                            text = "Universal AI Commerce Platform | Index: ${String.format("%.1f", omegaIndex)}",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                },
                navigationIcon = {
                    if (onBackClick != null) {
                        IconButton(onClick = onBackClick) {
                            Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                        }
                    }
                },
                actions = {
                    IconButton(
                        onClick = { viewModel.runOmegaCore() },
                        modifier = Modifier.testTag("run_omega_core_button")
                    ) {
                        Icon(Icons.Default.Refresh, contentDescription = "Run Omega Core", tint = MaterialTheme.colorScheme.primary)
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
        ) {
            // Module Navigation Scroll Row
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f))
                    .padding(vertical = 8.dp, horizontal = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(OmegaModule.values()) { module ->
                    val isSelected = selectedModule == module
                    FilterChip(
                        selected = isSelected,
                        onClick = { selectedModule = module },
                        label = {
                            Text(
                                text = module.title,
                                fontSize = 12.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                            )
                        },
                        leadingIcon = {
                            Icon(
                                imageVector = module.icon,
                                contentDescription = module.title,
                                modifier = Modifier.size(16.dp)
                            )
                        },
                        modifier = Modifier.testTag("module_tab_${module.name.lowercase()}")
                    )
                }
            }

            // Main Content Body based on selected module
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
                    .padding(16.dp)
            ) {
                when (selectedModule) {
                    OmegaModule.COMMAND_TOWER -> OmegaCommandTowerView(viewModel, omegaIndex, omegaCore, omegaHealth)
                    OmegaModule.OMEGA_CORE -> OmegaCoreView(viewModel, omegaCore)
                    OmegaModule.COMMERCE_GRID -> UniversalCommerceGridView()
                    OmegaModule.TRADE_INTELLIGENCE -> GlobalTradeIntelligenceView(viewModel, tradeIntel)
                    OmegaModule.GOVERNANCE_CENTER -> AiGovernanceCenterView()
                    OmegaModule.DEMAND_INTELLIGENCE -> GlobalDemandEngineView()
                    OmegaModule.CAPITAL_ENGINE -> AiCapitalEngineView(viewModel, capitalEngine)
                    OmegaModule.COMPETITOR_INTELLIGENCE -> GlobalCompetitorView(competitors)
                    OmegaModule.SUPPLY_CHAIN_AI -> AutonomousSupplyChainView(viewModel, supplyChain)
                    OmegaModule.OMEGA_TWIN -> OmegaDigitalTwinView(viewModel, twinScenarios)
                    OmegaModule.REVENUE_INTELLIGENCE -> GlobalRevenueEngineView(revenueEngine)
                    OmegaModule.STRATEGY_CENTER -> OmegaStrategyEngineView()
                    OmegaModule.KNOWLEDGE_UNIVERSE -> GlobalKnowledgeUniverseView()
                    OmegaModule.EXECUTION_CENTER -> OmegaExecutionEngineView()
                    OmegaModule.OMEGA_HEALTH -> OmegaHealthSystemView(viewModel, omegaHealth)
                }
            }
        }
    }
}

@Composable
fun OmegaCommandTowerView(
    viewModel: OmegaViewModel,
    omegaIndex: Double,
    omegaCore: List<com.example.vascs.data.model.OmegaCoreEntity>,
    omegaHealth: List<com.example.vascs.data.model.OmegaHealthEntity>
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "OMEGA COMMAND TOWER",
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f)
                            )
                            Text(
                                text = "Omega Intelligence Index",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        }
                        Text(
                            text = "${String.format("%.1f", omegaIndex)}",
                            fontSize = 32.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "Autonomous System Status: GOVERNING ALL 15 COMMERCE MODULES",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Button(
                            onClick = { viewModel.runOmegaCore() },
                            modifier = Modifier.weight(1f).testTag("command_tower_sync")
                        ) {
                            Text("Synchronize Grid", fontSize = 12.sp)
                        }
                        Button(
                            onClick = { viewModel.calculateOmegaHealth() },
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary),
                            modifier = Modifier.weight(1f).testTag("command_tower_health")
                        ) {
                            Text("Recalculate Health", fontSize = 12.sp)
                        }
                    }
                }
            }
        }

        item {
            Text(
                text = "Autonomous System Monitor",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
        }

        val systemList = listOf(
            "AI Brain Governance" to "Active & Reasoning",
            "AI Board Executive" to "Unanimous Consensus",
            "Autonomous Trade Network" to "Cross-Border Active",
            "Dealer Network Grid" to "Connected (1,240 Dealers)",
            "Universal Commerce Grid" to "Synchronized",
            "Global Revenue Engine" to "Yield Maximized",
            "Expansion Engine" to "USA & UAE Launch Phase"
        )

        items(systemList) { (system, status) ->
            Card(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(10.dp)
                                .clip(CircleShape)
                                .background(Color(0xFF4CAF50))
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(text = system, fontWeight = FontWeight.SemiBold)
                    }
                    Text(text = status, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.primary)
                }
            }
        }
    }
}

@Composable
fun OmegaCoreView(
    viewModel: OmegaViewModel,
    omegaCore: List<com.example.vascs.data.model.OmegaCoreEntity>
) {
    LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        item {
            Text("MODULE 1: OMEGA CORE", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Text("Global Coordination • Autonomous Governance • Intelligence Distribution • Decision Supervision", style = MaterialTheme.typography.bodySmall)
        }

        items(omegaCore) { core ->
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("System Status: ${core.systemStatus}", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("Active Subsystems: ${core.activeSubsystemsCount}", style = MaterialTheme.typography.bodyMedium)
                    Text("Omega Index: ${core.omegaIndex}", style = MaterialTheme.typography.bodyMedium)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Global Strategy Directive:", fontWeight = FontWeight.SemiBold)
                    Text(core.globalStrategyDirective, style = MaterialTheme.typography.bodyMedium)
                }
            }
        }

        item {
            Button(
                onClick = { viewModel.runOmegaCore() },
                modifier = Modifier.fillMaxWidth().testTag("execute_omega_core")
            ) {
                Text("Execute Global Strategy Cycle")
            }
        }
    }
}

@Composable
fun UniversalCommerceGridView() {
    val entities = listOf("Manufacturers", "Suppliers", "Distributors", "Dealers", "Retailers", "Customers")
    LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        item {
            Text("MODULE 2: UNIVERSAL AI COMMERCE GRID", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Text("Connects all ecosystem participants into a unified autonomous commerce network.", style = MaterialTheme.typography.bodySmall)
        }

        items(entities) { entity ->
            Card(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = entity, fontWeight = FontWeight.SemiBold)
                    Text(text = "CONNECTED & AUTONOMOUS", fontSize = 11.sp, color = Color(0xFF2E7D32), fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
fun GlobalTradeIntelligenceView(
    viewModel: OmegaViewModel,
    tradeList: List<com.example.vascs.data.model.GlobalTradeDataEntity>
) {
    LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text("MODULE 3: GLOBAL TRADE INTELLIGENCE", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    Text("Imports, Exports, Country Demand & Trade Routes", style = MaterialTheme.typography.bodySmall)
                }
                Button(
                    onClick = { viewModel.analyzeGlobalTrade() },
                    modifier = Modifier.testTag("analyze_trade_btn")
                ) {
                    Text("Analyze Trade")
                }
            }
        }

        items(tradeList) { item ->
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Target Country: ${item.targetCountry}", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                    Text("Trade Route: ${item.tradeRoute}", style = MaterialTheme.typography.bodyMedium)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("Optimal Category: ${item.optimalCategory}", style = MaterialTheme.typography.bodySmall)
                    Text("Projected Volume: ${item.projectedVolumePcs} pcs | Demand Score: ${item.demandScore}/100", fontSize = 12.sp)
                }
            }
        }
    }
}

@Composable
fun AiGovernanceCenterView() {
    var selectedLevel by remember { mutableStateOf("Fully Autonomous") }
    LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        item {
            Text("MODULE 4: AI GOVERNANCE SYSTEM", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Text("Controls Automation Policies, Risk Policies, Approval Rules & Execution Rules", style = MaterialTheme.typography.bodySmall)
        }

        item {
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Autonomous Governance Level", fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(8.dp))
                    listOf("Manual", "Semi Autonomous", "Fully Autonomous").forEach { level ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth().clickable { selectedLevel = level }.padding(vertical = 4.dp)
                        ) {
                            RadioButton(
                                selected = selectedLevel == level,
                                onClick = { selectedLevel = level }
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(level, fontWeight = FontWeight.Medium)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun GlobalDemandEngineView() {
    val horizons = listOf("1 Month", "3 Months", "1 Year", "5 Years")
    var selectedHorizon by remember { mutableStateOf("1 Year") }
    LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        item {
            Text("MODULE 5: GLOBAL DEMAND ENGINE", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Text("Predictive forecasting across Countries, States, Cities, Dealers, and Products", style = MaterialTheme.typography.bodySmall)
        }

        item {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                horizons.forEach { h ->
                    FilterChip(
                        selected = selectedHorizon == h,
                        onClick = { selectedHorizon = h },
                        label = { Text(h) }
                    )
                }
            }
        }

        item {
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Demand Horizon Forecast ($selectedHorizon)", fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("• North America Bridal Demand: +48% YoY growth")
                    Text("• Middle East Festival Zari Demand: +62% YoY growth")
                    Text("• Domestic Tier-1 Wholesale Demand: +34% YoY growth")
                }
            }
        }
    }
}

@Composable
fun AiCapitalEngineView(
    viewModel: OmegaViewModel,
    capitalList: List<com.example.vascs.data.model.CapitalManagementEntity>
) {
    LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text("MODULE 6: AI CAPITAL MANAGEMENT", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    Text("Investments, Expansion, Inventory & Marketing Budgets", style = MaterialTheme.typography.bodySmall)
                }
                Button(onClick = { viewModel.manageCapital() }) {
                    Text("Deploy Capital")
                }
            }
        }

        items(capitalList) { item ->
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Category: ${item.allocationCategory}", fontWeight = FontWeight.Bold)
                    Text("Allocated Budget: ₹${String.format("%,.0f", item.allocatedBudgetInr)}", color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
                    Text("Projected ROI: ${item.projectedRoiPct}% | Risk: ${item.riskLevel}", fontSize = 12.sp)
                }
            }
        }
    }
}

@Composable
fun GlobalCompetitorView(competitors: List<com.example.vascs.data.model.CompetitorIntelligenceEntity>) {
    LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        item {
            Text("MODULE 7: GLOBAL COMPETITOR NETWORK", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Text("Tracks competitor brands, products, prices, and identifies market gap opportunities.", style = MaterialTheme.typography.bodySmall)
        }

        items(competitors) { comp ->
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(comp.competitorName, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                    Text("Region: ${comp.primaryRegion} | Market Share: ${comp.marketSharePct}%", style = MaterialTheme.typography.bodySmall)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("Opportunity Gap: ${comp.competitiveGapOpportunity}", fontSize = 12.sp)
                }
            }
        }
    }
}

@Composable
fun AutonomousSupplyChainView(
    viewModel: OmegaViewModel,
    supplyChain: List<com.example.vascs.data.model.SupplyChainAiEntity>
) {
    LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text("MODULE 8: AUTONOMOUS SUPPLY CHAIN", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    Text("Procurement, Warehousing, Logistics & Distribution Optimization", style = MaterialTheme.typography.bodySmall)
                }
                Button(onClick = { viewModel.optimizeSupplyChain() }) {
                    Text("Optimize All")
                }
            }
        }

        items(supplyChain) { item ->
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(item.logisticsNode, fontWeight = FontWeight.Bold)
                    Text("Efficiency: ${String.format("%.1f", item.efficiencyScorePct)}% | Cost Saved: ${item.costReductionPct}%", color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
                    Text(item.bottleneckAlert, fontSize = 12.sp)
                }
            }
        }
    }
}

@Composable
fun OmegaDigitalTwinView(
    viewModel: OmegaViewModel,
    twinScenarios: List<com.example.vascs.data.model.OmegaTwinEntity>
) {
    LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text("MODULE 9: OMEGA DIGITAL TWIN", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    Text("Business, Market, Dealer & Customer Digital Replicas for Strategy Simulation", style = MaterialTheme.typography.bodySmall)
                }
                Button(onClick = { viewModel.simulateOmegaTwin() }) {
                    Text("Run Simulation")
                }
            }
        }

        items(twinScenarios) { twin ->
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Replica: ${twin.replicaType}", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                    Text("Fidelity Score: ${twin.fidelityScorePct}% | Forecasted Growth: ${twin.forecastedGrowthMultiplier}x", style = MaterialTheme.typography.bodyMedium)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("Strategic Insight: ${twin.strategicInsight}", fontSize = 12.sp)
                }
            }
        }
    }
}

@Composable
fun GlobalRevenueEngineView(revenueList: List<com.example.vascs.data.model.RevenueEngineEntity>) {
    LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        item {
            Text("MODULE 10: GLOBAL REVENUE ENGINE", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Text("Tracks Revenue Streams, Profit Margins, Growth Rates & Profit Centers", style = MaterialTheme.typography.bodySmall)
        }

        items(revenueList) { rev ->
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(rev.streamName, fontWeight = FontWeight.Bold)
                    Text("Current Revenue: ₹${String.format("%,.0f", rev.currentRevenueInr)}", color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
                    Text("Profit Margin: ${rev.profitMarginPct}% | Growth Rate: +${rev.growthRatePct}%", fontSize = 12.sp)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("Optimization Plan: ${rev.optimizationDirective}", fontSize = 12.sp)
                }
            }
        }
    }
}

@Composable
fun OmegaStrategyEngineView() {
    LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        item {
            Text("MODULE 11: OMEGA STRATEGY ENGINE", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Text("Creates Growth, Expansion, Market & Innovation Strategies across 1Y, 3Y, 5Y, 10Y horizons.", style = MaterialTheme.typography.bodySmall)
        }

        item {
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("1-Year Objective: Autonomous Market Penetration across Tier-1 Cities", fontWeight = FontWeight.Bold)
                    Text("3-Year Objective: Dominant Global Export Hub in USA, UAE & Europe", fontWeight = FontWeight.Bold)
                    Text("5-Year Objective: Universal Cross-Industry AI Commerce Infrastructure", fontWeight = FontWeight.Bold)
                    Text("10-Year Objective: VASCS INFINITY Singularity Platform", fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
fun GlobalKnowledgeUniverseView() {
    LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        item {
            Text("MODULE 12: GLOBAL KNOWLEDGE UNIVERSE", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Text("Centralized knowledge store for Business, Dealer, Market, Customer & Trade Data.", style = MaterialTheme.typography.bodySmall)
        }

        val knowledgeNodes = listOf("Business Knowledge Graph", "Dealer Behavior Patterns", "Market Pricing Intelligence", "Customer Affinity Profiles", "Global Trade Policy Engine")

        items(knowledgeNodes) { node ->
            Card(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(node, fontWeight = FontWeight.SemiBold)
                    Text("INDEXED & REASONING READY", fontSize = 11.sp, color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
fun OmegaExecutionEngineView() {
    LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        item {
            Text("MODULE 13: OMEGA EXECUTION ENGINE", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Text("Autonomous execution of Campaigns, Dealer Programs, Expansion Plans, Pricing & Inventory", style = MaterialTheme.typography.bodySmall)
        }

        item {
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Active Autonomous Executions", fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("• Price Optimization: +8% adjustment on Bridal Silk (Executed)")
                    Text("• WhatsApp Broadcast: 5,000 Dealers notified (Completed)")
                    Text("• Inventory Dispatch: Auto-routed 1,200 sarees to Delhi Hub (In Transit)")
                }
            }
        }
    }
}

@Composable
fun OmegaHealthSystemView(
    viewModel: OmegaViewModel,
    healthList: List<com.example.vascs.data.model.OmegaHealthEntity>
) {
    LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text("MODULE 14: OMEGA HEALTH SYSTEM", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    Text("Commerce, Dealer, Market, Inventory & Finance Health Scores", style = MaterialTheme.typography.bodySmall)
                }
                Button(onClick = { viewModel.calculateOmegaHealth() }) {
                    Text("Recalculate Health")
                }
            }
        }

        items(healthList) { health ->
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(health.healthDomain, fontWeight = FontWeight.Bold)
                    Text("Health Score: ${String.format("%.1f", health.score)}/100", color = Color(0xFF2E7D32), fontWeight = FontWeight.ExtraBold)
                    Text("Status: ${health.statusGrade} | Risk: ${health.riskFactor}", fontSize = 12.sp)
                }
            }
        }
    }
}
