package com.example.vascs.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.vascs.data.model.AiAutomationRuleEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AiAutomationRuleDao {
    @Query("SELECT * FROM ai_automation_rules ORDER BY ruleId DESC")
    fun getAllAutomationRules(): Flow<List<AiAutomationRuleEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAutomationRule(rule: AiAutomationRuleEntity): Long

    @Update
    suspend fun updateAutomationRule(rule: AiAutomationRuleEntity)
}
