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
import com.example.vascs.data.model.AIInventoryAlertEntity
import com.example.vascs.data.model.AIInventoryForecastEntity
import com.example.vascs.data.model.AIInventoryHealthEntity
import com.example.vascs.data.model.AIInventoryRecommendationEntity
import com.example.vascs.ui.theme.GoldAccent
import com.example.vascs.ui.theme.Maroon500
import com.example.vascs.ui.theme.Maroon700
import com.example.vascs.viewmodel.AIInventoryViewModel
import com.example.vascs.viewmodel.InventoryPreset
import java.text.NumberFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AIInventoryScreen(
    viewModel: AIInventoryViewModel,
    onNavigateBack: () -> Unit
) {
    val context = LocalContext.current
    val inputState by viewModel.inputState.collectAsState()
    val loadingState by viewModel.loadingState.collectAsState()
    val errorState by viewModel.errorState.collectAsState()
    val exportMessage by viewModel.exportMessage.collectAsState()
    val activeForecast by viewModel.activeForecast.collectAsState()
    val allForecasts by viewModel.allForecasts.collectAsState()
    val fastMovingStock by viewModel.fastMovingStock.collectAsState()
    val slowMovingStock by viewModel.slowMovingStock.collectAsState()
    val deadStockList by viewModel.deadStockList.collectAsState()
    val allAlerts by viewModel.allAlerts.collectAsState()
    val activeAlerts by viewModel.activeAlerts.collectAsState()
    val latestHealth by viewModel.latestHealth.collectAsState()
    val allRecommendations by viewModel.allRecommendations.collectAsState()
    val pendingRecommendations by viewModel.pendingRecommendations.collectAsState()
    val selectedTab by viewModel.selectedTab.collectAsState()
    val velocityFilter by viewModel.velocityFilter.collectAsState()
    val selectedWarehouse by viewModel.selectedWarehouse.collectAsState()

    val currencyFormatter = remember {
        NumberFormat.getCurrencyInstance(Locale("en", "IN")).apply {
            maximumFractionDigits = 0
        }
    }

    LaunchedEffect(exportMessage) {
        exportMessage?.let {
            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
            viewModel.clearExportMessage()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "AI INVENTORY INTELLIGENCE",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = GoldAccent,
                                letterSpacing = 1.1.sp
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Surface(
                                color = Color(0xFF047857),
                                shape = RoundedCornerShape(4.dp)
                            ) {
                                Text(
                                    text = "U6 ENGINE",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFFD1FAE5),
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                        }
                        Text(
                            text = "Velocity Scoring • Reorder Automation • Dead Stock & Health",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color(0xFFD1D5DB)
                        )
                    }
                },
                navigationIcon = {
                    IconButton(
                        onClick = onNavigateBack,
                        modifier = Modifier.testTag("inventory_back_button")
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = GoldAccent
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = { viewModel.exportInventoryReport("PDF") },
                        modifier = Modifier.testTag("inventory_export_pdf_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.PictureAsPdf,
                            contentDescription = "Export PDF",
                            tint = GoldAccent
                        )
                    }
                    IconButton(
                        onClick = { viewModel.exportInventoryReport("EXCEL") },
                        modifier = Modifier.testTag("inventory_export_excel_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.TableChart,
                            contentDescription = "Export Excel",
                            tint = GoldAccent
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Maroon700,
                    titleContentColor = GoldAccent
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color(0xFF0F172A))
        ) {
            // Live KPI Header Strip
            InventoryKpiStrip(
                health = latestHealth,
                activeAlertsCount = activeAlerts.size,
                deadStockCount = deadStockList.size,
                currencyFormatter = currencyFormatter
            )

            // Primary Navigation Tabs
            ScrollableTabRow(
                selectedTabIndex = selectedTab,
                containerColor = Color(0xFF1E293B),
                contentColor = GoldAccent,
                edgePadding = 12.dp,
                divider = {}
            ) {
                Tab(
                    selected = selectedTab == 0,
                    onClick = { viewModel.setSelectedTab(0) },
                    modifier = Modifier.testTag("tab_ai_intelligence"),
                    text = { Text("AI Intelligence", fontWeight = if (selectedTab == 0) FontWeight.Bold else FontWeight.Normal) },
                    icon = { Icon(Icons.Default.AutoAwesome, contentDescription = null, modifier = Modifier.size(18.dp)) }
                )
                Tab(
                    selected = selectedTab == 1,
                    onClick = { viewModel.setSelectedTab(1) },
                    modifier = Modifier.testTag("tab_stock_velocity"),
                    text = { Text("Stock Velocity (${allForecasts.size})", fontWeight = if (selectedTab == 1) FontWeight.Bold else FontWeight.Normal) },
                    icon = { Icon(Icons.AutoMirrored.Filled.TrendingUp, contentDescription = null, modifier = Modifier.size(18.dp)) }
                )
                Tab(
                    selected = selectedTab == 2,
                    onClick = { viewModel.setSelectedTab(2) },
                    modifier = Modifier.testTag("tab_reorder_plan"),
                    text = { Text("Reorder & POs", fontWeight = if (selectedTab == 2) FontWeight.Bold else FontWeight.Normal) },
                    icon = { Icon(Icons.Default.ShoppingCart, contentDescription = null, modifier = Modifier.size(18.dp)) }
                )
                Tab(
                    selected = selectedTab == 3,
                    onClick = { viewModel.setSelectedTab(3) },
                    modifier = Modifier.testTag("tab_warehouse_health"),
                    text = { Text("Warehouse & Alerts (${activeAlerts.size})", fontWeight = if (selectedTab == 3) FontWeight.Bold else FontWeight.Normal) },
                    icon = { Icon(Icons.Default.Warning, contentDescription = null, modifier = Modifier.size(18.dp)) }
                )
                Tab(
                    selected = selectedTab == 4,
                    onClick = { viewModel.setSelectedTab(4) },
                    modifier = Modifier.testTag("tab_optimization_plan"),
                    text = { Text("AI Plan (${pendingRecommendations.size})", fontWeight = if (selectedTab == 4) FontWeight.Bold else FontWeight.Normal) },
                    icon = { Icon(Icons.Default.Checklist, contentDescription = null, modifier = Modifier.size(18.dp)) }
                )
            }

            // Tab Content
            when (selectedTab) {
                0 -> AIInventoryAnalyzerTab(
                    viewModel = viewModel,
                    inputState = inputState,
                    loadingState = loadingState,
                    errorState = errorState,
                    activeForecast = activeForecast,
                    currencyFormatter = currencyFormatter
                )
                1 -> StockVelocityListTab(
                    allForecasts = allForecasts,
                    fastMovingStock = fastMovingStock,
                    slowMovingStock = slowMovingStock,
                    deadStockList = deadStockList,
                    velocityFilter = velocityFilter,
                    onFilterChange = { viewModel.setVelocityFilter(it) },
                    currencyFormatter = currencyFormatter
                )
                2 -> ReorderPlanTab(
                    forecasts = allForecasts,
                    onGeneratePO = { viewModel.exportInventoryReport("PURCHASE_ORDER") },
                    currencyFormatter = currencyFormatter
                )
                3 -> WarehouseAndAlertsTab(
                    health = latestHealth,
                    alerts = allAlerts,
                    selectedWarehouse = selectedWarehouse,
                    onWarehouseSelect = { viewModel.setSelectedWarehouse(it) },
                    onResolveAlert = { viewModel.resolveAlert(it) },
                    onDeleteAlert = { viewModel.deleteAlert(it) },
                    currencyFormatter = currencyFormatter
                )
                4 -> AIOptimizationPlanTab(
                    recommendations = allRecommendations,
                    onApply = { viewModel.applyRecommendation(it) },
                    currencyFormatter = currencyFormatter
                )
            }
        }
    }
}

