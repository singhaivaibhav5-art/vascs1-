package com.example.vascs.ui.screens

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.AssignmentInd
import androidx.compose.material.icons.filled.Business
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Campaign
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.CopyAll
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.InsertDriveFile
import androidx.compose.material.icons.filled.LocalOffer
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material.icons.filled.PictureAsPdf
import androidx.compose.material.icons.filled.QrCode
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Store
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.vascs.data.model.DealerCatalogueEntity
import com.example.vascs.data.model.DealerEntity
import com.example.vascs.data.model.DealerOrderEntity
import com.example.vascs.data.model.ProductEntity
import com.example.vascs.data.model.WhatsAppCampaignEntity
import com.example.vascs.ui.viewmodel.DealerNetworkViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SocialDealerNetworkScreen(
    viewModel: DealerNetworkViewModel,
    onNavigateToProduct: (Long) -> Unit = {}
) {
    val context = LocalContext.current
    val clipboardManager = LocalClipboardManager.current

    val dealers by viewModel.dealers.collectAsState()
    val campaigns by viewModel.campaigns.collectAsState()
    val orders by viewModel.orders.collectAsState()
    val catalogues by viewModel.dealerCatalogues.collectAsState()
    val analytics by viewModel.analyticsEvents.collectAsState()
    val products by viewModel.products.collectAsState()

    var selectedTabIndex by remember { mutableStateOf(0) }
    var showAddDealerDialog by remember { mutableStateOf(false) }
    var showCreateCampaignDialog by remember { mutableStateOf(false) }
    var showCreatePdfDialog by remember { mutableStateOf(false) }
    var showNewOrderDialog by remember { mutableStateOf(false) }

    val tabs = listOf(
        "Dealers Directory",
        "Product Assignment",
        "WhatsApp Factory",
        "Social Media Factories",
        "Dealer PDF Factory",
        "Dealer Order Portal",
        "Social Analytics"
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = "SOCIAL COMMERCE & DEALER NETWORK",
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )
                        Text(
                            text = "Distribution Factory • WhatsApp Automation • Social Studios • Dealer Portal",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surfaceContainer
                ),
                actions = {
                    IconButton(onClick = { showAddDealerDialog = true }) {
                        Icon(Icons.Default.PersonAdd, contentDescription = "Add Dealer")
                    }
                }
            )
        },
        floatingActionButton = {
            if (selectedTabIndex == 0) {
                FloatingActionButton(
                    onClick = { showAddDealerDialog = true },
                    containerColor = MaterialTheme.colorScheme.primary
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Add Dealer")
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Dashboard Summary Banner
            DealerNetworkSummaryBanner(
                totalDealers = dealers.size,
                activeOrders = orders.filter { it.status != "Cancelled" }.size,
                campaignsCount = campaigns.size,
                totalShares = analytics.size
            )

            // Scrollable Tab Row
            ScrollableTabRow(
                selectedTabIndex = selectedTabIndex,
                edgePadding = 12.dp,
                containerColor = MaterialTheme.colorScheme.surfaceContainerLow
            ) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTabIndex == index,
                        onClick = { selectedTabIndex = index },
                        text = {
                            Text(
                                text = title,
                                fontWeight = if (selectedTabIndex == index) FontWeight.Bold else FontWeight.Normal
                            )
                        }
                    )
                }
            }

            // Tab Content
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
            ) {
                when (selectedTabIndex) {
                    0 -> DealersDirectoryTab(
                        dealers = dealers,
                        onUpdateStatus = { id, status -> viewModel.updateDealerStatus(id, status) },
                        onAddDealerClick = { showAddDealerDialog = true }
                    )
                    1 -> ProductAssignmentTab(
                        dealers = dealers,
                        products = products,
                        onAssign = { dealerIds, productId, price ->
                            viewModel.assignProductToDealers(dealerIds, productId, price)
                            Toast.makeText(context, "Product assigned to ${dealerIds.size} dealer(s)", Toast.LENGTH_SHORT).show()
                        }
                    )
                    2 -> WhatsAppFactoryTab(
                        dealers = dealers,
                        products = products,
                        campaigns = campaigns,
                        onCreateCampaignClick = { showCreateCampaignDialog = true },
                        onQuickShareMessage = { msg ->
                            clipboardManager.setText(AnnotatedString(msg))
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://wa.me/?text=${Uri.encode(msg)}"))
                            try {
                                context.startActivity(intent)
                            } catch (e: Exception) {
                                Toast.makeText(context, "WhatsApp message copied to clipboard", Toast.LENGTH_SHORT).show()
                            }
                        }
                    )
                    3 -> SocialMediaStudioTab(
                        products = products,
                        onShareEvent = { type, channel -> viewModel.logSocialEvent(type, channel = channel) }
                    )
                    4 -> DealerPdfFactoryTab(
                        catalogues = catalogues,
                        onCreatePdfClick = { showCreatePdfDialog = true },
                        onDownloadPdf = { cat ->
                            Toast.makeText(context, "Downloaded Dealer PDF: ${cat.title}", Toast.LENGTH_SHORT).show()
                        }
                    )
                    5 -> DealerOrderPortalTab(
                        dealers = dealers,
                        products = products,
                        orders = orders,
                        onNewOrderClick = { showNewOrderDialog = true },
                        onUpdateOrderStatus = { id, status -> viewModel.updateOrderStatus(id, status) }
                    )
                    6 -> SocialAnalyticsTab(
                        dealers = dealers,
                        products = products,
                        orders = orders,
                        catalogues = catalogues,
                        analytics = analytics
                    )
                }
            }
        }
    }

    // Modal Dialogs
    if (showAddDealerDialog) {
        AddDealerDialog(
            onDismiss = { showAddDealerDialog = false },
            onConfirm = { name, firm, mobile, wa, email, city, state, gst, credit, type ->
                viewModel.addDealer(name, firm, mobile, wa, email, city, state, gst, credit, type)
                showAddDealerDialog = false
                Toast.makeText(context, "New Dealer Created Successfully", Toast.LENGTH_SHORT).show()
            }
        )
    }

    if (showCreateCampaignDialog) {
        CreateCampaignDialog(
            dealers = dealers,
            products = products,
            onDismiss = { showCreateCampaignDialog = false },
            onConfirm = { title, type, targetType, targetCount, msg, selectedProductIds ->
                viewModel.createWhatsAppCampaign(title, type, targetType, targetCount, msg, selectedProductIds)
                showCreateCampaignDialog = false
                Toast.makeText(context, "WhatsApp Campaign Dispatched to $targetCount dealers", Toast.LENGTH_SHORT).show()
            }
        )
    }

    if (showCreatePdfDialog) {
        CreateDealerPdfDialog(
            dealers = dealers,
            products = products,
            onDismiss = { showCreatePdfDialog = false },
            onConfirm = { dealerId, title, type, selectedProductIds ->
                viewModel.generateDealerCatalogue(dealerId, title, type, selectedProductIds)
                showCreatePdfDialog = false
                Toast.makeText(context, "Dealer PDF Catalogue Generated", Toast.LENGTH_SHORT).show()
            }
        )
    }

    if (showNewOrderDialog) {
        CreateDealerOrderDialog(
            dealers = dealers,
            products = products,
            onDismiss = { showNewOrderDialog = false },
            onConfirm = { dealerId, dealerName, productId, productName, qty, rate, notes ->
                viewModel.createDealerOrder(dealerId, dealerName, productId, productName, qty, rate, notes)
                showNewOrderDialog = false
                Toast.makeText(context, "Dealer Order Placed Successfully", Toast.LENGTH_SHORT).show()
            }
        )
    }
}

