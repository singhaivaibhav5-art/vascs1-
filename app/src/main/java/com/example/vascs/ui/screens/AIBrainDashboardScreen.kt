package com.example.vascs.ui.screens

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.FactCheck
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material.icons.filled.Timeline
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.vascs.data.ai.AIResponseParser
import com.example.vascs.ui.theme.GoldAccent
import com.example.vascs.ui.theme.Maroon500
import com.example.vascs.ui.theme.Maroon700
import com.example.vascs.viewmodel.AICatalogueUiState
import com.example.vascs.viewmodel.AIDealerUiState
import com.example.vascs.viewmodel.AIDemandUiState
import com.example.vascs.viewmodel.AIPricingUiState
import com.example.vascs.viewmodel.AIStrategyUiState
import com.example.vascs.viewmodel.VascsAIBrainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AIBrainDashboardScreen(
    viewModel: VascsAIBrainViewModel,
    onNavigateBack: () -> Unit
) {
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    var showApiKeyDialog by remember { mutableStateOf(false) }
    var showHistoryDialog by remember { mutableStateOf(false) }

    val isKeyConfigured by viewModel.isApiKeyConfigured.collectAsState()
    val prompts by viewModel.allAiPrompts.collectAsState()

    val tabTitles = listOf(
        "Catalogue AI",
        "Pricing AI",
        "Demand AI",
        "Dealer AI",
        "Strategy AI"
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "VASCS AI BRAIN",
                                fontWeight = FontWeight.Bold,
                                color = GoldAccent,
                                fontSize = 18.sp
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Surface(
                                shape = RoundedCornerShape(12.dp),
                                color = if (isKeyConfigured) Color(0xFF1B5E20) else Color(0xFFE65100)
                            ) {
                                Text(
                                    text = if (isKeyConfigured) "GEMINI LIVE" else "HEURISTIC FALLBACK",
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                        }
                        Text(
                            text = "Cognitive Enterprise Intelligence & Neural Synthesis",
                            fontSize = 11.sp,
                            color = Color.White.copy(alpha = 0.8f)
                        )
                    }
                },
                navigationIcon = {
                    IconButton(
                        onClick = onNavigateBack,
                        modifier = Modifier.testTag("ai_brain_back_button")
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = GoldAccent
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { showHistoryDialog = true }) {
                        Icon(
                            imageVector = Icons.Default.Timeline,
                            contentDescription = "AI Audit History",
                            tint = GoldAccent
                        )
                    }
                    IconButton(onClick = { showApiKeyDialog = true }) {
                        Icon(
                            imageVector = Icons.Default.Key,
                            contentDescription = "Configure Gemini API Key",
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
                .background(Color(0xFFF9F7F2))
        ) {
            // Tabs Bar
            ScrollableTabRow(
                selectedTabIndex = selectedTabIndex,
                containerColor = Maroon500,
                contentColor = GoldAccent,
                edgePadding = 16.dp,
                indicator = { tabPositions ->
                    TabRowDefaults.SecondaryIndicator(
                        Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex]),
                        color = GoldAccent,
                        height = 3.dp
                    )
                }
            ) {
                tabTitles.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTabIndex == index,
                        onClick = { selectedTabIndex = index },
                        text = {
                            Text(
                                text = title,
                                fontWeight = if (selectedTabIndex == index) FontWeight.Bold else FontWeight.Normal,
                                color = if (selectedTabIndex == index) GoldAccent else Color.White.copy(alpha = 0.8f)
                            )
                        }
                    )
                }
            }

            // Tab Content Body
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                when (selectedTabIndex) {
                    0 -> CatalogueAITab(viewModel)
                    1 -> PricingAITab(viewModel)
                    2 -> DemandAITab(viewModel)
                    3 -> DealerAITab(viewModel)
                    4 -> StrategyAITab(viewModel)
                }
            }
        }
    }

    if (showApiKeyDialog) {
        ApiKeyConfigDialog(
            initialKey = "",
            isConfigured = isKeyConfigured,
            onDismiss = { showApiKeyDialog = false },
            onSaveKey = { newKey ->
                viewModel.updateApiKey(newKey)
                showApiKeyDialog = false
            }
        )
    }

    if (showHistoryDialog) {
        AIHistoryDialog(
            prompts = prompts,
            onDismiss = { showHistoryDialog = false }
        )
    }
}

