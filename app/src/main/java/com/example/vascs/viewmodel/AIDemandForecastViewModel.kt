package com.example.vascs.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.vascs.data.model.AIDemandForecastEntity
import com.example.vascs.data.model.AIDemandHistoryEntity
import com.example.vascs.data.model.AIDemandModelEntity
import com.example.vascs.data.model.AIDemandRequestEntity
import com.example.vascs.data.repository.VascsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

enum class DemandTab {
    FORECAST_STUDIO,
    INVENTORY_PLANNER,
    RISK_RADAR,
    HISTORY_LOGS
}

enum class ForecastHorizon(val label: String, val days: Int) {
    SEVEN_DAYS("7 Days", 7),
    THIRTY_DAYS("30 Days", 30),
    NINETY_DAYS("90 Days", 90),
    ONE_YEAR("1 Year", 365)
}

data class DemandProductPreset(
    val productName: String,
    val sku: String,
    val category: String,
    val region: String,
    val dealerNetwork: String,
    val season: String,
    val festivalCalendar: String,
    val marketingCampaignData: String,
    val currentInventory: Int,
    val salesHistory30d: Int,
    val salesHistory90d: Int,
    val salesHistory1y: Int,
    val unitPrice: Double,
    val leadTimeDays: Int
)

class AIDemandForecastViewModel(
    private val repository: VascsRepository
) : ViewModel() {

    private val _activeTab = MutableStateFlow(DemandTab.FORECAST_STUDIO)
    val activeTab: StateFlow<DemandTab> = _activeTab.asStateFlow()

    private val _selectedHorizon = MutableStateFlow(ForecastHorizon.THIRTY_DAYS)
    val selectedHorizon: StateFlow<ForecastHorizon> = _selectedHorizon.asStateFlow()

    private val _forecastInput = MutableStateFlow(
        AIDemandRequestEntity(
            productName = "Royal Banarasi Pure Silk Saree (Katan Gold)",
            sku = "SKU-SLK-BNR-01",
            category = "Bridal Silk Sarees",
            region = "Pan-India & North Zone",
            dealerNetwork = "Tier 1 Wholesalers & Bridal Boutiques",
            season = "Wedding & Festive Season (Q3/Q4)",
            festivalCalendar = "Diwali, Karwa Chauth & Winter Wedding Muhurats",
            marketingCampaignData = "Instagram Bridal Spotlight & WhatsApp VIP Dealer Catalog",
            currentInventory = 120,
            salesHistory30d = 85,
            salesHistory90d = 240,
            salesHistory1y = 920,
            unitPrice = 14500.0,
            leadTimeDays = 18
        )
    )
    val forecastInput: StateFlow<AIDemandRequestEntity> = _forecastInput.asStateFlow()

    private val _forecastResult = MutableStateFlow<AIDemandForecastEntity?>(null)
    val forecastResult: StateFlow<AIDemandForecastEntity?> = _forecastResult.asStateFlow()

    private val _loadingState = MutableStateFlow(false)
    val loadingState: StateFlow<Boolean> = _loadingState.asStateFlow()

    private val _errorState = MutableStateFlow<String?>(null)
    val errorState: StateFlow<String?> = _errorState.asStateFlow()

    private val _successMessage = MutableStateFlow<String?>(null)
    val successMessage: StateFlow<String?> = _successMessage.asStateFlow()

    val forecastHistory: StateFlow<List<AIDemandHistoryEntity>> = repository.demandHistoryList
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val savedForecasts: StateFlow<List<AIDemandForecastEntity>> = repository.allDemandForecasts
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val demandModels: StateFlow<List<AIDemandModelEntity>> = repository.allDemandModels
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val presets = listOf(
        DemandProductPreset(
            productName = "Royal Banarasi Pure Silk Saree (Katan Gold)",
            sku = "SKU-SLK-BNR-01",
            category = "Bridal Silk Sarees",
            region = "Pan-India & North Zone",
            dealerNetwork = "Tier 1 Wholesalers & Bridal Boutiques",
            season = "Wedding & Festive Season (Q3/Q4)",
            festivalCalendar = "Diwali, Karwa Chauth & Winter Muhurats",
            marketingCampaignData = "Instagram Bridal Spotlight Blitz",
            currentInventory = 120,
            salesHistory30d = 85,
            salesHistory90d = 240,
            salesHistory1y = 920,
            unitPrice = 14500.0,
            leadTimeDays = 18
        ),
        DemandProductPreset(
            productName = "Zari Tissue Organza Bridal Lehenga Set",
            sku = "SKU-ORG-LHG-09",
            category = "Lehengas & Dupattas",
            region = "West & North-West Urban Metros",
            dealerNetwork = "Multi-Brand Flagships & Premium Retailers",
            season = "Pre-Winter Wedding Rush",
            festivalCalendar = "Navratri, Dussehra & Wedding Receptions",
            marketingCampaignData = "Celebrity Influencer Styling Video Series",
            currentInventory = 45,
            salesHistory30d = 62,
            salesHistory90d = 155,
            salesHistory1y = 480,
            unitPrice = 28000.0,
            leadTimeDays = 24
        ),
        DemandProductPreset(
            productName = "Pure Matka Raw Silk Groom Sherwani",
            sku = "SKU-MEN-SHR-04",
            category = "Men's Luxury Ethnic",
            region = "North & Central Zone",
            dealerNetwork = "Bespoke Ethnic Couture Boutiques",
            season = "Winter Marriage Calendar (Nov-Feb)",
            festivalCalendar = "December Grand Wedding Muhurats",
            marketingCampaignData = "Dealer Trunk Show Lookbook & WhatsApp Push",
            currentInventory = 30,
            salesHistory30d = 28,
            salesHistory90d = 72,
            salesHistory1y = 210,
            unitPrice = 22500.0,
            leadTimeDays = 21
        ),
        DemandProductPreset(
            productName = "Chanderi Cotton Zari Border Handloom Saree",
            sku = "SKU-CHD-COT-12",
            category = "Daily & Semi-Festive",
            region = "South & Central Zone Distributors",
            dealerNetwork = "High-Volume Mandi Wholesalers",
            season = "Post-Monsoon Festive Everyday",
            festivalCalendar = "Ganesh Chaturthi & Onam",
            marketingCampaignData = "B2B WhatsApp Catalog Broadcast",
            currentInventory = 280,
            salesHistory30d = 210,
            salesHistory90d = 590,
            salesHistory1y = 2400,
            unitPrice = 3200.0,
            leadTimeDays = 10
        )
    )

    init {
        viewModelScope.launch {
            repository.seedDefaultDemandModels()
            // Check if there is an existing forecast or create default
            repository.latestDemandForecast.collect { latest ->
                if (_forecastResult.value == null && latest != null) {
                    _forecastResult.value = latest
                }
            }
        }
    }

    fun selectTab(tab: DemandTab) {
        _activeTab.value = tab
    }

    fun selectHorizon(horizon: ForecastHorizon) {
        _selectedHorizon.value = horizon
    }

    fun loadPreset(preset: DemandProductPreset) {
        _forecastInput.value = _forecastInput.value.copy(
            productName = preset.productName,
            sku = preset.sku,
            category = preset.category,
            region = preset.region,
            dealerNetwork = preset.dealerNetwork,
            season = preset.season,
            festivalCalendar = preset.festivalCalendar,
            marketingCampaignData = preset.marketingCampaignData,
            currentInventory = preset.currentInventory,
            salesHistory30d = preset.salesHistory30d,
            salesHistory90d = preset.salesHistory90d,
            salesHistory1y = preset.salesHistory1y,
            unitPrice = preset.unitPrice,
            leadTimeDays = preset.leadTimeDays
        )
        _successMessage.value = "Loaded preset: ${preset.productName}"
    }

    fun updateProductName(value: String) {
        _forecastInput.value = _forecastInput.value.copy(productName = value)
    }

    fun updateSku(value: String) {
        _forecastInput.value = _forecastInput.value.copy(sku = value)
    }

    fun updateCategory(value: String) {
        _forecastInput.value = _forecastInput.value.copy(category = value)
    }

    fun updateRegion(value: String) {
        _forecastInput.value = _forecastInput.value.copy(region = value)
    }

    fun updateDealerNetwork(value: String) {
        _forecastInput.value = _forecastInput.value.copy(dealerNetwork = value)
    }

    fun updateSeason(value: String) {
        _forecastInput.value = _forecastInput.value.copy(season = value)
    }

    fun updateFestivalCalendar(value: String) {
        _forecastInput.value = _forecastInput.value.copy(festivalCalendar = value)
    }

    fun updateMarketingCampaign(value: String) {
        _forecastInput.value = _forecastInput.value.copy(marketingCampaignData = value)
    }

    fun updateCurrentInventory(value: Int) {
        _forecastInput.value = _forecastInput.value.copy(currentInventory = value.coerceAtLeast(0))
    }

    fun updateSalesHistory30d(value: Int) {
        _forecastInput.value = _forecastInput.value.copy(salesHistory30d = value.coerceAtLeast(0))
    }

    fun updateSalesHistory90d(value: Int) {
        _forecastInput.value = _forecastInput.value.copy(salesHistory90d = value.coerceAtLeast(0))
    }

    fun updateSalesHistory1y(value: Int) {
        _forecastInput.value = _forecastInput.value.copy(salesHistory1y = value.coerceAtLeast(0))
    }

    fun updateUnitPrice(value: Double) {
        _forecastInput.value = _forecastInput.value.copy(unitPrice = value.coerceAtLeast(0.0))
    }

    fun updateLeadTimeDays(value: Int) {
        _forecastInput.value = _forecastInput.value.copy(leadTimeDays = value.coerceAtLeast(1))
    }

    fun generateForecast() {
        viewModelScope.launch {
            _loadingState.value = true
            _errorState.value = null
            try {
                val input = _forecastInput.value
                val result = repository.generateDemandForecast(input)
                _forecastResult.value = result
                _successMessage.value = "AI Demand Forecast generated successfully!"
            } catch (e: Exception) {
                _errorState.value = e.message ?: "Failed to generate demand forecast"
            } finally {
                _loadingState.value = false
            }
        }
    }

    fun toggleFavorite(forecast: AIDemandForecastEntity) {
        viewModelScope.launch {
            val updated = forecast.copy(isFavorite = !forecast.isFavorite)
            repository.updateDemandForecast(updated)
            if (_forecastResult.value?.forecastId == forecast.forecastId) {
                _forecastResult.value = updated
            }
            _successMessage.value = if (updated.isFavorite) "Added to Favorites" else "Removed from Favorites"
        }
    }

    fun applyReorderPlan(forecast: AIDemandForecastEntity) {
        viewModelScope.launch {
            val updated = forecast.copy(isApplied = true)
            repository.updateDemandForecast(updated)
            repository.saveDemandHistory(
                AIDemandHistoryEntity(
                    forecastId = forecast.forecastId,
                    productName = forecast.productName,
                    sku = forecast.sku,
                    category = forecast.category,
                    currentInventory = forecast.currentInventory,
                    forecast30dUnits = forecast.forecast30dUnits,
                    forecast90dUnits = forecast.forecast90dUnits,
                    reorderQuantity = forecast.reorderQuantity,
                    safetyStock = forecast.safetyStockRecommendation,
                    deadStockRisk = forecast.deadStockRisk,
                    growthOpportunityScore = forecast.growthOpportunityScore,
                    actionTaken = "Triggered Reorder PO for ${forecast.reorderQuantity} Units"
                )
            )
            if (_forecastResult.value?.forecastId == forecast.forecastId) {
                _forecastResult.value = updated
            }
            _successMessage.value = "Reorder Plan of ${forecast.reorderQuantity} units successfully submitted to Weavers!"
        }
    }

    fun deleteHistoryItem(id: Long) {
        viewModelScope.launch {
            repository.deleteDemandHistory(id)
            _successMessage.value = "History entry deleted"
        }
    }

    fun clearAllHistory() {
        viewModelScope.launch {
            repository.clearDemandHistory()
            _successMessage.value = "All demand history logs cleared"
        }
    }

    fun clearMessages() {
        _errorState.value = null
        _successMessage.value = null
    }

    class Factory(private val repository: VascsRepository) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(AIDemandForecastViewModel::class.java)) {
                return AIDemandForecastViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
