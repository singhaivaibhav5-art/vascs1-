package com.example.vascs.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.vascs.data.model.DealerEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DealerDao {

    @Query("SELECT * FROM dealers ORDER BY createdDate DESC")
    fun getAllDealers(): Flow<List<DealerEntity>>

    @Query("SELECT * FROM dealers WHERE dealerType = :dealerType ORDER BY createdDate DESC")
    fun getDealersByType(dealerType: String): Flow<List<DealerEntity>>

    @Query("SELECT * FROM dealers WHERE status = :status ORDER BY createdDate DESC")
    fun getDealersByStatus(status: String): Flow<List<DealerEntity>>

    @Query("SELECT * FROM dealers WHERE id = :id LIMIT 1")
    suspend fun getDealerById(id: Long): DealerEntity?

    @Query("SELECT * FROM dealers WHERE dealerId = :dealerId LIMIT 1")
    suspend fun getDealerByDealerId(dealerId: String): DealerEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(dealer: DealerEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(dealers: List<DealerEntity>)

    @Update
    suspend fun update(dealer: DealerEntity)

    @Delete
    suspend fun delete(dealer: DealerEntity)

    @Query("UPDATE dealers SET status = :status WHERE id = :id")
    suspend fun updateStatus(id: Long, status: String)

    @Query("SELECT COUNT(*) FROM dealers")
    fun getDealerCount(): Flow<Int>
}
