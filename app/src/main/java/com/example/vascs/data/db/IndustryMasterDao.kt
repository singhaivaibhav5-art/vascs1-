package com.example.vascs.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.vascs.data.model.IndustryMasterEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface IndustryMasterDao {
    @Query("SELECT * FROM industry_master ORDER BY industryId DESC")
    fun getAllIndustries(): Flow<List<IndustryMasterEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertIndustry(industry: IndustryMasterEntity): Long

    @Update
    suspend fun updateIndustry(industry: IndustryMasterEntity)
}
