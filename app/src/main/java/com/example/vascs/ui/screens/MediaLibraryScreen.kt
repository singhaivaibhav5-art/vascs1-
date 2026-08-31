package com.example.vascs.ui.screens

import android.content.Intent
import android.net.Uri
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Crop
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.OpenInNew
import androidx.compose.material.icons.filled.PhotoLibrary
import androidx.compose.material.icons.filled.RadioButtonUnchecked
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import coil.compose.rememberAsyncImagePainter
import com.example.vascs.data.model.MediaLibraryEntity
import com.example.vascs.ui.theme.GoldAccent
import com.example.vascs.ui.theme.Maroon500
import com.example.vascs.ui.viewmodel.MediaLibraryViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun MediaLibraryScreen(
    viewModel: MediaLibraryViewModel,
    onGotoProduct: (productId: String) -> Unit
) {
    val context = LocalContext.current
    val searchQuery by viewModel.searchQuery.collectAsState()
    val activeFilter by viewModel.activeFilter.collectAsState()
    val filteredMediaItems by viewModel.filteredMediaItems.collectAsState()
    val selectedItemIds by viewModel.selectedItemIds.collectAsState()

    var previewItem by remember { mutableStateOf<MediaLibraryEntity?>(null) }
    var itemToDelete by remember { mutableStateOf<MediaLibraryEntity?>(null) }
    var showBatchDeleteConfirm by remember { mutableStateOf(false) }
    var resizeTargetItems by remember { mutableStateOf<List<MediaLibraryEntity>?>(null) }

    val tabs = listOf("ALL", "AI", "CAMERA", "GALLERY", "RESIZED")

    val activeResizeItems = resizeTargetItems
    if (activeResizeItems != null) {
        ImageResizeStudioScreen(
            itemsToResize = activeResizeItems,
            viewModel = viewModel,
            onBack = { resizeTargetItems = null },
            onComplete = {
                resizeTargetItems = null
                viewModel.clearSelection()
            }
        )
        return
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Selection Toolbar when items selected
        if (selectedItemIds.isNotEmpty()) {
            Surface(
                color = Maroon500,
                contentColor = Color.White,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        IconButton(onClick = { viewModel.clearSelection() }) {
                            Icon(Icons.Default.Close, contentDescription = "Clear Selection", tint = Color.White)
                        }
                        Text(
                            text = "${selectedItemIds.size} Selected",
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            fontSize = 16.sp
                        )
                    }

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        // Goto Product (if single selection or first item has productId)
                        val firstSelectedProduct = filteredMediaItems.firstOrNull { selectedItemIds.contains(it.id) }?.productId
                        if (!firstSelectedProduct.isNullOrBlank()) {
                            TextButton(
                                onClick = {
                                    onGotoProduct(firstSelectedProduct)
                                },
                                colors = ButtonDefaults.textButtonColors(contentColor = GoldAccent)
                            ) {
                                Icon(Icons.Default.OpenInNew, contentDescription = null, modifier = Modifier.size(18.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Goto Product", fontWeight = FontWeight.Bold)
                            }
                        }

                        // Resize Button
                        TextButton(
                            onClick = {
                                 val selectedList = filteredMediaItems.filter { selectedItemIds.contains(it.id) }
                                 if (selectedList.isNotEmpty()) {
                                     resizeTargetItems = selectedList
                                 }
                            },
                            enabled = selectedItemIds.isNotEmpty(),
                            colors = ButtonDefaults.textButtonColors(
                                contentColor = GoldAccent,
                                disabledContentColor = Color.LightGray
                            )
                        ) {
                            Icon(Icons.Default.Crop, contentDescription = null, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Resize", fontWeight = FontWeight.Bold)
                        }

                        // Delete
                        IconButton(onClick = { showBatchDeleteConfirm = true }) {
                            Icon(Icons.Default.Delete, contentDescription = "Delete Selected", tint = Color(0xFFFF6B6B))
                        }
                    }
                }
            }
        }

        // Search Bar
        PaddingBox {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { viewModel.searchQuery.value = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Search by Product ID, SKU, QR Number...") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                trailingIcon = {
                    if (searchQuery.isNotEmpty()) {
                        IconButton(onClick = { viewModel.searchQuery.value = "" }) {
                            Icon(Icons.Default.Close, contentDescription = "Clear search")
                        }
                    }
                },
                singleLine = true,
                shape = RoundedCornerShape(12.dp)
            )
        }

        // Filter Tabs
        ScrollableTabRow(
            selectedTabIndex = tabs.indexOf(activeFilter).coerceAtLeast(0),
            edgePadding = 16.dp,
            containerColor = Color.Transparent,
            indicator = {},
            divider = {}
        ) {
            tabs.forEach { filterName ->
                val isSelected = activeFilter == filterName
                FilterChip(
                    selected = isSelected,
                    onClick = { viewModel.activeFilter.value = filterName },
                    label = {
                        Text(
                            text = filterName,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                        )
                    },
                    modifier = Modifier.padding(end = 8.dp),
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = Maroon500,
                        selectedLabelColor = Color.White
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Total Count Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "${filteredMediaItems.size} Media Item(s)",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray,
                fontWeight = FontWeight.SemiBold
            )

            if (filteredMediaItems.isNotEmpty()) {
                TextButton(
                    onClick = {
                        if (selectedItemIds.size == filteredMediaItems.size) {
                            viewModel.clearSelection()
                        } else {
                            viewModel.selectAll()
                        }
                    }
                ) {
                    Text(
                        text = if (selectedItemIds.size == filteredMediaItems.size) "Deselect All" else "Select All",
                        fontSize = 12.sp
                    )
                }
            }
        }

        // Media Item List
        if (filteredMediaItems.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(Icons.Default.Image, contentDescription = null, modifier = Modifier.size(64.dp), tint = Color.LightGray)
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "No media items found",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Gray
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Import images via the Photo Upload Center or capture photos using the camera.",
                        fontSize = 14.sp,
                        color = Color.Gray,
                        textAlign = TextAlign.Center
                    )
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(filteredMediaItems, key = { it.id }) { media ->
                    val isSelected = selectedItemIds.contains(media.id)
                    MediaItemRow(
                        media = media,
                        isSelected = isSelected,
                        onToggleSelect = { viewModel.toggleSelection(media.id) },
                        onView = { previewItem = media },
                        onDelete = { itemToDelete = media },
                        onSetPrimary = { viewModel.setPrimaryCover(media) },
                        onGotoProduct = {
                            if (!media.productId.isNullOrBlank()) {
                                onGotoProduct(media.productId)
                            } else {
                                Toast.makeText(context, "No product linked to this image", Toast.LENGTH_SHORT).show()
                            }
                        }
                    )
                }
                item {
                    Spacer(modifier = Modifier.height(24.dp))
                }
            }
        }
    }

    // Single Delete Dialog
    itemToDelete?.let { media ->
        AlertDialog(
            onDismissRequest = { itemToDelete = null },
            title = { Text("Delete Image?") },
            text = { Text("This action cannot be undone.") },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.deleteMedia(media)
                        itemToDelete = null
                        Toast.makeText(context, "Image deleted", Toast.LENGTH_SHORT).show()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                ) {
                    Text("YES")
                }
            },
            dismissButton = {
                TextButton(onClick = { itemToDelete = null }) {
                    Text("NO")
                }
            }
        )
    }

    // Batch Delete Dialog
    if (showBatchDeleteConfirm) {
        AlertDialog(
            onDismissRequest = { showBatchDeleteConfirm = false },
            title = { Text("Delete Image?") },
            text = { Text("This action cannot be undone. Selected: ${selectedItemIds.size} image(s).") },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.deleteSelectedMedia()
                        showBatchDeleteConfirm = false
                        Toast.makeText(context, "Selected images deleted", Toast.LENGTH_SHORT).show()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                ) {
                    Text("YES")
                }
            },
            dismissButton = {
                TextButton(onClick = { showBatchDeleteConfirm = false }) {
                    Text("NO")
                }
            }
        )
    }

    // Full Screen Preview Dialog
    previewItem?.let { media ->
        Dialog(
            onDismissRequest = { previewItem = null },
            properties = DialogProperties(usePlatformDefaultWidth = false)
        ) {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = Color.Black
            ) {
                Box(modifier = Modifier.fillMaxSize()) {
                    Image(
                        painter = rememberAsyncImagePainter(media.imageUri),
                        contentDescription = "Full Screen Preview",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Fit
                    )

                    // Top Bar
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .align(Alignment.TopCenter),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(
                            onClick = { previewItem = null },
                            modifier = Modifier
                                .background(Color.Black.copy(alpha = 0.6f), CircleShape)
                        ) {
                            Icon(Icons.Default.Close, contentDescription = "Close", tint = Color.White)
                        }

                        Row {
                            IconButton(
                                onClick = {
                                    val shareIntent = Intent(Intent.ACTION_SEND).apply {
                                        type = "image/*"
                                        putExtra(Intent.EXTRA_STREAM, Uri.parse(media.imageUri))
                                        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                                    }
                                    context.startActivity(Intent.createChooser(shareIntent, "Share Image"))
                                },
                                modifier = Modifier
                                    .background(Color.Black.copy(alpha = 0.6f), CircleShape)
                            ) {
                                Icon(Icons.Default.Share, contentDescription = "Share", tint = Color.White)
                            }

                            Spacer(modifier = Modifier.width(8.dp))

                            if (!media.productId.isNullOrBlank()) {
                                IconButton(
                                    onClick = {
                                        previewItem = null
                                        onGotoProduct(media.productId)
                                    },
                                    modifier = Modifier
                                        .background(Color.Black.copy(alpha = 0.6f), CircleShape)
                                ) {
                                    Icon(Icons.Default.OpenInNew, contentDescription = "Goto Product", tint = GoldAccent)
                                }
                            }
                        }
                    }

                    // Bottom Bar
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .align(Alignment.BottomCenter),
                        colors = CardDefaults.cardColors(containerColor = Color.Black.copy(alpha = 0.85f)),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = "Source: ${media.imageSource} | Type: ${media.imageType}",
                                color = GoldAccent,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Dimensions: ${media.width} x ${media.height} px",
                                color = Color.White,
                                fontSize = 13.sp
                            )
                            if (!media.sku.isNullOrBlank()) {
                                Text(text = "SKU: ${media.sku}", color = Color.LightGray, fontSize = 12.sp)
                            }
                            Spacer(modifier = Modifier.height(12.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Button(
                                    onClick = {
                                        viewModel.setPrimaryCover(media)
                                        Toast.makeText(context, "Set as main product cover!", Toast.LENGTH_SHORT).show()
                                    },
                                    colors = ButtonDefaults.buttonColors(containerColor = GoldAccent, contentColor = Color.Black)
                                ) {
                                    Icon(Icons.Default.Star, contentDescription = null, modifier = Modifier.size(16.dp))
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text("Use As Main Cover", fontWeight = FontWeight.Bold)
                                }

                                Button(
                                    onClick = {
                                        val pItem = media
                                        previewItem = null
                                        resizeTargetItems = listOf(pItem)
                                    },
                                    colors = ButtonDefaults.buttonColors(containerColor = Maroon500, contentColor = GoldAccent)
                                ) {
                                    Icon(Icons.Default.Crop, contentDescription = null, modifier = Modifier.size(16.dp))
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text("Resize", fontWeight = FontWeight.Bold)
                                }

                                if (media.isPrimary) {
                                    Text("★ PRIMARY COVER", color = GoldAccent, fontSize = 12.sp, fontWeight = FontWeight.Bold)
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
fun MediaItemRow(
    media: MediaLibraryEntity,
    isSelected: Boolean,
    onToggleSelect: () -> Unit,
    onView: () -> Unit,
    onDelete: () -> Unit,
    onSetPrimary: () -> Unit,
    onGotoProduct: () -> Unit
) {
    val dateFormat = remember { SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.getDefault()) }
    val formattedDate = remember(media.createdDate) { dateFormat.format(Date(media.createdDate)) }

    val sourceColor = when (media.imageSource.uppercase()) {
        "AI" -> Color(0xFF0284C7)
        "CAMERA" -> Maroon500
        "GALLERY" -> Color(0xFF059669)
        "RESIZED" -> Color(0xFFD97706)
        else -> Color.Gray
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onToggleSelect() },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) Maroon500.copy(alpha = 0.08f) else MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Checkbox
            Checkbox(
                checked = isSelected,
                onCheckedChange = { onToggleSelect() },
                colors = CheckboxDefaults.colors(checkedColor = Maroon500)
            )

            Spacer(modifier = Modifier.width(8.dp))

            // Thumbnail
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .border(1.dp, Color.LightGray, RoundedCornerShape(8.dp))
            ) {
                Image(
                    painter = rememberAsyncImagePainter(media.imageUri),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )

                if (media.isPrimary) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .background(GoldAccent, RoundedCornerShape(bottomStart = 6.dp))
                            .padding(2.dp)
                    ) {
                        Icon(Icons.Default.Star, contentDescription = "Primary", tint = Color.Black, modifier = Modifier.size(12.dp))
                    }
                }
            }

            Spacer(modifier = Modifier.width(12.dp))

            // Info Column
            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .background(sourceColor.copy(alpha = 0.15f), RoundedCornerShape(4.dp))
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = media.imageSource,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = sourceColor
                        )
                    }
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = media.imageType,
                        fontSize = 10.sp,
                        color = Color.Gray
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = if (!media.sku.isNullOrBlank()) "SKU: ${media.sku}" else "ID: ${media.productId ?: "Unlinked"}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                if (!media.qrNumber.isNullOrBlank()) {
                    Text(
                        text = "QR: ${media.qrNumber}",
                        fontSize = 12.sp,
                        color = Color.DarkGray
                    )
                }

                Text(
                    text = "${media.width}x${media.height} • $formattedDate",
                    fontSize = 11.sp,
                    color = Color.Gray
                )
            }

            // Action Buttons
            Row {
                IconButton(onClick = onView) {
                    Icon(Icons.Default.Visibility, contentDescription = "View", tint = MaterialTheme.colorScheme.primary)
                }
                IconButton(onClick = onDelete) {
                    Icon(Icons.Default.Delete, contentDescription = "Delete", tint = Color(0xFFDC2626))
                }
            }
        }
    }
}

@Composable
private fun PaddingBox(content: @Composable () -> Unit) {
    Box(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
        content()
    }
}
