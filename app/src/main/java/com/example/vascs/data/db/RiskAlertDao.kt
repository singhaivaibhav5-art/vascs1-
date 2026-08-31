package com.example.vascs.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.vascs.data.model.RiskAlertEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RiskAlertDao {
    @Query("SELECT * FROM risk_alerts ORDER BY riskId DESC")
    fun getAllRiskAlerts(): Flow<List<RiskAlertEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRiskAlert(alert: RiskAlertEntity): Long

    @Update
    suspend fun updateRiskAlert(alert: RiskAlertEntity)
}
