package com.example.vascs.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.vascs.data.model.MediaCommandCenterEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MediaCommandCenterDao {

    @Query("SELECT * FROM media_command_center WHERE isDeleted = 0 ORDER BY createdDate DESC")
    fun getAllMedia(): Flow<List<MediaCommandCenterEntity>>

    @Query("SELECT * FROM media_command_center WHERE isDeleted = 0 AND isArchived = 0 ORDER BY createdDate DESC")
    fun getActiveMedia(): Flow<List<MediaCommandCenterEntity>>

    @Query("SELECT * FROM media_command_center WHERE isDeleted = 0 AND isArchived = 1 ORDER BY createdDate DESC")
    fun getArchivedMedia(): Flow<List<MediaCommandCenterEntity>>

    @Query("SELECT * FROM media_command_center WHERE isDeleted = 1 ORDER BY updatedDate DESC")
    fun getRecycleBinMedia(): Flow<List<MediaCommandCenterEntity>>

    @Query("SELECT * FROM media_command_center WHERE productId = :productId AND isDeleted = 0 ORDER BY versionNumber ASC")
    fun getMediaHistoryByProduct(productId: Long): Flow<List<MediaCommandCenterEntity>>

    @Query("SELECT * FROM media_command_center WHERE sku = :sku AND isDeleted = 0 ORDER BY versionNumber ASC")
    fun getMediaHistoryBySku(sku: String): Flow<List<MediaCommandCenterEntity>>

    @Query("SELECT * FROM media_command_center WHERE id = :id LIMIT 1")
    suspend fun getMediaById(id: Long): MediaCommandCenterEntity?

    @Query("SELECT * FROM media_command_center WHERE mediaId = :mediaId LIMIT 1")
    suspend fun getMediaByMediaId(mediaId: String): MediaCommandCenterEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(media: MediaCommandCenterEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(mediaList: List<MediaCommandCenterEntity>)

    @Update
    suspend fun update(media: MediaCommandCenterEntity)

    @Query("UPDATE media_command_center SET isDeleted = 1, updatedDate = :updatedDate WHERE id = :id")
    suspend fun softDelete(id: Long, updatedDate: Long = System.currentTimeMillis())

    @Query("UPDATE media_command_center SET isDeleted = 1, updatedDate = :updatedDate WHERE id IN (:ids)")
    suspend fun softDeleteAll(ids: List<Long>, updatedDate: Long = System.currentTimeMillis())

    @Query("UPDATE media_command_center SET isArchived = 1, updatedDate = :updatedDate WHERE id = :id")
    suspend fun archive(id: Long, updatedDate: Long = System.currentTimeMillis())

    @Query("UPDATE media_command_center SET isArchived = 1, updatedDate = :updatedDate WHERE id IN (:ids)")
    suspend fun archiveAll(ids: List<Long>, updatedDate: Long = System.currentTimeMillis())

    @Query("UPDATE media_command_center SET isDeleted = 0, isArchived = 0, updatedDate = :updatedDate WHERE id = :id")
    suspend fun restore(id: Long, updatedDate: Long = System.currentTimeMillis())

    @Query("UPDATE media_command_center SET isDeleted = 0, isArchived = 0, updatedDate = :updatedDate WHERE id IN (:ids)")
    suspend fun restoreAll(ids: List<Long>, updatedDate: Long = System.currentTimeMillis())

    @Query("DELETE FROM media_command_center WHERE id = :id")
    suspend fun deletePermanently(id: Long)

    @Query("DELETE FROM media_command_center WHERE id IN (:ids)")
    suspend fun deleteAllPermanently(ids: List<Long>)

    @Query("UPDATE media_command_center SET viewCount = viewCount + 1 WHERE id = :id")
    suspend fun incrementViewCount(id: Long)

    @Query("UPDATE media_command_center SET shareCount = shareCount + 1 WHERE id = :id")
    suspend fun incrementShareCount(id: Long)

    @Query("UPDATE media_command_center SET downloadCount = downloadCount + 1 WHERE id = :id")
    suspend fun incrementDownloadCount(id: Long)

    @Query("SELECT * FROM media_command_center WHERE isDeleted = 0 ORDER BY viewCount DESC LIMIT 10")
    fun getTopViewedMedia(): Flow<List<MediaCommandCenterEntity>>

    @Query("SELECT * FROM media_command_center WHERE isDeleted = 0 ORDER BY shareCount DESC LIMIT 10")
    fun getTopSharedMedia(): Flow<List<MediaCommandCenterEntity>>

    @Query("SELECT * FROM media_command_center WHERE isDeleted = 0 ORDER BY downloadCount DESC LIMIT 10")
    fun getTopDownloadedMedia(): Flow<List<MediaCommandCenterEntity>>
}
