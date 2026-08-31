package com.example.vascs.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.vascs.data.api.NetworkResult
import com.example.vascs.data.api.VascsApiClient
import com.example.vascs.data.model.CatalogueGenerationJobEntity
import com.example.vascs.data.model.ProductBatchEntity
import com.example.vascs.data.model.ProductEntity
import com.example.vascs.data.repository.VascsRepository
import com.example.vascs.util.AiDrapingStyle
import com.example.vascs.util.AiGenerationRequest
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class DashboardStats(
    val totalProducts: Int = 0,
    val activeBatches: Int = 0,
    val totalStock: Int = 0,
    val totalInventoryValue: Double = 0.0,
    val lowStockCount: Int = 0
)

class VascsViewModel(private val repository: VascsRepository) : ViewModel() {

    init {
        viewModelScope.launch {
            repository.seedSampleDataIfEmpty()
        }
    }

    val products: StateFlow<List<ProductEntity>> = repository.allProducts
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val batches: StateFlow<List<ProductBatchEntity>> = repository.allBatches
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val searchQuery = MutableStateFlow("")
    val selectedCategory = MutableStateFlow("All")
    val selectedColour = MutableStateFlow("All")
    val selectedBrand = MutableStateFlow("All")
    val selectedStockFilter = MutableStateFlow("All") // All, Low Stock, In Stock, Out of Stock

