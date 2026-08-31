package com.example.vascs.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.vascs.data.model.ApiKeyEntity
import com.example.vascs.data.model.BillingRecordEntity
import com.example.vascs.data.model.CompanyEntity
import com.example.vascs.data.model.SubscriptionEntity
import com.example.vascs.data.model.SupportTicketEntity
import com.example.vascs.data.model.WhiteLabelConfigEntity
import com.example.vascs.data.repository.VascsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class SystemHealthState(
    val cpuUsage: Double = 14.5,
    val memoryUsage: Double = 38.2,
    val databaseUsage: Double = 22.0,
    val storageUsage: Double = 18.4,
    val apiCallsPerMin: Int = 340,
    val status: String = "Healthy"
)

class SaaSPlatformViewModel(private val repository: VascsRepository) : ViewModel() {

    val companies: StateFlow<List<CompanyEntity>> = repository.allCompanies
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val subscriptions: StateFlow<List<SubscriptionEntity>> = repository.allSubscriptions
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val billing: StateFlow<List<BillingRecordEntity>> = repository.allBillingRecords
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val tickets: StateFlow<List<SupportTicketEntity>> = repository.allSupportTickets
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val apiUsage: StateFlow<List<ApiKeyEntity>> = repository.allApiKeys
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val whiteLabelConfigs: StateFlow<List<WhiteLabelConfigEntity>> = repository.allWhiteLabelConfigs
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _systemHealth = MutableStateFlow(SystemHealthState())
    val systemHealth: StateFlow<SystemHealthState> = _systemHealth.asStateFlow()

    fun createCompany(company: CompanyEntity) {
        viewModelScope.launch {
            repository.createCompany(company)
        }
    }

    fun assignPlan(subscription: SubscriptionEntity) {
        viewModelScope.launch {
            repository.assignPlan(subscription)
        }
    }

    fun renewSubscription(subscription: SubscriptionEntity) {
        viewModelScope.launch {
            repository.renewSubscription(subscription)
        }
    }

    fun createWhiteLabel(config: WhiteLabelConfigEntity) {
        viewModelScope.launch {
            repository.createWhiteLabel(config)
        }
    }

    fun generateApiKey(key: ApiKeyEntity) {
        viewModelScope.launch {
            repository.generateApiKey(key)
        }
    }

    fun createSupportTicket(ticket: SupportTicketEntity) {
        viewModelScope.launch {
            repository.createSupportTicket(ticket)
        }
    }

    class Factory(private val repository: VascsRepository) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(SaaSPlatformViewModel::class.java)) {
                return SaaSPlatformViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
