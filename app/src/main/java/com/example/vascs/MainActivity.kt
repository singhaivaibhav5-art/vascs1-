package com.example.vascs

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.BatchPrediction
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.Collections
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.QrCode
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.vascs.data.model.ProductBatchEntity
import com.example.vascs.data.model.ProductEntity
import com.example.vascs.ui.screens.AiArchiveCenterScreen
import com.example.vascs.ui.screens.BatchDetailDialog
import com.example.vascs.ui.screens.BatchesScreen
import com.example.vascs.ui.screens.DashboardScreen
import com.example.vascs.ui.screens.LabelPrinterScreen
import com.example.vascs.ui.screens.MediaLibraryScreen
import com.example.vascs.ui.screens.PhotoUploadCenterScreen
import com.example.vascs.ui.screens.PricingCalculatorScreen
import com.example.vascs.ui.screens.ProductDetailDialog
import com.example.vascs.ui.screens.ProductEditDialog
import com.example.vascs.ui.screens.ProductImageGalleryDialog
import com.example.vascs.ui.screens.ProductsScreen
import com.example.vascs.ui.screens.SareeCatalogueGenerationDialog
import com.example.vascs.ui.theme.GoldAccent
import com.example.vascs.ui.theme.Maroon500
import com.example.vascs.ui.theme.VASCSTheme
import com.example.vascs.ui.viewmodel.AiArchiveViewModel
import com.example.vascs.ui.viewmodel.AiArchiveViewModelFactory
import com.example.vascs.ui.viewmodel.MediaLibraryViewModel
import com.example.vascs.ui.viewmodel.MediaLibraryViewModelFactory
import com.example.vascs.ui.viewmodel.VascsViewModel
import com.example.vascs.ui.viewmodel.VascsViewModelFactory

import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.LocalShipping
import androidx.compose.material.icons.filled.PhotoLibrary
import androidx.compose.material.icons.filled.Share
import com.example.vascs.ui.screens.ExportPreviewScreen
import com.example.vascs.ui.screens.MediaCommandCenterScreen
import com.example.vascs.ui.screens.OrderDispatchFactoryScreen
import com.example.vascs.ui.screens.SocialDealerNetworkScreen
import com.example.vascs.ui.screens.SocialMediaExportStudioScreen
import com.example.vascs.ui.viewmodel.DealerNetworkViewModel
import com.example.vascs.ui.viewmodel.DealerNetworkViewModelFactory
import com.example.vascs.ui.viewmodel.MediaCommandCenterViewModel
import com.example.vascs.ui.viewmodel.MediaCommandCenterViewModelFactory
import com.example.vascs.ui.viewmodel.OrderDispatchViewModel
import com.example.vascs.ui.viewmodel.OrderDispatchViewModelFactory
import com.example.vascs.util.ExportType

import androidx.compose.material.icons.filled.Chat
import com.example.vascs.ui.screens.WhatsappCommerceScreen
import androidx.compose.material.icons.filled.Stars
import com.example.vascs.ui.screens.OmegaPlatformScreen
import com.example.vascs.ui.viewmodel.OmegaViewModel
import com.example.vascs.ui.viewmodel.OmegaViewModelFactory
import com.example.vascs.ui.viewmodel.WhatsappCommerceViewModel
import com.example.vascs.ui.viewmodel.WhatsappCommerceViewModelFactory

import androidx.compose.material.icons.filled.AllInclusive
import com.example.vascs.ui.screens.InfinityPlatformScreen
import com.example.vascs.ui.viewmodel.InfinityViewModel
import com.example.vascs.ui.viewmodel.InfinityViewModelFactory

import androidx.compose.material.icons.filled.Public
import com.example.vascs.ui.screens.CosmosPlatformScreen
import com.example.vascs.ui.viewmodel.CosmosViewModel
import com.example.vascs.ui.viewmodel.CosmosViewModelFactory

import androidx.compose.material.icons.filled.Hub
import com.example.vascs.ui.screens.NexusPlatformScreen
import com.example.vascs.ui.viewmodel.NexusViewModel
import com.example.vascs.ui.viewmodel.NexusViewModelFactory

