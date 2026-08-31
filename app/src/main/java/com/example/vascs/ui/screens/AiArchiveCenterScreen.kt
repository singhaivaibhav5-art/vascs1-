package com.example.vascs.ui.screens

import android.content.Intent
import android.widget.Toast
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
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Compare
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.DeleteForever
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Insights
import androidx.compose.material.icons.filled.QrCode
import androidx.compose.material.icons.filled.Recycling
import androidx.compose.material.icons.filled.Restore
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
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
import com.example.vascs.data.model.AiImageArchiveEntity
import com.example.vascs.ui.theme.GoldAccent
import com.example.vascs.ui.theme.Maroon500
import com.example.vascs.ui.theme.Maroon700
import com.example.vascs.ui.viewmodel.AiArchiveViewModel
import com.example.vascs.ui.viewmodel.FolderGrouping

@Composable
fun AiArchiveCenterScreen(
    viewModel: AiArchiveViewModel,
    onGotoProduct: ((Long) -> Unit)? = null
) {
    val context = LocalContext.current
    var selectedTab by remember { mutableStateOf(0) }
    val tabs = listOf("ARCHIVE GALLERY", "VERSIONS & COMPARE", "ANALYTICS", "RECYCLE BIN")

    val filteredArchives by viewModel.filteredArchives.collectAsState()
    val recycleBinArchives by viewModel.rawRecycleBin.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val sourceFilter by viewModel.selectedSourceFilter.collectAsState()
    val providerFilter by viewModel.selectedProviderFilter.collectAsState()
    val selectedFolderGrouping by viewModel.folderGrouping.collectAsState()
    val selectedIds by viewModel.selectedArchiveIds.collectAsState()

    var previewArchiveItem by remember { mutableStateOf<AiImageArchiveEntity?>(null) }
    var itemToDelete by remember { mutableStateOf<AiImageArchiveEntity?>(null) }
    var isPermanentDelete by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Header Banner
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
                                Icons.Default.AutoAwesome,
                                contentDescription = null,
                                tint = Maroon700,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(
                                text = "VASCS AI ARCHIVE CENTER",
                                color = GoldAccent,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 1.sp
                            )
                            Text(
                                text = "AI Photo Lifecycle & Version Vault",
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
                            text = "${filteredArchives.size} ACTIVE VAULT ITEMS",
                            color = GoldAccent,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                        )
                    }
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

        // Tab Contents
        Box(modifier = Modifier.weight(1f)) {
            when (selectedTab) {
                0 -> ArchiveGalleryTab(
                    viewModel = viewModel,
                    filteredArchives = filteredArchives,
                    selectedIds = selectedIds,
                    searchQuery = searchQuery,
                    sourceFilter = sourceFilter,
                    providerFilter = providerFilter,
                    selectedFolderGrouping = selectedFolderGrouping,
                    onPreviewItem = { previewArchiveItem = it },
                    onSoftDelete = {
                        itemToDelete = it
                        isPermanentDelete = false
                    }
                )

                1 -> VersionHistoryAndCompareTab(
                    viewModel = viewModel,
                    archives = filteredArchives
                )

                2 -> AnalyticsDashboardTab(
                    viewModel = viewModel,
                    archives = filteredArchives
                )

                3 -> RecycleBinTab(
                    viewModel = viewModel,
                    recycleBinItems = recycleBinArchives,
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

    // Image Full Preview Dialog
    previewArchiveItem?.let { archiveItem ->
        ArchiveItemDetailDialog(
            archiveItem = archiveItem,
            onDismiss = { previewArchiveItem = null },
            onSetMainCover = {
                viewModel.setAsMainCover(archiveItem)
                Toast.makeText(context, "Set as main cover for ${archiveItem.productName}", Toast.LENGTH_SHORT).show()
                previewArchiveItem = null
            },
            onShareSocial = { platform ->
                viewModel.incrementShare(archiveItem.id)
                val shareIntent = Intent(Intent.ACTION_SEND).apply {
                    type = "text/plain"
                    putExtra(Intent.EXTRA_SUBJECT, "VASCS Saree Entry: ${archiveItem.productName}")
                    putExtra(
                        Intent.EXTRA_TEXT,
                        "Check out this saree entry from VASCS Catalogue!\n\n" +
                                "Product: ${archiveItem.productName}\n" +
                                "SKU: ${archiveItem.sku}\n" +
                                "QR Code: ${archiveItem.qrNumber}\n" +
                                "Version: V${archiveItem.versionNumber}\n" +
                                "Provider: ${archiveItem.providerName}\n" +
                                "Image Link: ${archiveItem.imageUri}"
                    )
                }
                context.startActivity(Intent.createChooser(shareIntent, "Share via $platform"))
            },
            onGotoProduct = {
                if (archiveItem.productId > 0) {
                    onGotoProduct?.invoke(archiveItem.productId)
                    previewArchiveItem = null
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
                        "Are you sure you want to permanently erase version V${item.versionNumber} of '${item.productName}'? This action cannot be undone."
                    } else {
                        "Move version V${item.versionNumber} of '${item.productName}' to AI Recycle Bin? You can restore it anytime."
                    }
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (isPermanentDelete) {
                            viewModel.deletePermanently(item.id)
                            Toast.makeText(context, "Permanently deleted archive record", Toast.LENGTH_SHORT).show()
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
fun ArchiveGalleryTab(
    viewModel: AiArchiveViewModel,
    filteredArchives: List<AiImageArchiveEntity>,
    selectedIds: Set<Long>,
    searchQuery: String,
    sourceFilter: String,
    providerFilter: String,
    selectedFolderGrouping: FolderGrouping,
    onPreviewItem: (AiImageArchiveEntity) -> Unit,
    onSoftDelete: (AiImageArchiveEntity) -> Unit
) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp)
    ) {
        // Search & Smart Filters
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { viewModel.searchQuery.value = it },
            placeholder = { Text("Search by Product, SKU, QR, Prompt or Archive ID...") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            shape = RoundedCornerShape(12.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Source Filters Row
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(Icons.Default.FilterList, contentDescription = null, modifier = Modifier.size(16.dp), tint = Maroon500)
            Spacer(modifier = Modifier.width(6.dp))
            Text("SOURCE:", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Maroon500)
            Spacer(modifier = Modifier.width(8.dp))

            val sources = listOf("ALL", "AI", "CAMERA", "GALLERY", "RESIZED", "IMPORTED")
            LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                items(sources) { src ->
                    val isSel = sourceFilter.equals(src, ignoreCase = true)
                    Card(
                        onClick = { viewModel.selectedSourceFilter.value = src },
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = if (isSel) Maroon500 else MaterialTheme.colorScheme.surfaceVariant
                        )
                    ) {
                        Text(
                            text = src,
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

        // Provider Filters Row
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(Icons.Default.AutoAwesome, contentDescription = null, modifier = Modifier.size(16.dp), tint = Maroon500)
            Spacer(modifier = Modifier.width(6.dp))
            Text("PROVIDER:", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Maroon500)
            Spacer(modifier = Modifier.width(8.dp))

            val providers = listOf("ALL", "NANO_BANANA", "GOOGLE_AI_STUDIO", "OOTDIFFUSION", "COMFYUI", "STABLE_DIFFUSION", "MANUAL_IMPORT")
            LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                items(providers) { prov ->
                    val isSel = providerFilter.equals(prov, ignoreCase = true)
                    Card(
                        onClick = { viewModel.selectedProviderFilter.value = prov },
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = if (isSel) GoldAccent else MaterialTheme.colorScheme.surfaceVariant
                        )
                    ) {
                        Text(
                            text = prov.replace("_", " "),
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (isSel) Maroon700 else MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Folder View Switcher
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Folder, contentDescription = null, modifier = Modifier.size(16.dp), tint = Color.Gray)
                Spacer(modifier = Modifier.width(4.dp))
                Text("FOLDER VIEW:", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color.Gray)
            }

            LazyRow(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                items(FolderGrouping.entries) { group ->
                    val isSel = selectedFolderGrouping == group
                    TextButton(
                        onClick = { viewModel.folderGrouping.value = group },
                        contentPadding = PaddingValues(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = group.name,
                            fontSize = 11.sp,
                            fontWeight = if (isSel) FontWeight.Bold else FontWeight.Normal,
                            color = if (isSel) Maroon500 else Color.Gray
                        )
                    }
                }
            }
        }

        // Bulk Selection Bar
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
                        text = "${selectedIds.size} ITEMS SELECTED",
                        fontWeight = FontWeight.Bold,
                        color = Maroon700,
                        fontSize = 12.sp
                    )

                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Button(
                            onClick = {
                                viewModel.bulkSoftDelete()
                                Toast.makeText(context, "Moved selected items to Recycle Bin", Toast.LENGTH_SHORT).show()
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Maroon500, contentColor = Color.White)
                        ) {
                            Icon(Icons.Default.Delete, contentDescription = null, modifier = Modifier.size(14.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Recycle", fontSize = 11.sp)
                        }

                        OutlinedButton(onClick = { viewModel.clearSelection() }) {
                            Text("Clear", fontSize = 11.sp, color = Maroon700)
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(4.dp))

        // Grid View
        if (filteredArchives.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(Icons.Default.Image, contentDescription = null, modifier = Modifier.size(48.dp), tint = Color.Gray)
                    Spacer(modifier = Modifier.height(12.dp))
                    Text("No AI Archive records match current filters.", color = Color.Gray, fontWeight = FontWeight.Bold)
                }
            }
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(filteredArchives, key = { it.id }) { item ->
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
                                    .height(150.dp)
                                    .background(Color.LightGray)
                            ) {
                                AsyncImage(
                                    model = item.imageUri,
                                    contentDescription = item.productName,
                                    modifier = Modifier.fillMaxSize(),
                                    contentScale = ContentScale.Crop
                                )

                                // Version Badge
                                Box(
                                    modifier = Modifier
                                        .padding(6.dp)
                                        .align(Alignment.TopStart)
                                        .clip(RoundedCornerShape(6.dp))
                                        .background(GoldAccent)
                                        .padding(horizontal = 6.dp, vertical = 2.dp)
                                ) {
                                    Text(
                                        text = "V${item.versionNumber}",
                                        color = Maroon700,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 10.sp
                                    )
                                }

                                // Provider Badge
                                Box(
                                    modifier = Modifier
                                        .padding(6.dp)
                                        .align(Alignment.TopEnd)
                                        .clip(RoundedCornerShape(6.dp))
                                        .background(Maroon500.copy(alpha = 0.85f))
                                        .padding(horizontal = 6.dp, vertical = 2.dp)
                                ) {
                                    Text(
                                        text = item.providerName.replace("_", " "),
                                        color = Color.White,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 9.sp
                                    )
                                }

                                // Selection Checkbox
                                Checkbox(
                                    checked = isSelected,
                                    onCheckedChange = { viewModel.toggleSelectArchiveId(item.id) },
                                    modifier = Modifier.align(Alignment.BottomEnd),
                                    colors = CheckboxDefaults.colors(
                                        checkedColor = GoldAccent,
                                        checkmarkColor = Maroon700
                                    )
                                )
                            }

                            Column(modifier = Modifier.padding(10.dp)) {
                                Text(
                                    text = item.productName,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 13.sp,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                                Text(
                                    text = "SKU: ${item.sku} • ${item.archiveId}",
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
                                        text = "Used: ${item.usageCount} | Shared: ${item.shareCount}",
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Maroon500
                                    )

                                    IconButton(
                                        onClick = { onSoftDelete(item) },
                                        modifier = Modifier.size(24.dp)
                                    ) {
                                        Icon(
                                            Icons.Default.Delete,
                                            contentDescription = "Delete",
                                            tint = Color.Gray,
                                            modifier = Modifier.size(16.dp)
                                        )
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
fun VersionHistoryAndCompareTab(
    viewModel: AiArchiveViewModel,
    archives: List<AiImageArchiveEntity>
) {
    val groupedBySku = remember(archives) {
        archives.groupBy { it.sku.ifBlank { "PROD-${it.productId}" } }
    }

    val compA by viewModel.compareVersionA.collectAsState()
    val compB by viewModel.compareVersionB.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp)
    ) {
        // Comparison Header Box
        if (compA != null || compB != null) {
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
                        Text("COMPARE AI VERSIONS SIDE-BY-SIDE", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = Maroon500)
                        TextButton(onClick = {
                            viewModel.compareVersionA.value = null
                            viewModel.compareVersionB.value = null
                        }) {
                            Text("Reset Compare")
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(180.dp),
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
                                    Text("VERSION A: V${sideA.versionNumber}", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Maroon700)
                                }
                            } else {
                                Text("Select Version A below", color = Color.White, fontSize = 11.sp)
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
                                    Text("VERSION B: V${sideB.versionNumber}", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Maroon700)
                                }
                            } else {
                                Text("Select Version B below", color = Color.White, fontSize = 11.sp)
                            }
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
        }

        // Grouped Version History
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(groupedBySku.entries.toList()) { entry ->
                val sku = entry.key
                val versionList = entry.value.sortedByDescending { it.versionNumber }
                val firstItem = versionList.first()

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
                                Text(
                                    text = firstItem.productName,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 15.sp
                                )
                                Text(
                                    text = "SKU: $sku • ${versionList.size} Saved Versions",
                                    fontSize = 12.sp,
                                    color = Maroon500,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                            Icon(Icons.Default.History, contentDescription = null, tint = GoldAccent)
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        LazyRow(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                            items(versionList) { versionItem ->
                                Card(
                                    shape = RoundedCornerShape(8.dp),
                                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                                    modifier = Modifier.width(130.dp)
                                ) {
                                    Column(modifier = Modifier.padding(8.dp)) {
                                        Box(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .height(90.dp)
                                                .clip(RoundedCornerShape(6.dp))
                                                .background(Color.Gray)
                                        ) {
                                            AsyncImage(
                                                model = versionItem.imageUri,
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
                                                Text("V${versionItem.versionNumber}", fontSize = 9.sp, fontWeight = FontWeight.Bold, color = Maroon700)
                                            }
                                        }

                                        Spacer(modifier = Modifier.height(6.dp))
                                        Text(versionItem.providerName.replace("_", " "), fontSize = 10.sp, fontWeight = FontWeight.Bold, maxLines = 1)
                                        Text(versionItem.modelName, fontSize = 9.sp, color = Color.Gray, maxLines = 1)

                                        Spacer(modifier = Modifier.height(6.dp))

                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceBetween
                                        ) {
                                            TextButton(
                                                onClick = { viewModel.compareVersionA.value = versionItem },
                                                contentPadding = PaddingValues(0.dp)
                                            ) {
                                                Text("Set A", fontSize = 10.sp, fontWeight = FontWeight.Bold)
                                            }
                                            TextButton(
                                                onClick = { viewModel.compareVersionB.value = versionItem },
                                                contentPadding = PaddingValues(0.dp)
                                            ) {
                                                Text("Set B", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Maroon500)
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
    }
}

@Composable
fun AnalyticsDashboardTab(
    viewModel: AiArchiveViewModel,
    archives: List<AiImageArchiveEntity>
) {
    val totalUsage = remember(archives) { archives.sumOf { it.usageCount } }
    val totalShares = remember(archives) { archives.sumOf { it.shareCount } }
    val totalDownloads = remember(archives) { archives.sumOf { it.downloadCount } }
    val totalCovers = remember(archives) { archives.sumOf { it.coverAppliedCount } }

    val topUsed = remember(archives) { archives.sortedByDescending { it.usageCount }.take(5) }
    val topShared = remember(archives) { archives.sortedByDescending { it.shareCount }.take(5) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Analytics Summary Cards
        item {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text("LIFECYCLE ANALYTICS METRICS", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = Maroon500)

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    StatCard(
                        modifier = Modifier.weight(1f),
                        title = "Total Views & Usage",
                        value = "$totalUsage",
                        icon = Icons.Default.Insights,
                        accentColor = Maroon500
                    )
                    StatCard(
                        modifier = Modifier.weight(1f),
                        title = "Social Shares",
                        value = "$totalShares",
                        icon = Icons.Default.Share,
                        accentColor = Color(0xFF0284C7)
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    StatCard(
                        modifier = Modifier.weight(1f),
                        title = "Downloads",
                        value = "$totalDownloads",
                        icon = Icons.Default.Download,
                        accentColor = Color(0xFF059669)
                    )
                    StatCard(
                        modifier = Modifier.weight(1f),
                        title = "Covers Applied",
                        value = "$totalCovers",
                        icon = Icons.Default.Star,
                        accentColor = Color(0xFFD97706)
                    )
                }
            }
        }

        // Top Used AI Photos
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text("MOST ENGAGED AI PHOTOS", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    Spacer(modifier = Modifier.height(10.dp))

                    topUsed.forEach { item ->
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
                                Text("SKU: ${item.sku} • V${item.versionNumber}", fontSize = 11.sp, color = Color.Gray)
                            }
                            Text("${item.usageCount} views", fontWeight = FontWeight.Bold, color = Maroon500, fontSize = 13.sp)
                        }
                    }
                }
            }
        }

        // Most Shared
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text("MOST SHARED SOCIAL MEDIA ASSETS", fontWeight = FontWeight.Bold, fontSize = 14.sp)
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
                                Text("Provider: ${item.providerName}", fontSize = 11.sp, color = Color.Gray)
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
fun RecycleBinTab(
    viewModel: AiArchiveViewModel,
    recycleBinItems: List<AiImageArchiveEntity>,
    selectedIds: Set<Long>,
    onRestore: (AiImageArchiveEntity) -> Unit,
    onPermanentDelete: (AiImageArchiveEntity) -> Unit
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
                Text("AI RECYCLE BIN", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = Maroon500)
            }

            if (selectedIds.isNotEmpty()) {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Button(
                        onClick = {
                            viewModel.bulkRestore()
                            Toast.makeText(context, "Restored selected items to Archive", Toast.LENGTH_SHORT).show()
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
                            Toast.makeText(context, "Permanently erased selected items", Toast.LENGTH_SHORT).show()
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Red, contentColor = Color.White)
                    ) {
                        Icon(Icons.Default.DeleteForever, contentDescription = null, modifier = Modifier.size(14.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Erase All", fontSize = 11.sp, fontWeight = FontWeight.Bold)
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
                    Text("Recycle Bin is empty. Soft-deleted items will appear here.", color = Color.Gray, fontWeight = FontWeight.Bold)
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
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                    ) {
                        Row(
                            modifier = Modifier.padding(10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Checkbox(
                                checked = isSelected,
                                onCheckedChange = { viewModel.toggleSelectArchiveId(item.id) },
                                colors = CheckboxDefaults.colors(checkedColor = Maroon500)
                            )

                            AsyncImage(
                                model = item.imageUri,
                                contentDescription = null,
                                modifier = Modifier
                                    .size(60.dp)
                                    .clip(RoundedCornerShape(8.dp)),
                                contentScale = ContentScale.Crop
                            )

                            Spacer(modifier = Modifier.width(12.dp))

                            Column(modifier = Modifier.weight(1f)) {
                                Text(item.productName, fontWeight = FontWeight.Bold, fontSize = 14.sp, maxLines = 1)
                                Text("SKU: ${item.sku} • V${item.versionNumber}", fontSize = 11.sp, color = Color.Gray)
                                Text("Archive ID: ${item.archiveId}", fontSize = 10.sp, color = Maroon500)
                            }

                            Row {
                                IconButton(onClick = { onRestore(item) }) {
                                    Icon(Icons.Default.Restore, contentDescription = "Restore", tint = GoldAccent)
                                }
                                IconButton(onClick = { onPermanentDelete(item) }) {
                                    Icon(Icons.Default.DeleteForever, contentDescription = "Delete Permanently", tint = Color.Red)
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
fun ArchiveItemDetailDialog(
    archiveItem: AiImageArchiveEntity,
    onDismiss: () -> Unit,
    onSetMainCover: () -> Unit,
    onShareSocial: (String) -> Unit,
    onGotoProduct: () -> Unit
) {
    androidx.compose.ui.window.Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(archiveItem.productName, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                        Text("SKU: ${archiveItem.sku} • Version V${archiveItem.versionNumber}", fontSize = 12.sp, color = Maroon500)
                    }
                    IconButton(onClick = onDismiss) {
                        Icon(Icons.Default.Delete, contentDescription = "Close")
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Image
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(240.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color.Black)
                ) {
                    AsyncImage(
                        model = archiveItem.imageUri,
                        contentDescription = archiveItem.productName,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Fit
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Metadata Card
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text("ARCHIVE METADATA", fontWeight = FontWeight.Bold, fontSize = 11.sp, color = Maroon500)
                        DetailRow("Archive ID", archiveItem.archiveId)
                        DetailRow("Provider", archiveItem.providerName)
                        DetailRow("AI Model", archiveItem.modelName)
                        DetailRow("Resolution", "${archiveItem.width} x ${archiveItem.height}")
                        if (archiveItem.prompt.isNotBlank()) {
                            DetailRow("Prompt", archiveItem.prompt)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Action Buttons
                Button(
                    onClick = onSetMainCover,
                    colors = ButtonDefaults.buttonColors(containerColor = GoldAccent, contentColor = Maroon700),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(Icons.Default.Star, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Use As Main Product Cover", fontWeight = FontWeight.Bold)
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = { onShareSocial("WhatsApp") },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF25D366), contentColor = Color.White),
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(Icons.Default.Share, contentDescription = null, modifier = Modifier.size(14.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("WhatsApp", fontSize = 11.sp)
                    }

                    Button(
                        onClick = { onShareSocial("Instagram") },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE4405F), contentColor = Color.White),
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(Icons.Default.Share, contentDescription = null, modifier = Modifier.size(14.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Instagram", fontSize = 11.sp)
                    }

                    if (archiveItem.productId > 0) {
                        OutlinedButton(
                            onClick = onGotoProduct,
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Product", fontSize = 11.sp)
                        }
                    }
                }
            }
        }
    }
}
