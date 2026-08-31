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
import com.example.vascs.ui.viewmodel.QuantumViewModel

enum class QuantumModule(val title: String, val icon: ImageVector) {
    QUANTUM_CORE("Quantum Core", Icons.Default.AllInclusive),
    FUTURE_ENGINE("Future Engine", Icons.Default.Timeline),
    SIMULATION_NETWORK("Simulation Network", Icons.Default.Speed),
    EVOLUTION_ENGINE("Evolution Engine", Icons.Default.AutoAwesome),
    OPPORTUNITY_QUANTUM("Opportunity Quantum", Icons.Default.TrendingUp),
    MARKET_QUANTUM("Market Quantum", Icons.Default.QueryStats),
    DECISION_MATRIX("Decision Matrix", Icons.Default.AccountTree),
    QUANTUM_TWIN("Quantum Twin", Icons.Default.DeviceHub),
    ECONOMIC_QUANTUM("Economic Quantum", Icons.Default.MonetizationOn),
    REVENUE_QUANTUM("Revenue Quantum", Icons.Default.Savings),
    KNOWLEDGE_QUANTUM("Knowledge Quantum", Icons.Default.Psychology),
    QUANTUM_RESEARCH("Quantum Research", Icons.Default.Science),
    RISK_QUANTUM("Risk Quantum", Icons.Default.Security),
    QUANTUM_HEALTH("Quantum Health", Icons.Default.HealthAndSafety),
    COMMAND_TOWER("Quantum Command Tower", Icons.Default.Podcasts)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuantumPlatformScreen(
    viewModel: QuantumViewModel,
    onBackClick: (() -> Unit)? = null
) {
    var selectedModule by remember { mutableStateOf(QuantumModule.QUANTUM_CORE) }

    val futureScenarios by viewModel.futureScenarios.collectAsState()
    val simulations by viewModel.simulations.collectAsState()
    val evolutionLogs by viewModel.evolutionLogs.collectAsState()
    val opportunities by viewModel.opportunities.collectAsState()
    val marketPredictions by viewModel.marketPredictions.collectAsState()
    val decisionMatrix by viewModel.decisionMatrix.collectAsState()
    val riskMatrix by viewModel.riskMatrix.collectAsState()
    val quantumHealthList by viewModel.quantumHealthList.collectAsState()
    val evolutionScore by viewModel.evolutionScore.collectAsState()
    val quantumIndex by viewModel.quantumIndex.collectAsState()
    val isSimulating by viewModel.isSimulating.collectAsState()
    val telemetryLog by viewModel.quantumTelemetryLog.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "VASCS QUANTUM",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Surface(
                                shape = RoundedCornerShape(12.dp),
                                color = MaterialTheme.colorScheme.tertiaryContainer
                            ) {
                                Text(
                                    text = "CHECKPOINT 18.0",
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.onTertiaryContainer,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                        Text(
                            text = "Predictive Intelligence Platform • Multi-Future Optimization",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                },
                navigationIcon = {
                    if (onBackClick != null) {
                        IconButton(onClick = onBackClick, modifier = Modifier.testTag("quantum_back_btn")) {
                            Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                        }
                    }
                },
                actions = {
                    IconButton(
                        onClick = { viewModel.generateFutureScenarios() },
                        modifier = Modifier.testTag("refresh_quantum_scenarios")
                    ) {
                        Icon(Icons.Default.Refresh, contentDescription = "Refresh Quantum Engine")
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
            // Quantum Vision Banner
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 6.dp),
                shape = RoundedCornerShape(14.dp),
                color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.6f)
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "QUANTUM VISION",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            text = "Predict Before It Happens • Optimize Before It Fails • Learn Before It Is Needed • Grow Before Demand Appears",
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.Medium,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Column(horizontalAlignment = Alignment.End) {
                        Text(
                            text = "INDEX: $quantumIndex%",
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.ExtraBold,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            text = "EVOLUTION: $evolutionScore%",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.secondary
                        )
                    }
                }
            }

            // Horizontal Module Navigation Chips
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp),
                contentPadding = PaddingValues(horizontal = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(QuantumModule.values()) { module ->
                    val isSelected = selectedModule == module
                    FilterChip(
                        selected = isSelected,
                        onClick = { selectedModule = module },
                        leadingIcon = {
                            Icon(
                                imageVector = module.icon,
                                contentDescription = module.title,
                                modifier = Modifier.size(18.dp)
                            )
                        },
                        label = {
                            Text(
                                text = module.title,
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                            )
                        },
                        modifier = Modifier.testTag("quantum_chip_${module.name.lowercase()}")
                    )
                }
            }

