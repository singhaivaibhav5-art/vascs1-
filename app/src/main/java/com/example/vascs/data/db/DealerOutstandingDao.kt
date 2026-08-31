package com.example.vascs.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.vascs.data.model.DealerOutstandingEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DealerOutstandingDao {
    @Query("SELECT * FROM dealer_outstanding ORDER BY id DESC")
    fun getAllDealerOutstandings(): Flow<List<DealerOutstandingEntity>>

    @Query("SELECT * FROM dealer_outstanding WHERE dealerId = :dealerId LIMIT 1")
    suspend fun getOutstandingForDealer(dealerId: Long): DealerOutstandingEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDealerOutstanding(outstanding: DealerOutstandingEntity): Long

    @Update
    suspend fun updateDealerOutstanding(outstanding: DealerOutstandingEntity)
}
