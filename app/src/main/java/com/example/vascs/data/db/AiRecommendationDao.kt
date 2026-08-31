package com.example.vascs.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.vascs.data.model.AiRecommendationEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AiRecommendationDao {
    @Query("SELECT * FROM ai_recommendations ORDER BY recommendationId DESC")
    fun getAllAiRecommendations(): Flow<List<AiRecommendationEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAiRecommendation(recommendation: AiRecommendationEntity): Long

    @Update
    suspend fun updateAiRecommendation(recommendation: AiRecommendationEntity)
}