// -------------------------------------------------------------------------------------------------
// TAB 1: CATALOGUE AI
// -------------------------------------------------------------------------------------------------
@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun CatalogueAITab(viewModel: VascsAIBrainViewModel) {
    val state by viewModel.catalogueState.collectAsState()
    val clipboard = LocalClipboardManager.current
    val context = LocalContext.current

    var productName by remember { mutableStateOf("Varanasi Katan Silk Saree") }
    var category by remember { mutableStateOf("Bridal Sarees") }
    var fabric by remember { mutableStateOf("Pure Mulberry Silk & Gold Zari") }
    var color by remember { mutableStateOf("Crimson Red & Antique Gold") }
    var priceText by remember { mutableStateOf("18500") }

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Quick Presets
        Card(
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(2.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Quick Product Presets",
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp,
                    color = Maroon700
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    AssistChip(
                        onClick = {
                            productName = "Varanasi Bridal Katan Saree"
                            category = "Bridal Silk"
                            fabric = "Pure Mulberry Katan Silk"
                            color = "Sindoor Red & Zari"
                            priceText = "24500"
                        },
                        label = { Text("Bridal Katan Silk") },
                        colors = AssistChipDefaults.assistChipColors(labelColor = Maroon700)
                    )
                    AssistChip(
                        onClick = {
                            productName = "Chanderi Tissue Jacquard Dupatta"
                            category = "Dupattas"
                            fabric = "Silk Cotton Tissue"
                            color = "Rose Quartz & Silver"
                            priceText = "4800"
                        },
                        label = { Text("Chanderi Tissue") }
                    )
                    AssistChip(
                        onClick = {
                            productName = "Embroidered Royal Raw Silk Sherwani Fabric"
                            category = "Men's Ethnic"
                            fabric = "Matka Raw Silk"
                            color = "Ivory Cream"
                            priceText = "12500"
                        },
                        label = { Text("Raw Silk Sherwani") }
                    )
                }
            }
        }

        // Input Form
        Card(
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(3.dp),
            shape = RoundedCornerShape(14.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Text(
                    text = "Product Inputs",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Maroon700
                )

                OutlinedTextField(
                    value = productName,
                    onValueChange = { productName = it },
                    label = { Text("Product Name") },
                    modifier = Modifier.fillMaxWidth().testTag("ai_cat_product_name")
                )

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = category,
                        onValueChange = { category = it },
                        label = { Text("Category") },
                        modifier = Modifier.weight(1f)
                    )
                    OutlinedTextField(
                        value = priceText,
                        onValueChange = { priceText = it },
                        label = { Text("Price (₹)") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.weight(1f)
                    )
                }

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = fabric,
                        onValueChange = { fabric = it },
                        label = { Text("Fabric / Material") },
                        modifier = Modifier.weight(1f)
                    )
                    OutlinedTextField(
                        value = color,
                        onValueChange = { color = it },
                        label = { Text("Color / Shade") },
                        modifier = Modifier.weight(1f)
                    )
                }

                Button(
                    onClick = {
                        val price = priceText.toDoubleOrNull() ?: 0.0
                        viewModel.generateCatalogue(productName, category, fabric, color, price)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .testTag("ai_cat_generate_button"),
                    colors = ButtonDefaults.buttonColors(containerColor = Maroon700)
                ) {
                    Icon(imageVector = Icons.Default.AutoAwesome, contentDescription = null, tint = GoldAccent)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "GENERATE LUXURY CATALOGUE", fontWeight = FontWeight.Bold, color = GoldAccent)
                }
            }
        }

        // Result States
        when (val curr = state) {
            is AICatalogueUiState.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(32.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        CircularProgressIndicator(color = Maroon700)
                        Spacer(modifier = Modifier.height(12.dp))
                        Text("VASCS AI Brain synthesizing luxury copy...", color = Maroon700, fontWeight = FontWeight.Medium)
                    }
                }
            }

            is AICatalogueUiState.Error -> {
                Card(colors = CardDefaults.cardColors(containerColor = Color(0xFFFFEBEE))) {
                    Text(
                        text = "Generation Error: ${curr.message}",
                        color = Color(0xFFC62828),
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }

            is AICatalogueUiState.Success -> {
                val res = curr.result
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    // Title Card
                    ResultCard(title = "Generated Product Title", content = res.productTitle) {
                        clipboard.setText(AnnotatedString(res.productTitle))
                        Toast.makeText(context, "Title Copied!", Toast.LENGTH_SHORT).show()
                    }

                    // Description Card
                    ResultCard(title = "Product Description", content = res.productDescription) {
                        clipboard.setText(AnnotatedString(res.productDescription))
                        Toast.makeText(context, "Description Copied!", Toast.LENGTH_SHORT).show()
                    }

                    // Instagram Caption
                    ResultCard(title = "Instagram Caption", content = res.instagramCaption) {
                        clipboard.setText(AnnotatedString(res.instagramCaption))
                        Toast.makeText(context, "Instagram Caption Copied!", Toast.LENGTH_SHORT).show()
                    }

                    // WhatsApp Broadcast
                    ResultCard(title = "WhatsApp Caption", content = res.whatsappCaption) {
                        clipboard.setText(AnnotatedString(res.whatsappCaption))
                        Toast.makeText(context, "WhatsApp Broadcast Copied!", Toast.LENGTH_SHORT).show()
                    }

                    // SEO Keywords
                    Card(
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(2.dp),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(text = "SEO Keywords", fontWeight = FontWeight.Bold, color = Maroon700, fontSize = 14.sp)
                            Spacer(modifier = Modifier.height(8.dp))
                            FlowRow(
                                horizontalArrangement = Arrangement.spacedBy(6.dp),
                                verticalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                res.seoKeywords.forEach { kw ->
                                    Surface(
                                        shape = RoundedCornerShape(16.dp),
                                        color = Maroon500.copy(alpha = 0.1f),
                                        border = androidx.compose.foundation.BorderStroke(1.dp, Maroon500.copy(alpha = 0.3f))
                                    ) {
                                        Text(
                                            text = kw,
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.Medium,
                                            color = Maroon700,
                                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

            AICatalogueUiState.Idle -> {
                // Idle state placeholder
            }
        }
    }
}

// -------------------------------------------------------------------------------------------------
// TAB 2: PRICING AI
// -------------------------------------------------------------------------------------------------
@Composable
private fun PricingAITab(viewModel: VascsAIBrainViewModel) {
    val state by viewModel.pricingState.collectAsState()

    var costPriceText by remember { mutableStateOf("4500") }
    var category by remember { mutableStateOf("Banarasi Pure Silk") }
    var marginRules by remember { mutableStateOf("30% Dealer Margin, 55% Retail Target, 5% Logistics Cushion") }

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Quick Presets
        Card(
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(2.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = "Pricing Presets", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = Maroon700)
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    AssistChip(
                        onClick = {
                            costPriceText = "3200"
                            category = "Jacquard Silk Shirting"
                            marginRules = "25% Dealer Tier-1, 40% B2B Wholesale, 60% Retail"
                        },
                        label = { Text("Silk Shirting (Cost ₹3.2k)") }
                    )
                    AssistChip(
                        onClick = {
                            costPriceText = "8500"
                            category = "Heavy Zari Bridal Saree"
                            marginRules = "35% Dealer Network, 70% Flagship Showroom Gross Margin"
                        },
                        label = { Text("Bridal Zari (Cost ₹8.5k)") }
                    )
                }
            }
        }

        // Form
        Card(
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(3.dp),
            shape = RoundedCornerShape(14.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Text(text = "Cost & Channel Parameters", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Maroon700)

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = costPriceText,
                        onValueChange = { costPriceText = it },
                        label = { Text("Cost Price (₹)") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.weight(1f).testTag("ai_price_cost")
                    )
                    OutlinedTextField(
                        value = category,
                        onValueChange = { category = it },
                        label = { Text("Category") },
                        modifier = Modifier.weight(1f)
                    )
                }

                OutlinedTextField(
                    value = marginRules,
                    onValueChange = { marginRules = it },
                    label = { Text("Margin Rules / Constraints") },
                    modifier = Modifier.fillMaxWidth()
                )

                Button(
                    onClick = {
                        val cost = costPriceText.toDoubleOrNull() ?: 0.0
                        viewModel.calculatePricing(cost, category, marginRules)
                    },
                    modifier = Modifier.fillMaxWidth().height(50.dp).testTag("ai_price_calc_button"),
                    colors = ButtonDefaults.buttonColors(containerColor = Maroon700)
                ) {
                    Icon(imageVector = Icons.Default.Calculate, contentDescription = null, tint = GoldAccent)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "OPTIMIZE PRICING TIERS", fontWeight = FontWeight.Bold, color = GoldAccent)
                }
            }
        }

        when (val curr = state) {
            is AIPricingUiState.Loading -> {
                Box(modifier = Modifier.fillMaxWidth().padding(32.dp), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = Maroon700)
                }
            }

            is AIPricingUiState.Error -> {
                Card(colors = CardDefaults.cardColors(containerColor = Color(0xFFFFEBEE))) {
                    Text(text = "Error: ${curr.message}", color = Color(0xFFC62828), modifier = Modifier.padding(16.dp))
                }
            }

            is AIPricingUiState.Success -> {
                val res = curr.result
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    // Price Grid
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        PricingMetricCard("Retail Price", "₹${res.retailPrice}", Color(0xFF2E7D32), Modifier.weight(1f))
                        PricingMetricCard("Wholesale Price", "₹${res.wholesalePrice}", Color(0xFF1565C0), Modifier.weight(1f))
                        PricingMetricCard("Dealer Price", "₹${res.dealerPrice}", Color(0xFF6A1B9A), Modifier.weight(1f))
                    }

                    // Suggested Margin
                    Card(
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(2.dp),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(imageVector = Icons.Default.TrendingUp, contentDescription = null, tint = Color(0xFF2E7D32))
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "Suggested Retail Margin: ${res.suggestedMarginPct}%",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 15.sp,
                                    color = Color(0xFF2E7D32)
                                )
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            HorizontalDivider()
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Pricing Rationale",
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp,
                                color = Maroon700
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = res.pricingRationale,
                                fontSize = 13.sp,
                                color = Color(0xFF333333),
                                lineHeight = 18.sp
                            )
                        }
                    }
                }
            }

            AIPricingUiState.Idle -> {}
        }
    }
}

