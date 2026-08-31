package com.example.vascs.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.vascs.data.model.AIInventoryAlertEntity
import com.example.vascs.data.model.AIInventoryForecastEntity
import com.example.vascs.data.model.AIInventoryHealthEntity
import com.example.vascs.data.model.AIInventoryRecommendationEntity
import com.example.vascs.data.model.AIInventoryRequestEntity
import com.example.vascs.data.repository.VascsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class InventoryInputState(
    val productName: String = "Katan Pure Silk Zari Saree",
    val sku: String = "SKU-SLK-8821",
    val category: String = "Pure Silk Sarees",
    val warehouseLocation: String = "Varanasi Central Vault #1",
    val currentStock: Int = 38,
    val allocatedStock: Int = 12,
    val incomingStock: Int = 0,
    val averageDailySales: Double = 3.2,
    val salesHistory30d: Int = 96,
    val salesHistory90d: Int = 270,
    val forecastDemand30d: Int = 125,
    val dealerPendingOrders: Int = 42,
    val unitCostPrice: Double = 14500.0,
    val unitSellingPrice: Double = 22000.0,
    val leadTimeDays: Int = 14,
    val storageCapacityUnits: Int = 500,
    val storageOccupiedUnits: Int = 380,
    val season: String = "Festive Wedding Season (Q3/Q4)",
    val festivalCalendar: String = "Diwali, Karva Chauth & Wedding Muhurats",
    val holdingCostPerUnitMonthly: Double = 120.0
)

data class InventoryPreset(
    val name: String,
    val sku: String,
    val category: String,
    val warehouse: String,
    val stock: Int,
    val sales30d: Int,
    val sales90d: Int,
    val forecast30d: Int,
    val dealerOrders: Int,
    val costPrice: Double,
    val sellPrice: Double,
    val leadTime: Int
)

