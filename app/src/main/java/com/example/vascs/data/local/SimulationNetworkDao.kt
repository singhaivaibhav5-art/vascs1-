package com.example.vascs.data.local

import androidx.room.*
import com.example.vascs.data.model.SimulationNetworkEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SimulationNetworkDao {
    @Query("SELECT * FROM simulation_network ORDER BY simulationId DESC")
    fun getAllSimulations(): Flow<List<SimulationNetworkEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSimulation(simulation: SimulationNetworkEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSimulations(simulations: List<SimulationNetworkEntity>)
}