@Composable
private fun PricingMetricCard(title: String, value: String, accentColor: Color, modifier: Modifier) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = title, fontSize = 11.sp, color = Color.Gray, fontWeight = FontWeight.Medium)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = value, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = accentColor)
        }
    }
}

// -------------------------------------------------------------------------------------------------
// TAB 3: DEMAND AI
// -------------------------------------------------------------------------------------------------
@Composable
private fun DemandAITab(viewModel: VascsAIBrainViewModel) {
    val state by viewModel.forecastState.collectAsState()

    var salesHistory by remember { mutableStateOf("1,450 units sold in Q2, trailing 4-week run-rate +18%, stock velocity 22 days") }
    var category by remember { mutableStateOf("Banarasi Bridal Silk") }
    var season by remember { mutableStateOf("Diwali & Festive Q3-Q4 2026") }

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Card(
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(3.dp),
            shape = RoundedCornerShape(14.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Text(text = "Demand Forecast Inputs", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Maroon700)

                OutlinedTextField(
                    value = salesHistory,
                    onValueChange = { salesHistory = it },
                    label = { Text("Sales History & Velocity Summary") },
                    modifier = Modifier.fillMaxWidth().testTag("ai_demand_history")
                )

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = category,
                        onValueChange = { category = it },
                        label = { Text("Category") },
                        modifier = Modifier.weight(1f)
                    )
                    OutlinedTextField(
                        value = season,
                        onValueChange = { season = it },
                        label = { Text("Target Season") },
                        modifier = Modifier.weight(1f)
                    )
                }

                Button(
                    onClick = {
                        viewModel.forecastDemand(salesHistory, category, season)
                    },
                    modifier = Modifier.fillMaxWidth().height(50.dp).testTag("ai_demand_forecast_button"),
                    colors = ButtonDefaults.buttonColors(containerColor = Maroon700)
                ) {
                    Icon(imageVector = Icons.Default.TrendingUp, contentDescription = null, tint = GoldAccent)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "FORECAST DEMAND & REORDER", fontWeight = FontWeight.Bold, color = GoldAccent)
                }
            }
        }

        when (val curr = state) {
            is AIDemandUiState.Loading -> {
                Box(modifier = Modifier.fillMaxWidth().padding(32.dp), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = Maroon700)
                }
            }

            is AIDemandUiState.Error -> {
                Card(colors = CardDefaults.cardColors(containerColor = Color(0xFFFFEBEE))) {
                    Text(text = "Error: ${curr.message}", color = Color(0xFFC62828), modifier = Modifier.padding(16.dp))
                }
            }

            is AIDemandUiState.Success -> {
                val res = curr.result
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        MetricBlock("Demand Prediction", res.demandPrediction, Color(0xFFE65100), Modifier.weight(1f))
                        MetricBlock("Growth Trend", res.growthTrend, Color(0xFF1B5E20), Modifier.weight(1f))
                    }

                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        MetricBlock("Projected Sales", "${res.predictedSalesUnits} units", Color(0xFF0D47A1), Modifier.weight(1f))
                        MetricBlock("Recommended Reorder", "${res.reorderQuantity} units", Color(0xFF4A148C), Modifier.weight(1f))
                    }

                    Card(
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(2.dp),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(text = "AI Replenishment Rationale", fontWeight = FontWeight.Bold, color = Maroon700)
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(text = res.aiRationale, fontSize = 13.sp, color = Color(0xFF333333), lineHeight = 18.sp)
                        }
                    }
                }
            }

            AIDemandUiState.Idle -> {}
        }
    }
}