// -------------------------------------------------------------
// COMPONENT 1: KPI STRIP
// -------------------------------------------------------------
@Composable
private fun InventoryKpiStrip(
    health: AIInventoryHealthEntity?,
    activeAlertsCount: Int,
    deadStockCount: Int,
    currencyFormatter: NumberFormat
) {
    Surface(
        color = Color(0xFF1E293B),
        border = BorderStroke(1.dp, Color(0xFF334155))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            KpiMiniItem(
                label = "Health Score",
                value = "${health?.overallHealthScore ?: 88}/100",
                color = Color(0xFF10B981)
            )
            KpiMiniItem(
                label = "Fast Moving",
                value = "${health?.fastMovingPercentage ?: 58.4}%",
                color = Color(0xFF3B82F6)
            )
            KpiMiniItem(
                label = "Dead Stock",
                value = "$deadStockCount SKUs",
                color = if (deadStockCount > 0) Color(0xFFEF4444) else Color(0xFF10B981)
            )
            KpiMiniItem(
                label = "Active Alerts",
                value = "$activeAlertsCount Critical",
                color = if (activeAlertsCount > 0) Color(0xFFF59E0B) else Color(0xFF10B981)
            )
        }
    }
}

@Composable
private fun KpiMiniItem(label: String, value: String, color: Color) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = label, fontSize = 10.sp, color = Color(0xFF94A3B8))
        Text(text = value, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = color)
    }
}

