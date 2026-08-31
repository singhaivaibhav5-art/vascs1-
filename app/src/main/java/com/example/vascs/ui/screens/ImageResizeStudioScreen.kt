package com.example.vascs.ui.screens

import android.widget.Toast
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Crop
import androidx.compose.material.icons.filled.FitScreen
import androidx.compose.material.icons.filled.PhotoSizeSelectLarge
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.vascs.data.model.MediaLibraryEntity
import com.example.vascs.ui.theme.GoldAccent
import com.example.vascs.ui.theme.Maroon500
import com.example.vascs.ui.theme.Maroon700
import com.example.vascs.ui.viewmodel.MediaLibraryViewModel
import com.example.vascs.util.FitMode
import com.example.vascs.util.ResizePreset

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImageResizeStudioScreen(
    itemsToResize: List<MediaLibraryEntity>,
    viewModel: MediaLibraryViewModel,
    onBack: () -> Unit,
    onComplete: () -> Unit = onBack
) {
    val context = LocalContext.current
    var selectedPreset by remember { mutableStateOf(ResizePreset.SQUARE) }
    var fitMode by remember { mutableStateOf(FitMode.CENTER_CROP) }
    
    var customWidthText by remember { mutableStateOf(selectedPreset.width.toString()) }
    var customHeightText by remember { mutableStateOf(selectedPreset.height.toString()) }
    var isCustomResolution by remember { mutableStateOf(false) }

    var isProcessing by remember { mutableStateOf(false) }
    var processMessage by remember { mutableStateOf("") }

    val activeWidth = if (isCustomResolution) (customWidthText.toIntOrNull() ?: 1080) else selectedPreset.width
    val activeHeight = if (isCustomResolution) (customHeightText.toIntOrNull() ?: 1080) else selectedPreset.height

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = "IMAGE RESIZE STUDIO",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = GoldAccent
                        )
                        Text(
                            text = "${itemsToResize.size} image(s) selected for processing",
                            fontSize = 12.sp,
                            color = GoldAccent.copy(alpha = 0.8f)
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = GoldAccent
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Maroon500
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Source Image Preview Carousel
            Text(
                text = "SELECTED SOURCE PHOTOS",
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = Maroon700
            )

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(itemsToResize) { media ->
                    Card(
                        modifier = Modifier
                            .size(120.dp)
                            .border(1.dp, GoldAccent, RoundedCornerShape(8.dp)),
                        shape = RoundedCornerShape(8.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                    ) {
                        Box(modifier = Modifier.fillMaxSize()) {
                            AsyncImage(
                                model = media.imageUri,
                                contentDescription = media.notes,
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                            Box(
                                modifier = Modifier
                                    .align(Alignment.BottomCenter)
                                    .fillMaxWidth()
                                    .background(Color.Black.copy(alpha = 0.65f))
                                    .padding(4.dp)
                            ) {
                                Text(
                                    text = "${media.width}x${media.height}",
                                    fontSize = 10.sp,
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.align(Alignment.Center)
                                )
                            }
                        }
                    }
                }
            }

            // Target Resolution Presets
            Text(
                text = "TARGET DIMENSION PRESETS",
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = Maroon700
            )

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                ResizePreset.entries.forEach { preset ->
                    val isSelected = !isCustomResolution && selectedPreset == preset
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                isCustomResolution = false
                                selectedPreset = preset
                                customWidthText = preset.width.toString()
                                customHeightText = preset.height.toString()
                            },
                        shape = RoundedCornerShape(10.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = if (isSelected) Maroon500 else MaterialTheme.colorScheme.surface
                        ),
                        border = if (isSelected) androidx.compose.foundation.BorderStroke(2.dp, GoldAccent) else null
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(14.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.PhotoSizeSelectLarge,
                                    contentDescription = null,
                                    tint = if (isSelected) GoldAccent else Maroon700
                                )
                                Spacer(modifier = Modifier.width(12.dp))
                                Column {
                                    Text(
                                        text = preset.title,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 14.sp,
                                        color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface
                                    )
                                    Text(
                                        text = "${preset.width} x ${preset.height} px (${preset.ratioLabel})",
                                        fontSize = 12.sp,
                                        color = if (isSelected) GoldAccent.copy(alpha = 0.9f) else MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }
                            if (isSelected) {
                                Icon(
                                    Icons.Default.Check,
                                    contentDescription = "Selected",
                                    tint = GoldAccent
                                )
                            }
                        }
                    }
                }
            }

            // Custom Resolution
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { isCustomResolution = true }
                    ) {
                        Text(
                            text = "CUSTOM RESOLUTION",
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp,
                            color = Maroon700
                        )
                    }
                    if (isCustomResolution) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            OutlinedTextField(
                                value = customWidthText,
                                onValueChange = { customWidthText = it },
                                label = { Text("Width (px)") },
                                modifier = Modifier.weight(1f),
                                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = Maroon500)
                            )
                            OutlinedTextField(
                                value = customHeightText,
                                onValueChange = { customHeightText = it },
                                label = { Text("Height (px)") },
                                modifier = Modifier.weight(1f),
                                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = Maroon500)
                            )
                        }
                    }
                }
            }

            // Fit Mode Options
            Text(
                text = "ASPECT RATIO CROP & FIT MODE",
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = Maroon700
            )

            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                FilterChip(
                    selected = fitMode == FitMode.CENTER_CROP,
                    onClick = { fitMode = FitMode.CENTER_CROP },
                    label = { Text("Center Crop (Fill)") },
                    leadingIcon = { Icon(Icons.Default.Crop, contentDescription = null) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = Maroon500,
                        selectedLabelColor = GoldAccent
                    )
                )

                FilterChip(
                    selected = fitMode == FitMode.FIT_CENTER_PADDING,
                    onClick = { fitMode = FitMode.FIT_CENTER_PADDING },
                    label = { Text("Fit Center (Letterbox)") },
                    leadingIcon = { Icon(Icons.Default.FitScreen, contentDescription = null) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = Maroon500,
                        selectedLabelColor = GoldAccent
                    )
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Action Button
            Button(
                onClick = {
                    if (itemsToResize.isEmpty()) {
                        Toast.makeText(context, "No images selected to resize", Toast.LENGTH_SHORT).show()
                        return@Button
                    }
                    isProcessing = true
                    processMessage = "Resizing ${itemsToResize.size} image(s) to ${activeWidth}x${activeHeight}..."

                    if (itemsToResize.size == 1) {
                        viewModel.resizeAndSaveImage(
                            context = context,
                            mediaItem = itemsToResize.first(),
                            targetWidth = activeWidth,
                            targetHeight = activeHeight,
                            fitMode = fitMode
                        ) { success ->
                            isProcessing = false
                            if (success) {
                                Toast.makeText(context, "Image resized & saved to Catalogue DB!", Toast.LENGTH_LONG).show()
                                onComplete()
                            } else {
                                Toast.makeText(context, "Failed to resize image", Toast.LENGTH_SHORT).show()
                            }
                        }
                    } else {
                        viewModel.bulkResizeAndSave(
                            context = context,
                            mediaItems = itemsToResize,
                            targetWidth = activeWidth,
                            targetHeight = activeHeight,
                            fitMode = fitMode
                        ) { count ->
                            isProcessing = false
                            Toast.makeText(context, "Resized & saved $count images to Catalogue DB!", Toast.LENGTH_LONG).show()
                            onComplete()
                        }
                    }
                },
                enabled = !isProcessing && itemsToResize.isNotEmpty(),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Maroon500,
                    contentColor = GoldAccent
                ),
                shape = RoundedCornerShape(10.dp)
            ) {
                if (isProcessing) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = GoldAccent,
                        strokeWidth = 2.dp
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(processMessage, fontSize = 14.sp)
                } else {
                    Icon(Icons.Default.Crop, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = if (itemsToResize.size > 1) "BULK RESIZE (${itemsToResize.size} IMAGES)" else "RESIZE & SAVE TO CATALOGUE",
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                }
            }
        }
    }
}
