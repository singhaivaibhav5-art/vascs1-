package com.example.vascs.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.vascs.data.model.MediaCommandCenterEntity
import com.example.vascs.data.model.ProductEntity
import com.example.vascs.data.repository.VascsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

enum class MediaSortOption {
    NEWEST_FIRST,
    OLDEST_FIRST,
    MOST_VIEWED,
    MOST_SHARED,
    MOST_DOWNLOADED,
    MOST_RECENT_VERSION
}

class MediaCommandCenterViewModel(private val repository: VascsRepository) : ViewModel() {

    val products: StateFlow<List<ProductEntity>> = repository.allProducts
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val rawMediaItems: StateFlow<List<MediaCommandCenterEntity>> = repository.allCommandCenterMedia
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val rawRecycleBin: StateFlow<List<MediaCommandCenterEntity>> = repository.recycleBinCommandCenterMedia
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val searchQuery = MutableStateFlow("")
    val selectedTypeFilter = MutableStateFlow("ALL") // ALL, ORIGINAL, CAMERA, GALLERY, AI, RESIZED, EXPORT, ARCHIVE
    val selectedSourceFilter = MutableStateFlow("ALL") // ALL, MANUAL, CAMERA, GALLERY, NANO_BANANA, GOOGLE_AI_STUDIO, OOTDIFFUSION, COMFYUI, IMPORT
    val sortOption = MutableStateFlow(MediaSortOption.NEWEST_FIRST)

    val selectedMediaIds = MutableStateFlow<Set<Long>>(emptySet())

    val compareMediaA = MutableStateFlow<MediaCommandCenterEntity?>(null)
    val compareMediaB = MutableStateFlow<MediaCommandCenterEntity?>(null)

