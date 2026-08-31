package com.example.vascs.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.vascs.data.model.PlanetarySimulationEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PlanetarySimulationDao {
    @Query("SELECT * FROM planetary_simulations ORDER BY simId DESC")
    fun getAllSimulations(): Flow<List<PlanetarySimulationEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSimulation(sim: PlanetarySimulationEntity): Long

    @Update
    suspend fun updateSimulation(sim: PlanetarySimulationEntity)
}
