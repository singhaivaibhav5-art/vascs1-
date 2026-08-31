package com.example.vascs.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.vascs.data.model.ProductionBatchEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductionBatchDao {
    @Query("SELECT * FROM production_batches ORDER BY batchId DESC")
    fun getAllBatches(): Flow<List<ProductionBatchEntity>>

    @Query("SELECT * FROM production_batches WHERE productionId = :productionId ORDER BY batchId DESC")
    fun getBatchesForProduction(productionId: Long): Flow<List<ProductionBatchEntity>>

    @Query("SELECT * FROM production_batches WHERE batchId = :id LIMIT 1")
    suspend fun getBatchById(id: Long): ProductionBatchEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBatch(batch: ProductionBatchEntity): Long

    @Update
    suspend fun updateBatch(batch: ProductionBatchEntity)
}
