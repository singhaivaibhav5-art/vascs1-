package com.example.vascs.ui.screens

import android.content.Context
import android.content.Intent
import android.net.Uri
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material.icons.filled.Approval
import androidx.compose.material.icons.filled.Archive
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ConfirmationNumber
import androidx.compose.material.icons.filled.DoneAll
import androidx.compose.material.icons.filled.Inventory
import androidx.compose.material.icons.filled.LocalShipping
import androidx.compose.material.icons.filled.MoneyOff
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.PendingActions
import androidx.compose.material.icons.filled.Print
import androidx.compose.material.icons.filled.QrCode
import androidx.compose.material.icons.filled.Receipt
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.Timeline
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.vascs.data.model.DeliveryEntity
import com.example.vascs.data.model.DispatchEntity
import com.example.vascs.data.model.OrderItemEntity
import com.example.vascs.data.model.OrderMasterEntity
import com.example.vascs.data.model.OrderTrackingEntity
import com.example.vascs.data.model.PackingSlipEntity
import com.example.vascs.data.model.ProductEntity
import com.example.vascs.ui.viewmodel.OrderDispatchViewModel
import java.net.URLEncoder

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderDispatchFactoryScreen(
    viewModel: OrderDispatchViewModel,
    products: List<ProductEntity> = emptyList(),
    onNavigateBack: () -> Unit = {}
) {
    var selectedTab by remember { mutableStateOf(0) }
    val tabs = listOf(
        "Order Management",
        "Packing Slips",
        "Dispatch Center",
        "Delivery Confirmation",
        "Analytics"
    )

    var showNewOrderDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text("ORDER TO DISPATCH FACTORY", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                        Text("VASCS Enterprise Supply Chain Engine", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                },
                actions = {
                    Button(
                        onClick = { showNewOrderDialog = true },
                        modifier = Modifier.padding(end = 8.dp)
                    ) {
                        Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Create Order")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            ScrollableTabRow(
                selectedTabIndex = selectedTab,
                edgePadding = 12.dp
            ) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        text = { Text(title, fontWeight = FontWeight.SemiBold) }
                    )
                }
            }

            when (selectedTab) {
                0 -> OrderManagementCenterTab(viewModel)
                1 -> PackingSlipFactoryTab(viewModel)
                2 -> DispatchCenterTab(viewModel)
                3 -> DeliveryConfirmationTab(viewModel)
                4 -> AnalyticsDashboardTab(viewModel)
            }
        }
    }

    if (showNewOrderDialog) {
        CreateOrderDialog(
            products = products,
            onDismiss = { showNewOrderDialog = false },
            onCreate = { dealerId, dealerName, mobile, whatsapp, items, remarks ->
                viewModel.createOrder(dealerId, dealerName, mobile, whatsapp, items, remarks)
                showNewOrderDialog = false
            }
        )
    }
}

