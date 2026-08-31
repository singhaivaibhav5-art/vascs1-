package com.example.vascs.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.BatchPrediction
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.Collections
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Inventory
import androidx.compose.material.icons.filled.LocalOffer
import androidx.compose.material.icons.filled.LocalShipping
import androidx.compose.material.icons.filled.PhotoLibrary
import androidx.compose.material.icons.filled.QrCode
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material.icons.filled.AllInclusive
import androidx.compose.material.icons.filled.Stars
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.Hub
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.vascs.data.model.ProductEntity
import com.example.vascs.ui.theme.GoldAccent
import com.example.vascs.ui.theme.Maroon500
import com.example.vascs.ui.viewmodel.DashboardStats
import java.text.NumberFormat
import java.util.Locale

import androidx.compose.material.icons.filled.Chat

@Composable
fun DashboardScreen(
    stats: DashboardStats,
    recentProducts: List<ProductEntity>,
    onNavigateToProducts: () -> Unit,
    onNavigateToBatches: () -> Unit,
    onNavigateToPricing: () -> Unit,
    onNavigateToLabels: () -> Unit,
    onNavigateToPhotoUploadStudio: () -> Unit,
    onNavigateToMediaLibrary: () -> Unit,
    onNavigateToSocialExportStudio: () -> Unit,
    onNavigateToAiArchiveCenter: () -> Unit,
    onNavigateToMediaCommandCenter: () -> Unit = {},
    onNavigateToSocialDealerNetwork: () -> Unit = {},
    onNavigateToOrderDispatch: () -> Unit = {},
    onNavigateToWhatsappCommerce: () -> Unit = {},
    onNavigateToOmega: () -> Unit = {},
    onNavigateToInfinity: () -> Unit = {},
    onNavigateToCosmos: () -> Unit = {},
    onNavigateToNexus: () -> Unit = {},
    onNavigateToQuantum: () -> Unit = {},
    onNavigateToAscension: () -> Unit = {},
    onNavigateToOmniverse: () -> Unit = {},
    onNavigateToEternity: () -> Unit = {},
    onNavigateToTranscendence: () -> Unit = {},
    onNavigateToSupremacy: () -> Unit = {},
    onNavigateToSingularityPrime: () -> Unit = {},
    onNavigateToAbsolute: () -> Unit = {},
    onNavigateToUltima: () -> Unit = {},
    onNavigateToAiBrain: () -> Unit = {},
    onNavigateToAiCatalogue: () -> Unit = {},
    onNavigateToAiPricing: () -> Unit = {},
    onNavigateToAiDemand: () -> Unit = {},
    onNavigateToAiDealer: () -> Unit = {},
    onNavigateToAiInventory: () -> Unit = {},
    onAddProductClick: () -> Unit,
    onProductClick: (ProductEntity) -> Unit
) {
    val currencyFormatter = NumberFormat.getCurrencyInstance(Locale("en", "IN")).apply {
        maximumFractionDigits = 0
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        // Hero Header Banner
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Maroon500),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp)
                ) {
                    Text(
                        text = "VASCS ULTIMA 27.0",
                        color = GoldAccent,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Final Universal Commerce Intelligence State",
                        color = Color.White,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "The ultimate state of VASCS: every business, market, industry, economy, and intelligence system operates under a unified autonomous commerce civilization.",
                        color = Color.White.copy(alpha = 0.85f),
                        fontSize = 14.sp
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        Button(
                            onClick = onNavigateToUltima,
                            colors = ButtonDefaults.buttonColors(containerColor = GoldAccent, contentColor = Color.Black)
                        ) {
                            Icon(Icons.Default.AllInclusive, contentDescription = null, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Launch VASCS Ultima 27.0", fontWeight = FontWeight.Bold)
                        }
                        OutlinedButton(
                            onClick = onNavigateToProducts,
                            colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White),
                            border = ButtonDefaults.outlinedButtonBorder.copy(brush = androidx.compose.ui.graphics.SolidColor(Color.White))
                        ) {
                            Text("View Catalogue")
                        }
                    }
                }
            }
        }

        // Stat Cards Grid
        item {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Text(
                    text = "CATALOGUE METRICS",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    StatCard(
                        modifier = Modifier.weight(1f),
                        title = "Total Sarees",
                        value = "${stats.totalProducts}",
                        icon = Icons.Default.ShoppingBag,
                        accentColor = Maroon500
                    )
                    StatCard(
                        modifier = Modifier.weight(1f),
                        title = "Batches",
                        value = "${stats.activeBatches}",
                        icon = Icons.Default.BatchPrediction,
                        accentColor = Color(0xFF0284C7)
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    StatCard(
                        modifier = Modifier.weight(1f),
                        title = "Stock Items",
                        value = "${stats.totalStock} pcs",
                        icon = Icons.Default.Inventory,
                        accentColor = Color(0xFF059669)
                    )
                    StatCard(
                        modifier = Modifier.weight(1f),
                        title = "Catalog Valuation",
                        value = currencyFormatter.format(stats.totalInventoryValue),
                        icon = Icons.Default.LocalOffer,
                        accentColor = Color(0xFFD97706)
                    )
                }

                if (stats.lowStockCount > 0) {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFFEF2F2)),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(Icons.Default.Warning, contentDescription = null, tint = Color(0xFFDC2626))
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text(
                                    text = "Low Stock Alert",
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF991B1B)
                                )
                                Text(
                                    text = "${stats.lowStockCount} saree item(s) have 5 or fewer pieces remaining.",
                                    fontSize = 13.sp,
                                    color = Color(0xFFB91C1C)
                                )
                            }
                        }
                    }
                }
            }
        }

        // Quick Tools Row
        item {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Text(
                    text = "QUICK TOOLS",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold
                )

                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(horizontal = 2.dp)
                ) {
                    item {
                        QuickToolChip(
                            title = "AI INVENTORY ENGINE",
                            icon = Icons.Default.Inventory,
                            onClick = onNavigateToAiInventory
                        )
                    }
                    item {
                        QuickToolChip(
                            title = "AI CATALOGUE GENERATOR",
                            icon = Icons.AutoMirrored.Filled.MenuBook,
                            onClick = onNavigateToAiCatalogue
                        )
                    }
                    item {
                        QuickToolChip(
                            title = "AI DEALER ADVISOR",
                            icon = Icons.Default.Groups,
                            onClick = onNavigateToAiDealer
                        )
                    }
                    item {
                        QuickToolChip(
                            title = "AI DEMAND FORECAST",
                            icon = Icons.AutoMirrored.Filled.TrendingUp,
                            onClick = onNavigateToAiDemand
                        )
                    }
                    item {
                        QuickToolChip(
                            title = "AI PRICING ENGINE",
                            icon = Icons.Default.Calculate,
                            onClick = onNavigateToAiPricing
                        )
                    }
                    item {
                        QuickToolChip(
                            title = "VASCS AI BRAIN",
                            icon = Icons.Default.AutoAwesome,
                            onClick = onNavigateToAiBrain
                        )
                    }
                    item {
                        QuickToolChip(
                            title = "VASCS ULTIMA 27.0",
                            icon = Icons.Default.AllInclusive,
                            onClick = onNavigateToUltima
                        )
                    }
                    item {
                        QuickToolChip(
                            title = "VASCS ABSOLUTE 26.0",
                            icon = Icons.Default.AllInclusive,
                            onClick = onNavigateToAbsolute
                        )
                    }
                    item {
                        QuickToolChip(
                            title = "VASCS SINGULARITY PRIME 25.0",
                            icon = Icons.Default.AllInclusive,
                            onClick = onNavigateToSingularityPrime
                        )
                    }
                    item {
                        QuickToolChip(
                            title = "VASCS SUPREMACY 24.0",
                            icon = Icons.Default.Stars,
                            onClick = onNavigateToSupremacy
                        )
                    }
                    item {
                        QuickToolChip(
                            title = "VASCS TRANSCENDENCE 23.0",
                            icon = Icons.Default.AllInclusive,
                            onClick = onNavigateToTranscendence
                        )
                    }
                    item {
                        QuickToolChip(
                            title = "VASCS ETERNITY 22.0",
                            icon = Icons.Default.AllInclusive,
                            onClick = onNavigateToEternity
                        )
                    }
                    item {
                        QuickToolChip(
                            title = "VASCS OMNIVERSE 21.0",
                            icon = Icons.Default.Public,
                            onClick = onNavigateToOmniverse
                        )
                    }
                    item {
                        QuickToolChip(
                            title = "VASCS ASCENSION 20.0",
                            icon = Icons.Default.Public,
                            onClick = onNavigateToAscension
                        )
                    }
                    item {
                        QuickToolChip(
                            title = "VASCS QUANTUM 18.0",
                            icon = Icons.Default.AllInclusive,
                            onClick = onNavigateToQuantum
                        )
                    }
                    item {
                        QuickToolChip(
                            title = "VASCS NEXUS 17.0",
                            icon = Icons.Default.Hub,
                            onClick = onNavigateToNexus
                        )
                    }
                    item {
                        QuickToolChip(
                            title = "VASCS COSMOS 16.0",
                            icon = Icons.Default.Public,
                            onClick = onNavigateToCosmos
                        )
                    }
                    item {
                        QuickToolChip(
                            title = "VASCS INFINITY 15.0",
                            icon = Icons.Default.AllInclusive,
                            onClick = onNavigateToInfinity
                        )
                    }
                    item {
                        QuickToolChip(
                            title = "VASCS OMEGA 14.0",
                            icon = Icons.Default.Stars,
                            onClick = onNavigateToOmega
                        )
                    }
                    item {
                        QuickToolChip(
                            title = "WhatsApp Auto Commerce",
                            icon = Icons.Default.Chat,
                            onClick = onNavigateToWhatsappCommerce
                        )
                    }
                    item {
                        QuickToolChip(
                            title = "Order to Dispatch",
                            icon = Icons.Default.LocalShipping,
                            onClick = onNavigateToOrderDispatch
                        )
                    }
                    item {
                        QuickToolChip(
                            title = "Social & Dealer Network",
                            icon = Icons.Default.Groups,
                            onClick = onNavigateToSocialDealerNetwork
                        )
                    }
                    item {
                        QuickToolChip(
                            title = "Media Command Center",
                            icon = Icons.Default.PhotoLibrary,
                            onClick = onNavigateToMediaCommandCenter
                        )
                    }
                    item {
                        QuickToolChip(
                            title = "AI Archive Center",
                            icon = Icons.Default.AutoAwesome,
                            onClick = onNavigateToAiArchiveCenter
                        )
                    }
                    item {
                        QuickToolChip(
                            title = "Social Media Export Studio",
                            icon = Icons.Default.Share,
                            onClick = onNavigateToSocialExportStudio
                        )
                    }
                    item {
                        QuickToolChip(
                            title = "Photo Upload & Resize Studio",
                            icon = Icons.Default.AddAPhoto,
                            onClick = onNavigateToPhotoUploadStudio
                        )
                    }
                    item {
                        QuickToolChip(
                            title = "Media Library",
                            icon = Icons.Default.Collections,
                            onClick = onNavigateToMediaLibrary
                        )
                    }
                    item {
                        QuickToolChip(
                            title = "Pricing Calculator",
                            icon = Icons.Default.Calculate,
                            onClick = onNavigateToPricing
                        )
                    }
                    item {
                        QuickToolChip(
                            title = "Barcode Tag Printer",
                            icon = Icons.Default.QrCode,
                            onClick = onNavigateToLabels
                        )
                    }
                    item {
                        QuickToolChip(
                            title = "Batch Management",
                            icon = Icons.Default.BatchPrediction,
                            onClick = onNavigateToBatches
                        )
                    }
                }
            }
        }

        // Recent Catalogue Items
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "RECENT CATALOGUE ITEMS",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold
                )
                Button(
                    onClick = onNavigateToProducts,
                    colors = ButtonDefaults.textButtonColors()
                ) {
                    Text("View All")
                }
            }
        }

        if (recentProducts.isEmpty()) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(32.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("No sarees in catalogue yet. Tap 'New Saree Entry' to add.")
                    }
                }
            }
        } else {
            items(recentProducts.take(5)) { product ->
                Card(
                    onClick = { onProductClick(product) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(Maroon500.copy(alpha = 0.1f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = product.category.take(2).uppercase(),
                                fontWeight = FontWeight.Bold,
                                color = Maroon500
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = product.name,
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp,
                                maxLines = 1
                            )
                            Text(
                                text = "SKU: ${product.sku} • Fabric: ${product.fabric}",
                                fontSize = 12.sp,
                                color = Color.Gray
                            )
                        }
                        Column(horizontalAlignment = Alignment.End) {
                            Text(
                                text = currencyFormatter.format(product.retailPrice),
                                fontWeight = FontWeight.Bold,
                                color = Maroon500,
                                fontSize = 15.sp
                            )
                            Text(
                                text = "Stock: ${product.stock}",
                                fontSize = 12.sp,
                                color = if (product.stock <= 5) Color.Red else Color.Gray
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun StatCard(
    modifier: Modifier = Modifier,
    title: String,
    value: String,
    icon: ImageVector,
    accentColor: Color
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = title, fontSize = 13.sp, color = Color.Gray)
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(accentColor.copy(alpha = 0.15f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(icon, contentDescription = null, tint = accentColor, modifier = Modifier.size(18.dp))
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = value,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Composable
fun QuickToolChip(
    title: String,
    icon: ImageVector,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(icon, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
            Spacer(modifier = Modifier.width(8.dp))
            Text(title, fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
        }
    }
}
