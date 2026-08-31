package com.example.vascs.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.vascs.data.model.CosmosTelemetryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CosmosTelemetryDao {
    @Query("SELECT * FROM cosmos_telemetry ORDER BY metricId DESC")
    fun getAllTelemetry(): Flow<List<CosmosTelemetryEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTelemetry(telemetry: CosmosTelemetryEntity): Long

    @Update
    suspend fun updateTelemetry(telemetry: CosmosTelemetryEntity)
}
