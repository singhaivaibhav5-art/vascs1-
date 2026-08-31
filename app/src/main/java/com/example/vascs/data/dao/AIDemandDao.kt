package com.example.vascs.data.dao

import androidx.room.*
import com.example.vascs.data.model.AIDemandForecastEntity
import com.example.vascs.data.model.AIDemandHistoryEntity
import com.example.vascs.data.model.AIDemandModelEntity
import com.example.vascs.data.model.AIDemandRequestEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AIDemandDao {

    // Demand Requests
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRequest(request: AIDemandRequestEntity): Long

    @Query("SELECT * FROM ai_demand_requests ORDER BY createdAt DESC LIMIT 1")
    suspend fun getLatestRequest(): AIDemandRequestEntity?

    // Demand Forecast Results
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertForecast(forecast: AIDemandForecastEntity): Long

    @Update
    suspend fun updateForecast(forecast: AIDemandForecastEntity)

    @Query("DELETE FROM ai_demand_forecasts WHERE forecastId = :forecastId")
    suspend fun deleteForecast(forecastId: Long)

    @Query("SELECT * FROM ai_demand_forecasts WHERE forecastId = :forecastId LIMIT 1")
    suspend fun getForecastById(forecastId: Long): AIDemandForecastEntity?

    @Query("SELECT * FROM ai_demand_forecasts ORDER BY createdAt DESC LIMIT 1")
    fun getLatestForecast(): Flow<AIDemandForecastEntity?>

    @Query("SELECT * FROM ai_demand_forecasts ORDER BY createdAt DESC")
    fun getAllForecasts(): Flow<List<AIDemandForecastEntity>>

    @Query("SELECT * FROM ai_demand_forecasts WHERE isFavorite = 1 ORDER BY createdAt DESC")
    fun getFavoriteForecasts(): Flow<List<AIDemandForecastEntity>>

    // Demand History Audit Log
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHistory(history: AIDemandHistoryEntity): Long

    @Query("SELECT * FROM ai_demand_history ORDER BY timestamp DESC")
    fun getAllHistory(): Flow<List<AIDemandHistoryEntity>>

    @Query("DELETE FROM ai_demand_history WHERE historyId = :historyId")
    suspend fun deleteHistory(historyId: Long)

    @Query("DELETE FROM ai_demand_history")
    suspend fun clearHistory()

    // Forecasting Models & Seasonality Rules
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertModel(model: AIDemandModelEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertModels(models: List<AIDemandModelEntity>)

    @Query("SELECT * FROM ai_demand_models WHERE isActive = 1 ORDER BY category ASC")
    fun getAllModels(): Flow<List<AIDemandModelEntity>>

    @Query("SELECT * FROM ai_demand_models WHERE category = :category AND isActive = 1 LIMIT 1")
    suspend fun getModelByCategory(category: String): AIDemandModelEntity?

    @Query("SELECT COUNT(*) FROM ai_demand_models")
    suspend fun getModelCount(): Int
}
