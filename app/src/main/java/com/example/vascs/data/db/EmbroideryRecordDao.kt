package com.example.vascs.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.vascs.data.model.EmbroideryRecordEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface EmbroideryRecordDao {
    @Query("SELECT * FROM embroidery_records ORDER BY embroideryId DESC")
    fun getAllEmbroideryRecords(): Flow<List<EmbroideryRecordEntity>>

    @Query("SELECT * FROM embroidery_records WHERE batchId = :batchId")
    fun getEmbroideryRecordsForBatch(batchId: Long): Flow<List<EmbroideryRecordEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEmbroideryRecord(record: EmbroideryRecordEntity): Long
}