@Composable
fun DealerNetworkSummaryBanner(
    totalDealers: Int,
    activeOrders: Int,
    campaignsCount: Int,
    totalShares: Int
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            SummaryBannerItem("Dealers", totalDealers.toString(), Icons.Default.Business)
            SummaryBannerItem("Orders", activeOrders.toString(), Icons.Default.ShoppingCart)
            SummaryBannerItem("Campaigns", campaignsCount.toString(), Icons.Default.Campaign)
            SummaryBannerItem("Shares", totalShares.toString(), Icons.Default.Share)
        }
    }
}

@Composable
fun SummaryBannerItem(title: String, value: String, icon: ImageVector) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(icon, contentDescription = title, modifier = Modifier.size(20.dp), tint = MaterialTheme.colorScheme.primary)
        Text(text = value, fontWeight = FontWeight.Bold, fontSize = 16.sp)
        Text(text = title, style = MaterialTheme.typography.labelSmall)
    }
}

// ==========================================
// TAB 0: DEALERS DIRECTORY
// ==========================================
@Composable
fun DealersDirectoryTab(
    dealers: List<DealerEntity>,
    onUpdateStatus: (Long, String) -> Unit,
    onAddDealerClick: () -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedFilterType by remember { mutableStateOf("ALL") }

    val filteredDealers = dealers.filter {
        val matchesSearch = it.dealerName.contains(searchQuery, ignoreCase = true) ||
                it.firmName.contains(searchQuery, ignoreCase = true) ||
                it.city.contains(searchQuery, ignoreCase = true) ||
                it.mobile.contains(searchQuery)
        val matchesFilter = if (selectedFilterType == "ALL") true else it.dealerType.equals(selectedFilterType, ignoreCase = true)
        matchesSearch && matchesFilter
    }

    Column(modifier = Modifier.fillMaxSize()) {
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Search by Dealer, Firm Name, City, Mobile...") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") },
            shape = RoundedCornerShape(12.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            val filterOptions = listOf("ALL", "Wholesaler", "Distributor", "Retailer", "Agent", "Online Seller")
            items(filterOptions) { type ->
                FilterChip(
                    selected = selectedFilterType == type,
                    onClick = { selectedFilterType = type },
                    label = { Text(type) }
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        if (filteredDealers.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(Icons.Default.Groups, contentDescription = null, modifier = Modifier.size(48.dp), tint = Color.Gray)
                    Text("No Dealers Found", fontWeight = FontWeight.SemiBold, color = Color.Gray)
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(onClick = onAddDealerClick) {
                        Text("Add First Dealer")
                    }
                }
            }
        } else {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(filteredDealers) { dealer ->
                    DealerCardItem(dealer = dealer, onUpdateStatus = onUpdateStatus)
                }
            }
        }
    }
}

