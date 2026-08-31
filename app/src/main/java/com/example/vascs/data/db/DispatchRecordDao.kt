package com.example.vascs.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.vascs.data.model.DispatchRecordEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DispatchRecordDao {
    @Query("SELECT * FROM dispatch_records ORDER BY id DESC")
    fun getAllDispatchRecords(): Flow<List<DispatchRecordEntity>>

    @Query("SELECT * FROM dispatch_records WHERE orderId = :orderId ORDER BY id DESC")
    fun getDispatchRecordsForOrder(orderId: Long): Flow<List<DispatchRecordEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDispatchRecord(record: DispatchRecordEntity): Long

    @Update
    suspend fun updateDispatchRecord(record: DispatchRecordEntity)
}