            // Main Content Area
            Box(modifier = Modifier.weight(1f)) {
                when (selectedModule) {
                    QuantumModule.QUANTUM_CORE -> QuantumCoreView(
                        quantumIndex = quantumIndex,
                        evolutionScore = evolutionScore,
                        isSimulating = isSimulating,
                        telemetryLog = telemetryLog,
                        onRunSimulations = { viewModel.runQuantumSimulation() },
                        onGenerateFutures = { viewModel.generateFutureScenarios() },
                        onEvolveAi = { viewModel.recordEvolution() }
                    )
                    QuantumModule.FUTURE_ENGINE -> FutureEngineView(
                        scenarios = futureScenarios,
                        onRegenerate = { viewModel.generateFutureScenarios() }
                    )
                    QuantumModule.SIMULATION_NETWORK -> SimulationNetworkView(
                        simulations = simulations,
                        onRunSimulation = { viewModel.runQuantumSimulation() }
                    )
                    QuantumModule.EVOLUTION_ENGINE -> EvolutionEngineView(
                        evolutionLogs = evolutionLogs,
                        evolutionScore = evolutionScore,
                        onTriggerEvolution = { viewModel.recordEvolution() }
                    )
                    QuantumModule.OPPORTUNITY_QUANTUM -> OpportunityQuantumView(
                        opportunities = opportunities,
                        onScanOpportunities = { viewModel.detectFutureOpportunities() }
                    )
                    QuantumModule.MARKET_QUANTUM -> MarketQuantumView(
                        predictions = marketPredictions,
                        onPredictMarkets = { viewModel.predictMarketFuture() }
                    )
                    QuantumModule.DECISION_MATRIX -> DecisionMatrixView(
                        decisions = decisionMatrix,
                        onOptimizeMatrix = { viewModel.calculateDecisionMatrix() }
                    )
                    QuantumModule.QUANTUM_TWIN -> QuantumTwinView()
                    QuantumModule.ECONOMIC_QUANTUM -> EconomicQuantumView()
                    QuantumModule.REVENUE_QUANTUM -> RevenueQuantumView()
                    QuantumModule.KNOWLEDGE_QUANTUM -> KnowledgeQuantumView()
                    QuantumModule.QUANTUM_RESEARCH -> QuantumResearchView()
                    QuantumModule.RISK_QUANTUM -> RiskQuantumView(
                        risks = riskMatrix,
                        onScanRisks = { viewModel.recordRisk() }
                    )
                    QuantumModule.QUANTUM_HEALTH -> QuantumHealthView(
                        healthList = quantumHealthList,
                        onRecalculate = { viewModel.calculateQuantumIndex() }
                    )
                    QuantumModule.COMMAND_TOWER -> QuantumCommandTowerView(
                        quantumIndex = quantumIndex,
                        evolutionScore = evolutionScore,
                        scenarios = futureScenarios,
                        decisions = decisionMatrix,
                        telemetryLog = telemetryLog
                    )
                }
            }
        }
    }
}