import com.example.vascs.ui.screens.QuantumPlatformScreen
import com.example.vascs.ui.viewmodel.QuantumViewModel
import com.example.vascs.ui.viewmodel.QuantumViewModelFactory

import com.example.vascs.ui.screens.AscensionPlatformScreen
import com.example.vascs.ui.viewmodel.AscensionViewModel
import com.example.vascs.ui.viewmodel.AscensionViewModelFactory

import com.example.vascs.ui.screens.OmniversePlatformScreen
import com.example.vascs.ui.viewmodel.OmniverseViewModel
import com.example.vascs.ui.viewmodel.OmniverseViewModelFactory

import com.example.vascs.ui.screens.EternityPlatformScreen
import com.example.vascs.ui.viewmodel.EternityViewModel
import com.example.vascs.ui.viewmodel.EternityViewModelFactory

import com.example.vascs.ui.screens.TranscendencePlatformScreen
import com.example.vascs.ui.viewmodel.TranscendenceViewModel
import com.example.vascs.ui.viewmodel.TranscendenceViewModelFactory

import com.example.vascs.ui.screen.SupremacyPlatformScreen
import com.example.vascs.ui.viewmodel.SupremacyViewModel
import com.example.vascs.ui.viewmodel.SupremacyViewModelFactory

import com.example.vascs.ui.screens.SingularityPrimePlatformScreen
import com.example.vascs.viewmodel.SingularityPrimeViewModel

import com.example.vascs.ui.screens.AbsolutePlatformScreen
import com.example.vascs.viewmodel.AbsoluteViewModel

import com.example.vascs.ui.screens.UltimaPlatformScreen
import com.example.vascs.ui.viewmodel.UltimaViewModel
import com.example.vascs.ui.screens.AIBrainDashboardScreen
import com.example.vascs.viewmodel.VascsAIBrainViewModel
import com.example.vascs.ui.screens.AICatalogueScreen
import com.example.vascs.viewmodel.AICatalogueViewModel
import com.example.vascs.ui.screens.AIPricingScreen
import com.example.vascs.viewmodel.AIPricingViewModel
import com.example.vascs.ui.screens.AIDemandForecastScreen
import com.example.vascs.viewmodel.AIDemandForecastViewModel
import com.example.vascs.ui.screens.AIDealerRecommendationScreen
import com.example.vascs.viewmodel.AIDealerRecommendationViewModel
import com.example.vascs.ui.screens.AIInventoryScreen
import com.example.vascs.viewmodel.AIInventoryViewModel
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material.icons.filled.Inventory

enum class NavDestination(val label: String, val icon: ImageVector) {
    AI_INVENTORY("AI Inventory", Icons.Default.Inventory),
    AI_DEALER("AI Dealer", Icons.Default.Groups),
    AI_DEMAND("AI Demand", Icons.AutoMirrored.Filled.TrendingUp),
    AI_PRICING("AI Pricing", Icons.Default.Calculate),
    AI_CATALOGUE("AI Catalogue", Icons.AutoMirrored.Filled.MenuBook),
    AI_BRAIN("AI Brain", Icons.Default.AutoAwesome),
    ULTIMA("VASCS ULTIMA", Icons.Default.AllInclusive),
    ABSOLUTE("VASCS ABSOLUTE", Icons.Default.AllInclusive),
    SINGULARITY_PRIME("VASCS SINGULARITY PRIME", Icons.Default.AllInclusive),
    SUPREMACY("VASCS SUPREMACY", Icons.Default.Stars),
    TRANSCENDENCE("VASCS TRANSCENDENCE", Icons.Default.AllInclusive),
    ETERNITY("VASCS ETERNITY", Icons.Default.AllInclusive),
    OMNIVERSE("VASCS OMNIVERSE", Icons.Default.Public),
    ASCENSION("VASCS ASCENSION", Icons.Default.Public),
    QUANTUM("VASCS QUANTUM", Icons.Default.AllInclusive),
    NEXUS("VASCS NEXUS", Icons.Default.Hub),
    COSMOS("VASCS COSMOS", Icons.Default.Public),
    INFINITY("VASCS INFINITY", Icons.Default.AllInclusive),
    OMEGA("VASCS OMEGA", Icons.Default.Stars),
    DASHBOARD("Dashboard", Icons.Default.Dashboard),
    WHATSAPP_COMMERCE("WhatsApp Engine", Icons.Default.Chat),
    ORDER_DISPATCH("Dispatch Hub", Icons.Default.LocalShipping),
    SOCIAL_DEALER_NETWORK("Dealer Hub", Icons.Default.Groups),
    MEDIA_COMMAND_CENTER("Media Hub", Icons.Default.PhotoLibrary),
    AI_ARCHIVE_CENTER("AI Archive", Icons.Default.AutoAwesome),
    SOCIAL_EXPORT_STUDIO("Export Studio", Icons.Default.Share),
    PHOTO_UPLOAD_STUDIO("Upload", Icons.Default.AddAPhoto),
    MEDIA_LIBRARY("Media Lib", Icons.Default.Collections),
    PRODUCTS("Catalogue", Icons.Default.ShoppingBag),
    BATCHES("Batches", Icons.Default.BatchPrediction),
    PRICING("Pricing", Icons.Default.Calculate),
    LABELS("Tag Printer", Icons.Default.QrCode)
}

