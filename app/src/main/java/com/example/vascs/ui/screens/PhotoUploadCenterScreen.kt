package com.example.vascs.ui.screens

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Collections
import androidx.compose.material.icons.filled.PhotoLibrary
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.core.content.ContextCompat
import com.example.vascs.ui.theme.GoldAccent
import com.example.vascs.ui.theme.Maroon500
import com.example.vascs.ui.viewmodel.MediaLibraryViewModel
import com.example.vascs.util.ImageStorageManager
import java.io.File

@Composable
fun PhotoUploadCenterScreen(
    viewModel: MediaLibraryViewModel,
    onNavigateToMediaLibrary: () -> Unit
) {
    val context = LocalContext.current

    var pendingCameraFile by remember { mutableStateOf<File?>(null) }
    var pendingCameraUri by remember { mutableStateOf<Uri?>(null) }

    // Camera Launcher
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success ->
        if (success) {
            val file = pendingCameraFile
            if (file != null && file.exists()) {
                viewModel.saveCapturedCameraImage(context, file)
                Toast.makeText(context, "Camera photo saved into Media Library!", Toast.LENGTH_SHORT).show()
            }
        }
        pendingCameraFile = null
        pendingCameraUri = null
    }

    // Permission Launcher for Camera
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            val cameraFileResult = ImageStorageManager.createMediaLibraryCameraFile(context)
            if (cameraFileResult != null) {
                pendingCameraFile = cameraFileResult.first
                pendingCameraUri = cameraFileResult.second
                cameraLauncher.launch(cameraFileResult.second)
            } else {
                Toast.makeText(context, "Unable to initialize camera storage", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(context, "Camera permission required to capture photos", Toast.LENGTH_SHORT).show()
        }
    }

    // Gallery Multi-Select Launcher
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetMultipleContents()
    ) { uris ->
        if (!uris.isNullOrEmpty()) {
            viewModel.importGalleryImages(context, uris)
            Toast.makeText(context, "Imported ${uris.size} image(s) to Media Library", Toast.LENGTH_SHORT).show()
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        // Header Banner
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Maroon500),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(modifier = Modifier.padding(24.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.AddAPhoto, contentDescription = null, tint = GoldAccent, modifier = Modifier.size(28.dp))
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = "PHOTO UPLOAD CENTER",
                            color = GoldAccent,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.sp
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Photo Upload & Media Ingestion Studio",
                        color = Color.White,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Capture live camera photos, import gallery images, or launch AI Studio assets directly into the unified Media Library.",
                        color = Color.White.copy(alpha = 0.85f),
                        fontSize = 14.sp
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = onNavigateToMediaLibrary,
                        colors = ButtonDefaults.buttonColors(containerColor = GoldAccent, contentColor = Color.Black)
                    ) {
                        Icon(Icons.Default.Collections, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Open Media Library", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        // Action Buttons / Tiles
        item {
            Text(
                text = "INGESTION CHANNELS",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold
            )
        }

        // BUTTON 1: LIVE CAMERA
        item {
            UploadSourceCard(
                title = "LIVE CAMERA",
                description = "Capture full-resolution product photos directly using the Android camera.",
                buttonText = "LIVE CAMERA",
                icon = Icons.Default.CameraAlt,
                accentColor = Maroon500,
                onClick = {
                    val cameraCheck = ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA)
                    if (cameraCheck == PackageManager.PERMISSION_GRANTED) {
                        val cameraFileResult = ImageStorageManager.createMediaLibraryCameraFile(context)
                        if (cameraFileResult != null) {
                            pendingCameraFile = cameraFileResult.first
                            pendingCameraUri = cameraFileResult.second
                            cameraLauncher.launch(cameraFileResult.second)
                        } else {
                            Toast.makeText(context, "Unable to create camera file", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        permissionLauncher.launch(Manifest.permission.CAMERA)
                    }
                }
            )
        }

        // BUTTON 2: FROM AI
        item {
            UploadSourceCard(
                title = "FROM AI",
                description = "Launch Google AI Studio in browser to generate & explore AI saree models.",
                buttonText = "FROM AI",
                icon = Icons.Default.AutoAwesome,
                accentColor = Color(0xFF0284C7),
                onClick = {
                    val aiStudioUrl = "https://aistudio.google.com/"
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(aiStudioUrl))
                    context.startActivity(intent)
                }
            )
        }

        // BUTTON 3: UPLOAD FROM GALLERY
        item {
            UploadSourceCard(
                title = "UPLOAD FROM GALLERY",
                description = "Import single or multi-select images (JPG, JPEG, PNG, WEBP) from device gallery.",
                buttonText = "UPLOAD FROM GALLERY",
                icon = Icons.Default.PhotoLibrary,
                accentColor = Color(0xFF059669),
                onClick = {
                    galleryLauncher.launch("image/*")
                }
            )
        }
    }
}

@Composable
fun UploadSourceCard(
    title: String,
    description: String,
    buttonText: String,
    icon: ImageVector,
    accentColor: Color,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .clip(CircleShape)
                    .background(accentColor.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(icon, contentDescription = null, tint = accentColor, modifier = Modifier.size(28.dp))
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = description,
                    fontSize = 13.sp,
                    color = Color.Gray,
                    lineHeight = 18.sp
                )
                Spacer(modifier = Modifier.height(12.dp))
                Button(
                    onClick = onClick,
                    colors = ButtonDefaults.buttonColors(containerColor = accentColor, contentColor = Color.White),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(buttonText, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}
