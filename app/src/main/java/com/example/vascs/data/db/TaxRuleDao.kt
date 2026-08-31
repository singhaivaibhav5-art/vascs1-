package com.example.vascs.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.vascs.data.model.TaxRuleEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TaxRuleDao {
    @Query("SELECT * FROM tax_rules ORDER BY ruleId DESC")
    fun getAllTaxRules(): Flow<List<TaxRuleEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTaxRule(rule: TaxRuleEntity): Long
}
