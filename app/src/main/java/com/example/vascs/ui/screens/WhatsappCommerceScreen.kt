package com.example.vascs.ui.screens

import android.content.Context
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.vascs.data.model.BroadcastCampaignEntity
import com.example.vascs.data.model.CustomerLeadEntity
import com.example.vascs.data.model.FollowupEntity
import com.example.vascs.data.model.ProductEntity
import com.example.vascs.data.model.QuotationEntity
import com.example.vascs.data.model.WhatsappTemplateEntity
import com.example.vascs.ui.theme.GoldAccent
import com.example.vascs.ui.viewmodel.SalesDashboardMetrics
import com.example.vascs.ui.viewmodel.WhatsappCommerceViewModel
import java.util.Locale

private val WhatsappGreen = Color(0xFF25D366)
private val WhatsappDarkGreen = Color(0xFF128C7E)
private val DeepTeal = Color(0xFF075E54)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WhatsappCommerceScreen(
    viewModel: WhatsappCommerceViewModel,
    onBack: () -> Unit
) {
    val context = LocalContext.current
    var selectedTab by remember { mutableIntStateOf(0) }
    val tabs = listOf(
        "Sales Pipeline",
        "Lead Capture",
        "Catalogue Factory",
        "Auto Quotation",
        "Templates",
        "Customer CRM",
        "Bulk Broadcast",
        "Follow-ups"
    )

    val leads by viewModel.leads.collectAsState()
    val quotations by viewModel.quotations.collectAsState()
    val followups by viewModel.followups.collectAsState()
    val templates by viewModel.templates.collectAsState()
    val campaigns by viewModel.campaigns.collectAsState()
    val products by viewModel.products.collectAsState()
    val dashboardMetrics by viewModel.salesDashboard.collectAsState()

    var showAddLeadDialog by remember { mutableStateOf(false) }
    var showAddQuotationDialog by remember { mutableStateOf(false) }
    var showScheduleFollowupDialog by remember { mutableStateOf(false) }
    var showBroadcastDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = "WhatsApp Commerce Engine",
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )
                        Text(
                            text = "Auto Commerce • Lead Capture • Quotations • Broadcast",
                            fontSize = 11.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    Surface(
                        shape = CircleShape,
                        color = WhatsappGreen.copy(alpha = 0.15f),
                        modifier = Modifier.padding(end = 12.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(8.dp)
                                    .clip(CircleShape)
                                    .background(WhatsappGreen)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "WhatsApp Active",
                                color = WhatsappDarkGreen,
                                fontWeight = FontWeight.Bold,
                                fontSize = 11.sp
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        },
        floatingActionButton = {
            when (selectedTab) {
                1 -> {
                    ExtendedFloatingActionButton(
                        onClick = { showAddLeadDialog = true },
                        icon = { Icon(Icons.Default.PersonAdd, contentDescription = null) },
                        text = { Text("New Lead") },
                        containerColor = WhatsappGreen,
                        contentColor = Color.White
                    )
                }
                3 -> {
                    ExtendedFloatingActionButton(
                        onClick = { showAddQuotationDialog = true },
                        icon = { Icon(Icons.Default.RequestQuote, contentDescription = null) },
                        text = { Text("Create Quotation") },
                        containerColor = WhatsappGreen,
                        contentColor = Color.White
                    )
                }
                6 -> {
                    ExtendedFloatingActionButton(
                        onClick = { showBroadcastDialog = true },
                        icon = { Icon(Icons.Default.Campaign, contentDescription = null) },
                        text = { Text("New Broadcast") },
                        containerColor = WhatsappGreen,
                        contentColor = Color.White
                    )
                }
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            // Scrollable Tab Row
            ScrollableTabRow(
                selectedTabIndex = selectedTab,
                edgePadding = 12.dp,
                containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f),
                contentColor = MaterialTheme.colorScheme.primary
            ) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        text = {
                            Text(
                                text = title,
                                fontWeight = if (selectedTab == index) FontWeight.Bold else FontWeight.Normal,
                                fontSize = 13.sp
                            )
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            when (selectedTab) {
                0 -> SalesPipelineModule(metrics = dashboardMetrics, leads = leads, onLeadClick = { selectedTab = 1 })
                1 -> LeadCaptureModule(leads = leads, viewModel = viewModel, onScheduleFollowup = { showScheduleFollowupDialog = true })
                2 -> CatalogueFactoryModule(products = products, viewModel = viewModel, context = context)
                3 -> AutoQuotationModule(quotations = quotations, viewModel = viewModel, context = context, onCreateClick = { showAddQuotationDialog = true })
                4 -> TemplatesModule(templates = templates, viewModel = viewModel, context = context)
                5 -> CustomerCrmModule(leads = leads, quotations = quotations, followups = followups, viewModel = viewModel)
                6 -> BulkBroadcastModule(campaigns = campaigns, templates = templates, onCreateClick = { showBroadcastDialog = true })
                7 -> FollowupEngineModule(followups = followups, viewModel = viewModel, context = context)
            }
        }
    }

    if (showAddLeadDialog) {
        AddLeadDialog(
            onDismiss = { showAddLeadDialog = false },
            onSave = { name, mobile, wa, city, state, source, prod, remarks ->
                viewModel.saveLead(name, mobile, wa, city, state, source, prod, remarks) {
                    showAddLeadDialog = false
                }
            }
        )
    }

    if (showAddQuotationDialog) {
        CreateQuotationDialog(
            leads = leads,
            products = products,
            onDismiss = { showAddQuotationDialog = false },
            onSave = { leadId, name, mobile, pJson, qty, amt, gst, valDays ->
                viewModel.generateQuotation(leadId, name, mobile, pJson, qty, amt, gst, valDays) {
                    showAddQuotationDialog = false
                }
            }
        )
    }

    if (showScheduleFollowupDialog) {
        ScheduleFollowupDialog(
            leads = leads,
            onDismiss = { showScheduleFollowupDialog = false },
            onSave = { leadId, name, mobile, remType, notes ->
                viewModel.scheduleFollowup(leadId, name, mobile, remType, notes) {
                    showScheduleFollowupDialog = false
                }
            }
        )
    }

    if (showBroadcastDialog) {
        CreateBroadcastDialog(
            templates = templates,
            onDismiss = { showBroadcastDialog = false },
            onSave = { name, seg, count, tUsed ->
                viewModel.sendBroadcast(name, seg, count, tUsed) {
                    showBroadcastDialog = false
                }
            }
        )
    }
}

// ==========================================
// MODULE 8: SALES PIPELINE DASHBOARD
// ==========================================
@Composable
fun SalesPipelineModule(
    metrics: SalesDashboardMetrics,
    leads: List<CustomerLeadEntity>,
    onLeadClick: () -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = DeepTeal),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "WhatsApp Sales Pipeline Overview",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                        Icon(Icons.Default.TrendingUp, contentDescription = null, tint = GoldAccent)
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text("Conversion Rate", color = Color.White.copy(alpha = 0.7f), fontSize = 11.sp)
                            Text(
                                text = String.format(Locale.getDefault(), "%.1f%%", metrics.conversionRate),
                                color = GoldAccent,
                                fontWeight = FontWeight.ExtraBold,
                                fontSize = 24.sp
                            )
                        }
                        Column(horizontalAlignment = Alignment.End) {
                            Text("Total Pipeline Value", color = Color.White.copy(alpha = 0.7f), fontSize = 11.sp)
                            Text(
                                text = "₹" + String.format(Locale.getDefault(), "%.0f", metrics.totalQuotationValue),
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp
                            )
                        }
                    }
                }
            }
        }

        item {
            Text("Pipeline Stage Breakdown", fontWeight = FontWeight.Bold, fontSize = 14.sp)
        }

        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                MetricCard(
                    title = "New Leads",
                    count = metrics.newLeadsCount,
                    badgeColor = Color(0xFF2196F3),
                    modifier = Modifier.weight(1f)
                )
                MetricCard(
                    title = "Active Leads",
                    count = metrics.activeLeadsCount,
                    badgeColor = Color(0xFFFF9800),
                    modifier = Modifier.weight(1f)
                )
                MetricCard(
                    title = "Quotations",
                    count = metrics.quotationsCount,
                    badgeColor = Color(0xFF9C27B0),
                    modifier = Modifier.weight(1f)
                )
            }
        }

        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                MetricCard(
                    title = "Confirmed Orders",
                    count = metrics.confirmedOrdersCount,
                    badgeColor = WhatsappGreen,
                    modifier = Modifier.weight(1f)
                )
                MetricCard(
                    title = "Lost Leads",
                    count = metrics.lostLeadsCount,
                    badgeColor = Color(0xFFE53935),
                    modifier = Modifier.weight(1f)
                )
                MetricCard(
                    title = "Total Captured",
                    count = metrics.totalLeadsCount,
                    badgeColor = Color(0xFF607D8B),
                    modifier = Modifier.weight(1f)
                )
            }
        }

        item {
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Recent Captured Leads", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                TextButton(onClick = onLeadClick) {
                    Text("View All Leads")
                }
            }
        }

        items(leads.take(5)) { lead ->
            LeadCardItem(lead = lead, onStatusChange = {}, onAction = {})
        }
    }
}

