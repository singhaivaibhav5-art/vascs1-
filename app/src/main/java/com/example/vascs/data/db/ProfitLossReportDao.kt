package com.example.vascs.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.vascs.data.model.ProfitLossReportEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ProfitLossReportDao {
    @Query("SELECT * FROM profit_loss_reports ORDER BY reportId DESC")
    fun getAllProfitLossReports(): Flow<List<ProfitLossReportEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProfitLossReport(report: ProfitLossReportEntity): Long
}