@Composable
private fun MetricBlock(title: String, value: String, accent: Color, modifier: Modifier) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(text = title, fontSize = 11.sp, color = Color.Gray, fontWeight = FontWeight.Medium)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = value, fontSize = 15.sp, fontWeight = FontWeight.Bold, color = accent)
        }
    }
}

// -------------------------------------------------------------------------------------------------
// TAB 4: DEALER AI
// -------------------------------------------------------------------------------------------------
@Composable
private fun DealerAITab(viewModel: VascsAIBrainViewModel) {
    val state by viewModel.dealerState.collectAsState()

    var dealerPerf by remember { mutableStateOf("142 North Zone dealers; top 20% generate 68% of turnover. 12 dealers show 45+ days invoice lag.") }
    var location by remember { mutableStateOf("North & Central India (UP, Delhi, MP)") }
    var category by remember { mutableStateOf("Bridal & Heavy Handloom Sarees") }

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Card(
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(3.dp),
            shape = RoundedCornerShape(14.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Text(text = "Dealer Network Parameters", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Maroon700)

                OutlinedTextField(
                    value = dealerPerf,
                    onValueChange = { dealerPerf = it },
                    label = { Text("Dealer Performance Summary") },
                    modifier = Modifier.fillMaxWidth().testTag("ai_dealer_perf")
                )

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = location,
                        onValueChange = { location = it },
                        label = { Text("Location / Zone") },
                        modifier = Modifier.weight(1f)
                    )
                    OutlinedTextField(
                        value = category,
                        onValueChange = { category = it },
                        label = { Text("Category") },
                        modifier = Modifier.weight(1f)
                    )
                }

                Button(
                    onClick = {
                        viewModel.recommendDealers(dealerPerf, location, category)
                    },
                    modifier = Modifier.fillMaxWidth().height(50.dp).testTag("ai_dealer_recommend_button"),
                    colors = ButtonDefaults.buttonColors(containerColor = Maroon700)
                ) {
                    Icon(imageVector = Icons.Default.Groups, contentDescription = null, tint = GoldAccent)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "GENERATE DEALER RECOMMENDATIONS", fontWeight = FontWeight.Bold, color = GoldAccent)
                }
            }
        }

        when (val curr = state) {
            is AIDealerUiState.Loading -> {
                Box(modifier = Modifier.fillMaxWidth().padding(32.dp), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = Maroon700)
                }
            }

            is AIDealerUiState.Error -> {
                Card(colors = CardDefaults.cardColors(containerColor = Color(0xFFFFEBEE))) {
                    Text(text = "Error: ${curr.message}", color = Color(0xFFC62828), modifier = Modifier.padding(16.dp))
                }
            }

            is AIDealerUiState.Success -> {
                val res = curr.result
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    // Top Dealers
                    Text(text = "Top Performing Dealers (${res.topDealers.size})", fontWeight = FontWeight.Bold, color = Maroon700, fontSize = 15.sp)
                    res.topDealers.forEach { d ->
                        Card(
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            elevation = CardDefaults.cardElevation(2.dp),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                    Text(text = d.dealerName, fontWeight = FontWeight.Bold, color = Maroon700)
                                    Text(text = "₹${d.annualTurnoverCr} Cr / +${d.growthRatePct}%", fontWeight = FontWeight.Bold, color = Color(0xFF2E7D32), fontSize = 12.sp)
                                }
                                Text(text = "Region: ${d.region}", fontSize = 12.sp, color = Color.Gray)
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(text = "Strength: ${d.keyStrength}", fontSize = 12.sp, color = Color(0xFF333333))
                                Text(text = "Incentive: ${d.recommendedIncentive}", fontSize = 12.sp, color = Maroon500, fontWeight = FontWeight.Medium)
                            }
                        }
                    }

                    // Expansion Dealers
                    Text(text = "Expansion Targets (${res.expansionDealers.size})", fontWeight = FontWeight.Bold, color = Color(0xFF0D47A1), fontSize = 15.sp)
                    res.expansionDealers.forEach { e ->
                        Card(
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            elevation = CardDefaults.cardElevation(2.dp),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Text(text = e.dealerName, fontWeight = FontWeight.Bold, color = Color(0xFF0D47A1))
                                Text(text = "Target Potential: ₹${e.targetRevenueInrCr} Cr | Credit Limit: ₹${e.creditLimitCr} Cr", fontSize = 12.sp, color = Color.DarkGray)
                                Text(text = "Rationale: ${e.expansionRationale}", fontSize = 12.sp, color = Color(0xFF333333))
                            }
                        }
                    }

                    // Recovery Dealers
                    Text(text = "Recovery / Intervention Dealers (${res.recoveryDealers.size})", fontWeight = FontWeight.Bold, color = Color(0xFFC62828), fontSize = 15.sp)
                    res.recoveryDealers.forEach { r ->
                        Card(
                            colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF8E1)),
                            elevation = CardDefaults.cardElevation(2.dp),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                    Text(text = r.dealerName, fontWeight = FontWeight.Bold, color = Color(0xFFB71C1C))
                                    Text(text = "${r.daysOverdue} Days Lag", fontWeight = FontWeight.Bold, color = Color(0xFFC62828), fontSize = 12.sp)
                                }
                                Text(text = "Issue: ${r.issueIdentified}", fontSize = 12.sp, color = Color.DarkGray)
                                Text(text = "Plan: ${r.turnaroundPlan}", fontSize = 12.sp, color = Color(0xFFE65100), fontWeight = FontWeight.Medium)
                            }
                        }
                    }

                    // Strategic Action Plan
                    Card(
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(2.dp),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(text = "Strategic Channel Action Plan", fontWeight = FontWeight.Bold, color = Maroon700)
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(text = res.strategicActionPlan, fontSize = 13.sp, color = Color(0xFF333333), lineHeight = 18.sp)
                        }
                    }
                }
            }

            AIDealerUiState.Idle -> {}
        }
    }
}

