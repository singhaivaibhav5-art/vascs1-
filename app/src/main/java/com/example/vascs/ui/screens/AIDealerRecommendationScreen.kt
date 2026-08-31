package com.example.vascs.ui.screens

import android.content.Intent
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
import com.example.vascs.data.model.AIDealerGrowthForecastEntity
import com.example.vascs.data.model.AIDealerRecommendationEntity
import com.example.vascs.data.model.AIDealerScoreEntity
import com.example.vascs.ui.theme.GoldAccent
import com.example.vascs.ui.theme.Maroon500
import com.example.vascs.ui.theme.Maroon700
import com.example.vascs.viewmodel.AIDealerRecommendationViewModel
import com.example.vascs.viewmodel.DealerPreset
import com.example.vascs.viewmodel.PRESET_DEALERS
import java.text.NumberFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AIDealerRecommendationScreen(
    viewModel: AIDealerRecommendationViewModel,
    onNavigateBack: () -> Unit
) {
    val context = LocalContext.current
    val inputState by viewModel.dealerInput.collectAsState()
    val loadingState by viewModel.loadingState.collectAsState()
    val errorState by viewModel.errorState.collectAsState()
    val activeRecommendation by viewModel.activeRecommendation.collectAsState()
    val recommendations by viewModel.dealerRecommendations.collectAsState()
    val dealerScores by viewModel.dealerScores.collectAsState()
    val dealerForecasts by viewModel.dealerForecasts.collectAsState()
    val selectedTab by viewModel.selectedTab.collectAsState()
    val classificationFilter by viewModel.classificationFilter.collectAsState()

    val currencyFormatter = remember { NumberFormat.getCurrencyInstance(Locale("en", "IN")) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "AI DEALER INTELLIGENCE",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = GoldAccent,
                                letterSpacing = 1.2.sp
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Surface(
                                color = Color(0xFF1E3A8A),
                                shape = RoundedCornerShape(4.dp)
                            ) {
                                Text(
                                    text = "U5 ENGINE",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF93C5FD),
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                        }
                        Text(
                            text = "Network Expansion, Dealer Rankings & Growth Forecaster",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color(0xFFD1D5DB)
                        )
                    }
                },
                navigationIcon = {
                    IconButton(
                        onClick = onNavigateBack,
                        modifier = Modifier.testTag("back_button")
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
                        onClick = {
                            val count = recommendations.size
                            Toast.makeText(context, "Exporting $count Dealer Dossiers to PDF...", Toast.LENGTH_SHORT).show()
                        },
                        modifier = Modifier.testTag("export_pdf_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.PictureAsPdf,
                            contentDescription = "Export PDF",
                            tint = Color.White
                        )
                    }
                    IconButton(
                        onClick = {
                            Toast.makeText(context, "Exporting Dealer Rankings to Excel .XLSX...", Toast.LENGTH_SHORT).show()
                        },
                        modifier = Modifier.testTag("export_excel_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.TableChart,
                            contentDescription = "Export Excel",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Maroon700
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
            // Tab Navigation
            ScrollableTabRow(
                selectedTabIndex = selectedTab,
                containerColor = Color(0xFF1E293B),
                contentColor = GoldAccent,
                edgePadding = 8.dp
            ) {
                val tabs = listOf(
                    Triple(0, "AI Advisor", Icons.Default.AutoAwesome),
                    Triple(1, "Rankings", Icons.Default.Leaderboard),
                    Triple(2, "Growth Forecast", Icons.AutoMirrored.Filled.TrendingUp),
                    Triple(3, "Risk Radar", Icons.Default.Shield),
                    Triple(4, "Audit Logs (${recommendations.size})", Icons.Default.History)
                )
                tabs.forEach { (index, title, icon) ->
                    Tab(
                        selected = selectedTab == index,
                        onClick = { viewModel.setSelectedTab(index) },
                        text = {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(icon, contentDescription = null, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(title, fontWeight = if (selectedTab == index) FontWeight.Bold else FontWeight.Normal)
                            }
                        },
                        selectedContentColor = GoldAccent,
                        unselectedContentColor = Color(0xFF94A3B8)
                    )
                }
            }

            // Error banner if any
            AnimatedVisibility(visible = errorState != null) {
                Surface(
                    color = Color(0xFF7F1D1D),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.ErrorOutline, contentDescription = null, tint = Color(0xFFFCA5A5))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = errorState ?: "",
                            color = Color(0xFFFEE2E2),
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }

            when (selectedTab) {
                0 -> DealerAdvisorTab(
                    viewModel = viewModel,
                    inputState = inputState,
                    activeRecommendation = activeRecommendation,
                    loadingState = loadingState,
                    currencyFormatter = currencyFormatter
                )
                1 -> DealerRankingsTab(
                    dealerScores = dealerScores,
                    onSelectDealer = { dealerName ->
                        val preset = PRESET_DEALERS.find { it.name.contains(dealerName, ignoreCase = true) }
                        if (preset != null) viewModel.loadDealerPreset(preset)
                        viewModel.setSelectedTab(0)
                    }
                )
                2 -> DealerGrowthForecastTab(
                    dealerForecasts = dealerForecasts,
                    currencyFormatter = currencyFormatter
                )
                3 -> DealerRiskRadarTab(
                    recommendations = recommendations,
                    dealerScores = dealerScores,
                    onSelectDealer = { rec ->
                        viewModel.selectRecommendation(rec)
                        viewModel.setSelectedTab(0)
                    }
                )
                4 -> DealerAuditLogsTab(
                    recommendations = recommendations,
                    filter = classificationFilter,
                    onFilterChange = { viewModel.setClassificationFilter(it) },
                    onSelect = { rec ->
                        viewModel.selectRecommendation(rec)
                        viewModel.setSelectedTab(0)
                    },
                    onToggleFavorite = { viewModel.toggleFavorite(it) },
                    onDelete = { viewModel.deleteRecommendation(it.recommendationId) },
                    onApply = { viewModel.applyRecommendation(it) },
                    currencyFormatter = currencyFormatter
                )
            }
        }
    }
}