@Composable
fun MetricCard(
    title: String,
    count: Int,
    badgeColor: Color,
    modifier: Modifier = Modifier
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)),
        shape = RoundedCornerShape(12.dp),
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Box(
                modifier = Modifier
                    .size(10.dp)
                    .clip(CircleShape)
                    .background(badgeColor)
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(text = count.toString(), fontWeight = FontWeight.Bold, fontSize = 20.sp)
            Text(text = title, fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}

// ==========================================
// MODULE 2: LEAD CAPTURE CENTER
// ==========================================
@Composable
fun LeadCaptureModule(
    leads: List<CustomerLeadEntity>,
    viewModel: WhatsappCommerceViewModel,
    onScheduleFollowup: () -> Unit
) {
    var filterStatus by remember { mutableStateOf("ALL") }
    val statuses = listOf("ALL", "NEW", "CONTACTED", "FOLLOWUP", "QUOTATION_SENT", "ORDER_CONFIRMED", "LOST")
    val context = LocalContext.current

    val filteredLeads = remember(leads, filterStatus) {
        if (filterStatus == "ALL") leads else leads.filter { it.status == filterStatus }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            modifier = Modifier.padding(bottom = 8.dp)
        ) {
            items(statuses) { status ->
                FilterChip(
                    selected = filterStatus == status,
                    onClick = { filterStatus = status },
                    label = { Text(status, fontSize = 11.sp) }
                )
            }
        }

        if (filteredLeads.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No leads found for status $filterStatus.\nTap '+' to capture a new lead.",
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(filteredLeads, key = { it.leadId }) { lead ->
                    LeadCardItem(
                        lead = lead,
                        onStatusChange = { newStatus ->
                            viewModel.updateLeadStatus(lead.leadId, newStatus)
                        },
                        onAction = {
                            viewModel.shareWhatsAppMessage(
                                context,
                                lead.mobile,
                                "Namaste ${lead.customerName}, thank you for inquiring with Veeransh AI Studio regarding ${lead.interestedProduct}. How can we assist you today?"
                            )
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun LeadCardItem(
    lead: CustomerLeadEntity,
    onStatusChange: (String) -> Unit,
    onAction: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Surface(
                        shape = CircleShape,
                        color = WhatsappGreen.copy(alpha = 0.2f),
                        modifier = Modifier.size(36.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text(
                                text = lead.customerName.take(1).uppercase(),
                                fontWeight = FontWeight.Bold,
                                color = WhatsappDarkGreen
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(lead.customerName, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                        Text("${lead.mobile} • ${lead.city}, ${lead.state}", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }

                StatusBadge(status = lead.status)
            }

            Spacer(modifier = Modifier.height(8.dp))

            if (lead.interestedProduct.isNotBlank()) {
                Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Interested in: ${lead.interestedProduct}",
                        fontSize = 11.sp,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Source: ${lead.source} • ${lead.createdDate}",
                    fontSize = 10.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    IconButton(
                        onClick = onAction,
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(Icons.AutoMirrored.Filled.Send, contentDescription = "WhatsApp", tint = WhatsappGreen)
                    }

                    Box {
                        IconButton(
                            onClick = { expanded = true },
                            modifier = Modifier.size(32.dp)
                        ) {
                            Icon(Icons.Default.MoreVert, contentDescription = "Status")
                        }

                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            listOf("NEW", "CONTACTED", "FOLLOWUP", "QUOTATION_SENT", "ORDER_CONFIRMED", "LOST").forEach { st ->
                                DropdownMenuItem(
                                    text = { Text(st, fontSize = 12.sp) },
                                    onClick = {
                                        onStatusChange(st)
                                        expanded = false
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun StatusBadge(status: String) {
    val (bg, fg) = when (status) {
        "NEW" -> Color(0xFFE3F2FD) to Color(0xFF1976D2)
        "CONTACTED" -> Color(0xFFFFF3E0) to Color(0xFFF57C00)
        "FOLLOWUP" -> Color(0xFFF3E5F5) to Color(0xFF7B1FA2)
        "QUOTATION_SENT" -> Color(0xFFE8EAF6) to Color(0xFF3F51B5)
        "ORDER_CONFIRMED" -> Color(0xE8E8F5E9) to WhatsappDarkGreen
        "LOST" -> Color(0xFFFFEBEE) to Color(0xFFD32F2F)
        else -> Color.LightGray to Color.DarkGray
    }

    Surface(
        shape = RoundedCornerShape(12.dp),
        color = bg
    ) {
        Text(
            text = status,
            color = fg,
            fontWeight = FontWeight.Bold,
            fontSize = 10.sp,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
        )
    }
}

// ==========================================
// MODULE 1: WHATSAPP CATALOGUE FACTORY
// ==========================================
@Composable
fun CatalogueFactoryModule(
    products: List<ProductEntity>,
    viewModel: WhatsappCommerceViewModel,
    context: Context
) {
    var selectedType by remember { mutableStateOf("Single Product Card") }
    val cardTypes = listOf(
        "Single Product Card",
        "Multi Product Card",
        "Festival Catalogue",
        "Dealer Catalogue",
        "Premium Catalogue"
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Text("Select Catalogue Format", fontWeight = FontWeight.Bold, fontSize = 14.sp)
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(top = 6.dp)
            ) {
                items(cardTypes) { type ->
                    FilterChip(
                        selected = selectedType == type,
                        onClick = { selectedType = type },
                        label = { Text(type, fontSize = 12.sp) }
                    )
                }
            }
        }

        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text("Catalogue Card Preview", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = when (selectedType) {
                            "Single Product Card" -> "🥻 *Veeransh Designer Saree*\nCode: V-108 | Silk Organza\nWholesale Price: ₹1,850/pc\nMoq: 5 Pcs\nTap to order via WhatsApp!"
                            "Multi Product Card" -> "📦 *Festive Combo Saree Pack (6 Pcs)*\nIncludes Kanjivaram, Organza & Chanderi Silk\nSpecial Wholesale Rate: ₹9,999/-"
                            "Festival Catalogue" -> "🎉 *Diwali Festive Catalogue 2026*\nExclusive 50 Designs Rendered in AI Studio\nView & Order: https://veeransh.ai/festive2026"
                            "Dealer Catalogue" -> "💼 *Registered Dealer Wholesale Catalog*\nTier-1 Discount Active (15% Extra Off)\nMinimum Order Quantity: 10 sets"
                            else -> "✨ *Royal Premium Silk Collection*\nPure Soft Silk Sarees with Zari Weave"
                        },
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Button(
                        onClick = {
                            viewModel.shareWhatsAppMessage(
                                context,
                                "",
                                "🌟 *Veeransh AI Studio Catalogue*\nFormat: $selectedType\nCheck our exclusive saree catalogue rendering!"
                            )
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = WhatsappGreen),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(Icons.AutoMirrored.Filled.Send, contentDescription = null, tint = Color.White)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Share $selectedType on WhatsApp", color = Color.White, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        item {
            Text("Product Inventory Quick Share", fontWeight = FontWeight.Bold, fontSize = 14.sp)
        }

        items(products) { product ->
            Card(
                shape = RoundedCornerShape(10.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .padding(12.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(product.name, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                        Text("SKU: ${product.sku} • Category: ${product.category}", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Text("₹${product.wholesalePrice}", fontWeight = FontWeight.Bold, color = WhatsappDarkGreen, fontSize = 13.sp)
                    }

                    IconButton(
                        onClick = {
                            viewModel.shareWhatsAppMessage(
                                context,
                                "",
                                "🥻 *Product Offer*: ${product.name}\nSKU: ${product.sku}\nWholesale Price: ₹${product.wholesalePrice}\nFabrics: ${product.fabric}\nReply YES to order now!"
                            )
                        }
                    ) {
                        Icon(Icons.AutoMirrored.Filled.Send, contentDescription = "Share", tint = WhatsappGreen)
                    }
                }
            }
        }
    }
}

// ==========================================
// MODULE 3: AUTO QUOTATION FACTORY
// ==========================================
@Composable
fun AutoQuotationModule(
    quotations: List<QuotationEntity>,
    viewModel: WhatsappCommerceViewModel,
    context: Context,
    onCreateClick: () -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.4f)),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text("Auto Quotation Factory", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                        Text("Instant PDF Quotation Generation with GST calculation", fontSize = 11.sp)
                    }
                    Button(
                        onClick = onCreateClick,
                        colors = ButtonDefaults.buttonColors(containerColor = WhatsappGreen)
                    ) {
                        Text("Create")
                    }
                }
            }
        }

        if (quotations.isEmpty()) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(32.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No quotations created yet.\nTap 'Create' to issue a new client quotation.",
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        } else {
            items(quotations, key = { it.quotationId }) { quotation ->
                Card(
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(quotation.quotationNo, fontWeight = FontWeight.Bold, fontSize = 14.sp, color = MaterialTheme.colorScheme.primary)
                                Text("Client: ${quotation.customerName} (${quotation.mobile})", fontSize = 12.sp)
                            }
                            Surface(
                                shape = RoundedCornerShape(6.dp),
                                color = MaterialTheme.colorScheme.surfaceVariant
                            ) {
                                Text("Valid: ${quotation.validityDate}", fontSize = 10.sp, modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp))
                            }
                        }

                        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Total Qty: ${quotation.totalQty} Sarees", fontSize = 12.sp)
                            Text("Base: ₹" + String.format(Locale.getDefault(), "%.2f", quotation.totalAmount), fontSize = 12.sp)
                            Text("GST: ₹" + String.format(Locale.getDefault(), "%.2f", quotation.gstAmount), fontSize = 12.sp)
                        }

                        Spacer(modifier = Modifier.height(4.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Net Amount: ₹" + String.format(Locale.getDefault(), "%.2f", quotation.netAmount),
                                fontWeight = FontWeight.ExtraBold,
                                fontSize = 15.sp,
                                color = WhatsappDarkGreen
                            )

                            IconButton(
                                onClick = {
                                    val msg = "📄 *OFFICIAL QUOTATION ${quotation.quotationNo}*\nClient: ${quotation.customerName}\nTotal Qty: ${quotation.totalQty} Sarees\nNet Total: ₹${quotation.netAmount} (incl. GST)\nValidity Date: ${quotation.validityDate}\n\nReply to confirm order!"
                                    viewModel.shareWhatsAppMessage(context, quotation.mobile, msg)
                                }
                            ) {
                                Icon(Icons.AutoMirrored.Filled.Send, contentDescription = "Send", tint = WhatsappGreen)
                            }
                        }
                    }
                }
            }
        }
    }
}

// ==========================================
// MODULE 4: WHATSAPP TEMPLATE ENGINE
// ==========================================
@Composable
fun TemplatesModule(
    templates: List<WhatsappTemplateEntity>,
    viewModel: WhatsappCommerceViewModel,
    context: Context
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        item {
            Text("Official WhatsApp Template Engine", fontWeight = FontWeight.Bold, fontSize = 14.sp)
        }

        items(templates) { template ->
            Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(template.title, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = WhatsappGreen.copy(alpha = 0.15f)
                        ) {
                            Text(
                                template.templateType,
                                fontSize = 10.sp,
                                color = WhatsappDarkGreen,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = template.content,
                            fontSize = 11.sp,
                            modifier = Modifier.padding(10.dp),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        Button(
                            onClick = {
                                viewModel.shareWhatsAppMessage(context, "", template.content)
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = WhatsappGreen)
                        ) {
                            Icon(Icons.AutoMirrored.Filled.Send, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Use Template", fontSize = 12.sp)
                        }
                    }
                }
            }
        }
    }
}

// ==========================================
// MODULE 5: CUSTOMER CRM CENTER
// ==========================================
@Composable
fun CustomerCrmModule(
    leads: List<CustomerLeadEntity>,
    quotations: List<QuotationEntity>,
    followups: List<FollowupEntity>,
    viewModel: WhatsappCommerceViewModel
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        item {
            Text("Customer CRM Activity Center", fontWeight = FontWeight.Bold, fontSize = 14.sp)
        }

        items(leads) { lead ->
            val leadQuotes = quotations.filter { it.leadId == lead.leadId }
            val leadFollowups = followups.filter { it.leadId == lead.leadId }

            Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(lead.customerName, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                            Text("${lead.mobile} • ${lead.city}", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                        StatusBadge(status = lead.status)
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        CrmMetricItem("Quotations", leadQuotes.size.toString(), Icons.Default.Receipt)
                        CrmMetricItem("Follow-ups", leadFollowups.size.toString(), Icons.Default.Schedule)
                        CrmMetricItem("WhatsApp", "Active", Icons.Default.Chat)
                    }
                }
            }
        }
    }
}

@Composable
fun CrmMetricItem(label: String, value: String, icon: androidx.compose.ui.graphics.vector.ImageVector) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(icon, contentDescription = null, modifier = Modifier.size(16.dp), tint = MaterialTheme.colorScheme.primary)
        Spacer(modifier = Modifier.width(4.dp))
        Text("$label: ", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Text(value, fontSize = 11.sp, fontWeight = FontWeight.Bold)
    }
}

// ==========================================
// MODULE 6: BULK BROADCAST CENTER
// ==========================================
@Composable
fun BulkBroadcastModule(
    campaigns: List<BroadcastCampaignEntity>,
    templates: List<WhatsappTemplateEntity>,
    onCreateClick: () -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = DeepTeal),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Bulk Broadcast Hub", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                        Button(
                            onClick = onCreateClick,
                            colors = ButtonDefaults.buttonColors(containerColor = WhatsappGreen)
                        ) {
                            Text("New Campaign")
                        }
                    }
                    Text(
                        "Broadcast up to 10,000 WhatsApp contacts in a single click.",
                        color = Color.White.copy(alpha = 0.8f),
                        fontSize = 11.sp
                    )
                }
            }
        }

        if (campaigns.isEmpty()) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(32.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No broadcast campaigns sent yet.\nTap 'New Campaign' to start a broadcast.",
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        } else {
            items(campaigns, key = { it.campaignId }) { campaign ->
                Card(
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(campaign.campaignName, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                            Surface(
                                shape = RoundedCornerShape(6.dp),
                                color = WhatsappGreen.copy(alpha = 0.15f)
                            ) {
                                Text(campaign.status, fontSize = 10.sp, color = WhatsappDarkGreen, fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp))
                            }
                        }

                        Spacer(modifier = Modifier.height(6.dp))

                        Text("Target: ${campaign.targetSegment} (${campaign.targetCount} Contacts)", fontSize = 12.sp)
                        Text("Template: ${campaign.templateUsed}", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)

                        Spacer(modifier = Modifier.height(8.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Sent: ${campaign.sentCount}", fontSize = 11.sp, color = MaterialTheme.colorScheme.primary)
                            Text("Delivered: ${campaign.deliveredCount} (96%)", fontSize = 11.sp, color = WhatsappDarkGreen)
                            Text("Date: ${campaign.createdDate}", fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                    }
                }
            }
        }
    }
}

