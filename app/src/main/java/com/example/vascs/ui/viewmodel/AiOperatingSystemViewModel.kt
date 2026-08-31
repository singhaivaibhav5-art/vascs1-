package com.example.vascs.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.vascs.data.model.AiActivityLogEntity
import com.example.vascs.data.model.AiAutomationRuleEntity
import com.example.vascs.data.model.AiDecisionEntity
import com.example.vascs.data.model.AiEmployeeEntity
import com.example.vascs.data.model.AiForecastEntity
import com.example.vascs.data.model.AiRecommendationEntity
import com.example.vascs.data.model.AiTaskEntity
import com.example.vascs.data.repository.VascsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class AiOperatingSystemViewModel(private val repository: VascsRepository) : ViewModel() {

    val aiEmployees: StateFlow<List<AiEmployeeEntity>> = repository.allAiEmployees
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val aiTasks: StateFlow<List<AiTaskEntity>> = repository.allAiTasks
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val aiForecasts: StateFlow<List<AiForecastEntity>> = repository.allAiForecasts
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val aiRecommendations: StateFlow<List<AiRecommendationEntity>> = repository.allAiRecommendations
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val aiDecisions: StateFlow<List<AiDecisionEntity>> = repository.allAiDecisions
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val automationRules: StateFlow<List<AiAutomationRuleEntity>> = repository.allAutomationRules
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val activityLogs: StateFlow<List<AiActivityLogEntity>> = repository.allAiActivityLogs
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _lastAiOutput = MutableStateFlow("")
    val lastAiOutput: StateFlow<String> = _lastAiOutput.asStateFlow()

    fun runAiCEO() {
        viewModelScope.launch {
            _lastAiOutput.value = repository.runAiCEO()
        }
    }

    fun runAiSalesManager() {
        viewModelScope.launch {
            _lastAiOutput.value = repository.runAiSalesManager()
        }
    }

    fun runAiInventoryManager() {
        viewModelScope.launch {
            _lastAiOutput.value = repository.runAiInventoryManager()
        }
    }

    fun runAiMarketingManager() {
        viewModelScope.launch {
            _lastAiOutput.value = repository.runAiMarketingManager()
        }
    }

    fun runAiFinanceManager() {
        viewModelScope.launch {
            _lastAiOutput.value = repository.runAiFinanceManager()
        }
    }

    fun generateForecast(forecast: AiForecastEntity) {
        viewModelScope.launch {
            repository.generateForecast(forecast)
        }
    }

    fun executeAutomationRule(rule: AiAutomationRuleEntity) {
        viewModelScope.launch {
            repository.executeAutomationRule(rule)
        }
    }

    fun createAiTask(task: AiTaskEntity) {
        viewModelScope.launch {
            repository.createAiTask(task)
        }
    }

    fun createRecommendation(recommendation: AiRecommendationEntity) {
        viewModelScope.launch {
            repository.createRecommendation(recommendation)
        }
    }

    fun recordBoardDecision(decision: AiDecisionEntity) {
        viewModelScope.launch {
            repository.recordBoardDecision(decision)
        }
    }

    fun createAutomationRule(rule: AiAutomationRuleEntity) {
        viewModelScope.launch {
            repository.createAutomationRule(rule)
        }
    }

    fun initializeDefaultAiEmployeesIfEmpty() {
        viewModelScope.launch {
            if (aiEmployees.value.isEmpty()) {
                val defaultEmployees = listOf(
                    AiEmployeeEntity(role = "AI CEO", name = "Chief Executive Agent", status = "Active", healthScore = 99, lastAction = "Monitoring Enterprise KPIs"),
                    AiEmployeeEntity(role = "AI Sales Manager", name = "Sales Optimization Agent", status = "Active", healthScore = 97, lastAction = "Auditing Dealer Orders"),
                    AiEmployeeEntity(role = "AI Inventory Manager", name = "Inventory & Logistics Agent", status = "Active", healthScore = 98, lastAction = "Balancing Warehouse Stocks"),
                    AiEmployeeEntity(role = "AI Marketing Manager", name = "Campaign & Brand Agent", status = "Active", healthScore = 95, lastAction = "Running Diwali Whatsapp Campaign"),
                    AiEmployeeEntity(role = "AI Finance Manager", name = "Treasury & Audit Agent", status = "Active", healthScore = 99, lastAction = "Reconciling Receivables"),
                    AiEmployeeEntity(role = "AI Dealer Manager", name = "Dealer Success Agent", status = "Active", healthScore = 96, lastAction = "Analyzing Dealer Credit Tiers"),
                    AiEmployeeEntity(role = "AI Customer Manager", name = "Customer Retention Agent", status = "Active", healthScore = 97, lastAction = "Triggering Loyalty Reminders"),
                    AiEmployeeEntity(role = "AI Operations Manager", name = "Dispatch & Route Agent", status = "Active", healthScore = 98, lastAction = "Optimizing Route SLA")
                )
                defaultEmployees.forEach { repository.insertAiEmployee(it) }
            }
        }
    }

    class Factory(private val repository: VascsRepository) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(AiOperatingSystemViewModel::class.java)) {
                return AiOperatingSystemViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