// -------------------------------------------------------------
// MODULE 1: QUANTUM CORE
// -------------------------------------------------------------
@Composable
fun QuantumCoreView(
    quantumIndex: Double,
    evolutionScore: Double,
    isSimulating: Boolean,
    telemetryLog: List<String>,
    onRunSimulations: () -> Unit,
    onGenerateFutures: () -> Unit,
    onEvolveAi: () -> Unit
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
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "QUANTUM CORE ENGINE",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "Autonomous Future-State Prediction & Decision Synthesis",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        Surface(
                            shape = CircleShape,
                            color = MaterialTheme.colorScheme.primary
                        ) {
                            Text(
                                text = "ACTIVE",
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onPrimary,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        QuantumStatBox(title = "Quantum Index", value = "$quantumIndex%", color = MaterialTheme.colorScheme.primary)
                        QuantumStatBox(title = "AI Evolution", value = "$evolutionScore%", color = MaterialTheme.colorScheme.secondary)
                        QuantumStatBox(title = "Parallel Futures", value = "50M+", color = MaterialTheme.colorScheme.tertiary)
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "CORE RESPONSIBILITIES",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        QuantumBadge(text = "Future Prediction", modifier = Modifier.weight(1f))
                        QuantumBadge(text = "Decision Optimization", modifier = Modifier.weight(1f))
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        QuantumBadge(text = "Self Learning", modifier = Modifier.weight(1f))
                        QuantumBadge(text = "Intelligence Expansion", modifier = Modifier.weight(1f))
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Button(
                            onClick = onGenerateFutures,
                            modifier = Modifier
                                .weight(1f)
                                .testTag("btn_generate_futures"),
                            enabled = !isSimulating
                        ) {
                            Icon(Icons.Default.Timeline, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Generate 5 Futures", style = MaterialTheme.typography.labelMedium)
                        }
                        FilledTonalButton(
                            onClick = onRunSimulations,
                            modifier = Modifier
                                .weight(1f)
                                .testTag("btn_run_simulations"),
                            enabled = !isSimulating
                        ) {
                            Icon(Icons.Default.Speed, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Run Simulations", style = MaterialTheme.typography.labelMedium)
                        }
                    }
                }
            }
        }

        item {
            Text(
                text = "REAL-TIME QUANTUM TELEMETRY STREAM",
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
        }

        items(telemetryLog) { log ->
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp),
                color = MaterialTheme.colorScheme.surface,
                shadowElevation = 1.dp
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.primary)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = log,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }
    }
}

// -------------------------------------------------------------
// MODULE 2: MULTI-FUTURE ENGINE
// -------------------------------------------------------------
@Composable
fun FutureEngineView(
    scenarios: List<FutureEngineEntity>,
    onRegenerate: () -> Unit
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
                        text = "MULTI-FUTURE ENGINE",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Simulating Futures A, B, C, D, E • Calculating Optimal Path",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Button(
                    onClick = onRegenerate,
                    modifier = Modifier.testTag("regenerate_futures_btn")
                ) {
                    Icon(Icons.Default.AutoMode, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Resimulate")
                }
            }
        }

        items(scenarios) { scenario ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (scenario.isBestPath) MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.7f) else MaterialTheme.colorScheme.surfaceVariant
                ),
                border = if (scenario.isBestPath) CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(MaterialTheme.colorScheme.primary)) else null
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = scenario.futurePathName,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.ExtraBold,
                                color = if (scenario.isBestPath) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "•  ${scenario.trajectoryDescription}",
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                        if (scenario.isBestPath) {
                            Surface(
                                shape = RoundedCornerShape(12.dp),
                                color = MaterialTheme.colorScheme.primary
                            ) {
                                Text(
                                    text = "★ BEST FUTURE PATH",
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.onPrimary,
                                    fontWeight = FontWeight.ExtraBold
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text("Probability", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            Text("${scenario.probabilityScorePct}%", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold)
                        }
                        Column {
                            Text("Growth Forecast", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            Text("${scenario.growthForecastMultiplier}x", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                        }
                        Column {
                            Text("Revenue Est.", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            Text("$${scenario.revenueProjectionBillionUsd}B", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold)
                        }
                        Column {
                            Text("Risk Factor", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            Text("${scenario.riskFactorScore}%", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold, color = if (scenario.riskFactorScore > 15) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.secondary)
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = scenario.strategicRecommendation,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }
    }
}

// -------------------------------------------------------------
// MODULE 3: QUANTUM SIMULATION NETWORK
// -------------------------------------------------------------
@Composable
fun SimulationNetworkView(
    simulations: List<SimulationNetworkEntity>,
    onRunSimulation: () -> Unit
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
                        text = "QUANTUM SIMULATION NETWORK",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "50M+ Iterations across Growth, Market, Economy & Supply",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                FilledTonalButton(
                    onClick = onRunSimulation,
                    modifier = Modifier.testTag("run_quantum_sim_btn")
                ) {
                    Icon(Icons.Default.PlayArrow, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Execute Sim")
                }
            }
        }

        items(simulations) { sim ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = sim.simulationType,
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            text = "${sim.iterationsRun / 1000000}M Iterations",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    Text(
                        text = sim.simulationTitle,
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Text(
                            text = "Success Probability: ${sim.successProbabilityPct}%",
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            text = "Projected Growth: +${sim.projectedGrowthPct}%",
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.4f),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "⚠️ Detected Vulnerability: ${sim.vulnerabilityDetected}",
                            modifier = Modifier.padding(8.dp),
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onErrorContainer
                        )
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.4f),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "🛡️ Automated Mitigation: ${sim.automatedMitigation}",
                            modifier = Modifier.padding(8.dp),
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                }
            }
        }
    }
}

