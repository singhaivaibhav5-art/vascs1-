package com.example.vascs.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.vascs.data.model.GlobalRiskEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface GlobalRiskDao {
    @Query("SELECT * FROM global_risk ORDER BY riskId DESC")
    fun getAllGlobalRisk(): Flow<List<GlobalRiskEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGlobalRisk(risk: GlobalRiskEntity): Long

    @Update
    suspend fun updateGlobalRisk(risk: GlobalRiskEntity)
}