// -------------------------------------------------------------
// TAB 0: AI INVENTORY ANALYZER TAB
// -------------------------------------------------------------
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AIInventoryAnalyzerTab(
    viewModel: AIInventoryViewModel,
    inputState: com.example.vascs.viewmodel.InventoryInputState,
    loadingState: Boolean,
    errorState: String?,
    activeForecast: AIInventoryForecastEntity?,
    currencyFormatter: NumberFormat
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Presets Selector
        item {
            Text(
                text = "SELECT INVENTORY SKU PRESET",
                style = MaterialTheme.typography.labelSmall,
                color = GoldAccent,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(viewModel.presets) { preset ->
                    val isSelected = inputState.sku == preset.sku
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = if (isSelected) GoldAccent else Color(0xFF1E293B),
                        border = BorderStroke(1.dp, if (isSelected) GoldAccent else Color(0xFF475569)),
                        modifier = Modifier
                            .clickable { viewModel.applyPreset(preset) }
                            .testTag("preset_${preset.sku}")
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = if (preset.name.contains("Dead")) Icons.Default.Warning else Icons.Default.Inventory,
                                contentDescription = null,
                                modifier = Modifier.size(16.dp),
                                tint = if (isSelected) Color.Black else GoldAccent
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = preset.name,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (isSelected) Color.Black else Color.White
                            )
                        }
                    }
                }
            }
        }

        // Input Form Card
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = Color(0xFF1E293B)),
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(1.dp, Color(0xFF334155))
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "SKU & WAREHOUSE CONFIGURATION",
                        style = MaterialTheme.typography.titleSmall,
                        color = GoldAccent,
                        fontWeight = FontWeight.Bold
                    )

                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        OutlinedTextField(
                            value = inputState.productName,
                            onValueChange = { viewModel.updateProductName(it) },
                            label = { Text("Product Name", color = Color(0xFF94A3B8)) },
                            modifier = Modifier.weight(1.5f).testTag("input_product_name"),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = Color.White,
                                unfocusedTextColor = Color.White,
                                focusedBorderColor = GoldAccent
                            )
                        )
                        OutlinedTextField(
                            value = inputState.sku,
                            onValueChange = { viewModel.updateSku(it) },
                            label = { Text("SKU Code", color = Color(0xFF94A3B8)) },
                            modifier = Modifier.weight(1f).testTag("input_sku"),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = Color.White,
                                unfocusedTextColor = Color.White,
                                focusedBorderColor = GoldAccent
                            )
                        )
                    }

                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        OutlinedTextField(
                            value = inputState.category,
                            onValueChange = { viewModel.updateCategory(it) },
                            label = { Text("Category", color = Color(0xFF94A3B8)) },
                            modifier = Modifier.weight(1f),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = Color.White,
                                unfocusedTextColor = Color.White
                            )
                        )
                        OutlinedTextField(
                            value = inputState.warehouseLocation,
                            onValueChange = { viewModel.updateWarehouse(it) },
                            label = { Text("Warehouse Vault", color = Color(0xFF94A3B8)) },
                            modifier = Modifier.weight(1f),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = Color.White,
                                unfocusedTextColor = Color.White
                            )
                        )
                    }

                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        OutlinedTextField(
                            value = inputState.currentStock.toString(),
                            onValueChange = { it.toIntOrNull()?.let { v -> viewModel.updateCurrentStock(v) } },
                            label = { Text("Stock (pcs)", color = Color(0xFF94A3B8)) },
                            modifier = Modifier.weight(1f).testTag("input_stock"),
                            colors = OutlinedTextFieldDefaults.colors(focusedTextColor = Color.White, unfocusedTextColor = Color.White)
                        )
                        OutlinedTextField(
                            value = inputState.salesHistory30d.toString(),
                            onValueChange = { it.toIntOrNull()?.let { v -> viewModel.updateSales30d(v) } },
                            label = { Text("Sales 30d", color = Color(0xFF94A3B8)) },
                            modifier = Modifier.weight(1f),
                            colors = OutlinedTextFieldDefaults.colors(focusedTextColor = Color.White, unfocusedTextColor = Color.White)
                        )
                        OutlinedTextField(
                            value = inputState.forecastDemand30d.toString(),
                            onValueChange = { it.toIntOrNull()?.let { v -> viewModel.updateForecast30d(v) } },
                            label = { Text("Demand 30d", color = Color(0xFF94A3B8)) },
                            modifier = Modifier.weight(1f),
                            colors = OutlinedTextFieldDefaults.colors(focusedTextColor = Color.White, unfocusedTextColor = Color.White)
                        )
                        OutlinedTextField(
                            value = inputState.dealerPendingOrders.toString(),
                            onValueChange = { it.toIntOrNull()?.let { v -> viewModel.updateDealerOrders(v) } },
                            label = { Text("Dealer Orders", color = Color(0xFF94A3B8)) },
                            modifier = Modifier.weight(1f),
                            colors = OutlinedTextFieldDefaults.colors(focusedTextColor = Color.White, unfocusedTextColor = Color.White)
                        )
                    }

                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        OutlinedTextField(
                            value = inputState.unitCostPrice.toInt().toString(),
                            onValueChange = { it.toDoubleOrNull()?.let { v -> viewModel.updateCostPrice(v) } },
                            label = { Text("Unit Cost (₹)", color = Color(0xFF94A3B8)) },
                            modifier = Modifier.weight(1f),
                            colors = OutlinedTextFieldDefaults.colors(focusedTextColor = Color.White, unfocusedTextColor = Color.White)
                        )
                        OutlinedTextField(
                            value = inputState.leadTimeDays.toString(),
                            onValueChange = { it.toIntOrNull()?.let { v -> viewModel.updateLeadTime(v) } },
                            label = { Text("Lead Time (Days)", color = Color(0xFF94A3B8)) },
                            modifier = Modifier.weight(1f),
                            colors = OutlinedTextFieldDefaults.colors(focusedTextColor = Color.White, unfocusedTextColor = Color.White)
                        )
                    }

                    // Run AI Button
                    Button(
                        onClick = { viewModel.generateInventoryIntelligence() },
                        enabled = !loadingState,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp)
                            .testTag("run_ai_inventory_button"),
                        colors = ButtonDefaults.buttonColors(containerColor = GoldAccent, contentColor = Color.Black),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        if (loadingState) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(22.dp),
                                color = Color.Black,
                                strokeWidth = 2.5.dp
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Text("EXECUTING GEMINI INVENTORY INFERENCE...", fontWeight = FontWeight.Bold)
                        } else {
                            Icon(Icons.Default.AutoAwesome, contentDescription = null, modifier = Modifier.size(20.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("RUN AI INVENTORY & REORDER ANALYSIS", fontWeight = FontWeight.Bold, letterSpacing = 0.8.sp)
                        }
                    }
                }
            }
        }

        // Error message if any
        if (errorState != null) {
            item {
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF7F1D1D)),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = errorState,
                        color = Color(0xFFFCA5A5),
                        modifier = Modifier.padding(12.dp),
                        fontSize = 13.sp
                    )
                }
            }
        }

        // Active Forecast Display Card
        if (activeForecast != null) {
            item {
                AIInventoryResultCard(forecast = activeForecast, currencyFormatter = currencyFormatter)
            }
        }
    }
}