class MainActivity : ComponentActivity() {

    private val viewModel: VascsViewModel by viewModels {
        VascsViewModelFactory((application as VascsApplication).repository)
    }

    private val mediaLibraryViewModel: MediaLibraryViewModel by viewModels {
        MediaLibraryViewModelFactory((application as VascsApplication).repository)
    }

    private val aiArchiveViewModel: AiArchiveViewModel by viewModels {
        AiArchiveViewModelFactory((application as VascsApplication).repository)
    }

    private val mediaCommandCenterViewModel: MediaCommandCenterViewModel by viewModels {
        MediaCommandCenterViewModelFactory((application as VascsApplication).repository)
    }

    private val dealerNetworkViewModel: DealerNetworkViewModel by viewModels {
        DealerNetworkViewModelFactory((application as VascsApplication).repository)
    }

    private val orderDispatchViewModel: OrderDispatchViewModel by viewModels {
        OrderDispatchViewModelFactory((application as VascsApplication).repository)
    }

    private val whatsappCommerceViewModel: WhatsappCommerceViewModel by viewModels {
        WhatsappCommerceViewModelFactory((application as VascsApplication).repository)
    }

    private val omegaViewModel: OmegaViewModel by viewModels {
        OmegaViewModelFactory((application as VascsApplication).repository)
    }

    private val infinityViewModel: InfinityViewModel by viewModels {
        InfinityViewModelFactory((application as VascsApplication).repository)
    }

    private val cosmosViewModel: CosmosViewModel by viewModels {
        CosmosViewModelFactory((application as VascsApplication).repository)
    }

    private val nexusViewModel: NexusViewModel by viewModels {
        NexusViewModelFactory((application as VascsApplication).repository)
    }

    private val quantumViewModel: QuantumViewModel by viewModels {
        QuantumViewModelFactory((application as VascsApplication).repository)
    }

    private val ascensionViewModel: AscensionViewModel by viewModels {
        AscensionViewModelFactory((application as VascsApplication).repository)
    }

    private val omniverseViewModel: OmniverseViewModel by viewModels {
        OmniverseViewModelFactory((application as VascsApplication).repository)
    }

    private val eternityViewModel: EternityViewModel by viewModels {
        EternityViewModelFactory((application as VascsApplication).repository)
    }

    private val transcendenceViewModel: TranscendenceViewModel by viewModels {
        TranscendenceViewModelFactory((application as VascsApplication).repository)
    }

    private val supremacyViewModel: SupremacyViewModel by viewModels {
        SupremacyViewModelFactory((application as VascsApplication).repository)
    }

    private val singularityPrimeViewModel: SingularityPrimeViewModel by viewModels {
        SingularityPrimeViewModel.Factory((application as VascsApplication).repository)
    }

    private val absoluteViewModel: AbsoluteViewModel by viewModels {
        AbsoluteViewModel.Factory((application as VascsApplication).repository)
    }

