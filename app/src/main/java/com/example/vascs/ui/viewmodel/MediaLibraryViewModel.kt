package com.example.vascs.ui.viewmodel

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.vascs.data.model.AiCatalogueImageEntity
import com.example.vascs.data.model.MediaLibraryEntity
import com.example.vascs.data.repository.VascsRepository
import com.example.vascs.util.FitMode
import com.example.vascs.util.ImageResizeEngine
import com.example.vascs.util.ImageStorageManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.io.File

class MediaLibraryViewModel(private val repository: VascsRepository) : ViewModel() {

    val searchQuery = MutableStateFlow("")
    val activeFilter = MutableStateFlow("ALL") // "ALL", "AI", "CAMERA", "GALLERY", "RESIZED"
    val selectedItemIds = MutableStateFlow<Set<Long>>(emptySet())

    val aiImages: StateFlow<List<AiCatalogueImageEntity>> = repository.allAiCatalogueImages
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val allMediaItems: StateFlow<List<MediaLibraryEntity>> = repository.allMedia
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val filteredMediaItems: StateFlow<List<MediaLibraryEntity>> = combine(
        allMediaItems,
        searchQuery,
        activeFilter
    ) { items, query, filter ->
        items.filter { media ->
            val matchesSource = if (filter == "ALL" || filter.isBlank()) {
                true
            } else {
                media.imageSource.equals(filter, ignoreCase = true)
            }

            val matchesQuery = if (query.isBlank()) {
                true
            } else {
                (media.productId?.contains(query, ignoreCase = true) == true) ||
                        (media.sku?.contains(query, ignoreCase = true) == true) ||
                        (media.qrNumber?.contains(query, ignoreCase = true) == true) ||
                        (media.notes?.contains(query, ignoreCase = true) == true)
            }

            matchesSource && matchesQuery
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    fun toggleSelection(id: Long) {
        val current = selectedItemIds.value
        selectedItemIds.value = if (current.contains(id)) {
            current - id
        } else {
            current + id
        }
    }

    fun selectAll() {
        selectedItemIds.value = filteredMediaItems.value.map { it.id }.toSet()
    }

    fun clearSelection() {
        selectedItemIds.value = emptySet()
    }

    fun deleteMedia(item: MediaLibraryEntity) {
        viewModelScope.launch {
            repository.deleteMedia(item)
            selectedItemIds.value = selectedItemIds.value - item.id
        }
    }

    fun deleteSelectedMedia() {
        val ids = selectedItemIds.value.toList()
        if (ids.isNotEmpty()) {
            viewModelScope.launch {
                repository.deleteMediaByIds(ids)
                selectedItemIds.value = emptySet()
            }
        }
    }

    fun setPrimaryCover(item: MediaLibraryEntity) {
        viewModelScope.launch {
            val pId = item.productId
            if (!pId.isNullOrBlank()) {
                repository.setPrimaryMedia(item.id, pId)
                val product = repository.getProductById(pId)
                if (product != null) {
                    repository.updateProduct(product.copy(image = item.imageUri))
                }
            }
        }
    }

    fun importGalleryImages(
        context: Context,
        uris: List<Uri>,
        productId: String? = null,
        sku: String? = null,
        qrNumber: String? = null
    ) {
        viewModelScope.launch {
            val entities = mutableListOf<MediaLibraryEntity>()
            for (uri in uris) {
                val result = ImageStorageManager.copyUriToMediaLibrary(
                    context = context,
                    sourceUri = uri,
                    imageSource = "GALLERY"
                )
                if (result != null) {
                    entities.add(
                        MediaLibraryEntity(
                            productId = productId,
                            sku = sku,
                            qrNumber = qrNumber,
                            imageUri = result.uriString,
                            imageSource = "GALLERY",
                            imageType = "ORIGINAL",
                            width = result.width,
                            height = result.height
                        )
                    )
                }
            }
            if (entities.isNotEmpty()) {
                repository.saveMediaAll(entities)
            }
        }
    }

    fun saveCapturedCameraImage(
        context: Context,
        file: File,
        productId: String? = null,
        sku: String? = null,
        qrNumber: String? = null
    ) {
        viewModelScope.launch {
            val dims = ImageStorageManager.getImageDimensions(file)
            val uriString = Uri.fromFile(file).toString()
            val entity = MediaLibraryEntity(
                productId = productId,
                sku = sku,
                qrNumber = qrNumber,
                imageUri = uriString,
                imageSource = "CAMERA",
                imageType = "ORIGINAL",
                width = dims.first,
                height = dims.second
            )
            repository.saveMedia(entity)
        }
    }

    fun resizeAndSaveImage(
        context: Context,
        mediaItem: MediaLibraryEntity,
        targetWidth: Int,
        targetHeight: Int,
        fitMode: FitMode = FitMode.CENTER_CROP,
        onComplete: (Boolean) -> Unit = {}
    ) {
        viewModelScope.launch {
            val result = ImageResizeEngine.resizeAndSave(
                context = context,
                sourceUriString = mediaItem.imageUri,
                targetWidth = targetWidth,
                targetHeight = targetHeight,
                fitMode = fitMode
            )
            if (result != null) {
                val mediaEntity = MediaLibraryEntity(
                    productId = mediaItem.productId,
                    sku = mediaItem.sku,
                    qrNumber = mediaItem.qrNumber,
                    imageUri = result.uriString,
                    imageSource = "RESIZED",
                    imageType = "RESIZED_${targetWidth}x${targetHeight}",
                    width = result.width,
                    height = result.height,
                    notes = mediaItem.notes
                )
                repository.saveMedia(mediaEntity)

                val aiEntity = AiCatalogueImageEntity(
                    productId = mediaItem.productId?.toLongOrNull() ?: 0L,
                    sku = mediaItem.sku ?: "SKU-RESIZED",
                    qrNumber = mediaItem.qrNumber ?: "",
                    productName = mediaItem.notes ?: "Resized Catalogue Photo",
                    imageUri = result.uriString,
                    imageType = "RESIZED_${targetWidth}x${targetHeight}",
                    imageSource = "RESIZED",
                    createdDate = System.currentTimeMillis(),
                    updatedDate = System.currentTimeMillis(),
                    status = "READY",
                    width = result.width,
                    height = result.height,
                    isPrimary = false,
                    notes = "Resized from ID ${mediaItem.id}"
                )
                repository.saveAiCatalogueImage(aiEntity)
                onComplete(true)
            } else {
                onComplete(false)
            }
        }
    }

    fun bulkResizeAndSave(
        context: Context,
        mediaItems: List<MediaLibraryEntity>,
        targetWidth: Int,
        targetHeight: Int,
        fitMode: FitMode = FitMode.CENTER_CROP,
        onComplete: (Int) -> Unit = {}
    ) {
        viewModelScope.launch {
            var successCount = 0
            for (item in mediaItems) {
                val result = ImageResizeEngine.resizeAndSave(
                    context = context,
                    sourceUriString = item.imageUri,
                    targetWidth = targetWidth,
                    targetHeight = targetHeight,
                    fitMode = fitMode
                )
                if (result != null) {
                    val mediaEntity = MediaLibraryEntity(
                        productId = item.productId,
                        sku = item.sku,
                        qrNumber = item.qrNumber,
                        imageUri = result.uriString,
                        imageSource = "RESIZED",
                        imageType = "RESIZED_${targetWidth}x${targetHeight}",
                        width = result.width,
                        height = result.height,
                        notes = item.notes
                    )
                    repository.saveMedia(mediaEntity)

                    val aiEntity = AiCatalogueImageEntity(
                        productId = item.productId?.toLongOrNull() ?: 0L,
                        sku = item.sku ?: "SKU-RESIZED",
                        qrNumber = item.qrNumber ?: "",
                        productName = item.notes ?: "Resized Catalogue Photo",
                        imageUri = result.uriString,
                        imageType = "RESIZED_${targetWidth}x${targetHeight}",
                        imageSource = "RESIZED",
                        createdDate = System.currentTimeMillis(),
                        updatedDate = System.currentTimeMillis(),
                        status = "READY",
                        width = result.width,
                        height = result.height,
                        isPrimary = false,
                        notes = "Bulk resized from ID ${item.id}"
                    )
                    repository.saveAiCatalogueImage(aiEntity)
                    successCount++
                }
            }
            onComplete(successCount)
        }
    }
}

class MediaLibraryViewModelFactory(private val repository: VascsRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MediaLibraryViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MediaLibraryViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