    val filteredProducts: StateFlow<List<ProductEntity>> = combine(
        products,
        searchQuery,
        selectedCategory,
        selectedColour,
        selectedBrand,
        selectedStockFilter
    ) { flows ->
        @Suppress("UNCHECKED_CAST")
        val prodList = flows[0] as List<ProductEntity>
        val query = flows[1] as String
        val cat = flows[2] as String
        val col = flows[3] as String
        val brand = flows[4] as String
        val stock = flows[5] as String

        prodList.filter { product ->
            val matchesQuery = query.isBlank() ||
                    product.name.contains(query, ignoreCase = true) ||
                    product.sku.contains(query, ignoreCase = true) ||
                    product.barcode.contains(query, ignoreCase = true) ||
                    product.category.contains(query, ignoreCase = true)

            val matchesCat = cat == "All" || product.category.equals(cat, ignoreCase = true)
            val matchesCol = col == "All" || product.colour.equals(col, ignoreCase = true)
            val matchesBrand = brand == "All" || product.brand.equals(brand, ignoreCase = true)

            val matchesStock = when (stock) {
                "Low Stock" -> product.stock in 1..5
                "In Stock" -> product.stock > 0
                "Out of Stock" -> product.stock == 0
                else -> true
            }

            matchesQuery && matchesCat && matchesCol && matchesBrand && matchesStock
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    val dashboardStats: StateFlow<DashboardStats> = combine(products, batches) { prodList, batchList ->
        DashboardStats(
            totalProducts = prodList.size,
            activeBatches = batchList.count { it.status == "ACTIVE" || it.status == "DRAFT" },
            totalStock = prodList.sumOf { it.stock },
            totalInventoryValue = prodList.sumOf { it.retailPrice * it.stock },
            lowStockCount = prodList.count { it.stock in 1..5 }
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = DashboardStats()
    )

    fun saveProduct(product: ProductEntity) {
        viewModelScope.launch {
            repository.saveProduct(product)
        }
    }

    fun updateProduct(product: ProductEntity) {
        viewModelScope.launch {
            repository.updateProduct(product)
        }
    }

    fun deleteProduct(id: String) {
        viewModelScope.launch {
            repository.deleteProduct(id)
        }
    }

    fun saveBatch(batch: ProductBatchEntity) {
        viewModelScope.launch {
            repository.saveBatch(batch)
        }
    }

    fun updateBatch(batch: ProductBatchEntity) {
        viewModelScope.launch {
            repository.updateBatch(batch)
        }
    }

    fun deleteBatch(id: String) {
        viewModelScope.launch {
            repository.deleteBatch(id)
        }
    }

    val allExportJobs: StateFlow<List<com.example.vascs.data.model.ExportQueueEntity>> = repository.allExportJobs
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun enqueueBulkExport(
        context: android.content.Context,
        productList: List<ProductEntity>,
        exportType: com.example.vascs.util.ExportType
    ) {
        viewModelScope.launch {
            val jobs = productList.map { product ->
                com.example.vascs.data.model.ExportQueueEntity(
                    productId = product.id,
                    sku = product.sku,
                    exportType = exportType.code,
                    targetWidth = exportType.width,
                    targetHeight = exportType.height,
                    sourceImageUri = product.image,
                    status = "QUEUED",
                    progress = 0
                )
            }
            repository.saveExportJobs(jobs)

            // Trigger worker processing
            val worker = com.example.vascs.util.ExportWorker(context, repository.let { 
                // Uses app level repository
                (context.applicationContext as com.example.vascs.VascsApplication).database.exportQueueDao()
            }, (context.applicationContext as com.example.vascs.VascsApplication).database.productDao())
            worker.processPendingJobs()
        }
    }

    fun clearCompletedExportJobs() {
        viewModelScope.launch {
            repository.clearCompletedExportJobs()
        }
    }

    val activeAiJob = MutableStateFlow<CatalogueGenerationJobEntity?>(null)
    val networkErrorMessage = MutableStateFlow<String?>(null)

    fun getProductImages(productId: String): Flow<List<com.example.vascs.data.model.ProductImageEntity>> {
        return repository.getImagesForProduct(productId)
    }

    fun getAiJobsForProduct(productId: String): Flow<List<CatalogueGenerationJobEntity>> {
        return repository.getJobsForProduct(productId)
    }

    fun triggerAiCatalogueGeneration(
        context: android.content.Context,
        request: AiGenerationRequest
    ) {
        viewModelScope.launch {
            networkErrorMessage.value = null
            val apiClient = VascsApiClient(context)
            val result = apiClient.submitGenerationJob(request)

            when (result) {
                is NetworkResult.Success -> {
                    val job = result.data
                    activeAiJob.value = job
                    repository.saveAiJob(job)

                    // Poll job to completion
                    delay(1500)
                    val pollResult = apiClient.pollJobStatus(job.jobId, job)
                    if (pollResult is NetworkResult.Success) {
                        val completedJob = pollResult.data
                        activeAiJob.value = completedJob
                        repository.saveAiJob(completedJob)

                        // Save generated image to Product Gallery as AI_CATALOGUE image
                        val resultUri = completedJob.resultImageUri ?: request.sourceImageUri
                        if (resultUri.isNotBlank()) {
                            val imageEntity = com.example.vascs.data.model.ProductImageEntity(
                                id = "ai-img-${java.util.UUID.randomUUID()}",
                                productId = request.productId,
                                uri = resultUri,
                                imageType = "AI_CATALOGUE",
                                isPrimary = false
                            )
                            repository.saveProductImage(imageEntity)
                        }
                    }
                }
                is NetworkResult.Error -> {
                    networkErrorMessage.value = result.message
                    val failedJob = CatalogueGenerationJobEntity(
                        jobId = "job-err-${System.currentTimeMillis()}",
                        productId = request.productId,
                        sourceImageUri = request.sourceImageUri,
                        style = request.style.name,
                        status = "FAILED",
                        errorMessage = result.message
                    )
                    activeAiJob.value = failedJob
                    repository.saveAiJob(failedJob)
                }
            }
        }
    }

    fun clearActiveAiJob() {
        activeAiJob.value = null
        networkErrorMessage.value = null
    }

    fun addAndSetPrimaryImage(productId: String, uri: String) {
        viewModelScope.launch {
            val imageId = "ai-cover-${java.util.UUID.randomUUID()}"
            val imageEntity = com.example.vascs.data.model.ProductImageEntity(
                id = imageId,
                productId = productId,
                uri = uri,
                imageType = "AI_CATALOGUE",
                isPrimary = true
            )
            repository.saveProductImage(imageEntity)
            repository.setPrimaryProductImage(productId, imageId)
        }
    }

    fun addProductImage(
        productId: String,
        uri: String,
        imageType: String = "PRODUCT",
        isPrimary: Boolean = false
    ) {
        viewModelScope.launch {
            val imageEntity = com.example.vascs.data.model.ProductImageEntity(
                id = "img-${java.util.UUID.randomUUID()}",
                productId = productId,
                uri = uri,
                imageType = imageType,
                isPrimary = isPrimary
            )
            repository.saveProductImage(imageEntity)
        }
    }

    fun deleteProductImage(imageId: String, productId: String) {
        viewModelScope.launch {
            repository.deleteProductImage(imageId, productId)
        }
    }

    fun setPrimaryProductImage(productId: String, imageId: String) {
        viewModelScope.launch {
            repository.setPrimaryProductImage(productId, imageId)
        }
    }
}

class VascsViewModelFactory(private val repository: VascsRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(VascsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return VascsViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