    private val ultimaViewModel: UltimaViewModel by viewModels {
        UltimaViewModel.Factory((application as VascsApplication).repository)
    }

    private val aiBrainViewModel: VascsAIBrainViewModel by viewModels {
        VascsAIBrainViewModel.Factory((application as VascsApplication).repository)
    }

    private val aiCatalogueViewModel: AICatalogueViewModel by viewModels {
        AICatalogueViewModel.Factory((application as VascsApplication).repository)
    }

    private val aiPricingViewModel: AIPricingViewModel by viewModels {
        AIPricingViewModel.Factory((application as VascsApplication).repository)
    }

    private val aiDemandForecastViewModel: AIDemandForecastViewModel by viewModels {
        AIDemandForecastViewModel.Factory((application as VascsApplication).repository)
    }

    private val aiDealerViewModel: AIDealerRecommendationViewModel by viewModels {
        AIDealerRecommendationViewModel.Factory((application as VascsApplication).repository)
    }

    private val aiInventoryViewModel: AIInventoryViewModel by viewModels {
        AIInventoryViewModel.Factory((application as VascsApplication).repository)
    }

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            VASCSTheme {
                val windowSizeClass = calculateWindowSizeClass(this)
                val isWideScreen = windowSizeClass.widthSizeClass != WindowWidthSizeClass.Compact

                VascsAppContent(
                    viewModel = viewModel,
                    mediaLibraryViewModel = mediaLibraryViewModel,
                    aiArchiveViewModel = aiArchiveViewModel,
                    mediaCommandCenterViewModel = mediaCommandCenterViewModel,
                    dealerNetworkViewModel = dealerNetworkViewModel,
                    orderDispatchViewModel = orderDispatchViewModel,
                    whatsappCommerceViewModel = whatsappCommerceViewModel,
                    omegaViewModel = omegaViewModel,
                    infinityViewModel = infinityViewModel,
                    cosmosViewModel = cosmosViewModel,
                    nexusViewModel = nexusViewModel,
                    quantumViewModel = quantumViewModel,
                    ascensionViewModel = ascensionViewModel,
                    omniverseViewModel = omniverseViewModel,
                    eternityViewModel = eternityViewModel,
                    transcendenceViewModel = transcendenceViewModel,
                    supremacyViewModel = supremacyViewModel,
                    singularityPrimeViewModel = singularityPrimeViewModel,
                    absoluteViewModel = absoluteViewModel,
                    ultimaViewModel = ultimaViewModel,
                    aiBrainViewModel = aiBrainViewModel,
                    aiCatalogueViewModel = aiCatalogueViewModel,
                    aiPricingViewModel = aiPricingViewModel,
                    aiDemandForecastViewModel = aiDemandForecastViewModel,
                    aiDealerViewModel = aiDealerViewModel,
                    aiInventoryViewModel = aiInventoryViewModel,
                    isWideScreen = isWideScreen
                )
            }
        }
    }
}

