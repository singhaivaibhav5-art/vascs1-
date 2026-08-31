package com.example.vascs.ui.screens

import android.widget.Toast
import androidx.compose.animation.*
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.vascs.data.model.AIDemandForecastEntity
import com.example.vascs.data.model.AIDemandHistoryEntity
import com.example.vascs.data.model.AIDemandModelEntity
import com.example.vascs.viewmodel.AIDemandForecastViewModel
import com.example.vascs.viewmodel.DemandProductPreset
import com.example.vascs.viewmodel.DemandTab
import com.example.vascs.viewmodel.ForecastHorizon
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AIDemandForecastScreen(
    viewModel: AIDemandForecastViewModel,
    onNavigateBack: () -> Unit
) {
    val context = LocalContext.current
    val activeTab by viewModel.activeTab.collectAsState()
    val selectedHorizon by viewModel.selectedHorizon.collectAsState()
    val inputState by viewModel.forecastInput.collectAsState()
    val forecastResult by viewModel.forecastResult.collectAsState()
    val isLoading by viewModel.loadingState.collectAsState()
    val errorState by viewModel.errorState.collectAsState()
    val successMessage by viewModel.successMessage.collectAsState()
    val historyList by viewModel.forecastHistory.collectAsState()
    val modelsList by viewModel.demandModels.collectAsState()

    val currencyFormatter = remember {
        NumberFormat.getCurrencyInstance(Locale("en", "IN")).apply {
            maximumFractionDigits = 0
        }
    }

    LaunchedEffect(successMessage) {
        successMessage?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            viewModel.clearMessages()
        }
    }

    LaunchedEffect(errorState) {
        errorState?.let {
            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
            viewModel.clearMessages()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "AI Demand Forecast Engine",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Surface(
                                shape = RoundedCornerShape(6.dp),
                                color = MaterialTheme.colorScheme.primaryContainer
                            ) {
                                Text(
                                    text = "U4",
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = MaterialTheme.colorScheme.onPrimaryContainer
                                )
                            }
                        }
                        Text(
                            text = "Predictive Inventory, Seasonal Surge & Reorder Matrix",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                },
                navigationIcon = {
                    IconButton(
                        onClick = onNavigateBack,
                        modifier = Modifier.testTag("demand_forecast_back_button")
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = {
                            forecastResult?.let {
                                val report = "VASCS AI Demand Forecast:\nProduct: ${it.productName}\n30D: ${it.forecast30dUnits} units (${currencyFormatter.format(it.forecast30dRevenue)})\nReorder: ${it.reorderQuantity} units\nSafety Stock: ${it.safetyStockRecommendation} units\nDead Stock Risk: ${it.deadStockRisk}"
                                Toast.makeText(context, "Summary copied to clipboard", Toast.LENGTH_SHORT).show()
                            } ?: Toast.makeText(context, "Generate forecast first to export", Toast.LENGTH_SHORT).show()
                        },
                        modifier = Modifier.testTag("demand_export_action_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Share,
                            contentDescription = "Share Demand Report"
                        )
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
            // Navigation Tab Bar
            SecondaryTabRow(
                selectedTabIndex = activeTab.ordinal,
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("demand_forecast_tabs")
            ) {
                Tab(
                    selected = activeTab == DemandTab.FORECAST_STUDIO,
                    onClick = { viewModel.selectTab(DemandTab.FORECAST_STUDIO) },
                    text = { Text("Forecast Studio") },
                    icon = { Icon(Icons.Default.AutoGraph, contentDescription = null, modifier = Modifier.size(18.dp)) },
                    modifier = Modifier.testTag("tab_forecast_studio")
                )
                Tab(
                    selected = activeTab == DemandTab.INVENTORY_PLANNER,
                    onClick = { viewModel.selectTab(DemandTab.INVENTORY_PLANNER) },
                    text = { Text("Inventory Plan") },
                    icon = { Icon(Icons.Default.Inventory, contentDescription = null, modifier = Modifier.size(18.dp)) },
                    modifier = Modifier.testTag("tab_inventory_plan")
                )
                Tab(
                    selected = activeTab == DemandTab.RISK_RADAR,
                    onClick = { viewModel.selectTab(DemandTab.RISK_RADAR) },
                    text = { Text("Risk Radar") },
                    icon = { Icon(Icons.Default.Shield, contentDescription = null, modifier = Modifier.size(18.dp)) },
                    modifier = Modifier.testTag("tab_risk_radar")
                )
                Tab(
                    selected = activeTab == DemandTab.HISTORY_LOGS,
                    onClick = { viewModel.selectTab(DemandTab.HISTORY_LOGS) },
                    text = { Text("Audit Logs") },
                    icon = { Icon(Icons.Default.History, contentDescription = null, modifier = Modifier.size(18.dp)) },
                    modifier = Modifier.testTag("tab_demand_history")
                )
            }

            AnimatedContent(targetState = activeTab, label = "DemandTabTransition") { tab ->
                when (tab) {
                    DemandTab.FORECAST_STUDIO -> ForecastStudioTab(
                        viewModel = viewModel,
                        inputState = inputState,
                        forecastResult = forecastResult,
                        selectedHorizon = selectedHorizon,
                        isLoading = isLoading,
                        currencyFormatter = currencyFormatter
                    )
                    DemandTab.INVENTORY_PLANNER -> InventoryPlannerTab(
                        forecastResult = forecastResult,
                        modelsList = modelsList,
                        onApplyReorder = { viewModel.applyReorderPlan(it) },
                        currencyFormatter = currencyFormatter
                    )
                    DemandTab.RISK_RADAR -> RiskRadarTab(
                        forecastResult = forecastResult,
                        modelsList = modelsList,
                        currencyFormatter = currencyFormatter
                    )
                    DemandTab.HISTORY_LOGS -> DemandHistoryLogsTab(
                        historyList = historyList,
                        onDeleteHistory = { viewModel.deleteHistoryItem(it) },
                        onClearAll = { viewModel.clearAllHistory() },
                        currencyFormatter = currencyFormatter
                    )
                }
            }
        }
    }
}

