package com.example.vascs.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.vascs.data.model.DispatchEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DispatchDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDispatch(dispatch: DispatchEntity): Long

    @Query("SELECT * FROM dispatches WHERE orderId = :orderId LIMIT 1")
    fun getDispatchForOrder(orderId: Long): Flow<DispatchEntity?>

    @Query("SELECT * FROM dispatches WHERE orderId = :orderId LIMIT 1")
    suspend fun getDispatchForOrderOnce(orderId: Long): DispatchEntity?

    @Query("SELECT * FROM dispatches ORDER BY dispatchId DESC")
    fun getAllDispatches(): Flow<List<DispatchEntity>>
}
