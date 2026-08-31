package com.example.vascs.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.vascs.data.model.BusinessTwinModelEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BusinessTwinModelDao {
    @Query("SELECT * FROM business_twin_models ORDER BY twinId DESC")
    fun getAllBusinessTwinModels(): Flow<List<BusinessTwinModelEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBusinessTwinModel(model: BusinessTwinModelEntity): Long
}