@Composable
fun DealerAdvisorTab(
    viewModel: AIDealerRecommendationViewModel,
    inputState: com.example.vascs.viewmodel.DealerInputState,
    activeRecommendation: AIDealerRecommendationEntity?,
    loadingState: Boolean,
    currencyFormatter: NumberFormat
) {
    val context = LocalContext.current

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Preset selector
        item {
            Text(
                text = "STRATEGIC ARCHETYPE PRESETS",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = GoldAccent,
                letterSpacing = 1.sp
            )
            Spacer(modifier = Modifier.height(6.dp))
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(PRESET_DEALERS) { preset ->
                    AssistChip(
                        onClick = { viewModel.loadDealerPreset(preset) },
                        label = { Text(preset.name, fontSize = 12.sp) },
                        leadingIcon = {
                            Icon(
                                imageVector = when (preset.tag) {
                                    "Top Performer Star" -> Icons.Default.Stars
                                    "High Growth Velocity" -> Icons.AutoMirrored.Filled.TrendingUp
                                    "Expansion Target" -> Icons.Default.Explore
                                    "Recovery Opportunity" -> Icons.Default.Refresh
                                    else -> Icons.Default.WarningAmber
                                },
                                contentDescription = null,
                                modifier = Modifier.size(14.dp),
                                tint = when (preset.tag) {
                                    "Top Performer Star" -> GoldAccent
                                    "High Growth Velocity" -> Color(0xFF4ADE80)
                                    "Expansion Target" -> Color(0xFF60A5FA)
                                    "Recovery Opportunity" -> Color(0xFFFBBF24)
                                    else -> Color(0xFFF87171)
                                }
                            )
                        },
                        colors = AssistChipDefaults.assistChipColors(
                            containerColor = Color(0xFF1E293B),
                            labelColor = Color.White
                        ),
                        border = AssistChipDefaults.assistChipBorder(
                            enabled = true,
                            borderColor = Color(0xFF334155)
                        )
                    )
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
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Storefront, contentDescription = null, tint = GoldAccent)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Dealer Commercial Profile",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }

                    // Dealer Name & Category
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        OutlinedTextField(
                            value = inputState.dealerName,
                            onValueChange = { viewModel.updateDealerName(it) },
                            label = { Text("Dealer Name", color = Color(0xFF94A3B8)) },
                            modifier = Modifier.weight(1.2f).testTag("dealer_name_input"),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = Color.White,
                                unfocusedTextColor = Color.White,
                                focusedBorderColor = GoldAccent,
                                unfocusedBorderColor = Color(0xFF475569)
                            )
                        )
                        OutlinedTextField(
                            value = inputState.dealerCategory,
                            onValueChange = { viewModel.updateDealerCategory(it) },
                            label = { Text("Category", color = Color(0xFF94A3B8)) },
                            modifier = Modifier.weight(1f).testTag("dealer_category_input"),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = Color.White,
                                unfocusedTextColor = Color.White,
                                focusedBorderColor = GoldAccent,
                                unfocusedBorderColor = Color(0xFF475569)
                            )
                        )
                    }

                    // Location & Product Preferences
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        OutlinedTextField(
                            value = inputState.location,
                            onValueChange = { viewModel.updateLocation(it) },
                            label = { Text("Location / Region", color = Color(0xFF94A3B8)) },
                            modifier = Modifier.weight(1f).testTag("dealer_location_input"),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = Color.White,
                                unfocusedTextColor = Color.White,
                                focusedBorderColor = GoldAccent,
                                unfocusedBorderColor = Color(0xFF475569)
                            )
                        )
                        OutlinedTextField(
                            value = inputState.productPreferences,
                            onValueChange = { viewModel.updateProductPreferences(it) },
                            label = { Text("Product Preferences", color = Color(0xFF94A3B8)) },
                            modifier = Modifier.weight(1f).testTag("dealer_preferences_input"),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = Color.White,
                                unfocusedTextColor = Color.White,
                                focusedBorderColor = GoldAccent,
                                unfocusedBorderColor = Color(0xFF475569)
                            )
                        )
                    }

                    // Financial metrics
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        OutlinedTextField(
                            value = inputState.salesHistoryAnnual.toInt().toString(),
                            onValueChange = { it.toDoubleOrNull()?.let { v -> viewModel.updateSalesAnnual(v) } },
                            label = { Text("Annual Sales (₹)", color = Color(0xFF94A3B8)) },
                            modifier = Modifier.weight(1f).testTag("annual_sales_input"),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = Color.White,
                                unfocusedTextColor = Color.White,
                                focusedBorderColor = GoldAccent,
                                unfocusedBorderColor = Color(0xFF475569)
                            )
                        )
                        OutlinedTextField(
                            value = inputState.salesHistoryQuarterly.toInt().toString(),
                            onValueChange = { it.toDoubleOrNull()?.let { v -> viewModel.updateSalesQuarterly(v) } },
                            label = { Text("Last Qtr Sales (₹)", color = Color(0xFF94A3B8)) },
                            modifier = Modifier.weight(1f).testTag("quarterly_sales_input"),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = Color.White,
                                unfocusedTextColor = Color.White,
                                focusedBorderColor = GoldAccent,
                                unfocusedBorderColor = Color(0xFF475569)
                            )
                        )
                    }

                    // Order frequency & Growth %
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        OutlinedTextField(
                            value = inputState.orderFrequencyPerMonth.toString(),
                            onValueChange = { it.toDoubleOrNull()?.let { v -> viewModel.updateOrderFrequency(v) } },
                            label = { Text("Orders / Mo", color = Color(0xFF94A3B8)) },
                            modifier = Modifier.weight(1f).testTag("order_frequency_input"),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = Color.White,
                                unfocusedTextColor = Color.White,
                                focusedBorderColor = GoldAccent,
                                unfocusedBorderColor = Color(0xFF475569)
                            )
                        )
                        OutlinedTextField(
                            value = inputState.growthTrendPercent.toString(),
                            onValueChange = { it.toDoubleOrNull()?.let { v -> viewModel.updateGrowthTrend(v) } },
                            label = { Text("YoY Growth %", color = Color(0xFF94A3B8)) },
                            modifier = Modifier.weight(1f).testTag("growth_trend_input"),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = Color.White,
                                unfocusedTextColor = Color.White,
                                focusedBorderColor = GoldAccent,
                                unfocusedBorderColor = Color(0xFF475569)
                            )
                        )
                        OutlinedTextField(
                            value = inputState.dealerRating.toString(),
                            onValueChange = { it.toDoubleOrNull()?.let { v -> viewModel.updateDealerRating(v) } },
                            label = { Text("Rating (1-5)", color = Color(0xFF94A3B8)) },
                            modifier = Modifier.weight(1f).testTag("dealer_rating_input"),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = Color.White,
                                unfocusedTextColor = Color.White,
                                focusedBorderColor = GoldAccent,
                                unfocusedBorderColor = Color(0xFF475569)
                            )
                        )
                    }

                    // Payment velocity & Customer reach
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        OutlinedTextField(
                            value = inputState.paymentPerformance,
                            onValueChange = { viewModel.updatePaymentPerformance(it) },
                            label = { Text("Payment Terms", color = Color(0xFF94A3B8)) },
                            modifier = Modifier.weight(1.2f).testTag("payment_terms_input"),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = Color.White,
                                unfocusedTextColor = Color.White,
                                focusedBorderColor = GoldAccent,
                                unfocusedBorderColor = Color(0xFF475569)
                            )
                        )
                        OutlinedTextField(
                            value = inputState.customerReachCount.toString(),
                            onValueChange = { it.toIntOrNull()?.let { v -> viewModel.updateCustomerReach(v) } },
                            label = { Text("Reach (B2C)", color = Color(0xFF94A3B8)) },
                            modifier = Modifier.weight(0.8f).testTag("customer_reach_input"),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = Color.White,
                                unfocusedTextColor = Color.White,
                                focusedBorderColor = GoldAccent,
                                unfocusedBorderColor = Color(0xFF475569)
                            )
                        )
                    }

                    // Credit limits
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        OutlinedTextField(
                            value = inputState.creditLimit.toInt().toString(),
                            onValueChange = { it.toDoubleOrNull()?.let { v -> viewModel.updateCreditLimit(v) } },
                            label = { Text("Credit Cap (₹)", color = Color(0xFF94A3B8)) },
                            modifier = Modifier.weight(1f).testTag("credit_limit_input"),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = Color.White,
                                unfocusedTextColor = Color.White,
                                focusedBorderColor = GoldAccent,
                                unfocusedBorderColor = Color(0xFF475569)
                            )
                        )
                        OutlinedTextField(
                            value = inputState.creditUsed.toInt().toString(),
                            onValueChange = { it.toDoubleOrNull()?.let { v -> viewModel.updateCreditUsed(v) } },
                            label = { Text("Credit Used (₹)", color = Color(0xFF94A3B8)) },
                            modifier = Modifier.weight(1f).testTag("credit_used_input"),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = Color.White,
                                unfocusedTextColor = Color.White,
                                focusedBorderColor = GoldAccent,
                                unfocusedBorderColor = Color(0xFF475569)
                            )
                        )
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    // Generation Action Button
                    Button(
                        onClick = { viewModel.generateRecommendations() },
                        enabled = !loadingState,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .testTag("generate_dealer_recommendation_button"),
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Maroon500
                        )
                    ) {
                        if (loadingState) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(22.dp),
                                color = GoldAccent,
                                strokeWidth = 2.dp
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Text("GEMINI IS ANALYZING DEALER NETWORK...", fontWeight = FontWeight.Bold)
                        } else {
                            Icon(Icons.Default.Psychology, contentDescription = null, tint = GoldAccent)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                "GENERATE AI DEALER RECOMMENDATIONS",
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }
                    }
                }
            }
        }

        // Active Recommendation Result
        if (activeRecommendation != null) {
            item {
                ActiveDealerRecommendationCard(
                    recommendation = activeRecommendation,
                    currencyFormatter = currencyFormatter,
                    onApply = {
                        viewModel.applyRecommendation(activeRecommendation)
                        Toast.makeText(context, "Dealer Strategy Authorized & Dispatched!", Toast.LENGTH_SHORT).show()
                    },
                    onToggleFavorite = { viewModel.toggleFavorite(activeRecommendation) },
                    onShare = {
                        val shareIntent = Intent().apply {
                            action = Intent.ACTION_SEND
                            type = "text/plain"
                            putExtra(
                                Intent.EXTRA_TEXT,
                                """
                                VASCS AI DEALER STRATEGY REPORT
                                Dealer: ${activeRecommendation.dealerName} (${activeRecommendation.location})
                                Classification: ${activeRecommendation.classification}
                                Potential Score: ${activeRecommendation.dealerPotentialScore}/100
                                Loyalty Score: ${activeRecommendation.dealerLoyaltyScore}/100
                                Growth Forecast: +${activeRecommendation.futureGrowthForecastPercent}%
                                Strategic Actions: ${activeRecommendation.recommendedActions}
                                Credit Terms: ${activeRecommendation.creditRecommendation}
                                Catalog Allocations: ${activeRecommendation.exclusiveCatalogAccess}
                                """.trimIndent()
                            )
                        }
                        context.startActivity(Intent.createChooser(shareIntent, "Share Dealer Strategy"))
                    }
                )
            }
        }
    }
}

