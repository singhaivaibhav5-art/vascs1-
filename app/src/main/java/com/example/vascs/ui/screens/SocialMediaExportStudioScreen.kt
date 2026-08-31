package com.example.vascs.ui.screens

import android.widget.Toast
import androidx.compose.foundation.Image
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
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Layers
import androidx.compose.material.icons.filled.PhotoLibrary
import androidx.compose.material.icons.filled.QrCode
import androidx.compose.material.icons.filled.Queue
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.vascs.data.model.ExportQueueEntity
import com.example.vascs.data.model.ProductEntity
import com.example.vascs.ui.theme.GoldAccent
import com.example.vascs.ui.theme.Maroon500
import com.example.vascs.ui.viewmodel.VascsViewModel
import com.example.vascs.util.ExportType
import com.example.vascs.util.SocialExportEngine

@Composable
fun SocialMediaExportStudioScreen(
    viewModel: VascsViewModel,
    onPreviewExport: (product: ProductEntity, exportType: ExportType, exportImageUri: String) -> Unit
) {
    val context = LocalContext.current
    val products by viewModel.products.collectAsState()
    val exportJobs by viewModel.allExportJobs.collectAsState()

    var selectedFormat by remember { mutableStateOf(ExportType.WHATSAPP_CARD) }
    var selectedProduct by remember { mutableStateOf<ProductEntity?>(products.firstOrNull()) }
    var bulkCountChoice by remember { mutableStateOf(10) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        // Hero Banner
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Maroon500),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Share, contentDescription = null, tint = GoldAccent, modifier = Modifier.size(28.dp))
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = "SOCIAL MEDIA EXPORT STUDIO",
                            color = GoldAccent,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.sp
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Saree Marketing & Bulk Export Engine",
                        color = Color.White,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "GOLDEN RULE: No photo is exported alone. Every export integrates product specs, prices, GST, WhatsApp contacts, and QR codes.",
                        color = Color.White.copy(alpha = 0.85f),
                        fontSize = 13.sp,
                        lineHeight = 18.sp
                    )
                }
            }
        }

        // Section 1: Choose Export Format
        item {
            Text(
                text = "1. CHOOSE EXPORT FORMAT",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold
            )
        }

        item {
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(ExportType.values()) { type ->
                    val isSelected = selectedFormat == type
                    FilterChip(
                        selected = isSelected,
                        onClick = { selectedFormat = type },
                        label = {
                            Text("${type.displayName}\n(${type.width}x${type.height})")
                        },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = Maroon500,
                            selectedLabelColor = Color.White
                        )
                    )
                }
            }
        }

        // Section 2: Single Card Generator
        item {
            Text(
                text = "2. SINGLE CARD GENERATOR",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold
            )
        }

        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Select Product to Export:", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    Spacer(modifier = Modifier.height(8.dp))

                    LazyRow(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        items(products) { prod ->
                            val isSelected = selectedProduct?.id == prod.id
                            Card(
                                modifier = Modifier
                                    .width(130.dp)
                                    .clickable { selectedProduct = prod }
                                    .border(
                                        width = if (isSelected) 2.dp else 1.dp,
                                        color = if (isSelected) Maroon500 else Color.LightGray,
                                        shape = RoundedCornerShape(8.dp)
                                    ),
                                colors = CardDefaults.cardColors(
                                    containerColor = if (isSelected) Maroon500.copy(alpha = 0.05f) else Color.White
                                )
                            ) {
                                Column(
                                    modifier = Modifier.padding(8.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Image(
                                        painter = rememberAsyncImagePainter(prod.image),
                                        contentDescription = null,
                                        modifier = Modifier
                                            .size(80.dp)
                                            .clip(RoundedCornerShape(6.dp)),
                                        contentScale = ContentScale.Crop
                                    )
                                    Spacer(modifier = Modifier.height(6.dp))
                                    Text(
                                        text = prod.name,
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                    Text(
                                        text = "₹${prod.retailPrice}",
                                        fontSize = 11.sp,
                                        color = Maroon500,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    val activeProduct = selectedProduct
                    Button(
                        onClick = {
                            if (activeProduct != null) {
                                val exportUri = SocialExportEngine.generateAndSaveExport(
                                    context = context,
                                    product = activeProduct,
                                    exportType = selectedFormat,
                                    sourceImageUri = activeProduct.image
                                )
                                if (exportUri != null) {
                                    onPreviewExport(activeProduct, selectedFormat, exportUri)
                                } else {
                                    Toast.makeText(context, "Export generation failed", Toast.LENGTH_SHORT).show()
                                }
                            } else {
                                Toast.makeText(context, "Please select a product first", Toast.LENGTH_SHORT).show()
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = Maroon500),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Icon(Icons.Default.AutoAwesome, contentDescription = null, tint = GoldAccent)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("GENERATE & PREVIEW ${selectedFormat.displayName.uppercase()}", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        // Section 3: Bulk Export Engine (10, 50, 100, 500)
        item {
            Text(
                text = "3. BULK AUTOMATED EXPORT ENGINE",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold
            )
        }

        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Select Batch Volume:", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        listOf(10, 50, 100, 500).forEach { count ->
                            val isSelected = bulkCountChoice == count
                            Button(
                                onClick = { bulkCountChoice = count },
                                modifier = Modifier.weight(1f),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (isSelected) Maroon500 else Color.LightGray.copy(alpha = 0.3f),
                                    contentColor = if (isSelected) Color.White else Color.Black
                                ),
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Text("$count Cards", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Button(
                        onClick = {
                            val targetList = products.take(bulkCountChoice)
                            viewModel.enqueueBulkExport(context, targetList, selectedFormat)
                            Toast.makeText(context, "Enqueued ${targetList.size} cards for bulk background processing", Toast.LENGTH_SHORT).show()
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0284C7)),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Icon(Icons.Default.Layers, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("LAUNCH BULK EXPORT QUEUE ($bulkCountChoice)", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        // Section 4: Export Queue Monitor
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "4. EXPORT QUEUE MONITOR",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold
                )

                if (exportJobs.isNotEmpty()) {
                    TextButton(onClick = { viewModel.clearCompletedExportJobs() }) {
                        Text("Clear Completed", fontSize = 12.sp)
                    }
                }
            }
        }

        if (exportJobs.isEmpty()) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Box(modifier = Modifier.padding(24.dp), contentAlignment = Alignment.Center) {
                        Text("No queued export jobs. Launch single or bulk exports above.", color = Color.Gray, fontSize = 13.sp)
                    }
                }
            }
        } else {
            items(exportJobs, key = { it.id }) { job ->
                ExportQueueItemCard(job)
            }
        }
    }
}

@Composable
fun ExportQueueItemCard(job: ExportQueueEntity) {
    val statusColor = when (job.status) {
        "SUCCESS" -> Color(0xFF059669)
        "PROCESSING" -> Color(0xFF0284C7)
        "FAILED" -> Color(0xFFDC2626)
        else -> Color.Gray
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Product ID: ${job.productId} (${job.exportType})", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                Box(
                    modifier = Modifier
                        .background(statusColor.copy(alpha = 0.15f), RoundedCornerShape(4.dp))
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                ) {
                    Text(job.status, fontSize = 10.sp, fontWeight = FontWeight.Bold, color = statusColor)
                }
            }

            if (job.status == "PROCESSING") {
                Spacer(modifier = Modifier.height(8.dp))
                LinearProgressIndicator(modifier = Modifier.fillMaxWidth(), color = Maroon500)
            }

            if (!job.outputImageUri.isNullOrBlank()) {
                Spacer(modifier = Modifier.height(4.dp))
                Text("Saved: ${job.outputImageUri}", fontSize = 11.sp, color = Color.Gray, maxLines = 1, overflow = TextOverflow.Ellipsis)
            }
        }
    }
}
