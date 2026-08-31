package com.example.vascs.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.vascs.data.model.AiForecastEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AiForecastDao {
    @Query("SELECT * FROM ai_forecasts ORDER BY forecastId DESC")
    fun getAllAiForecasts(): Flow<List<AiForecastEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAiForecast(forecast: AiForecastEntity): Long
}
