package com.example.vascs.ui.screens

import android.content.Intent
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Archive
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Compare
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.DeleteForever
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Insights
import androidx.compose.material.icons.filled.PhotoLibrary
import androidx.compose.material.icons.filled.QrCode
import androidx.compose.material.icons.filled.Recycling
import androidx.compose.material.icons.filled.Restore
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Sort
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Transform
import androidx.compose.material.icons.filled.ZoomIn
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
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
import coil.compose.AsyncImage
import com.example.vascs.data.model.MediaCommandCenterEntity
import com.example.vascs.ui.theme.GoldAccent
import com.example.vascs.ui.theme.Maroon500
import com.example.vascs.ui.theme.Maroon700
import com.example.vascs.ui.viewmodel.MediaCommandCenterViewModel
import com.example.vascs.ui.viewmodel.MediaSortOption
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun MediaCommandCenterScreen(
    viewModel: MediaCommandCenterViewModel,
    onGotoProduct: ((Long) -> Unit)? = null
) {
    val context = LocalContext.current
    var selectedTab by remember { mutableStateOf(0) }
    val tabs = listOf("MEDIA LIBRARY", "VERSION TIMELINE", "COMPARISON MODE", "ANALYTICS", "RECYCLE BIN")

    val allRawItems by viewModel.rawMediaItems.collectAsState()
    val filteredItems by viewModel.filteredMediaItems.collectAsState()
    val recycleBinItems by viewModel.rawRecycleBin.collectAsState()

    val searchQuery by viewModel.searchQuery.collectAsState()
    val typeFilter by viewModel.selectedTypeFilter.collectAsState()
    val sourceFilter by viewModel.selectedSourceFilter.collectAsState()
    val currentSort by viewModel.sortOption.collectAsState()
    val selectedIds by viewModel.selectedMediaIds.collectAsState()

    var previewMediaItem by remember { mutableStateOf<MediaCommandCenterEntity?>(null) }
    var itemToDelete by remember { mutableStateOf<MediaCommandCenterEntity?>(null) }
    var isPermanentDelete by remember { mutableStateOf(false) }

    // Dashboard Banner Counts
    val totalImages = allRawItems.size
    val originalCount = allRawItems.count { it.mediaType == "ORIGINAL" && !it.isArchived && !it.isDeleted }
    val aiCount = allRawItems.count { it.mediaType == "AI" && !it.isArchived && !it.isDeleted }
    val resizedCount = allRawItems.count { it.mediaType == "RESIZED" && !it.isArchived && !it.isDeleted }
    val archiveCount = allRawItems.count { it.isArchived && !it.isDeleted }
    val deletedCount = recycleBinItems.size
    val exportCount = allRawItems.count { it.mediaType == "EXPORT" && !it.isArchived && !it.isDeleted }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Top Enterprise Header
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(0.dp),
            colors = CardDefaults.cardColors(containerColor = Maroon500)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(GoldAccent),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                Icons.Default.PhotoLibrary,
                                contentDescription = null,
                                tint = Maroon700,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(
                                text = "PRODUCT MEDIA COMMAND CENTER",
                                color = GoldAccent,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 1.sp
                            )
                            Text(
                                text = "Unified Enterprise Asset Hub",
                                color = Color.White,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    Card(
                        colors = CardDefaults.cardColors(containerColor = Maroon700),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = "$totalImages TOTAL ASSETS",
                            color = GoldAccent,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Stats Dashboard Bar
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    item { DashboardStatChip("Original", "$originalCount", Maroon700, Color.White) }
                    item { DashboardStatChip("AI Photos", "$aiCount", GoldAccent, Maroon700) }
                    item { DashboardStatChip("Resized", "$resizedCount", Color(0xFF0284C7), Color.White) }
                    item { DashboardStatChip("Exported", "$exportCount", Color(0xFF059669), Color.White) }
                    item { DashboardStatChip("Archived", "$archiveCount", Color(0xFFD97706), Color.White) }
                    item { DashboardStatChip("Deleted", "$deletedCount", Color.Red, Color.White) }
                }
            }
        }

        // Navigation Tabs
        ScrollableTabRow(
            selectedTabIndex = selectedTab,
            containerColor = Maroon700,
            contentColor = GoldAccent,
            edgePadding = 12.dp,
            indicator = { tabPositions ->
                TabRowDefaults.SecondaryIndicator(
                    Modifier.tabIndicatorOffset(tabPositions[selectedTab]),
                    color = GoldAccent,
                    height = 3.dp
                )
            }
        ) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTab == index,
                    onClick = {
                        selectedTab = index
                        viewModel.clearSelection()
                    },
                    text = {
                        Text(
                            text = title,
                            fontWeight = if (selectedTab == index) FontWeight.Bold else FontWeight.Medium,
                            color = if (selectedTab == index) GoldAccent else Color.LightGray,
                            fontSize = 12.sp
                        )
                    }
                )
            }
        }

        // Main Tab Content Area
        Box(modifier = Modifier.weight(1f)) {
            when (selectedTab) {
                0 -> MediaLibraryTab(
                    viewModel = viewModel,
                    filteredItems = filteredItems,
                    selectedIds = selectedIds,
                    searchQuery = searchQuery,
                    typeFilter = typeFilter,
                    sourceFilter = sourceFilter,
                    currentSort = currentSort,
                    onPreviewItem = {
                        viewModel.incrementView(it.id)
                        previewMediaItem = it
                    },
                    onSoftDelete = {
                        itemToDelete = it
                        isPermanentDelete = false
                    }
                )

                1 -> VersionTimelineTab(
                    viewModel = viewModel,
                    allItems = allRawItems
                )

                2 -> ComparisonModeTab(
                    viewModel = viewModel,
                    allItems = allRawItems
                )

                3 -> MediaAnalyticsTab(
                    viewModel = viewModel,
                    allItems = allRawItems
                )

                4 -> MediaRecycleBinTab(
                    viewModel = viewModel,
                    recycleBinItems = recycleBinItems,
                    selectedIds = selectedIds,
                    onRestore = { viewModel.restore(it.id) },
                    onPermanentDelete = {
                        itemToDelete = it
                        isPermanentDelete = true
                    }
                )
            }
        }
    }

    // Full Preview & Details Dialog
    previewMediaItem?.let { item ->
        MediaItemDetailDialog(
            mediaItem = item,
            onDismiss = { previewMediaItem = null },
            onSetCover = {
                viewModel.setAsPrimaryCover(item)
                Toast.makeText(context, "Set as main cover for ${item.productName}", Toast.LENGTH_SHORT).show()
                previewMediaItem = null
            },
            onArchive = {
                viewModel.archiveMedia(item.id)
                Toast.makeText(context, "Moved to Archive", Toast.LENGTH_SHORT).show()
                previewMediaItem = null
            },
            onDelete = {
                itemToDelete = item
                isPermanentDelete = false
                previewMediaItem = null
            },
            onShare = { platform ->
                viewModel.incrementShare(item.id)
                val shareIntent = Intent(Intent.ACTION_SEND).apply {
                    type = "text/plain"
                    putExtra(Intent.EXTRA_SUBJECT, "VASCS Media Asset: ${item.productName}")
                    putExtra(
                        Intent.EXTRA_TEXT,
                        "VASCS Media Command Center Asset\n\n" +
                                "Product: ${item.productName}\n" +
                                "SKU: ${item.sku}\n" +
                                "QR: ${item.qrNumber}\n" +
                                "Type: ${item.mediaType}\n" +
                                "Version: V${item.versionNumber}\n" +
                                "Image Link: ${item.imageUri}"
                    )
                }
                context.startActivity(Intent.createChooser(shareIntent, "Share via $platform"))
            },
            onDownload = { location ->
                viewModel.incrementDownload(item.id)
                Toast.makeText(context, "Downloaded asset to $location", Toast.LENGTH_SHORT).show()
            },
            onGotoProduct = {
                if (item.productId > 0) {
                    onGotoProduct?.invoke(item.productId)
                    previewMediaItem = null
                }
            }
        )
    }

    // Delete Confirmation Dialog
    itemToDelete?.let { item ->
        AlertDialog(
            onDismissRequest = { itemToDelete = null },
            title = {
                Text(
                    text = if (isPermanentDelete) "Permanent Delete Confirmation" else "Move to Recycle Bin",
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Text(
                    text = if (isPermanentDelete) {
                        "Are you sure you want to permanently delete media asset '${item.mediaId}' for product '${item.productName}'? This action cannot be undone."
                    } else {
                        "Move media asset '${item.mediaId}' to Media Recycle Bin? You can restore it at any time."
                    }
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (isPermanentDelete) {
                            viewModel.deletePermanently(item.id)
                            Toast.makeText(context, "Permanently erased asset", Toast.LENGTH_SHORT).show()
                        } else {
                            viewModel.softDelete(item.id)
                            Toast.makeText(context, "Moved to Recycle Bin", Toast.LENGTH_SHORT).show()
                        }
                        itemToDelete = null
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isPermanentDelete) Color.Red else Maroon500
                    )
                ) {
                    Text(if (isPermanentDelete) "Delete Permanently" else "Move to Recycle Bin", fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { itemToDelete = null }) {
                    Text("Cancel")
                }
            }
        )
    }
}