// -------------------------------------------------------------
// MODULE 4: SELF EVOLVING AI / EVOLUTION ENGINE
// -------------------------------------------------------------
@Composable
fun EvolutionEngineView(
    evolutionLogs: List<EvolutionEngineEntity>,
    evolutionScore: Double,
    onTriggerEvolution: () -> Unit
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
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.6f))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "SELF EVOLVING AI CAPABILITY",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.secondary
                    )
                    Text(
                        text = "Learn • Adapt • Improve • Expand Autonomous Architecture",
                        style = MaterialTheme.typography.bodySmall
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text("AI EVOLUTION SCORE", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold)
                            Text("$evolutionScore%", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.ExtraBold, color = MaterialTheme.colorScheme.secondary)
                        }
                        Button(
                            onClick = onTriggerEvolution,
                            modifier = Modifier.testTag("trigger_ai_evolution_btn")
                        ) {
                            Icon(Icons.Default.AutoAwesome, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Trigger Self Upgrade")
                        }
                    }
                }
            }
        }

        item {
            Text(
                text = "AUTONOMOUS EVOLUTION & LEARNING LOGS",
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
        }

        items(evolutionLogs) { log ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(log.agentOrSubsystem, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                        Text("${log.learningIterationsCompleted / 1000000}M Iterations", style = MaterialTheme.typography.labelSmall)
                    }
                    Text("Capability: ${log.evolutionaryCapability}", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.primary)
                    Spacer(modifier = Modifier.height(6.dp))
                    Text("Emergent Behavior: ${log.emergentBehaviorDiscovered}", style = MaterialTheme.typography.bodySmall)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("Upgrade Action: ${log.autonomousSelfUpgradeAction}", style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.secondary)
                }
            }
        }
    }
}

