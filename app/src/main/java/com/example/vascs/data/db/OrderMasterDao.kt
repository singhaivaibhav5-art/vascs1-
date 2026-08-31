package com.example.vascs.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.vascs.data.model.OrderMasterEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface OrderMasterDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrder(order: OrderMasterEntity): Long

    @Update
    suspend fun updateOrder(order: OrderMasterEntity)

    @Query("SELECT * FROM order_master ORDER BY orderId DESC")
    fun getAllOrders(): Flow<List<OrderMasterEntity>>

    @Query("SELECT * FROM order_master WHERE orderId = :orderId LIMIT 1")
    suspend fun getOrderById(orderId: Long): OrderMasterEntity?

    @Query("SELECT * FROM order_master WHERE status = :status ORDER BY orderId DESC")
    fun getOrdersByStatus(status: String): Flow<List<OrderMasterEntity>>

    @Query("UPDATE order_master SET status = :status, updatedDate = :updatedDate WHERE orderId = :orderId")
    suspend fun updateOrderStatus(orderId: Long, status: String, updatedDate: String)

    @Query("""
        SELECT * FROM order_master 
        WHERE orderNumber LIKE '%' || :query || '%' 
        OR dealerName LIKE '%' || :query || '%' 
        OR mobile LIKE '%' || :query || '%'
        ORDER BY orderId DESC
    """)
    fun searchOrders(query: String): Flow<List<OrderMasterEntity>>

    @Query("SELECT COUNT(*) FROM order_master")
    fun getOrderCount(): Flow<Int>

    @Query("SELECT COUNT(*) FROM order_master WHERE status = :status")
    fun getOrderCountByStatus(status: String): Flow<Int>
}