@Composable
fun DealerCardItem(dealer: DealerEntity, onUpdateStatus: (Long, String) -> Unit) {
    val context = LocalContext.current
    ElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Store,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(text = dealer.firmName, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                        Text(text = "Prop: ${dealer.dealerName} (${dealer.dealerId})", style = MaterialTheme.typography.bodySmall)
                    }
                }
                Surface(
                    color = if (dealer.status == "ACTIVE") Color(0xFF4CAF50).copy(alpha = 0.2f) else Color.Red.copy(alpha = 0.2f),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text(
                        text = dealer.status,
                        color = if (dealer.status == "ACTIVE") Color(0xFF2E7D32) else Color.Red,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(text = "📍 ${dealer.city}, ${dealer.state}", style = MaterialTheme.typography.bodySmall)
                Text(text = "🏷️ Type: ${dealer.dealerType}", style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.SemiBold)
            }

            Spacer(modifier = Modifier.height(4.dp))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(text = "📞 ${dealer.mobile}", style = MaterialTheme.typography.bodySmall)
                Text(text = "💳 Credit Limit: ₹${dealer.creditLimit.toInt()}", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.primary)
            }

            if (dealer.gstNumber.isNotBlank()) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = "🏛️ GSTIN: ${dealer.gstNumber}", style = MaterialTheme.typography.bodySmall, color = Color.Gray)
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = {
                    val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:${dealer.mobile}"))
                    context.startActivity(intent)
                }) {
                    Icon(Icons.Default.Call, contentDescription = "Call", tint = MaterialTheme.colorScheme.primary)
                }
                IconButton(onClick = {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://wa.me/${dealer.whatsapp.replace("+", "").replace(" ", "")}"))
                    context.startActivity(intent)
                }) {
                    Icon(Icons.Default.Send, contentDescription = "WhatsApp", tint = Color(0xFF25D366))
                }
                TextButton(onClick = {
                    val newStatus = if (dealer.status == "ACTIVE") "BLOCKED" else "ACTIVE"
                    onUpdateStatus(dealer.id, newStatus)
                }) {
                    Text(if (dealer.status == "ACTIVE") "Block Dealer" else "Activate")
                }
            }
        }
    }
}

