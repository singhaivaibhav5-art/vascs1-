package com.example.vascs.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.vascs.data.model.CountryEntity
import com.example.vascs.data.model.CurrencyEntity
import com.example.vascs.data.model.CurrencyRateEntity
import com.example.vascs.data.model.ExportDocumentEntity
import com.example.vascs.data.model.GlobalShipmentEntity
import com.example.vascs.data.model.ImportDocumentEntity
import com.example.vascs.data.model.MarketplaceProductEntity
import com.example.vascs.data.repository.VascsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class GlobalAnalyticsState(
    val globalRevenueUsd: Double = 1250000.0,
    val topCountry: String = "United States",
    val exportRevenueUsd: Double = 840000.0,
    val importCostUsd: Double = 310000.0,
    val activeCountriesCount: Int = 8,
    val aiMarketInsight: String = "High Q3 growth projected in UAE and USA markets."
)

class GlobalCommerceViewModel(private val repository: VascsRepository) : ViewModel() {

    val countries: StateFlow<List<CountryEntity>> = repository.allCountries
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val currencies: StateFlow<List<CurrencyEntity>> = repository.allCurrencies
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val currencyRates: StateFlow<List<CurrencyRateEntity>> = repository.allCurrencyRates
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val marketplace: StateFlow<List<MarketplaceProductEntity>> = repository.allMarketplaceProducts
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val exports: StateFlow<List<ExportDocumentEntity>> = repository.allExportDocuments
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val imports: StateFlow<List<ImportDocumentEntity>> = repository.allImportDocuments
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val shipments: StateFlow<List<GlobalShipmentEntity>> = repository.allGlobalShipments
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _globalAnalytics = MutableStateFlow(GlobalAnalyticsState())
    val globalAnalytics: StateFlow<GlobalAnalyticsState> = _globalAnalytics.asStateFlow()

    fun addCountry(country: CountryEntity) {
        viewModelScope.launch {
            repository.addCountry(country)
        }
    }

    fun updateCurrency(rate: CurrencyRateEntity) {
        viewModelScope.launch {
            repository.updateCurrency(rate)
        }
    }

    fun generateExportInvoice(doc: ExportDocumentEntity) {
        viewModelScope.launch {
            repository.generateExportInvoice(doc)
        }
    }

    fun calculateGlobalTax(amount: Double, countryCode: String, taxType: String, onResult: (Double) -> Unit) {
        viewModelScope.launch {
            val taxAmount = repository.calculateGlobalTax(amount, countryCode, taxType)
            onResult(taxAmount)
        }
    }

    fun trackShipment(shipment: GlobalShipmentEntity) {
        viewModelScope.launch {
            repository.trackShipment(shipment)
        }
    }

    fun predictMarketDemand(countryCode: String, productCategory: String, onResult: (String) -> Unit) {
        viewModelScope.launch {
            val insight = repository.predictMarketDemand(countryCode, productCategory)
            onResult(insight)
        }
    }

    class Factory(private val repository: VascsRepository) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(GlobalCommerceViewModel::class.java)) {
                return GlobalCommerceViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
