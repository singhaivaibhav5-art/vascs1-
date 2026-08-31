package com.example.vascs.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.vascs.data.ai.AIResponseParser
import com.example.vascs.data.model.AICatalogueRequestEntity
import com.example.vascs.data.model.AICatalogueResultEntity
import com.example.vascs.data.model.AICatalogueTemplateEntity
import com.example.vascs.data.repository.VascsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class AICatalogueInputState(
    val productName: String = "Royal Banarasi Silk Saree",
    val category: String = "Sarees",
    val fabric: String = "Pure Katan Silk",
    val color: String = "Crimson Red & Antique Gold",
    val price: String = "18500",
    val designDetails: String = "Intricate floral Kadwa Jangla weave with pure gold zari borders and heavy bridal pallu.",
    val occasion: String = "Bridal & Festive",
    val productImageUrl: String = "",
    val tone: String = "Royal & Heritage Luxury"
)

sealed interface AICatalogueExecutionState {
    object Idle : AICatalogueExecutionState
    object Loading : AICatalogueExecutionState
    data class Success(val result: AICatalogueResultEntity) : AICatalogueExecutionState
    data class Error(val message: String) : AICatalogueExecutionState
}

class AICatalogueViewModel(
    private val repository: VascsRepository
) : ViewModel() {

    // 1. Input StateFlow
    private val _catalogueInput = MutableStateFlow(AICatalogueInputState())
    val catalogueInput: StateFlow<AICatalogueInputState> = _catalogueInput.asStateFlow()

    // 2. Result StateFlow
    private val _catalogueResult = MutableStateFlow<AICatalogueResultEntity?>(null)
    val catalogueResult: StateFlow<AICatalogueResultEntity?> = _catalogueResult.asStateFlow()

    // 3. History StateFlow
    val catalogueHistory: StateFlow<List<AICatalogueResultEntity>> = repository.loadCatalogueHistory()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    // 4. Loading StateFlow
    private val _loadingState = MutableStateFlow(false)
    val loadingState: StateFlow<Boolean> = _loadingState.asStateFlow()

    // Execution UI State
    private val _executionState = MutableStateFlow<AICatalogueExecutionState>(AICatalogueExecutionState.Idle)
    val executionState: StateFlow<AICatalogueExecutionState> = _executionState.asStateFlow()

    // Templates StateFlow
    val catalogueTemplates: StateFlow<List<AICatalogueTemplateEntity>> = repository.loadCatalogueTemplates()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    private val _selectedTab = MutableStateFlow(0) // 0: Form & Output, 1: Omni-Channels, 2: History, 3: Templates
    val selectedTab: StateFlow<Int> = _selectedTab.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    init {
        viewModelScope.launch {
            repository.seedDefaultCatalogueTemplates()
        }
    }

    fun setTab(index: Int) {
        _selectedTab.value = index
    }

    fun updateProductName(value: String) = _catalogueInput.update { it.copy(productName = value) }
    fun updateCategory(value: String) = _catalogueInput.update { it.copy(category = value) }
    fun updateFabric(value: String) = _catalogueInput.update { it.copy(fabric = value) }
    fun updateColor(value: String) = _catalogueInput.update { it.copy(color = value) }
    fun updatePrice(value: String) = _catalogueInput.update { it.copy(price = value) }
    fun updateDesignDetails(value: String) = _catalogueInput.update { it.copy(designDetails = value) }
    fun updateOccasion(value: String) = _catalogueInput.update { it.copy(occasion = value) }
    fun updateProductImageUrl(value: String) = _catalogueInput.update { it.copy(productImageUrl = value) }
    fun updateTone(value: String) = _catalogueInput.update { it.copy(tone = value) }

    fun applyTemplate(template: AICatalogueTemplateEntity) {
        _catalogueInput.update {
            it.copy(
                productName = template.templateName,
                category = template.category,
                fabric = template.sampleFabric,
                color = template.sampleColor,
                designDetails = template.sampleDesignDetails,
                occasion = template.sampleOccasion,
                tone = template.tone
            )
        }
    }

    fun generateCatalogue() {
        val input = _catalogueInput.value
        val priceVal = input.price.toDoubleOrNull() ?: 0.0

        if (input.productName.isBlank()) {
            _errorMessage.value = "Please enter a valid product name."
            return
        }

        viewModelScope.launch {
            _loadingState.value = true
            _executionState.value = AICatalogueExecutionState.Loading
            _errorMessage.value = null

            try {
                val parsedResult = repository.generateCatalogue(
                    productName = input.productName.trim(),
                    category = input.category.trim(),
                    fabric = input.fabric.trim(),
                    color = input.color.trim(),
                    price = priceVal,
                    designDetails = input.designDetails.trim(),
                    occasion = input.occasion.trim(),
                    productImageUrl = input.productImageUrl.trim()
                )

                val resultEntity = AICatalogueResultEntity(
                    productName = input.productName,
                    category = input.category,
                    fabric = input.fabric,
                    color = input.color,
                    price = priceVal,
                    designDetails = input.designDetails,
                    occasion = input.occasion,
                    productImageUrl = input.productImageUrl,
                    productTitle = parsedResult.productTitle,
                    shortDescription = parsedResult.shortDescription,
                    longDescription = parsedResult.longDescription,
                    seoDescription = parsedResult.seoDescription,
                    seoKeywords = parsedResult.seoKeywords.joinToString(", "),
                    instagramCaption = parsedResult.instagramCaption,
                    facebookCaption = parsedResult.facebookCaption,
                    whatsappPromotionText = parsedResult.whatsappPromotionText,
                    dealerMarketingText = parsedResult.dealerMarketingText,
                    premiumCatalogueContent = parsedResult.premiumCatalogueContent,
                    isFallback = parsedResult.isFallback
                )

                _catalogueResult.value = resultEntity
                _executionState.value = AICatalogueExecutionState.Success(resultEntity)
            } catch (e: Exception) {
                val errorMsg = e.message ?: "Failed to generate catalogue. Please check network connection."
                _errorMessage.value = errorMsg
                _executionState.value = AICatalogueExecutionState.Error(errorMsg)
            } finally {
                _loadingState.value = false
            }
        }
    }

    fun selectHistoryItem(item: AICatalogueResultEntity) {
        _catalogueResult.value = item
        _catalogueInput.update {
            it.copy(
                productName = item.productName,
                category = item.category,
                fabric = item.fabric,
                color = item.color,
                price = item.price.toString(),
                designDetails = item.designDetails,
                occasion = item.occasion,
                productImageUrl = item.productImageUrl
            )
        }
        _selectedTab.value = 0
    }

    fun toggleFavorite(item: AICatalogueResultEntity) {
        viewModelScope.launch {
            val updated = item.copy(isFavorite = !item.isFavorite)
            repository.updateCatalogueResult(updated)
            if (_catalogueResult.value?.resultId == item.resultId) {
                _catalogueResult.value = updated
            }
        }
    }

    fun deleteCatalogueResult(id: Long) {
        viewModelScope.launch {
            repository.deleteCatalogueResult(id)
            if (_catalogueResult.value?.resultId == id) {
                _catalogueResult.value = null
            }
        }
    }

    fun clearInput() {
        _catalogueInput.value = AICatalogueInputState(
            productName = "",
            category = "Sarees",
            fabric = "",
            color = "",
            price = "",
            designDetails = "",
            occasion = "Festive",
            productImageUrl = ""
        )
        _catalogueResult.value = null
        _errorMessage.value = null
        _executionState.value = AICatalogueExecutionState.Idle
    }

    class Factory(private val repository: VascsRepository) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(AICatalogueViewModel::class.java)) {
                return AICatalogueViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }
}
