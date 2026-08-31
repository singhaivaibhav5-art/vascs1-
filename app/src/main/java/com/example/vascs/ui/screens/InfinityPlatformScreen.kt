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
import com.example.vascs.ui.viewmodel.InfinityViewModel

enum class InfinityModule(val title: String, val icon: ImageVector) {
    INFINITY_TOWER("Infinity Tower", Icons.Default.AllInclusive),
    INDUSTRY_UNIVERSE("Industry Universe", Icons.Default.Business),
    UNIVERSAL_PRODUCTS("Universal Products", Icons.Default.ShoppingBag),
    COUNTRY_INTELLIGENCE("Country Intelligence", Icons.Default.Public),
    ENTERPRISE_NETWORK("Enterprise Network", Icons.Default.Domain),
    INFINITY_ASSISTANT("Infinity Assistant", Icons.Default.SmartToy),
    INDUSTRY_INTELLIGENCE("Industry Intelligence", Icons.Default.TrendingUp),
    ECONOMIC_INTELLIGENCE("Economic Intelligence", Icons.Default.AccountBalance),
    KNOWLEDGE_MATRIX("Knowledge Matrix", Icons.Default.AutoStories),
    INFINITY_TWIN("Infinity Twin", Icons.Default.AccountTree),
    REVENUE_UNIVERSE("Revenue Universe", Icons.Default.MonetizationOn),
    RESEARCH_CENTER("Research Center", Icons.Default.Science),
    UNIVERSAL_MARKETPLACE("Universal Marketplace", Icons.Default.Storefront),
    INFINITY_GROWTH("Infinity Growth", Icons.Default.RocketLaunch),
    INFINITY_CONTROL("Infinity Control", Icons.Default.Tune)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InfinityPlatformScreen(
    viewModel: InfinityViewModel,
    onBackClick: (() -> Unit)? = null
) {
    var selectedModule by remember { mutableStateOf(InfinityModule.INFINITY_TOWER) }

    val industries by viewModel.industries.collectAsState()
    val countries by viewModel.countries.collectAsState()
    val economy by viewModel.economy.collectAsState()
    val researchReports by viewModel.researchReports.collectAsState()
    val opportunities by viewModel.opportunities.collectAsState()
    val expansionPlans by viewModel.expansionPlans.collectAsState()
    val marketplace by viewModel.marketplace.collectAsState()
    val infinityScore by viewModel.infinityScore.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "VASCS INFINITY",
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
                                    text = "CHECKPOINT 15.0",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                    color = MaterialTheme.colorScheme.onPrimaryContainer
                                )
                            }
                        }
                        Text(
                            text = "Infinite Scale & Multi-Industry Intelligence | Score: ${String.format("%.1f", infinityScore)}",
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
                        onClick = { viewModel.calculateInfinityScore() },
                        modifier = Modifier.testTag("refresh_infinity_score_button")
                    ) {
                        Icon(Icons.Default.Refresh, contentDescription = "Recalculate Infinity Score", tint = MaterialTheme.colorScheme.primary)
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
                    .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.35f))
                    .padding(vertical = 8.dp, horizontal = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(InfinityModule.values()) { module ->
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
                        modifier = Modifier.testTag("infinity_tab_${module.name.lowercase()}")
                    )
                }
            }

            // Main Module View
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
                    .padding(16.dp)
            ) {
                when (selectedModule) {
                    InfinityModule.INFINITY_TOWER -> InfinityCommandTowerView(viewModel, infinityScore, industries, countries, opportunities)
                    InfinityModule.INDUSTRY_UNIVERSE -> MultiIndustryEngineView(viewModel, industries)
                    InfinityModule.UNIVERSAL_PRODUCTS -> UniversalProductEngineView(viewModel, marketplace)
                    InfinityModule.COUNTRY_INTELLIGENCE -> GlobalCountryNetworkView(viewModel, countries)
                    InfinityModule.ENTERPRISE_NETWORK -> MultiCompanyEcosystemView()
                    InfinityModule.INFINITY_ASSISTANT -> UniversalAiAssistantView(viewModel)
                    InfinityModule.INDUSTRY_INTELLIGENCE -> CrossIndustryIntelligenceView(opportunities)
                    InfinityModule.ECONOMIC_INTELLIGENCE -> GlobalEconomicEngineView(viewModel, economy)
                    InfinityModule.KNOWLEDGE_MATRIX -> UniversalKnowledgeNetworkView()
                    InfinityModule.INFINITY_TWIN -> InfinityDigitalTwinView(viewModel)
                    InfinityModule.REVENUE_UNIVERSE -> UniversalRevenueEngineView()
                    InfinityModule.RESEARCH_CENTER -> AiResearchLabView(viewModel, researchReports)
                    InfinityModule.UNIVERSAL_MARKETPLACE -> InfinityMarketplaceView(viewModel, marketplace)
                    InfinityModule.INFINITY_GROWTH -> UniversalExpansionEngineView(viewModel, expansionPlans)
                    InfinityModule.INFINITY_CONTROL -> InfinityCommandNetworkView(viewModel)
                }
            }
        }
    }
}

