package com.example.vascs.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.vascs.data.ai.AIResponseParser
import com.example.vascs.data.ai.GeminiConfig
import com.example.vascs.data.model.AIConversationEntity
import com.example.vascs.data.model.AIPromptEntity
import com.example.vascs.data.model.AISuggestionEntity
import com.example.vascs.data.repository.VascsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

sealed interface AICatalogueUiState {
    object Idle : AICatalogueUiState
    object Loading : AICatalogueUiState
    data class Success(val result: AIResponseParser.CatalogueResult) : AICatalogueUiState
    data class Error(val message: String) : AICatalogueUiState
}

sealed interface AIPricingUiState {
    object Idle : AIPricingUiState
    object Loading : AIPricingUiState
    data class Success(val result: AIResponseParser.PricingResult) : AIPricingUiState
    data class Error(val message: String) : AIPricingUiState
}

sealed interface AIDemandUiState {
    object Idle : AIDemandUiState
    object Loading : AIDemandUiState
    data class Success(val result: AIResponseParser.DemandForecastResult) : AIDemandUiState
    data class Error(val message: String) : AIDemandUiState
}

sealed interface AIDealerUiState {
    object Idle : AIDealerUiState
    object Loading : AIDealerUiState
    data class Success(val result: AIResponseParser.DealerRecommendationResult) : AIDealerUiState
    data class Error(val message: String) : AIDealerUiState
}

sealed interface AIStrategyUiState {
    object Idle : AIStrategyUiState
    object Loading : AIStrategyUiState
    data class Success(val result: AIResponseParser.StrategyResult) : AIStrategyUiState
    data class Error(val message: String) : AIStrategyUiState
}

class VascsAIBrainViewModel(
    private val repository: VascsRepository
) : ViewModel() {

    private val _catalogueState = MutableStateFlow<AICatalogueUiState>(AICatalogueUiState.Idle)
    val catalogueState: StateFlow<AICatalogueUiState> = _catalogueState.asStateFlow()

    private val _pricingState = MutableStateFlow<AIPricingUiState>(AIPricingUiState.Idle)
    val pricingState: StateFlow<AIPricingUiState> = _pricingState.asStateFlow()

    private val _forecastState = MutableStateFlow<AIDemandUiState>(AIDemandUiState.Idle)
    val forecastState: StateFlow<AIDemandUiState> = _forecastState.asStateFlow()

    private val _dealerState = MutableStateFlow<AIDealerUiState>(AIDealerUiState.Idle)
    val dealerState: StateFlow<AIDealerUiState> = _dealerState.asStateFlow()

    private val _strategyState = MutableStateFlow<AIStrategyUiState>(AIStrategyUiState.Idle)
    val strategyState: StateFlow<AIStrategyUiState> = _strategyState.asStateFlow()

    val allAiPrompts: StateFlow<List<AIPromptEntity>> = repository.allAiPrompts
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val allAiSuggestions: StateFlow<List<AISuggestionEntity>> = repository.allAiSuggestions
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val allAiConversations: StateFlow<List<AIConversationEntity>> = repository.allAiConversations
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _isApiKeyConfigured = MutableStateFlow(GeminiConfig.isConfigured())
    val isApiKeyConfigured: StateFlow<Boolean> = _isApiKeyConfigured.asStateFlow()

    fun updateApiKey(newKey: String) {
        GeminiConfig.setCustomApiKey(newKey)
        _isApiKeyConfigured.value = GeminiConfig.isConfigured()
    }

    /**
     * AI Catalogue Generator Action
     */
    fun generateCatalogue(
        productName: String,
        category: String,
        fabric: String,
        color: String,
        price: Double
    ) {
        viewModelScope.launch {
            _catalogueState.value = AICatalogueUiState.Loading
            try {
                val result = repository.generateAiCatalogue(productName, category, fabric, color, price)
                _catalogueState.value = AICatalogueUiState.Success(result)
            } catch (e: Exception) {
                _catalogueState.value = AICatalogueUiState.Error(e.localizedMessage ?: "Failed to generate catalogue")
            }
        }
    }

    /**
     * AI Pricing Engine Action
     */
    fun calculatePricing(
        costPrice: Double,
        category: String,
        marginRules: String
    ) {
        viewModelScope.launch {
            _pricingState.value = AIPricingUiState.Loading
            try {
                val result = repository.calculateAiPricing(costPrice, category, marginRules)
                _pricingState.value = AIPricingUiState.Success(result)
            } catch (e: Exception) {
                _pricingState.value = AIPricingUiState.Error(e.localizedMessage ?: "Failed to calculate pricing")
            }
        }
    }

    /**
     * AI Demand Forecast Action
     */
    fun forecastDemand(
        salesHistorySummary: String,
        category: String,
        season: String
    ) {
        viewModelScope.launch {
            _forecastState.value = AIDemandUiState.Loading
            try {
                val result = repository.forecastAiDemand(salesHistorySummary, category, season)
                _forecastState.value = AIDemandUiState.Success(result)
            } catch (e: Exception) {
                _forecastState.value = AIDemandUiState.Error(e.localizedMessage ?: "Failed to forecast demand")
            }
        }
    }

    /**
     * AI Dealer Recommendation Action
     */
    fun recommendDealers(
        dealerPerformanceData: String,
        location: String,
        category: String
    ) {
        viewModelScope.launch {
            _dealerState.value = AIDealerUiState.Loading
            try {
                val result = repository.recommendAiDealers(dealerPerformanceData, location, category)
                _dealerState.value = AIDealerUiState.Success(result)
            } catch (e: Exception) {
                _dealerState.value = AIDealerUiState.Error(e.localizedMessage ?: "Failed to recommend dealers")
            }
        }
    }

    /**
     * AI Strategy Action
     */
    fun generateStrategy(
        businessContext: String,
        targetGoals: String
    ) {
        viewModelScope.launch {
            _strategyState.value = AIStrategyUiState.Loading
            try {
                val result = repository.generateAiStrategy(businessContext, targetGoals)
                _strategyState.value = AIStrategyUiState.Success(result)
            } catch (e: Exception) {
                _strategyState.value = AIStrategyUiState.Error(e.localizedMessage ?: "Failed to generate strategy")
            }
        }
    }

    fun clearState(tabIndex: Int) {
        when (tabIndex) {
            0 -> _catalogueState.value = AICatalogueUiState.Idle
            1 -> _pricingState.value = AIPricingUiState.Idle
            2 -> _forecastState.value = AIDemandUiState.Idle
            3 -> _dealerState.value = AIDealerUiState.Idle
            4 -> _strategyState.value = AIStrategyUiState.Idle
        }
    }

    class Factory(private val repository: VascsRepository) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(VascsAIBrainViewModel::class.java)) {
                return VascsAIBrainViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }
}