// ==========================================
// TAB 1: PRODUCT ASSIGNMENT
// ==========================================
@Composable
fun ProductAssignmentTab(
    dealers: List<DealerEntity>,
    products: List<ProductEntity>,
    onAssign: (List<String>, Long, Double) -> Unit
) {
    var selectedProductId by remember { mutableStateOf(products.firstOrNull()?.id ?: "") }
    var specialPriceText by remember { mutableStateOf("") }
    val selectedDealerIds = remember { mutableStateOf(setOf<String>()) }

    val selectedProduct = products.firstOrNull { it.id == selectedProductId }

    LazyColumn(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        item {
            Text("Assign Product to Single or Bulk Dealers", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Spacer(modifier = Modifier.height(8.dp))

            // Product Selection Selector
            Text("Select Product:", style = MaterialTheme.typography.labelMedium)
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(products) { prod ->
                    FilterChip(
                        selected = selectedProductId == prod.id,
                        onClick = { selectedProductId = prod.id },
                        label = { Text(prod.name) }
                    )
                }
            }
        }

        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text(text = "Target Product: ${selectedProduct?.name ?: "N/A"}", fontWeight = FontWeight.Bold)
                    Text(text = "MRP: ₹${selectedProduct?.mrp ?: 0.0} | Standard Wholesale: ₹${selectedProduct?.wholesalePrice ?: 0.0}")
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = specialPriceText,
                        onValueChange = { specialPriceText = it },
                        label = { Text("Special Dealer Price Override (Optional)") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }

        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Select Target Dealers (${selectedDealerIds.value.size} Selected):", fontWeight = FontWeight.Bold)
                Row {
                    TextButton(onClick = { selectedDealerIds.value = dealers.map { it.dealerId }.toSet() }) {
                        Text("Select All (${dealers.size})")
                    }
                    TextButton(onClick = { selectedDealerIds.value = emptySet() }) {
                        Text("Clear")
                    }
                }
            }
        }

        items(dealers) { dealer ->
            val isChecked = selectedDealerIds.value.contains(dealer.dealerId)
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        selectedDealerIds.value = if (isChecked) {
                            selectedDealerIds.value - dealer.dealerId
                        } else {
                            selectedDealerIds.value + dealer.dealerId
                        }
                    },
                colors = CardDefaults.cardColors(
                    containerColor = if (isChecked) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surface
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(dealer.firmName, fontWeight = FontWeight.Bold)
                        Text("${dealer.dealerName} • ${dealer.city} (${dealer.dealerType})", style = MaterialTheme.typography.bodySmall)
                    }
                    Checkbox(
                        checked = isChecked,
                        onCheckedChange = {
                            selectedDealerIds.value = if (it) {
                                selectedDealerIds.value + dealer.dealerId
                            } else {
                                selectedDealerIds.value - dealer.dealerId
                            }
                        }
                    )
                }
            }
        }

        item {
            Button(
                onClick = {
                    val sp = specialPriceText.toDoubleOrNull() ?: (selectedProduct?.wholesalePrice ?: 0.0)
                    val prodNumericId = selectedProductId.toLongOrNull() ?: selectedProductId.hashCode().toLong()
                    onAssign(selectedDealerIds.value.toList(), prodNumericId, sp)
                },
                enabled = selectedDealerIds.value.isNotEmpty() && selectedProductId.isNotBlank(),
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(Icons.Default.AssignmentInd, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Assign Product to ${selectedDealerIds.value.size} Dealers")
            }
        }
    }
}

