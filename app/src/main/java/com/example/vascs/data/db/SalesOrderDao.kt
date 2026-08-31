package com.example.vascs.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.vascs.data.model.SalesOrderEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SalesOrderDao {
    @Query("SELECT * FROM sales_orders ORDER BY id DESC")
    fun getAllSalesOrders(): Flow<List<SalesOrderEntity>>

    @Query("SELECT * FROM sales_orders WHERE id = :id LIMIT 1")
    suspend fun getSalesOrderById(id: Long): SalesOrderEntity?

    @Query("SELECT * FROM sales_orders WHERE dealerId = :dealerId ORDER BY id DESC")
    fun getSalesOrdersForDealer(dealerId: Long): Flow<List<SalesOrderEntity>>

    @Query("SELECT * FROM sales_orders WHERE orderStatus = :status ORDER BY id DESC")
    fun getSalesOrdersByStatus(status: String): Flow<List<SalesOrderEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSalesOrder(order: SalesOrderEntity): Long

    @Update
    suspend fun updateSalesOrder(order: SalesOrderEntity)

    @Query("UPDATE sales_orders SET orderStatus = :status, updatedDate = :updatedDate WHERE id = :id")
    suspend fun updateOrderStatus(id: Long, status: String, updatedDate: String)
}
