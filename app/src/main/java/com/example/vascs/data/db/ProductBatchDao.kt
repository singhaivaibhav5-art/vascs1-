package com.example.vascs.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.vascs.data.model.ProductBatchEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductBatchDao {

    @Query("SELECT * FROM product_batches ORDER BY createdAt DESC")
    fun getAllBatches(): Flow<List<ProductBatchEntity>>

    @Query("SELECT * FROM product_batches WHERE id = :id")
    suspend fun getBatchById(id: String): ProductBatchEntity?

    @Query("SELECT COUNT(*) FROM product_batches")
    suspend fun getBatchCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBatch(batch: ProductBatchEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(batches: List<ProductBatchEntity>)

    @Update
    suspend fun updateBatch(batch: ProductBatchEntity)

    @Query("DELETE FROM product_batches WHERE id = :id")
    suspend fun deleteBatchById(id: String)
}
