package com.example.vascs.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.vascs.data.model.CosmosAnalyticsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CosmosAnalyticsDao {
    @Query("SELECT * FROM cosmos_analytics ORDER BY analyticsId DESC")
    fun getAllCosmosAnalytics(): Flow<List<CosmosAnalyticsEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCosmosAnalytics(analytics: CosmosAnalyticsEntity): Long

    @Update
    suspend fun updateCosmosAnalytics(analytics: CosmosAnalyticsEntity)
}
