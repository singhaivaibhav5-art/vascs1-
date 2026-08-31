package com.example.vascs.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.vascs.data.model.CustomerEntity
import com.example.vascs.data.model.DealerEntity
import com.example.vascs.data.model.DeliveryPartnerEntity
import com.example.vascs.data.model.NotificationEntity
import com.example.vascs.data.model.RewardPointEntity
import com.example.vascs.data.model.SupportTicketEntity
import com.example.vascs.data.model.VendorEntity
import com.example.vascs.data.repository.VascsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class EcosystemAnalyticsState(
    val activeDealersCount: Int = 142,
    val activeCustomersCount: Int = 3850,
    val activeVendorsCount: Int = 28,
    val dailyOrdersCount: Int = 312,
    val dailyRevenueInr: Double = 845000.0,
    val deliverySuccessRatePercent: Double = 98.4,
    val aiControlInsight: String = "High dealer engagement (+34% orders). Delivery SLA optimal at 98.4%."
)

class EcosystemViewModel(private val repository: VascsRepository) : ViewModel() {

    val dealers: StateFlow<List<DealerEntity>> = repository.allDealers
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val customers: StateFlow<List<CustomerEntity>> = repository.allCustomers
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val vendors: StateFlow<List<VendorEntity>> = repository.allVendors
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val deliveries: StateFlow<List<DeliveryPartnerEntity>> = repository.allDeliveryPartners
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val notifications: StateFlow<List<NotificationEntity>> = repository.allNotifications
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val tickets: StateFlow<List<SupportTicketEntity>> = repository.allSupportTickets
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _analytics = MutableStateFlow(EcosystemAnalyticsState())
    val analytics: StateFlow<EcosystemAnalyticsState> = _analytics.asStateFlow()

    fun createDealer(dealer: DealerEntity) {
        viewModelScope.launch {
            repository.createDealer(dealer)
        }
    }

    fun createCustomer(customer: CustomerEntity) {
        viewModelScope.launch {
            repository.createCustomer(customer)
        }
    }

    fun createVendor(vendor: VendorEntity) {
        viewModelScope.launch {
            repository.createVendor(vendor)
        }
    }

    fun assignDelivery(partner: DeliveryPartnerEntity) {
        viewModelScope.launch {
            repository.assignDelivery(partner)
        }
    }

    fun sendNotification(notification: NotificationEntity) {
        viewModelScope.launch {
            repository.sendNotification(notification)
        }
    }

    fun createTicket(ticket: SupportTicketEntity) {
        viewModelScope.launch {
            repository.createTicket(ticket)
        }
    }

    fun rewardPoints(reward: RewardPointEntity) {
        viewModelScope.launch {
            repository.rewardPoints(reward)
        }
    }

    class Factory(private val repository: VascsRepository) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(EcosystemViewModel::class.java)) {
                return EcosystemViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
