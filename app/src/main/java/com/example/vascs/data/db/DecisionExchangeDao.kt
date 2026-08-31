package com.example.vascs.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.vascs.data.model.DecisionExchangeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DecisionExchangeDao {
    @Query("SELECT * FROM decision_exchange ORDER BY decisionId DESC")
    fun getAllDecisionExchange(): Flow<List<DecisionExchangeEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDecisionExchange(decision: DecisionExchangeEntity): Long

    @Update
    suspend fun updateDecisionExchange(decision: DecisionExchangeEntity)
}