// ==========================================
// TAB 2: WHATSAPP FACTORY
// ==========================================
@Composable
fun WhatsAppFactoryTab(
    dealers: List<DealerEntity>,
    products: List<ProductEntity>,
    campaigns: List<WhatsAppCampaignEntity>,
    onCreateCampaignClick: () -> Unit,
    onQuickShareMessage: (String) -> Unit
) {
    val sampleProduct = products.firstOrNull()
    val defaultMessage = """
        👑 *VASCS EXCLUSIVE FESTIVE SAREE OFFER*
        Product: ${sampleProduct?.name ?: "Banarasi Royal Silk Saree"}
        MRP: ₹${sampleProduct?.mrp ?: 3500.0}
        *Special Dealer Rate: ₹${sampleProduct?.wholesalePrice ?: 1850.0}*
        
        📲 Order Now on WhatsApp:
        https://wa.me/919825112345?text=Interested%20In%20${sampleProduct?.sku ?: "SKU-101"}
    """.trimIndent()

    LazyColumn(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF25D366).copy(alpha = 0.15f))
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Campaign, contentDescription = null, tint = Color(0xFF128C7E))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("WhatsApp Business Campaign Factory", fontWeight = FontWeight.Bold, color = Color(0xFF128C7E))
                    }
                    Text("Automate single product messages, festival offers, and bulk wholesale blasts for up to 5,000 dealers.", style = MaterialTheme.typography.bodySmall)
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(
                        onClick = onCreateCampaignClick,
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF128C7E))
                    ) {
                        Icon(Icons.Default.Add, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Create & Dispatch WhatsApp Campaign")
                    }
                }
            }
        }

        item {
            Text("Auto-Generated Marketing Message Preview:", fontWeight = FontWeight.Bold)
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text(defaultMessage, style = MaterialTheme.typography.bodyMedium)
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxWidth()) {
                        OutlinedButton(onClick = { onQuickShareMessage(defaultMessage) }) {
                            Icon(Icons.Default.Send, contentDescription = null, tint = Color(0xFF25D366))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Broadcast Quick Message")
                        }
                    }
                }
            }
        }

        item {
            Text("Campaign History & Logs:", fontWeight = FontWeight.Bold)
        }

        items(campaigns) { campaign ->
            ElevatedCard(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text(campaign.title, fontWeight = FontWeight.Bold)
                        Text(campaign.status, color = Color(0xFF2E7D32), fontWeight = FontWeight.Bold, style = MaterialTheme.typography.labelSmall)
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("Target: ${campaign.targetDealerType} (${campaign.targetDealerCount} dealers)", style = MaterialTheme.typography.bodySmall)
                    Text("Sent Messages: ${campaign.sentCount} / ${campaign.targetDealerCount}", style = MaterialTheme.typography.bodySmall)
                }
            }
        }
    }
}