// -------------------------------------------------------------
// MODULE 15: INFINITY COMMAND TOWER
// -------------------------------------------------------------
@Composable
fun InfinityCommandTowerView(
    viewModel: InfinityViewModel,
    score: Double,
    industries: List<IndustryMasterEntity>,
    countries: List<CountryMasterEntity>,
    opportunities: List<MarketOpportunityEntity>
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
                                text = "VASCS INFINITY SINGULARITY",
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f)
                            )
                            Text(
                                text = "Infinity Intelligence Index",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        }
                        Text(
                            text = String.format("%.1f", score),
                            fontSize = 34.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "Universal Scale: 10 Industries • 190+ Countries • Unlimited Businesses",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Button(
                            onClick = { viewModel.calculateInfinityScore() },
                            modifier = Modifier.weight(1f).testTag("tower_recalculate_score_btn")
                        ) {
                            Text("Recalculate Index", fontSize = 12.sp)
                        }
                        Button(
                            onClick = { viewModel.runResearch() },
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary),
                            modifier = Modifier.weight(1f).testTag("tower_run_research_btn")
                        ) {
                            Text("Run AI Research", fontSize = 12.sp)
                        }
                    }
                }
            }
        }

        item {
            Text(
                text = "Global Ecosystem Telemetry",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
        }

        val telemetryStats = listOf(
            "Total Managed Industries" to "${industries.size} Global Industries",
            "Connected Global Corridors" to "${countries.size} Sovereign Trade Corridors",
            "Market Growth Opportunities" to "${opportunities.size} Prime Value Propositions",
            "Multi-Company Governance" to "Synchronized (Global Group Tier)",
            "Cross-Industry Knowledge Graph" to "100% Connected & Reasoning"
        )

        items(telemetryStats) { (label, value) ->
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
                                .background(Color(0xFF2E7D32))
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(text = label, fontWeight = FontWeight.SemiBold)
                    }
                    Text(text = value, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

// -------------------------------------------------------------
// MODULE 1: MULTI-INDUSTRY ENGINE
// -------------------------------------------------------------
@Composable
fun MultiIndustryEngineView(
    viewModel: InfinityViewModel,
    industries: List<IndustryMasterEntity>
) {
    LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text("MODULE 1: MULTI-INDUSTRY ENGINE", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    Text("One ERP • All 10 Core Global Industries", style = MaterialTheme.typography.bodySmall)
                }
                Button(
                    onClick = { viewModel.analyzeIndustry() },
                    modifier = Modifier.testTag("analyze_new_industry_btn")
                ) {
                    Text("+ Add Industry", fontSize = 12.sp)
                }
            }
        }

        items(industries) { ind ->
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(ind.industryName, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                        Text("Sector: ${ind.sector}", style = MaterialTheme.typography.labelSmall)
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("Global Market Cap: $${ind.marketCapTrillionUsd}T | Growth Rate: +${ind.globalGrowthRatePct}% YoY", style = MaterialTheme.typography.bodyMedium)
                    Text("Automation Index: ${ind.automationIndex}% | Risk: ${ind.riskFactor}", fontSize = 12.sp)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("Status: ${ind.status}", fontWeight = FontWeight.Bold, color = Color(0xFF2E7D32), fontSize = 11.sp)
                }
            }
        }
    }
}