    val filteredMediaItems: StateFlow<List<MediaCommandCenterEntity>> = combine(
        rawMediaItems,
        searchQuery,
        selectedTypeFilter,
        selectedSourceFilter,
        sortOption
    ) { items, query, typeFilter, sourceFilter, sort ->
        var list = items.filter { item ->
            // Exclude soft deleted items from active list
            if (item.isDeleted) return@filter false

            val matchesQuery = query.isBlank() ||
                    item.productName.contains(query, ignoreCase = true) ||
                    item.sku.contains(query, ignoreCase = true) ||
                    item.qrNumber.contains(query, ignoreCase = true) ||
                    item.mediaId.contains(query, ignoreCase = true)

            val matchesType = when (typeFilter) {
                "ALL" -> true
                "ARCHIVE" -> item.isArchived
                else -> item.mediaType.equals(typeFilter, ignoreCase = true) && !item.isArchived
            }

            val matchesSource = sourceFilter == "ALL" || item.mediaSource.equals(sourceFilter, ignoreCase = true)

            matchesQuery && matchesType && matchesSource
        }

        list = when (sort) {
            MediaSortOption.NEWEST_FIRST -> list.sortedByDescending { it.createdDate }
            MediaSortOption.OLDEST_FIRST -> list.sortedBy { it.createdDate }
            MediaSortOption.MOST_VIEWED -> list.sortedByDescending { it.viewCount }
            MediaSortOption.MOST_SHARED -> list.sortedByDescending { it.shareCount }
            MediaSortOption.MOST_DOWNLOADED -> list.sortedByDescending { it.downloadCount }
            MediaSortOption.MOST_RECENT_VERSION -> list.sortedByDescending { it.versionNumber }
        }

        list
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    init {
        viewModelScope.launch {
            rawMediaItems.collect { items ->
                if (items.isEmpty()) {
                    seedDefaultMedia()
                }
            }
        }
    }

    private suspend fun seedDefaultMedia() {
        val sampleList = listOf(
            MediaCommandCenterEntity(
                mediaId = "MED-KJL1001-ORIG",
                productId = 1L,
                productName = "Kanjivaram Silk Saree - Royal Red",
                sku = "KJL-1001",
                qrNumber = "QR-KJL1001",
                mediaType = "ORIGINAL",
                mediaSource = "MANUAL",
                versionNumber = 1,
                imageUri = "https://images.unsplash.com/photo-1610030469983-98e550d6193c?w=800",
                thumbnailUri = "https://images.unsplash.com/photo-1610030469983-98e550d6193c?w=800",
                createdDate = System.currentTimeMillis() - 86400000L * 5,
                width = 1080,
                height = 1080,
                isPrimary = true,
                viewCount = 42,
                shareCount = 15,
                downloadCount = 20
            ),
            MediaCommandCenterEntity(
                mediaId = "MED-KJL1001-AI-V1",
                productId = 1L,
                productName = "Kanjivaram Silk Saree - Royal Red",
                sku = "KJL-1001",
                qrNumber = "QR-KJL1001",
                mediaType = "AI",
                mediaSource = "NANO_BANANA",
                versionNumber = 2,
                imageUri = "https://images.unsplash.com/photo-1617627143750-d86bc21e42bb?w=800",
                thumbnailUri = "https://images.unsplash.com/photo-1617627143750-d86bc21e42bb?w=800",
                createdDate = System.currentTimeMillis() - 86400000L * 4,
                width = 1080,
                height = 1080,
                viewCount = 68,
                shareCount = 34,
                downloadCount = 45
            ),
            MediaCommandCenterEntity(
                mediaId = "MED-BAN2002-ORIG",
                productId = 2L,
                productName = "Banarasi Brocade Silk - Emerald Green",
                sku = "BAN-2002",
                qrNumber = "QR-BAN2002",
                mediaType = "ORIGINAL",
                mediaSource = "CAMERA",
                versionNumber = 1,
                imageUri = "https://images.unsplash.com/photo-1583391733956-3750e0ff4e8b?w=800",
                thumbnailUri = "https://images.unsplash.com/photo-1583391733956-3750e0ff4e8b?w=800",
                createdDate = System.currentTimeMillis() - 86400000L * 3,
                width = 1080,
                height = 1350,
                isPrimary = true,
                viewCount = 35,
                shareCount = 12,
                downloadCount = 18
            ),
            MediaCommandCenterEntity(
                mediaId = "MED-BAN2002-RESIZED",
                productId = 2L,
                productName = "Banarasi Brocade Silk - Emerald Green",
                sku = "BAN-2002",
                qrNumber = "QR-BAN2002",
                mediaType = "RESIZED",
                mediaSource = "MANUAL",
                versionNumber = 2,
                imageUri = "https://images.unsplash.com/photo-1583391733956-3750e0ff4e8b?w=800",
                thumbnailUri = "https://images.unsplash.com/photo-1583391733956-3750e0ff4e8b?w=800",
                createdDate = System.currentTimeMillis() - 86400000L * 2,
                width = 1080,
                height = 1080,
                viewCount = 22,
                shareCount = 8,
                downloadCount = 10
            ),
            MediaCommandCenterEntity(
                mediaId = "MED-COT3003-AI-V1",
                productId = 3L,
                productName = "Chanderi Cotton Blend - Peacock Blue",
                sku = "COT-3003",
                qrNumber = "QR-COT3003",
                mediaType = "AI",
                mediaSource = "GOOGLE_AI_STUDIO",
                versionNumber = 1,
                imageUri = "https://images.unsplash.com/photo-1609357605129-26f69add5d6e?w=800",
                thumbnailUri = "https://images.unsplash.com/photo-1609357605129-26f69add5d6e?w=800",
                createdDate = System.currentTimeMillis() - 86400000L * 1,
                width = 1080,
                height = 1080,
                viewCount = 55,
                shareCount = 28,
                downloadCount = 31
            ),
            MediaCommandCenterEntity(
                mediaId = "MED-COT3003-EXPORT-INSTA",
                productId = 3L,
                productName = "Chanderi Cotton Blend - Peacock Blue",
                sku = "COT-3003",
                qrNumber = "QR-COT3003",
                mediaType = "EXPORT",
                mediaSource = "IMPORT",
                versionNumber = 2,
                imageUri = "https://images.unsplash.com/photo-1609357605129-26f69add5d6e?w=800",
                thumbnailUri = "https://images.unsplash.com/photo-1609357605129-26f69add5d6e?w=800",
                createdDate = System.currentTimeMillis(),
                width = 1080,
                height = 1350,
                viewCount = 18,
                shareCount = 14,
                downloadCount = 25
            )
        )
        repository.saveAllCommandCenterMedia(sampleList)
    }

    fun toggleSelectMedia(id: Long) {
        val current = selectedMediaIds.value.toMutableSet()
        if (current.contains(id)) {
            current.remove(id)
        } else {
            current.add(id)
        }
        selectedMediaIds.value = current
    }

    fun selectAllFiltered() {
        selectedMediaIds.value = filteredMediaItems.value.map { it.id }.toSet()
    }

    fun clearSelection() {
        selectedMediaIds.value = emptySet()
    }

    fun archiveMedia(id: Long) {
        viewModelScope.launch {
            repository.archiveMedia(id)
        }
    }

    fun bulkArchive() {
        viewModelScope.launch {
            val ids = selectedMediaIds.value.toList()
            if (ids.isNotEmpty()) {
                repository.archiveAllMedia(ids)
                clearSelection()
            }
        }
    }

    fun softDelete(id: Long) {
        viewModelScope.launch {
            repository.softDeleteMedia(id)
        }
    }

    fun bulkSoftDelete() {
        viewModelScope.launch {
            val ids = selectedMediaIds.value.toList()
            if (ids.isNotEmpty()) {
                repository.softDeleteAllMedia(ids)
                clearSelection()
            }
        }
    }

    fun restore(id: Long) {
        viewModelScope.launch {
            repository.restoreMedia(id)
        }
    }

    fun bulkRestore() {
        viewModelScope.launch {
            val ids = selectedMediaIds.value.toList()
            if (ids.isNotEmpty()) {
                repository.restoreAllMedia(ids)
                clearSelection()
            }
        }
    }

    fun deletePermanently(id: Long) {
        viewModelScope.launch {
            repository.deleteMediaPermanently(id)
        }
    }

    fun bulkDeletePermanently() {
        viewModelScope.launch {
            val ids = selectedMediaIds.value.toList()
            if (ids.isNotEmpty()) {
                repository.deleteAllMediaPermanently(ids)
                clearSelection()
            }
        }
    }

    fun incrementView(id: Long) {
        viewModelScope.launch {
            repository.incrementMediaViewCount(id)
        }
    }

    fun incrementShare(id: Long) {
        viewModelScope.launch {
            repository.incrementMediaShareCount(id)
        }
    }

    fun incrementDownload(id: Long) {
        viewModelScope.launch {
            repository.incrementMediaDownloadCount(id)
        }
    }

    fun setAsPrimaryCover(item: MediaCommandCenterEntity) {
        viewModelScope.launch {
            val pId = item.productId
            if (pId > 0) {
                val product = repository.getProductById(pId.toString())
                if (product != null) {
                    repository.updateProduct(product.copy(image = item.imageUri))
                }
            }
        }
    }
}

class MediaCommandCenterViewModelFactory(private val repository: VascsRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MediaCommandCenterViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MediaCommandCenterViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
