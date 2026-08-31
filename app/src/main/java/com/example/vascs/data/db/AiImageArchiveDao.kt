package com.example.vascs.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.vascs.data.model.AiImageArchiveEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AiImageArchiveDao {

    @Query("SELECT * FROM ai_image_archive WHERE isDeleted = 0 ORDER BY generationDate DESC")
    fun getAllActiveArchives(): Flow<List<AiImageArchiveEntity>>

    @Query("SELECT * FROM ai_image_archive WHERE isDeleted = 1 ORDER BY generationDate DESC")
    fun getRecycleBinArchives(): Flow<List<AiImageArchiveEntity>>

    @Query("SELECT * FROM ai_image_archive WHERE productId = :productId AND isDeleted = 0 ORDER BY versionNumber DESC")
    fun getHistoryForProduct(productId: Long): Flow<List<AiImageArchiveEntity>>

    @Query("SELECT * FROM ai_image_archive WHERE sku = :sku AND isDeleted = 0 ORDER BY versionNumber DESC")
    fun getHistoryForSku(sku: String): Flow<List<AiImageArchiveEntity>>

    @Query("SELECT MAX(versionNumber) FROM ai_image_archive WHERE productId = :productId OR sku = :sku")
    suspend fun getMaxVersionNumber(productId: Long, sku: String): Int?

    @Query("SELECT * FROM ai_image_archive WHERE id = :id LIMIT 1")
    suspend fun getArchiveById(id: Long): AiImageArchiveEntity?

    @Query("SELECT * FROM ai_image_archive WHERE archiveId = :archiveId LIMIT 1")
    suspend fun getArchiveByArchiveId(archiveId: String): AiImageArchiveEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(archive: AiImageArchiveEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(archives: List<AiImageArchiveEntity>)

    @Update
    suspend fun update(archive: AiImageArchiveEntity)

    @Query("UPDATE ai_image_archive SET isDeleted = 1 WHERE id = :id")
    suspend fun softDelete(id: Long)

    @Query("UPDATE ai_image_archive SET isDeleted = 1 WHERE id IN (:ids)")
    suspend fun softDeleteAll(ids: List<Long>)

    @Query("UPDATE ai_image_archive SET isDeleted = 0 WHERE id = :id")
    suspend fun restore(id: Long)

    @Query("UPDATE ai_image_archive SET isDeleted = 0 WHERE id IN (:ids)")
    suspend fun restoreAll(ids: List<Long>)

    @Query("DELETE FROM ai_image_archive WHERE id = :id")
    suspend fun deletePermanently(id: Long)

    @Query("DELETE FROM ai_image_archive WHERE id IN (:ids)")
    suspend fun deleteAllPermanently(ids: List<Long>)

    @Query("UPDATE ai_image_archive SET usageCount = usageCount + 1 WHERE id = :id")
    suspend fun incrementUsageCount(id: Long)

    @Query("UPDATE ai_image_archive SET shareCount = shareCount + 1 WHERE id = :id")
    suspend fun incrementShareCount(id: Long)

    @Query("UPDATE ai_image_archive SET downloadCount = downloadCount + 1 WHERE id = :id")
    suspend fun incrementDownloadCount(id: Long)

    @Query("UPDATE ai_image_archive SET coverAppliedCount = coverAppliedCount + 1 WHERE id = :id")
    suspend fun incrementCoverAppliedCount(id: Long)

    @Query("SELECT * FROM ai_image_archive WHERE isDeleted = 0 ORDER BY usageCount DESC LIMIT 10")
    fun getTopUsedImages(): Flow<List<AiImageArchiveEntity>>

    @Query("SELECT * FROM ai_image_archive WHERE isDeleted = 0 ORDER BY shareCount DESC LIMIT 10")
    fun getMostSharedImages(): Flow<List<AiImageArchiveEntity>>

    @Query("SELECT * FROM ai_image_archive WHERE isDeleted = 0 ORDER BY downloadCount DESC LIMIT 10")
    fun getMostDownloadedImages(): Flow<List<AiImageArchiveEntity>>

    @Query("SELECT * FROM ai_image_archive WHERE isDeleted = 0 ORDER BY coverAppliedCount DESC LIMIT 10")
    fun getMostUsedCoverImages(): Flow<List<AiImageArchiveEntity>>
}