// ==========================================
// MODULE 7: FOLLOW-UP ENGINE
// ==========================================
@Composable
fun FollowupEngineModule(
    followups: List<FollowupEntity>,
    viewModel: WhatsappCommerceViewModel,
    context: Context
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        item {
            Text("Automated Follow-up Reminders", fontWeight = FontWeight.Bold, fontSize = 14.sp)
        }

        if (followups.isEmpty()) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(32.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No active follow-ups scheduled.\nSchedule follow-ups directly from Lead Capture.",
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        } else {
            items(followups, key = { it.followupId }) { followup ->
                Card(
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(followup.customerName, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                                Text("Mobile: ${followup.mobile}", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            }
                            Surface(
                                shape = RoundedCornerShape(6.dp),
                                color = if (followup.status == "COMPLETED") WhatsappGreen.copy(alpha = 0.2f) else Color(0xFFFFE0B2)
                            ) {
                                Text(
                                    followup.status,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (followup.status == "COMPLETED") WhatsappDarkGreen else Color(0xFFE65100),
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(6.dp))

                        Text("Reminder Interval: ${followup.reminderType} • Due Date: ${followup.dueDate}", fontSize = 12.sp, fontWeight = FontWeight.Medium)
                        if (followup.notes.isNotBlank()) {
                            Text("Notes: ${followup.notes}", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            if (followup.status == "PENDING") {
                                OutlinedButton(
                                    onClick = { viewModel.updateFollowupStatus(followup.followupId, "COMPLETED") },
                                    modifier = Modifier.height(36.dp)
                                ) {
                                    Text("Mark Complete", fontSize = 11.sp)
                                }
                                Spacer(modifier = Modifier.width(8.dp))
                            }

                            Button(
                                onClick = {
                                    val msg = "Namaste ${followup.customerName}, following up regarding your saree inquiry with Veeransh AI Studio. Let us know if you need any assistance!"
                                    viewModel.shareWhatsAppMessage(context, followup.mobile, msg)
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = WhatsappGreen),
                                modifier = Modifier.height(36.dp)
                            ) {
                                Icon(Icons.AutoMirrored.Filled.Send, contentDescription = null, modifier = Modifier.size(14.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Ping WhatsApp", fontSize = 11.sp)
                            }
                        }
                    }
                }
            }
        }
    }
}

// ==========================================
// MODAL DIALOGS
// ==========================================
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddLeadDialog(
    onDismiss: () -> Unit,
    onSave: (String, String, String, String, String, String, String, String) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var mobile by remember { mutableStateOf("") }
    var city by remember { mutableStateOf("") }
    var state by remember { mutableStateOf("") }
    var product by remember { mutableStateOf("") }
    var remarks by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Capture Customer Lead", fontWeight = FontWeight.Bold) },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Customer Name") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = mobile,
                    onValueChange = { mobile = it },
                    label = { Text("Mobile / WhatsApp Number") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                    modifier = Modifier.fillMaxWidth()
                )
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = city,
                        onValueChange = { city = it },
                        label = { Text("City") },
                        modifier = Modifier.weight(1f)
                    )
                    OutlinedTextField(
                        value = state,
                        onValueChange = { state = it },
                        label = { Text("State") },
                        modifier = Modifier.weight(1f)
                    )
                }
                OutlinedTextField(
                    value = product,
                    onValueChange = { product = it },
                    label = { Text("Interested Product / Saree Type") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = remarks,
                    onValueChange = { remarks = it },
                    label = { Text("Remarks / Inquiry Notes") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = { onSave(name, mobile, mobile, city, state, "WhatsApp Studio", product, remarks) },
                enabled = name.isNotBlank() && mobile.isNotBlank(),
                colors = ButtonDefaults.buttonColors(containerColor = WhatsappGreen)
            ) {
                Text("Save Lead")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancel") }
        }
    )
}

@Composable
fun CreateQuotationDialog(
    leads: List<CustomerLeadEntity>,
    products: List<ProductEntity>,
    onDismiss: () -> Unit,
    onSave: (Long, String, String, String, Int, Double, Double, Int) -> Unit
) {
    var selectedLeadName by remember { mutableStateOf(leads.firstOrNull()?.customerName ?: "") }
    var selectedLeadMobile by remember { mutableStateOf(leads.firstOrNull()?.mobile ?: "") }
    var selectedLeadId by remember { mutableLongStateOf(leads.firstOrNull()?.leadId ?: 0L) }
    var qtyStr by remember { mutableStateOf("10") }
    var amountStr by remember { mutableStateOf("18500") }
    var gstRateStr by remember { mutableStateOf("5.0") }
    var validityDaysStr by remember { mutableStateOf("7") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Generate Auto Quotation", fontWeight = FontWeight.Bold) },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = selectedLeadName,
                    onValueChange = { selectedLeadName = it },
                    label = { Text("Customer Name") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = selectedLeadMobile,
                    onValueChange = { selectedLeadMobile = it },
                    label = { Text("Mobile Number") },
                    modifier = Modifier.fillMaxWidth()
                )
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = qtyStr,
                        onValueChange = { qtyStr = it },
                        label = { Text("Total Qty") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.weight(1f)
                    )
                    OutlinedTextField(
                        value = amountStr,
                        onValueChange = { amountStr = it },
                        label = { Text("Base Amount (₹)") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.weight(1f)
                    )
                }
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = gstRateStr,
                        onValueChange = { gstRateStr = it },
                        label = { Text("GST Rate (%)") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.weight(1f)
                    )
                    OutlinedTextField(
                        value = validityDaysStr,
                        onValueChange = { validityDaysStr = it },
                        label = { Text("Validity Days") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val qty = qtyStr.toIntOrNull() ?: 10
                    val amt = amountStr.toDoubleOrNull() ?: 18500.0
                    val gst = gstRateStr.toDoubleOrNull() ?: 5.0
                    val valDays = validityDaysStr.toIntOrNull() ?: 7
                    onSave(selectedLeadId, selectedLeadName, selectedLeadMobile, "[]", qty, amt, gst, valDays)
                },
                colors = ButtonDefaults.buttonColors(containerColor = WhatsappGreen)
            ) {
                Text("Issue Quotation")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancel") }
        }
    )
}

@Composable
fun ScheduleFollowupDialog(
    leads: List<CustomerLeadEntity>,
    onDismiss: () -> Unit,
    onSave: (Long, String, String, String, String) -> Unit
) {
    var leadId by remember { mutableLongStateOf(leads.firstOrNull()?.leadId ?: 0L) }
    var name by remember { mutableStateOf(leads.firstOrNull()?.customerName ?: "") }
    var mobile by remember { mutableStateOf(leads.firstOrNull()?.mobile ?: "") }
    var reminderType by remember { mutableStateOf("3 Days") }
    var notes by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Schedule Auto Follow-up", fontWeight = FontWeight.Bold) },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Customer Name") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = mobile,
                    onValueChange = { mobile = it },
                    label = { Text("Mobile") },
                    modifier = Modifier.fillMaxWidth()
                )
                Text("Reminder Interval", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    listOf("1 Day", "3 Days", "7 Days", "15 Days").forEach { interval ->
                        FilterChip(
                            selected = reminderType == interval,
                            onClick = { reminderType = interval },
                            label = { Text(interval, fontSize = 11.sp) }
                        )
                    }
                }
                OutlinedTextField(
                    value = notes,
                    onValueChange = { notes = it },
                    label = { Text("Follow-up Notes") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = { onSave(leadId, name, mobile, reminderType, notes) },
                colors = ButtonDefaults.buttonColors(containerColor = WhatsappGreen)
            ) {
                Text("Schedule")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancel") }
        }
    )
}

