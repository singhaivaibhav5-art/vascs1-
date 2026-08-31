package com.example.vascs.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.vascs.data.model.BalanceSheetReportEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BalanceSheetReportDao {
    @Query("SELECT * FROM balance_sheet_reports ORDER BY reportId DESC")
    fun getAllBalanceSheetReports(): Flow<List<BalanceSheetReportEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBalanceSheetReport(report: BalanceSheetReportEntity): Long
}