// -------------------------------------------------------------
// MODULE 2: UNIVERSAL PRODUCT ENGINE
// -------------------------------------------------------------
@Composable
fun UniversalProductEngineView(
    viewModel: InfinityViewModel,
    products: List<UniversalMarketplaceEntity>
) {
    val productTypes = listOf("Physical Products", "Digital Products", "Services", "Subscriptions", "Bundles")
    var selectedType by remember { mutableStateOf("Physical Products") }

    LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        item {
            Text("MODULE 2: UNIVERSAL PRODUCT ENGINE", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Text("Handles Physical, Digital, Services, Subscriptions & Bundles with AI Product Intelligence.", style = MaterialTheme.typography.bodySmall)
        }

        item {
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(productTypes) { type ->
                    FilterChip(
                        selected = selectedType == type,
                        onClick = { selectedType = type },
                        label = { Text(type, fontSize = 12.sp) }
                    )
                }
            }
        }

        items(products) { item ->
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(item.itemName, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary, modifier = Modifier.weight(1f))
                        Text(item.productType, fontSize = 11.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.secondary)
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("Seller: ${item.sellerName} | Industry: ${item.industry}", style = MaterialTheme.typography.bodySmall)
                    Text("Price: ₹${String.format("%,.0f", item.basePriceInr)} | Target: ${item.targetAudience}", fontWeight = FontWeight.Bold)
                    Text("Stock: ${item.stockOrCapacity} | AI Demand Rating: ${item.aiDemandRating}/100", fontSize = 12.sp)
                }
            }
        }

        item {
            Button(
                onClick = { viewModel.publishMarketplaceItem() },
                modifier = Modifier.fillMaxWidth().testTag("publish_universal_product_btn")
            ) {
                Text("Generate AI Product Intelligence")
            }
        }
    }
}

// -------------------------------------------------------------
// MODULE 3: GLOBAL COUNTRY NETWORK
// -------------------------------------------------------------
@Composable
fun GlobalCountryNetworkView(
    viewModel: InfinityViewModel,
    countries: List<CountryMasterEntity>
) {
    LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text("MODULE 3: GLOBAL COUNTRY NETWORK", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    Text("Country Profiles • Import/Export Rules • Tax Rules • Ratings", style = MaterialTheme.typography.bodySmall)
                }
                Button(
                    onClick = { viewModel.analyzeCountry() },
                    modifier = Modifier.testTag("analyze_country_btn")
                ) {
                    Text("+ Connect Country", fontSize = 12.sp)
                }
            }
        }

        items(countries) { country ->
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("${country.countryName} (${country.isoCode})", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                        Text(country.easeOfBusinessRating, fontWeight = FontWeight.Bold, fontSize = 11.sp, color = Color(0xFF2E7D32))
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("GDP: $${String.format("%,.0f", country.gdpBillionUsd)}B | Corporate Tax: ${country.corporateTaxPct}% | Export Tariff: ${country.exportTariffPct}%", style = MaterialTheme.typography.bodyMedium)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("Trade Opportunities: ${country.primaryTradeOpportunities}", fontSize = 12.sp)
                }
            }
        }
    }
}

// -------------------------------------------------------------
// MODULE 4: MULTI-COMPANY ECOSYSTEM
// -------------------------------------------------------------
@Composable
fun MultiCompanyEcosystemView() {
    var selectedTier by remember { mutableStateOf("Global Wise") }
    val tiers = listOf("Company Wise", "Group Wise", "Global Wise")

    LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        item {
            Text("MODULE 4: MULTI-COMPANY ECOSYSTEM", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Text("Supports Single, Multi, Group, Holding & Global Networks with Multi-Tier Dashboards.", style = MaterialTheme.typography.bodySmall)
        }

        item {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                tiers.forEach { tier ->
                    FilterChip(
                        selected = selectedTier == tier,
                        onClick = { selectedTier = tier },
                        label = { Text(tier) }
                    )
                }
            }
        }

        item {
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Active Holding Structure: VASCS INFINITY GLOBAL GROUP", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("• Subsidiary 1: VASCS Silk Handlooms Ltd. (Native Manufacturer)", style = MaterialTheme.typography.bodyMedium)
                    Text("• Subsidiary 2: VASCS Global Export Corp (Dubai & New York Gateway)", style = MaterialTheme.typography.bodyMedium)
                    Text("• Subsidiary 3: VASCS AI Technologies SaaS LLC (Autonomous Commerce OS)", style = MaterialTheme.typography.bodyMedium)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Consolidated Global Health: 99.9% Autonomous Compliance", fontWeight = FontWeight.Bold, color = Color(0xFF2E7D32))
                }
            }
        }
    }
}