@Composable
fun CreateBroadcastDialog(
    templates: List<WhatsappTemplateEntity>,
    onDismiss: () -> Unit,
    onSave: (String, String, Int, String) -> Unit
) {
    var campaignName by remember { mutableStateOf("Festive Saree Launch 2026") }
    var targetSegment by remember { mutableStateOf("All Dealers") }
    var targetCountStr by remember { mutableStateOf("1000") }
    var templateUsed by remember { mutableStateOf(templates.firstOrNull()?.title ?: "New Arrival Collection") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Launch Bulk WhatsApp Broadcast", fontWeight = FontWeight.Bold) },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = campaignName,
                    onValueChange = { campaignName = it },
                    label = { Text("Campaign Name") },
                    modifier = Modifier.fillMaxWidth()
                )
                Text("Target Segment", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    listOf("All Dealers", "New Leads", "Retail Customers").forEach { seg ->
                        FilterChip(
                            selected = targetSegment == seg,
                            onClick = { targetSegment = seg },
                            label = { Text(seg, fontSize = 11.sp) }
                        )
                    }
                }
                Text("Target Audience Count", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    listOf("100", "1000", "10000").forEach { cnt ->
                        FilterChip(
                            selected = targetCountStr == cnt,
                            onClick = { targetCountStr = cnt },
                            label = { Text("$cnt Contacts", fontSize = 11.sp) }
                        )
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val count = targetCountStr.toIntOrNull() ?: 1000
                    onSave(campaignName, targetSegment, count, templateUsed)
                },
                colors = ButtonDefaults.buttonColors(containerColor = WhatsappGreen)
            ) {
                Text("Broadcast Now")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancel") }
        }
    )
}