@Composable
fun DashboardStatChip(label: String, count: String, bgColor: Color, textColor: Color) {
    Card(
        colors = CardDefaults.cardColors(containerColor = bgColor),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "$label: ", fontSize = 11.sp, color = textColor)
            Text(text = count, fontSize = 11.sp, fontWeight = FontWeight.Bold, color = textColor)
        }
    }
}

@Composable
fun MediaLibraryTab(
    viewModel: MediaCommandCenterViewModel,
    filteredItems: List<MediaCommandCenterEntity>,
    selectedIds: Set<Long>,
    searchQuery: String,
    typeFilter: String,
    sourceFilter: String,
    currentSort: MediaSortOption,
    onPreviewItem: (MediaCommandCenterEntity) -> Unit,
    onSoftDelete: (MediaCommandCenterEntity) -> Unit
) {
    val context = LocalContext.current
    var showSortDropdown by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp)
    ) {
        // Search & Sort Row
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { viewModel.searchQuery.value = it },
                placeholder = { Text("Search Product, SKU, QR, or Media ID...") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                modifier = Modifier.weight(1f),
                singleLine = true,
                shape = RoundedCornerShape(12.dp)
            )

            // Sort Dropdown Button
            Box {
                OutlinedButton(
                    onClick = { showSortDropdown = true },
                    shape = RoundedCornerShape(12.dp),
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 14.dp)
                ) {
                    Icon(Icons.Default.Sort, contentDescription = null, modifier = Modifier.size(18.dp), tint = Maroon500)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Sort", fontSize = 12.sp, color = Maroon500, fontWeight = FontWeight.Bold)
                }

                DropdownMenu(
                    expanded = showSortDropdown,
                    onDismissRequest = { showSortDropdown = false }
                ) {
                    MediaSortOption.entries.forEach { option ->
                        DropdownMenuItem(
                            text = { Text(option.name.replace("_", " "), fontSize = 12.sp) },
                            onClick = {
                                viewModel.sortOption.value = option
                                showSortDropdown = false
                            }
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Type Filters
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(Icons.Default.FilterList, contentDescription = null, modifier = Modifier.size(16.dp), tint = Maroon500)
            Spacer(modifier = Modifier.width(6.dp))
            Text("TYPE:", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Maroon500)
            Spacer(modifier = Modifier.width(8.dp))

            val types = listOf("ALL", "ORIGINAL", "CAMERA", "GALLERY", "AI", "RESIZED", "EXPORT", "ARCHIVE")
            LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                items(types) { type ->
                    val isSel = typeFilter.equals(type, ignoreCase = true)
                    Card(
                        onClick = { viewModel.selectedTypeFilter.value = type },
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = if (isSel) Maroon500 else MaterialTheme.colorScheme.surfaceVariant
                        )
                    ) {
                        Text(
                            text = type,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (isSel) GoldAccent else MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(6.dp))

        // Bulk Actions Bar
        if (selectedIds.isNotEmpty()) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                colors = CardDefaults.cardColors(containerColor = GoldAccent)
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "${selectedIds.size} SELECTED",
                        fontWeight = FontWeight.Bold,
                        color = Maroon700,
                        fontSize = 12.sp
                    )

                    LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        item {
                            Button(
                                onClick = {
                                    viewModel.bulkArchive()
                                    Toast.makeText(context, "Archived selected media assets", Toast.LENGTH_SHORT).show()
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = Maroon700, contentColor = Color.White),
                                contentPadding = PaddingValues(horizontal = 8.dp, vertical = 2.dp)
                            ) {
                                Icon(Icons.Default.Archive, contentDescription = null, modifier = Modifier.size(12.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Archive", fontSize = 10.sp)
                            }
                        }

                        item {
                            Button(
                                onClick = {
                                    viewModel.bulkSoftDelete()
                                    Toast.makeText(context, "Moved selected to Recycle Bin", Toast.LENGTH_SHORT).show()
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = Maroon500, contentColor = Color.White),
                                contentPadding = PaddingValues(horizontal = 8.dp, vertical = 2.dp)
                            ) {
                                Icon(Icons.Default.Delete, contentDescription = null, modifier = Modifier.size(12.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Delete", fontSize = 10.sp)
                            }
                        }

                        item {
                            OutlinedButton(
                                onClick = { viewModel.clearSelection() },
                                contentPadding = PaddingValues(horizontal = 8.dp, vertical = 2.dp)
                            ) {
                                Text("Clear", fontSize = 10.sp, color = Maroon700)
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(6.dp))

        // Grid View
        if (filteredItems.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(Icons.Default.Image, contentDescription = null, modifier = Modifier.size(48.dp), tint = Color.Gray)
                    Spacer(modifier = Modifier.height(12.dp))
                    Text("No media assets match current filter/search.", color = Color.Gray, fontWeight = FontWeight.Bold)
                }
            }
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(filteredItems, key = { it.id }) { item ->
                    val isSelected = selectedIds.contains(item.id)

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onPreviewItem(item) },
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        Column {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(140.dp)
                                    .background(Color.LightGray)
                            ) {
                                AsyncImage(
                                    model = item.imageUri,
                                    contentDescription = item.productName,
                                    modifier = Modifier.fillMaxSize(),
                                    contentScale = ContentScale.Crop
                                )

                                // Type Badge
                                Box(
                                    modifier = Modifier
                                        .padding(6.dp)
                                        .align(Alignment.TopStart)
                                        .clip(RoundedCornerShape(6.dp))
                                        .background(
                                            when (item.mediaType) {
                                                "AI" -> GoldAccent
                                                "RESIZED" -> Color(0xFF0284C7)
                                                "EXPORT" -> Color(0xFF059669)
                                                else -> Maroon500
                                            }
                                        )
                                        .padding(horizontal = 6.dp, vertical = 2.dp)
                                ) {
                                    Text(
                                        text = item.mediaType,
                                        color = if (item.mediaType == "AI") Maroon700 else Color.White,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 9.sp
                                    )
                                }

                                // Version Badge
                                Box(
                                    modifier = Modifier
                                        .padding(6.dp)
                                        .align(Alignment.TopEnd)
                                        .clip(RoundedCornerShape(6.dp))
                                        .background(Maroon700.copy(alpha = 0.85f))
                                        .padding(horizontal = 6.dp, vertical = 2.dp)
                                ) {
                                    Text(
                                        text = "V${item.versionNumber}",
                                        color = GoldAccent,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 9.sp
                                    )
                                }

                                Checkbox(
                                    checked = isSelected,
                                    onCheckedChange = { viewModel.toggleSelectMedia(item.id) },
                                    modifier = Modifier.align(Alignment.BottomEnd),
                                    colors = CheckboxDefaults.colors(checkedColor = GoldAccent, checkmarkColor = Maroon700)
                                )
                            }

                            Column(modifier = Modifier.padding(10.dp)) {
                                Text(
                                    text = item.productName,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 12.sp,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                                Text(
                                    text = "SKU: ${item.sku} • ${item.mediaId}",
                                    fontSize = 10.sp,
                                    color = Color.Gray,
                                    maxLines = 1
                                )

                                Spacer(modifier = Modifier.height(6.dp))

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "Views: ${item.viewCount} | Shared: ${item.shareCount}",
                                        fontSize = 10.sp,
                                        color = Maroon500,
                                        fontWeight = FontWeight.Bold
                                    )

                                    IconButton(
                                        onClick = { onSoftDelete(item) },
                                        modifier = Modifier.size(24.dp)
                                    ) {
                                        Icon(Icons.Default.Delete, contentDescription = "Delete", tint = Color.Gray, modifier = Modifier.size(16.dp))
                                    }
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
fun VersionTimelineTab(
    viewModel: MediaCommandCenterViewModel,
    allItems: List<MediaCommandCenterEntity>
) {
    val groupedByProduct = remember(allItems) {
        allItems.filter { !it.isDeleted }.groupBy { it.sku.ifBlank { "PROD-${it.productId}" } }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(groupedByProduct.entries.toList()) { entry ->
            val sku = entry.key
            val mediaList = entry.value.sortedBy { it.versionNumber }
            val firstItem = mediaList.first()

            Card(
                modifier = Modifier.fillMaxWidth(),
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
                            Text(text = firstItem.productName, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                            Text(text = "SKU: $sku • ${mediaList.size} Linked Versions", fontSize = 12.sp, color = Maroon500, fontWeight = FontWeight.SemiBold)
                        }
                        Icon(Icons.Default.History, contentDescription = null, tint = GoldAccent)
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Version Sequence
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        items(mediaList) { item ->
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Card(
                                    shape = RoundedCornerShape(8.dp),
                                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                                    modifier = Modifier.width(120.dp)
                                ) {
                                    Column(modifier = Modifier.padding(8.dp)) {
                                        Box(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .height(80.dp)
                                                .clip(RoundedCornerShape(6.dp))
                                                .background(Color.Gray)
                                        ) {
                                            AsyncImage(
                                                model = item.imageUri,
                                                contentDescription = null,
                                                modifier = Modifier.fillMaxSize(),
                                                contentScale = ContentScale.Crop
                                            )
                                            Box(
                                                modifier = Modifier
                                                    .align(Alignment.TopStart)
                                                    .padding(4.dp)
                                                    .background(GoldAccent)
                                                    .padding(horizontal = 4.dp, vertical = 2.dp)
                                            ) {
                                                Text("V${item.versionNumber}", fontSize = 9.sp, fontWeight = FontWeight.Bold, color = Maroon700)
                                            }
                                        }

                                        Spacer(modifier = Modifier.height(4.dp))
                                        Text(item.mediaType, fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Maroon500)
                                        Text(item.mediaSource, fontSize = 9.sp, color = Color.Gray, maxLines = 1)
                                    }
                                }

                                if (item != mediaList.last()) {
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text("➔", color = GoldAccent, fontWeight = FontWeight.Bold)
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
fun ComparisonModeTab(
    viewModel: MediaCommandCenterViewModel,
    allItems: List<MediaCommandCenterEntity>
) {
    val compA by viewModel.compareMediaA.collectAsState()
    val compB by viewModel.compareMediaB.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp)
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(modifier = Modifier.padding(12.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("MEDIA ASSET COMPARISON", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = Maroon500)
                    TextButton(onClick = {
                        viewModel.compareMediaA.value = null
                        viewModel.compareMediaB.value = null
                    }) {
                        Text("Reset Comparison")
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // Side A
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color.DarkGray),
                        contentAlignment = Alignment.Center
                    ) {
                        val sideA = compA
                        if (sideA != null) {
                            AsyncImage(
                                model = sideA.imageUri,
                                contentDescription = null,
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                            Box(
                                modifier = Modifier
                                    .align(Alignment.TopStart)
                                    .padding(6.dp)
                                    .background(GoldAccent)
                                    .padding(horizontal = 6.dp, vertical = 2.dp)
                            ) {
                                Text("A: ${sideA.mediaType} V${sideA.versionNumber}", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Maroon700)
                            }
                        } else {
                            Text("Tap 'Set A' below", color = Color.White, fontSize = 11.sp)
                        }
                    }

                    // Side B
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color.DarkGray),
                        contentAlignment = Alignment.Center
                    ) {
                        val sideB = compB
                        if (sideB != null) {
                            AsyncImage(
                                model = sideB.imageUri,
                                contentDescription = null,
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                            Box(
                                modifier = Modifier
                                    .align(Alignment.TopStart)
                                    .padding(6.dp)
                                    .background(GoldAccent)
                                    .padding(horizontal = 6.dp, vertical = 2.dp)
                            ) {
                                Text("B: ${sideB.mediaType} V${sideB.versionNumber}", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Maroon700)
                            }
                        } else {
                            Text("Tap 'Set B' below", color = Color.White, fontSize = 11.sp)
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text("SELECT MEDIA ASSETS TO COMPARE", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = Maroon500)
        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(allItems.filter { !it.isDeleted }) { item ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        AsyncImage(
                            model = item.imageUri,
                            contentDescription = null,
                            modifier = Modifier
                                .size(48.dp)
                                .clip(RoundedCornerShape(6.dp)),
                            contentScale = ContentScale.Crop
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(item.productName, fontWeight = FontWeight.Bold, fontSize = 13.sp, maxLines = 1)
                            Text("SKU: ${item.sku} • Type: ${item.mediaType} V${item.versionNumber}", fontSize = 11.sp, color = Color.Gray)
                        }

                        TextButton(onClick = { viewModel.compareMediaA.value = item }) {
                            Text("Set A", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }
                        TextButton(onClick = { viewModel.compareMediaB.value = item }) {
                            Text("Set B", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Maroon500)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MediaAnalyticsTab(
    viewModel: MediaCommandCenterViewModel,
    allItems: List<MediaCommandCenterEntity>
) {
    val totalViews = remember(allItems) { allItems.sumOf { it.viewCount } }
    val totalShares = remember(allItems) { allItems.sumOf { it.shareCount } }
    val totalDownloads = remember(allItems) { allItems.sumOf { it.downloadCount } }

    val topViewed = remember(allItems) { allItems.sortedByDescending { it.viewCount }.take(5) }
    val topShared = remember(allItems) { allItems.sortedByDescending { it.shareCount }.take(5) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text("COMMAND CENTER ANALYTICS", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = Maroon500)
            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                StatCard(modifier = Modifier.weight(1f), title = "Total Views", value = "$totalViews", icon = Icons.Default.Insights, accentColor = Maroon500)
                StatCard(modifier = Modifier.weight(1f), title = "Total Shares", value = "$totalShares", icon = Icons.Default.Share, accentColor = Color(0xFF0284C7))
                StatCard(modifier = Modifier.weight(1f), title = "Downloads", value = "$totalDownloads", icon = Icons.Default.Download, accentColor = Color(0xFF059669))
            }
        }

        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text("MOST VIEWED MEDIA ASSETS", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    Spacer(modifier = Modifier.height(10.dp))

                    topViewed.forEach { item ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            AsyncImage(
                                model = item.imageUri,
                                contentDescription = null,
                                modifier = Modifier
                                    .size(40.dp)
                                    .clip(RoundedCornerShape(6.dp)),
                                contentScale = ContentScale.Crop
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(item.productName, fontWeight = FontWeight.Bold, fontSize = 13.sp, maxLines = 1)
                                Text("SKU: ${item.sku} • Type: ${item.mediaType}", fontSize = 11.sp, color = Color.Gray)
                            }
                            Text("${item.viewCount} views", fontWeight = FontWeight.Bold, color = Maroon500, fontSize = 13.sp)
                        }
                    }
                }
            }
        }

        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text("MOST SHARED MEDIA ASSETS", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    Spacer(modifier = Modifier.height(10.dp))

                    topShared.forEach { item ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            AsyncImage(
                                model = item.imageUri,
                                contentDescription = null,
                                modifier = Modifier
                                    .size(40.dp)
                                    .clip(RoundedCornerShape(6.dp)),
                                contentScale = ContentScale.Crop
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(item.productName, fontWeight = FontWeight.Bold, fontSize = 13.sp, maxLines = 1)
                                Text("Source: ${item.mediaSource}", fontSize = 11.sp, color = Color.Gray)
                            }
                            Text("${item.shareCount} shares", fontWeight = FontWeight.Bold, color = Color(0xFF0284C7), fontSize = 13.sp)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MediaRecycleBinTab(
    viewModel: MediaCommandCenterViewModel,
    recycleBinItems: List<MediaCommandCenterEntity>,
    selectedIds: Set<Long>,
    onRestore: (MediaCommandCenterEntity) -> Unit,
    onPermanentDelete: (MediaCommandCenterEntity) -> Unit
) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Recycling, contentDescription = null, tint = Maroon500)
                Spacer(modifier = Modifier.width(8.dp))
                Text("MEDIA RECYCLE BIN", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = Maroon500)
            }

            if (selectedIds.isNotEmpty()) {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Button(
                        onClick = {
                            viewModel.bulkRestore()
                            Toast.makeText(context, "Restored selected assets", Toast.LENGTH_SHORT).show()
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = GoldAccent, contentColor = Maroon700)
                    ) {
                        Icon(Icons.Default.Restore, contentDescription = null, modifier = Modifier.size(14.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Restore Selected", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }

                    Button(
                        onClick = {
                            viewModel.bulkDeletePermanently()
                            Toast.makeText(context, "Permanently erased selected assets", Toast.LENGTH_SHORT).show()
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Red, contentColor = Color.White)
                    ) {
                        Icon(Icons.Default.DeleteForever, contentDescription = null, modifier = Modifier.size(14.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Erase Selected", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        if (recycleBinItems.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(Icons.Default.Recycling, contentDescription = null, modifier = Modifier.size(48.dp), tint = Color.Gray)
                    Spacer(modifier = Modifier.height(12.dp))
                    Text("Recycle Bin is empty.", color = Color.Gray, fontWeight = FontWeight.Bold)
                }
            }
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(recycleBinItems, key = { it.id }) { item ->
                    val isSelected = selectedIds.contains(item.id)

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Checkbox(
                                checked = isSelected,
                                onCheckedChange = { viewModel.toggleSelectMedia(item.id) },
                                colors = CheckboxDefaults.colors(checkedColor = GoldAccent, checkmarkColor = Maroon700)
                            )

                            AsyncImage(
                                model = item.imageUri,
                                contentDescription = null,
                                modifier = Modifier
                                    .size(50.dp)
                                    .clip(RoundedCornerShape(6.dp)),
                                contentScale = ContentScale.Crop
                            )

                            Spacer(modifier = Modifier.width(12.dp))

                            Column(modifier = Modifier.weight(1f)) {
                                Text(item.productName, fontWeight = FontWeight.Bold, fontSize = 13.sp, maxLines = 1)
                                Text("SKU: ${item.sku} • Type: ${item.mediaType}", fontSize = 11.sp, color = Color.Gray)
                            }

                            Row {
                                IconButton(onClick = { onRestore(item) }) {
                                    Icon(Icons.Default.Restore, contentDescription = "Restore", tint = Color(0xFF059669))
                                }
                                IconButton(onClick = { onPermanentDelete(item) }) {
                                    Icon(Icons.Default.DeleteForever, contentDescription = "Erase", tint = Color.Red)
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
fun MediaItemDetailDialog(
    mediaItem: MediaCommandCenterEntity,
    onDismiss: () -> Unit,
    onSetCover: () -> Unit,
    onArchive: () -> Unit,
    onDelete: () -> Unit,
    onShare: (String) -> Unit,
    onDownload: (String) -> Unit,
    onGotoProduct: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            Button(onClick = onDismiss, colors = ButtonDefaults.buttonColors(containerColor = Maroon500)) {
                Text("Close", fontWeight = FontWeight.Bold)
            }
        },
        title = {
            Text(text = "MEDIA ASSET DETAILS", fontWeight = FontWeight.Bold, fontSize = 15.sp, color = Maroon500)
        },
        text = {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color.Black)
                    ) {
                        AsyncImage(
                            model = mediaItem.imageUri,
                            contentDescription = mediaItem.productName,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Fit
                        )
                    }
                }

                item {
                    Column {
                        Text(text = mediaItem.productName, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                        Text(text = "SKU: ${mediaItem.sku} • QR: ${mediaItem.qrNumber}", fontSize = 12.sp, color = Color.Gray)
                        Text(text = "Media ID: ${mediaItem.mediaId}", fontSize = 11.sp, color = Maroon500, fontWeight = FontWeight.Bold)
                        Text(text = "Type: ${mediaItem.mediaType} | Source: ${mediaItem.mediaSource} | V${mediaItem.versionNumber}", fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
                        Text(text = "Resolution: ${mediaItem.width} x ${mediaItem.height}", fontSize = 11.sp, color = Color.Gray)
                    }
                }

                item {
                    Text("QUICK ACTIONS", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = Maroon500)
                    Spacer(modifier = Modifier.height(6.dp))

                    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        item {
                            Button(
                                onClick = onSetCover,
                                colors = ButtonDefaults.buttonColors(containerColor = GoldAccent, contentColor = Maroon700)
                            ) {
                                Icon(Icons.Default.Star, contentDescription = null, modifier = Modifier.size(14.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Set Main Cover", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                            }
                        }

                        item {
                            Button(
                                onClick = { onShare("WhatsApp") },
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF25D366))
                            ) {
                                Icon(Icons.Default.Share, contentDescription = null, modifier = Modifier.size(14.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("WhatsApp", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                            }
                        }

                        item {
                            Button(
                                onClick = { onDownload("Android Downloads") },
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0284C7))
                            ) {
                                Icon(Icons.Default.Download, contentDescription = null, modifier = Modifier.size(14.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Download", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                            }
                        }

                        item {
                            Button(
                                onClick = onArchive,
                                colors = ButtonDefaults.buttonColors(containerColor = Maroon700)
                            ) {
                                Icon(Icons.Default.Archive, contentDescription = null, modifier = Modifier.size(14.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Archive", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                            }
                        }

                        item {
                            Button(
                                onClick = onDelete,
                                colors = ButtonDefaults.buttonColors(containerColor = Maroon500)
                            ) {
                                Icon(Icons.Default.Delete, contentDescription = null, modifier = Modifier.size(14.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Delete", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                            }
                        }

                        item {
                            OutlinedButton(onClick = onGotoProduct) {
                                Text("Go to Product", fontSize = 11.sp, color = Maroon500)
                            }
                        }
                    }
                }
            }
        }
    )
}