@Composable
private fun OrderManagementCenterTab(viewModel: OrderDispatchViewModel) {
    val orders by viewModel.orders.collectAsState()
    val pending by viewModel.pendingOrders.collectAsState()
    val approved by viewModel.approvedOrders.collectAsState()
    val packing by viewModel.packingOrders.collectAsState()
    val dispatched by viewModel.dispatchedOrders.collectAsState()
    val delivered by viewModel.deliveredOrders.collectAsState()
    val cancelled by viewModel.cancelledOrders.collectAsState()

    val searchQuery by viewModel.searchQuery.collectAsState()
    val statusFilter by viewModel.statusFilter.collectAsState()
    val selectedOrder by viewModel.selectedOrder.collectAsState()

    val context = LocalContext.current

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Text("ORDER WORKFLOW METRICS", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = MaterialTheme.colorScheme.primary)
            Spacer(modifier = Modifier.height(6.dp))
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                item { WorkflowMetricCard("Pending", pending.size.toString(), Color(0xFFFF9800), Icons.Default.PendingActions) }
                item { WorkflowMetricCard("Approved", approved.size.toString(), Color(0xFF2196F3), Icons.Default.Approval) }
                item { WorkflowMetricCard("Packing", packing.size.toString(), Color(0xFF9C27B0), Icons.Default.Inventory) }
                item { WorkflowMetricCard("Dispatched", dispatched.size.toString(), Color(0xFF00BCD4), Icons.Default.LocalShipping) }
                item { WorkflowMetricCard("Delivered", delivered.size.toString(), Color(0xFF4CAF50), Icons.Default.CheckCircle) }
                item { WorkflowMetricCard("Cancelled", cancelled.size.toString(), Color(0xFFF44336), Icons.Default.Close) }
            }
        }

        item {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { viewModel.setSearchQuery(it) },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Search by Order #, Dealer Name, Mobile, SKU...") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                singleLine = true
            )
        }

        item {
            LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                val filters = listOf("ALL", "PENDING", "APPROVED", "PACKED", "DISPATCHED", "DELIVERED", "CANCELLED")
                items(filters) { f ->
                    FilterChip(
                        selected = statusFilter == f,
                        onClick = { viewModel.setStatusFilter(f) },
                        label = { Text(f) }
                    )
                }
            }
        }

        if (orders.isEmpty()) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth().padding(top = 24.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(Icons.Default.ShoppingCart, contentDescription = null, modifier = Modifier.size(48.dp), tint = MaterialTheme.colorScheme.outline)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("No orders match current filter", fontWeight = FontWeight.Bold)
                        Text("Click 'Create Order' at top right to place a new enterprise dealer order.", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }
            }
        } else {
            items(orders) { order ->
                OrderMasterCard(
                    order = order,
                    isSelected = selectedOrder?.orderId == order.orderId,
                    onSelect = { viewModel.selectOrder(if (selectedOrder?.orderId == order.orderId) null else order) },
                    onApprove = { viewModel.approveOrder(order.orderId) },
                    onCancel = { viewModel.cancelOrder(order.orderId, "Cancelled by Admin Studio") },
                    onWhatsApp = { msg -> sendWhatsAppMessage(context, order.whatsapp, msg) }
                )
            }
        }
    }

    selectedOrder?.let { order ->
        OrderDetailBottomSheetDialog(
            order = order,
            viewModel = viewModel,
            onDismiss = { viewModel.selectOrder(null) }
        )
    }
}

