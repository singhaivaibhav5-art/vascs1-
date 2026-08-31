package com.example.vascs.ui.screens

import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.BatchPrediction
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.HourglassTop
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.vascs.data.model.ProductBatchEntity
import com.example.vascs.data.model.ProductEntity
import com.example.vascs.ui.theme.Maroon500
import java.util.UUID

@Composable
fun BatchesScreen(
    batches: List<ProductBatchEntity>,
    allProducts: List<ProductEntity>,
    onCreateBatchSave: (ProductBatchEntity) -> Unit,
    onDeleteBatchClick: (String) -> Unit,
    onBatchClick: (ProductBatchEntity) -> Unit
) {
    var showCreateModal by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Top Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "PRODUCT BATCHES",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = Maroon500
                )
                Text(
                    text = "Catalog Production Lots",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Button(
                onClick = { showCreateModal = true },
                colors = ButtonDefaults.buttonColors(containerColor = Maroon500),
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(Icons.Default.Add, contentDescription = null)
                Spacer(modifier = Modifier.width(4.dp))
                Text("New Batch")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (batches.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        Icons.Default.BatchPrediction,
                        contentDescription = null,
                        modifier = Modifier.size(64.dp),
                        tint = Maroon500.copy(alpha = 0.5f)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "No batch lots created yet.",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Group saree entries into batch runs for print labels and cataloging.",
                        fontSize = 13.sp,
                        color = Color.Gray
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(onClick = { showCreateModal = true }) {
                        Text("Create First Batch")
                    }
                }
            }
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(batches, key = { it.id }) { batch ->
                    BatchCardItem(
                        batch = batch,
                        onCardClick = { onBatchClick(batch) },
                        onDeleteClick = { onDeleteBatchClick(batch.id) }
                    )
                }
            }
        }
    }

    if (showCreateModal) {
        CreateBatchDialog(
            allProducts = allProducts,
            onDismiss = { showCreateModal = false },
            onSave = { newBatch ->
                onCreateBatchSave(newBatch)
                showCreateModal = false
            }
        )
    }
}

@Composable
fun BatchCardItem(
    batch: ProductBatchEntity,
    onCardClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onCardClick() },
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = batch.batchNumber,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = Maroon500,
                        letterSpacing = 1.sp
                    )
                    Text(
                        text = batch.batchName,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                // Status Badge
                val (statusBg, statusFg) = when (batch.status) {
                    "ACTIVE" -> Color(0xFFDCFCE7) to Color(0xFF15803D)
                    "COMPLETED" -> Color(0xFFE0F2FE) to Color(0xFF0369A1)
                    else -> Color(0xFFFEF3C7) to Color(0xFFB45309)
                }

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(statusBg)
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = batch.status,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = statusFg
                    )
                }
            }

            if (batch.description.isNotBlank()) {
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = batch.description,
                    fontSize = 13.sp,
                    color = Color.Gray
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Progress bar
            val progress = if (batch.totalProducts > 0) {
                batch.completedProducts.toFloat() / batch.totalProducts.toFloat()
            } else 0f

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Total Items: ${batch.totalProducts}", fontSize = 12.sp, color = Color.Gray)
                Text(text = "Completed: ${batch.completedProducts}/${batch.totalProducts}", fontSize = 12.sp, fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(6.dp))

            LinearProgressIndicator(
                progress = { progress },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp)
                    .clip(RoundedCornerShape(3.dp)),
                color = Maroon500,
                trackColor = Color(0xFFE2E8F0)
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Created: ${batch.createdAt.take(10)}", fontSize = 11.sp, color = Color.Gray)

                Row {
                    OutlinedButton(onClick = onCardClick) {
                        Text("View Batch")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    IconButton(onClick = onDeleteClick) {
                        Icon(Icons.Default.Delete, contentDescription = "Delete", tint = Color(0xFFEF4444))
                    }
                }
            }
        }
    }
}

@Composable
fun CreateBatchDialog(
    allProducts: List<ProductEntity>,
    onDismiss: () -> Unit,
    onSave: (ProductBatchEntity) -> Unit
) {
    var batchName by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("Banarasi") }
    var brand by remember { mutableStateOf("VASCS Signature") }

    val selectedProductIds = remember { mutableStateListOf<String>() }

    var errorMessage by remember { mutableStateOf("") }

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = MaterialTheme.colorScheme.surface,
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(20.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Create New Product Batch", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Maroon500)
                    IconButton(onClick = onDismiss) {
                        Icon(Icons.Default.Close, contentDescription = "Close")
                    }
                }

                if (errorMessage.isNotBlank()) {
                    Text(errorMessage, color = MaterialTheme.colorScheme.error, fontSize = 13.sp)
                }

                OutlinedTextField(
                    value = batchName,
                    onValueChange = { batchName = it; errorMessage = "" },
                    label = { Text("Batch Name *") },
                    placeholder = { Text("e.g. Banarasi Festive Collection 2026") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Description") },
                    placeholder = { Text("Brief notes on this production batch...") },
                    modifier = Modifier.fillMaxWidth()
                )

                Text("SELECT SAREES FOR BATCH (${selectedProductIds.size} selected)", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Maroon500)

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                ) {
                    if (allProducts.isEmpty()) {
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            Text("No sarees available in catalog master.")
                        }
                    } else {
                        LazyColumn(modifier = Modifier.padding(8.dp)) {
                            items(allProducts) { product ->
                                val isSelected = selectedProductIds.contains(product.id)
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable {
                                            if (isSelected) selectedProductIds.remove(product.id)
                                            else selectedProductIds.add(product.id)
                                        }
                                        .padding(8.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Checkbox(
                                        checked = isSelected,
                                        onCheckedChange = {
                                            if (it) selectedProductIds.add(product.id)
                                            else selectedProductIds.remove(product.id)
                                        },
                                        colors = CheckboxDefaults.colors(checkedColor = Maroon500)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Column {
                                        Text(product.name, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                                        Text("${product.category} • SKU: ${product.sku}", fontSize = 11.sp, color = Color.Gray)
                                    }
                                }
                                HorizontalDivider(modifier = Modifier.padding(horizontal = 8.dp))
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    Button(
                        onClick = {
                            if (batchName.isBlank()) {
                                errorMessage = "Please enter a Batch Name."
                                return@Button
                            }
                            if (selectedProductIds.isEmpty()) {
                                errorMessage = "Please select at least one saree product."
                                return@Button
                            }

                            val productIdsJsonStr = "[\"" + selectedProductIds.joinToString("\",\"") + "\"]"

                            val newBatch = ProductBatchEntity(
                                id = "batch-" + UUID.randomUUID().toString().take(8),
                                batchNumber = "BATCH-${System.currentTimeMillis()}",
                                batchName = batchName.trim(),
                                category = category,
                                brand = brand,
                                description = description.trim(),
                                productIdsJson = productIdsJsonStr,
                                status = "ACTIVE",
                                totalProducts = selectedProductIds.size,
                                completedProducts = selectedProductIds.size,
                                pendingProducts = 0,
                                failedProducts = 0,
                                createdAt = java.text.SimpleDateFormat("yyyy-MM-dd HH:mm", java.util.Locale.getDefault()).format(java.util.Date()),
                                updatedAt = java.text.SimpleDateFormat("yyyy-MM-dd HH:mm", java.util.Locale.getDefault()).format(java.util.Date())
                            )

                            onSave(newBatch)
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Maroon500),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Create Batch", fontWeight = FontWeight.Bold)
                    }

                    OutlinedButton(
                        onClick = onDismiss,
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Cancel")
                    }
                }
            }
        }
    }
}