@Composable
fun ActiveDealerRecommendationCard(
    recommendation: AIDealerRecommendationEntity,
    currencyFormatter: NumberFormat,
    onApply: () -> Unit,
    onToggleFavorite: () -> Unit,
    onShare: () -> Unit
) {
    val classColor = when (recommendation.classification) {
        "TOP_PERFORMER" -> GoldAccent
        "HIGH_GROWTH" -> Color(0xFF4ADE80)
        "EXPANSION" -> Color(0xFF60A5FA)
        "RECOVERY" -> Color(0xFFFBBF24)
        else -> Color(0xFFF87171)
    }

    Card(
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1E293B)),
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.5.dp, classColor)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = recommendation.dealerName,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        text = "${recommendation.dealerCategory} • ${recommendation.location}",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0xFF94A3B8)
                    )
                }

                Surface(
                    color = classColor.copy(alpha = 0.2f),
                    shape = RoundedCornerShape(8.dp),
                    border = BorderStroke(1.dp, classColor)
                ) {
                    Text(
                        text = recommendation.classification.replace("_", " "),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = classColor,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }

            HorizontalDivider(color = Color(0xFF334155))

            // Score Metrics 4-Grid
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                ScoreMetricBox(
                    title = "Potential",
                    score = recommendation.dealerPotentialScore,
                    color = Color(0xFF60A5FA),
                    modifier = Modifier.weight(1f)
                )
                ScoreMetricBox(
                    title = "Loyalty",
                    score = recommendation.dealerLoyaltyScore,
                    color = Color(0xFF4ADE80),
                    modifier = Modifier.weight(1f)
                )
                ScoreMetricBox(
                    title = "Rev Contrib",
                    score = recommendation.revenueContributionScore,
                    color = GoldAccent,
                    modifier = Modifier.weight(1f)
                )
                ScoreMetricBox(
                    title = "Risk Index",
                    score = recommendation.riskScore,
                    color = if (recommendation.riskScore > 50) Color(0xFFF87171) else Color(0xFF34D399),
                    modifier = Modifier.weight(1f)
                )
            }

            // Projected 1Y Growth Badge
            Surface(
                color = Color(0xFF0F172A),
                shape = RoundedCornerShape(8.dp),
                border = BorderStroke(1.dp, Color(0xFF334155)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.AutoMirrored.Filled.TrendingUp, contentDescription = null, tint = Color(0xFF4ADE80))
                        Spacer(modifier = Modifier.width(8.dp))
                        Column {
                            Text("Projected 1-Year Growth", fontSize = 11.sp, color = Color(0xFF94A3B8))
                            Text(
                                "+${recommendation.futureGrowthForecastPercent}% Annual Surge",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF4ADE80)
                            )
                        }
                    }
                    if (recommendation.isApplied) {
                        Surface(
                            color = Color(0xFF065F46),
                            shape = RoundedCornerShape(6.dp)
                        ) {
                            Text(
                                "STRATEGY ACTIVE",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF6EE7B7),
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
                            )
                        }
                    }
                }
            }

            // Recommended Action Box
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF0F172A), RoundedCornerShape(8.dp))
                    .padding(12.dp)
            ) {
                Text(
                    "STRATEGIC DIRECTIVES",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    color = GoldAccent,
                    letterSpacing = 0.8.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    recommendation.recommendedActions,
                    fontSize = 13.sp,
                    color = Color.White,
                    lineHeight = 18.sp
                )
            }

            // Credit & Catalog Recommendations
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .background(Color(0xFF0F172A), RoundedCornerShape(8.dp))
                        .padding(10.dp)
                ) {
                    Text("CREDIT MANDATE", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color(0xFF93C5FD))
                    Spacer(modifier = Modifier.height(3.dp))
                    Text(recommendation.creditRecommendation, fontSize = 12.sp, color = Color(0xFFE2E8F0))
                }
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .background(Color(0xFF0F172A), RoundedCornerShape(8.dp))
                        .padding(10.dp)
                ) {
                    Text("EXCLUSIVE ACCESS", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color(0xFFFDE68A))
                    Spacer(modifier = Modifier.height(3.dp))
                    Text(recommendation.exclusiveCatalogAccess, fontSize = 12.sp, color = Color(0xFFE2E8F0))
                }
            }

            // Rationale
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF0F172A), RoundedCornerShape(8.dp))
                    .padding(10.dp)
            ) {
                Text("AI RATIONALE & ANALYTICS", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color(0xFF94A3B8))
                Spacer(modifier = Modifier.height(3.dp))
                Text(recommendation.rationale, fontSize = 12.sp, color = Color(0xFFCBD5E1))
            }

            // Action Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedButton(
                    onClick = onToggleFavorite,
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White),
                    border = BorderStroke(1.dp, Color(0xFF475569))
                ) {
                    Icon(
                        imageVector = if (recommendation.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = null,
                        tint = if (recommendation.isFavorite) Color(0xFFF87171) else Color.White,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(if (recommendation.isFavorite) "Saved" else "Bookmark", fontSize = 12.sp)
                }

                OutlinedButton(
                    onClick = onShare,
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White),
                    border = BorderStroke(1.dp, Color(0xFF475569))
                ) {
                    Icon(Icons.Default.Share, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Share", fontSize = 12.sp)
                }

                Button(
                    onClick = onApply,
                    modifier = Modifier.weight(1.3f),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF059669))
                ) {
                    Icon(Icons.Default.CheckCircle, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Authorize Plan", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
fun ScoreMetricBox(
    title: String,
    score: Int,
    color: Color,
    modifier: Modifier = Modifier
) {
    Surface(
        color = Color(0xFF0F172A),
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(1.dp, Color(0xFF334155)),
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(title, fontSize = 10.sp, color = Color(0xFF94A3B8))
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = "$score",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = color
            )
            Text("/100", fontSize = 9.sp, color = Color(0xFF64748B))
        }
    }
}

