package com.example.vascs.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.vascs.data.model.AIDealerGrowthForecastEntity
import com.example.vascs.data.model.AIDealerRecommendationEntity
import com.example.vascs.data.model.AIDealerRequestEntity
import com.example.vascs.data.model.AIDealerScoreEntity
import com.example.vascs.data.repository.VascsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class DealerInputState(
    val dealerName: String = "Sri Kashi Silk Emporium",
    val dealerCategory: String = "Master Distributor",
    val location: String = "Varanasi (Chowk Silk Hub)",
    val salesHistoryAnnual: Double = 4850000.0,
    val salesHistoryQuarterly: Double = 1380000.0,
    val orderFrequencyPerMonth: Double = 4.5,
    val paymentPerformance: String = "Excellent (0-7d)",
    val productPreferences: String = "Pure Katan Silk & Tissue Organza",
    val growthTrendPercent: Double = 28.5,
    val dealerRating: Double = 4.8,
    val customerReachCount: Int = 2400,
    val creditLimit: Double = 1500000.0,
    val creditUsed: Double = 420000.0
)

class AIDealerRecommendationViewModel(
    private val repository: VascsRepository
) : ViewModel() {

    // Inputs
    private val _dealerInput = MutableStateFlow(DealerInputState())
    val dealerInput: StateFlow<DealerInputState> = _dealerInput.asStateFlow()

    // Loading & Error states
    private val _loadingState = MutableStateFlow(false)
    val loadingState: StateFlow<Boolean> = _loadingState.asStateFlow()

    private val _errorState = MutableStateFlow<String?>(null)
    val errorState: StateFlow<String?> = _errorState.asStateFlow()

    private val _activeRecommendation = MutableStateFlow<AIDealerRecommendationEntity?>(null)
    val activeRecommendation: StateFlow<AIDealerRecommendationEntity?> = _activeRecommendation.asStateFlow()

    private val _selectedTab = MutableStateFlow(0)
    val selectedTab: StateFlow<Int> = _selectedTab.asStateFlow()

    private val _classificationFilter = MutableStateFlow("ALL")
    val classificationFilter: StateFlow<String> = _classificationFilter.asStateFlow()

    // Stream from database
    val dealerRecommendations: StateFlow<List<AIDealerRecommendationEntity>> =
        repository.allAiDealerRecommendations.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val dealerScores: StateFlow<List<AIDealerScoreEntity>> =
        repository.allAiDealerScores.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val dealerForecasts: StateFlow<List<AIDealerGrowthForecastEntity>> =
        repository.allAiDealerGrowthForecasts.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    init {
        viewModelScope.launch {
            repository.seedDefaultDealerScores()
        }
    }

    fun setSelectedTab(tab: Int) {
        _selectedTab.value = tab
    }

    fun setClassificationFilter(filter: String) {
        _classificationFilter.value = filter
    }

    fun updateDealerName(name: String) {
        _dealerInput.value = _dealerInput.value.copy(dealerName = name)
    }

    fun updateDealerCategory(category: String) {
        _dealerInput.value = _dealerInput.value.copy(dealerCategory = category)
    }

    fun updateLocation(loc: String) {
        _dealerInput.value = _dealerInput.value.copy(location = loc)
    }

    fun updateSalesAnnual(sales: Double) {
        _dealerInput.value = _dealerInput.value.copy(salesHistoryAnnual = sales)
    }

    fun updateSalesQuarterly(sales: Double) {
        _dealerInput.value = _dealerInput.value.copy(salesHistoryQuarterly = sales)
    }

    fun updateOrderFrequency(freq: Double) {
        _dealerInput.value = _dealerInput.value.copy(orderFrequencyPerMonth = freq)
    }

    fun updatePaymentPerformance(perf: String) {
        _dealerInput.value = _dealerInput.value.copy(paymentPerformance = perf)
    }

    fun updateProductPreferences(pref: String) {
        _dealerInput.value = _dealerInput.value.copy(productPreferences = pref)
    }

    fun updateGrowthTrend(trend: Double) {
        _dealerInput.value = _dealerInput.value.copy(growthTrendPercent = trend)
    }

    fun updateDealerRating(rating: Double) {
        _dealerInput.value = _dealerInput.value.copy(dealerRating = rating)
    }

    fun updateCustomerReach(reach: Int) {
        _dealerInput.value = _dealerInput.value.copy(customerReachCount = reach)
    }

    fun updateCreditLimit(limit: Double) {
        _dealerInput.value = _dealerInput.value.copy(creditLimit = limit)
    }

    fun updateCreditUsed(used: Double) {
        _dealerInput.value = _dealerInput.value.copy(creditUsed = used)
    }

    fun loadDealerPreset(preset: DealerPreset) {
        _dealerInput.value = DealerInputState(
            dealerName = preset.name,
            dealerCategory = preset.category,
            location = preset.location,
            salesHistoryAnnual = preset.annualSales,
            salesHistoryQuarterly = preset.quarterlySales,
            orderFrequencyPerMonth = preset.orderFreq,
            paymentPerformance = preset.payment,
            productPreferences = preset.preferences,
            growthTrendPercent = preset.growthTrend,
            dealerRating = preset.rating,
            customerReachCount = preset.reach,
            creditLimit = preset.creditLimit,
            creditUsed = preset.creditUsed
        )
    }

    fun generateRecommendations() {
        viewModelScope.launch {
            _loadingState.value = true
            _errorState.value = null
            try {
                val input = _dealerInput.value
                val request = AIDealerRequestEntity(
                    dealerName = input.dealerName,
                    dealerCategory = input.dealerCategory,
                    location = input.location,
                    salesHistoryAnnual = input.salesHistoryAnnual,
                    salesHistoryQuarterly = input.salesHistoryQuarterly,
                    orderFrequencyPerMonth = input.orderFrequencyPerMonth,
                    paymentPerformance = input.paymentPerformance,
                    productPreferences = input.productPreferences,
                    growthTrendPercent = input.growthTrendPercent,
                    dealerRating = input.dealerRating,
                    customerReachCount = input.customerReachCount,
                    creditLimit = input.creditLimit,
                    creditUsed = input.creditUsed
                )

                val result = repository.generateDealerRecommendations(request)
                _activeRecommendation.value = result
            } catch (e: Exception) {
                _errorState.value = e.localizedMessage ?: "Failed to generate AI dealer recommendations"
            } finally {
                _loadingState.value = false
            }
        }
    }

    fun calculateQuickScore() {
        val input = _dealerInput.value
        val score = repository.calculateDealerScore(
            dealerName = input.dealerName,
            dealerCategory = input.dealerCategory,
            location = input.location,
            rating = input.dealerRating,
            growthPct = input.growthTrendPercent,
            orderFreqPerMonth = input.orderFrequencyPerMonth,
            paymentStr = input.paymentPerformance,
            salesAnnual = input.salesHistoryAnnual,
            customerReach = input.customerReachCount
        )
        // Switch tab to rankings to observe
        _selectedTab.value = 1
    }

    fun forecastQuickGrowth() {
        val input = _dealerInput.value
        val forecast = repository.forecastDealerGrowth(
            dealerName = input.dealerName,
            quarterlySales = input.salesHistoryQuarterly,
            growthPct = input.growthTrendPercent,
            recommendedProductMix = input.productPreferences
        )
        // Switch tab to growth forecast
        _selectedTab.value = 2
    }

    fun selectRecommendation(recommendation: AIDealerRecommendationEntity) {
        _activeRecommendation.value = recommendation
    }

    fun applyRecommendation(recommendation: AIDealerRecommendationEntity) {
        viewModelScope.launch {
            val updated = recommendation.copy(isApplied = true)
            repository.updateDealerRecommendation(updated)
            if (_activeRecommendation.value?.recommendationId == recommendation.recommendationId) {
                _activeRecommendation.value = updated
            }
        }
    }

    fun toggleFavorite(recommendation: AIDealerRecommendationEntity) {
        viewModelScope.launch {
            val updated = recommendation.copy(isFavorite = !recommendation.isFavorite)
            repository.updateDealerRecommendation(updated)
            if (_activeRecommendation.value?.recommendationId == recommendation.recommendationId) {
                _activeRecommendation.value = updated
            }
        }
    }

    fun deleteRecommendation(id: Long) {
        viewModelScope.launch {
            repository.deleteDealerRecommendation(id)
            if (_activeRecommendation.value?.recommendationId == id) {
                _activeRecommendation.value = null
            }
        }
    }

    class Factory(private val repository: VascsRepository) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(AIDealerRecommendationViewModel::class.java)) {
                return AIDealerRecommendationViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}