@Composable
private fun WorkflowMetricCard(
    label: String,
    value: String,
    color: Color,
    icon: ImageVector
) {
    Card(
        modifier = Modifier.width(115.dp),
        colors = CardDefaults.cardColors(containerColor = color.copy(alpha = 0.12f))
    ) {
        Column(
            modifier = Modifier.padding(10.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Icon(icon, contentDescription = null, tint = color, modifier = Modifier.size(20.dp))
            Spacer(modifier = Modifier.height(4.dp))
            Text(value, fontWeight = FontWeight.Bold, fontSize = 20.sp, color = color)
            Text(label, fontSize = 11.sp, fontWeight = FontWeight.Medium)
        }
    }
}

@Composable
private fun OrderMasterCard(
    order: OrderMasterEntity,
    isSelected: Boolean,
    onSelect: () -> Unit,
    onApprove: () -> Unit,
    onCancel: () -> Unit,
    onWhatsApp: (String) -> Unit
) {
    val statusColor = when (order.status) {
        "PENDING" -> Color(0xFFFF9800)
        "APPROVED" -> Color(0xFF2196F3)
        "PACKING", "PACKED" -> Color(0xFF9C27B0)
        "DISPATCHED" -> Color(0xFF00BCD4)
        "DELIVERED" -> Color(0xFF4CAF50)
        else -> Color(0xFFF44336)
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onSelect() },
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("#${order.orderNumber}", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    Spacer(modifier = Modifier.width(8.dp))
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .background(statusColor.copy(alpha = 0.15f))
                            .padding(horizontal = 8.dp, vertical = 2.dp)
                    ) {
                        Text(order.status, color = statusColor, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }
                }
                Text("₹${order.netAmount}", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = MaterialTheme.colorScheme.primary)
            }

            Spacer(modifier = Modifier.height(6.dp))
            Text("Dealer: ${order.dealerName}", fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
            Text("Mobile: ${order.mobile} | Date: ${order.orderDate}", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)

            Spacer(modifier = Modifier.height(6.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Total Items: ${order.totalItems} (${order.totalQty} pcs)", fontSize = 12.sp)
                Text("GST: ₹${order.gstAmount}", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }

            if (order.status == "PENDING") {
                Spacer(modifier = Modifier.height(8.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Button(
                        onClick = onApprove,
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Approve Order", fontSize = 12.sp)
                    }
                    OutlinedButton(
                        onClick = onCancel,
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Cancel", fontSize = 12.sp, color = Color.Red)
                    }
                }
            }
        }
    }
}

@Composable
private fun OrderDetailBottomSheetDialog(
    order: OrderMasterEntity,
    viewModel: OrderDispatchViewModel,
    onDismiss: () -> Unit
) {
    val items by viewModel.getOrderItems(order.orderId).collectAsState(initial = emptyList())
    val packing by viewModel.getPackingSlip(order.orderId).collectAsState(initial = null)
    val dispatch by viewModel.getDispatch(order.orderId).collectAsState(initial = null)
    val delivery by viewModel.getDelivery(order.orderId).collectAsState(initial = null)
    val tracking by viewModel.trackOrder(order.orderId).collectAsState(initial = emptyList())

    val context = LocalContext.current

    var showPackingDialog by remember { mutableStateOf(false) }
    var showDispatchDialog by remember { mutableStateOf(false) }
    var showDeliveryDialog by remember { mutableStateOf(false) }
    var showQrDialog by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Order #${order.orderNumber}", fontWeight = FontWeight.Bold)
                IconButton(onClick = { showQrDialog = true }) {
                    Icon(Icons.Default.QrCode, contentDescription = "Order QR")
                }
            }
        },
        text = {
            LazyColumn(
                modifier = Modifier.padding(vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.surfaceVariant, RoundedCornerShape(8.dp))
                            .padding(10.dp)
                    ) {
                        Text("Dealer: ${order.dealerName}", fontWeight = FontWeight.Bold)
                        Text("Mobile: ${order.mobile} | WhatsApp: ${order.whatsapp}", fontSize = 12.sp)
                        Text("Status: ${order.status}", fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.primary)
                        Text("Date: ${order.orderDate}", fontSize = 12.sp)
                    }
                }

                item {
                    Text("ORDERED ITEMS (${items.size})", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                }

                items(items) { item ->
                    Card(modifier = Modifier.fillMaxWidth()) {
                        Row(
                            modifier = Modifier.padding(8.dp).fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column {
                                Text(item.productName, fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
                                Text("SKU: ${item.sku} | Qty: ${item.qty} pcs", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            }
                            Column(horizontalAlignment = Alignment.End) {
                                Text("₹${item.netAmount}", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                                Text("Rate: ₹${item.rate} + GST ₹${item.gst}", fontSize = 10.sp)
                            }
                        }
                    }
                }

                item {
                    Divider()
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Total Amount: ₹${order.totalAmount}", fontSize = 12.sp)
                        Text("GST: ₹${order.gstAmount}", fontSize = 12.sp)
                        Text("Net: ₹${order.netAmount}", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                    }
                }

                if (packing != null) {
                    item {
                        Card(colors = CardDefaults.cardColors(containerColor = Color(0xFFF3E5F5))) {
                            Column(modifier = Modifier.padding(10.dp)) {
                                Text("📦 PACKING SLIP #${packing?.packingNumber}", fontWeight = FontWeight.Bold)
                                Text("Boxes: ${packing?.totalBoxes} | Packed By: ${packing?.packedBy}", fontSize = 12.sp)
                                Text("Date: ${packing?.packedDate}", fontSize = 11.sp)
                            }
                        }
                    }
                }

                if (dispatch != null) {
                    item {
                        Card(colors = CardDefaults.cardColors(containerColor = Color(0xFFE0F7FA))) {
                            Column(modifier = Modifier.padding(10.dp)) {
                                Text("🚚 DISPATCH NOTE #${dispatch?.dispatchNumber}", fontWeight = FontWeight.Bold)
                                Text("Transport: ${dispatch?.transportName} | LR: ${dispatch?.lrNumber}", fontSize = 12.sp)
                                Text("Vehicle: ${dispatch?.vehicleNumber} | Expected: ${dispatch?.expectedDeliveryDate}", fontSize = 11.sp)
                            }
                        }
                    }
                }

                if (delivery != null) {
                    item {
                        Card(colors = CardDefaults.cardColors(containerColor = Color(0xFFE8F5E9))) {
                            Column(modifier = Modifier.padding(10.dp)) {
                                Text("✅ DELIVERY CONFIRMATION", fontWeight = FontWeight.Bold, color = Color(0xFF2E7D32))
                                Text("Received By: ${delivery?.receivedBy} (${delivery?.mobile})", fontSize = 12.sp)
                                Text("Date: ${delivery?.deliveredDate}", fontSize = 11.sp)
                            }
                        }
                    }
                }

                item {
                    Text("ORDER TRACKING TIMELINE", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                    Spacer(modifier = Modifier.height(4.dp))
                    tracking.forEach { track ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(vertical = 2.dp)
                        ) {
                            Icon(Icons.Default.Verified, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Column {
                                Text(track.message, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                                Text(track.createdDate, fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            }
                        }
                    }
                }
            }
        },
        confirmButton = {
            Column {
                when (order.status) {
                    "PENDING" -> {
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Button(
                                onClick = {
                                    viewModel.approveOrder(order.orderId)
                                    sendWhatsAppMessage(context, order.whatsapp, "Your order #${order.orderNumber} has been approved by VASCS Enterprise.")
                                    onDismiss()
                                }
                            ) { Text("Approve") }
                            OutlinedButton(onClick = { viewModel.cancelOrder(order.orderId, "Admin Cancelled"); onDismiss() }) {
                                Text("Cancel", color = Color.Red)
                            }
                        }
                    }
                    "APPROVED" -> {
                        Button(onClick = { showPackingDialog = true }) {
                            Icon(Icons.Default.Inventory, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Create Packing Slip")
                        }
                    }
                    "PACKED", "PACKING" -> {
                        Button(onClick = { showDispatchDialog = true }) {
                            Icon(Icons.Default.LocalShipping, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Create Dispatch Note")
                        }
                    }
                    "DISPATCHED" -> {
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Button(onClick = { showDeliveryDialog = true }) {
                                Icon(Icons.Default.CheckCircle, contentDescription = null, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Mark Delivered")
                            }
                            OutlinedButton(
                                onClick = {
                                    sendWhatsAppMessage(
                                        context,
                                        order.whatsapp,
                                        "Your order #${order.orderNumber} has been dispatched via ${dispatch?.transportName ?: "Logistics"}. LR No: ${dispatch?.lrNumber ?: "N/A"}."
                                    )
                                }
                            ) { Text("WhatsApp Alert") }
                        }
                    }
                    "DELIVERED" -> {
                        OutlinedButton(
                            onClick = {
                                sendWhatsAppMessage(context, order.whatsapp, "Your order #${order.orderNumber} has been delivered successfully. Thank you for choosing VASCS!")
                            }
                        ) { Text("WhatsApp Delivered Note") }
                    }
                }
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Close") }
        }
    )

    if (showPackingDialog) {
        CreatePackingSlipDialog(
            order = order,
            itemCount = items.size,
            onDismiss = { showPackingDialog = false },
            onCreate = { packingNo, boxes, packedBy, remarks ->
                viewModel.createPackingSlip(order.orderId, packingNo, boxes, items.size, packedBy, remarks)
                showPackingDialog = false
                onDismiss()
            }
        )
    }

    if (showDispatchDialog) {
        CreateDispatchDialog(
            order = order,
            onDismiss = { showDispatchDialog = false },
            onCreate = { dispatchNo, transport, lrNo, vehicleNo, expectedDate ->
                viewModel.createDispatch(order.orderId, dispatchNo, transport, lrNo, vehicleNo, expectedDate)
                sendWhatsAppMessage(context, order.whatsapp, "Your order #${order.orderNumber} is dispatched via $transport. LR: $lrNo.")
                showDispatchDialog = false
                onDismiss()
            }
        )
    }

    if (showDeliveryDialog) {
        CreateDeliveryDialog(
            order = order,
            onDismiss = { showDeliveryDialog = false },
            onCreate = { receivedBy, mobile, remarks, proofUri ->
                viewModel.markDelivered(order.orderId, receivedBy, mobile, remarks, proofUri)
                sendWhatsAppMessage(context, order.whatsapp, "Your order #${order.orderNumber} was delivered to $receivedBy ($mobile).")
                showDeliveryDialog = false
                onDismiss()
            }
        )
    }

    if (showQrDialog) {
        AlertDialog(
            onDismissRequest = { showQrDialog = false },
            title = { Text("ORDER TRACKING QR", fontWeight = FontWeight.Bold) },
            text = {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Box(
                        modifier = Modifier
                            .size(160.dp)
                            .background(Color.White, RoundedCornerShape(12.dp))
                            .border(2.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(12.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(Icons.Default.QrCode, contentDescription = null, modifier = Modifier.size(96.dp), tint = MaterialTheme.colorScheme.primary)
                            Text("ORDER #${order.orderNumber}", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    Text("Scan this QR in Warehouse / Delivery Hub for instant tracking.", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            },
            confirmButton = {
                Button(onClick = { showQrDialog = false }) { Text("OK") }
            }
        )
    }
}

@Composable
private fun PackingSlipFactoryTab(viewModel: OrderDispatchViewModel) {
    val orders by viewModel.orders.collectAsState()
    val context = LocalContext.current

    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        item {
            Text("PACKING SLIP FACTORY", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = MaterialTheme.colorScheme.primary)
            Text("Generate and export enterprise packing slips with box counts and itemized verification.", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }

        items(orders.filter { it.status == "APPROVED" || it.status == "PACKED" || it.status == "DISPATCHED" }) { order ->
            val packing by viewModel.getPackingSlip(order.orderId).collectAsState(initial = null)

            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Order #${order.orderNumber}", fontWeight = FontWeight.Bold)
                        Text(if (packing != null) "PACKED" else "READY TO PACK", color = if (packing != null) Color(0xFF9C27B0) else Color(0xFF2196F3), fontWeight = FontWeight.Bold, fontSize = 12.sp)
                    }
                    Text("Dealer: ${order.dealerName}", fontSize = 13.sp)

                    if (packing != null) {
                        Spacer(modifier = Modifier.height(6.dp))
                        Text("Slip #${packing?.packingNumber} | Boxes: ${packing?.totalBoxes} | Packed By: ${packing?.packedBy}", fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            OutlinedButton(
                                onClick = {
                                    val printIntent = Intent(Intent.ACTION_SEND).apply {
                                        type = "text/plain"
                                        putExtra(Intent.EXTRA_SUBJECT, "Packing Slip #${packing?.packingNumber}")
                                        putExtra(Intent.EXTRA_TEXT, "VASCS PACKING SLIP\nSlip #: ${packing?.packingNumber}\nOrder #: ${order.orderNumber}\nDealer: ${order.dealerName}\nBoxes: ${packing?.totalBoxes}\nItems: ${order.totalItems}\nPacked By: ${packing?.packedBy}")
                                    }
                                    context.startActivity(Intent.createChooser(printIntent, "Export Packing Slip PDF"))
                                },
                                modifier = Modifier.weight(1f)
                            ) {
                                Icon(Icons.Default.Print, contentDescription = null, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Export Slip", fontSize = 12.sp)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun DispatchCenterTab(viewModel: OrderDispatchViewModel) {
    val orders by viewModel.orders.collectAsState()
    val context = LocalContext.current

    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        item {
            Text("DISPATCH & LOGISTICS CENTER", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = MaterialTheme.colorScheme.primary)
            Text("Manage transport name, LR numbers, vehicle numbers, and dispatch notifications.", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }

        items(orders.filter { it.status == "DISPATCHED" || it.status == "DELIVERED" }) { order ->
            val dispatch by viewModel.getDispatch(order.orderId).collectAsState(initial = null)

            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Order #${order.orderNumber}", fontWeight = FontWeight.Bold)
                        Text(order.status, fontWeight = FontWeight.Bold, color = if (order.status == "DELIVERED") Color(0xFF4CAF50) else Color(0xFF00BCD4), fontSize = 12.sp)
                    }
                    Text("Dealer: ${order.dealerName}", fontSize = 13.sp)

                    if (dispatch != null) {
                        Spacer(modifier = Modifier.height(6.dp))
                        Text("Transport: ${dispatch?.transportName} | LR #: ${dispatch?.lrNumber}", fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                        Text("Vehicle #: ${dispatch?.vehicleNumber} | Expected: ${dispatch?.expectedDeliveryDate}", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedButton(
                            onClick = {
                                val msg = "🚚 *VASCS DISPATCH ADVICE*\nOrder #: ${order.orderNumber}\nTransport: ${dispatch?.transportName}\nLR No: ${dispatch?.lrNumber}\nVehicle: ${dispatch?.vehicleNumber}\nExpected: ${dispatch?.expectedDeliveryDate}"
                                sendWhatsAppMessage(context, order.whatsapp, msg)
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Icon(Icons.Default.Share, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Share Dispatch Advice via WhatsApp", fontSize = 12.sp)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun DeliveryConfirmationTab(viewModel: OrderDispatchViewModel) {
    val orders by viewModel.orders.collectAsState()

    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        item {
            Text("DELIVERY CONFIRMATION CENTER", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = MaterialTheme.colorScheme.primary)
            Text("Proof of Delivery (POD) record hub with mobile confirmation and image tracking.", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }

        items(orders.filter { it.status == "DELIVERED" }) { order ->
            val delivery by viewModel.getDelivery(order.orderId).collectAsState(initial = null)

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFE8F5E9))
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Order #${order.orderNumber}", fontWeight = FontWeight.Bold, color = Color(0xFF1B5E20))
                        Icon(Icons.Default.Verified, contentDescription = null, tint = Color(0xFF2E7D32))
                    }
                    Text("Dealer: ${order.dealerName}", fontSize = 13.sp)
                    if (delivery != null) {
                        Spacer(modifier = Modifier.height(4.dp))
                        Text("Received By: ${delivery?.receivedBy} (${delivery?.mobile})", fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                        Text("Delivered Date: ${delivery?.deliveredDate}", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        if (!delivery?.remarks.isNull_or_empty()) {
                            Text("Remarks: ${delivery?.remarks}", fontSize = 11.sp)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun AnalyticsDashboardTab(viewModel: OrderDispatchViewModel) {
    val orders by viewModel.orders.collectAsState()
    val pending by viewModel.pendingOrders.collectAsState()
    val delivered by viewModel.deliveredOrders.collectAsState()
    val cancelled by viewModel.cancelledOrders.collectAsState()
    val dispatched by viewModel.dispatchedOrders.collectAsState()

    val pendingVal = pending.sumOf { it.netAmount }
    val deliveredVal = delivered.sumOf { it.netAmount }
    val cancelledVal = cancelled.sumOf { it.netAmount }

    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Text("SUPPLY CHAIN ANALYTICS", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = MaterialTheme.colorScheme.primary)
            Text("Real-time operational pipeline metrics and monetary values.", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }

        item {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
                AnalyticsCard("Pending Value", "₹${pendingVal.toInt()}", Color(0xFFFF9800), Modifier.weight(1f))
                AnalyticsCard("Delivered Value", "₹${deliveredVal.toInt()}", Color(0xFF4CAF50), Modifier.weight(1f))
            }
        }

        item {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
                AnalyticsCard("Cancelled Value", "₹${cancelledVal.toInt()}", Color(0xFFF44336), Modifier.weight(1f))
                AnalyticsCard("Active Pipeline", "${orders.size - cancelled.size} Orders", Color(0xFF2196F3), Modifier.weight(1f))
            }
        }

        item {
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text("DAILY DISPATCH EFFICIENCY", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Total Orders Processed: ${orders.size}", fontSize = 13.sp)
                    Text("Dispatched & En Route: ${dispatched.size}", fontSize = 13.sp)
                    Text("Fully Delivered: ${delivered.size}", fontSize = 13.sp)
                    Text("Fulfillment Rate: ${if (orders.isNotEmpty()) ((delivered.size * 100) / orders.size) else 0}%", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                }
            }
        }
    }
}

@Composable
private fun AnalyticsCard(title: String, value: String, color: Color, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = color.copy(alpha = 0.12f))
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(title, fontSize = 11.sp, fontWeight = FontWeight.Medium)
            Spacer(modifier = Modifier.height(4.dp))
            Text(value, fontWeight = FontWeight.Bold, fontSize = 18.sp, color = color)
        }
    }
}

@Composable
private fun CreateOrderDialog(
    products: List<ProductEntity>,
    onDismiss: () -> Unit,
    onCreate: (String, String, String, String, List<OrderItemEntity>, String) -> Unit
) {
    var dealerId by remember { mutableStateOf("DLR-1001") }
    var dealerName by remember { mutableStateOf("Royal Silk House") }
    var mobile by remember { mutableStateOf("9825112345") }
    var whatsapp by remember { mutableStateOf("9825112345") }
    var remarks by remember { mutableStateOf("Festive Stock Requirement") }

    var selectedProdId by remember { mutableStateOf(products.firstOrNull()?.id ?: "") }
    var qtyText by remember { mutableStateOf("25") }

    val orderItems = remember { mutableStateOf(mutableListOf<OrderItemEntity>()) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("CREATE ENTERPRISE ORDER", fontWeight = FontWeight.Bold) },
        text = {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(vertical = 4.dp)
            ) {
                item {
                    OutlinedTextField(
                        value = dealerName,
                        onValueChange = { dealerName = it },
                        label = { Text("Dealer Firm Name") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )
                }
                item {
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        OutlinedTextField(
                            value = mobile,
                            onValueChange = { mobile = it },
                            label = { Text("Mobile") },
                            modifier = Modifier.weight(1f),
                            singleLine = true
                        )
                        OutlinedTextField(
                            value = whatsapp,
                            onValueChange = { whatsapp = it },
                            label = { Text("WhatsApp") },
                            modifier = Modifier.weight(1f),
                            singleLine = true
                        )
                    }
                }

                item {
                    Divider(modifier = Modifier.padding(vertical = 4.dp))
                    Text("ADD ITEM TO ORDER", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                }

                item {
                    val selProd = products.firstOrNull { it.id == selectedProdId } ?: products.firstOrNull()
                    OutlinedTextField(
                        value = selProd?.name ?: "Sample Saree",
                        onValueChange = {},
                        label = { Text("Selected Product") },
                        readOnly = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                item {
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
                        OutlinedTextField(
                            value = qtyText,
                            onValueChange = { qtyText = it },
                            label = { Text("Qty (Pcs)") },
                            modifier = Modifier.weight(1f),
                            singleLine = true
                        )
                        Button(
                            onClick = {
                                val p = products.firstOrNull { it.id == selectedProdId } ?: products.firstOrNull()
                                val qty = qtyText.toIntOrNull() ?: 10
                                val rate = p?.wholesalePrice ?: 1850.0
                                val amt = qty * rate
                                val gst = amt * 0.05
                                val net = amt + gst

                                val newItem = OrderItemEntity(
                                    orderId = 0,
                                    productId = p?.id ?: "P-101",
                                    sku = p?.sku ?: "SKU-101",
                                    productName = p?.name ?: "Banarasi Royal Silk Saree",
                                    qty = qty,
                                    rate = rate,
                                    amount = amt,
                                    gst = gst,
                                    netAmount = net
                                )
                                orderItems.value = (orderItems.value + newItem).toMutableList()
                            }
                        ) {
                            Text("Add Item")
                        }
                    }
                }

                items(orderItems.value) { item ->
                    Card(modifier = Modifier.fillMaxWidth()) {
                        Row(
                            modifier = Modifier.padding(8.dp).fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("${item.productName} (${item.qty} pcs)", fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                            Text("₹${item.netAmount}", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (orderItems.value.isEmpty()) {
                        val p = products.firstOrNull()
                        val rate = p?.wholesalePrice ?: 1850.0
                        val amt = 25 * rate
                        val gst = amt * 0.05
                        orderItems.value.add(
                            OrderItemEntity(
                                orderId = 0,
                                productId = p?.id ?: "P-101",
                                sku = p?.sku ?: "SKU-101",
                                productName = p?.name ?: "Banarasi Royal Silk Saree",
                                qty = 25,
                                rate = rate,
                                amount = amt,
                                gst = gst,
                                netAmount = amt + gst
                            )
                        )
                    }
                    onCreate(dealerId, dealerName, mobile, whatsapp, orderItems.value, remarks)
                }
            ) { Text("Submit Order") }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancel") }
        }
    )
}

@Composable
private fun CreatePackingSlipDialog(
    order: OrderMasterEntity,
    itemCount: Int,
    onDismiss: () -> Unit,
    onCreate: (String, Int, String, String) -> Unit
) {
    var packingNo by remember { mutableStateOf("PACK-${System.currentTimeMillis().toString().takeLast(6)}") }
    var boxCountText by remember { mutableStateOf("2") }
    var packedBy by remember { mutableStateOf("Ramesh Patel (Logistics Lead)") }
    var remarks by remember { mutableStateOf("Heavy Duty Corrugated Carton - Double Sealed") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("GENERATE PACKING SLIP", fontWeight = FontWeight.Bold) },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("Order #: ${order.orderNumber} | Dealer: ${order.dealerName}", fontSize = 12.sp)
                OutlinedTextField(
                    value = packingNo,
                    onValueChange = { packingNo = it },
                    label = { Text("Packing Slip #") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = boxCountText,
                    onValueChange = { boxCountText = it },
                    label = { Text("Total Boxes / Cartons") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = packedBy,
                    onValueChange = { packedBy = it },
                    label = { Text("Packed By") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = remarks,
                    onValueChange = { remarks = it },
                    label = { Text("Remarks") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val boxes = boxCountText.toIntOrNull() ?: 2
                    onCreate(packingNo, boxes, packedBy, remarks)
                }
            ) { Text("Confirm & Pack") }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancel") }
        }
    )
}

@Composable
private fun CreateDispatchDialog(
    order: OrderMasterEntity,
    onDismiss: () -> Unit,
    onCreate: (String, String, String, String, String) -> Unit
) {
    var dispatchNo by remember { mutableStateOf("DISP-${System.currentTimeMillis().toString().takeLast(6)}") }
    var transport by remember { mutableStateOf("VRL Logistics Limited") }
    var lrNo by remember { mutableStateOf("LR-9821104") }
    var vehicleNo by remember { mutableStateOf("GJ-05-BX-4321") }
    var expectedDate by remember { mutableStateOf("15 Aug 2026") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("CREATE DISPATCH NOTE", fontWeight = FontWeight.Bold) },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("Order #: ${order.orderNumber} | Dealer: ${order.dealerName}", fontSize = 12.sp)
                OutlinedTextField(
                    value = dispatchNo,
                    onValueChange = { dispatchNo = it },
                    label = { Text("Dispatch Note #") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = transport,
                    onValueChange = { transport = it },
                    label = { Text("Transport / Courier Name") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = lrNo,
                    onValueChange = { lrNo = it },
                    label = { Text("LR / Lorry Receipt #") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = vehicleNo,
                    onValueChange = { vehicleNo = it },
                    label = { Text("Vehicle #") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = expectedDate,
                    onValueChange = { expectedDate = it },
                    label = { Text("Expected Delivery Date") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    onCreate(dispatchNo, transport, lrNo, vehicleNo, expectedDate)
                }
            ) { Text("Dispatch Order") }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancel") }
        }
    )
}

@Composable
private fun CreateDeliveryDialog(
    order: OrderMasterEntity,
    onDismiss: () -> Unit,
    onCreate: (String, String, String, String) -> Unit
) {
    var receivedBy by remember { mutableStateOf(order.dealerName) }
    var mobile by remember { mutableStateOf(order.mobile) }
    var remarks by remember { mutableStateOf("All boxes received intact with seals unbroken") }
    var proofUri by remember { mutableStateOf("file://pod_proof_101.jpg") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("RECORD DELIVERY PROOF", fontWeight = FontWeight.Bold) },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("Order #: ${order.orderNumber}", fontSize = 12.sp)
                OutlinedTextField(
                    value = receivedBy,
                    onValueChange = { receivedBy = it },
                    label = { Text("Received By (Person Name)") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = mobile,
                    onValueChange = { mobile = it },
                    label = { Text("Receiver Mobile") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = remarks,
                    onValueChange = { remarks = it },
                    label = { Text("Remarks") },
                    modifier = Modifier.fillMaxWidth()
                )
                Button(
                    onClick = { proofUri = "file://pod_photo_${System.currentTimeMillis()}.jpg" },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(Icons.Default.Receipt, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(if (proofUri.contains("pod_photo")) "Proof Photo Captured ✅" else "Take Delivery Proof Photo")
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    onCreate(receivedBy, mobile, remarks, proofUri)
                }
            ) { Text("Confirm Delivery") }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancel") }
        }
    )
}

private fun sendWhatsAppMessage(context: Context, mobile: String, text: String) {
    try {
        val formattedNum = mobile.replace("+", "").replace(" ", "")
        val intent = Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse("https://wa.me/$formattedNum?text=${URLEncoder.encode(text, "UTF-8")}")
        }
        context.startActivity(intent)
    } catch (e: Exception) {
        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, text)
        }
        context.startActivity(Intent.createChooser(shareIntent, "Share Message"))
    }
}

private fun String?.isNull_or_empty(): Boolean = this == null || this.trim().isEmpty()
