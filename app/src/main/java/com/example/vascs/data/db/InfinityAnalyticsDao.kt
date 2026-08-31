package com.example.vascs.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.vascs.data.model.InfinityAnalyticsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface InfinityAnalyticsDao {
    @Query("SELECT * FROM infinity_analytics ORDER BY metricId DESC")
    fun getAllInfinityAnalytics(): Flow<List<InfinityAnalyticsEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertInfinityAnalytics(analytics: InfinityAnalyticsEntity): Long

    @Update
    suspend fun updateInfinityAnalytics(analytics: InfinityAnalyticsEntity)
}
