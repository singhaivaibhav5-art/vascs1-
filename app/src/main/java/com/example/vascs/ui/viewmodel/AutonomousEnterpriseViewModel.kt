package com.example.vascs.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.vascs.data.model.AiAgentEntity
import com.example.vascs.data.model.AutonomousDecisionEntity
import com.example.vascs.data.model.BusinessTwinModelEntity
import com.example.vascs.data.model.ExecutionLogEntity
import com.example.vascs.data.model.MarketIntelligenceEntity
import com.example.vascs.data.model.OptimizationLogEntity
import com.example.vascs.data.model.PredictionEntity
import com.example.vascs.data.model.RiskAlertEntity
import com.example.vascs.data.repository.VascsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class EnterpriseHealthScoreState(
    val salesScore: Int = 96,
    val financeScore: Int = 98,
    val inventoryScore: Int = 94,
    val dealerScore: Int = 95,
    val customerScore: Int = 97,
    val growthScore: Int = 98,
    val overallIndex: Int = 96
)

class AutonomousEnterpriseViewModel(private val repository: VascsRepository) : ViewModel() {

    val aiAgents: StateFlow<List<AiAgentEntity>> = repository.allAiAgents
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val predictions: StateFlow<List<PredictionEntity>> = repository.allPredictions
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val decisions: StateFlow<List<AutonomousDecisionEntity>> = repository.allAutonomousDecisions
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val riskAlerts: StateFlow<List<RiskAlertEntity>> = repository.allRiskAlerts
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val optimizations: StateFlow<List<OptimizationLogEntity>> = repository.allOptimizationLogs
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val executions: StateFlow<List<ExecutionLogEntity>> = repository.allExecutionLogs
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val businessTwinModels: StateFlow<List<BusinessTwinModelEntity>> = repository.allBusinessTwinModels
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val marketIntelligence: StateFlow<List<MarketIntelligenceEntity>> = repository.allMarketIntelligence
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _healthScore = MutableStateFlow(EnterpriseHealthScoreState())
    val healthScore: StateFlow<EnterpriseHealthScoreState> = _healthScore.asStateFlow()

    private val _lastExecutionOutput = MutableStateFlow("")
    val lastExecutionOutput: StateFlow<String> = _lastExecutionOutput.asStateFlow()

    fun runBusinessTwin(scenarioName: String) {
        viewModelScope.launch {
            _lastExecutionOutput.value = repository.runBusinessTwin(scenarioName)
        }
    }

    fun predictDemand(domain: String, period: String) {
        viewModelScope.launch {
            _lastExecutionOutput.value = repository.predictDemand(domain, period)
        }
    }

    fun executeDecision(decisionId: Long) {
        viewModelScope.launch {
            _lastExecutionOutput.value = repository.executeDecision(decisionId)
        }
    }

    fun optimizeBusiness(area: String) {
        viewModelScope.launch {
            _lastExecutionOutput.value = repository.optimizeBusiness(area)
        }
    }

    fun detectRisks() {
        viewModelScope.launch {
            _lastExecutionOutput.value = repository.detectRisks()
        }
    }

    fun generateStrategy(horizon: String) {
        viewModelScope.launch {
            _lastExecutionOutput.value = repository.generateStrategy(horizon)
        }
    }

    fun initializeDefaultAgentsIfEmpty() {
        viewModelScope.launch {
            if (aiAgents.value.isEmpty()) {
                val defaultAgents = listOf(
                    AiAgentEntity(agentType = "Sales Agent", agentName = "Autonomous Sales Pipeline Agent", status = "Active", tasksCompleted = 340, performanceScore = 98.6, executionMode = "Fully Automatic"),
                    AiAgentEntity(agentType = "Inventory Agent", agentName = "Warehouse & Reorder Agent", status = "Active", tasksCompleted = 210, performanceScore = 97.8, executionMode = "Fully Automatic"),
                    AiAgentEntity(agentType = "Marketing Agent", agentName = "Multi-Channel Offer Agent", status = "Active", tasksCompleted = 185, performanceScore = 96.4, executionMode = "Semi Automatic"),
                    AiAgentEntity(agentType = "Finance Agent", agentName = "Ledger & Reconciliation Agent", status = "Active", tasksCompleted = 420, performanceScore = 99.2, executionMode = "Fully Automatic"),
                    AiAgentEntity(agentType = "Dealer Agent", agentName = "Dealer Growth & Tier Agent", status = "Active", tasksCompleted = 150, performanceScore = 95.8, executionMode = "Semi Automatic"),
                    AiAgentEntity(agentType = "Customer Agent", agentName = "Shopper Loyalty Agent", status = "Active", tasksCompleted = 290, performanceScore = 98.1, executionMode = "Fully Automatic"),
                    AiAgentEntity(agentType = "Support Agent", agentName = "Helpdesk Ticket Agent", status = "Active", tasksCompleted = 310, performanceScore = 97.5, executionMode = "Fully Automatic"),
                    AiAgentEntity(agentType = "Purchase Agent", agentName = "Yarn & Raw Material Agent", status = "Active", tasksCompleted = 115, performanceScore = 96.9, executionMode = "Semi Automatic")
                )
                defaultAgents.forEach { repository.insertAiAgent(it) }
            }
        }
    }

    class Factory(private val repository: VascsRepository) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(AutonomousEnterpriseViewModel::class.java)) {
                return AutonomousEnterpriseViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
