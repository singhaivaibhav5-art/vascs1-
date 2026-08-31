package com.example.vascs.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.vascs.data.model.ExportQueueEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ExportQueueDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: ExportQueueEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<ExportQueueEntity>): List<Long>

    @Update
    suspend fun update(item: ExportQueueEntity)

    @Delete
    suspend fun delete(item: ExportQueueEntity)

    @Query("DELETE FROM export_queue WHERE id = :id")
    suspend fun deleteById(id: Long)

    @Query("DELETE FROM export_queue WHERE status = 'SUCCESS' OR status = 'FAILED' OR status = 'CANCELLED'")
    suspend fun clearCompleted()

    @Query("SELECT * FROM export_queue ORDER BY createdDate DESC")
    fun getAllJobs(): Flow<List<ExportQueueEntity>>

    @Query("SELECT * FROM export_queue WHERE status = 'QUEUED' ORDER BY createdDate ASC")
    suspend fun getPendingJobs(): List<ExportQueueEntity>

    @Query("SELECT * FROM export_queue WHERE id = :id")
    suspend fun getById(id: Long): ExportQueueEntity?

    @Query("UPDATE export_queue SET status = :status, progress = :progress, outputImageUri = :outputUri, errorMessage = :errorMsg, updatedDate = :updatedDate WHERE id = :id")
    suspend fun updateStatus(
        id: Long,
        status: String,
        progress: Int,
        outputUri: String? = null,
        errorMsg: String? = null,
        updatedDate: Long = System.currentTimeMillis()
    )
}