// -------------------------------------------------------------------------------------------------
// TAB 5: STRATEGY AI
// -------------------------------------------------------------------------------------------------
@Composable
private fun StrategyAITab(viewModel: VascsAIBrainViewModel) {
    val state by viewModel.strategyState.collectAsState()

    var businessContext by remember { mutableStateOf("VASCS operates 1,420 dealer networks, 8,940 active SKUs, monthly run-rate ₹128.5 Cr. Raw silk prices up 8% QoQ.") }
    var targetGoals by remember { mutableStateOf("Expand high-margin silk bridal catalogue by 40%, achieve 3.5x inventory turn, and establish direct Dubai luxury export hub.") }

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Card(
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(3.dp),
            shape = RoundedCornerShape(14.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Text(text = "Executive Strategy Synthesis", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Maroon700)

                OutlinedTextField(
                    value = businessContext,
                    onValueChange = { businessContext = it },
                    label = { Text("Current Business Context") },
                    modifier = Modifier.fillMaxWidth().testTag("ai_strat_context")
                )

                OutlinedTextField(
                    value = targetGoals,
                    onValueChange = { targetGoals = it },
                    label = { Text("Target Strategic Goals") },
                    modifier = Modifier.fillMaxWidth().testTag("ai_strat_goals")
                )

                Button(
                    onClick = {
                        viewModel.generateStrategy(businessContext, targetGoals)
                    },
                    modifier = Modifier.fillMaxWidth().height(50.dp).testTag("ai_strat_synthesize_button"),
                    colors = ButtonDefaults.buttonColors(containerColor = Maroon700)
                ) {
                    Icon(imageVector = Icons.Default.Psychology, contentDescription = null, tint = GoldAccent)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "SYNTHESIZE STRATEGY BLUEPRINT", fontWeight = FontWeight.Bold, color = GoldAccent)
                }
            }
        }

        when (val curr = state) {
            is AIStrategyUiState.Loading -> {
                Box(modifier = Modifier.fillMaxWidth().padding(32.dp), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = Maroon700)
                }
            }

            is AIStrategyUiState.Error -> {
                Card(colors = CardDefaults.cardColors(containerColor = Color(0xFFFFEBEE))) {
                    Text(text = "Error: ${curr.message}", color = Color(0xFFC62828), modifier = Modifier.padding(16.dp))
                }
            }

            is AIStrategyUiState.Success -> {
                val res = curr.result
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    // Executive Summary
                    Card(
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(3.dp),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(text = "Executive Board Summary", fontWeight = FontWeight.Bold, color = Maroon700, fontSize = 15.sp)
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(text = res.executiveSummary, fontSize = 13.sp, color = Color(0xFF333333), lineHeight = 19.sp)
                        }
                    }

                    // Growth Vectors
                    Text(text = "Growth Vectors", fontWeight = FontWeight.Bold, color = Maroon700, fontSize = 15.sp)
                    res.growthVectors.forEach { v ->
                        Card(
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            elevation = CardDefaults.cardElevation(2.dp),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                    Text(text = v.title, fontWeight = FontWeight.Bold, color = Maroon700)
                                    Text(text = v.projectedRoi, fontWeight = FontWeight.Bold, color = Color(0xFF2E7D32), fontSize = 12.sp)
                                }
                                Text(text = "Timeframe: ${v.timeframe}", fontSize = 11.sp, color = Color.Gray)
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(text = v.description, fontSize = 12.sp, color = Color(0xFF333333))
                            }
                        }
                    }

                    // Risk Mitigations
                    Text(text = "Risk Shield Matrix", fontWeight = FontWeight.Bold, color = Color(0xFFC62828), fontSize = 15.sp)
                    res.riskMitigations.forEach { r ->
                        Card(
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            elevation = CardDefaults.cardElevation(2.dp),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                    Text(text = r.risk, fontWeight = FontWeight.Bold, color = Color(0xFFB71C1C))
                                    Text(text = r.severity, fontWeight = FontWeight.Bold, color = Color(0xFFC62828), fontSize = 11.sp)
                                }
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(text = "Mitigation: ${r.solution}", fontSize = 12.sp, color = Color(0xFF1B5E20))
                            }
                        }
                    }

                    // Capital Allocation Plan
                    Card(
                        colors = CardDefaults.cardColors(containerColor = Maroon700),
                        elevation = CardDefaults.cardElevation(3.dp),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(text = "Capital Allocation & Sovereign Reserves", fontWeight = FontWeight.Bold, color = GoldAccent, fontSize = 14.sp)
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(text = res.capitalAllocationPlan, fontSize = 13.sp, color = Color.White, lineHeight = 18.sp)
                        }
                    }
                }
            }

            AIStrategyUiState.Idle -> {}
        }
    }
}

