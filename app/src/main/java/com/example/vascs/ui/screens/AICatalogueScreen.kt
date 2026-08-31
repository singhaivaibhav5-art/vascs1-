package com.example.vascs.ui.screens

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.vascs.data.model.AICatalogueResultEntity
import com.example.vascs.data.model.AICatalogueTemplateEntity
import com.example.vascs.viewmodel.AICatalogueExecutionState
import com.example.vascs.viewmodel.AICatalogueViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AICatalogueScreen(
    viewModel: AICatalogueViewModel,
    onNavigateBack: () -> Unit = {}
) {
    val inputState by viewModel.catalogueInput.collectAsStateWithLifecycle()
    val resultState by viewModel.catalogueResult.collectAsStateWithLifecycle()
    val historyList by viewModel.catalogueHistory.collectAsStateWithLifecycle()
    val templatesList by viewModel.catalogueTemplates.collectAsStateWithLifecycle()
    val loadingState by viewModel.loadingState.collectAsStateWithLifecycle()
    val selectedTab by viewModel.selectedTab.collectAsStateWithLifecycle()
    val errorMessage by viewModel.errorMessage.collectAsStateWithLifecycle()

    val context = LocalContext.current
    val clipboardManager = LocalClipboardManager.current

    var showPdfExportDialog by remember { mutableStateOf(false) }

    fun copyToClipboard(text: String, label: String = "Catalogue Content") {
        clipboardManager.setText(AnnotatedString(text))
        Toast.makeText(context, "$label copied to clipboard!", Toast.LENGTH_SHORT).show()
    }

    fun shareContent(text: String, title: String = "Share Catalogue") {
        val sendIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, text)
            type = "text/plain"
        }
        val shareIntent = Intent.createChooser(sendIntent, title)
        context.startActivity(shareIntent)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "AI Catalogue Generator",
                                fontWeight = FontWeight.Bold,
                                style = MaterialTheme.typography.titleLarge
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Surface(
                                shape = RoundedCornerShape(12.dp),
                                color = MaterialTheme.colorScheme.primaryContainer,
                                modifier = Modifier.padding(2.dp)
                            ) {
                                Text(
                                    text = "U2 ENGINE",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                        }
                        Text(
                            text = "Omnichannel Commercial & Luxury Fashion Copywriting",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                },
                navigationIcon = {
                    IconButton(
                        onClick = onNavigateBack,
                        modifier = Modifier.testTag("catalogue_back_button")
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Navigate Back"
                        )
                    }
                },
                actions = {
                    if (resultState != null) {
                        IconButton(
                            onClick = { showPdfExportDialog = true },
                            modifier = Modifier.testTag("catalogue_export_pdf_button")
                        ) {
                            Icon(
                                imageVector = Icons.Default.PictureAsPdf,
                                contentDescription = "Export PDF",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                    IconButton(
                        onClick = { viewModel.clearInput() },
                        modifier = Modifier.testTag("catalogue_reset_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = "Reset Form"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Tab Selector
            TabRow(
                selectedTabIndex = selectedTab,
                containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(1.dp),
                contentColor = MaterialTheme.colorScheme.primary
            ) {
                Tab(
                    selected = selectedTab == 0,
                    onClick = { viewModel.setTab(0) },
                    text = { Text("Generator", fontWeight = FontWeight.SemiBold) },
                    icon = { Icon(Icons.Default.AutoAwesome, contentDescription = null) },
                    modifier = Modifier.testTag("tab_generator")
                )
                Tab(
                    selected = selectedTab == 1,
                    onClick = { viewModel.setTab(1) },
                    text = { Text("Omnichannel (${if (resultState != null) "Ready" else "0"})", fontWeight = FontWeight.SemiBold) },
                    icon = { Icon(Icons.Default.Share, contentDescription = null) },
                    modifier = Modifier.testTag("tab_omnichannel")
                )
                Tab(
                    selected = selectedTab == 2,
                    onClick = { viewModel.setTab(2) },
                    text = { Text("History (${historyList.size})", fontWeight = FontWeight.SemiBold) },
                    icon = { Icon(Icons.Default.History, contentDescription = null) },
                    modifier = Modifier.testTag("tab_history")
                )
                Tab(
                    selected = selectedTab == 3,
                    onClick = { viewModel.setTab(3) },
                    text = { Text("Presets", fontWeight = FontWeight.SemiBold) },
                    icon = { Icon(Icons.Default.Bookmarks, contentDescription = null) },
                    modifier = Modifier.testTag("tab_templates")
                )
            }

            // Error banner
            AnimatedVisibility(visible = errorMessage != null) {
                errorMessage?.let { errorText ->
                    Surface(
                        color = MaterialTheme.colorScheme.errorContainer,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                Icons.Default.Warning,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.error
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = errorText,
                                color = MaterialTheme.colorScheme.onErrorContainer,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                }
            }

            // Tab Contents
            when (selectedTab) {
                0 -> GeneratorTab(
                    inputState = inputState,
                    resultState = resultState,
                    loadingState = loadingState,
                    onUpdateName = { viewModel.updateProductName(it) },
                    onUpdateCategory = { viewModel.updateCategory(it) },
                    onUpdateFabric = { viewModel.updateFabric(it) },
                    onUpdateColor = { viewModel.updateColor(it) },
                    onUpdatePrice = { viewModel.updatePrice(it) },
                    onUpdateDetails = { viewModel.updateDesignDetails(it) },
                    onUpdateOccasion = { viewModel.updateOccasion(it) },
                    onUpdateImageUrl = { viewModel.updateProductImageUrl(it) },
                    onGenerate = { viewModel.generateCatalogue() },
                    onCopy = { text, label -> copyToClipboard(text, label) },
                    onShare = { text, title -> shareContent(text, title) },
                    onExportPdf = { showPdfExportDialog = true },
                    onGoToOmnichannel = { viewModel.setTab(1) }
                )
                1 -> OmnichannelTab(
                    result = resultState,
                    onCopy = { text, label -> copyToClipboard(text, label) },
                    onShare = { text, title -> shareContent(text, title) },
                    onExportPdf = { showPdfExportDialog = true }
                )
                2 -> HistoryTab(
                    history = historyList,
                    onSelect = { viewModel.selectHistoryItem(it) },
                    onDelete = { viewModel.deleteCatalogueResult(it.resultId) },
                    onToggleFavorite = { viewModel.toggleFavorite(it) },
                    onCopy = { text, label -> copyToClipboard(text, label) },
                    onShare = { text, title -> shareContent(text, title) }
                )
                3 -> PresetsTab(
                    templates = templatesList,
                    onApply = {
                        viewModel.applyTemplate(it)
                        viewModel.setTab(0)
                    }
                )
            }
        }
    }

    // PDF Preview / Export Dialog
    if (showPdfExportDialog && resultState != null) {
        val result = resultState!!
        AlertDialog(
            onDismissRequest = { showPdfExportDialog = false },
            icon = { Icon(Icons.Default.PictureAsPdf, contentDescription = null, tint = MaterialTheme.colorScheme.primary) },
            title = { Text("Export Luxury PDF Catalogue", fontWeight = FontWeight.Bold) },
            text = {
                Column(
                    modifier = Modifier
                        .verticalScroll(rememberScrollState())
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "A complete luxury catalog spec-sheet has been structured for export and print distribution.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant
                        ),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text(result.productTitle, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleSmall)
                            Text("Fabric: ${result.fabric} | Color: ${result.color} | MRP: ₹${result.price}", style = MaterialTheme.typography.bodySmall)
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(result.premiumCatalogueContent, style = MaterialTheme.typography.bodySmall, maxLines = 6, overflow = TextOverflow.Ellipsis)
                        }
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        showPdfExportDialog = false
                        val pdfContent = buildString {
                            appendLine("==========================================")
                            appendLine("      VASCS ULTIMA LUXURY CATALOGUE       ")
                            appendLine("==========================================")
                            appendLine()
                            appendLine("TITLE: ${result.productTitle}")
                            appendLine("CATEGORY: ${result.category}")
                            appendLine("FABRIC: ${result.fabric}")
                            appendLine("COLOR: ${result.color}")
                            appendLine("PRICE: ₹${result.price}")
                            appendLine("OCCASION: ${result.occasion}")
                            appendLine()
                            appendLine("--- SHORT OVERVIEW ---")
                            appendLine(result.shortDescription)
                            appendLine()
                            appendLine("--- CRAFTSMANSHIP & DRAPE ---")
                            appendLine(result.longDescription)
                            appendLine()
                            appendLine("--- SEO METADATA ---")
                            appendLine("Meta Description: ${result.seoDescription}")
                            appendLine("Keywords: ${result.seoKeywords}")
                            appendLine()
                            appendLine("--- MAISON ARCHIVE SPEC ---")
                            appendLine(result.premiumCatalogueContent)
                            appendLine()
                            appendLine("==========================================")
                            appendLine("Generated by VASCS ULTIMA AI Engine")
                        }
                        shareContent(pdfContent, "Export PDF Spec-Sheet")
                    },
                    modifier = Modifier.testTag("confirm_export_pdf_button")
                ) {
                    Icon(Icons.Default.Share, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Share / Print")
                }
            },
            dismissButton = {
                TextButton(onClick = { showPdfExportDialog = false }) {
                    Text("Close")
                }
            }
        )
    }
}

// ----------------------------------------------------
// TAB 0: GENERATOR TAB
// ----------------------------------------------------
@Composable
fun GeneratorTab(
    inputState: com.example.vascs.viewmodel.AICatalogueInputState,
    resultState: AICatalogueResultEntity?,
    loadingState: Boolean,
    onUpdateName: (String) -> Unit,
    onUpdateCategory: (String) -> Unit,
    onUpdateFabric: (String) -> Unit,
    onUpdateColor: (String) -> Unit,
    onUpdatePrice: (String) -> Unit,
    onUpdateDetails: (String) -> Unit,
    onUpdateOccasion: (String) -> Unit,
    onUpdateImageUrl: (String) -> Unit,
    onGenerate: () -> Unit,
    onCopy: (String, String) -> Unit,
    onShare: (String, String) -> Unit,
    onExportPdf: () -> Unit,
    onGoToOmnichannel: () -> Unit
) {
    val scrollState = rememberScrollState()

    val categoryPresets = listOf("Sarees", "Bridal Lehengas", "Kurtis & Suits", "Dupattas", "Raw Fabrics", "Sherwanis")
    val fabricPresets = listOf("Pure Katan Silk", "Tissue Organza", "Georgette", "Art Chanderi", "Raw Silk", "Tussar")
    val occasionPresets = listOf("Bridal & Festive", "Royal Wedding", "Cocktail & Sangeet", "Festive Daily", "Gifting")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp)
    ) {
        // Hero Header Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer
            )
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Default.AutoAwesome,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onPrimaryContainer,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "10-Point Multichannel AI Engine",
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Generates SEO descriptions, Instagram captions, WhatsApp dealer broadcasts, and haute couture catalogues instantly from product attributes.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.85f)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Input Form Section
        Text(
            text = "PRODUCT SPECIFICATIONS",
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(8.dp))

        // Product Name
        OutlinedTextField(
            value = inputState.productName,
            onValueChange = onUpdateName,
            label = { Text("Product Name *") },
            placeholder = { Text("e.g. Royal Banarasi Silk Saree") },
            modifier = Modifier
                .fillMaxWidth()
                .testTag("input_product_name"),
            singleLine = true,
            leadingIcon = { Icon(Icons.Default.ShoppingBag, contentDescription = null) }
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Category with quick chips
        OutlinedTextField(
            value = inputState.category,
            onValueChange = onUpdateCategory,
            label = { Text("Category") },
            modifier = Modifier
                .fillMaxWidth()
                .testTag("input_category"),
            singleLine = true,
            leadingIcon = { Icon(Icons.Default.Category, contentDescription = null) }
        )
        LazyRow(
            modifier = Modifier.padding(vertical = 6.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            items(categoryPresets) { cat ->
                FilterChip(
                    selected = inputState.category == cat,
                    onClick = { onUpdateCategory(cat) },
                    label = { Text(cat, fontSize = 12.sp) }
                )
            }
        }

        Spacer(modifier = Modifier.height(4.dp))

        // Fabric & Color Row
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedTextField(
                value = inputState.fabric,
                onValueChange = onUpdateFabric,
                label = { Text("Fabric *") },
                placeholder = { Text("e.g. Pure Katan Silk") },
                modifier = Modifier
                    .weight(1f)
                    .testTag("input_fabric"),
                singleLine = true,
                leadingIcon = { Icon(Icons.Default.Texture, contentDescription = null) }
            )
            OutlinedTextField(
                value = inputState.color,
                onValueChange = onUpdateColor,
                label = { Text("Color / Palette *") },
                placeholder = { Text("e.g. Crimson Red") },
                modifier = Modifier
                    .weight(1f)
                    .testTag("input_color"),
                singleLine = true,
                leadingIcon = { Icon(Icons.Default.Palette, contentDescription = null) }
            )
        }

        LazyRow(
            modifier = Modifier.padding(vertical = 6.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            items(fabricPresets) { fab ->
                SuggestionChip(
                    onClick = { onUpdateFabric(fab) },
                    label = { Text(fab, fontSize = 11.sp) }
                )
            }
        }

        Spacer(modifier = Modifier.height(4.dp))

        // Price & Occasion Row
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedTextField(
                value = inputState.price,
                onValueChange = onUpdatePrice,
                label = { Text("Price / MRP (₹)") },
                placeholder = { Text("18500") },
                modifier = Modifier
                    .weight(1f)
                    .testTag("input_price"),
                singleLine = true,
                leadingIcon = { Icon(Icons.Default.CurrencyRupee, contentDescription = null) }
            )
            OutlinedTextField(
                value = inputState.occasion,
                onValueChange = onUpdateOccasion,
                label = { Text("Occasion / Theme") },
                placeholder = { Text("Bridal & Festive") },
                modifier = Modifier
                    .weight(1f)
                    .testTag("input_occasion"),
                singleLine = true,
                leadingIcon = { Icon(Icons.Default.Celebration, contentDescription = null) }
            )
        }

        LazyRow(
            modifier = Modifier.padding(vertical = 6.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            items(occasionPresets) { occ ->
                SuggestionChip(
                    onClick = { onUpdateOccasion(occ) },
                    label = { Text(occ, fontSize = 11.sp) }
                )
            }
        }

        Spacer(modifier = Modifier.height(4.dp))

        // Design Details
        OutlinedTextField(
            value = inputState.designDetails,
            onValueChange = onUpdateDetails,
            label = { Text("Design & Weave Details") },
            placeholder = { Text("e.g. Kadwa Jangla weave with pure gold zari borders and heavy pallu") },
            modifier = Modifier
                .fillMaxWidth()
                .testTag("input_design_details"),
            minLines = 2,
            maxLines = 4,
            leadingIcon = { Icon(Icons.Default.Brush, contentDescription = null) }
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Product Image URL (Optional)
        OutlinedTextField(
            value = inputState.productImageUrl,
            onValueChange = onUpdateImageUrl,
            label = { Text("Product Image URL (Optional)") },
            placeholder = { Text("https://example.com/images/saree1.jpg") },
            modifier = Modifier
                .fillMaxWidth()
                .testTag("input_image_url"),
            singleLine = true,
            leadingIcon = { Icon(Icons.Default.Image, contentDescription = null) }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Generate CTA Button
        Button(
            onClick = onGenerate,
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp)
                .testTag("btn_generate_catalogue"),
            enabled = !loadingState && inputState.productName.isNotBlank(),
            shape = RoundedCornerShape(12.dp)
        ) {
            if (loadingState) {
                CircularProgressIndicator(
                    modifier = Modifier.size(24.dp),
                    color = MaterialTheme.colorScheme.onPrimary,
                    strokeWidth = 2.5.dp
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text("Synthesizing Multichannel Copy...", fontWeight = FontWeight.Bold)
            } else {
                Icon(Icons.Default.AutoAwesome, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Generate AI Catalogue", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Generated Results Presentation
        if (resultState != null) {
            val result = resultState

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "GENERATED CATALOGUE OUTPUT",
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary
                )
                TextButton(onClick = onGoToOmnichannel) {
                    Text("View Omnichannel Hub →")
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // 1. Title Card
            CatalogueOutputCard(
                title = "1. Regal Product Title",
                content = result.productTitle,
                badge = "Ecommerce & Catalogue",
                onCopy = { onCopy(result.productTitle, "Product Title") },
                onShare = { onShare(result.productTitle, "Share Title") }
            )

            // 2. Short Description
            CatalogueOutputCard(
                title = "2. Short Description (Summary)",
                content = result.shortDescription,
                badge = "Product Card / Listing",
                onCopy = { onCopy(result.shortDescription, "Short Description") },
                onShare = { onShare(result.shortDescription, "Share Short Description") }
            )

            // 3. Long Description
            CatalogueOutputCard(
                title = "3. Long Description (Craftsmanship & Drape)",
                content = result.longDescription,
                badge = "PDP & Lookbook",
                onCopy = { onCopy(result.longDescription, "Long Description") },
                onShare = { onShare(result.longDescription, "Share Long Description") }
            )

            // 4. SEO Meta Description
            CatalogueOutputCard(
                title = "4. SEO Meta Description",
                content = result.seoDescription,
                badge = "Google Search & Meta",
                onCopy = { onCopy(result.seoDescription, "SEO Description") },
                onShare = { onShare(result.seoDescription, "Share SEO Description") }
            )

            // 5. SEO Keywords
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("5. SEO Keywords & Search Tags", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleSmall)
                        IconButton(
                            onClick = { onCopy(result.seoKeywords, "SEO Keywords") },
                            modifier = Modifier.size(32.dp)
                        ) {
                            Icon(Icons.Default.ContentCopy, contentDescription = "Copy Keywords", modifier = Modifier.size(16.dp))
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    val tags = result.seoKeywords.split(",").map { it.trim() }.filter { it.isNotBlank() }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        tags.forEach { tag ->
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.12f)
                            ) {
                                Text(
                                    text = tag,
                                    color = MaterialTheme.colorScheme.primary,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Medium,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                )
                            }
                        }
                    }
                }
            }

            // Quick actions to share Omnichannel
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = onGoToOmnichannel,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(Icons.Default.Share, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Open Instagram, WhatsApp & Dealer Broadcasts")
            }
        }
    }
}

// ----------------------------------------------------
// TAB 1: OMNICHANNEL EXPORT & PROMOTION TAB
// ----------------------------------------------------
@Composable
fun OmnichannelTab(
    result: AICatalogueResultEntity?,
    onCopy: (String, String) -> Unit,
    onShare: (String, String) -> Unit,
    onExportPdf: () -> Unit
) {
    val scrollState = rememberScrollState()

    if (result == null) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    Icons.Outlined.ContentPasteOff,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.outline,
                    modifier = Modifier.size(64.dp)
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "No Catalogue Generated Yet",
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Fill in the product specifications in the Generator tab to synthesize omnichannel social copy and marketing broadcasts.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center
                )
            }
        }
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(16.dp)
        ) {
            Text(
                text = "OMNICHANNEL MARKETING SUITE",
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = result.productTitle,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = 4.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            // 6. Instagram Caption
            ChannelSocialCard(
                channelName = "6. Instagram Caption & Hashtags",
                content = result.instagramCaption,
                icon = Icons.Default.CameraAlt,
                accentColor = Color(0xFFE1306C),
                onCopy = { onCopy(result.instagramCaption, "Instagram Caption") },
                onShare = { onShare(result.instagramCaption, "Share on Instagram") }
            )

            // 7. Facebook Caption
            ChannelSocialCard(
                channelName = "7. Facebook Post & Storytelling",
                content = result.facebookCaption,
                icon = Icons.Default.ThumbUp,
                accentColor = Color(0xFF1877F2),
                onCopy = { onCopy(result.facebookCaption, "Facebook Caption") },
                onShare = { onShare(result.facebookCaption, "Share on Facebook") }
            )

            // 8. WhatsApp Promotion Text
            ChannelSocialCard(
                channelName = "8. WhatsApp Promotional Broadcast",
                content = result.whatsappPromotionText,
                icon = Icons.Default.Chat,
                accentColor = Color(0xFF25D366),
                onCopy = { onCopy(result.whatsappPromotionText, "WhatsApp Broadcast") },
                onShare = { onShare(result.whatsappPromotionText, "Send via WhatsApp") }
            )

            // 9. Dealer Marketing Text
            ChannelSocialCard(
                channelName = "9. B2B Dealer Wholesale Marketing",
                content = result.dealerMarketingText,
                icon = Icons.Default.Business,
                accentColor = Color(0xFF673AB7),
                onCopy = { onCopy(result.dealerMarketingText, "Dealer Marketing Text") },
                onShare = { onShare(result.dealerMarketingText, "Share with Dealers") }
            )

            // 10. Premium Catalogue Content
            ChannelSocialCard(
                channelName = "10. Haute Couture Maison Brochure Spec",
                content = result.premiumCatalogueContent,
                icon = Icons.Default.Stars,
                accentColor = Color(0xFFD4AF37),
                onCopy = { onCopy(result.premiumCatalogueContent, "Maison Brochure") },
                onShare = { onShare(result.premiumCatalogueContent, "Share Luxury Brochure") }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Export Actions Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedButton(
                    onClick = onExportPdf,
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(Icons.Default.PictureAsPdf, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Export PDF Spec")
                }
                Button(
                    onClick = { onShare(result.whatsappPromotionText, "Send Wholesale Broadcast") },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(Icons.Default.Send, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Broadcast All")
                }
            }
        }
    }
}

// ----------------------------------------------------
// TAB 2: HISTORY TAB
// ----------------------------------------------------
@Composable
fun HistoryTab(
    history: List<AICatalogueResultEntity>,
    onSelect: (AICatalogueResultEntity) -> Unit,
    onDelete: (AICatalogueResultEntity) -> Unit,
    onToggleFavorite: (AICatalogueResultEntity) -> Unit,
    onCopy: (String, String) -> Unit,
    onShare: (String, String) -> Unit
) {
    if (history.isEmpty()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    Icons.Outlined.History,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.outline,
                    modifier = Modifier.size(64.dp)
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "No Generation History",
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Generated product catalogues are automatically saved to your Room database for instant retrieval.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center
                )
            }
        }
    } else {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(history, key = { it.resultId }) { item ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onSelect(item) },
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant
                    )
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = item.productTitle,
                                fontWeight = FontWeight.Bold,
                                style = MaterialTheme.typography.titleMedium,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                modifier = Modifier.weight(1f)
                            )
                            IconButton(
                                onClick = { onToggleFavorite(item) },
                                modifier = Modifier.size(32.dp)
                            ) {
                                Icon(
                                    imageVector = if (item.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                                    contentDescription = "Toggle Favorite",
                                    tint = if (item.isFavorite) Color.Red else MaterialTheme.colorScheme.outline,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                            IconButton(
                                onClick = { onDelete(item) },
                                modifier = Modifier.size(32.dp)
                            ) {
                                Icon(
                                    Icons.Default.DeleteOutline,
                                    contentDescription = "Delete Result",
                                    tint = MaterialTheme.colorScheme.error,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }

                        Row(
                            modifier = Modifier.padding(vertical = 4.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Surface(
                                shape = RoundedCornerShape(6.dp),
                                color = MaterialTheme.colorScheme.primaryContainer
                            ) {
                                Text(
                                    text = item.fabric,
                                    fontSize = 11.sp,
                                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                            Surface(
                                shape = RoundedCornerShape(6.dp),
                                color = MaterialTheme.colorScheme.secondaryContainer
                            ) {
                                Text(
                                    text = item.color,
                                    fontSize = 11.sp,
                                    color = MaterialTheme.colorScheme.onSecondaryContainer,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                            Surface(
                                shape = RoundedCornerShape(6.dp),
                                color = MaterialTheme.colorScheme.tertiaryContainer
                            ) {
                                Text(
                                    text = "₹${item.price}",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onTertiaryContainer,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = item.shortDescription,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )

                        Spacer(modifier = Modifier.height(8.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            TextButton(
                                onClick = { onCopy(item.whatsappPromotionText, "WhatsApp Broadcast") },
                                contentPadding = PaddingValues(horizontal = 8.dp, vertical = 2.dp)
                            ) {
                                Icon(Icons.Default.ContentCopy, contentDescription = null, modifier = Modifier.size(14.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Copy WA", fontSize = 12.sp)
                            }
                            Spacer(modifier = Modifier.width(4.dp))
                            TextButton(
                                onClick = { onSelect(item) },
                                contentPadding = PaddingValues(horizontal = 8.dp, vertical = 2.dp)
                            ) {
                                Text("Load Editor →", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }
        }
    }
}

// ----------------------------------------------------
// TAB 3: PRESETS & TEMPLATES TAB
// ----------------------------------------------------
@Composable
fun PresetsTab(
    templates: List<AICatalogueTemplateEntity>,
    onApply: (AICatalogueTemplateEntity) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Text(
                text = "INDUSTRY EDITORIAL PRESETS",
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = "Pre-configured luxury fabric presets with battle-tested styling and copywriting tones.",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(4.dp))
        }

        items(templates, key = { it.templateId }) { template ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = template.templateName,
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.titleMedium
                        )
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)
                        ) {
                            Text(
                                text = template.category,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = template.headerTagline,
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.primary
                    )

                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "• Fabric: ${template.sampleFabric}\n• Palette: ${template.sampleColor}\n• Weave: ${template.sampleDesignDetails}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(modifier = Modifier.height(10.dp))
                    Button(
                        onClick = { onApply(template) },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Icon(Icons.Default.Check, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Apply Template & Fill Form")
                    }
                }
            }
        }
    }
}

// ----------------------------------------------------
// REUSABLE HELPER COMPONENTS
// ----------------------------------------------------
@Composable
fun CatalogueOutputCard(
    title: String,
    content: String,
    badge: String,
    onCopy: () -> Unit,
    onShare: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(title, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleSmall)
                    Text(badge, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.primary, fontSize = 11.sp)
                }
                Row {
                    IconButton(onClick = onCopy, modifier = Modifier.size(32.dp)) {
                        Icon(Icons.Default.ContentCopy, contentDescription = "Copy", modifier = Modifier.size(16.dp))
                    }
                    IconButton(onClick = onShare, modifier = Modifier.size(32.dp)) {
                        Icon(Icons.Default.Share, contentDescription = "Share", modifier = Modifier.size(16.dp))
                    }
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Surface(
                shape = RoundedCornerShape(8.dp),
                color = MaterialTheme.colorScheme.surface,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = content,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(10.dp)
                )
            }
        }
    }
}

@Composable
fun ChannelSocialCard(
    channelName: String,
    content: String,
    icon: ImageVector,
    accentColor: Color,
    onCopy: () -> Unit,
    onShare: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                    Surface(
                        shape = CircleShape,
                        color = accentColor.copy(alpha = 0.15f),
                        modifier = Modifier.size(32.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(icon, contentDescription = null, tint = accentColor, modifier = Modifier.size(18.dp))
                        }
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(channelName, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleSmall)
                }
                Row {
                    IconButton(onClick = onCopy, modifier = Modifier.size(32.dp)) {
                        Icon(Icons.Default.ContentCopy, contentDescription = "Copy", modifier = Modifier.size(16.dp))
                    }
                    IconButton(onClick = onShare, modifier = Modifier.size(32.dp)) {
                        Icon(Icons.Default.Share, contentDescription = "Share", modifier = Modifier.size(16.dp), tint = accentColor)
                    }
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Surface(
                shape = RoundedCornerShape(8.dp),
                color = MaterialTheme.colorScheme.surface,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = content,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(10.dp)
                )
            }
        }
    }
}