// -------------------------------------------------------------
// MODULE 5: UNIVERSAL AI ASSISTANT
// -------------------------------------------------------------
@Composable
fun UniversalAiAssistantView(viewModel: InfinityViewModel) {
    var activeCommand by remember { mutableStateOf("Show Growth Plan") }
    var assistantResponse by remember {
        mutableStateOf(
            "VASCS INFINITY AI GROWTH PLAN:\n" +
            "1. Consolidate Pan-India Silk Weaving Hubs with instant WhatsApp dynamic pricing.\n" +
            "2. Deploy Dubai Bonded Fulfillment for zero-tariff 48-hour delivery across GCC.\n" +
            "3. Expand into European NRI Wedding boutiques via direct-to-dealer cloud catalogs.\n" +
            "Projected Yield: +78% Top-line Revenue with 4.5x Capital Multiplier."
        )
    }

    val commands = listOf("Show Growth Plan", "Show Risks", "Show Opportunities", "Show Expansion Ideas")

    LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        item {
            Text("MODULE 5: UNIVERSAL AI ASSISTANT", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Text("Business • Market • Finance • Sales • Growth Advisor", style = MaterialTheme.typography.bodySmall)
        }

        item {
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(commands) { cmd ->
                    FilterChip(
                        selected = activeCommand == cmd,
                        onClick = {
                            activeCommand = cmd
                            assistantResponse = when (cmd) {
                                "Show Growth Plan" ->
                                    "VASCS INFINITY AI GROWTH PLAN:\n" +
                                    "1. Consolidate Pan-India Silk Weaving Hubs with instant WhatsApp dynamic pricing.\n" +
                                    "2. Deploy Dubai Bonded Fulfillment for zero-tariff 48-hour delivery across GCC.\n" +
                                    "3. Expand into European NRI Wedding boutiques via direct-to-dealer cloud catalogs.\n" +
                                    "Projected Yield: +78% Top-line Revenue with 4.5x Capital Multiplier."
                                "Show Risks" ->
                                    "AI RISK EVALUATION:\n" +
                                    "• Global Currency Volatility: Minimal risk (Auto-hedged via USD/AED lock).\n" +
                                    "• Raw Material Price Fluctuations: Hedged via direct weaver advance allocation.\n" +
                                    "• Logistics Disruptions: Zero bottlenecks (Redundant sea/air corridors active)."
                                "Show Opportunities" ->
                                    "AI PRIME OPPORTUNITIES:\n" +
                                    "• $450M North America NRI Bridal Boutique Wholesale Syndicate.\n" +
                                    "• $320M UAE & Saudi Luxury Zari Silk Free-Trade Corridor.\n" +
                                    "• Cross-Industry Jewellery & Fashion Bundle Automation."
                                else ->
                                    "AI EXPANSION BLUEPRINT:\n" +
                                    "• City Tier: 24 Tier-1 Metro Distribution Centers.\n" +
                                    "• State Tier: Pan-India Weaver Clusters Connected.\n" +
                                    "• Country Tier: USA, UAE, UK, Singapore & Australia.\n" +
                                    "• Global Singularity: Universal Multi-Industry Platform."
                            }
                        },
                        label = { Text(cmd, fontSize = 12.sp) },
                        modifier = Modifier.testTag("assistant_cmd_${cmd.lowercase().replace(" ", "_")}")
                    )
                }
            }
        }

        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.SmartToy, contentDescription = "AI", tint = MaterialTheme.colorScheme.primary)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Infinity AI Intelligence Output", fontWeight = FontWeight.Bold)
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(assistantResponse, style = MaterialTheme.typography.bodyMedium, lineHeight = 22.sp)
                }
            }
        }
    }
}

