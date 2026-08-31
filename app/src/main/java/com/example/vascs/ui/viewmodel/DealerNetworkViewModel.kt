package com.example.vascs.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.vascs.data.model.DealerCatalogueEntity
import com.example.vascs.data.model.DealerEntity
import com.example.vascs.data.model.DealerOrderEntity
import com.example.vascs.data.model.DealerProductEntity
import com.example.vascs.data.model.ProductEntity
import com.example.vascs.data.model.SocialAnalyticsEntity
import com.example.vascs.data.model.WhatsAppCampaignEntity
import com.example.vascs.data.repository.VascsRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class DealerNetworkViewModel(
    private val repository: VascsRepository
) : ViewModel() {

    val dealers: StateFlow<List<DealerEntity>> = repository.allDealers.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    val campaigns: StateFlow<List<WhatsAppCampaignEntity>> = repository.allWhatsAppCampaigns.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    val orders: StateFlow<List<DealerOrderEntity>> = repository.allDealerOrders.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    val dealerCatalogues: StateFlow<List<DealerCatalogueEntity>> = repository.allDealerCatalogues.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    val analyticsEvents: StateFlow<List<SocialAnalyticsEntity>> = repository.allSocialAnalytics.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    val dealerProducts: StateFlow<List<DealerProductEntity>> = repository.allDealerProducts.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    val products: StateFlow<List<ProductEntity>> = repository.allProducts.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    init {
        seedSampleDataIfNeeded()
    }

    private fun seedSampleDataIfNeeded() {
        viewModelScope.launch {
            val currentDealers = repository.allDealers.first()
            if (currentDealers.isEmpty()) {
                val sampleDealers = listOf(
                    DealerEntity(
                        dealerId = "DLR-1001",
                        dealerName = "Rajesh Shah",
                        firmName = "Shree Ram Synthetics",
                        mobile = "+91 98251 12345",
                        whatsapp = "+91 98251 12345",
                        email = "shreeram@vascs.in",
                        city = "Surat",
                        state = "Gujarat",
                        gstNumber = "24AABCS1234D1Z5",
                        address = "Ring Road Market, Surat",
                        status = "ACTIVE",
                        creditLimit = 500000.0,
                        dealerType = "Wholesaler"
                    ),
                    DealerEntity(
                        dealerId = "DLR-1002",
                        dealerName = "Vikram Kothari",
                        firmName = "Kothari Saree Emporium",
                        mobile = "+91 98790 67890",
                        whatsapp = "+91 98790 67890",
                        email = "kotharisarees@gmail.com",
                        city = "Jaipur",
                        state = "Rajasthan",
                        gstNumber = "08AABCK5678E1Z2",
                        address = "Johari Bazaar, Jaipur",
                        status = "ACTIVE",
                        creditLimit = 350000.0,
                        dealerType = "Distributor"
                    ),
                    DealerEntity(
                        dealerId = "DLR-1003",
                        dealerName = "Amit Patel",
                        firmName = "Radha Krishna Fashion Studio",
                        mobile = "+91 94260 44556",
                        whatsapp = "+91 94260 44556",
                        email = "rkfashion@outlook.com",
                        city = "Ahmedabad",
                        state = "Gujarat",
                        gstNumber = "24AABCR9900F1Z8",
                        address = "Relief Road, Ahmedabad",
                        status = "ACTIVE",
                        creditLimit = 200000.0,
                        dealerType = "Retailer"
                    ),
                    DealerEntity(
                        dealerId = "DLR-1004",
                        dealerName = "Sneha Mehta",
                        firmName = "Apsara Silk Hub",
                        mobile = "+91 91670 11223",
                        whatsapp = "+91 91670 11223",
                        email = "sneha@apsarasilks.com",
                        city = "Mumbai",
                        state = "Maharashtra",
                        gstNumber = "27AABCA3344G1Z9",
                        address = "Dadar TT Circle, Mumbai",
                        status = "ACTIVE",
                        creditLimit = 250000.0,
                        dealerType = "Online Seller"
                    ),
                    DealerEntity(
                        dealerId = "DLR-1005",
                        dealerName = "Suresh Agarwal",
                        firmName = "Agarwal Textile Agency",
                        mobile = "+91 93110 55667",
                        whatsapp = "+91 93110 55667",
                        email = "agarwalagency@gmail.com",
                        city = "Delhi",
                        state = "Delhi",
                        gstNumber = "07AABCA7788H1Z1",
                        address = "Chandni Chowk, New Delhi",
                        status = "ACTIVE",
                        creditLimit = 1000000.0,
                        dealerType = "Agent"
                    )
                )

                sampleDealers.forEach { repository.createDealer(it) }

                // Seed WhatsApp Campaign
                repository.createCampaign(
                    WhatsAppCampaignEntity(
                        campaignId = "CMP-801",
                        title = "Festive Banarasi Royal Wholesale Blast",
                        campaignType = "WHOLESALE_OFFER",
                        targetDealerType = "Wholesaler",
                        targetDealerCount = 500,
                        messageTemplate = "👑 *VASCS ROYAL BANARASI COLLECTION*\nExclusive Festive Stock Available Now!\nDirect Factory Price: ₹1,850/pc\nWhatsApp Order Link: https://wa.me/919825112345?text=Order%20Banarasi",
                        status = "COMPLETED",
                        sentCount = 482
                    )
                )

                // Seed Dealer Catalogues
                repository.createDealerCatalogue(
                    DealerCatalogueEntity(
                        catalogueId = "CAT-PDF-001",
                        dealerId = "DLR-1001",
                        title = "Surat Wholesale Festival Catalog 2026",
                        catalogueType = "Wholesale Dealer Catalogue",
                        downloadCount = 142
                    )
                )

                // Seed Orders
                repository.createDealerOrder(
                    DealerOrderEntity(
                        orderId = "ORD-9001",
                        dealerId = "DLR-1001",
                        dealerName = "Shree Ram Synthetics",
                        productId = 1L,
                        productName = "Banarasi Royal Silk Saree",
                        qty = 50,
                        rate = 1850.0,
                        amount = 92500.0,
                        status = "Dispatched",
                        notes = "Priority festival stock delivery via V-Trans"
                    )
                )
                repository.createDealerOrder(
                    DealerOrderEntity(
                        orderId = "ORD-9002",
                        dealerId = "DLR-1002",
                        dealerName = "Kothari Saree Emporium",
                        productId = 2L,
                        productName = "Kanjivaram Zari Soft Silk",
                        qty = 30,
                        rate = 2200.0,
                        amount = 66000.0,
                        status = "Approved",
                        notes = "Jaipur Branch stock allocation"
                    )
                )

                // Seed Analytics
                repository.logSocialAnalytics("WHATSAPP_SHARE", "DLR-1001", 1L, "Banarasi Royal Silk Saree", "WhatsApp")
                repository.logSocialAnalytics("TELEGRAM_SHARE", "DLR-1002", 2L, "Kanjivaram Zari Soft Silk", "Telegram")
                repository.logSocialAnalytics("INSTAGRAM_EXPORT", "DLR-1004", 1L, "Banarasi Royal Silk Saree", "Instagram")
                repository.logSocialAnalytics("DEALER_DOWNLOAD", "DLR-1001", 0L, "Wholesale PDF Catalog", "PDF")
                repository.logSocialAnalytics("DEALER_ORDER", "DLR-1001", 1L, "Banarasi Royal Silk Saree", "Dealer Portal")
            }
        }
    }

    fun addDealer(
        dealerName: String,
        firmName: String,
        mobile: String,
        whatsapp: String,
        email: String,
        city: String,
        state: String,
        gstNumber: String,
        creditLimit: Double,
        dealerType: String
    ) {
        viewModelScope.launch {
            val dId = "DLR-${(1000..9999).random()}"
            val newDealer = DealerEntity(
                dealerId = dId,
                dealerName = dealerName,
                firmName = firmName,
                mobile = mobile,
                whatsapp = whatsapp.ifBlank { mobile },
                email = email,
                city = city,
                state = state.ifBlank { "Gujarat" },
                gstNumber = gstNumber,
                creditLimit = creditLimit,
                dealerType = dealerType,
                status = "ACTIVE"
            )
            repository.createDealer(newDealer)
        }
    }

    fun updateDealerStatus(id: Long, status: String) {
        viewModelScope.launch {
            repository.updateDealerStatus(id, status)
        }
    }

    fun assignProductToDealers(dealerIds: List<String>, productId: Long, specialPrice: Double) {
        viewModelScope.launch {
            repository.assignProductToDealers(dealerIds, productId, specialPrice)
        }
    }

    fun createWhatsAppCampaign(
        title: String,
        campaignType: String,
        targetDealerType: String,
        targetDealerCount: Int,
        messageTemplate: String,
        productIds: List<Long>
    ) {
        viewModelScope.launch {
            val cmpId = "CMP-${(100..999).random()}"
            val pIdsJson = "[${productIds.joinToString(",")}]"
            val campaign = WhatsAppCampaignEntity(
                campaignId = cmpId,
                title = title,
                campaignType = campaignType,
                targetDealerType = targetDealerType,
                targetDealerCount = targetDealerCount,
                messageTemplate = messageTemplate,
                productIdsJson = pIdsJson,
                status = "SCHEDULED"
            )
            val insertedId = repository.createCampaign(campaign)
            // Auto dispatch simulation
            repository.updateCampaignStatus(insertedId, "COMPLETED", targetDealerCount)
            repository.logSocialAnalytics("WHATSAPP_SHARE", channel = "WhatsApp Campaign: $title")
        }
    }

    fun generateDealerCatalogue(
        dealerId: String,
        title: String,
        catalogueType: String,
        productIds: List<Long>
    ) {
        viewModelScope.launch {
            val catId = "CAT-PDF-${(100..999).random()}"
            val pIdsJson = "[${productIds.joinToString(",")}]"
            val catalogue = DealerCatalogueEntity(
                catalogueId = catId,
                dealerId = dealerId,
                title = title,
                catalogueType = catalogueType,
                productIdsJson = pIdsJson,
                fileUri = "file:///storage/emulated/0/VASCS/DealerCatalogues/$catId.pdf"
            )
            repository.createDealerCatalogue(catalogue)
            repository.logSocialAnalytics("DEALER_DOWNLOAD", dealerId = dealerId, channel = "Dealer PDF Catalogue")
        }
    }

    fun createDealerOrder(
        dealerId: String,
        dealerName: String,
        productId: Long,
        productName: String,
        qty: Int,
        rate: Double,
        notes: String
    ) {
        viewModelScope.launch {
            val ordId = "ORD-${(9000..9999).random()}"
            val amount = qty * rate
            val order = DealerOrderEntity(
                orderId = ordId,
                dealerId = dealerId,
                dealerName = dealerName,
                productId = productId,
                productName = productName,
                qty = qty,
                rate = rate,
                amount = amount,
                status = "Pending",
                notes = notes
            )
            repository.createDealerOrder(order)
            repository.logSocialAnalytics("DEALER_ORDER", dealerId = dealerId, productId = productId, productName = productName, channel = "Dealer App")
        }
    }

    fun updateOrderStatus(id: Long, status: String) {
        viewModelScope.launch {
            repository.updateOrderStatus(id, status)
        }
    }

    fun logSocialEvent(eventType: String, dealerId: String = "", productId: Long = 0L, productName: String = "", channel: String = "") {
        viewModelScope.launch {
            repository.logSocialAnalytics(eventType, dealerId, productId, productName, channel)
        }
    }
}

class DealerNetworkViewModelFactory(
    private val repository: VascsRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DealerNetworkViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DealerNetworkViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
