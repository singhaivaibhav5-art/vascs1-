package com.example.vascs.data.dao

import androidx.room.*
import com.example.vascs.data.model.*
import kotlinx.coroutines.flow.Flow

@Dao
interface AscensionCoreDao {
    @Query("SELECT * FROM ascension_core ORDER BY coreId DESC")
    fun getAllAscensionCore(): Flow<List<AscensionCoreEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCore(core: AscensionCoreEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCores(cores: List<AscensionCoreEntity>)

    @Query("DELETE FROM ascension_core")
    suspend fun clearCores()
}

@Dao
interface EconomicCivilizationDao {
    @Query("SELECT * FROM economic_civilization ORDER BY civilizationId DESC")
    fun getAllCivilizations(): Flow<List<EconomicCivilizationEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCivilization(civilization: EconomicCivilizationEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCivilizations(civilizations: List<EconomicCivilizationEntity>)

    @Update
    suspend fun updateCivilization(civilization: EconomicCivilizationEntity)

    @Query("DELETE FROM economic_civilization")
    suspend fun clearCivilizations()
}

@Dao
interface ResourceIntelligenceDao {
    @Query("SELECT * FROM resource_intelligence ORDER BY resourceId DESC")
    fun getAllResources(): Flow<List<ResourceIntelligenceEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertResource(resource: ResourceIntelligenceEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertResources(resources: List<ResourceIntelligenceEntity>)

    @Update
    suspend fun updateResource(resource: ResourceIntelligenceEntity)

    @Query("DELETE FROM resource_intelligence")
    suspend fun clearResources()
}

@Dao
interface TradeUniverseDao {
    @Query("SELECT * FROM trade_universe ORDER BY routeId DESC")
    fun getAllTradeRoutes(): Flow<List<TradeUniverseEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTradeRoute(route: TradeUniverseEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTradeRoutes(routes: List<TradeUniverseEntity>)

    @Update
    suspend fun updateTradeRoute(route: TradeUniverseEntity)

    @Query("DELETE FROM trade_universe")
    suspend fun clearTradeRoutes()
}

@Dao
interface ProsperityEngineDao {
    @Query("SELECT * FROM prosperity_engine ORDER BY prosperityId DESC")
    fun getAllProsperity(): Flow<List<ProsperityEngineEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProsperity(prosperity: ProsperityEngineEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProsperities(prosperities: List<ProsperityEngineEntity>)

    @Query("DELETE FROM prosperity_engine")
    suspend fun clearProsperities()
}

@Dao
interface InnovationUniverseDao {
    @Query("SELECT * FROM innovation_universe ORDER BY innovationId DESC")
    fun getAllInnovations(): Flow<List<InnovationUniverseEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertInnovation(innovation: InnovationUniverseEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertInnovations(innovations: List<InnovationUniverseEntity>)

    @Query("DELETE FROM innovation_universe")
    suspend fun clearInnovations()
}

@Dao
interface DecisionUniverseDao {
    @Query("SELECT * FROM decision_universe ORDER BY decisionId DESC")
    fun getAllDecisions(): Flow<List<DecisionUniverseEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDecision(decision: DecisionUniverseEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDecisions(decisions: List<DecisionUniverseEntity>)

    @Update
    suspend fun updateDecision(decision: DecisionUniverseEntity)

    @Query("DELETE FROM decision_universe")
    suspend fun clearDecisions()
}

@Dao
interface AscensionHealthDao {
    @Query("SELECT * FROM ascension_health ORDER BY healthId ASC")
    fun getAllHealth(): Flow<List<AscensionHealthEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHealth(health: AscensionHealthEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHealthList(healthList: List<AscensionHealthEntity>)

    @Query("DELETE FROM ascension_health")
    suspend fun clearHealth()
}