// ==========================================
// TAB 3: SOCIAL MEDIA FACTORIES
// ==========================================
@Composable
fun SocialMediaStudioTab(
    products: List<ProductEntity>,
    onShareEvent: (String, String) -> Unit
) {
    val context = LocalContext.current
    val clipboardManager = LocalClipboardManager.current
    var selectedChannel by remember { mutableStateOf("TELEGRAM") }

    Column(modifier = Modifier.fillMaxSize()) {
        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            val channels = listOf("TELEGRAM", "INSTAGRAM", "FACEBOOK")
            items(channels) { channel ->
                FilterChip(
                    selected = selectedChannel == channel,
                    onClick = { selectedChannel = channel },
                    label = { Text(channel) }
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        val product = products.firstOrNull()
        val promoText = """
            ✨ ${product?.name ?: "Banarasi Royal Silk Saree"}
            🏷️ SKU: ${product?.sku ?: "SKU-101"} | Wholesale: ₹${product?.wholesalePrice ?: 1850.0}
            📲 Order directly on VASCS Distribution Network!
        """.trimIndent()

        Card(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(12.dp)) {
                Text(text = "$selectedChannel Studio Card Generator", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Spacer(modifier = Modifier.height(8.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                        .background(MaterialTheme.colorScheme.primaryContainer, shape = RoundedCornerShape(12.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(Icons.Default.Share, contentDescription = null, modifier = Modifier.size(40.dp))
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = "$selectedChannel Visual Banner", fontWeight = FontWeight.Bold)
                        Text(text = product?.name ?: "Saree Collection", style = MaterialTheme.typography.bodySmall)
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Button(onClick = {
                        clipboardManager.setText(AnnotatedString(promoText))
                        onShareEvent("${selectedChannel}_EXPORT", selectedChannel)
                        Toast.makeText(context, "$selectedChannel promo card exported to clipboard", Toast.LENGTH_SHORT).show()
                    }) {
                        Icon(Icons.Default.CopyAll, contentDescription = null)
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Export $selectedChannel Card")
                    }
                }
            }
        }
    }
}

// ==========================================
// TAB 4: DEALER PDF FACTORY
// ==========================================
@Composable
fun DealerPdfFactoryTab(
    catalogues: List<DealerCatalogueEntity>,
    onCreatePdfClick: () -> Unit,
    onDownloadPdf: (DealerCatalogueEntity) -> Unit
) {
    LazyColumn(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text("Dealer PDF Catalogue Factory", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    Text("Generate branded PDF catalogues for Retailers, Wholesalers, and Distributors with custom prices and contact details.", style = MaterialTheme.typography.bodySmall)
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(onClick = onCreatePdfClick) {
                        Icon(Icons.Default.PictureAsPdf, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Generate New Dealer PDF")
                    }
                }
            }
        }

        item {
            Text("Generated Catalogues:", fontWeight = FontWeight.Bold)
        }

        items(catalogues) { cat ->
            ElevatedCard(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(cat.title, fontWeight = FontWeight.Bold)
                        Text("${cat.catalogueType} • Downloads: ${cat.downloadCount}", style = MaterialTheme.typography.bodySmall)
                    }
                    IconButton(onClick = { onDownloadPdf(cat) }) {
                        Icon(Icons.Default.Download, contentDescription = "Download", tint = MaterialTheme.colorScheme.primary)
                    }
                }
            }
        }
    }
}

// ==========================================
// TAB 5: DEALER ORDER PORTAL
// ==========================================
@Composable
fun DealerOrderPortalTab(
    dealers: List<DealerEntity>,
    products: List<ProductEntity>,
    orders: List<DealerOrderEntity>,
    onNewOrderClick: () -> Unit,
    onUpdateOrderStatus: (Long, String) -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Dealer Order Request Portal", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Button(onClick = onNewOrderClick) {
                Icon(Icons.Default.Add, contentDescription = null)
                Spacer(modifier = Modifier.width(6.dp))
                Text("Place Order Request")
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        if (orders.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("No Dealer Orders Found", color = Color.Gray)
            }
        } else {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(orders) { order ->
                    DealerOrderCard(order = order, onUpdateOrderStatus = onUpdateOrderStatus)
                }
            }
        }
    }
}