// -------------------------------------------------------------------------------------------------
// HELPER COMPOSABLES & DIALOGS
// -------------------------------------------------------------------------------------------------
@Composable
private fun ResultCard(title: String, content: String, onCopy: () -> Unit) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = title, fontWeight = FontWeight.Bold, color = Maroon700, fontSize = 14.sp)
                IconButton(onClick = onCopy, modifier = Modifier.size(32.dp)) {
                    Icon(imageVector = Icons.Default.ContentCopy, contentDescription = "Copy", tint = Maroon500, modifier = Modifier.size(18.dp))
                }
            }
            Spacer(modifier = Modifier.height(6.dp))
            Text(text = content, fontSize = 13.sp, color = Color(0xFF333333), lineHeight = 19.sp)
        }
    }
}

@Composable
private fun ApiKeyConfigDialog(
    initialKey: String,
    isConfigured: Boolean,
    onDismiss: () -> Unit,
    onSaveKey: (String) -> Unit
) {
    var apiKeyText by remember { mutableStateOf(initialKey) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(imageVector = Icons.Default.Key, contentDescription = null, tint = GoldAccent)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Gemini AI Configuration", fontWeight = FontWeight.Bold, color = Maroon700)
            }
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text(
                    text = if (isConfigured)
                        "Gemini API key is currently detected and active."
                    else
                        "No environment API key detected. You can paste a custom Google Gemini API Key below, or the app will automatically use deterministic neural heuristics fallback.",
                    fontSize = 13.sp,
                    color = Color.DarkGray
                )
                OutlinedTextField(
                    value = apiKeyText,
                    onValueChange = { apiKeyText = it },
                    label = { Text("Gemini API Key") },
                    placeholder = { Text("AIzaSy...") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = { onSaveKey(apiKeyText) },
                colors = ButtonDefaults.buttonColors(containerColor = Maroon700)
            ) {
                Text("Save Key", color = GoldAccent)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel", color = Maroon700)
            }
        }
    )
}

@Composable
private fun AIHistoryDialog(
    prompts: List<com.example.vascs.data.model.AIPromptEntity>,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(imageVector = Icons.Default.Timeline, contentDescription = null, tint = GoldAccent)
                Spacer(modifier = Modifier.width(8.dp))
                Text("AI Telemetry & Prompt Audit", fontWeight = FontWeight.Bold, color = Maroon700)
            }
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(350.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                if (prompts.isEmpty()) {
                    Text("No AI operations recorded yet in this session.", color = Color.Gray, fontSize = 13.sp)
                } else {
                    prompts.forEach { p ->
                        Card(
                            colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5)),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Column(modifier = Modifier.padding(10.dp)) {
                                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                    Text(text = p.featureType, fontWeight = FontWeight.Bold, color = Maroon700, fontSize = 12.sp)
                                    Text(text = "${p.latencyMs}ms | ${p.status}", fontSize = 11.sp, color = Color.DarkGray)
                                }
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(text = p.inputPayload, fontSize = 11.sp, color = Color(0xFF444444), maxLines = 2)
                            }
                        }
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Close", color = Maroon700)
            }
        }
    )
}
