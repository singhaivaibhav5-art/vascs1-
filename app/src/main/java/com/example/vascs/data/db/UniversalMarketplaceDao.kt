package com.example.vascs.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.vascs.data.model.UniversalMarketplaceEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UniversalMarketplaceDao {
    @Query("SELECT * FROM universal_marketplace ORDER BY itemId DESC")
    fun getAllUniversalMarketplace(): Flow<List<UniversalMarketplaceEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUniversalMarketplace(item: UniversalMarketplaceEntity): Long

    @Update
    suspend fun updateUniversalMarketplace(item: UniversalMarketplaceEntity)
}
