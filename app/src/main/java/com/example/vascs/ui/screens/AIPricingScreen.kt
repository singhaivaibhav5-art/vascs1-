package com.example.vascs.ui.screens

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.vascs.data.model.AIPricingHistoryEntity
import com.example.vascs.data.model.AIPricingResultEntity
import com.example.vascs.data.model.AIPricingRuleEntity
import com.example.vascs.viewmodel.AIPricingExecutionState
import com.example.vascs.viewmodel.AIPricingViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AIPricingScreen(
    viewModel: AIPricingViewModel,
    onNavigateBack: () -> Unit = {}
) {
    val inputState by viewModel.pricingInput.collectAsStateWithLifecycle()
    val resultState by viewModel.pricingResult.collectAsStateWithLifecycle()
    val historyList by viewModel.pricingHistory.collectAsStateWithLifecycle()
    val rulesList by viewModel.pricingRules.collectAsStateWithLifecycle()
    val loadingState by viewModel.loadingState.collectAsStateWithLifecycle()
    val selectedTab by viewModel.selectedTab.collectAsStateWithLifecycle()
    val errorState by viewModel.errorState.collectAsStateWithLifecycle()
    val calculatorState by viewModel.calculatorState.collectAsStateWithLifecycle()

    val context = LocalContext.current
    val clipboardManager = LocalClipboardManager.current

    var showExportDialog by remember { mutableStateOf(false) }

    fun copyToClipboard(text: String, label: String = "Price Quote") {
        clipboardManager.setText(AnnotatedString(text))
        Toast.makeText(context, "$label copied to clipboard!", Toast.LENGTH_SHORT).show()
    }

    fun shareContent(text: String, title: String = "Share Pricing Intelligence") {
        val sendIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, text)
            type = "text/plain"
        }
        val shareIntent = Intent.createChooser(sendIntent, title)
        context.startActivity(shareIntent)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "AI Pricing Engine",
                                fontWeight = FontWeight.Bold,
                                style = MaterialTheme.typography.titleLarge
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Surface(
                                shape = RoundedCornerShape(12.dp),
                                color = MaterialTheme.colorScheme.primaryContainer,
                                modifier = Modifier.padding(2.dp)
                            ) {
                                Text(
                                    text = "U3 PRO",
                                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                        }
                        Text(
                            text = "Multi-Channel Wholesale & Margin Optimization",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                },
                navigationIcon = {
                    IconButton(
                        onClick = onNavigateBack,
                        modifier = Modifier.testTag("pricing_nav_back_button")
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = { showExportDialog = true },
                        modifier = Modifier.testTag("pricing_export_button")
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.PictureAsPdf,
                            contentDescription = "Export Dossier",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                    IconButton(
                        onClick = {
                            val result = resultState
                            if (result != null) {
                                val quoteText = buildString {
                                    appendLine("⚜️ *VASCS MULTI-TIER PRICING INTELLIGENCE*")
                                    appendLine("Product: ${result.productName} (${result.fabricType})")
                                    appendLine("Cost Base: ₹${result.costPrice}")
                                    appendLine("--------------------------------")
                                    appendLine("🏷️ Retail Price: ₹${result.retailPrice}")
                                    appendLine("🏢 Wholesale (B2B): ₹${result.wholesalePrice}")
                                    appendLine("🚚 Regional Distributor: ₹${result.distributorPrice}")
                                    appendLine("🏬 Authorized Dealer: ₹${result.dealerPrice}")
                                    appendLine("👑 Haute Couture Premium: ₹${result.premiumPrice}")
                                    appendLine("--------------------------------")
                                    appendLine("📈 Recommended Margin: ${result.recommendedMargin}%")
                                    appendLine("💰 Profit Mark-up: ${result.profitPercentage}%")
                                    appendLine("🛡️ Max Discount Limit: ${result.discountLimit}%")
                                    appendLine("🎯 Competitiveness Score: ${result.marketCompetitivenessScore}/100")
                                    appendLine("💡 Strategy: ${result.aiRationale}")
                                }
                                shareContent(quoteText, "Share Pricing Quotation")
                            } else {
                                Toast.makeText(context, "Generate a pricing recommendation first.", Toast.LENGTH_SHORT).show()
                            }
                        },
                        modifier = Modifier.testTag("pricing_share_button")
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Share,
                            contentDescription = "Share",
                            tint = MaterialTheme.colorScheme.primary
                        )
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
            // Tab Navigation
            ScrollableTabRow(
                selectedTabIndex = selectedTab,
                edgePadding = 16.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("pricing_tab_row"),
                containerColor = MaterialTheme.colorScheme.surface
            ) {
                Tab(
                    selected = selectedTab == 0,
                    onClick = { viewModel.setTab(0) },
                    text = { Text("Price Generator") },
                    icon = { Icon(Icons.Outlined.AutoAwesome, contentDescription = null, modifier = Modifier.size(18.dp)) },
                    modifier = Modifier.testTag("pricing_tab_generator")
                )
                Tab(
                    selected = selectedTab == 1,
                    onClick = { viewModel.setTab(1) },
                    text = { Text("Channel Matrix & Sim") },
                    icon = { Icon(Icons.Outlined.Calculate, contentDescription = null, modifier = Modifier.size(18.dp)) },
                    modifier = Modifier.testTag("pricing_tab_matrix")
                )
                Tab(
                    selected = selectedTab == 2,
                    onClick = { viewModel.setTab(2) },
                    text = { Text("Analytics & Benchmarks") },
                    icon = { Icon(Icons.Outlined.QueryStats, contentDescription = null, modifier = Modifier.size(18.dp)) },
                    modifier = Modifier.testTag("pricing_tab_analytics")
                )
                Tab(
                    selected = selectedTab == 3,
                    onClick = { viewModel.setTab(3) },
                    text = { Text("Audit History (${historyList.size})") },
                    icon = { Icon(Icons.Outlined.History, contentDescription = null, modifier = Modifier.size(18.dp)) },
                    modifier = Modifier.testTag("pricing_tab_history")
                )
            }

            // Error Banner
            if (errorState != null) {
                Surface(
                    color = MaterialTheme.colorScheme.errorContainer,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Warning,
                            contentDescription = "Error",
                            tint = MaterialTheme.colorScheme.error
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = errorState ?: "",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onErrorContainer,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }

            // Tab Content
            when (selectedTab) {
                0 -> PricingGeneratorTab(
                    inputState = inputState,
                    resultState = resultState,
                    loadingState = loadingState,
                    rulesList = rulesList,
                    onUpdateName = { viewModel.updateProductName(it) },
                    onUpdateCost = { viewModel.updateCostPrice(it) },
                    onUpdateCategory = { viewModel.updateCategory(it) },
                    onUpdateBrand = { viewModel.updateBrand(it) },
                    onUpdateFabric = { viewModel.updateFabricType(it) },
                    onUpdateDealerCat = { viewModel.updateDealerCategory(it) },
                    onUpdateExistingPrice = { viewModel.updateExistingSellingPrice(it) },
                    onUpdateCompetitorPrice = { viewModel.updateCompetitorPrice(it) },
                    onUpdateTargetMargin = { viewModel.updateTargetMargin(it) },
                    onUpdateRegion = { viewModel.updateRegion(it) },
                    onUpdateMarketType = { viewModel.updateMarketType(it) },
                    onApplyRule = { viewModel.applyRule(it) },
                    onGenerate = { viewModel.generatePricingRecommendation() },
                    onCopy = { text, label -> copyToClipboard(text, label) },
                    onToggleFavorite = { viewModel.toggleFavorite(it) }
                )
                1 -> MultiChannelMatrixTab(
                    resultState = resultState,
                    calculatorState = calculatorState,
                    onUpdateCalcCost = { viewModel.updateCalculatorCost(it) },
                    onUpdateCalcSelling = { viewModel.updateCalculatorSelling(it) },
                    onUpdateCalcDiscount = { viewModel.updateCalculatorDiscount(it) },
                    onCopy = { text, label -> copyToClipboard(text, label) }
                )
                2 -> AnalyticsAndBenchmarksTab(
                    resultState = resultState,
                    onCopy = { text, label -> copyToClipboard(text, label) }
                )
                3 -> AuditHistoryTab(
                    historyList = historyList,
                    rulesList = rulesList,
                    onSelectHistory = { viewModel.selectHistoryItem(it) },
                    onDeleteHistory = { viewModel.deleteHistoryItem(it) },
                    onClearHistory = { viewModel.clearHistory() },
                    onApplyRule = {
                        viewModel.applyRule(it)
                        viewModel.setTab(0)
                    }
                )
            }
        }
    }

    if (showExportDialog) {
        PricingExportDialog(
            result = resultState,
            onDismiss = { showExportDialog = false },
            onCopy = { text, label -> copyToClipboard(text, label) }
        )
    }
}

@Composable
fun PricingGeneratorTab(
    inputState: com.example.vascs.viewmodel.AIPricingInputState,
    resultState: AIPricingResultEntity?,
    loadingState: Boolean,
    rulesList: List<AIPricingRuleEntity>,
    onUpdateName: (String) -> Unit,
    onUpdateCost: (String) -> Unit,
    onUpdateCategory: (String) -> Unit,
    onUpdateBrand: (String) -> Unit,
    onUpdateFabric: (String) -> Unit,
    onUpdateDealerCat: (String) -> Unit,
    onUpdateExistingPrice: (String) -> Unit,
    onUpdateCompetitorPrice: (String) -> Unit,
    onUpdateTargetMargin: (String) -> Unit,
    onUpdateRegion: (String) -> Unit,
    onUpdateMarketType: (String) -> Unit,
    onApplyRule: (AIPricingRuleEntity) -> Unit,
    onGenerate: () -> Unit,
    onCopy: (String, String) -> Unit,
    onToggleFavorite: (AIPricingResultEntity) -> Unit
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Quick Category Multiplier Presets
        if (rulesList.isNotEmpty()) {
            Column {
                Text(
                    text = "Quick Multiplier Rules",
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(8.dp))
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(rulesList) { rule ->
                        SuggestionChip(
                            onClick = { onApplyRule(rule) },
                            label = { Text(rule.ruleName) },
                            icon = { Icon(Icons.Outlined.Layers, contentDescription = null, modifier = Modifier.size(16.dp)) },
                            colors = SuggestionChipDefaults.suggestionChipColors(
                                containerColor = MaterialTheme.colorScheme.surfaceVariant
                            )
                        )
                    }
                }
            }
        }

        // Input Card
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "Product & Financial Parameters",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )

                // 1. Product Name
                OutlinedTextField(
                    value = inputState.productName,
                    onValueChange = onUpdateName,
                    label = { Text("Product Name / SKU") },
                    leadingIcon = { Icon(Icons.Outlined.ShoppingBag, contentDescription = null) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("pricing_input_name"),
                    singleLine = true
                )

                // 2. Cost Price & Existing Selling Price (2 Columns)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedTextField(
                        value = inputState.costPrice,
                        onValueChange = onUpdateCost,
                        label = { Text("Cost Price (₹)*") },
                        leadingIcon = { Icon(Icons.Outlined.CurrencyRupee, contentDescription = null) },
                        modifier = Modifier
                            .weight(1f)
                            .testTag("pricing_input_cost"),
                        singleLine = true
                    )
                    OutlinedTextField(
                        value = inputState.existingSellingPrice,
                        onValueChange = onUpdateExistingPrice,
                        label = { Text("Current Selling (₹)") },
                        leadingIcon = { Icon(Icons.Outlined.Sell, contentDescription = null) },
                        modifier = Modifier
                            .weight(1f)
                            .testTag("pricing_input_current_selling"),
                        singleLine = true
                    )
                }

                // 3. Category & Fabric Type
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedTextField(
                        value = inputState.category,
                        onValueChange = onUpdateCategory,
                        label = { Text("Category") },
                        leadingIcon = { Icon(Icons.Outlined.Category, contentDescription = null) },
                        modifier = Modifier
                            .weight(1f)
                            .testTag("pricing_input_category"),
                        singleLine = true
                    )
                    OutlinedTextField(
                        value = inputState.fabricType,
                        onValueChange = onUpdateFabric,
                        label = { Text("Fabric / Material") },
                        leadingIcon = { Icon(Icons.Outlined.Texture, contentDescription = null) },
                        modifier = Modifier
                            .weight(1f)
                            .testTag("pricing_input_fabric"),
                        singleLine = true
                    )
                }

                // 4. Competitor Price & Target Margin
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedTextField(
                        value = inputState.competitorPrice,
                        onValueChange = onUpdateCompetitorPrice,
                        label = { Text("Competitor Price (₹)") },
                        leadingIcon = { Icon(Icons.Outlined.PriceCheck, contentDescription = null) },
                        modifier = Modifier
                            .weight(1f)
                            .testTag("pricing_input_competitor"),
                        singleLine = true
                    )
                    OutlinedTextField(
                        value = inputState.targetMargin,
                        onValueChange = onUpdateTargetMargin,
                        label = { Text("Target Margin (%)") },
                        leadingIcon = { Icon(Icons.Outlined.Percent, contentDescription = null) },
                        modifier = Modifier
                            .weight(1f)
                            .testTag("pricing_input_target_margin"),
                        singleLine = true
                    )
                }

                // 5. Dealer Category & Brand
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedTextField(
                        value = inputState.dealerCategory,
                        onValueChange = onUpdateDealerCat,
                        label = { Text("Dealer Tier") },
                        leadingIcon = { Icon(Icons.Outlined.Store, contentDescription = null) },
                        modifier = Modifier
                            .weight(1f)
                            .testTag("pricing_input_dealer_tier"),
                        singleLine = true
                    )
                    OutlinedTextField(
                        value = inputState.brand,
                        onValueChange = onUpdateBrand,
                        label = { Text("Brand / Label") },
                        leadingIcon = { Icon(Icons.Outlined.WorkspacePremium, contentDescription = null) },
                        modifier = Modifier
                            .weight(1f)
                            .testTag("pricing_input_brand"),
                        singleLine = true
                    )
                }

                // 6. Region & Market Type
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedTextField(
                        value = inputState.region,
                        onValueChange = onUpdateRegion,
                        label = { Text("Region") },
                        leadingIcon = { Icon(Icons.Outlined.Place, contentDescription = null) },
                        modifier = Modifier
                            .weight(1f)
                            .testTag("pricing_input_region"),
                        singleLine = true
                    )
                    OutlinedTextField(
                        value = inputState.marketType,
                        onValueChange = onUpdateMarketType,
                        label = { Text("Market Segment") },
                        leadingIcon = { Icon(Icons.Outlined.ShowChart, contentDescription = null) },
                        modifier = Modifier
                            .weight(1f)
                            .testTag("pricing_input_market_type"),
                        singleLine = true
                    )
                }

                // Action Button
                Button(
                    onClick = onGenerate,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                        .testTag("pricing_generate_button"),
                    shape = RoundedCornerShape(12.dp),
                    enabled = !loadingState
                ) {
                    if (loadingState) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(22.dp),
                            color = MaterialTheme.colorScheme.onPrimary,
                            strokeWidth = 2.5.dp
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text("Calculating Optimal Pricing Tiers...")
                    } else {
                        Icon(Icons.Filled.AutoAwesome, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            "Generate AI Pricing Intelligence",
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp
                        )
                    }
                }
            }
        }

        // Output Result Section
        if (resultState != null) {
            PricingResultCard(
                result = resultState,
                onCopy = onCopy,
                onToggleFavorite = onToggleFavorite
            )
        }
    }
}