// -------------------------------------------------------------
// MODULE 5: OPPORTUNITY QUANTUM
// -------------------------------------------------------------
@Composable
fun OpportunityQuantumView(
    opportunities: List<OpportunityQuantumEntity>,
    onScanOpportunities: () -> Unit
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
                        text = "OPPORTUNITY QUANTUM",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Hidden Markets • Future Trends • Emerging Industries • New Revenues",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Button(
                    onClick = onScanOpportunities,
                    modifier = Modifier.testTag("scan_quantum_opps_btn")
                ) {
                    Icon(Icons.Default.Search, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Scan Quantum")
                }
            }
        }

        items(opportunities) { opp ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = MaterialTheme.colorScheme.primaryContainer
                    ) {
                        Text(
                            text = opp.detectionType,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(opp.title, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Value: $${opp.estimatedEconomicValueBillionUsd}B USD", style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                        Text("Horizon: ${opp.timeToManifestHorizonMonths} Mo", style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.SemiBold)
                        Text("Readiness: ${opp.strategicReadinessPct}%", style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.SemiBold)
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Text("Directive: ${opp.actionDirective}", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
        }
    }
}

// -------------------------------------------------------------
// MODULE 6: MARKET QUANTUM
// -------------------------------------------------------------
@Composable
fun MarketQuantumView(
    predictions: List<MarketQuantumEntity>,
    onPredictMarkets: () -> Unit
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
                        text = "MARKET QUANTUM",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Predicting Consumer Intent • Market Signals • Trend Acceleration",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                FilledTonalButton(
                    onClick = onPredictMarkets,
                    modifier = Modifier.testTag("predict_market_quantum_btn")
                ) {
                    Icon(Icons.Default.QueryStats, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Forecast")
                }
            }
        }

        items(predictions) { pred ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text(pred.sectorOrRegion, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                    Text("Dimension: ${pred.marketDimension}", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.primary)
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Index: ${pred.marketPredictionIndexPct}%", style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Bold)
                        Text("Intent Velocity: ${pred.intentVelocityScore}/100", style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Bold)
                        Text("Demand Surge: ${pred.forecastedDemandSurgeMultiplier}x", style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Text("Insight: ${pred.predictiveSignalInsight}", style = MaterialTheme.typography.bodySmall)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("Allocation Rule: ${pred.autoAllocationRule}", style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.secondary)
                }
            }
        }
    }
}

// -------------------------------------------------------------
// MODULE 7: DECISION MATRIX
// -------------------------------------------------------------
@Composable
fun DecisionMatrixView(
    decisions: List<DecisionMatrixEntity>,
    onOptimizeMatrix: () -> Unit
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
                        text = "DECISION MATRIX",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Risk vs Reward • Time • Capital Efficiency • Best Decisions",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Button(
                    onClick = onOptimizeMatrix,
                    modifier = Modifier.testTag("optimize_decision_matrix_btn")
                ) {
                    Icon(Icons.Default.CheckCircle, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Optimize")
                }
            }
        }

        items(decisions) { dec ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text(dec.decisionTopic, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Risk: ${dec.riskScore}%", style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.secondary)
                        Text("Reward: ${dec.rewardScore}%", style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                        Text("Capital: $${dec.capitalRequiredMillionUsd}M", style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Bold)
                        Text("Efficiency: ${dec.compositeEfficiencyScore}%", style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Bold)
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "★ Best Decision: ${dec.bestDecisionRecommendation}",
                            modifier = Modifier.padding(8.dp),
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                }
            }
        }
    }
}

// -------------------------------------------------------------
// MODULE 8: QUANTUM TWIN
// -------------------------------------------------------------
@Composable
fun QuantumTwinView() {
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
                    Text("QUANTUM TWIN NETWORK", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    Text("Simulates Entire Business Future in Parallel Execution", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Spacer(modifier = Modifier.height(14.dp))
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                        QuantumStatBox(title = "Market Twin", value = "SYNCED", color = MaterialTheme.colorScheme.primary)
                        QuantumStatBox(title = "Company Twin", value = "100%", color = MaterialTheme.colorScheme.secondary)
                        QuantumStatBox(title = "Global Trade Twin", value = "LIVE", color = MaterialTheme.colorScheme.tertiary)
                    }
                }
            }
        }

        item {
            Text("ACTIVE DIGITAL QUANTUM TWINS", style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
        }

        item {
            QuantumTwinCard(
                title = "Global Surat-To-Diaspora Textile Mesh Twin",
                desc = "Real-time thermodynamic and logistical model of 14,000 power looms, 2,800 dye houses, and 18,500 retail touchpoints across 42 nations.",
                syncRate = "99.98% Zero-Latency Sync",
                state = "OPTIMAL_EQUILIBRIUM"
            )
        }
        item {
            QuantumTwinCard(
                title = "Predictive Liquidity & Dynamic Currency Twin",
                desc = "Multi-currency treasury mirror tracking INR, USD, AED, GBP, EUR, SGD with automated forward hedge triggers.",
                syncRate = "100% Real-Time Settlement",
                state = "HEDGED_STABLE"
            )
        }
    }
}