@Composable
private fun ForecastStudioTab(
    viewModel: AIDemandForecastViewModel,
    inputState: com.example.vascs.data.model.AIDemandRequestEntity,
    forecastResult: AIDemandForecastEntity?,
    selectedHorizon: ForecastHorizon,
    isLoading: Boolean,
    currencyFormatter: NumberFormat
) {
    val context = LocalContext.current

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(vertical = 16.dp)
    ) {
        // Presets Selector
        item {
            Text(
                text = "Product Archetypes & Catalogs",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(viewModel.presets) { preset ->
                    val isSelected = inputState.productName == preset.productName
                    FilterChip(
                        selected = isSelected,
                        onClick = { viewModel.loadPreset(preset) },
                        label = {
                            Text(
                                text = preset.productName,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        },
                        leadingIcon = {
                            if (isSelected) {
                                Icon(Icons.Default.Check, contentDescription = null, modifier = Modifier.size(16.dp))
                            }
                        },
                        modifier = Modifier.testTag("preset_chip_${preset.sku}")
                    )
                }
            }
        }

        // Input Configuration Card
        item {
            ElevatedCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("forecast_input_card"),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.elevatedCardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                )
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
                            text = "Forecast Input Parameters",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Badge(
                            containerColor = MaterialTheme.colorScheme.primaryContainer,
                            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                        ) {
                            Text("Gemini 2.5 Pro", modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp))
                        }
                    }

                    OutlinedTextField(
                        value = inputState.productName,
                        onValueChange = { viewModel.updateProductName(it) },
                        label = { Text("Product Name") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("input_demand_product_name"),
                        singleLine = true
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedTextField(
                            value = inputState.sku,
                            onValueChange = { viewModel.updateSku(it) },
                            label = { Text("SKU Code") },
                            modifier = Modifier
                                .weight(1f)
                                .testTag("input_demand_sku"),
                            singleLine = true
                        )
                        OutlinedTextField(
                            value = inputState.category,
                            onValueChange = { viewModel.updateCategory(it) },
                            label = { Text("Category") },
                            modifier = Modifier
                                .weight(1.2f)
                                .testTag("input_demand_category"),
                            singleLine = true
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedTextField(
                            value = inputState.region,
                            onValueChange = { viewModel.updateRegion(it) },
                            label = { Text("Target Region") },
                            modifier = Modifier
                                .weight(1f)
                                .testTag("input_demand_region"),
                            singleLine = true
                        )
                        OutlinedTextField(
                            value = inputState.dealerNetwork,
                            onValueChange = { viewModel.updateDealerNetwork(it) },
                            label = { Text("Dealer Tier") },
                            modifier = Modifier
                                .weight(1f)
                                .testTag("input_demand_dealer_network"),
                            singleLine = true
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedTextField(
                            value = inputState.season,
                            onValueChange = { viewModel.updateSeason(it) },
                            label = { Text("Season Context") },
                            modifier = Modifier
                                .weight(1f)
                                .testTag("input_demand_season"),
                            singleLine = true
                        )
                        OutlinedTextField(
                            value = inputState.festivalCalendar,
                            onValueChange = { viewModel.updateFestivalCalendar(it) },
                            label = { Text("Festival Calendar") },
                            modifier = Modifier
                                .weight(1.2f)
                                .testTag("input_demand_festivals"),
                            singleLine = true
                        )
                    }

                    OutlinedTextField(
                        value = inputState.marketingCampaignData,
                        onValueChange = { viewModel.updateMarketingCampaign(it) },
                        label = { Text("Marketing & Campaign Channel") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("input_demand_marketing"),
                        singleLine = true
                    )

                    // Numerical Inputs (Stock, Historical Sales, Price, Lead Time)
                    Text(
                        text = "Historical Sales & Supply Chain Metrics",
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.primary
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedTextField(
                            value = inputState.currentInventory.toString(),
                            onValueChange = { it.toIntOrNull()?.let { v -> viewModel.updateCurrentInventory(v) } },
                            label = { Text("Stock On Hand") },
                            modifier = Modifier
                                .weight(1f)
                                .testTag("input_demand_current_stock"),
                            singleLine = true
                        )
                        OutlinedTextField(
                            value = inputState.unitPrice.toString(),
                            onValueChange = { it.toDoubleOrNull()?.let { v -> viewModel.updateUnitPrice(v) } },
                            label = { Text("Price (₹)") },
                            modifier = Modifier
                                .weight(1f)
                                .testTag("input_demand_unit_price"),
                            singleLine = true
                        )
                        OutlinedTextField(
                            value = inputState.leadTimeDays.toString(),
                            onValueChange = { it.toIntOrNull()?.let { v -> viewModel.updateLeadTimeDays(v) } },
                            label = { Text("Lead Time (Days)") },
                            modifier = Modifier
                                .weight(1f)
                                .testTag("input_demand_lead_time"),
                            singleLine = true
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedTextField(
                            value = inputState.salesHistory30d.toString(),
                            onValueChange = { it.toIntOrNull()?.let { v -> viewModel.updateSalesHistory30d(v) } },
                            label = { Text("Sales 30D") },
                            modifier = Modifier
                                .weight(1f)
                                .testTag("input_demand_sales_30d"),
                            singleLine = true
                        )
                        OutlinedTextField(
                            value = inputState.salesHistory90d.toString(),
                            onValueChange = { it.toIntOrNull()?.let { v -> viewModel.updateSalesHistory90d(v) } },
                            label = { Text("Sales 90D") },
                            modifier = Modifier
                                .weight(1f)
                                .testTag("input_demand_sales_90d"),
                            singleLine = true
                        )
                        OutlinedTextField(
                            value = inputState.salesHistory1y.toString(),
                            onValueChange = { it.toIntOrNull()?.let { v -> viewModel.updateSalesHistory1y(v) } },
                            label = { Text("Sales 1Y") },
                            modifier = Modifier
                                .weight(1f)
                                .testTag("input_demand_sales_1y"),
                            singleLine = true
                        )
                    }

                    Button(
                        onClick = { viewModel.generateForecast() },
                        enabled = !isLoading,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp)
                            .testTag("generate_demand_forecast_button"),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        if (isLoading) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(22.dp),
                                color = MaterialTheme.colorScheme.onPrimary,
                                strokeWidth = 2.5.dp
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Text("Running AI Predictive Demand Engine...", fontWeight = FontWeight.Bold)
                        } else {
                            Icon(Icons.Default.Psychology, contentDescription = null)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Generate AI Demand Forecast", fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }

        // 10 Key Outputs Result View
        if (forecastResult != null) {
            val result = forecastResult

            // Action banner & Fast moving badges
            item {
                DemandHighlightBanner(result = result, currencyFormatter = currencyFormatter)
            }

            // Time Horizon Multi-Period Selector
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Predictive Demand Horizons",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                SingleChoiceSegmentedButtonRow(modifier = Modifier.fillMaxWidth()) {
                    ForecastHorizon.values().forEachIndexed { index, horizon ->
                        SegmentedButton(
                            shape = SegmentedButtonDefaults.itemShape(index = index, count = ForecastHorizon.values().size),
                            onClick = { viewModel.selectHorizon(horizon) },
                            selected = selectedHorizon == horizon,
                            label = { Text(horizon.label, fontSize = 12.sp) },
                            modifier = Modifier.testTag("horizon_btn_${horizon.name}")
                        )
                    }
                }
            }

            // Dynamic Horizon Card
            item {
                DynamicHorizonDemandCard(
                    result = result,
                    horizon = selectedHorizon,
                    currencyFormatter = currencyFormatter
                )
            }

            // Multi-Horizon Comparison Chart Matrix
            item {
                DemandForecastMultiPeriodGrid(
                    result = result,
                    currencyFormatter = currencyFormatter
                )
            }

            // Reorder & Inventory Action Recommendations
            item {
                InventoryActionCard(
                    result = result,
                    onApplyReorder = { viewModel.applyReorderPlan(result) },
                    currencyFormatter = currencyFormatter
                )
            }

            // Strategic Rationale & Seasonality Insights
            item {
                ElevatedCard(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Lightbulb, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "AI Neural Rationale & Peak Timing",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Text(
                            text = result.aiRationale,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        HorizontalDivider()
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = "Seasonal Peak Window",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Text(
                                    text = result.seasonalPeakTiming,
                                    style = MaterialTheme.typography.bodyMedium,
                                    fontWeight = FontWeight.SemiBold,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }
                            AssistChip(
                                onClick = {
                                    Toast.makeText(context, "Exporting Detailed Forecast PDF...", Toast.LENGTH_SHORT).show()
                                },
                                label = { Text("Export PDF") },
                                leadingIcon = { Icon(Icons.Default.PictureAsPdf, contentDescription = null, modifier = Modifier.size(16.dp)) },
                                modifier = Modifier.testTag("export_pdf_button")
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun DemandHighlightBanner(
    result: AIDemandForecastEntity,
    currencyFormatter: NumberFormat
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("demand_highlight_banner"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
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
                Column {
                    Text(
                        text = result.productName,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    Text(
                        text = "${result.sku} • ${result.category}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f)
                    )
                }
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = MaterialTheme.colorScheme.primary
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.Default.Star,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onPrimary,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "${result.growthOpportunityScore}/100",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }
            }

            // Key Quick Badges: Fast Moving, Dead Stock Risk, Growth Opportunity
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Surface(
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(8.dp),
                    color = if (result.isFastMoving) Color(0xFFE8F5E9) else MaterialTheme.colorScheme.surface
                ) {
                    Column(modifier = Modifier.padding(8.dp)) {
                        Text(
                            text = "Velocity Status",
                            style = MaterialTheme.typography.labelSmall,
                            color = Color.DarkGray
                        )
                        Text(
                            text = if (result.isFastMoving) "⚡ Fast Moving" else "🔄 Steady Flow",
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.Bold,
                            color = if (result.isFastMoving) Color(0xFF2E7D32) else Color(0xFF1565C0)
                        )
                    }
                }

                Surface(
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(8.dp),
                    color = if (result.deadStockRiskScore > 40) Color(0xFFFFEBEE) else Color(0xFFE8F5E9)
                ) {
                    Column(modifier = Modifier.padding(8.dp)) {
                        Text(
                            text = "Dead Stock Risk",
                            style = MaterialTheme.typography.labelSmall,
                            color = Color.DarkGray
                        )
                        Text(
                            text = result.deadStockRisk,
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.Bold,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            color = if (result.deadStockRiskScore > 40) Color(0xFFC62828) else Color(0xFF2E7D32)
                        )
                    }
                }

                Surface(
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(8.dp),
                    color = MaterialTheme.colorScheme.surface
                ) {
                    Column(modifier = Modifier.padding(8.dp)) {
                        Text(
                            text = "Stockout Risk",
                            style = MaterialTheme.typography.labelSmall,
                            color = Color.DarkGray
                        )
                        Text(
                            text = "${result.stockOutRiskProbability}% Prob",
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.Bold,
                            color = if (result.stockOutRiskProbability > 60) Color(0xFFE65100) else Color(0xFF2E7D32)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun DynamicHorizonDemandCard(
    result: AIDemandForecastEntity,
    horizon: ForecastHorizon,
    currencyFormatter: NumberFormat
) {
    val units = when (horizon) {
        ForecastHorizon.SEVEN_DAYS -> result.forecast7dUnits
        ForecastHorizon.THIRTY_DAYS -> result.forecast30dUnits
        ForecastHorizon.NINETY_DAYS -> result.forecast90dUnits
        ForecastHorizon.ONE_YEAR -> result.forecast1yUnits
    }

    val revenue = when (horizon) {
        ForecastHorizon.SEVEN_DAYS -> result.forecast7dRevenue
        ForecastHorizon.THIRTY_DAYS -> result.forecast30dRevenue
        ForecastHorizon.NINETY_DAYS -> result.forecast90dRevenue
        ForecastHorizon.ONE_YEAR -> result.forecast1yRevenue
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("dynamic_horizon_card"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.4f)
        )
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
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.TrendingUp,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.secondary
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "${horizon.label} Projected Demand",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
                Text(
                    text = "${units} Units",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.ExtraBold,
                    color = MaterialTheme.colorScheme.secondary
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Projected Sales Value",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = currencyFormatter.format(revenue),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }

                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = "Daily Run-Rate",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    val daily = (units.toDouble() / horizon.days).coerceAtLeast(0.1)
                    Text(
                        text = "%.1f units/day".format(daily),
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            // Visual Progress Bar relative to stock
            val stockCoverageDays = if (units > 0) ((result.currentInventory.toDouble() / units) * horizon.days).toInt() else 0
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Current Stock Coverage: $stockCoverageDays Days",
                        style = MaterialTheme.typography.labelSmall,
                        color = if (stockCoverageDays < horizon.days) Color(0xFFC62828) else Color(0xFF2E7D32),
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "${result.currentInventory} on hand",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                LinearProgressIndicator(
                    progress = { (result.currentInventory.toFloat() / (units.toFloat() + 1f)).coerceIn(0f, 1f) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp)
                        .clip(RoundedCornerShape(4.dp)),
                    color = if (stockCoverageDays < horizon.days) Color(0xFFE65100) else Color(0xFF2E7D32),
                    trackColor = MaterialTheme.colorScheme.surfaceVariant
                )
            }
        }
    }
}

@Composable
private fun DemandForecastMultiPeriodGrid(
    result: AIDemandForecastEntity,
    currencyFormatter: NumberFormat
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "Full Multi-Period Forecast Matrix",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                PeriodStatBox(
                    title = "7 Days",
                    units = result.forecast7dUnits,
                    revenue = currencyFormatter.format(result.forecast7dRevenue),
                    modifier = Modifier.weight(1f)
                )
                PeriodStatBox(
                    title = "30 Days",
                    units = result.forecast30dUnits,
                    revenue = currencyFormatter.format(result.forecast30dRevenue),
                    modifier = Modifier.weight(1f)
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                PeriodStatBox(
                    title = "90 Days",
                    units = result.forecast90dUnits,
                    revenue = currencyFormatter.format(result.forecast90dRevenue),
                    modifier = Modifier.weight(1f)
                )
                PeriodStatBox(
                    title = "1 Year",
                    units = result.forecast1yUnits,
                    revenue = currencyFormatter.format(result.forecast1yRevenue),
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
private fun PeriodStatBox(
    title: String,
    units: Int,
    revenue: String,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = "$units units",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.ExtraBold
            )
            Text(
                text = revenue,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
private fun InventoryActionCard(
    result: AIDemandForecastEntity,
    onApplyReorder: () -> Unit,
    currencyFormatter: NumberFormat
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("inventory_action_card"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.tertiaryContainer.copy(alpha = 0.4f)
        )
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
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Factory,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.tertiary
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Procurement & Safety Stock",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
                if (result.isApplied) {
                    AssistChip(
                        onClick = {},
                        label = { Text("PO Generated", color = Color(0xFF2E7D32)) },
                        leadingIcon = { Icon(Icons.Default.CheckCircle, contentDescription = null, tint = Color(0xFF2E7D32), modifier = Modifier.size(16.dp)) }
                    )
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Surface(
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(10.dp),
                    color = MaterialTheme.colorScheme.surface
                ) {
                    Column(modifier = Modifier.padding(10.dp)) {
                        Text(
                            text = "Recommended Reorder",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            text = "${result.reorderQuantity} units",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            text = currencyFormatter.format(result.reorderQuantity * result.unitPrice),
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                Surface(
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(10.dp),
                    color = MaterialTheme.colorScheme.surface
                ) {
                    Column(modifier = Modifier.padding(10.dp)) {
                        Text(
                            text = "Safety Buffer Stock",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            text = "${result.safetyStockRecommendation} units",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.secondary
                        )
                        Text(
                            text = "Mitigates loom delays",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            Text(
                text = "Directive: ${result.recommendedAction}",
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurface
            )

            Button(
                onClick = onApplyReorder,
                enabled = !result.isApplied,
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("apply_reorder_po_button"),
                shape = RoundedCornerShape(10.dp)
            ) {
                Icon(Icons.Default.ShoppingCartCheckout, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text(if (result.isApplied) "Loom Production Order Dispatched" else "Authorize Weaver Production PO (${result.reorderQuantity} Units)")
            }
        }
    }
}

@Composable
private fun InventoryPlannerTab(
    forecastResult: AIDemandForecastEntity?,
    modelsList: List<AIDemandModelEntity>,
    onApplyReorder: (AIDemandForecastEntity) -> Unit,
    currencyFormatter: NumberFormat
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                text = "Autonomous Replenishment Models",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
        }

        items(modelsList) { model ->
            ElevatedCard(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp)
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
                        Text(
                            text = model.modelName,
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold
                        )
                        Badge {
                            Text(model.category)
                        }
                    }

                    Text(
                        text = model.description,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Surface(
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(6.dp),
                            color = MaterialTheme.colorScheme.surfaceVariant
                        ) {
                            Text(
                                text = "Season Mult: ${model.seasonalityMultiplier}x",
                                modifier = Modifier.padding(6.dp),
                                style = MaterialTheme.typography.labelSmall
                            )
                        }
                        Surface(
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(6.dp),
                            color = MaterialTheme.colorScheme.surfaceVariant
                        ) {
                            Text(
                                text = "Festival Surge: ${model.festivalSpikeMultiplier}x",
                                modifier = Modifier.padding(6.dp),
                                style = MaterialTheme.typography.labelSmall
                            )
                        }
                        Surface(
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(6.dp),
                            color = MaterialTheme.colorScheme.surfaceVariant
                        ) {
                            Text(
                                text = "Buffer: ${model.leadTimeBufferDays}d",
                                modifier = Modifier.padding(6.dp),
                                style = MaterialTheme.typography.labelSmall
                            )
                        }
                    }
                }
            }
        }

        if (forecastResult != null) {
            item {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Active Product Stock Simulation",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }

            item {
                InventoryActionCard(
                    result = forecastResult,
                    onApplyReorder = { onApplyReorder(forecastResult) },
                    currencyFormatter = currencyFormatter
                )
            }
        }
    }
}

@Composable
private fun RiskRadarTab(
    forecastResult: AIDemandForecastEntity?,
    modelsList: List<AIDemandModelEntity>,
    currencyFormatter: NumberFormat
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                text = "Dead Stock & Supply Chain Risk Radar",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
        }

        if (forecastResult != null) {
            item {
                ElevatedCard(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp)
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
                                text = "Dead Stock Risk Analysis",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                            Surface(
                                shape = RoundedCornerShape(6.dp),
                                color = if (forecastResult.deadStockRiskScore > 40) Color(0xFFFFEBEE) else Color(0xFFE8F5E9)
                            ) {
                                Text(
                                    text = "Risk Score: ${forecastResult.deadStockRiskScore}/100",
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = if (forecastResult.deadStockRiskScore > 40) Color(0xFFC62828) else Color(0xFF2E7D32)
                                )
                            }
                        }

                        Text(
                            text = "Assessment: ${forecastResult.deadStockRisk}",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.SemiBold
                        )

                        LinearProgressIndicator(
                            progress = { (forecastResult.deadStockRiskScore / 100f).coerceIn(0f, 1f) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(8.dp)
                                .clip(RoundedCornerShape(4.dp)),
                            color = if (forecastResult.deadStockRiskScore > 40) Color(0xFFC62828) else Color(0xFF2E7D32)
                        )

                        HorizontalDivider()

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column {
                                Text("Fast-Moving Trajectory", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                Text(forecastResult.fastMovingPrediction, style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.SemiBold)
                            }
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column {
                                Text("Slow-Moving / Drag Assessment", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                Text(forecastResult.slowMovingPrediction, style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.SemiBold)
                            }
                        }
                    }
                }
            }
        }

        item {
            ElevatedCard(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(
                        text = "Warehouse Risk Mitigation Protocols",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold
                    )
                    Text("• Saree collections exceeding 120 days of stock holding trigger auto-rebate dealer bundles.")
                    Text("• Bridal Silk lots are monitored against wedding muhurat calendars to prevent post-season overhang.")
                    Text("• Weaving capacity reservations are released 4 weeks before festival peak to prevent idle loom costs.")
                }
            }
        }
    }
}

@Composable
private fun DemandHistoryLogsTab(
    historyList: List<AIDemandHistoryEntity>,
    onDeleteHistory: (Long) -> Unit,
    onClearAll: () -> Unit,
    currencyFormatter: NumberFormat
) {
    val dateFormatter = remember { SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.getDefault()) }

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
                Text(
                    text = "Demand Forecast Audit Trail (${historyList.size})",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                if (historyList.isNotEmpty()) {
                    TextButton(onClick = onClearAll) {
                        Text("Clear All")
                    }
                }
            }
        }

        if (historyList.isEmpty()) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 48.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No forecast audit records yet.\nRun a simulation in Forecast Studio.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }

        items(historyList, key = { it.historyId }) { entry ->
            ElevatedCard(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(
                    modifier = Modifier.padding(12.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = entry.productName,
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold
                        )
                        IconButton(
                            onClick = { onDeleteHistory(entry.historyId) },
                            modifier = Modifier.size(28.dp)
                        ) {
                            Icon(Icons.Default.DeleteOutline, contentDescription = "Delete", modifier = Modifier.size(18.dp))
                        }
                    }

                    Text(
                        text = "${entry.sku} • ${entry.category}",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text("30D: ${entry.forecast30dUnits} u", style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.SemiBold)
                        Text("90D: ${entry.forecast90dUnits} u", style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.SemiBold)
                        Text("Reorder: ${entry.reorderQuantity} u", style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.primary)
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = entry.actionTaken,
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.secondary
                        )
                        Text(
                            text = dateFormatter.format(Date(entry.timestamp)),
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }
}