@Composable
fun VascsAppContent(
    viewModel: VascsViewModel,
    mediaLibraryViewModel: MediaLibraryViewModel,
    aiArchiveViewModel: AiArchiveViewModel,
    mediaCommandCenterViewModel: MediaCommandCenterViewModel,
    dealerNetworkViewModel: DealerNetworkViewModel,
    orderDispatchViewModel: OrderDispatchViewModel,
    whatsappCommerceViewModel: WhatsappCommerceViewModel,
    omegaViewModel: OmegaViewModel,
    infinityViewModel: InfinityViewModel,
    cosmosViewModel: CosmosViewModel,
    nexusViewModel: NexusViewModel,
    quantumViewModel: QuantumViewModel,
    ascensionViewModel: AscensionViewModel,
    omniverseViewModel: OmniverseViewModel,
    eternityViewModel: EternityViewModel,
    transcendenceViewModel: TranscendenceViewModel,
    supremacyViewModel: SupremacyViewModel,
    singularityPrimeViewModel: SingularityPrimeViewModel,
    absoluteViewModel: AbsoluteViewModel,
    ultimaViewModel: UltimaViewModel,
    aiBrainViewModel: VascsAIBrainViewModel,
    aiCatalogueViewModel: AICatalogueViewModel,
    aiPricingViewModel: AIPricingViewModel,
    aiDemandForecastViewModel: AIDemandForecastViewModel,
    aiDealerViewModel: AIDealerRecommendationViewModel,
    aiInventoryViewModel: AIInventoryViewModel,
    isWideScreen: Boolean
) {
    var currentDestination by remember { mutableStateOf(NavDestination.DASHBOARD) }

    val products by viewModel.products.collectAsState()
    val filteredProducts by viewModel.filteredProducts.collectAsState()
    val batches by viewModel.batches.collectAsState()
    val stats by viewModel.dashboardStats.collectAsState()

    val searchQuery by viewModel.searchQuery.collectAsState()
    val selectedCategory by viewModel.selectedCategory.collectAsState()

    // Dialog & Preview States
    var viewingProduct by remember { mutableStateOf<ProductEntity?>(null) }
    var editingProduct by remember { mutableStateOf<ProductEntity?>(null) }
    var managingGalleryProduct by remember { mutableStateOf<ProductEntity?>(null) }
    var generatingAiProduct by remember { mutableStateOf<ProductEntity?>(null) }
    var showNewProductDialog by remember { mutableStateOf(false) }
    var viewingBatch by remember { mutableStateOf<ProductBatchEntity?>(null) }

    // Export Preview State
    var previewExportProduct by remember { mutableStateOf<ProductEntity?>(null) }
    var previewExportType by remember { mutableStateOf<ExportType?>(null) }
    var previewExportImageUri by remember { mutableStateOf<String?>(null) }

    val categoriesList = remember(products) {
        products.map { it.category }.distinct().filter { it.isNotBlank() }.sorted()
    }

    Scaffold(
        bottomBar = {
            if (!isWideScreen) {
                NavigationBar(
                    containerColor = Maroon500,
                    contentColor = GoldAccent
                ) {
                    NavDestination.entries.forEach { destination ->
                        val isSelected = currentDestination == destination
                        NavigationBarItem(
                            selected = isSelected,
                            onClick = { currentDestination = destination },
                            icon = { Icon(destination.icon, contentDescription = destination.label) },
                            label = { Text(destination.label) },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = Maroon500,
                                selectedTextColor = GoldAccent,
                                indicatorColor = GoldAccent,
                                unselectedIconColor = GoldAccent.copy(alpha = 0.7f),
                                unselectedTextColor = GoldAccent.copy(alpha = 0.7f)
                            )
                        )
                    }
                }
            }
        }
    ) { paddingValues ->
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (isWideScreen) {
                NavigationRail(
                    containerColor = Maroon500,
                    contentColor = GoldAccent
                ) {
                    NavDestination.entries.forEach { destination ->
                        val isSelected = currentDestination == destination
                        NavigationRailItem(
                            selected = isSelected,
                            onClick = { currentDestination = destination },
                            icon = { Icon(destination.icon, contentDescription = destination.label) },
                            label = { Text(destination.label) }
                        )
                    }
                }
            }

            Box(modifier = Modifier.weight(1f)) {
                when (currentDestination) {
                    NavDestination.AI_INVENTORY -> AIInventoryScreen(
                        viewModel = aiInventoryViewModel,
                        onNavigateBack = { currentDestination = NavDestination.DASHBOARD }
                    )

                    NavDestination.AI_DEALER -> AIDealerRecommendationScreen(
                        viewModel = aiDealerViewModel,
                        onNavigateBack = { currentDestination = NavDestination.DASHBOARD }
                    )

                    NavDestination.AI_DEMAND -> AIDemandForecastScreen(
                        viewModel = aiDemandForecastViewModel,
                        onNavigateBack = { currentDestination = NavDestination.DASHBOARD }
                    )

                    NavDestination.AI_PRICING -> AIPricingScreen(
                        viewModel = aiPricingViewModel,
                        onNavigateBack = { currentDestination = NavDestination.DASHBOARD }
                    )

                    NavDestination.AI_CATALOGUE -> AICatalogueScreen(
                        viewModel = aiCatalogueViewModel,
                        onNavigateBack = { currentDestination = NavDestination.DASHBOARD }
                    )

                    NavDestination.AI_BRAIN -> AIBrainDashboardScreen(
                        viewModel = aiBrainViewModel,
                        onNavigateBack = { currentDestination = NavDestination.DASHBOARD }
                    )

                    NavDestination.ULTIMA -> UltimaPlatformScreen(
                        viewModel = ultimaViewModel,
                        onNavigateBack = { currentDestination = NavDestination.DASHBOARD }
                    )

                    NavDestination.ABSOLUTE -> AbsolutePlatformScreen(
                        viewModel = absoluteViewModel,
                        onNavigateBack = { currentDestination = NavDestination.DASHBOARD }
                    )

                    NavDestination.SINGULARITY_PRIME -> SingularityPrimePlatformScreen(
                        viewModel = singularityPrimeViewModel,
                        onNavigateBack = { currentDestination = NavDestination.DASHBOARD }
                    )

                    NavDestination.SUPREMACY -> SupremacyPlatformScreen(
                        viewModel = supremacyViewModel,
                        onNavigateBack = { currentDestination = NavDestination.DASHBOARD }
                    )

                    NavDestination.TRANSCENDENCE -> TranscendencePlatformScreen(
                        viewModel = transcendenceViewModel,
                        onBackClick = { currentDestination = NavDestination.DASHBOARD }
                    )

                    NavDestination.ETERNITY -> EternityPlatformScreen(
                        viewModel = eternityViewModel,
                        onBackClick = { currentDestination = NavDestination.DASHBOARD }
                    )

                    NavDestination.OMNIVERSE -> OmniversePlatformScreen(
                        viewModel = omniverseViewModel,
                        onBackClick = { currentDestination = NavDestination.DASHBOARD }
                    )

                    NavDestination.ASCENSION -> AscensionPlatformScreen(
                        viewModel = ascensionViewModel,
                        onBackClick = { currentDestination = NavDestination.DASHBOARD }
                    )

                    NavDestination.QUANTUM -> QuantumPlatformScreen(
                        viewModel = quantumViewModel,
                        onBackClick = { currentDestination = NavDestination.DASHBOARD }
                    )

                    NavDestination.NEXUS -> NexusPlatformScreen(
                        viewModel = nexusViewModel,
                        onBackClick = { currentDestination = NavDestination.DASHBOARD }
                    )

                    NavDestination.COSMOS -> CosmosPlatformScreen(
                        viewModel = cosmosViewModel,
                        onBackClick = { currentDestination = NavDestination.DASHBOARD }
                    )

                    NavDestination.INFINITY -> InfinityPlatformScreen(
                        viewModel = infinityViewModel,
                        onBackClick = { currentDestination = NavDestination.DASHBOARD }
                    )

                    NavDestination.OMEGA -> OmegaPlatformScreen(
                        viewModel = omegaViewModel,
                        onBackClick = { currentDestination = NavDestination.DASHBOARD }
                    )

                    NavDestination.DASHBOARD -> DashboardScreen(
                        stats = stats,
                        recentProducts = products,
                        onNavigateToProducts = { currentDestination = NavDestination.PRODUCTS },
                        onNavigateToBatches = { currentDestination = NavDestination.BATCHES },
                        onNavigateToPricing = { currentDestination = NavDestination.PRICING },
                        onNavigateToLabels = { currentDestination = NavDestination.LABELS },
                        onNavigateToPhotoUploadStudio = { currentDestination = NavDestination.PHOTO_UPLOAD_STUDIO },
                        onNavigateToMediaLibrary = { currentDestination = NavDestination.MEDIA_LIBRARY },
                        onNavigateToSocialExportStudio = { currentDestination = NavDestination.SOCIAL_EXPORT_STUDIO },
                        onNavigateToAiArchiveCenter = { currentDestination = NavDestination.AI_ARCHIVE_CENTER },
                        onNavigateToMediaCommandCenter = { currentDestination = NavDestination.MEDIA_COMMAND_CENTER },
                        onNavigateToSocialDealerNetwork = { currentDestination = NavDestination.SOCIAL_DEALER_NETWORK },
                        onNavigateToOrderDispatch = { currentDestination = NavDestination.ORDER_DISPATCH },
                        onNavigateToWhatsappCommerce = { currentDestination = NavDestination.WHATSAPP_COMMERCE },
                        onNavigateToOmega = { currentDestination = NavDestination.OMEGA },
                        onNavigateToInfinity = { currentDestination = NavDestination.INFINITY },
                        onNavigateToCosmos = { currentDestination = NavDestination.COSMOS },
                        onNavigateToNexus = { currentDestination = NavDestination.NEXUS },
                        onNavigateToQuantum = { currentDestination = NavDestination.QUANTUM },
                        onNavigateToAscension = { currentDestination = NavDestination.ASCENSION },
                        onNavigateToOmniverse = { currentDestination = NavDestination.OMNIVERSE },
                        onNavigateToEternity = { currentDestination = NavDestination.ETERNITY },
                        onNavigateToTranscendence = { currentDestination = NavDestination.TRANSCENDENCE },
                        onNavigateToSupremacy = { currentDestination = NavDestination.SUPREMACY },
                        onNavigateToSingularityPrime = { currentDestination = NavDestination.SINGULARITY_PRIME },
                        onNavigateToAbsolute = { currentDestination = NavDestination.ABSOLUTE },
                        onNavigateToUltima = { currentDestination = NavDestination.ULTIMA },
                        onNavigateToAiBrain = { currentDestination = NavDestination.AI_BRAIN },
                        onNavigateToAiCatalogue = { currentDestination = NavDestination.AI_CATALOGUE },
                        onNavigateToAiPricing = { currentDestination = NavDestination.AI_PRICING },
                        onNavigateToAiDemand = { currentDestination = NavDestination.AI_DEMAND },
                        onNavigateToAiDealer = { currentDestination = NavDestination.AI_DEALER },
                        onNavigateToAiInventory = { currentDestination = NavDestination.AI_INVENTORY },
                        onAddProductClick = { showNewProductDialog = true },
                        onProductClick = { viewingProduct = it }
                    )

                    NavDestination.WHATSAPP_COMMERCE -> WhatsappCommerceScreen(
                        viewModel = whatsappCommerceViewModel,
                        onBack = { currentDestination = NavDestination.DASHBOARD }
                    )

                    NavDestination.ORDER_DISPATCH -> OrderDispatchFactoryScreen(
                        viewModel = orderDispatchViewModel,
                        products = products
                    )

                    NavDestination.SOCIAL_DEALER_NETWORK -> SocialDealerNetworkScreen(
                        viewModel = dealerNetworkViewModel,
                        onNavigateToProduct = { targetProductId ->
                            val target = products.firstOrNull { it.id == targetProductId.toString() }
                            if (target != null) {
                                viewingProduct = target
                            }
                        }
                    )

                    NavDestination.MEDIA_COMMAND_CENTER -> MediaCommandCenterScreen(
                        viewModel = mediaCommandCenterViewModel,
                        onGotoProduct = { targetProductId ->
                            val target = products.firstOrNull { it.id == targetProductId.toString() || it.sku == targetProductId.toString() }
                            if (target != null) {
                                viewingProduct = target
                            }
                        }
                    )

                    NavDestination.AI_ARCHIVE_CENTER -> AiArchiveCenterScreen(
                        viewModel = aiArchiveViewModel,
                        onGotoProduct = { targetProductId ->
                            val target = products.firstOrNull { it.id == targetProductId.toString() || it.sku == targetProductId.toString() }
                            if (target != null) {
                                viewingProduct = target
                            }
                        }
                    )

                    NavDestination.SOCIAL_EXPORT_STUDIO -> {
                        val activePreviewProduct = previewExportProduct
                        val activePreviewType = previewExportType
                        val activePreviewUri = previewExportImageUri

                        if (activePreviewProduct != null && activePreviewType != null && activePreviewUri != null) {
                            ExportPreviewScreen(
                                product = activePreviewProduct,
                                exportType = activePreviewType,
                                exportImageUri = activePreviewUri,
                                onBack = {
                                    previewExportProduct = null
                                    previewExportType = null
                                    previewExportImageUri = null
                                }
                            )
                        } else {
                            SocialMediaExportStudioScreen(
                                viewModel = viewModel,
                                onPreviewExport = { prod, type, uri ->
                                    previewExportProduct = prod
                                    previewExportType = type
                                    previewExportImageUri = uri
                                }
                            )
                        }
                    }

                    NavDestination.PHOTO_UPLOAD_STUDIO -> PhotoUploadCenterScreen(
                        viewModel = mediaLibraryViewModel,
                        onNavigateToMediaLibrary = { currentDestination = NavDestination.MEDIA_LIBRARY }
                    )

                    NavDestination.MEDIA_LIBRARY -> MediaLibraryScreen(
                        viewModel = mediaLibraryViewModel,
                        onGotoProduct = { targetProductId ->
                            val target = products.firstOrNull { it.id == targetProductId || it.sku == targetProductId }
                            if (target != null) {
                                viewingProduct = target
                            }
                        }
                    )

                    NavDestination.PRODUCTS -> ProductsScreen(
                        products = filteredProducts,
                        searchQuery = searchQuery,
                        onSearchQueryChange = { viewModel.searchQuery.value = it },
                        selectedCategory = selectedCategory,
                        onCategorySelect = { viewModel.selectedCategory.value = it },
                        categories = categoriesList,
                        onAddProductClick = { showNewProductDialog = true },
                        onProductClick = { viewingProduct = it },
                        onEditProductClick = { editingProduct = it },
                        onDeleteProductClick = { viewModel.deleteProduct(it) }
                    )

                    NavDestination.BATCHES -> BatchesScreen(
                        batches = batches,
                        allProducts = products,
                        onCreateBatchSave = { viewModel.saveBatch(it) },
                        onDeleteBatchClick = { viewModel.deleteBatch(it) },
                        onBatchClick = { viewingBatch = it }
                    )

                    NavDestination.PRICING -> PricingCalculatorScreen()

                    NavDestination.LABELS -> LabelPrinterScreen(
                        products = products
                    )
                }
            }
        }
    }

    // Product Detail Dialog
    viewingProduct?.let { product ->
        ProductDetailDialog(
            product = product,
            onDismiss = { viewingProduct = null },
            onEditClick = {
                val p = viewingProduct
                viewingProduct = null
                editingProduct = p
            },
            onManageGalleryClick = {
                val p = viewingProduct
                viewingProduct = null
                managingGalleryProduct = p
            },
            onGenerateAiCatalogueClick = {
                val p = viewingProduct
                viewingProduct = null
                generatingAiProduct = p
            }
        )
    }

    // Product Gallery & Camera Dialog
    managingGalleryProduct?.let { product ->
        ProductImageGalleryDialog(
            product = product,
            viewModel = viewModel,
            onDismiss = { managingGalleryProduct = null }
        )
    }

    // AI Saree Catalogue Studio Dialog
    generatingAiProduct?.let { product ->
        SareeCatalogueGenerationDialog(
            product = product,
            viewModel = viewModel,
            onDismiss = { generatingAiProduct = null }
        )
    }

    // New Product Dialog
    if (showNewProductDialog) {
        ProductEditDialog(
            editingProduct = null,
            onDismiss = { showNewProductDialog = false },
            onSave = {
                viewModel.saveProduct(it)
                showNewProductDialog = false
            }
        )
    }

    // Edit Product Dialog
    editingProduct?.let { product ->
        ProductEditDialog(
            editingProduct = product,
            onDismiss = { editingProduct = null },
            onSave = {
                viewModel.updateProduct(it)
                editingProduct = null
            }
        )
    }

    // Batch Detail Dialog
    viewingBatch?.let { batch ->
        BatchDetailDialog(
            batch = batch,
            allProducts = products,
            onDismiss = { viewingBatch = null },
            onStatusChange = { targetBatch, newStatus ->
                viewModel.updateBatch(targetBatch.copy(status = newStatus))
                viewingBatch = targetBatch.copy(status = newStatus)
            }
        )
    }
}
