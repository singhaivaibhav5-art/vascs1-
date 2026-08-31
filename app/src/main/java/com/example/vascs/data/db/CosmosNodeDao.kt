package com.example.vascs.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.vascs.data.model.CosmosNodeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CosmosNodeDao {
    @Query("SELECT * FROM cosmos_nodes ORDER BY nodeId DESC")
    fun getAllNodes(): Flow<List<CosmosNodeEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNode(node: CosmosNodeEntity): Long

    @Update
    suspend fun updateNode(node: CosmosNodeEntity)
}
