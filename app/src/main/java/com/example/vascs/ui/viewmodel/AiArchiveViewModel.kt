package com.example.vascs.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.vascs.data.model.AiImageArchiveEntity
import com.example.vascs.data.model.ProductEntity
import com.example.vascs.data.repository.VascsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

enum class FolderGrouping {
    PRODUCT, SKU, QR, PROVIDER, DATE, VERSION
}

class AiArchiveViewModel(private val repository: VascsRepository) : ViewModel() {

    val products: StateFlow<List<ProductEntity>> = repository.allProducts
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val rawActiveArchives: StateFlow<List<AiImageArchiveEntity>> = repository.allArchiveImages
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val rawRecycleBin: StateFlow<List<AiImageArchiveEntity>> = repository.deletedArchiveImages
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val searchQuery = MutableStateFlow("")
    val selectedSourceFilter = MutableStateFlow("ALL") // "ALL", "AI", "CAMERA", "GALLERY", "RESIZED", "IMPORTED"
    val selectedProviderFilter = MutableStateFlow("ALL") // "ALL", "NANO_BANANA", "GOOGLE_AI_STUDIO", "OOTDIFFUSION", "COMFYUI", "STABLE_DIFFUSION", "MANUAL_IMPORT"
    val folderGrouping = MutableStateFlow(FolderGrouping.PRODUCT)

    val selectedArchiveIds = MutableStateFlow<Set<Long>>(emptySet())

    val compareVersionA = MutableStateFlow<AiImageArchiveEntity?>(null)
    val compareVersionB = MutableStateFlow<AiImageArchiveEntity?>(null)