// -------------------------------------------------------------
// MODULE 6: CROSS-INDUSTRY INTELLIGENCE
// -------------------------------------------------------------
@Composable
fun CrossIndustryIntelligenceView(opportunities: List<MarketOpportunityEntity>) {
    LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        item {
            Text("MODULE 6: CROSS-INDUSTRY INTELLIGENCE", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Text("Analyzes Trends, Growth, Risks & Identifies Best Industry to Enter/Invest.", style = MaterialTheme.typography.bodySmall)
        }

        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("AI RECOMMENDATION: Best Industry To Enter", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onPrimaryContainer)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("• Luxury Ethnic Fashion & Designer Jewellery Syndicate", fontWeight = FontWeight.SemiBold)
                    Text("Highest Gross Margin (54%), High NRI Demand, Zero Tariff Free Trade Corridor.", style = MaterialTheme.typography.bodySmall)
                }
            }
        }

        items(opportunities) { opp ->
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(opp.title, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary, modifier = Modifier.weight(1f))
                        Text(opp.aiRating, fontWeight = FontWeight.Bold, fontSize = 11.sp, color = Color(0xFF2E7D32))
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("Target: ${opp.targetIndustry} (${opp.targetRegion}) | Cap: ₹${String.format("%,.0f", opp.estimatedMarketCapInr)}", style = MaterialTheme.typography.bodyMedium)
                    Text("Expected ROI: ${opp.expectedRoiMultiplier}x Multiplier | Barrier: ${opp.entryBarrier}", fontSize = 12.sp)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("Strategy: ${opp.strategicActionPlan}", fontSize = 12.sp)
                }
            }
        }
    }
}

// -------------------------------------------------------------
// MODULE 7: GLOBAL ECONOMIC ENGINE
// -------------------------------------------------------------
@Composable
fun GlobalEconomicEngineView(
    viewModel: InfinityViewModel,
    economyList: List<GlobalEconomyEntity>
) {
    LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text("MODULE 7: GLOBAL ECONOMIC ENGINE", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    Text("GDP • Inflation • Interest Rates • Currency • Trade Trends", style = MaterialTheme.typography.bodySmall)
                }
                Button(onClick = { viewModel.updateGlobalEconomy() }) {
                    Text("Sync Economy", fontSize = 12.sp)
                }
            }
        }

        items(economyList) { eco ->
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(eco.indicatorName, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                        Text(eco.valueStr, fontWeight = FontWeight.Bold, color = Color(0xFF2E7D32))
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("Inflation: ${eco.inflationRatePct}% | Interest: ${eco.interestRatePct}% | Volatility: ${eco.currencyPairVolatility}", style = MaterialTheme.typography.bodySmall)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("Trade Trend: ${eco.globalTradeTrend}", fontSize = 12.sp)
                    Text("AI Forecast: ${eco.aiEconomicForecast}", fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                }
            }
        }
    }
}

