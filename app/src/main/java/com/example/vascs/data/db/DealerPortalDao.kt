package com.example.vascs.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.vascs.data.model.DealerPortalEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DealerPortalDao {
    @Query("SELECT * FROM dealer_portals ORDER BY portalId DESC")
    fun getAllDealerPortals(): Flow<List<DealerPortalEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDealerPortal(portal: DealerPortalEntity): Long
}
