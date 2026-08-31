package com.example.vascs.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.vascs.data.model.CustomerPortalEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CustomerPortalDao {
    @Query("SELECT * FROM customer_portals ORDER BY portalId DESC")
    fun getAllCustomerPortals(): Flow<List<CustomerPortalEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCustomerPortal(portal: CustomerPortalEntity): Long
}
