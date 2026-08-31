package com.example.vascs.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.vascs.data.model.AIPricingHistoryEntity
import com.example.vascs.data.model.AIPricingResultEntity
import com.example.vascs.data.model.AIPricingRuleEntity
import com.example.vascs.data.repository.VascsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class AIPricingInputState(
    val productName: String = "Royal Banarasi Silk Saree",
    val costPrice: String = "6500",
    val category: String = "Bridal Silk Sarees",
    val brand: String = "VASCS Heritage",
    val fabricType: String = "Pure Mulberry Katan Silk",
    val dealerCategory: String = "Tier 1 Wholesaler",
    val existingSellingPrice: String = "14500",
    val competitorPrice: String = "15200",
    val targetMargin: String = "38.0",
    val region: String = "Pan-India / North Mandis",
    val marketType: String = "Luxury B2B & Wholesale"
)

data class MarginCalculatorState(
    val customCost: String = "6500",
    val customSellingPrice: String = "14500",
    val customDealerDiscountPct: String = "15.0",
    val calculatedMarginPct: Double = 55.17,
    val calculatedProfitPct: Double = 123.08,
    val calculatedDealerNet: Double = 12325.0,
    val calculatedDealerMargin: Double = 47.26
)

sealed interface AIPricingExecutionState {
    object Idle : AIPricingExecutionState
    object Loading : AIPricingExecutionState
    data class Success(val result: AIPricingResultEntity) : AIPricingExecutionState
    data class Error(val message: String) : AIPricingExecutionState
}

