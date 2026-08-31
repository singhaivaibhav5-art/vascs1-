package com.example.vascs.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.vascs.data.model.*
import kotlinx.coroutines.flow.Flow

@Dao
interface UltimaCoreDao {
    @Query("SELECT * FROM ultima_core ORDER BY coreId DESC LIMIT 1")
    fun getLatestCore(): Flow<UltimaCoreEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCore(core: UltimaCoreEntity): Long

    @Query("DELETE FROM ultima_core")
    suspend fun clearAll()
}

@Dao
interface CommerceCivilizationDao {
    @Query("SELECT * FROM commerce_civilization ORDER BY civilizationId ASC")
    fun getAllCivilizations(): Flow<List<CommerceCivilizationEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(civilization: CommerceCivilizationEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(list: List<CommerceCivilizationEntity>)

    @Query("DELETE FROM commerce_civilization")
    suspend fun clearAll()
}

@Dao
interface UltimaWealthUniverseDao {
    @Query("SELECT * FROM ultima_wealth_universe ORDER BY generatedVolumeTrillionUsd DESC")
    fun getAllWealthStreams(): Flow<List<UltimaWealthUniverseEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(stream: UltimaWealthUniverseEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(list: List<UltimaWealthUniverseEntity>)

    @Query("DELETE FROM ultima_wealth_universe")
    suspend fun clearAll()
}

@Dao
interface FutureOpportunityDao {
    @Query("SELECT * FROM future_opportunities ORDER BY projectedValueTrillionUsd DESC")
    fun getAllOpportunities(): Flow<List<FutureOpportunityEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(opportunity: FutureOpportunityEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(list: List<FutureOpportunityEntity>)

    @Query("DELETE FROM future_opportunities")
    suspend fun clearAll()
}

@Dao
interface UltimaDemandUniverseDao {
    @Query("SELECT * FROM ultima_demand_universe ORDER BY forecastedVolumeMillionUnits DESC")
    fun getAllDemands(): Flow<List<UltimaDemandUniverseEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(demand: UltimaDemandUniverseEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(list: List<UltimaDemandUniverseEntity>)

    @Query("DELETE FROM ultima_demand_universe")
    suspend fun clearAll()
}

@Dao
interface UltimaCapitalAuthorityDao {
    @Query("SELECT * FROM ultima_capital_authority ORDER BY managedVolumeBillionUsd DESC")
    fun getAllCapitals(): Flow<List<UltimaCapitalAuthorityEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(capital: UltimaCapitalAuthorityEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(list: List<UltimaCapitalAuthorityEntity>)

    @Query("DELETE FROM ultima_capital_authority")
    suspend fun clearAll()
}

@Dao
interface TradeCivilizationDao {
    @Query("SELECT * FROM trade_civilization ORDER BY throughputBillionUsdPerMonth DESC")
    fun getAllTradeRoutes(): Flow<List<TradeCivilizationEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(route: TradeCivilizationEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(list: List<TradeCivilizationEntity>)

    @Query("DELETE FROM trade_civilization")
    suspend fun clearAll()
}

@Dao
interface UltimaRealityGridDao {
    @Query("SELECT * FROM ultima_reality_grid ORDER BY realityId ASC")
    fun getAllRealities(): Flow<List<UltimaRealityGridEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(reality: UltimaRealityGridEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(list: List<UltimaRealityGridEntity>)

    @Query("DELETE FROM ultima_reality_grid")
    suspend fun clearAll()
}

@Dao
interface UltimaDecisionAuthorityDao {
    @Query("SELECT * FROM ultima_decision_authority ORDER BY economicImpactTrillionUsd DESC")
    fun getAllDecisions(): Flow<List<UltimaDecisionAuthorityEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(decision: UltimaDecisionAuthorityEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(list: List<UltimaDecisionAuthorityEntity>)

    @Query("DELETE FROM ultima_decision_authority")
    suspend fun clearAll()
}

@Dao
interface KnowledgeCivilizationDao {
    @Query("SELECT * FROM knowledge_civilization ORDER BY knowledgeId ASC")
    fun getAllKnowledge(): Flow<List<KnowledgeCivilizationEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(knowledge: KnowledgeCivilizationEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(list: List<KnowledgeCivilizationEntity>)

    @Query("DELETE FROM knowledge_civilization")
    suspend fun clearAll()
}

@Dao
interface InnovationCivilizationDao {
    @Query("SELECT * FROM innovation_civilization ORDER BY commercialVelocityMultiplier DESC")
    fun getAllInnovations(): Flow<List<InnovationCivilizationEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(innovation: InnovationCivilizationEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(list: List<InnovationCivilizationEntity>)

    @Query("DELETE FROM innovation_civilization")
    suspend fun clearAll()
}

@Dao
interface ProtectionGridDao {
    @Query("SELECT * FROM protection_grid ORDER BY protectionId ASC")
    fun getAllProtections(): Flow<List<ProtectionGridEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(protection: ProtectionGridEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(list: List<ProtectionGridEntity>)

    @Query("DELETE FROM protection_grid")
    suspend fun clearAll()
}

@Dao
interface HealthCivilizationDao {
    @Query("SELECT * FROM health_civilization ORDER BY healthId ASC")
    fun getAllHealth(): Flow<List<HealthCivilizationEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(health: HealthCivilizationEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(list: List<HealthCivilizationEntity>)

    @Query("DELETE FROM health_civilization")
    suspend fun clearAll()
}

@Dao
interface UltimaTowerDao {
    @Query("SELECT * FROM ultima_tower ORDER BY towerId ASC")
    fun getAllTowers(): Flow<List<UltimaTowerEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(tower: UltimaTowerEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(list: List<UltimaTowerEntity>)

    @Query("DELETE FROM ultima_tower")
    suspend fun clearAll()
}

@Dao
interface UniversalHarmonyEngineDao {
    @Query("SELECT * FROM harmony_engine ORDER BY harmonyId ASC")
    fun getAllHarmony(): Flow<List<UniversalHarmonyEngineEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(harmony: UniversalHarmonyEngineEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(list: List<UniversalHarmonyEngineEntity>)

    @Query("DELETE FROM harmony_engine")
    suspend fun clearAll()
}