@Composable
private fun AIInventoryResultCard(
    forecast: AIInventoryForecastEntity,
    currencyFormatter: NumberFormat
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color(0xFF0F2338)),
        shape = RoundedCornerShape(14.dp),
        border = BorderStroke(1.5.dp, GoldAccent),
        modifier = Modifier.fillMaxWidth().testTag("ai_inventory_result_card")
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Header with Velocity Badge
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = forecast.productName,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        text = "${forecast.sku} • ${forecast.warehouseLocation}",
                        fontSize = 12.sp,
                        color = Color(0xFF94A3B8)
                    )
                }

                VelocityBadge(velocity = forecast.velocityClassification)
            }

            HorizontalDivider(color = Color(0xFF334155))

            // 4 Grid metrics
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                MetricBox(
                    modifier = Modifier.weight(1f),
                    title = "Reorder Qty",
                    value = "${forecast.reorderQuantity} pcs",
                    subtitle = "Target Date: ${forecast.reorderDate}",
                    accentColor = Color(0xFF10B981)
                )
                MetricBox(
                    modifier = Modifier.weight(1f),
                    title = "Days of Supply",
                    value = "${forecast.daysOfSupply} Days",
                    subtitle = "Safety: ${forecast.safetyStockUnits} pcs",
                    accentColor = if (forecast.daysOfSupply < 15) Color(0xFFEF4444) else Color(0xFF3B82F6)
                )
            }

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                MetricBox(
                    modifier = Modifier.weight(1f),
                    title = "Reorder Cost",
                    value = currencyFormatter.format(forecast.estimatedReorderCost),
                    subtitle = "Monthly Hold: ₹${forecast.projectedHoldingCostMonthly.toInt()}",
                    accentColor = GoldAccent
                )
                MetricBox(
                    modifier = Modifier.weight(1f),
                    title = "Stockout Risk",
                    value = if (forecast.stockoutRiskDays > 0) "${forecast.stockoutRiskDays} Days" else "Safe",
                    subtitle = "Seasonal: ${forecast.seasonalMultiplier}x",
                    accentColor = if (forecast.stockoutRiskDays > 0) Color(0xFFEF4444) else Color(0xFF10B981)
                )
            }

            // Scores Bar
            Surface(
                color = Color(0xFF1E293B),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    ScorePill(label = "Fast Moving", score = forecast.fastMovingScore, color = Color(0xFF10B981))
                    ScorePill(label = "Dead Risk", score = forecast.deadStockRiskScore, color = Color(0xFFEF4444))
                    ScorePill(label = "Opportunity", score = forecast.growthOpportunityScore, color = Color(0xFF3B82F6))
                }
            }

            // AI Rationale
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF1E293B), RoundedCornerShape(8.dp))
                    .padding(14.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Psychology, contentDescription = null, tint = GoldAccent, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "GEMINI OPTIMIZATION RATIONALE",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = GoldAccent
                    )
                }
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = forecast.aiOptimizationRationale,
                    fontSize = 13.sp,
                    color = Color(0xFFE2E8F0),
                    lineHeight = 18.sp
                )
            }
        }
    }
}