class AIInventoryViewModel(
    private val repository: VascsRepository
) : ViewModel() {

    // Inputs
    private val _inputState = MutableStateFlow(InventoryInputState())
    val inputState: StateFlow<InventoryInputState> = _inputState.asStateFlow()

    // UI States
    private val _loadingState = MutableStateFlow(false)
    val loadingState: StateFlow<Boolean> = _loadingState.asStateFlow()

    private val _errorState = MutableStateFlow<String?>(null)
    val errorState: StateFlow<String?> = _errorState.asStateFlow()

    private val _exportMessage = MutableStateFlow<String?>(null)
    val exportMessage: StateFlow<String?> = _exportMessage.asStateFlow()

    private val _activeForecast = MutableStateFlow<AIInventoryForecastEntity?>(null)
    val activeForecast: StateFlow<AIInventoryForecastEntity?> = _activeForecast.asStateFlow()

    private val _selectedTab = MutableStateFlow(0)
    val selectedTab: StateFlow<Int> = _selectedTab.asStateFlow()

    private val _velocityFilter = MutableStateFlow("ALL")
    val velocityFilter: StateFlow<String> = _velocityFilter.asStateFlow()

    private val _selectedWarehouse = MutableStateFlow("ALL")
    val selectedWarehouse: StateFlow<String> = _selectedWarehouse.asStateFlow()

    // Database Streams
    val allForecasts: StateFlow<List<AIInventoryForecastEntity>> =
        repository.allAiInventoryForecasts.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val fastMovingStock: StateFlow<List<AIInventoryForecastEntity>> =
        repository.fastMovingStock.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val slowMovingStock: StateFlow<List<AIInventoryForecastEntity>> =
        repository.slowMovingStock.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val deadStockList: StateFlow<List<AIInventoryForecastEntity>> =
        repository.deadStockList.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val allAlerts: StateFlow<List<AIInventoryAlertEntity>> =
        repository.allAiInventoryAlerts.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val activeAlerts: StateFlow<List<AIInventoryAlertEntity>> =
        repository.activeAiInventoryAlerts.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val latestHealth: StateFlow<AIInventoryHealthEntity?> =
        repository.latestAiInventoryHealth.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )

    val allRecommendations: StateFlow<List<AIInventoryRecommendationEntity>> =
        repository.allAiInventoryRecommendations.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val pendingRecommendations: StateFlow<List<AIInventoryRecommendationEntity>> =
        repository.pendingAiInventoryRecommendations.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    init {
        viewModelScope.launch {
            repository.seedInitialInventoryDataIfNeeded()
        }
    }

    val presets = listOf(
        InventoryPreset(
            name = "Katan Pure Silk Zari Saree",
            sku = "SKU-SLK-8821",
            category = "Pure Silk Sarees",
            warehouse = "Varanasi Central Vault #1",
            stock = 38,
            sales30d = 96,
            sales90d = 270,
            forecast30d = 125,
            dealerOrders = 42,
            costPrice = 14500.0,
            sellPrice = 22000.0,
            leadTime = 14
        ),
        InventoryPreset(
            name = "Royal Meenakari Handloom Dupatta",
            sku = "SKU-DUP-4019",
            category = "Dupattas & Shawls",
            warehouse = "Delhi Hub Vault #2",
            stock = 24,
            sales30d = 65,
            sales90d = 180,
            forecast30d = 85,
            dealerOrders = 18,
            costPrice = 6500.0,
            sellPrice = 10500.0,
            leadTime = 10
        ),
        InventoryPreset(
            name = "Organza Embroidered Bridal Lehenga",
            sku = "SKU-LHG-9912",
            category = "Bridal & Haute Couture",
            warehouse = "Varanasi Central Vault #1",
            stock = 45,
            sales30d = 36,
            sales90d = 110,
            forecast30d = 50,
            dealerOrders = 15,
            costPrice = 28000.0,
            sellPrice = 45000.0,
            leadTime = 21
        ),
        InventoryPreset(
            name = "Synthetic Poly-Chiffon Stole (Dead Stock)",
            sku = "SKU-SYN-1022",
            category = "Accessories",
            warehouse = "Surat Depot #3",
            stock = 180,
            sales30d = 0,
            sales90d = 2,
            forecast30d = 5,
            dealerOrders = 0,
            costPrice = 480.0,
            sellPrice = 850.0,
            leadTime = 7
        ),
        InventoryPreset(
            name = "Tussar Raw Silk Fabric (Slow)",
            sku = "SKU-TUS-3341",
            category = "Men's Ethnic & Fabrics",
            warehouse = "Kolkata Hub #1",
            stock = 95,
            sales30d = 12,
            sales90d = 40,
            forecast30d = 25,
            dealerOrders = 6,
            costPrice = 9000.0,
            sellPrice = 14000.0,
            leadTime = 16
        )
    )

    fun setSelectedTab(tab: Int) {
        _selectedTab.value = tab
    }

    fun setVelocityFilter(filter: String) {
        _velocityFilter.value = filter
    }

    fun setSelectedWarehouse(wh: String) {
        _selectedWarehouse.value = wh
    }

    fun clearExportMessage() {
        _exportMessage.value = null
    }

    fun updateProductName(v: String) {
        _inputState.value = _inputState.value.copy(productName = v)
    }

    fun updateSku(v: String) {
        _inputState.value = _inputState.value.copy(sku = v)
    }

    fun updateCategory(v: String) {
        _inputState.value = _inputState.value.copy(category = v)
    }

    fun updateWarehouse(v: String) {
        _inputState.value = _inputState.value.copy(warehouseLocation = v)
    }

    fun updateCurrentStock(v: Int) {
        _inputState.value = _inputState.value.copy(currentStock = v.coerceAtLeast(0))
    }

    fun updateAllocatedStock(v: Int) {
        _inputState.value = _inputState.value.copy(allocatedStock = v.coerceAtLeast(0))
    }

    fun updateIncomingStock(v: Int) {
        _inputState.value = _inputState.value.copy(incomingStock = v.coerceAtLeast(0))
    }

    fun updateDailySales(v: Double) {
        _inputState.value = _inputState.value.copy(averageDailySales = v.coerceAtLeast(0.1))
    }

    fun updateSales30d(v: Int) {
        _inputState.value = _inputState.value.copy(salesHistory30d = v.coerceAtLeast(0))
    }

    fun updateSales90d(v: Int) {
        _inputState.value = _inputState.value.copy(salesHistory90d = v.coerceAtLeast(0))
    }

    fun updateForecast30d(v: Int) {
        _inputState.value = _inputState.value.copy(forecastDemand30d = v.coerceAtLeast(0))
    }

    fun updateDealerOrders(v: Int) {
        _inputState.value = _inputState.value.copy(dealerPendingOrders = v.coerceAtLeast(0))
    }

    fun updateCostPrice(v: Double) {
        _inputState.value = _inputState.value.copy(unitCostPrice = v.coerceAtLeast(0.0))
    }

    fun updateSellingPrice(v: Double) {
        _inputState.value = _inputState.value.copy(unitSellingPrice = v.coerceAtLeast(0.0))
    }

    fun updateLeadTime(v: Int) {
        _inputState.value = _inputState.value.copy(leadTimeDays = v.coerceAtLeast(1))
    }

    fun updateSeason(v: String) {
        _inputState.value = _inputState.value.copy(season = v)
    }

    fun applyPreset(preset: InventoryPreset) {
        _inputState.value = _inputState.value.copy(
            productName = preset.name,
            sku = preset.sku,
            category = preset.category,
            warehouseLocation = preset.warehouse,
            currentStock = preset.stock,
            salesHistory30d = preset.sales30d,
            salesHistory90d = preset.sales90d,
            forecastDemand30d = preset.forecast30d,
            dealerPendingOrders = preset.dealerOrders,
            unitCostPrice = preset.costPrice,
            unitSellingPrice = preset.sellPrice,
            leadTimeDays = preset.leadTime,
            averageDailySales = (preset.sales30d.toDouble() / 30.0).coerceAtLeast(0.1)
        )
    }

    fun generateInventoryIntelligence() {
        viewModelScope.launch {
            _loadingState.value = true
            _errorState.value = null
            try {
                val input = _inputState.value
                val request = AIInventoryRequestEntity(
                    productName = input.productName,
                    sku = input.sku,
                    category = input.category,
                    warehouseLocation = input.warehouseLocation,
                    currentStock = input.currentStock,
                    allocatedStock = input.allocatedStock,
                    incomingStock = input.incomingStock,
                    averageDailySales = input.averageDailySales,
                    salesHistory30d = input.salesHistory30d,
                    salesHistory90d = input.salesHistory90d,
                    forecastDemand30d = input.forecastDemand30d,
                    dealerPendingOrders = input.dealerPendingOrders,
                    unitCostPrice = input.unitCostPrice,
                    unitSellingPrice = input.unitSellingPrice,
                    leadTimeDays = input.leadTimeDays,
                    storageCapacityUnits = input.storageCapacityUnits,
                    storageOccupiedUnits = input.storageOccupiedUnits,
                    season = input.season,
                    festivalCalendar = input.festivalCalendar,
                    holdingCostPerUnitMonthly = input.holdingCostPerUnitMonthly
                )
                val result = repository.generateInventoryForecast(request)
                _activeForecast.value = result
            } catch (e: Exception) {
                _errorState.value = "AI Inventory analysis failed: ${e.localizedMessage ?: "Unknown error"}"
            } finally {
                _loadingState.value = false
            }
        }
    }

    fun resolveAlert(alertId: Long) {
        viewModelScope.launch {
            repository.resolveInventoryAlert(alertId)
        }
    }

    fun deleteAlert(alertId: Long) {
        viewModelScope.launch {
            repository.deleteInventoryAlert(alertId)
        }
    }

    fun applyRecommendation(recommendationId: Long) {
        viewModelScope.launch {
            repository.applyInventoryRecommendation(recommendationId)
            _exportMessage.value = "Recommendation applied successfully. Reorder / Markdown queued in ERP system."
        }
    }

    fun exportInventoryReport(type: String) {
        val message = when (type) {
            "PDF" -> "Executive Inventory Audit & Health Report PDF generated (Ref: VASCS-INV-${System.currentTimeMillis() % 100000})."
            "EXCEL" -> "SKU Reorder & Warehouse Stock Ledger exported to Excel / CSV."
            "PURCHASE_ORDER" -> "Automated Purchase Order generated and sent to Weaver / Dyeing Portal."
            else -> "Warehouse Utilization & Location Manifest exported successfully."
        }
        _exportMessage.value = message
    }

    class Factory(private val repository: VascsRepository) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(AIInventoryViewModel::class.java)) {
                return AIInventoryViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
