package com.example.vascs.data.dao

import androidx.room.*
import com.example.vascs.data.model.AIInventoryAlertEntity
import com.example.vascs.data.model.AIInventoryForecastEntity
import com.example.vascs.data.model.AIInventoryHealthEntity
import com.example.vascs.data.model.AIInventoryRecommendationEntity
import com.example.vascs.data.model.AIInventoryRequestEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AIInventoryDao {

    // Requests
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRequest(request: AIInventoryRequestEntity): Long

    @Query("SELECT * FROM ai_inventory_requests ORDER BY timestamp DESC")
    fun getAllRequests(): Flow<List<AIInventoryRequestEntity>>

    // Forecasts
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertForecast(forecast: AIInventoryForecastEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertForecasts(forecasts: List<AIInventoryForecastEntity>)

    @Update
    suspend fun updateForecast(forecast: AIInventoryForecastEntity)

    @Query("SELECT * FROM ai_inventory_forecasts ORDER BY timestamp DESC")
    fun getAllForecasts(): Flow<List<AIInventoryForecastEntity>>

    @Query("SELECT * FROM ai_inventory_forecasts ORDER BY timestamp DESC LIMIT 1")
    fun getLatestForecast(): Flow<AIInventoryForecastEntity?>

    @Query("SELECT * FROM ai_inventory_forecasts WHERE velocityClassification = 'FAST_MOVING' ORDER BY fastMovingScore DESC")
    fun getFastMovingStock(): Flow<List<AIInventoryForecastEntity>>

    @Query("SELECT * FROM ai_inventory_forecasts WHERE velocityClassification = 'SLOW_MOVING' ORDER BY timestamp DESC")
    fun getSlowMovingStock(): Flow<List<AIInventoryForecastEntity>>

    @Query("SELECT * FROM ai_inventory_forecasts WHERE velocityClassification = 'DEAD_STOCK' ORDER BY deadStockRiskScore DESC")
    fun getDeadStockList(): Flow<List<AIInventoryForecastEntity>>

    @Query("SELECT * FROM ai_inventory_forecasts WHERE isFavorite = 1 ORDER BY timestamp DESC")
    fun getFavoriteForecasts(): Flow<List<AIInventoryForecastEntity>>

    @Query("DELETE FROM ai_inventory_forecasts WHERE forecastId = :id")
    suspend fun deleteForecast(id: Long)

    @Query("DELETE FROM ai_inventory_forecasts")
    suspend fun clearForecasts()

    // Alerts
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAlert(alert: AIInventoryAlertEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAlerts(alerts: List<AIInventoryAlertEntity>)

    @Update
    suspend fun updateAlert(alert: AIInventoryAlertEntity)

    @Query("SELECT * FROM ai_inventory_alerts ORDER BY CASE severity WHEN 'CRITICAL' THEN 1 WHEN 'HIGH' THEN 2 WHEN 'MEDIUM' THEN 3 ELSE 4 END, timestamp DESC")
    fun getAllAlerts(): Flow<List<AIInventoryAlertEntity>>

    @Query("SELECT * FROM ai_inventory_alerts WHERE isResolved = 0 ORDER BY timestamp DESC")
    fun getActiveAlerts(): Flow<List<AIInventoryAlertEntity>>

    @Query("SELECT * FROM ai_inventory_alerts WHERE alertType = :type ORDER BY timestamp DESC")
    fun getAlertsByType(type: String): Flow<List<AIInventoryAlertEntity>>

    @Query("UPDATE ai_inventory_alerts SET isResolved = 1 WHERE alertId = :id")
    suspend fun resolveAlert(id: Long)

    @Query("DELETE FROM ai_inventory_alerts WHERE alertId = :id")
    suspend fun deleteAlert(id: Long)

    @Query("DELETE FROM ai_inventory_alerts")
    suspend fun clearAlerts()

    // Health
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHealth(health: AIInventoryHealthEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHealthList(healthList: List<AIInventoryHealthEntity>)

    @Query("SELECT * FROM ai_inventory_health ORDER BY timestamp DESC LIMIT 1")
    fun getLatestHealth(): Flow<AIInventoryHealthEntity?>

    @Query("SELECT * FROM ai_inventory_health ORDER BY timestamp DESC")
    fun getAllHealthRecords(): Flow<List<AIInventoryHealthEntity>>

    @Query("DELETE FROM ai_inventory_health")
    suspend fun clearHealthRecords()

    // Recommendations
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecommendation(rec: AIInventoryRecommendationEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecommendations(recs: List<AIInventoryRecommendationEntity>)

    @Update
    suspend fun updateRecommendation(rec: AIInventoryRecommendationEntity)

    @Query("SELECT * FROM ai_inventory_recommendations ORDER BY CASE priority WHEN 'CRITICAL' THEN 1 WHEN 'HIGH' THEN 2 WHEN 'MEDIUM' THEN 3 ELSE 4 END, timestamp DESC")
    fun getAllRecommendations(): Flow<List<AIInventoryRecommendationEntity>>

    @Query("SELECT * FROM ai_inventory_recommendations WHERE isApplied = 0 ORDER BY timestamp DESC")
    fun getPendingRecommendations(): Flow<List<AIInventoryRecommendationEntity>>

    @Query("UPDATE ai_inventory_recommendations SET isApplied = 1 WHERE recommendationId = :id")
    suspend fun applyRecommendation(id: Long)

    @Query("DELETE FROM ai_inventory_recommendations WHERE recommendationId = :id")
    suspend fun deleteRecommendation(id: Long)

    @Query("DELETE FROM ai_inventory_recommendations")
    suspend fun clearRecommendations()
}
