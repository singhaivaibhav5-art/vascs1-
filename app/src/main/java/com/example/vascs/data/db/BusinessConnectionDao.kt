package com.example.vascs.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.vascs.data.model.BusinessConnectionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BusinessConnectionDao {
    @Query("SELECT * FROM business_connections ORDER BY connectionId DESC")
    fun getAllBusinessConnections(): Flow<List<BusinessConnectionEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBusinessConnection(connection: BusinessConnectionEntity): Long

    @Update
    suspend fun updateBusinessConnection(connection: BusinessConnectionEntity)
}
