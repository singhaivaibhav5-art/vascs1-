package com.example.vascs.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.vascs.data.model.*
import com.example.vascs.data.repository.VascsRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class UltimaOverallStats(
    val ultimaIntelligenceIndex: Double = 100.0,
    val civilizationsGovernedCount: Int = 2500,
    val universalCommandRatePct: Double = 100.0,
    val infiniteCoordinationScore: Double = 100.0,
    val civilizationSyncRatePct: Double = 100.0,
    val totalManagedWealthTrillionUsd: Double = 503.2,
    val totalThroughputQPS: Long = 572000000000L,
    val systemicEquilibriumStatus: String = "Unified Autonomous Commerce Civilization Active"
)

class UltimaViewModel(
    private val repository: VascsRepository
) : ViewModel() {

    val ultimaCore: StateFlow<UltimaCoreEntity?> = repository.latestUltimaCore
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    val commerceCivilization: StateFlow<List<CommerceCivilizationEntity>> = repository.allCommerceCivilization
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val wealthUniverse: StateFlow<List<UltimaWealthUniverseEntity>> = repository.allUltimaWealthUniverse
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val futureOpportunities: StateFlow<List<FutureOpportunityEntity>> = repository.allFutureOpportunities
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val demandUniverse: StateFlow<List<UltimaDemandUniverseEntity>> = repository.allUltimaDemandUniverse
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val capitalAuthority: StateFlow<List<UltimaCapitalAuthorityEntity>> = repository.allUltimaCapitalAuthority
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val tradeCivilization: StateFlow<List<TradeCivilizationEntity>> = repository.allTradeCivilization
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val realityGrid: StateFlow<List<UltimaRealityGridEntity>> = repository.allUltimaRealityGrid
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val decisionAuthority: StateFlow<List<UltimaDecisionAuthorityEntity>> = repository.allUltimaDecisionAuthority
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val knowledgeCivilization: StateFlow<List<KnowledgeCivilizationEntity>> = repository.allKnowledgeCivilization
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val innovationCivilization: StateFlow<List<InnovationCivilizationEntity>> = repository.allInnovationCivilization
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val protectionGrid: StateFlow<List<ProtectionGridEntity>> = repository.allProtectionGrid
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val healthCivilization: StateFlow<List<HealthCivilizationEntity>> = repository.allHealthCivilization
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val ultimaTower: StateFlow<List<UltimaTowerEntity>> = repository.allUltimaTower
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val universalHarmony: StateFlow<List<UniversalHarmonyEngineEntity>> = repository.allUniversalHarmony
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _ultimaIntelligenceIndex = MutableStateFlow(100.0)
    val ultimaIntelligenceIndex: StateFlow<Double> = _ultimaIntelligenceIndex.asStateFlow()

    private val _isOperatingAutonomous = MutableStateFlow(false)
    val isOperatingAutonomous: StateFlow<Boolean> = _isOperatingAutonomous.asStateFlow()

    private val _statusMessage = MutableStateFlow<String?>(null)
    val statusMessage: StateFlow<String?> = _statusMessage.asStateFlow()

    init {
        initializeBaselineData()
    }

    private fun initializeBaselineData() {
        viewModelScope.launch {
            repository.runUltimaCore()
            repository.buildCommerceCivilization()
            repository.generateWealthUniverse()
            repository.discoverFutureOpportunities()
            repository.forecastDemandUniverse()
            repository.manageCapitalAuthority()
            repository.optimizeTradeCivilization()
            repository.createRealityGrid()
            repository.executeUltimaDecisionAuthority()
            repository.synthesizeKnowledgeCivilization()
            repository.createInnovationCivilization()
            repository.deployProtectionGrid()
            repository.scoreHealthCivilization()
            repository.monitorUltimaTower()
            repository.synchronizeHarmonyEngine()
            recalculateIndex()
        }
    }

    fun recalculateIndex() {
        viewModelScope.launch {
            val score = repository.calculateUltimaIndex()
            _ultimaIntelligenceIndex.value = score
        }
    }

    fun triggerFullUltimaCycle() {
        viewModelScope.launch {
            _isOperatingAutonomous.value = true
            _statusMessage.value = "Initiating Universal Commerce Civilization Synchronization..."
            delay(400)
            repository.runUltimaCore()
            repository.buildCommerceCivilization()
            repository.generateWealthUniverse()
            repository.forecastDemandUniverse()
            repository.manageCapitalAuthority()
            repository.optimizeTradeCivilization()
            repository.synchronizeHarmonyEngine()
            recalculateIndex()
            delay(400)
            _isOperatingAutonomous.value = false
            _statusMessage.value = "VASCS ULTIMA: Universal Commerce Civilization Organism Synchronized (Index 100.0)"
        }
    }

    fun addCommerceCivilization(item: CommerceCivilizationEntity) {
        viewModelScope.launch {
            repository.insertCommerceCivilization(item)
            _statusMessage.value = "Commerce Civilization domain ${item.controlDomain} expanded"
            recalculateIndex()
        }
    }

    fun addWealthUniverse(item: UltimaWealthUniverseEntity) {
        viewModelScope.launch {
            repository.insertWealthUniverse(item)
            _statusMessage.value = "Wealth stream ${item.streamName} compounded"
            recalculateIndex()
        }
    }

    fun addFutureOpportunity(item: FutureOpportunityEntity) {
        viewModelScope.launch {
            repository.insertFutureOpportunity(item)
            _statusMessage.value = "Future opportunity ${item.conceptTitle} synthesized"
            recalculateIndex()
        }
    }

    fun addDemandUniverse(item: UltimaDemandUniverseEntity) {
        viewModelScope.launch {
            repository.insertDemandUniverse(item)
            _statusMessage.value = "Demand scope ${item.forecastScope} forecast updated"
            recalculateIndex()
        }
    }

    fun addCapitalAuthority(item: UltimaCapitalAuthorityEntity) {
        viewModelScope.launch {
            repository.insertCapitalAuthority(item)
            _statusMessage.value = "Capital authority fund ${item.fundName} deployed"
            recalculateIndex()
        }
    }

    fun addTradeCivilization(item: TradeCivilizationEntity) {
        viewModelScope.launch {
            repository.insertTradeCivilization(item)
            _statusMessage.value = "Trade mesh ${item.routeMeshName} optimized"
            recalculateIndex()
        }
    }

    fun addDecisionAuthority(item: UltimaDecisionAuthorityEntity) {
        viewModelScope.launch {
            repository.insertDecisionAuthority(item)
            _statusMessage.value = "Decision ${item.policyTitle} executed"
            recalculateIndex()
        }
    }

    fun clearStatusMessage() {
        _statusMessage.value = null
    }

    class Factory(private val repository: VascsRepository) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(UltimaViewModel::class.java)) {
                return UltimaViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }
}