class AIPricingViewModel(
    private val repository: VascsRepository
) : ViewModel() {

    // 1. Input StateFlow
    private val _pricingInput = MutableStateFlow(AIPricingInputState())
    val pricingInput: StateFlow<AIPricingInputState> = _pricingInput.asStateFlow()

    // 2. Result StateFlow
    private val _pricingResult = MutableStateFlow<AIPricingResultEntity?>(null)
    val pricingResult: StateFlow<AIPricingResultEntity?> = _pricingResult.asStateFlow()

    // 3. History StateFlow
    val pricingHistory: StateFlow<List<AIPricingHistoryEntity>> = repository.loadPricingHistory()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    // 4. Loading StateFlow
    private val _loadingState = MutableStateFlow(false)
    val loadingState: StateFlow<Boolean> = _loadingState.asStateFlow()

    // 5. Error StateFlow
    private val _errorState = MutableStateFlow<String?>(null)
    val errorState: StateFlow<String?> = _errorState.asStateFlow()

    // Execution UI State
    private val _executionState = MutableStateFlow<AIPricingExecutionState>(AIPricingExecutionState.Idle)
    val executionState: StateFlow<AIPricingExecutionState> = _executionState.asStateFlow()

    // Rules StateFlow
    val pricingRules: StateFlow<List<AIPricingRuleEntity>> = repository.allPricingRules
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    // Interactive Margin Calculator State
    private val _calculatorState = MutableStateFlow(MarginCalculatorState())
    val calculatorState: StateFlow<MarginCalculatorState> = _calculatorState.asStateFlow()

    private val _selectedTab = MutableStateFlow(0) // 0: Pricing Form & AI Breakdown, 1: Multi-Channel Matrix & Calculator, 2: Analytics & Competitor Comparison, 3: History & Rules
    val selectedTab: StateFlow<Int> = _selectedTab.asStateFlow()

    init {
        viewModelScope.launch {
            repository.seedDefaultPricingRules()
            recalculateCustomMargins()
        }
    }

    fun setTab(index: Int) {
        _selectedTab.value = index
    }

    fun updateProductName(value: String) = _pricingInput.update { it.copy(productName = value) }
    fun updateCostPrice(value: String) = _pricingInput.update { it.copy(costPrice = value) }
    fun updateCategory(value: String) = _pricingInput.update { it.copy(category = value) }
    fun updateBrand(value: String) = _pricingInput.update { it.copy(brand = value) }
    fun updateFabricType(value: String) = _pricingInput.update { it.copy(fabricType = value) }
    fun updateDealerCategory(value: String) = _pricingInput.update { it.copy(dealerCategory = value) }
    fun updateExistingSellingPrice(value: String) = _pricingInput.update { it.copy(existingSellingPrice = value) }
    fun updateCompetitorPrice(value: String) = _pricingInput.update { it.copy(competitorPrice = value) }
    fun updateTargetMargin(value: String) = _pricingInput.update { it.copy(targetMargin = value) }
    fun updateRegion(value: String) = _pricingInput.update { it.copy(region = value) }
    fun updateMarketType(value: String) = _pricingInput.update { it.copy(marketType = value) }

    fun updateCalculatorCost(value: String) {
        _calculatorState.update { it.copy(customCost = value) }
        recalculateCustomMargins()
    }

    fun updateCalculatorSelling(value: String) {
        _calculatorState.update { it.copy(customSellingPrice = value) }
        recalculateCustomMargins()
    }

    fun updateCalculatorDiscount(value: String) {
        _calculatorState.update { it.copy(customDealerDiscountPct = value) }
        recalculateCustomMargins()
    }

    private fun recalculateCustomMargins() {
        val cost = _calculatorState.value.customCost.toDoubleOrNull() ?: 0.0
        val sell = _calculatorState.value.customSellingPrice.toDoubleOrNull() ?: 0.0
        val discPct = _calculatorState.value.customDealerDiscountPct.toDoubleOrNull() ?: 0.0

        if (sell > 0 && cost > 0) {
            val margin = ((sell - cost) / sell) * 100.0
            val profit = ((sell - cost) / cost) * 100.0
            val dealerNet = sell * (1.0 - (discPct / 100.0))
            val dealerMargin = if (dealerNet > 0) ((dealerNet - cost) / dealerNet) * 100.0 else 0.0

            _calculatorState.update {
                it.copy(
                    calculatedMarginPct = Math.round(margin * 100.0) / 100.0,
                    calculatedProfitPct = Math.round(profit * 100.0) / 100.0,
                    calculatedDealerNet = Math.round(dealerNet * 100.0) / 100.0,
                    calculatedDealerMargin = Math.round(dealerMargin * 100.0) / 100.0
                )
            }
        }
    }

    fun applyRule(rule: AIPricingRuleEntity) {
        val costNum = _pricingInput.value.costPrice.toDoubleOrNull() ?: 5000.0
        val targetSelling = costNum * rule.retailMultiplier

        _pricingInput.update {
            it.copy(
                category = rule.category,
                fabricType = rule.fabricType,
                targetMargin = rule.targetMarginPercent.toString(),
                existingSellingPrice = targetSelling.toString()
            )
        }
    }

    fun generatePricingRecommendation() {
        val input = _pricingInput.value
        val costNum = input.costPrice.toDoubleOrNull()
        if (costNum == null || costNum <= 0) {
            _errorState.value = "Please provide a valid cost price (greater than 0)."
            return
        }

        viewModelScope.launch {
            _loadingState.value = true
            _errorState.value = null
            _executionState.value = AIPricingExecutionState.Loading

            try {
                val parsed = repository.generatePricingRecommendation(
                    productName = input.productName.trim().ifEmpty { "Luxury Apparel SKU" },
                    costPrice = costNum,
                    category = input.category.trim().ifEmpty { "General Apparel" },
                    brand = input.brand.trim().ifEmpty { "VASCS Heritage" },
                    fabricType = input.fabricType.trim().ifEmpty { "Premium Textile" },
                    dealerCategory = input.dealerCategory,
                    existingSellingPrice = input.existingSellingPrice.toDoubleOrNull() ?: 0.0,
                    competitorPrice = input.competitorPrice.toDoubleOrNull() ?: 0.0,
                    targetMargin = input.targetMargin.toDoubleOrNull() ?: 35.0,
                    region = input.region,
                    marketType = input.marketType
                )

                val resultEntity = AIPricingResultEntity(
                    productName = input.productName,
                    costPrice = costNum,
                    category = input.category,
                    brand = input.brand,
                    fabricType = input.fabricType,
                    dealerCategory = input.dealerCategory,
                    existingSellingPrice = input.existingSellingPrice.toDoubleOrNull() ?: 0.0,
                    competitorPrice = input.competitorPrice.toDoubleOrNull() ?: 0.0,
                    targetMargin = input.targetMargin.toDoubleOrNull() ?: 35.0,
                    region = input.region,
                    marketType = input.marketType,
                    retailPrice = parsed.retailPrice,
                    wholesalePrice = parsed.wholesalePrice,
                    distributorPrice = parsed.distributorPrice,
                    dealerPrice = parsed.dealerPrice,
                    premiumPrice = parsed.premiumPrice,
                    discountLimit = parsed.discountLimit,
                    recommendedMargin = parsed.recommendedMargin,
                    profitPercentage = parsed.profitPercentage,
                    marketCompetitivenessScore = parsed.marketCompetitivenessScore,
                    priceConfidenceScore = parsed.priceConfidenceScore,
                    competitorDifference = parsed.competitorDifference,
                    priceStrength = parsed.priceStrength,
                    marketRank = parsed.marketRank,
                    aiRationale = parsed.aiRationale,
                    volumeBreakEvenUnits = parsed.volumeBreakEvenUnits,
                    channelAdvice = parsed.channelAdvice,
                    isFallback = parsed.isFallback
                )

                _pricingResult.value = resultEntity
                _executionState.value = AIPricingExecutionState.Success(resultEntity)

                // Sync with calculator
                _calculatorState.update {
                    it.copy(
                        customCost = costNum.toString(),
                        customSellingPrice = parsed.retailPrice.toString(),
                        customDealerDiscountPct = parsed.discountLimit.toString()
                    )
                }
                recalculateCustomMargins()

            } catch (e: Exception) {
                val errorMsg = "Pricing generation encounter: ${e.localizedMessage ?: "Unknown error"}"
                _errorState.value = errorMsg
                _executionState.value = AIPricingExecutionState.Error(errorMsg)
            } finally {
                _loadingState.value = false
            }
        }
    }

    fun selectHistoryItem(item: AIPricingHistoryEntity) {
        val result = AIPricingResultEntity(
            resultId = item.resultId,
            productName = item.productName,
            costPrice = item.costPrice,
            category = item.category,
            brand = "VASCS Heritage",
            fabricType = item.fabricType,
            dealerCategory = "Tier 1 Wholesaler",
            existingSellingPrice = item.retailPrice,
            competitorPrice = 0.0,
            targetMargin = item.recommendedMargin,
            region = "Pan-India",
            marketType = "B2B & Retail",
            retailPrice = item.retailPrice,
            wholesalePrice = item.wholesalePrice,
            distributorPrice = item.distributorPrice,
            dealerPrice = item.dealerPrice,
            premiumPrice = item.retailPrice * 1.25,
            discountLimit = 15.0,
            recommendedMargin = item.recommendedMargin,
            profitPercentage = item.profitPercentage,
            marketCompetitivenessScore = item.marketCompetitivenessScore,
            priceConfidenceScore = 90,
            competitorDifference = 0.0,
            priceStrength = "Historical Benchmark",
            marketRank = "#1 Recommended Tier",
            aiRationale = "Restored from pricing audit log at timestamp ${item.timestamp}",
            volumeBreakEvenUnits = 80,
            channelAdvice = "Standard dealer terms applicable.",
            isFallback = false
        )
        _pricingResult.value = result
        _selectedTab.value = 0
    }

    fun deleteHistoryItem(id: Long) {
        viewModelScope.launch {
            repository.deletePricingHistory(id)
        }
    }

    fun clearHistory() {
        viewModelScope.launch {
            repository.clearPricingHistory()
        }
    }

    fun toggleFavorite(result: AIPricingResultEntity) {
        viewModelScope.launch {
            val updated = result.copy(isFavorite = !result.isFavorite)
            repository.updatePricingResult(updated)
            if (_pricingResult.value?.resultId == result.resultId) {
                _pricingResult.value = updated
            }
        }
    }

    class Factory(private val repository: VascsRepository) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(AIPricingViewModel::class.java)) {
                return AIPricingViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }
}
