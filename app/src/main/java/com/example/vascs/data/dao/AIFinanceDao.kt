package com.example.vascs.data.dao

import androidx.room.*
import com.example.vascs.data.model.AICashFlowForecastEntity
import com.example.vascs.data.model.AIFinanceRecommendationEntity
import com.example.vascs.data.model.AIFinanceReportEntity
import com.example.vascs.data.model.AIFinanceRequestEntity
import com.example.vascs.data.model.AIFinancialHealthEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AIFinanceDao {

    // Requests
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRequest(request: AIFinanceRequestEntity): Long

    @Query("SELECT * FROM ai_finance_requests ORDER BY timestamp DESC")
    fun getAllRequests(): Flow<List<AIFinanceRequestEntity>>

    // Finance Reports
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReport(report: AIFinanceReportEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReports(reports: List<AIFinanceReportEntity>)

    @Update
    suspend fun updateReport(report: AIFinanceReportEntity)

    @Query("SELECT * FROM ai_finance_reports ORDER BY timestamp DESC")
    fun getAllReports(): Flow<List<AIFinanceReportEntity>>

    @Query("SELECT * FROM ai_finance_reports ORDER BY timestamp DESC LIMIT 1")
    fun getLatestReport(): Flow<AIFinanceReportEntity?>

    @Query("SELECT * FROM ai_finance_reports WHERE isFavorite = 1 ORDER BY timestamp DESC")
    fun getFavoriteReports(): Flow<List<AIFinanceReportEntity>>

    @Query("DELETE FROM ai_finance_reports WHERE reportId = :id")
    suspend fun deleteReport(id: Long)

    @Query("DELETE FROM ai_finance_reports")
    suspend fun clearReports()

    // Cash Flow Forecasts
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCashFlowForecast(forecast: AICashFlowForecastEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCashFlowForecasts(forecasts: List<AICashFlowForecastEntity>)

    @Query("SELECT * FROM ai_cashflow_forecasts ORDER BY timestamp DESC")
    fun getAllCashFlowForecasts(): Flow<List<AICashFlowForecastEntity>>

    @Query("SELECT * FROM ai_cashflow_forecasts WHERE reportId = :reportId ORDER BY forecastPeriod ASC")
    fun getForecastsByReport(reportId: Long): Flow<List<AICashFlowForecastEntity>>

    @Query("DELETE FROM ai_cashflow_forecasts")
    suspend fun clearCashFlowForecasts()

    // Financial Health
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHealth(health: AIFinancialHealthEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHealthList(healthList: List<AIFinancialHealthEntity>)

    @Query("SELECT * FROM ai_financial_health ORDER BY timestamp DESC LIMIT 1")
    fun getLatestHealth(): Flow<AIFinancialHealthEntity?>

    @Query("SELECT * FROM ai_financial_health ORDER BY timestamp DESC")
    fun getAllHealthRecords(): Flow<List<AIFinancialHealthEntity>>

    @Query("DELETE FROM ai_financial_health")
    suspend fun clearHealthRecords()

    // Recommendations
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecommendation(recommendation: AIFinanceRecommendationEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecommendations(recommendations: List<AIFinanceRecommendationEntity>)

    @Query("SELECT * FROM ai_finance_recommendations ORDER BY CASE priority WHEN 'CRITICAL' THEN 1 WHEN 'HIGH' THEN 2 WHEN 'MEDIUM' THEN 3 ELSE 4 END, timestamp DESC")
    fun getAllRecommendations(): Flow<List<AIFinanceRecommendationEntity>>

    @Query("SELECT * FROM ai_finance_recommendations WHERE isApplied = 0 ORDER BY timestamp DESC")
    fun getPendingRecommendations(): Flow<List<AIFinanceRecommendationEntity>>

    @Query("UPDATE ai_finance_recommendations SET isApplied = 1 WHERE recommendationId = :id")
    suspend fun applyRecommendation(id: Long)

    @Query("DELETE FROM ai_finance_recommendations WHERE recommendationId = :id")
    suspend fun deleteRecommendation(id: Long)

    @Query("DELETE FROM ai_finance_recommendations")
    suspend fun clearRecommendations()
}
