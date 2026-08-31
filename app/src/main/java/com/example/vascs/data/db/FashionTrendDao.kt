package com.example.vascs.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.vascs.data.model.FashionTrendEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FashionTrendDao {
    @Query("SELECT * FROM fashion_trends ORDER BY trendId DESC")
    fun getAllFashionTrends(): Flow<List<FashionTrendEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFashionTrend(trend: FashionTrendEntity): Long

    @Update
    suspend fun updateFashionTrend(trend: FashionTrendEntity)
}