// -------------------------------------------------------------
// MODULE 8: UNIVERSAL KNOWLEDGE NETWORK
// -------------------------------------------------------------
@Composable
fun UniversalKnowledgeNetworkView() {
    val knowledgeNodes = listOf(
        "Cross-Industry Knowledge Graph" to "1.2 Million Nodes Synchronized",
        "Multi-Country Regulatory Matrix" to "190 Sovereign Tax & Tariff Rules",
        "Dealer Purchasing Behaviour Engine" to "15,000+ Verified Dealer Profiles",
        "Global Commodity & Raw Material Index" to "Real-Time Silk, Gold & Yarn Tickers",
        "Autonomous Decision Precedent Base" to "50,000+ Autonomous Decisions Logged"
    )

    LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        item {
            Text("MODULE 8: UNIVERSAL KNOWLEDGE NETWORK", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Text("Knowledge Matrix storing Industry, Business, Market & Economic Intelligence.", style = MaterialTheme.typography.bodySmall)
        }

        items(knowledgeNodes) { (node, status) ->
            Card(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(node, fontWeight = FontWeight.SemiBold, modifier = Modifier.weight(1f))
                    Text(status, fontSize = 11.sp, color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

// -------------------------------------------------------------
// MODULE 9: INFINITY DIGITAL TWIN
// -------------------------------------------------------------
@Composable
fun InfinityDigitalTwinView(viewModel: InfinityViewModel) {
    val twinTypes = listOf("Company Twin", "Industry Twin", "Country Twin", "Global Economy Twin")
    var selectedTwin by remember { mutableStateOf("Company Twin") }

    LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        item {
            Text("MODULE 9: INFINITY DIGITAL TWIN", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Text("High-fidelity simulations for Business, Industry, Country & Global Economy.", style = MaterialTheme.typography.bodySmall)
        }

        item {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                twinTypes.forEach { type ->
                    FilterChip(
                        selected = selectedTwin == type,
                        onClick = { selectedTwin = type },
                        label = { Text(type, fontSize = 12.sp) }
                    )
                }
            }
        }

        item {
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("ACTIVE SIMULATION: $selectedTwin", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("• Simulation Fidelity Score: 99.98%")
                    Text("• Active Parallel Scenario Runs: 500 scenarios")
                    Text("• Forecasted Multiplier: 4.8x Growth in 12 Months")
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Strategic Twin Insight: Launching automated WhatsApp B2B catalogues across 500 US NRI boutiques delivers ₹42Cr incremental Net Profit with zero inventory deadlock.", style = MaterialTheme.typography.bodySmall)
                }
            }
        }

        item {
            Button(
                onClick = { viewModel.calculateInfinityScore() },
                modifier = Modifier.fillMaxWidth().testTag("run_infinity_twin_sim_btn")
            ) {
                Text("Run New Global Digital Twin Simulation")
            }
        }
    }
}

// -------------------------------------------------------------
// MODULE 10: UNIVERSAL REVENUE ENGINE
// -------------------------------------------------------------
@Composable
fun UniversalRevenueEngineView() {
    val revenueStreams = listOf(
        "Direct Wholesale B2B Silk Network" to "₹120.0 Cr" to "+48.2%",
        "Global Cross-Border Export Corridor" to "₹65.0 Cr" to "+82.4%",
        "Autonomous Enterprise SaaS Subscriptions" to "₹18.5 Cr" to "+140.0%",
        "Universal Marketplace Transaction Fees" to "₹24.0 Cr" to "+95.0%"
    )

    LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        item {
            Text("MODULE 10: UNIVERSAL REVENUE ENGINE", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Text("Revenue Streams • Industry Revenue • Country Revenue • Growth Plans", style = MaterialTheme.typography.bodySmall)
        }

        items(revenueStreams) { (pair, growth) ->
            val (name, rev) = pair
            Card(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(name, fontWeight = FontWeight.Bold)
                        Text("Current Run Rate: $rev", color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.SemiBold, fontSize = 12.sp)
                    }
                    Text(growth, color = Color(0xFF2E7D32), fontWeight = FontWeight.ExtraBold, fontSize = 14.sp)
                }
            }
        }
    }
}

// -------------------------------------------------------------
// MODULE 11: AI RESEARCH LAB
// -------------------------------------------------------------
@Composable
fun AiResearchLabView(
    viewModel: InfinityViewModel,
    reports: List<ResearchReportEntity>
) {
    LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text("MODULE 11: AI RESEARCH LAB", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    Text("Researches Products, Markets, Industries & Technologies", style = MaterialTheme.typography.bodySmall)
                }
                Button(
                    onClick = { viewModel.runResearch() },
                    modifier = Modifier.testTag("run_lab_research_btn")
                ) {
                    Text("+ New Research", fontSize = 12.sp)
                }
            }
        }

        items(reports) { report ->
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(report.topicTitle, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                    Text("Domain: ${report.domain} | Confidence: ${report.aiConfidenceScore}%", style = MaterialTheme.typography.bodySmall)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(report.executiveSummary, fontSize = 12.sp)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("Disruptive Tech: ${report.disruptiveTechnologies}", fontSize = 11.sp, color = MaterialTheme.colorScheme.secondary)
                }
            }
        }
    }
}