@Composable
private fun VelocityBadge(velocity: String) {
    val (bgColor, textColor, label) = when (velocity) {
        "FAST_MOVING" -> Triple(Color(0xFF065F46), Color(0xFF6EE7B7), "⚡ FAST MOVING")
        "SLOW_MOVING" -> Triple(Color(0xFF854D0E), Color(0xFFFDE047), "⏱ SLOW MOVING")
        "DEAD_STOCK" -> Triple(Color(0xFF991B1B), Color(0xFFFCA5A5), "💀 DEAD STOCK")
        else -> Triple(Color(0xFF1E3A8A), Color(0xFF93C5FD), "⚖ MODERATE")
    }

    Surface(
        color = bgColor,
        shape = RoundedCornerShape(6.dp)
    ) {
        Text(
            text = label,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            color = textColor,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
        )
    }
}

@Composable
private fun MetricBox(
    modifier: Modifier = Modifier,
    title: String,
    value: String,
    subtitle: String,
    accentColor: Color
) {
    Surface(
        color = Color(0xFF1E293B),
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(1.dp, Color(0xFF334155)),
        modifier = modifier
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(text = title, fontSize = 11.sp, color = Color(0xFF94A3B8))
            Text(text = value, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = accentColor)
            Spacer(modifier = Modifier.height(2.dp))
            Text(text = subtitle, fontSize = 10.sp, color = Color(0xFFCBD5E1))
        }
    }
}

@Composable
private fun ScorePill(label: String, score: Int, color: Color) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = label, fontSize = 10.sp, color = Color(0xFF94A3B8))
        Text(text = "$score/100", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = color)
    }
}

