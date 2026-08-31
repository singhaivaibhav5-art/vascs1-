package com.example.vascs.data.dao

import androidx.room.*
import com.example.vascs.data.model.AIDealerGrowthForecastEntity
import com.example.vascs.data.model.AIDealerRecommendationEntity
import com.example.vascs.data.model.AIDealerRequestEntity
import com.example.vascs.data.model.AIDealerScoreEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AIDealerDao {

    // Requests
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRequest(request: AIDealerRequestEntity): Long

    @Query("SELECT * FROM ai_dealer_requests ORDER BY timestamp DESC")
    fun getAllRequests(): Flow<List<AIDealerRequestEntity>>

    // Recommendations
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecommendation(recommendation: AIDealerRecommendationEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecommendations(recommendations: List<AIDealerRecommendationEntity>)

    @Update
    suspend fun updateRecommendation(recommendation: AIDealerRecommendationEntity)

    @Query("SELECT * FROM ai_dealer_recommendations ORDER BY timestamp DESC")
    fun getAllRecommendations(): Flow<List<AIDealerRecommendationEntity>>

    @Query("SELECT * FROM ai_dealer_recommendations ORDER BY timestamp DESC LIMIT 1")
    fun getLatestRecommendation(): Flow<AIDealerRecommendationEntity?>

    @Query("SELECT * FROM ai_dealer_recommendations WHERE isFavorite = 1 ORDER BY timestamp DESC")
    fun getFavoriteRecommendations(): Flow<List<AIDealerRecommendationEntity>>

    @Query("SELECT * FROM ai_dealer_recommendations WHERE classification = :classification ORDER BY timestamp DESC")
    fun getRecommendationsByClassification(classification: String): Flow<List<AIDealerRecommendationEntity>>

    @Query("DELETE FROM ai_dealer_recommendations WHERE recommendationId = :id")
    suspend fun deleteRecommendation(id: Long)

    @Query("DELETE FROM ai_dealer_recommendations")
    suspend fun clearRecommendations()

    // Dealer Scores
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertScore(score: AIDealerScoreEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertScores(scores: List<AIDealerScoreEntity>)

    @Query("SELECT * FROM ai_dealer_scores ORDER BY overallScore DESC")
    fun getAllScoresRanked(): Flow<List<AIDealerScoreEntity>>

    @Query("DELETE FROM ai_dealer_scores")
    suspend fun clearScores()

    @Query("SELECT COUNT(*) FROM ai_dealer_scores")
    suspend fun getScoreCount(): Int

    // Growth Forecasts
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGrowthForecast(forecast: AIDealerGrowthForecastEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGrowthForecasts(forecasts: List<AIDealerGrowthForecastEntity>)

    @Query("SELECT * FROM ai_dealer_growth_forecasts ORDER BY annualProjectedRevenue DESC")
    fun getAllGrowthForecasts(): Flow<List<AIDealerGrowthForecastEntity>>

    @Query("DELETE FROM ai_dealer_growth_forecasts WHERE forecastId = :id")
    suspend fun deleteGrowthForecast(id: Long)

    @Query("DELETE FROM ai_dealer_growth_forecasts")
    suspend fun clearGrowthForecasts()
}