@Composable
fun DealerOrderCard(order: DealerOrderEntity, onUpdateOrderStatus: (Long, String) -> Unit) {
    var showMenu by remember { mutableStateOf(false) }
    val statusColor = when (order.status) {
        "Pending" -> Color(0xFFFF9800)
        "Approved" -> Color(0xFF2196F3)
        "Packed" -> Color(0xFF9C27B0)
        "Dispatched" -> Color(0xFF009688)
        "Delivered" -> Color(0xFF4CAF50)
        else -> Color.Red
    }

    ElevatedCard(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Column {
                    Text("Order #${order.orderId}", fontWeight = FontWeight.Bold)
                    Text("Dealer: ${order.dealerName}", style = MaterialTheme.typography.bodySmall)
                }
                Box {
                    Surface(
                        color = statusColor.copy(alpha = 0.2f),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.clickable { showMenu = true }
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)) {
                            Text(order.status, color = statusColor, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.labelSmall)
                            Icon(Icons.Default.MoreVert, contentDescription = null, modifier = Modifier.size(16.dp))
                        }
                    }
                    DropdownMenu(expanded = showMenu, onDismissRequest = { showMenu = false }) {
                        val statuses = listOf("Pending", "Approved", "Packed", "Dispatched", "Delivered", "Cancelled")
                        statuses.forEach { st ->
                            DropdownMenuItem(
                                text = { Text(st) },
                                onClick = {
                                    onUpdateOrderStatus(order.id, st)
                                    showMenu = false
                                }
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
            Text("Product: ${order.productName} | Qty: ${order.qty} pcs", style = MaterialTheme.typography.bodyMedium)
            Text("Rate: ₹${order.rate} | Total Amount: ₹${order.amount}", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
            if (order.notes.isNotBlank()) {
                Text("Notes: ${order.notes}", style = MaterialTheme.typography.bodySmall, color = Color.Gray)
            }
        }
    }
}

// ==========================================
// TAB 6: SOCIAL ANALYTICS
// ==========================================
@Composable
fun SocialAnalyticsTab(
    dealers: List<DealerEntity>,
    products: List<ProductEntity>,
    orders: List<DealerOrderEntity>,
    catalogues: List<DealerCatalogueEntity>,
    analytics: List<com.example.vascs.data.model.SocialAnalyticsEntity>
) {
    LazyColumn(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        item {
            Text("Social Commerce & Distribution Analytics", fontWeight = FontWeight.Bold, fontSize = 16.sp)
        }

        item {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                MetricCard("Top Dealer", dealers.firstOrNull()?.firmName ?: "N/A", Modifier.weight(1f))
                MetricCard("Top Product", products.firstOrNull()?.name ?: "N/A", Modifier.weight(1f))
            }
        }

        item {
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text("Distribution Event Breakdown:", fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("• WhatsApp Shares: ${analytics.count { it.eventType == "WHATSAPP_SHARE" }}")
                    Text("• Telegram Shares: ${analytics.count { it.eventType == "TELEGRAM_SHARE" }}")
                    Text("• Instagram Exports: ${analytics.count { it.eventType == "INSTAGRAM_EXPORT" }}")
                    Text("• Dealer Catalog PDF Downloads: ${catalogues.sumOf { it.downloadCount }}")
                    Text("• Total Dealer Order Requests: ${orders.size}")
                }
            }
        }
    }
}

@Composable
fun MetricCard(title: String, value: String, modifier: Modifier = Modifier) {
    Card(modifier = modifier) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(title, style = MaterialTheme.typography.labelMedium, color = Color.Gray)
            Text(value, fontWeight = FontWeight.Bold, maxLines = 1, overflow = TextOverflow.Ellipsis)
        }
    }
}