// -------------------------------------------------------------
// TAB 1: STOCK VELOCITY LIST TAB
// -------------------------------------------------------------
@Composable
private fun StockVelocityListTab(
    allForecasts: List<AIInventoryForecastEntity>,
    fastMovingStock: List<AIInventoryForecastEntity>,
    slowMovingStock: List<AIInventoryForecastEntity>,
    deadStockList: List<AIInventoryForecastEntity>,
    velocityFilter: String,
    onFilterChange: (String) -> Unit,
    currencyFormatter: NumberFormat
) {
    val filteredList = remember(velocityFilter, allForecasts, fastMovingStock, slowMovingStock, deadStockList) {
        when (velocityFilter) {
            "FAST_MOVING" -> fastMovingStock
            "SLOW_MOVING" -> slowMovingStock
            "DEAD_STOCK" -> deadStockList
            else -> allForecasts
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Filter Chips
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            VelocityFilterChip("ALL", "All (${allForecasts.size})", velocityFilter == "ALL") { onFilterChange("ALL") }
            VelocityFilterChip("FAST_MOVING", "Fast (${fastMovingStock.size})", velocityFilter == "FAST_MOVING") { onFilterChange("FAST_MOVING") }
            VelocityFilterChip("SLOW_MOVING", "Slow (${slowMovingStock.size})", velocityFilter == "SLOW_MOVING") { onFilterChange("SLOW_MOVING") }
            VelocityFilterChip("DEAD_STOCK", "Dead (${deadStockList.size})", velocityFilter == "DEAD_STOCK") { onFilterChange("DEAD_STOCK") }
        }

        // List
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(filteredList) { item ->
                StockVelocityCard(item = item, currencyFormatter = currencyFormatter)
            }
        }
    }
}

@Composable
private fun VelocityFilterChip(type: String, label: String, isSelected: Boolean, onClick: () -> Unit) {
    Surface(
        shape = RoundedCornerShape(6.dp),
        color = if (isSelected) GoldAccent else Color(0xFF1E293B),
        border = BorderStroke(1.dp, if (isSelected) GoldAccent else Color(0xFF475569)),
        modifier = Modifier.clickable { onClick() }
    ) {
        Text(
            text = label,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            color = if (isSelected) Color.Black else Color.White,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
        )
    }
}

@Composable
private fun StockVelocityCard(item: AIInventoryForecastEntity, currencyFormatter: NumberFormat) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1E293B)),
        shape = RoundedCornerShape(10.dp),
        border = BorderStroke(1.dp, Color(0xFF334155)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(text = item.productName, fontWeight = FontWeight.Bold, color = Color.White, fontSize = 14.sp)
                    Text(text = "${item.sku} • ${item.category}", fontSize = 11.sp, color = Color(0xFF94A3B8))
                }
                VelocityBadge(velocity = item.velocityClassification)
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Current Stock: ${item.currentStock} pcs", fontSize = 12.sp, color = Color(0xFFE2E8F0))
                Text(text = "Days of Supply: ${item.daysOfSupply}d", fontSize = 12.sp, color = Color(0xFF38BDF8))
                Text(text = "Reorder: ${item.reorderQuantity} pcs", fontSize = 12.sp, color = Color(0xFF34D399))
            }

            Text(
                text = item.aiOptimizationRationale,
                fontSize = 11.sp,
                color = Color(0xFF94A3B8),
                lineHeight = 15.sp
            )
        }
    }
}