@Composable
fun QuantumTwinCard(title: String, desc: String, syncRate: String, state: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(title, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                Text(state, style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
            }
            Spacer(modifier = Modifier.height(6.dp))
            Text(desc, style = MaterialTheme.typography.bodySmall)
            Spacer(modifier = Modifier.height(6.dp))
            Text("Sync: $syncRate", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.secondary)
        }
    }
}

// -------------------------------------------------------------
// MODULE 9: ECONOMIC QUANTUM
// -------------------------------------------------------------
@Composable
fun EconomicQuantumView() {
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
                    Text("ECONOMIC QUANTUM", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    Text("Macro Economic Trends • Currency Shifts • Inflation Impact • Global Demand Cycles", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Spacer(modifier = Modifier.height(14.dp))
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                        QuantumStatBox(title = "Global Demand", value = "+38.4%", color = MaterialTheme.colorScheme.primary)
                        QuantumStatBox(title = "FX Stability", value = "99.2%", color = MaterialTheme.colorScheme.secondary)
                        QuantumStatBox(title = "Inflation Defense", value = "ACTIVE", color = MaterialTheme.colorScheme.tertiary)
                    }
                }
            }
        }
        item {
            QuantumInfoCard(
                title = "Macro Economic Shift Forecast (2026-2030)",
                bullets = listOf(
                    "Accelerating luxury diaspora spending across North America and GCC markets (+42% CAGR).",
                    "Automated zero-tariff corridors between India-UAE CEPA accelerating fulfillment speed by 72 hours.",
                    "Raw material inflation neutralized through decentralized forward buying contracts."
                )
            )
        }
    }
}

// -------------------------------------------------------------
// MODULE 10: REVENUE QUANTUM
// -------------------------------------------------------------
@Composable
fun RevenueQuantumView() {
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
                    Text("REVENUE QUANTUM", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    Text("Predictive Revenue • Profit Maximization • Dynamic Pricing Future • Cost Optimization", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Spacer(modifier = Modifier.height(14.dp))
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                        QuantumStatBox(title = "Projected Run-Rate", value = "$18.4B", color = MaterialTheme.colorScheme.primary)
                        QuantumStatBox(title = "Gross Margin", value = "64.8%", color = MaterialTheme.colorScheme.secondary)
                        QuantumStatBox(title = "Cost Reduction", value = "-28.2%", color = MaterialTheme.colorScheme.tertiary)
                    }
                }
            }
        }
        item {
            QuantumInfoCard(
                title = "Dynamic Pricing & Profit Optimization Directives",
                bullets = listOf(
                    "Algorithmic dynamic pricing deployed across 1,200 overseas boutiques based on live wedding season search indices.",
                    "Surat mill fabric batch scheduling consolidated to reduce loom idling waste by 96.4%.",
                    "Cross-border margin maximization delivering 4.8x ROI on international catalog syndication."
                )
            )
        }
    }
}

// -------------------------------------------------------------
// MODULE 11: KNOWLEDGE QUANTUM
// -------------------------------------------------------------
@Composable
fun KnowledgeQuantumView() {
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
                    Text("KNOWLEDGE QUANTUM", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    Text("Global Industry Insights • Innovation Mapping • Patent & Technology Watch", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Spacer(modifier = Modifier.height(14.dp))
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                        QuantumStatBox(title = "Patents Tracked", value = "12,480", color = MaterialTheme.colorScheme.primary)
                        QuantumStatBox(title = "Tech Signals", value = "4,920/day", color = MaterialTheme.colorScheme.secondary)
                        QuantumStatBox(title = "Strategic Insights", value = "100%", color = MaterialTheme.colorScheme.tertiary)
                    }
                }
            }
        }
        item {
            QuantumInfoCard(
                title = "Key Knowledge Streams",
                bullets = listOf(
                    "AI-assisted generative Jacquard loom pattern compilation patents.",
                    "Bio-engineered sustainable mulberry silk cultivation with ultra-tensile strength.",
                    "Zero-waste waterless digital nano-pigment dyeing technology adoption."
                )
            )
        }
    }
}