@Composable
fun PricingResultCard(
    result: AIPricingResultEntity,
    onCopy: (String, String) -> Unit,
    onToggleFavorite: (AIPricingResultEntity) -> Unit
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.25f)),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
        modifier = Modifier.testTag("pricing_result_card")
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // Header Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = result.productName,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        if (result.isFallback) {
                            Spacer(modifier = Modifier.width(6.dp))
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = MaterialTheme.colorScheme.tertiaryContainer
                            ) {
                                Text(
                                    text = "OFFLINE ENGINE",
                                    color = MaterialTheme.colorScheme.onTertiaryContainer,
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
                                )
                            }
                        }
                    }
                    Text(
                        text = "${result.fabricType} • Base Cost: ₹${result.costPrice}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                IconButton(
                    onClick = { onToggleFavorite(result) },
                    modifier = Modifier.testTag("pricing_toggle_fav")
                ) {
                    Icon(
                        imageVector = if (result.isFavorite) Icons.Filled.Bookmark else Icons.Outlined.BookmarkBorder,
                        contentDescription = "Save Bookmark",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }

            HorizontalDivider()

            // 5-Tier Pricing Grid
            Text(
                text = "Multi-Channel Pricing Structure",
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                PricingTierItem(
                    title = "Retail (B2C)",
                    price = result.retailPrice,
                    subtitle = "Consumer MSRP",
                    highlight = true,
                    modifier = Modifier.weight(1f),
                    onCopy = { onCopy("₹${result.retailPrice}", "Retail Price") }
                )
                PricingTierItem(
                    title = "Wholesale (B2B)",
                    price = result.wholesalePrice,
                    subtitle = "Bulk Lot Price",
                    highlight = false,
                    modifier = Modifier.weight(1f),
                    onCopy = { onCopy("₹${result.wholesalePrice}", "Wholesale Price") }
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                PricingTierItem(
                    title = "Regional Distributor",
                    price = result.distributorPrice,
                    subtitle = "Master Hub Rate",
                    highlight = false,
                    modifier = Modifier.weight(1f),
                    onCopy = { onCopy("₹${result.distributorPrice}", "Distributor Price") }
                )
                PricingTierItem(
                    title = "Authorized Dealer",
                    price = result.dealerPrice,
                    subtitle = "Boutique Tier",
                    highlight = false,
                    modifier = Modifier.weight(1f),
                    onCopy = { onCopy("₹${result.dealerPrice}", "Dealer Price") }
                )
            }

            // Haute Couture VIP Tier
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = MaterialTheme.colorScheme.surface,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onCopy("₹${result.premiumPrice}", "Premium Price") }
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Diamond,
                            contentDescription = null,
                            tint = Color(0xFFD4AF37),
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = "Haute Couture / VIP Bespoke Tier",
                                fontWeight = FontWeight.Bold,
                                style = MaterialTheme.typography.bodyMedium
                            )
                            Text(
                                text = "Luxury showroom & bridal appointment pricing",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                    Text(
                        text = "₹${result.premiumPrice}",
                        fontWeight = FontWeight.ExtraBold,
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }

            // Key Metrics Pill Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                MetricChip(
                    title = "Margin",
                    value = "${result.recommendedMargin}%",
                    modifier = Modifier.weight(1f)
                )
                MetricChip(
                    title = "Profit Markup",
                    value = "+${result.profitPercentage}%",
                    modifier = Modifier.weight(1f)
                )
                MetricChip(
                    title = "Max Discount",
                    value = "${result.discountLimit}%",
                    modifier = Modifier.weight(1f)
                )
                MetricChip(
                    title = "AI Confidence",
                    value = "${result.priceConfidenceScore}%",
                    modifier = Modifier.weight(1f)
                )
            }

            // AI Strategy Rationale
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.7f),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Outlined.Lightbulb,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "AI Pricing Rationale",
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.labelLarge
                        )
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = result.aiRationale,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    if (result.channelAdvice.isNotBlank()) {
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "💡 Advice: ${result.channelAdvice}",
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.Medium,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun PricingTierItem(
    title: String,
    price: Double,
    subtitle: String,
    highlight: Boolean,
    modifier: Modifier = Modifier,
    onCopy: () -> Unit
) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = if (highlight) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surface,
        modifier = modifier.clickable { onCopy() }
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Bold,
                    color = if (highlight) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurface
                )
                Icon(
                    imageVector = Icons.Outlined.ContentCopy,
                    contentDescription = "Copy",
                    modifier = Modifier.size(14.dp),
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "₹${Math.round(price * 100.0) / 100.0}",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.ExtraBold,
                color = if (highlight) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodySmall,
                fontSize = 10.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun MetricChip(
    title: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = RoundedCornerShape(10.dp),
        color = MaterialTheme.colorScheme.surface,
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.labelSmall,
                fontSize = 10.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = value,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
fun MultiChannelMatrixTab(
    resultState: AIPricingResultEntity?,
    calculatorState: com.example.vascs.viewmodel.MarginCalculatorState,
    onUpdateCalcCost: (String) -> Unit,
    onUpdateCalcSelling: (String) -> Unit,
    onUpdateCalcDiscount: (String) -> Unit,
    onCopy: (String, String) -> Unit
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Channel Distribution Matrix
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Outlined.Hub,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Omni-Channel Tier Matrix",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                }

                val cost = resultState?.costPrice ?: 6500.0
                val retail = resultState?.retailPrice ?: 14500.0
                val wholesale = resultState?.wholesalePrice ?: 9800.0
                val dist = resultState?.distributorPrice ?: 8775.0
                val dealer = resultState?.dealerPrice ?: 9230.0
                val premium = resultState?.premiumPrice ?: 18560.0

                ChannelMatrixRow(channel = "Haute Couture VIP", price = premium, cost = cost, margin = ((premium - cost) / premium) * 100.0, share = "10% Volume", color = Color(0xFFD4AF37), onCopy = onCopy)
                HorizontalDivider()
                ChannelMatrixRow(channel = "Direct Retail (B2C)", price = retail, cost = cost, margin = ((retail - cost) / retail) * 100.0, share = "35% Volume", color = MaterialTheme.colorScheme.primary, onCopy = onCopy)
                HorizontalDivider()
                ChannelMatrixRow(channel = "Authorized Boutique Dealers", price = dealer, cost = cost, margin = ((dealer - cost) / dealer) * 100.0, share = "25% Volume", color = Color(0xFF388E3C), onCopy = onCopy)
                HorizontalDivider()
                ChannelMatrixRow(channel = "Wholesale Mandi Lots", price = wholesale, cost = cost, margin = ((wholesale - cost) / wholesale) * 100.0, share = "20% Volume", color = Color(0xFF0288D1), onCopy = onCopy)
                HorizontalDivider()
                ChannelMatrixRow(channel = "Master Regional Distributors", price = dist, cost = cost, margin = ((dist - cost) / dist) * 100.0, share = "10% Volume", color = Color(0xFF7B1FA2), onCopy = onCopy)
            }
        }

        // Live Margin & Profit Scenario Simulator
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Outlined.Calculate,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Interactive What-If Margin Simulator",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedTextField(
                        value = calculatorState.customCost,
                        onValueChange = onUpdateCalcCost,
                        label = { Text("Sim Cost (₹)") },
                        modifier = Modifier.weight(1f),
                        singleLine = true
                    )
                    OutlinedTextField(
                        value = calculatorState.customSellingPrice,
                        onValueChange = onUpdateCalcSelling,
                        label = { Text("Sim Price (₹)") },
                        modifier = Modifier.weight(1f),
                        singleLine = true
                    )
                    OutlinedTextField(
                        value = calculatorState.customDealerDiscountPct,
                        onValueChange = onUpdateCalcDiscount,
                        label = { Text("Dealer Disc %") },
                        modifier = Modifier.weight(1f),
                        singleLine = true
                    )
                }

                // Computed Output Bar
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = MaterialTheme.colorScheme.surface,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("Gross Margin", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            Text("${calculatorState.calculatedMarginPct}%", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                        }
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("Markup Profit", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            Text("+${calculatorState.calculatedProfitPct}%", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = Color(0xFF388E3C))
                        }
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("Dealer Net Rate", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            Text("₹${calculatorState.calculatedDealerNet}", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.secondary)
                        }
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("Dealer Net Margin", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            Text("${calculatorState.calculatedDealerMargin}%", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = Color(0xFF0288D1))
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ChannelMatrixRow(
    channel: String,
    price: Double,
    cost: Double,
    margin: Double,
    share: String,
    color: Color,
    onCopy: (String, String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onCopy("₹${Math.round(price)}", channel) }
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
            Box(
                modifier = Modifier
                    .size(10.dp)
                    .clip(CircleShape)
                    .background(color)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(
                    text = channel,
                    fontWeight = FontWeight.SemiBold,
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "$share • Margin: ${Math.round(margin * 10.0) / 10.0}%",
                    style = MaterialTheme.typography.bodySmall,
                    fontSize = 11.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
        Text(
            text = "₹${Math.round(price * 100.0) / 100.0}",
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
fun AnalyticsAndBenchmarksTab(
    resultState: AIPricingResultEntity?,
    onCopy: (String, String) -> Unit
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        val res = resultState ?: AIPricingResultEntity(
            productName = "Royal Banarasi Silk Saree",
            costPrice = 6500.0,
            retailPrice = 14500.0,
            wholesalePrice = 9800.0,
            distributorPrice = 8775.0,
            dealerPrice = 9230.0,
            premiumPrice = 18560.0,
            discountLimit = 15.0,
            recommendedMargin = 55.17,
            profitPercentage = 123.08,
            marketCompetitivenessScore = 92,
            priceConfidenceScore = 94,
            competitorDifference = -700.0,
            priceStrength = "Market Competitive Dominance",
            marketRank = "#1 Best Wholesale Margin",
            aiRationale = "Optimized price structure generates robust channel velocity and brand equity.",
            volumeBreakEvenUnits = 85,
            channelAdvice = "15-day dealer terms recommended."
        )

        // Market Competitiveness & Confidence Score Gauges
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "Quantitative Market Intelligence",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    GaugeCard(
                        title = "Competitiveness",
                        score = res.marketCompetitivenessScore,
                        subtitle = "Market Dominance Index",
                        modifier = Modifier.weight(1f)
                    )
                    GaugeCard(
                        title = "AI Confidence",
                        score = res.priceConfidenceScore,
                        subtitle = "Bayesian Fit Score",
                        modifier = Modifier.weight(1f)
                    )
                }

                HorizontalDivider()

                // Strategic Positioning Highlights
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text("Price Strength", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Text(res.priceStrength, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.primary)
                    }
                    Column(horizontalAlignment = Alignment.End) {
                        Text("Market Rank", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Text(res.marketRank, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.bodyMedium, color = Color(0xFF388E3C))
                    }
                }
            }
        }

        // Competitor Benchmark & Break-Even
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "Competitor Differential & Volume Target",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = MaterialTheme.colorScheme.surfaceVariant,
                        modifier = Modifier.weight(1f)
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text("Competitor Diff", style = MaterialTheme.typography.labelSmall)
                            Spacer(modifier = Modifier.height(4.dp))
                            val diffText = if (res.competitorDifference < 0) "₹${-res.competitorDifference} Lower" else "₹${res.competitorDifference} Premium"
                            Text(
                                text = diffText,
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold,
                                color = if (res.competitorDifference <= 0) Color(0xFF388E3C) else MaterialTheme.colorScheme.primary
                            )
                            Text("vs Market Benchmark", style = MaterialTheme.typography.bodySmall, fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                    }

                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = MaterialTheme.colorScheme.surfaceVariant,
                        modifier = Modifier.weight(1f)
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text("Break-Even Target", style = MaterialTheme.typography.labelSmall)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "${res.volumeBreakEvenUnits} Units",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.secondary
                            )
                            Text("Production Run Lot", style = MaterialTheme.typography.bodySmall, fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun GaugeCard(
    title: String,
    score: Int,
    subtitle: String,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = MaterialTheme.colorScheme.surfaceVariant,
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(title, style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.SemiBold)
            Spacer(modifier = Modifier.height(8.dp))
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.size(60.dp)
            ) {
                CircularProgressIndicator(
                    progress = { score / 100f },
                    modifier = Modifier.fillMaxSize(),
                    strokeWidth = 6.dp,
                    color = if (score >= 85) Color(0xFF388E3C) else MaterialTheme.colorScheme.primary,
                    trackColor = MaterialTheme.colorScheme.surface
                )
                Text(
                    text = "$score",
                    fontWeight = FontWeight.ExtraBold,
                    style = MaterialTheme.typography.titleMedium
                )
            }
            Spacer(modifier = Modifier.height(6.dp))
            Text(subtitle, style = MaterialTheme.typography.bodySmall, fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}

@Composable
fun AuditHistoryTab(
    historyList: List<AIPricingHistoryEntity>,
    rulesList: List<AIPricingRuleEntity>,
    onSelectHistory: (AIPricingHistoryEntity) -> Unit,
    onDeleteHistory: (Long) -> Unit,
    onClearHistory: () -> Unit,
    onApplyRule: (AIPricingRuleEntity) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        if (historyList.isEmpty()) {
            item {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(32.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.History,
                            contentDescription = null,
                            modifier = Modifier.size(48.dp),
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            text = "No Pricing History Yet",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Generated AI pricing intelligence records will be saved here automatically for compliance and auditing.",
                            style = MaterialTheme.typography.bodySmall,
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        } else {
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Pricing Recommendation Log (${historyList.size})",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold
                    )
                    TextButton(onClick = onClearHistory) {
                        Text("Clear All")
                    }
                }
            }

            items(historyList, key = { it.historyId }) { item ->
                Card(
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onSelectHistory(item) }
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = item.productName,
                                fontWeight = FontWeight.Bold,
                                style = MaterialTheme.typography.bodyMedium
                            )
                            Text(
                                text = "${item.category} • Cost: ₹${item.costPrice} • Retail: ₹${item.retailPrice}",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Text(
                                text = "Margin: ${item.recommendedMargin}% • Profit: +${item.profitPercentage}% • Dealer: ₹${item.dealerPrice}",
                                style = MaterialTheme.typography.bodySmall,
                                fontSize = 11.sp,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }

                        IconButton(onClick = { onDeleteHistory(item.historyId) }) {
                            Icon(
                                imageVector = Icons.Outlined.Delete,
                                contentDescription = "Delete",
                                tint = MaterialTheme.colorScheme.error
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun PricingExportDialog(
    result: AIPricingResultEntity?,
    onDismiss: () -> Unit,
    onCopy: (String, String) -> Unit
) {
    val res = result ?: AIPricingResultEntity(
        productName = "Royal Banarasi Silk Saree",
        costPrice = 6500.0,
        retailPrice = 14500.0,
        wholesalePrice = 9800.0,
        distributorPrice = 8775.0,
        dealerPrice = 9230.0,
        premiumPrice = 18560.0,
        discountLimit = 15.0,
        recommendedMargin = 55.17,
        profitPercentage = 123.08,
        marketCompetitivenessScore = 92,
        priceConfidenceScore = 94,
        competitorDifference = -700.0,
        priceStrength = "Market Competitive Dominance",
        marketRank = "#1 Best Wholesale Margin",
        aiRationale = "Optimized price structure generates robust channel velocity and brand equity.",
        volumeBreakEvenUnits = 85,
        channelAdvice = "15-day dealer terms recommended."
    )

    val exportText = buildString {
        appendLine("==================================================")
        appendLine("VASCS ULTIMA ENTERPRISE PRICING DOSSIER")
        appendLine("Product: ${res.productName}")
        appendLine("Category: ${res.category} | Fabric: ${res.fabricType}")
        appendLine("Cost Base: INR ${res.costPrice}")
        appendLine("==================================================")
        appendLine("1. Retail Price (B2C):        INR ${res.retailPrice}")
        appendLine("2. Wholesale Price (B2B):      INR ${res.wholesalePrice}")
        appendLine("3. Master Distributor Price:   INR ${res.distributorPrice}")
        appendLine("4. Authorized Dealer Price:    INR ${res.dealerPrice}")
        appendLine("5. Haute Couture VIP Price:    INR ${res.premiumPrice}")
        appendLine("--------------------------------------------------")
        appendLine("Recommended Gross Margin:      ${res.recommendedMargin}%")
        appendLine("Cost Markup Profit:            +${res.profitPercentage}%")
        appendLine("Promotional Discount Limit:    ${res.discountLimit}%")
        appendLine("Market Competitiveness Index:  ${res.marketCompetitivenessScore}/100")
        appendLine("AI Bayesian Confidence Score:  ${res.priceConfidenceScore}/100")
        appendLine("Estimated Break-Even Volume:   ${res.volumeBreakEvenUnits} units")
        appendLine("--------------------------------------------------")
        appendLine("Strategic Pricing Rationale:")
        appendLine(res.aiRationale)
        appendLine("==================================================")
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Outlined.PictureAsPdf, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Enterprise Pricing Dossier")
            }
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "The complete multi-tier commercial pricing schedule has been prepared for PDF export & dealer dispatch.",
                    style = MaterialTheme.typography.bodyMedium
                )
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = exportText,
                        style = MaterialTheme.typography.bodySmall,
                        fontFamily = FontFamily.Monospace,
                        fontSize = 10.sp,
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
        },
        confirmButton = {
            Button(onClick = {
                onCopy(exportText, "Full Pricing Dossier")
                onDismiss()
            }) {
                Icon(Icons.Outlined.ContentCopy, contentDescription = null)
                Spacer(modifier = Modifier.width(4.dp))
                Text("Copy Full Dossier")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Close")
            }
        }
    )
}