// ==========================================
// MODAL DIALOGS
// ==========================================
@Composable
fun AddDealerDialog(
    onDismiss: () -> Unit,
    onConfirm: (String, String, String, String, String, String, String, String, Double, String) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var firm by remember { mutableStateOf("") }
    var mobile by remember { mutableStateOf("") }
    var wa by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var city by remember { mutableStateOf("") }
    var state by remember { mutableStateOf("Gujarat") }
    var gst by remember { mutableStateOf("") }
    var credit by remember { mutableStateOf("100000") }
    var dealerType by remember { mutableStateOf("Wholesaler") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add New Dealer") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(value = firm, onValueChange = { firm = it }, label = { Text("Firm Name") })
                OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Dealer Contact Name") })
                OutlinedTextField(value = mobile, onValueChange = { mobile = it }, label = { Text("Mobile Number") }, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone))
                OutlinedTextField(value = city, onValueChange = { city = it }, label = { Text("City") })
                OutlinedTextField(value = gst, onValueChange = { gst = it }, label = { Text("GSTIN (Optional)") })
                OutlinedTextField(value = credit, onValueChange = { credit = it }, label = { Text("Credit Limit (₹)") }, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number))
            }
        },
        confirmButton = {
            Button(
                onClick = { onConfirm(name, firm, mobile, wa, email, city, state, gst, credit.toDoubleOrNull() ?: 100000.0, dealerType) },
                enabled = firm.isNotBlank() && mobile.isNotBlank()
            ) {
                Text("Create Dealer")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancel") }
        }
    )
}

@Composable
fun CreateCampaignDialog(
    dealers: List<DealerEntity>,
    products: List<ProductEntity>,
    onDismiss: () -> Unit,
    onConfirm: (String, String, String, Int, String, List<Long>) -> Unit
) {
    var title by remember { mutableStateOf("Festive Saree Launch") }
    var message by remember { mutableStateOf("Exclusive wholesale stock available!") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Create WhatsApp Campaign") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(value = title, onValueChange = { title = it }, label = { Text("Campaign Title") })
                OutlinedTextField(value = message, onValueChange = { message = it }, label = { Text("Message Template") }, modifier = Modifier.height(100.dp))
            }
        },
        confirmButton = {
            Button(onClick = { onConfirm(title, "WHOLESALE_OFFER", "ALL", dealers.size, message, products.map { it.id.toLongOrNull() ?: it.id.hashCode().toLong() }) }) {
                Text("Dispatch Campaign")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancel") }
        }
    )
}

@Composable
fun CreateDealerPdfDialog(
    dealers: List<DealerEntity>,
    products: List<ProductEntity>,
    onDismiss: () -> Unit,
    onConfirm: (String, String, String, List<Long>) -> Unit
) {
    var title by remember { mutableStateOf("Wholesale Catalogue 2026") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Generate Dealer PDF Catalogue") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(value = title, onValueChange = { title = it }, label = { Text("Catalogue Title") })
            }
        },
        confirmButton = {
            Button(onClick = { onConfirm(dealers.firstOrNull()?.dealerId ?: "DLR-1001", title, "Wholesale Dealer Catalogue", products.map { it.id.toLongOrNull() ?: it.id.hashCode().toLong() }) }) {
                Text("Generate PDF")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancel") }
        }
    )
}

@Composable
fun CreateDealerOrderDialog(
    dealers: List<DealerEntity>,
    products: List<ProductEntity>,
    onDismiss: () -> Unit,
    onConfirm: (String, String, Long, String, Int, Double, String) -> Unit
) {
    var selectedDealer by remember { mutableStateOf(dealers.firstOrNull()) }
    var selectedProduct by remember { mutableStateOf(products.firstOrNull()) }
    var qtyText by remember { mutableStateOf("10") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Place Dealer Order Request") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("Dealer: ${selectedDealer?.firmName ?: "N/A"}")
                Text("Product: ${selectedProduct?.name ?: "N/A"}")
                OutlinedTextField(value = qtyText, onValueChange = { qtyText = it }, label = { Text("Quantity (Pcs)") }, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number))
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val d = selectedDealer ?: return@Button
                    val p = selectedProduct ?: return@Button
                    val qty = qtyText.toIntOrNull() ?: 10
                    val pNumericId = p.id.toLongOrNull() ?: p.id.hashCode().toLong()
                    onConfirm(d.dealerId, d.firmName, pNumericId, p.name, qty, p.wholesalePrice, "Requested via Admin Studio")
                }
            ) {
                Text("Submit Order")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancel") }
        }
    )
}