// -------------------------------------------------------------
// MODULE 12: QUANTUM RESEARCH
// -------------------------------------------------------------
@Composable
fun QuantumResearchView() {
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
                    Text("QUANTUM RESEARCH", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    Text("Automated Business Research • Product Feasibility • Global Trade Studies", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Spacer(modifier = Modifier.height(14.dp))
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                        QuantumStatBox(title = "Active Studies", value = "48 Studies", color = MaterialTheme.colorScheme.primary)
                        QuantumStatBox(title = "Feasibility Index", value = "99.4%", color = MaterialTheme.colorScheme.secondary)
                        QuantumStatBox(title = "R&D Velocity", value = "10x Faster", color = MaterialTheme.colorScheme.tertiary)
                    }
                }
            }
        }
        item {
            QuantumInfoCard(
                title = "Published Quantum Feasibility Reports",
                bullets = listOf(
                    "Report #429: Feasibility of Autonomous 48-Hour Bespoke Bridal Weaving & Export to London/NYC.",
                    "Report #430: Global Saree Marketplace Liquidity & Dealer Franchise Economics.",
                    "Report #431: Solar Loom Micro-Factory Grid in Semi-Urban Weaving Clusters."
                )
            )
        }
    }
}

// -------------------------------------------------------------
// MODULE 13: RISK QUANTUM
// -------------------------------------------------------------
@Composable
fun RiskQuantumView(
    risks: List<RiskQuantumEntity>,
    onScanRisks: () -> Unit
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
                        text = "RISK QUANTUM",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Early Warning System • Business, Market, Economic & Supply Risk",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                FilledTonalButton(
                    onClick = onScanRisks,
                    modifier = Modifier.testTag("scan_quantum_risks_btn")
                ) {
                    Icon(Icons.Default.Security, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Scan Risks")
                }
            }
        }

        items(risks) { risk ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(risk.riskCategory, style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = MaterialTheme.colorScheme.secondaryContainer
                        ) {
                            Text(
                                text = risk.status,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSecondaryContainer
                            )
                        }
                    }
                    Text(risk.riskName, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Probability: ${risk.probabilityPct}%", style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Bold)
                        Text("Severity: ${risk.severityScorePct}%", style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Bold)
                        Text("Impact: $${risk.potentialFinancialImpactMillionUsd}M", style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.error)
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Text("Trigger: ${risk.earlyWarningDetectionTrigger}", style = MaterialTheme.typography.bodySmall)
                    Spacer(modifier = Modifier.height(6.dp))
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "🛡️ Countermeasure: ${risk.quantumAutomatedCountermeasure}",
                            modifier = Modifier.padding(8.dp),
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                }
            }
        }
    }
}

// -------------------------------------------------------------
// MODULE 14: QUANTUM HEALTH
// -------------------------------------------------------------
@Composable
fun QuantumHealthView(
    healthList: List<QuantumHealthEntity>,
    onRecalculate: () -> Unit
) {
    val health = healthList.firstOrNull() ?: QuantumHealthEntity(
        businessHealthScore = 99.96,
        marketHealthScore = 99.91,
        aiHealthScore = 99.98,
        economicHealthScore = 99.89,
        growthHealthScore = 99.94,
        quantumHealthIndex = 99.936,
        quantumIntelligenceIndex = 99.94,
        systemStatusSummary = "PREDICTIVE_EQUILIBRIUM_PEAK"
    )

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
                        Column {
                            Text("QUANTUM HEALTH INDEX", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                            Text("System State: ${health.systemStatusSummary}", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
                        }
                        Button(
                            onClick = onRecalculate,
                            modifier = Modifier.testTag("recalculate_quantum_health_btn")
                        ) {
                            Text("Recalculate")
                        }
                    }
                    Spacer(modifier = Modifier.height(14.dp))
                    Text("${health.quantumHealthIndex}%", style = MaterialTheme.typography.displaySmall, fontWeight = FontWeight.ExtraBold, color = MaterialTheme.colorScheme.primary)
                }
            }
        }

        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text("PILLAR HEALTH METRICS", style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                    HealthProgressBar(label = "Business Health", score = health.businessHealthScore)
                    HealthProgressBar(label = "Market Health", score = health.marketHealthScore)
                    HealthProgressBar(label = "AI Self-Evolution Health", score = health.aiHealthScore)
                    HealthProgressBar(label = "Economic & Sovereign Health", score = health.economicHealthScore)
                    HealthProgressBar(label = "Future Growth Velocity", score = health.growthHealthScore)
                }
            }
        }
    }
}