    val filteredArchives: StateFlow<List<AiImageArchiveEntity>> = combine(
        rawActiveArchives,
        searchQuery,
        selectedSourceFilter,
        selectedProviderFilter
    ) { archives, query, sourceFilter, providerFilter ->
        archives.filter { item ->
            val matchesQuery = query.isBlank() ||
                    item.productName.contains(query, ignoreCase = true) ||
                    item.sku.contains(query, ignoreCase = true) ||
                    item.qrNumber.contains(query, ignoreCase = true) ||
                    item.archiveId.contains(query, ignoreCase = true) ||
                    item.prompt.contains(query, ignoreCase = true)

            val matchesSource = sourceFilter == "ALL" || item.imageSource.equals(sourceFilter, ignoreCase = true)
            val matchesProvider = providerFilter == "ALL" || item.providerName.equals(providerFilter, ignoreCase = true)

            matchesQuery && matchesSource && matchesProvider
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    init {
        viewModelScope.launch {
            rawActiveArchives.collect { items ->
                if (items.isEmpty()) {
                    seedDefaultArchives()
                }
            }
        }
    }

    private suspend fun seedDefaultArchives() {
        val sampleList = listOf(
            AiImageArchiveEntity(
                archiveId = "ARC-KJL-1001-V1",
                productId = 1L,
                sku = "KJL-1001",
                qrNumber = "QR-KJL1001",
                productName = "Kanjivaram Silk Saree - Royal Red",
                versionNumber = 1,
                imageUri = "https://images.unsplash.com/photo-1610030469983-98e550d6193c?w=800",
                thumbnailUri = "https://images.unsplash.com/photo-1610030469983-98e550d6193c?w=800",
                imageSource = "AI",
                imageType = "CATALOGUE_STUDIO",
                prompt = "Royal Indian bride wearing red Kanjivaram silk saree with golden zari pallu in opulent palace court",
                modelName = "Nano Banana AI v2",
                providerName = "NANO_BANANA",
                generationDate = System.currentTimeMillis() - 86400000L * 3,
                generationTime = "10:30:15",
                createdBy = "Admin Studio",
                width = 1080,
                height = 1080,
                status = "ACTIVE",
                usageCount = 12,
                shareCount = 8,
                downloadCount = 15,
                coverAppliedCount = 3
            ),
            AiImageArchiveEntity(
                archiveId = "ARC-KJL-1001-V2",
                productId = 1L,
                sku = "KJL-1001",
                qrNumber = "QR-KJL1001",
                productName = "Kanjivaram Silk Saree - Royal Red",
                versionNumber = 2,
                imageUri = "https://images.unsplash.com/photo-1617627143750-d86bc21e42bb?w=800",
                thumbnailUri = "https://images.unsplash.com/photo-1617627143750-d86bc21e42bb?w=800",
                imageSource = "AI",
                imageType = "CATALOGUE_OUTDOOR",
                prompt = "Elegant South Indian wedding model posing in sunlight wearing Kanjivaram silk saree with traditional Temple background",
                modelName = "Gemini Studio Pro",
                providerName = "GOOGLE_AI_STUDIO",
                generationDate = System.currentTimeMillis() - 86400000L * 2,
                generationTime = "14:20:00",
                createdBy = "Admin Studio",
                width = 1080,
                height = 1080,
                status = "ACTIVE",
                usageCount = 28,
                shareCount = 19,
                downloadCount = 34,
                coverAppliedCount = 7
            ),
            AiImageArchiveEntity(
                archiveId = "ARC-BAN-2002-V1",
                productId = 2L,
                sku = "BAN-2002",
                qrNumber = "QR-BAN2002",
                productName = "Banarasi Brocade Silk - Emerald Green",
                versionNumber = 1,
                imageUri = "https://images.unsplash.com/photo-1583391733956-3750e0ff4e8b?w=800",
                thumbnailUri = "https://images.unsplash.com/photo-1583391733956-3750e0ff4e8b?w=800",
                imageSource = "AI",
                imageType = "MANNEQUIN_TRYON",
                prompt = "High fashion virtual model showcasing Banarasi Brocade saree with detailed golden weave embroidery",
                modelName = "OotDiffusion v1.5",
                providerName = "OOTDIFFUSION",
                generationDate = System.currentTimeMillis() - 86400000L * 1,
                generationTime = "11:15:45",
                createdBy = "AI Batch Process",
                width = 1080,
                height = 1350,
                status = "ACTIVE",
                usageCount = 45,
                shareCount = 30,
                downloadCount = 52,
                coverAppliedCount = 12
            ),
            AiImageArchiveEntity(
                archiveId = "ARC-COT-3003-V1",
                productId = 3L,
                sku = "COT-3003",
                qrNumber = "QR-COT3003",
                productName = "Chanderi Cotton Blend - Peacock Blue",
                versionNumber = 1,
                imageUri = "https://images.unsplash.com/photo-1609357605129-26f69add5d6e?w=800",
                thumbnailUri = "https://images.unsplash.com/photo-1609357605129-26f69add5d6e?w=800",
                imageSource = "AI",
                imageType = "FLAT_LAY_LIFESTYLE",
                prompt = "Minimalist studio flat lay of Chanderi Cotton saree with terracotta pottery and silver jhumka jewelry",
                modelName = "ComfyUI Saree Workflow",
                providerName = "COMFYUI",
                generationDate = System.currentTimeMillis(),
                generationTime = "09:05:12",
                createdBy = "Studio Designer",
                width = 1080,
                height = 1080,
                status = "ACTIVE",
                usageCount = 18,
                shareCount = 12,
                downloadCount = 20,
                coverAppliedCount = 4
            )
        )
        repository.saveAllArchiveImages(sampleList)
    }

    fun toggleSelectArchiveId(id: Long) {
        val current = selectedArchiveIds.value.toMutableSet()
        if (current.contains(id)) {
            current.remove(id)
        } else {
            current.add(id)
        }
        selectedArchiveIds.value = current
    }

    fun selectAllFiltered() {
        selectedArchiveIds.value = filteredArchives.value.map { it.id }.toSet()
    }

    fun clearSelection() {
        selectedArchiveIds.value = emptySet()
    }

    fun softDelete(id: Long) {
        viewModelScope.launch {
            repository.softDeleteArchiveImage(id)
        }
    }

    fun bulkSoftDelete() {
        viewModelScope.launch {
            val ids = selectedArchiveIds.value.toList()
            if (ids.isNotEmpty()) {
                repository.softDeleteAllArchiveImages(ids)
                clearSelection()
            }
        }
    }

    fun restore(id: Long) {
        viewModelScope.launch {
            repository.restoreArchiveImage(id)
        }
    }

    fun bulkRestore() {
        viewModelScope.launch {
            val ids = selectedArchiveIds.value.toList()
            if (ids.isNotEmpty()) {
                repository.restoreAllArchiveImages(ids)
                clearSelection()
            }
        }
    }

    fun deletePermanently(id: Long) {
        viewModelScope.launch {
            repository.deleteArchiveImagePermanently(id)
        }
    }

    fun bulkDeletePermanently() {
        viewModelScope.launch {
            val ids = selectedArchiveIds.value.toList()
            if (ids.isNotEmpty()) {
                repository.deleteAllArchiveImagesPermanently(ids)
                clearSelection()
            }
        }
    }

    fun incrementUsage(id: Long) {
        viewModelScope.launch {
            repository.incrementArchiveUsageCount(id)
        }
    }

    fun incrementShare(id: Long) {
        viewModelScope.launch {
            repository.incrementArchiveShareCount(id)
        }
    }

    fun incrementDownload(id: Long) {
        viewModelScope.launch {
            repository.incrementArchiveDownloadCount(id)
        }
    }

    fun setAsMainCover(archiveItem: AiImageArchiveEntity) {
        viewModelScope.launch {
            repository.incrementArchiveCoverAppliedCount(archiveItem.id)
            val pId = archiveItem.productId
            if (pId > 0) {
                val product = repository.getProductById(pId.toString())
                if (product != null) {
                    repository.updateProduct(product.copy(image = archiveItem.imageUri))
                }
            }
        }
    }

    fun createNewAiVersion(
        productId: Long,
        sku: String,
        qrNumber: String,
        productName: String,
        imageUri: String,
        providerName: String,
        prompt: String
    ) {
        viewModelScope.launch {
            repository.createArchiveVersion(
                productId = productId,
                sku = sku,
                qrNumber = qrNumber,
                productName = productName,
                imageUri = imageUri,
                providerName = providerName,
                prompt = prompt,
                imageSource = "AI"
            )
        }
    }
}

class AiArchiveViewModelFactory(private val repository: VascsRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AiArchiveViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AiArchiveViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
