package com.example.vascs.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.vascs.data.model.ExpansionPlanEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ExpansionPlanDao {
    @Query("SELECT * FROM expansion_plans ORDER BY planId DESC")
    fun getAllExpansionPlans(): Flow<List<ExpansionPlanEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExpansionPlan(plan: ExpansionPlanEntity): Long

    @Update
    suspend fun updateExpansionPlan(plan: ExpansionPlanEntity)
}