// -------------------------------------------------------------
// TAB 2: REORDER & REPLENISHMENT TAB
// -------------------------------------------------------------
@Composable
private fun ReorderPlanTab(
    forecasts: List<AIInventoryForecastEntity>,
    onGeneratePO: () -> Unit,
    currencyFormatter: NumberFormat
) {
    val reorderItems = remember(forecasts) {
        forecasts.filter { it.reorderQuantity > 0 }
    }
    val totalCost = remember(reorderItems) {
        reorderItems.sumOf { it.estimatedReorderCost }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = Color(0xFF064E3B)),
                shape = RoundedCornerShape(12.dp)
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(text = "AUTOMATED REORDER BATCH", fontWeight = FontWeight.Bold, color = Color(0xFF6EE7B7), fontSize = 13.sp)
                        Text(text = "${reorderItems.size} SKUs Require Loom / Weaver Allocation", fontSize = 11.sp, color = Color(0xFFD1FAE5))
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(text = "Total Investment: ${currencyFormatter.format(totalCost)}", fontWeight = FontWeight.Bold, color = GoldAccent, fontSize = 16.sp)
                    }

                    Button(
                        onClick = onGeneratePO,
                        colors = ButtonDefaults.buttonColors(containerColor = GoldAccent, contentColor = Color.Black),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.testTag("generate_po_button")
                    ) {
                        Icon(Icons.Default.ReceiptLong, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("GENERATE PO", fontWeight = FontWeight.Bold, fontSize = 11.sp)
                    }
                }
            }
        }

        items(reorderItems) { item ->
            Card(
                colors = CardDefaults.cardColors(containerColor = Color(0xFF1E293B)),
                shape = RoundedCornerShape(10.dp),
                border = BorderStroke(1.dp, Color(0xFF334155)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text(text = item.productName, fontWeight = FontWeight.Bold, color = Color.White, fontSize = 14.sp)
                        Text(text = "Target: ${item.reorderDate}", fontWeight = FontWeight.Bold, color = GoldAccent, fontSize = 12.sp)
                    }
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text(text = "Reorder Qty: ${item.reorderQuantity} pcs", color = Color(0xFF34D399), fontWeight = FontWeight.Bold, fontSize = 12.sp)
                        Text(text = "Est Cost: ${currencyFormatter.format(item.estimatedReorderCost)}", color = Color(0xFFFDE047), fontSize = 12.sp)
                        Text(text = "Safety Buffer: ${item.safetyStockUnits} pcs", color = Color(0xFF94A3B8), fontSize = 12.sp)
                    }
                    Text(text = item.aiOptimizationRationale, fontSize = 11.sp, color = Color(0xFFCBD5E1))
                }
            }
        }
    }
}

