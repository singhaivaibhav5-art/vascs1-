package com.example.vascs.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.vascs.data.model.ResearchReportEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ResearchReportDao {
    @Query("SELECT * FROM research_reports ORDER BY reportId DESC")
    fun getAllResearchReports(): Flow<List<ResearchReportEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertResearchReport(report: ResearchReportEntity): Long

    @Update
    suspend fun updateResearchReport(report: ResearchReportEntity)
}