@Composable
fun DealerRankingsTab(
    dealerScores: List<AIDealerScoreEntity>,
    onSelectDealer: (String) -> Unit
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
                border = BorderStroke(1.dp, Color(0xFF334155))
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.EmojiEvents, contentDescription = null, tint = GoldAccent)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Dealer Performance Leaderboard",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                    Text(
                        text = "Composite ranking index based on sales volume, payment velocity, loyalty, and customer reach.",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0xFF94A3B8)
                    )
                }
            }
        }

        items(dealerScores.sortedByDescending { it.overallScore }) { scoreItem ->
            val rankIndex = dealerScores.indexOf(scoreItem) + 1
            val medalColor = when (rankIndex) {
                1 -> GoldAccent
                2 -> Color(0xFFE2E8F0)
                3 -> Color(0xFFCD7F32)
                else -> Color(0xFF64748B)
            }

            Card(
                colors = CardDefaults.cardColors(containerColor = Color(0xFF1E293B)),
                shape = RoundedCornerShape(10.dp),
                border = BorderStroke(1.dp, if (rankIndex == 1) GoldAccent else Color(0xFF334155)),
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onSelectDealer(scoreItem.dealerName) }
            ) {
                Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Surface(
                                shape = CircleShape,
                                color = medalColor.copy(alpha = 0.2f),
                                border = BorderStroke(1.dp, medalColor),
                                modifier = Modifier.size(32.dp)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Text(
                                        text = "#$rankIndex",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 12.sp,
                                        color = medalColor
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text(
                                    text = scoreItem.dealerName,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp,
                                    color = Color.White
                                )
                                Text(
                                    text = "${scoreItem.dealerCategory} • ${scoreItem.location}",
                                    fontSize = 11.sp,
                                    color = Color(0xFF94A3B8)
                                )
                            }
                        }

                        Surface(
                            color = Color(0xFF0F172A),
                            shape = RoundedCornerShape(6.dp),
                            border = BorderStroke(1.dp, Color(0xFF334155))
                        ) {
                            Text(
                                text = "${scoreItem.overallScore} Index",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = GoldAccent,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                    }

                    // Progress Bar comparison
                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Sales: ${scoreItem.salesScore.toInt()}%", fontSize = 10.sp, color = Color(0xFF94A3B8))
                            Text("Growth: ${scoreItem.growthScore.toInt()}%", fontSize = 10.sp, color = Color(0xFF94A3B8))
                            Text("Payment: ${scoreItem.paymentScore.toInt()}%", fontSize = 10.sp, color = Color(0xFF94A3B8))
                            Text("Loyalty: ${scoreItem.loyaltyScore.toInt()}%", fontSize = 10.sp, color = Color(0xFF94A3B8))
                        }
                        LinearProgressIndicator(
                            progress = { (scoreItem.overallScore / 100.0).toFloat().coerceIn(0f, 1f) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(6.dp)
                                .clip(RoundedCornerShape(3.dp)),
                            color = when {
                                scoreItem.overallScore >= 85 -> GoldAccent
                                scoreItem.overallScore >= 70 -> Color(0xFF4ADE80)
                                else -> Color(0xFF60A5FA)
                            },
                            trackColor = Color(0xFF334155)
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            color = Color(0xFF0F172A),
                            shape = RoundedCornerShape(4.dp)
                        ) {
                            Text(
                                text = scoreItem.tierBadge,
                                fontSize = 10.sp,
                                color = Color(0xFFCBD5E1),
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                        Text(
                            text = "Tap to load advisor →",
                            fontSize = 11.sp,
                            color = GoldAccent
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun DealerGrowthForecastTab(
    dealerForecasts: List<AIDealerGrowthForecastEntity>,
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
                border = BorderStroke(1.dp, Color(0xFF334155))
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.AutoMirrored.Filled.TrendingUp, contentDescription = null, tint = Color(0xFF4ADE80))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Quarterly Revenue Projections",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                    Text(
                        text = "1-Year revenue trajectories based on product allocations and seasonal expansion elasticity.",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0xFF94A3B8)
                    )
                }
            }
        }

        if (dealerForecasts.isEmpty()) {
            item {
                Surface(
                    color = Color(0xFF1E293B),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(Icons.Default.Analytics, contentDescription = null, tint = Color(0xFF64748B), modifier = Modifier.size(36.dp))
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("No growth forecasts generated yet.", color = Color(0xFF94A3B8), fontSize = 13.sp)
                        Text("Run an AI Advisor analysis to generate forecasts.", color = Color(0xFF64748B), fontSize = 11.sp)
                    }
                }
            }
        } else {
            items(dealerForecasts) { forecast ->
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF1E293B)),
                    shape = RoundedCornerShape(10.dp),
                    border = BorderStroke(1.dp, Color(0xFF334155))
                ) {
                    Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = forecast.dealerName,
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                                color = Color.White
                            )
                            Surface(
                                color = Color(0xFF065F46),
                                shape = RoundedCornerShape(6.dp)
                            ) {
                                Text(
                                    text = "Annual: ${currencyFormatter.format(forecast.annualProjectedRevenue)}",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF6EE7B7),
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
                                )
                            }
                        }

                        // 4-Quarter Visual Bars
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            QuarterBar(title = "Q1", revenue = forecast.projectedQ1Revenue, formatter = currencyFormatter, modifier = Modifier.weight(1f))
                            QuarterBar(title = "Q2", revenue = forecast.projectedQ2Revenue, formatter = currencyFormatter, modifier = Modifier.weight(1f))
                            QuarterBar(title = "Q3", revenue = forecast.projectedQ3Revenue, formatter = currencyFormatter, modifier = Modifier.weight(1f))
                            QuarterBar(title = "Q4 (Festive)", revenue = forecast.projectedQ4Revenue, formatter = currencyFormatter, modifier = Modifier.weight(1.2f))
                        }

                        // Product Mix allocation
                        Surface(
                            color = Color(0xFF0F172A),
                            shape = RoundedCornerShape(6.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier.padding(10.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Column {
                                    Text("Recommended Catalog Mix", fontSize = 10.sp, color = Color(0xFF94A3B8))
                                    Text(forecast.recommendedProductMix, fontSize = 12.sp, color = Color.White)
                                }
                                Column(horizontalAlignment = Alignment.End) {
                                    Text("Co-op Incentive Pool", fontSize = 10.sp, color = Color(0xFF94A3B8))
                                    Text(currencyFormatter.format(forecast.targetIncentiveBudget), fontSize = 12.sp, fontWeight = FontWeight.Bold, color = GoldAccent)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun QuarterBar(
    title: String,
    revenue: Double,
    formatter: NumberFormat,
    modifier: Modifier = Modifier
) {
    Surface(
        color = Color(0xFF0F172A),
        shape = RoundedCornerShape(6.dp),
        border = BorderStroke(1.dp, Color(0xFF334155)),
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(title, fontSize = 10.sp, color = Color(0xFF94A3B8))
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = "₹${(revenue / 100000.0).toInt()}L",
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF60A5FA)
            )
        }
    }
}

@Composable
fun DealerRiskRadarTab(
    recommendations: List<AIDealerRecommendationEntity>,
    dealerScores: List<AIDealerScoreEntity>,
    onSelectDealer: (AIDealerRecommendationEntity) -> Unit
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
                border = BorderStroke(1.dp, Color(0xFF334155))
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Shield, contentDescription = null, tint = Color(0xFFF87171))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Dealer Risk & Delinquency Radar",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                    Text(
                        text = "Real-time monitoring of high credit exposure, delayed payment settlement cycles, and negative growth trajectories.",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0xFF94A3B8)
                    )
                }
            }
        }

        val riskRecs = recommendations.filter { it.isRiskAlert || it.isRecoveryTarget || it.riskScore > 40 }
        if (riskRecs.isEmpty()) {
            item {
                Surface(
                    color = Color(0xFF065F46).copy(alpha = 0.3f),
                    shape = RoundedCornerShape(8.dp),
                    border = BorderStroke(1.dp, Color(0xFF059669)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(Icons.Default.CheckCircleOutline, contentDescription = null, tint = Color(0xFF6EE7B7), modifier = Modifier.size(32.dp))
                        Spacer(modifier = Modifier.height(6.dp))
                        Text("No High-Risk Dealers Detected", fontWeight = FontWeight.Bold, color = Color(0xFF6EE7B7), fontSize = 14.sp)
                        Text("All dealer accounts have healthy credit settlement velocity.", color = Color(0xFFA7F3D0), fontSize = 11.sp)
                    }
                }
            }
        } else {
            items(riskRecs) { rec ->
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF1E293B)),
                    shape = RoundedCornerShape(10.dp),
                    border = BorderStroke(1.dp, Color(0xFFF87171)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onSelectDealer(rec) }
                ) {
                    Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = rec.dealerName,
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                                color = Color.White
                            )
                            Surface(
                                color = Color(0xFF7F1D1D),
                                shape = RoundedCornerShape(6.dp)
                            ) {
                                Text(
                                    text = "Risk Score: ${rec.riskScore}/100",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFFFCA5A5),
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
                                )
                            }
                        }

                        Text(
                            text = rec.recommendedActions,
                            fontSize = 12.sp,
                            color = Color(0xFFCBD5E1)
                        )

                        Surface(
                            color = Color(0xFF0F172A),
                            shape = RoundedCornerShape(6.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "Credit Directive: ${rec.creditRecommendation}",
                                fontSize = 11.sp,
                                color = Color(0xFFF87171),
                                modifier = Modifier.padding(8.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun DealerAuditLogsTab(
    recommendations: List<AIDealerRecommendationEntity>,
    filter: String,
    onFilterChange: (String) -> Unit,
    onSelect: (AIDealerRecommendationEntity) -> Unit,
    onToggleFavorite: (AIDealerRecommendationEntity) -> Unit,
    onDelete: (AIDealerRecommendationEntity) -> Unit,
    onApply: (AIDealerRecommendationEntity) -> Unit,
    currencyFormatter: NumberFormat
) {
    val filterOptions = listOf("ALL", "TOP_PERFORMER", "HIGH_GROWTH", "EXPANSION", "RECOVERY", "RISK_WATCH")
    val filteredList = if (filter == "ALL") recommendations else recommendations.filter { it.classification == filter }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(filterOptions) { opt ->
                    FilterChip(
                        selected = filter == opt,
                        onClick = { onFilterChange(opt) },
                        label = { Text(opt.replace("_", " "), fontSize = 11.sp) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = GoldAccent,
                            selectedLabelColor = Color.Black,
                            containerColor = Color(0xFF1E293B),
                            labelColor = Color.White
                        )
                    )
                }
            }
        }

        if (filteredList.isEmpty()) {
            item {
                Surface(
                    color = Color(0xFF1E293B),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(Icons.Default.FolderOpen, contentDescription = null, tint = Color(0xFF64748B), modifier = Modifier.size(36.dp))
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("No audit log entries for filter '$filter'.", color = Color(0xFF94A3B8), fontSize = 13.sp)
                    }
                }
            }
        } else {
            items(filteredList) { rec ->
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF1E293B)),
                    shape = RoundedCornerShape(10.dp),
                    border = BorderStroke(1.dp, Color(0xFF334155)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = rec.dealerName,
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp,
                                color = Color.White
                            )
                            Surface(
                                color = Color(0xFF0F172A),
                                shape = RoundedCornerShape(4.dp)
                            ) {
                                Text(
                                    text = rec.classification.replace("_", " "),
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = GoldAccent,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                        }

                        Text(
                            text = rec.recommendedActions,
                            fontSize = 11.sp,
                            color = Color(0xFFCBD5E1),
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Growth: +${rec.futureGrowthForecastPercent}% | Potential: ${rec.dealerPotentialScore}/100",
                                fontSize = 10.sp,
                                color = Color(0xFF94A3B8)
                            )

                            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                                IconButton(
                                    onClick = { onToggleFavorite(rec) },
                                    modifier = Modifier.size(28.dp)
                                ) {
                                    Icon(
                                        imageVector = if (rec.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                                        contentDescription = "Bookmark",
                                        tint = if (rec.isFavorite) Color(0xFFF87171) else Color(0xFF94A3B8),
                                        modifier = Modifier.size(16.dp)
                                    )
                                }
                                IconButton(
                                    onClick = { onDelete(rec) },
                                    modifier = Modifier.size(28.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Delete,
                                        contentDescription = "Delete",
                                        tint = Color(0xFF94A3B8),
                                        modifier = Modifier.size(16.dp)
                                    )
                                }
                                TextButton(
                                    onClick = { onSelect(rec) },
                                    contentPadding = PaddingValues(horizontal = 8.dp, vertical = 2.dp)
                                ) {
                                    Text("View →", fontSize = 11.sp, color = GoldAccent)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
