package com.example.vascs.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.vascs.data.model.GstReportEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface GstReportDao {
    @Query("SELECT * FROM gst_reports ORDER BY reportId DESC")
    fun getAllGstReports(): Flow<List<GstReportEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGstReport(report: GstReportEntity): Long
}
