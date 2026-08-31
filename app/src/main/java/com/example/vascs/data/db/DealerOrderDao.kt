package com.example.vascs.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.vascs.data.model.DealerOrderEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DealerOrderDao {

    @Query("SELECT * FROM dealer_orders ORDER BY createdDate DESC")
    fun getAllOrders(): Flow<List<DealerOrderEntity>>

    @Query("SELECT * FROM dealer_orders WHERE dealerId = :dealerId ORDER BY createdDate DESC")
    fun getOrdersForDealer(dealerId: String): Flow<List<DealerOrderEntity>>

    @Query("SELECT * FROM dealer_orders WHERE status = :status ORDER BY createdDate DESC")
    fun getOrdersByStatus(status: String): Flow<List<DealerOrderEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(order: DealerOrderEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(orders: List<DealerOrderEntity>)

    @Update
    suspend fun update(order: DealerOrderEntity)

    @Query("UPDATE dealer_orders SET status = :status WHERE id = :id")
    suspend fun updateOrderStatus(id: Long, status: String)

    @Delete
    suspend fun delete(order: DealerOrderEntity)

    @Query("SELECT COUNT(*) FROM dealer_orders")
    fun getOrderCount(): Flow<Int>
}
