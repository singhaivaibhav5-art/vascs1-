package com.example.vascs.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.vascs.data.model.ProductionOrderEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductionOrderDao {
    @Query("SELECT * FROM production_orders ORDER BY productionId DESC")
    fun getAllProductionOrders(): Flow<List<ProductionOrderEntity>>

    @Query("SELECT * FROM production_orders WHERE productionId = :id LIMIT 1")
    suspend fun getProductionOrderById(id: Long): ProductionOrderEntity?

    @Query("SELECT * FROM production_orders WHERE status = :status ORDER BY productionId DESC")
    fun getProductionOrdersByStatus(status: String): Flow<List<ProductionOrderEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProductionOrder(order: ProductionOrderEntity): Long

    @Update
    suspend fun updateProductionOrder(order: ProductionOrderEntity)

    @Query("UPDATE production_orders SET status = :status WHERE productionId = :id")
    suspend fun updateProductionStatus(id: Long, status: String)
}
