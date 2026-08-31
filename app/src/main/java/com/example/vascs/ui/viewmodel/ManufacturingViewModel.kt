package com.example.vascs.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.vascs.data.model.DyeingRecordEntity
import com.example.vascs.data.model.EmbroideryRecordEntity
import com.example.vascs.data.model.FabricStockEntity
import com.example.vascs.data.model.FinishedGoodsEntity
import com.example.vascs.data.model.ProductionBatchEntity
import com.example.vascs.data.model.ProductionOrderEntity
import com.example.vascs.data.model.QualityCheckEntity
import com.example.vascs.data.model.RawMaterialEntity
import com.example.vascs.data.model.WorkerEntity
import com.example.vascs.data.repository.VascsRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ManufacturingViewModel(private val repository: VascsRepository) : ViewModel() {

    val rawMaterials: StateFlow<List<RawMaterialEntity>> = repository.allRawMaterials
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val fabricStock: StateFlow<List<FabricStockEntity>> = repository.allFabricStock
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val productionOrders: StateFlow<List<ProductionOrderEntity>> = repository.allProductionOrders
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val productionBatches: StateFlow<List<ProductionBatchEntity>> = repository.allProductionBatches
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val dyeingRecords: StateFlow<List<DyeingRecordEntity>> = repository.allDyeingRecords
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val embroideryRecords: StateFlow<List<EmbroideryRecordEntity>> = repository.allEmbroideryRecords
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val qualityChecks: StateFlow<List<QualityCheckEntity>> = repository.allQualityChecks
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val finishedGoods: StateFlow<List<FinishedGoodsEntity>> = repository.allFinishedGoods
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val workers: StateFlow<List<WorkerEntity>> = repository.allWorkers
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun addRawMaterial(material: RawMaterialEntity) {
        viewModelScope.launch {
            repository.insertRawMaterial(material)
        }
    }

    fun addFabricStock(fabric: FabricStockEntity) {
        viewModelScope.launch {
            repository.insertFabricStock(fabric)
        }
    }

    fun createProductionOrder(order: ProductionOrderEntity) {
        viewModelScope.launch {
            repository.createProductionOrder(order)
        }
    }

    fun createBatch(batch: ProductionBatchEntity) {
        viewModelScope.launch {
            repository.createBatch(batch)
        }
    }

    fun addDyeingRecord(record: DyeingRecordEntity) {
        viewModelScope.launch {
            repository.insertDyeingRecord(record)
        }
    }

    fun addEmbroideryRecord(record: EmbroideryRecordEntity) {
        viewModelScope.launch {
            repository.insertEmbroideryRecord(record)
        }
    }

    fun performQualityCheck(qc: QualityCheckEntity) {
        viewModelScope.launch {
            repository.performQualityCheck(qc)
        }
    }

    fun postFinishedGoods(goods: FinishedGoodsEntity) {
        viewModelScope.launch {
            repository.postFinishedGoods(goods)
        }
    }

    fun addWorker(worker: WorkerEntity) {
        viewModelScope.launch {
            repository.insertWorker(worker)
        }
    }

    class Factory(private val repository: VascsRepository) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ManufacturingViewModel::class.java)) {
                return ManufacturingViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
