package com.example.vascs.data.repository

import com.example.vascs.data.db.MediaLibraryDao
import com.example.vascs.data.model.MediaLibraryEntity
import kotlinx.coroutines.flow.Flow

class MediaLibraryRepository(private val mediaLibraryDao: MediaLibraryDao) {

    val allMedia: Flow<List<MediaLibraryEntity>> = mediaLibraryDao.getAll()

    suspend fun saveMedia(item: MediaLibraryEntity): Long {
        return mediaLibraryDao.insert(item)
    }

    suspend fun saveMediaAll(items: List<MediaLibraryEntity>): List<Long> {
        return mediaLibraryDao.insertAll(items)
    }

    suspend fun deleteMedia(item: MediaLibraryEntity) {
        mediaLibraryDao.delete(item)
    }

    suspend fun deleteMediaByIds(ids: List<Long>) {
        mediaLibraryDao.deleteByIds(ids)
    }

    suspend fun getMedia(id: Long): MediaLibraryEntity? {
        return mediaLibraryDao.getById(id)
    }

    fun getMediaByProduct(productId: String): Flow<List<MediaLibraryEntity>> {
        return mediaLibraryDao.getByProduct(productId)
    }

    fun searchMedia(query: String): Flow<List<MediaLibraryEntity>> {
        return mediaLibraryDao.search(query)
    }

    fun filterMedia(source: String): Flow<List<MediaLibraryEntity>> {
        return if (source == "ALL" || source.isBlank()) {
            mediaLibraryDao.getAll()
        } else {
            mediaLibraryDao.filterBySource(source)
        }
    }

    suspend fun setPrimaryMedia(id: Long, productId: String) {
        mediaLibraryDao.setPrimary(id, productId)
    }
}