@Composable
fun HealthProgressBar(label: String, score: Double) {
    Column {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(label, style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.SemiBold)
            Text("$score%", style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
        }
        Spacer(modifier = Modifier.height(4.dp))
        LinearProgressIndicator(
            progress = (score / 100.0).toFloat().coerceIn(0f, 1f),
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .clip(RoundedCornerShape(4.dp)),
            color = MaterialTheme.colorScheme.primary,
            trackColor = MaterialTheme.colorScheme.surfaceVariant
        )
    }
}

// -------------------------------------------------------------
// MODULE 15: QUANTUM COMMAND TOWER
// -------------------------------------------------------------
@Composable
fun QuantumCommandTowerView(
    quantumIndex: Double,
    evolutionScore: Double,
    scenarios: List<FutureEngineEntity>,
    decisions: List<DecisionMatrixEntity>,
    telemetryLog: List<String>
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
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.8f))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "QUANTUM COMMAND TOWER",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.ExtraBold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    Text(
                        text = "Global Predictive Overview • Real-Time Decision Map • Autonomous Future Execution",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    Spacer(modifier = Modifier.height(14.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        QuantumStatBox(title = "Quantum Index", value = "$quantumIndex%", color = MaterialTheme.colorScheme.primary)
                        QuantumStatBox(title = "AI Evolution", value = "$evolutionScore%", color = MaterialTheme.colorScheme.secondary)
                        QuantumStatBox(title = "Futures Synthesized", value = "${scenarios.size} Futures", color = MaterialTheme.colorScheme.tertiary)
                    }
                }
            }
        }

        item {
            QuantumInfoCard(
                title = "PREEMPTIVE AUTONOMOUS EXECUTION STATUS",
                bullets = listOf(
                    "Best Future Path (Future C) executed across all 12,000 smart looms and international logistics hubs.",
                    "Decision Matrix #1 executed: Zero-latency automated inventory equalisation across US and EU distribution centers.",
                    "Self-evolving tensor compute clusters allocating real-time dynamic forward hedging to protect 64.8% gross margins."
                )
            )
        }

        item {
            Text(
                text = "GLOBAL QUANTUM TELEMETRY",
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
        }

        items(telemetryLog.take(6)) { log ->
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp),
                color = MaterialTheme.colorScheme.surface,
                shadowElevation = 1.dp
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.primary)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = log,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }
    }
}

// -------------------------------------------------------------
// REUSABLE HELPER COMPONENTS
// -------------------------------------------------------------
@Composable
fun QuantumStatBox(title: String, value: String, color: Color) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = MaterialTheme.colorScheme.surface,
        shadowElevation = 1.dp
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = title, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Spacer(modifier = Modifier.height(2.dp))
            Text(text = value, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = color)
        }
    }
}

@Composable
fun QuantumBadge(text: String, modifier: Modifier = Modifier) {
    Surface(
        shape = RoundedCornerShape(8.dp),
        color = MaterialTheme.colorScheme.surface,
        modifier = modifier
    ) {
        Text(
            text = "✓ $text",
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
            style = MaterialTheme.typography.bodySmall,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
fun QuantumInfoCard(title: String, bullets: List<String>) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Text(title, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
            Spacer(modifier = Modifier.height(8.dp))
            bullets.forEach { bullet ->
                Row(modifier = Modifier.padding(vertical = 3.dp)) {
                    Text("•", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(bullet, style = MaterialTheme.typography.bodySmall)
                }
            }
        }
    }
}
