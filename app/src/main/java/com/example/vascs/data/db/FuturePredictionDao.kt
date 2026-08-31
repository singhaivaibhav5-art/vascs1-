package com.example.vascs.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.vascs.data.model.FuturePredictionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FuturePredictionDao {
    @Query("SELECT * FROM future_predictions ORDER BY predictionId DESC")
    fun getAllFuturePredictions(): Flow<List<FuturePredictionEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFuturePrediction(prediction: FuturePredictionEntity): Long

    @Update
    suspend fun updateFuturePrediction(prediction: FuturePredictionEntity)
}