// -------------------------------------------------------------
// MODULE 12: INFINITY MARKETPLACE
// -------------------------------------------------------------
@Composable
fun InfinityMarketplaceView(
    viewModel: InfinityViewModel,
    items: List<UniversalMarketplaceEntity>
) {
    LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text("MODULE 12: INFINITY MARKETPLACE", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    Text("B2B • B2C • D2C • Global Trade | Unlimited Scale", style = MaterialTheme.typography.bodySmall)
                }
                Button(
                    onClick = { viewModel.publishMarketplaceItem() },
                    modifier = Modifier.testTag("publish_marketplace_btn")
                ) {
                    Text("+ List Item", fontSize = 12.sp)
                }
            }
        }

        items(items) { item ->
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(item.itemName, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary, modifier = Modifier.weight(1f))
                        Text("₹${String.format("%,.0f", item.basePriceInr)}", fontWeight = FontWeight.Bold, color = Color(0xFF2E7D32))
                    }
                    Text("Type: ${item.productType} | Audience: ${item.targetAudience} | Cross-Border: ${if (item.crossBorderEligible) "Yes" else "No"}", style = MaterialTheme.typography.bodySmall)
                    Text("Seller: ${item.sellerName} | Stock: ${item.stockOrCapacity}", fontSize = 12.sp)
                }
            }
        }
    }
}

// -------------------------------------------------------------
// MODULE 13: UNIVERSAL EXPANSION ENGINE
// -------------------------------------------------------------
@Composable
fun UniversalExpansionEngineView(
    viewModel: InfinityViewModel,
    plans: List<ExpansionBlueprintEntity>
) {
    LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text("MODULE 13: UNIVERSAL EXPANSION ENGINE", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    Text("City • State • Country • Global Expansion Blueprints", style = MaterialTheme.typography.bodySmall)
                }
                Button(
                    onClick = { viewModel.buildExpansionBlueprint() },
                    modifier = Modifier.testTag("build_expansion_btn")
                ) {
                    Text("+ New Blueprint", fontSize = 12.sp)
                }
            }
        }

        items(plans) { plan ->
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(plan.expansionName, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary, modifier = Modifier.weight(1f))
                        Text(plan.status, fontWeight = FontWeight.Bold, fontSize = 11.sp, color = Color(0xFF2E7D32))
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("Target: ${plan.targetLevel} (${plan.geographicalTarget})", style = MaterialTheme.typography.bodyMedium)
                    Text("Capital: ₹${String.format("%,.0f", plan.capitalRequiredInr)} | Projected Rev: ₹${String.format("%,.0f", plan.projectedRevenueInr)}", fontWeight = FontWeight.SemiBold, fontSize = 12.sp)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("Milestones:\n${plan.operationalMilestones}", fontSize = 11.sp)
                }
            }
        }
    }
}

// -------------------------------------------------------------
// MODULE 14: INFINITY COMMAND NETWORK
// -------------------------------------------------------------
@Composable
fun InfinityCommandNetworkView(viewModel: InfinityViewModel) {
    val controlNodes = listOf(
        "Companies Supercluster" to "Autonomous Coordination Active",
        "Cross-Industry Network" to "Unified Data Flow Synchronized",
        "Global Markets Grid" to "Dynamic Pricing & Supply Balancing",
        "Sovereign Country Portals" to "Tax & Customs Auto-Execution",
        "Autonomous Trade Corridors" to "Zero-Latency Routing Active"
    )

    LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        item {
            Text("MODULE 14: INFINITY COMMAND NETWORK", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Text("Global Control over Companies, Industries, Markets, Countries & Trade Networks.", style = MaterialTheme.typography.bodySmall)
        }

        items(controlNodes) { (node, status) ->
            Card(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(node, fontWeight = FontWeight.SemiBold, modifier = Modifier.weight(1f))
                    Text(status, fontSize = 11.sp, color = Color(0xFF2E7D32), fontWeight = FontWeight.Bold)
                }
            }
        }

        item {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(
                    onClick = { viewModel.calculateInfinityScore() },
                    modifier = Modifier.weight(1f).testTag("cmd_optimize_all_btn")
                ) {
                    Text("Optimize All", fontSize = 12.sp)
                }
                Button(
                    onClick = { viewModel.generateOpportunity() },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary),
                    modifier = Modifier.weight(1f).testTag("cmd_expand_network_btn")
                ) {
                    Text("Expand Network", fontSize = 12.sp)
                }
            }
        }
    }
}
