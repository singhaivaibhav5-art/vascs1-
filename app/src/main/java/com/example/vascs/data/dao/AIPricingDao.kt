package com.example.vascs.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.vascs.data.model.AIPricingHistoryEntity
import com.example.vascs.data.model.AIPricingRequestEntity
import com.example.vascs.data.model.AIPricingResultEntity
import com.example.vascs.data.model.AIPricingRuleEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AIPricingDao {

    // ================= Requests =================
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRequest(request: AIPricingRequestEntity): Long

    @Query("SELECT * FROM ai_pricing_requests ORDER BY createdAt DESC")
    fun getAllRequests(): Flow<List<AIPricingRequestEntity>>

    // ================= Results =================
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertResult(result: AIPricingResultEntity): Long

    @Update
    suspend fun updateResult(result: AIPricingResultEntity)

    @Query("SELECT * FROM ai_pricing_results ORDER BY createdAt DESC")
    fun getAllResults(): Flow<List<AIPricingResultEntity>>

    @Query("SELECT * FROM ai_pricing_results WHERE resultId = :id LIMIT 1")
    fun getResultById(id: Long): Flow<AIPricingResultEntity?>

    @Query("SELECT * FROM ai_pricing_results WHERE isFavorite = 1 ORDER BY createdAt DESC")
    fun getFavoriteResults(): Flow<List<AIPricingResultEntity>>

    @Query("DELETE FROM ai_pricing_results WHERE resultId = :id")
    suspend fun deleteResultById(id: Long)

    @Delete
    suspend fun deleteResult(result: AIPricingResultEntity)

    @Query("DELETE FROM ai_pricing_results")
    suspend fun clearAllResults()

    // ================= History =================
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHistory(history: AIPricingHistoryEntity): Long

    @Query("SELECT * FROM ai_pricing_history ORDER BY timestamp DESC")
    fun getAllHistory(): Flow<List<AIPricingHistoryEntity>>

    @Query("SELECT * FROM ai_pricing_history WHERE productName = :productName ORDER BY timestamp DESC")
    fun getHistoryForProduct(productName: String): Flow<List<AIPricingHistoryEntity>>

    @Query("DELETE FROM ai_pricing_history WHERE historyId = :id")
    suspend fun deleteHistoryById(id: Long)

    @Query("DELETE FROM ai_pricing_history")
    suspend fun clearAllHistory()

    // ================= Rules =================
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRule(rule: AIPricingRuleEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRules(rules: List<AIPricingRuleEntity>)

    @Update
    suspend fun updateRule(rule: AIPricingRuleEntity)

    @Query("SELECT * FROM ai_pricing_rules ORDER BY ruleId ASC")
    fun getAllRules(): Flow<List<AIPricingRuleEntity>>

    @Query("SELECT * FROM ai_pricing_rules WHERE isActive = 1 ORDER BY ruleId ASC")
    fun getActiveRules(): Flow<List<AIPricingRuleEntity>>

    @Query("SELECT * FROM ai_pricing_rules WHERE category = :category AND fabricType = :fabricType AND isActive = 1 LIMIT 1")
    suspend fun getRuleForCategoryAndFabric(category: String, fabricType: String): AIPricingRuleEntity?

    @Query("DELETE FROM ai_pricing_rules WHERE ruleId = :id")
    suspend fun deleteRuleById(id: Long)

    @Query("SELECT COUNT(*) FROM ai_pricing_rules")
    suspend fun getRuleCount(): Int
}