data class DealerPreset(
    val name: String,
    val category: String,
    val location: String,
    val annualSales: Double,
    val quarterlySales: Double,
    val orderFreq: Double,
    val payment: String,
    val preferences: String,
    val growthTrend: Double,
    val rating: Double,
    val reach: Int,
    val creditLimit: Double,
    val creditUsed: Double,
    val tag: String
)

val PRESET_DEALERS = listOf(
    DealerPreset(
        name = "Sri Kashi Silk Emporium",
        category = "Master Distributor",
        location = "Varanasi (Chowk Silk Hub)",
        annualSales = 5400000.0,
        quarterlySales = 1550000.0,
        orderFreq = 5.2,
        payment = "Excellent (0-7d)",
        preferences = "Pure Katan Silk & Bridal Sarees",
        growthTrend = 32.0,
        rating = 4.9,
        reach = 3200,
        creditLimit = 2000000.0,
        creditUsed = 450000.0,
        tag = "Top Performer Star"
    ),
    DealerPreset(
        name = "Surat Glamour Fabrics",
        category = "Tier 1 Wholesaler",
        location = "Surat (Millennium Textile Mkt)",
        annualSales = 3800000.0,
        quarterlySales = 1100000.0,
        orderFreq = 3.8,
        payment = "Good (8-15d)",
        preferences = "Tissue Organza & Digital Prints",
        growthTrend = 41.5,
        rating = 4.6,
        reach = 1900,
        creditLimit = 1200000.0,
        creditUsed = 380000.0,
        tag = "High Growth Velocity"
    ),
    DealerPreset(
        name = "Heritage Royal Textiles",
        category = "Multi-Brand Retailer",
        location = "Jaipur (Johari Bazaar)",
        annualSales = 2900000.0,
        quarterlySales = 750000.0,
        orderFreq = 2.5,
        payment = "Good (8-15d)",
        preferences = "Chanderi Cotton & Gotta Patti Dupattas",
        growthTrend = 18.0,
        rating = 4.4,
        reach = 2600,
        creditLimit = 800000.0,
        creditUsed = 210000.0,
        tag = "Expansion Target"
    ),
    DealerPreset(
        name = "Deccan Weaves & Silks",
        category = "Regional Wholesaler",
        location = "Hyderabad (Madina Market)",
        annualSales = 2200000.0,
        quarterlySales = 380000.0,
        orderFreq = 1.8,
        payment = "Moderate (16-30d)",
        preferences = "South Silk & Pochampally Ikat",
        growthTrend = -8.5,
        rating = 4.0,
        reach = 1400,
        creditLimit = 600000.0,
        creditUsed = 420000.0,
        tag = "Recovery Opportunity"
    ),
    DealerPreset(
        name = "Metro Saree House",
        category = "Retail Partner",
        location = "Kolkata (Burrabazar)",
        annualSales = 1500000.0,
        quarterlySales = 290000.0,
        orderFreq = 1.2,
        payment = "Delayed (>30d)",
        preferences = "Handloom Daily Wear & Linen",
        growthTrend = -14.0,
        rating = 3.6,
        reach = 950,
        creditLimit = 500000.0,
        creditUsed = 480000.0,
        tag = "Risk Alert Account"
    )
)
