package com.example.vascs.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.vascs.data.model.BusinessConnectionEntity
import com.example.vascs.data.model.FashionTrendEntity
import com.example.vascs.data.model.GlobalIntelligenceEntity
import com.example.vascs.data.model.ManufacturerEntity
import com.example.vascs.data.model.MarketplaceProductEntity
import com.example.vascs.data.model.ReputationScoreEntity
import com.example.vascs.data.model.SupplierEntity
import com.example.vascs.data.model.TradeLeadEntity
import com.example.vascs.data.repository.VascsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class UniverseViewModel(private val repository: VascsRepository) : ViewModel() {

    val manufacturers: StateFlow<List<ManufacturerEntity>> = repository.allManufacturers
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val suppliers: StateFlow<List<SupplierEntity>> = repository.allSuppliers
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val marketplace: StateFlow<List<MarketplaceProductEntity>> = repository.allMarketplaceProducts
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val tradeLeads: StateFlow<List<TradeLeadEntity>> = repository.allTradeLeads
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val reputationScores: StateFlow<List<ReputationScoreEntity>> = repository.allReputationScores
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val fashionTrends: StateFlow<List<FashionTrendEntity>> = repository.allFashionTrends
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val globalAnalytics: StateFlow<List<GlobalIntelligenceEntity>> = repository.allGlobalIntelligence
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val businessConnections: StateFlow<List<BusinessConnectionEntity>> = repository.allBusinessConnections
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _lastUniverseOutput = MutableStateFlow("")
    val lastUniverseOutput: StateFlow<String> = _lastUniverseOutput.asStateFlow()

    fun registerManufacturer(manufacturer: ManufacturerEntity) {
        viewModelScope.launch {
            val id = repository.registerManufacturer(manufacturer)
            _lastUniverseOutput.value = "Registered Manufacturer [${manufacturer.companyName}] - ID #$id"
        }
    }

    fun registerSupplier(supplier: SupplierEntity) {
        viewModelScope.launch {
            val id = repository.registerSupplier(supplier)
            _lastUniverseOutput.value = "Registered Supplier [${supplier.supplierName}] - ID #$id"
        }
    }

    fun publishMarketplaceProduct(product: MarketplaceProductEntity) {
        viewModelScope.launch {
            val id = repository.publishMarketplaceProduct(product)
            _lastUniverseOutput.value = "Published Marketplace Listing [${product.title}] - ID #$id"
        }
    }

    fun createTradeLead(lead: TradeLeadEntity) {
        viewModelScope.launch {
            val id = repository.createTradeLead(lead)
            _lastUniverseOutput.value = "Created Global Trade Lead [${lead.title}] - ID #$id"
        }
    }

    fun calculateReputation(score: ReputationScoreEntity) {
        viewModelScope.launch {
            val id = repository.calculateReputation(score)
            _lastUniverseOutput.value = "Updated Reputation Score for [${score.entityName}]: ${score.overallScore}/100"
        }
    }

    fun analyzeFashionTrend(trend: FashionTrendEntity) {
        viewModelScope.launch {
            val id = repository.analyzeFashionTrend(trend)
            _lastUniverseOutput.value = "Analyzed Fashion Trend [${trend.trendName}]: ${trend.trajectory} (+${trend.projectedGrowthPct}%)"
        }
    }

    fun initializeUniverseDefaultsIfEmpty() {
        viewModelScope.launch {
            if (manufacturers.value.isEmpty()) {
                val m1 = ManufacturerEntity(companyName = "Surat Weaving Mills & Co", location = "Surat, Gujarat, India", productionUnitsCount = 8, monthlyCapacityPcs = 350000, mainCategories = "Silk Sarees, Jacquard Sarees", factoryRating = 4.95)
                val m2 = ManufacturerEntity(companyName = "Kanjeevaram Handloom Guild", location = "Kanchipuram, Tamil Nadu", productionUnitsCount = 5, monthlyCapacityPcs = 120000, mainCategories = "Pure Zari Silk Sarees", factoryRating = 4.98)
                repository.registerManufacturer(m1)
                repository.registerManufacturer(m2)
            }

            if (suppliers.value.isEmpty()) {
                val s1 = SupplierEntity(supplierName = "Apex Zari & Thread Mills", supplierType = "Accessory Suppliers", location = "Surat", costIndex = "Low Cost", qualityRating = 4.9, aiRecommendationScore = 98)
                val s2 = SupplierEntity(supplierName = "Organic Mulberry Silk Co", supplierType = "Fabric Suppliers", location = "Bangalore", costIndex = "Premium Quality", qualityRating = 4.95, aiRecommendationScore = 99)
                repository.registerSupplier(s1)
                repository.registerSupplier(s2)
            }

            if (marketplace.value.isEmpty()) {
                val p1 = MarketplaceProductEntity(title = "Royal Banarasi Silk Saree Wholesale Lot", marketplaceType = "B2B Marketplace", wholesalePriceInr = 3800.0, minOrderQuantity = 50, sellerName = "Surat Central Weavers", category = "Silk Sarees")
                val p2 = MarketplaceProductEntity(title = "Festive Organza Designer Saree Export Pack", marketplaceType = "Export Marketplace", wholesalePriceInr = 4500.0, minOrderQuantity = 100, sellerName = "VASCS Global Export Division", category = "Organza Sarees")
                repository.publishMarketplaceProduct(p1)
                repository.publishMarketplaceProduct(p2)
            }

            if (tradeLeads.value.isEmpty()) {
                val l1 = TradeLeadEntity(leadType = "Buy Leads", title = "Requirement for 2,000 Pcs Pure Kanjeevaram Silk Sarees", requirementDetails = "Target buyer in Dubai, UAE for Diwali Wedding Season", quantityRequired = 2000, targetPriceInr = 8500.0, aiLeadScore = 98, postedDate = System.currentTimeMillis().toString())
                val l2 = TradeLeadEntity(leadType = "Export Leads", title = "USA NRI Boutiques Bulk Order Inquiry", requirementDetails = "Looking for premium linen and printed silk saree collection", quantityRequired = 1500, targetPriceInr = 3200.0, aiLeadScore = 95, postedDate = System.currentTimeMillis().toString())
                repository.createTradeLead(l1)
                repository.createTradeLead(l2)
            }

            if (fashionTrends.value.isEmpty()) {
                val t1 = FashionTrendEntity(trendCategory = "Design Trends", trendName = "Royal Gold Zari Kanjeevaram", trajectory = "Next Best Seller", projectedGrowthPct = 48.5, primaryRegion = "Pan-India & NRI Global")
                val t2 = FashionTrendEntity(trendCategory = "Color Trends", trendName = "Pastel Lavender & Champagne Gold", trajectory = "Emerging Trend", projectedGrowthPct = 36.2, primaryRegion = "Tier-1 Metro Boutiques")
                repository.analyzeFashionTrend(t1)
                repository.analyzeFashionTrend(t2)
            }

            if (reputationScores.value.isEmpty()) {
                repository.calculateReputation(ReputationScoreEntity(entityType = "Manufacturer Score", entityName = "Surat Central Weavers Guild", overallScore = 98.8, orderFulfillmentRate = 99.4, paymentTimelinessRate = 98.2, reviewRating = 4.92))
                repository.calculateReputation(ReputationScoreEntity(entityType = "Dealer Score", entityName = "Vikas Sarees Wholesale Network", overallScore = 97.5, orderFulfillmentRate = 98.1, paymentTimelinessRate = 97.0, reviewRating = 4.88))
            }

            if (globalAnalytics.value.isEmpty()) {
                repository.insertGlobalIntelligence(GlobalIntelligenceEntity(regionCountry = "United States (USA)", marketPotentialScore = 98, recommendedCategory = "Bridal & Heavy Zari Sarees", exportOpportunityInr = 45000000.0, tariffRiskLevel = "Low Risk", capturedDate = System.currentTimeMillis().toString()))
                repository.insertGlobalIntelligence(GlobalIntelligenceEntity(regionCountry = "United Arab Emirates (UAE)", marketPotentialScore = 96, recommendedCategory = "Designer Silk & Organza Sarees", exportOpportunityInr = 32000000.0, tariffRiskLevel = "Zero Tariff Zone", capturedDate = System.currentTimeMillis().toString()))
            }
        }
    }

    class Factory(private val repository: VascsRepository) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(UniverseViewModel::class.java)) {
                return UniverseViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
