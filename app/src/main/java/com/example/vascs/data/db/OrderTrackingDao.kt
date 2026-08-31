package com.example.vascs.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.vascs.data.model.OrderTrackingEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface OrderTrackingDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTracking(tracking: OrderTrackingEntity): Long

    @Query("SELECT * FROM order_tracking WHERE orderId = :orderId ORDER BY trackingId ASC")
    fun getTrackingForOrder(orderId: Long): Flow<List<OrderTrackingEntity>>

    @Query("SELECT * FROM order_tracking WHERE orderId = :orderId ORDER BY trackingId ASC")
    suspend fun getTrackingForOrderOnce(orderId: Long): List<OrderTrackingEntity>
}
