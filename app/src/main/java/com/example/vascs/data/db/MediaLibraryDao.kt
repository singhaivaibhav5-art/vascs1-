package com.example.vascs.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.vascs.data.model.MediaLibraryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MediaLibraryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: MediaLibraryEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<MediaLibraryEntity>): List<Long>

    @Update
    suspend fun update(item: MediaLibraryEntity)

    @Delete
    suspend fun delete(item: MediaLibraryEntity)

    @Query("DELETE FROM media_library WHERE id IN (:ids)")
    suspend fun deleteByIds(ids: List<Long>)

    @Query("SELECT * FROM media_library ORDER BY createdDate DESC")
    fun getAll(): Flow<List<MediaLibraryEntity>>

    @Query("SELECT * FROM media_library WHERE id = :id")
    suspend fun getById(id: Long): MediaLibraryEntity?

    @Query("SELECT * FROM media_library WHERE productId = :productId ORDER BY createdDate DESC")
    fun getByProduct(productId: String): Flow<List<MediaLibraryEntity>>

    @Query("""
        SELECT * FROM media_library 
        WHERE (:query = '' OR sku LIKE '%' || :query || '%' OR qrNumber LIKE '%' || :query || '%' OR productId LIKE '%' || :query || '%')
        ORDER BY createdDate DESC
    """)
    fun search(query: String): Flow<List<MediaLibraryEntity>>

    @Query("SELECT * FROM media_library WHERE imageSource = :source ORDER BY createdDate DESC")
    fun filterBySource(source: String): Flow<List<MediaLibraryEntity>>

    @Query("UPDATE media_library SET isPrimary = 0 WHERE productId = :productId")
    suspend fun clearPrimaryForProduct(productId: String)

    @Query("UPDATE media_library SET isPrimary = 1 WHERE id = :id")
    suspend fun setPrimaryById(id: Long)

    @Transaction
    suspend fun setPrimary(id: Long, productId: String) {
        clearPrimaryForProduct(productId)
        setPrimaryById(id)
    }
}
