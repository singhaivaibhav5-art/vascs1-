package com.example.vascs.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.vascs.data.model.SocialAnalyticsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SocialAnalyticsDao {

    @Query("SELECT * FROM social_analytics ORDER BY timestamp DESC")
    fun getAllEvents(): Flow<List<SocialAnalyticsEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(event: SocialAnalyticsEntity): Long

    @Query("SELECT * FROM social_analytics WHERE eventType = :eventType ORDER BY timestamp DESC")
    fun getEventsByType(eventType: String): Flow<List<SocialAnalyticsEntity>>
}
