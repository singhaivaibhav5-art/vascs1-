package com.example.vascs.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.vascs.data.model.QualityCheckEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface QualityCheckDao {
    @Query("SELECT * FROM quality_checks ORDER BY qcId DESC")
    fun getAllQualityChecks(): Flow<List<QualityCheckEntity>>

    @Query("SELECT * FROM quality_checks WHERE batchId = :batchId")
    fun getQualityChecksForBatch(batchId: Long): Flow<List<QualityCheckEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQualityCheck(qc: QualityCheckEntity): Long
}
