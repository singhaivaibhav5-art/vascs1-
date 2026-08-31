package com.example.vascs.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.vascs.data.model.BusinessTwinEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BusinessTwinDao {
    @Query("SELECT * FROM business_twin ORDER BY twinId DESC")
    fun getAllBusinessTwins(): Flow<List<BusinessTwinEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBusinessTwin(twin: BusinessTwinEntity): Long

    @Update
    suspend fun updateBusinessTwin(twin: BusinessTwinEntity)
}