// -------------------------------------------------------------
// TAB 3: WAREHOUSE & HEALTH ALERTS TAB
// -------------------------------------------------------------
@Composable
private fun WarehouseAndAlertsTab(
    health: AIInventoryHealthEntity?,
    alerts: List<AIInventoryAlertEntity>,
    selectedWarehouse: String,
    onWarehouseSelect: (String) -> Unit,
    onResolveAlert: (Long) -> Unit,
    onDeleteAlert: (Long) -> Unit,
    currencyFormatter: NumberFormat
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Warehouse Health Card
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = Color(0xFF1E293B)),
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(1.dp, Color(0xFF334155))
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text(
                        text = "CENTRAL VAULT HEALTH AUDIT",
                        style = MaterialTheme.typography.titleSmall,
                        color = GoldAccent,
                        fontWeight = FontWeight.Bold
                    )

                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        MetricBox(
                            modifier = Modifier.weight(1f),
                            title = "Vault Utilization",
                            value = "${health?.warehouseUtilizationScore ?: 78}%",
                            subtitle = "Capacity: 2,000 pcs",
                            accentColor = Color(0xFF38BDF8)
                        )
                        MetricBox(
                            modifier = Modifier.weight(1f),
                            title = "Stock Turnover",
                            value = "${health?.stockTurnoverRatio ?: 6.4}x / yr",
                            subtitle = "Velocity Rating: Optimal",
                            accentColor = Color(0xFF34D399)
                        )
                    }

                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        MetricBox(
                            modifier = Modifier.weight(1f),
                            title = "Total Catalog Value",
                            value = currencyFormatter.format(health?.totalStockValueInr ?: 18500000.0),
                            subtitle = "1,450 Finished Silk Units",
                            accentColor = GoldAccent
                        )
                        MetricBox(
                            modifier = Modifier.weight(1f),
                            title = "Dead Stock Value",
                            value = currencyFormatter.format(health?.deadStockValueInr ?: 880000.0),
                            subtitle = "Target: Zero within 45d",
                            accentColor = Color(0xFFEF4444)
                        )
                    }
                }
            }
        }

        // Active Alerts Header
        item {
            Text(
                text = "INVENTORY ALERTS & EXCEPTION LOG (${alerts.size})",
                style = MaterialTheme.typography.labelSmall,
                color = GoldAccent,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp
            )
        }

        items(alerts) { alert ->
            val (bgColor, borderCol, icon) = when (alert.severity) {
                "CRITICAL" -> Triple(Color(0xFF450A0A), Color(0xFFEF4444), Icons.Default.Dangerous)
                "HIGH" -> Triple(Color(0xFF451A03), Color(0xFFF59E0B), Icons.Default.Warning)
                else -> Triple(Color(0xFF1E293B), Color(0xFF334155), Icons.Default.Info)
            }

            Card(
                colors = CardDefaults.cardColors(containerColor = bgColor),
                shape = RoundedCornerShape(10.dp),
                border = BorderStroke(1.dp, borderCol),
                modifier = Modifier.fillMaxWidth().testTag("alert_item_${alert.alertId}")
            ) {
                Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                            Icon(icon, contentDescription = null, tint = borderCol, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "${alert.alertType.replace('_', ' ')} • ${alert.productName}",
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                fontSize = 13.sp
                            )
                        }
                        Surface(
                            color = borderCol.copy(alpha = 0.2f),
                            shape = RoundedCornerShape(4.dp)
                        ) {
                            Text(
                                text = alert.severity,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = borderCol,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                    }

                    Text(text = alert.message, fontSize = 12.sp, color = Color(0xFFE2E8F0))
                    Text(text = "Action: ${alert.actionRequired}", fontSize = 11.sp, color = Color(0xFFFDE047), fontWeight = FontWeight.SemiBold)

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        if (!alert.isResolved) {
                            OutlinedButton(
                                onClick = { onResolveAlert(alert.alertId) },
                                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFF34D399)),
                                border = BorderStroke(1.dp, Color(0xFF34D399)),
                                modifier = Modifier.height(32.dp)
                            ) {
                                Text("Mark Resolved", fontSize = 10.sp, fontWeight = FontWeight.Bold)
                            }
                        } else {
                            Surface(color = Color(0xFF065F46), shape = RoundedCornerShape(4.dp)) {
                                Text(
                                    text = "RESOLVED",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF6EE7B7),
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

// -------------------------------------------------------------
// TAB 4: AI OPTIMIZATION PLAN TAB
// -------------------------------------------------------------
@Composable
private fun AIOptimizationPlanTab(
    recommendations: List<AIInventoryRecommendationEntity>,
    onApply: (Long) -> Unit,
    currencyFormatter: NumberFormat
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = Color(0xFF1E293B)),
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(1.dp, GoldAccent)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.AutoAwesome, contentDescription = null, tint = GoldAccent, modifier = Modifier.size(20.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "VASCS AI INVENTORY OPTIMIZATION STRATEGY",
                            fontWeight = FontWeight.Bold,
                            color = GoldAccent,
                            fontSize = 13.sp
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Automated suggestions to eliminate dead stock carrying costs and accelerate festival turnover.",
                        fontSize = 11.sp,
                        color = Color(0xFF94A3B8)
                    )
                }
            }
        }

        items(recommendations) { rec ->
            Card(
                colors = CardDefaults.cardColors(containerColor = Color(0xFF1E293B)),
                shape = RoundedCornerShape(10.dp),
                border = BorderStroke(1.dp, Color(0xFF334155)),
                modifier = Modifier.fillMaxWidth().testTag("rec_card_${rec.recommendationId}")
            ) {
                Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "${rec.productName} (${rec.sku})", fontWeight = FontWeight.Bold, color = Color.White, fontSize = 13.sp)
                        Surface(
                            color = if (rec.priority == "CRITICAL") Color(0xFF7F1D1D) else Color(0xFF1E3A8A),
                            shape = RoundedCornerShape(4.dp)
                        ) {
                            Text(
                                text = rec.priority,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                    }

                    Text(text = rec.recommendedAction, fontSize = 12.sp, color = Color(0xFFE2E8F0), fontWeight = FontWeight.Medium)
                    Text(text = "Impact: ${rec.expectedImpact}", fontSize = 11.sp, color = Color(0xFF34D399))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Est. Savings: ₹${rec.estimatedCostSavingsInr.toInt()}",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = GoldAccent
                        )

                        if (!rec.isApplied) {
                            Button(
                                onClick = { onApply(rec.recommendationId) },
                                colors = ButtonDefaults.buttonColors(containerColor = GoldAccent, contentColor = Color.Black),
                                shape = RoundedCornerShape(6.dp),
                                modifier = Modifier.height(32.dp).testTag("apply_rec_${rec.recommendationId}")
                            ) {
                                Text("APPLY TO ERP", fontSize = 10.sp, fontWeight = FontWeight.Bold)
                            }
                        } else {
                            Surface(color = Color(0xFF065F46), shape = RoundedCornerShape(4.dp)) {
                                Text(
                                    text = "APPLIED TO ERP",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF6EE7B7),
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
