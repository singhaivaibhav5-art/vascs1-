package com.example.vascs.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.vascs.data.model.EnterpriseNetworkEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface EnterpriseNetworkDao {
    @Query("SELECT * FROM enterprise_network ORDER BY networkId DESC")
    fun getAllEnterpriseNetwork(): Flow<List<EnterpriseNetworkEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEnterpriseNetwork(network: EnterpriseNetworkEntity): Long

    @Update
    suspend fun updateEnterpriseNetwork(network: EnterpriseNetworkEntity)
}
