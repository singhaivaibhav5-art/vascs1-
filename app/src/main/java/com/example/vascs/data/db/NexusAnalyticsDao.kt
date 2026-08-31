package com.example.vascs.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.vascs.data.model.NexusAnalyticsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NexusAnalyticsDao {
    @Query("SELECT * FROM nexus_analytics ORDER BY analyticsId DESC")
    fun getAllNexusAnalytics(): Flow<List<NexusAnalyticsEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNexusAnalytics(analytics: NexusAnalyticsEntity): Long

    @Update
    suspend fun updateNexusAnalytics(analytics: NexusAnalyticsEntity)
}
