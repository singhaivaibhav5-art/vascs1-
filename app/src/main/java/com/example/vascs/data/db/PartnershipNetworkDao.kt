package com.example.vascs.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.vascs.data.model.PartnershipNetworkEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PartnershipNetworkDao {
    @Query("SELECT * FROM partnership_network ORDER BY partnershipId DESC")
    fun getAllPartnershipNetwork(): Flow<List<PartnershipNetworkEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPartnershipNetwork(item: PartnershipNetworkEntity): Long

    @Update
    suspend fun updatePartnershipNetwork(item: PartnershipNetworkEntity)
}
