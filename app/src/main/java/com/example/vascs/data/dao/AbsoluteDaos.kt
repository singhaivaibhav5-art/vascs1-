package com.example.vascs.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.vascs.data.model.*
import kotlinx.coroutines.flow.Flow

@Dao
interface AbsoluteCoreDao {
    @Query("SELECT * FROM absolute_core ORDER BY coreId DESC LIMIT 1")
    fun observeLatestCore(): Flow<AbsoluteCoreEntity?>

    @Query("SELECT * FROM absolute_core ORDER BY coreId DESC LIMIT 1")
    suspend fun getLatestCore(): AbsoluteCoreEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(core: AbsoluteCoreEntity): Long

    @Update
    suspend fun update(core: AbsoluteCoreEntity)
}

@Dao
interface EconomicOSDao {
    @Query("SELECT * FROM economic_os ORDER BY osId DESC")
    fun observeAll(): Flow<List<EconomicOSEntity>>

    @Query("SELECT * FROM economic_os ORDER BY osId DESC")
    suspend fun getAll(): List<EconomicOSEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: EconomicOSEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<EconomicOSEntity>)

    @Update
    suspend fun update(item: EconomicOSEntity)
}

@Dao
interface WealthMatrixDao {
    @Query("SELECT * FROM wealth_matrix ORDER BY matrixId DESC")
    fun observeAll(): Flow<List<WealthMatrixEntity>>

    @Query("SELECT * FROM wealth_matrix ORDER BY matrixId DESC")
    suspend fun getAll(): List<WealthMatrixEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: WealthMatrixEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<WealthMatrixEntity>)

    @Update
    suspend fun update(item: WealthMatrixEntity)
}

@Dao
interface OpportunityGridDao {
    @Query("SELECT * FROM opportunity_grid ORDER BY gridId DESC")
    fun observeAll(): Flow<List<OpportunityGridEntity>>

    @Query("SELECT * FROM opportunity_grid ORDER BY gridId DESC")
    suspend fun getAll(): List<OpportunityGridEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: OpportunityGridEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<OpportunityGridEntity>)

    @Update
    suspend fun update(item: OpportunityGridEntity)
}

@Dao
interface DemandMatrixDao {
    @Query("SELECT * FROM demand_matrix ORDER BY demandId DESC")
    fun observeAll(): Flow<List<DemandMatrixEntity>>

    @Query("SELECT * FROM demand_matrix ORDER BY demandId DESC")
    suspend fun getAll(): List<DemandMatrixEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: DemandMatrixEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<DemandMatrixEntity>)

    @Update
    suspend fun update(item: DemandMatrixEntity)
}

@Dao
interface CapitalSupremacyDao {
    @Query("SELECT * FROM capital_supremacy ORDER BY capitalId DESC")
    fun observeAll(): Flow<List<CapitalSupremacyEntity>>

    @Query("SELECT * FROM capital_supremacy ORDER BY capitalId DESC")
    suspend fun getAll(): List<CapitalSupremacyEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: CapitalSupremacyEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<CapitalSupremacyEntity>)

    @Update
    suspend fun update(item: CapitalSupremacyEntity)
}

@Dao
interface TradeNetworkDao {
    @Query("SELECT * FROM trade_network ORDER BY tradeId DESC")
    fun observeAll(): Flow<List<TradeNetworkEntity>>

    @Query("SELECT * FROM trade_network ORDER BY tradeId DESC")
    suspend fun getAll(): List<TradeNetworkEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: TradeNetworkEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<TradeNetworkEntity>)

    @Update
    suspend fun update(item: TradeNetworkEntity)
}

@Dao
interface RealityMatrixDao {
    @Query("SELECT * FROM reality_matrix ORDER BY realityId DESC")
    fun observeAll(): Flow<List<RealityMatrixEntity>>

    @Query("SELECT * FROM reality_matrix ORDER BY realityId DESC")
    suspend fun getAll(): List<RealityMatrixEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: RealityMatrixEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<RealityMatrixEntity>)

    @Update
    suspend fun update(item: RealityMatrixEntity)
}

@Dao
interface DecisionEngineDao {
    @Query("SELECT * FROM decision_engine ORDER BY decisionId DESC")
    fun observeAll(): Flow<List<DecisionEngineEntity>>

    @Query("SELECT * FROM decision_engine ORDER BY decisionId DESC")
    suspend fun getAll(): List<DecisionEngineEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: DecisionEngineEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<DecisionEngineEntity>)

    @Update
    suspend fun update(item: DecisionEngineEntity)
}

@Dao
interface KnowledgeMatrixDao {
    @Query("SELECT * FROM knowledge_matrix ORDER BY knowledgeId DESC")
    fun observeAll(): Flow<List<KnowledgeMatrixEntity>>

    @Query("SELECT * FROM knowledge_matrix ORDER BY knowledgeId DESC")
    suspend fun getAll(): List<KnowledgeMatrixEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: KnowledgeMatrixEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<KnowledgeMatrixEntity>)

    @Update
    suspend fun update(item: KnowledgeMatrixEntity)
}

@Dao
interface InnovationEngineDao {
    @Query("SELECT * FROM innovation_engine ORDER BY innovationId DESC")
    fun observeAll(): Flow<List<InnovationEngineEntity>>

    @Query("SELECT * FROM innovation_engine ORDER BY innovationId DESC")
    suspend fun getAll(): List<InnovationEngineEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: InnovationEngineEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<InnovationEngineEntity>)

    @Update
    suspend fun update(item: InnovationEngineEntity)
}

@Dao
interface ProtectionSystemDao {
    @Query("SELECT * FROM protection_system ORDER BY protectionId DESC")
    fun observeAll(): Flow<List<ProtectionSystemEntity>>

    @Query("SELECT * FROM protection_system ORDER BY protectionId DESC")
    suspend fun getAll(): List<ProtectionSystemEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: ProtectionSystemEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<ProtectionSystemEntity>)

    @Update
    suspend fun update(item: ProtectionSystemEntity)
}

@Dao
interface HealthEngineDao {
    @Query("SELECT * FROM health_engine ORDER BY healthId DESC")
    fun observeAll(): Flow<List<AbsoluteHealthEngineEntity>>

    @Query("SELECT * FROM health_engine ORDER BY healthId DESC")
    suspend fun getAll(): List<AbsoluteHealthEngineEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: AbsoluteHealthEngineEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<AbsoluteHealthEngineEntity>)

    @Update
    suspend fun update(item: AbsoluteHealthEngineEntity)
}

@Dao
interface AbsoluteCommandTowerDao {
    @Query("SELECT * FROM absolute_tower ORDER BY towerId DESC")
    fun observeAll(): Flow<List<AbsoluteCommandTowerEntity>>

    @Query("SELECT * FROM absolute_tower ORDER BY towerId DESC")
    suspend fun getAll(): List<AbsoluteCommandTowerEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: AbsoluteCommandTowerEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<AbsoluteCommandTowerEntity>)

    @Update
    suspend fun update(item: AbsoluteCommandTowerEntity)
}

@Dao
interface UnityEngineDao {
    @Query("SELECT * FROM unity_engine ORDER BY unityId DESC")
    fun observeAll(): Flow<List<UnityEngineEntity>>

    @Query("SELECT * FROM unity_engine ORDER BY unityId DESC")
    suspend fun getAll(): List<UnityEngineEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: UnityEngineEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<UnityEngineEntity>)

    @Update
    suspend fun update(item: UnityEngineEntity)
}
