package com.example.vascs.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.vascs.data.model.*
import kotlinx.coroutines.flow.Flow

@Dao
interface SupremacyCoreDao {
    @Query("SELECT * FROM supremacy_core ORDER BY coreId DESC LIMIT 1")
    fun observeLatestCore(): Flow<SupremacyCoreEntity?>

    @Query("SELECT * FROM supremacy_core ORDER BY coreId DESC LIMIT 1")
    suspend fun getLatestCore(): SupremacyCoreEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(core: SupremacyCoreEntity): Long

    @Update
    suspend fun update(core: SupremacyCoreEntity)
}

@Dao
interface CivilizationGovernanceDao {
    @Query("SELECT * FROM civilization_governance ORDER BY governanceId DESC")
    fun observeAll(): Flow<List<CivilizationGovernanceEntity>>

    @Query("SELECT * FROM civilization_governance ORDER BY governanceId DESC")
    suspend fun getAll(): List<CivilizationGovernanceEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: CivilizationGovernanceEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<CivilizationGovernanceEntity>)

    @Update
    suspend fun update(item: CivilizationGovernanceEntity)
}

@Dao
interface EconomicCommandDao {
    @Query("SELECT * FROM economic_command ORDER BY commandId DESC")
    fun observeAll(): Flow<List<EconomicCommandEntity>>

    @Query("SELECT * FROM economic_command ORDER BY commandId DESC")
    suspend fun getAll(): List<EconomicCommandEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: EconomicCommandEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<EconomicCommandEntity>)

    @Update
    suspend fun update(item: EconomicCommandEntity)
}

@Dao
interface SupremeOpportunityDao {
    @Query("SELECT * FROM supreme_opportunity ORDER BY opportunityId DESC")
    fun observeAll(): Flow<List<SupremeOpportunityEntity>>

    @Query("SELECT * FROM supreme_opportunity ORDER BY opportunityId DESC")
    suspend fun getAll(): List<SupremeOpportunityEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: SupremeOpportunityEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<SupremeOpportunityEntity>)

    @Update
    suspend fun update(item: SupremeOpportunityEntity)
}

@Dao
interface ExpansionNetworkDao {
    @Query("SELECT * FROM expansion_network ORDER BY networkId DESC")
    fun observeAll(): Flow<List<ExpansionNetworkEntity>>

    @Query("SELECT * FROM expansion_network ORDER BY networkId DESC")
    suspend fun getAll(): List<ExpansionNetworkEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: ExpansionNetworkEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<ExpansionNetworkEntity>)

    @Update
    suspend fun update(item: ExpansionNetworkEntity)
}

@Dao
interface CapitalMatrixDao {
    @Query("SELECT * FROM capital_matrix ORDER BY matrixId DESC")
    fun observeAll(): Flow<List<CapitalMatrixEntity>>

    @Query("SELECT * FROM capital_matrix ORDER BY matrixId DESC")
    suspend fun getAll(): List<CapitalMatrixEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: CapitalMatrixEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<CapitalMatrixEntity>)

    @Update
    suspend fun update(item: CapitalMatrixEntity)
}

@Dao
interface TradeAuthorityDao {
    @Query("SELECT * FROM trade_authority ORDER BY tradeId DESC")
    fun observeAll(): Flow<List<TradeAuthorityEntity>>

    @Query("SELECT * FROM trade_authority ORDER BY tradeId DESC")
    suspend fun getAll(): List<TradeAuthorityEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: TradeAuthorityEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<TradeAuthorityEntity>)

    @Update
    suspend fun update(item: TradeAuthorityEntity)
}

@Dao
interface DigitalCivilizationDao {
    @Query("SELECT * FROM digital_civilization ORDER BY civilizationTwinId DESC")
    fun observeAll(): Flow<List<DigitalCivilizationEntity>>

    @Query("SELECT * FROM digital_civilization ORDER BY civilizationTwinId DESC")
    suspend fun getAll(): List<DigitalCivilizationEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: DigitalCivilizationEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<DigitalCivilizationEntity>)

    @Update
    suspend fun update(item: DigitalCivilizationEntity)
}

@Dao
interface DecisionAuthorityDao {
    @Query("SELECT * FROM decision_authority ORDER BY decisionId DESC")
    fun observeAll(): Flow<List<DecisionAuthorityEntity>>

    @Query("SELECT * FROM decision_authority ORDER BY decisionId DESC")
    suspend fun getAll(): List<DecisionAuthorityEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: DecisionAuthorityEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<DecisionAuthorityEntity>)

    @Update
    suspend fun update(item: DecisionAuthorityEntity)
}

@Dao
interface KnowledgeGridDao {
    @Query("SELECT * FROM knowledge_grid ORDER BY gridId DESC")
    fun observeAll(): Flow<List<KnowledgeGridEntity>>

    @Query("SELECT * FROM knowledge_grid ORDER BY gridId DESC")
    suspend fun getAll(): List<KnowledgeGridEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: KnowledgeGridEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<KnowledgeGridEntity>)

    @Update
    suspend fun update(item: KnowledgeGridEntity)
}

@Dao
interface InnovationAuthorityDao {
    @Query("SELECT * FROM innovation_authority ORDER BY innovationId DESC")
    fun observeAll(): Flow<List<InnovationAuthorityEntity>>

    @Query("SELECT * FROM innovation_authority ORDER BY innovationId DESC")
    suspend fun getAll(): List<InnovationAuthorityEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: InnovationAuthorityEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<InnovationAuthorityEntity>)

    @Update
    suspend fun update(item: InnovationAuthorityEntity)
}

@Dao
interface RiskShieldSupremacyDao {
    @Query("SELECT * FROM risk_shield_supremacy ORDER BY shieldId DESC")
    fun observeAll(): Flow<List<RiskShieldSupremacyEntity>>

    @Query("SELECT * FROM risk_shield_supremacy ORDER BY shieldId DESC")
    suspend fun getAll(): List<RiskShieldSupremacyEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: RiskShieldSupremacyEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<RiskShieldSupremacyEntity>)

    @Update
    suspend fun update(item: RiskShieldSupremacyEntity)
}

@Dao
interface HealthAuthorityDao {
    @Query("SELECT * FROM health_authority ORDER BY healthId DESC")
    fun observeAll(): Flow<List<HealthAuthorityEntity>>

    @Query("SELECT * FROM health_authority ORDER BY healthId DESC")
    suspend fun getAll(): List<HealthAuthorityEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: HealthAuthorityEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<HealthAuthorityEntity>)

    @Update
    suspend fun update(item: HealthAuthorityEntity)
}

@Dao
interface SupremacyCommandTowerDao {
    @Query("SELECT * FROM supremacy_command_tower ORDER BY towerId DESC")
    fun observeAll(): Flow<List<SupremacyCommandTowerEntity>>

    @Query("SELECT * FROM supremacy_command_tower ORDER BY towerId DESC")
    suspend fun getAll(): List<SupremacyCommandTowerEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: SupremacyCommandTowerEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<SupremacyCommandTowerEntity>)

    @Update
    suspend fun update(item: SupremacyCommandTowerEntity)
}

@Dao
interface SovereigntyEngineDao {
    @Query("SELECT * FROM sovereignty_engine ORDER BY sovereigntyId DESC")
    fun observeAll(): Flow<List<SovereigntyEngineEntity>>

    @Query("SELECT * FROM sovereignty_engine ORDER BY sovereigntyId DESC")
    suspend fun getAll(): List<SovereigntyEngineEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: SovereigntyEngineEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<SovereigntyEngineEntity>)

    @Update
    suspend fun update(item: SovereigntyEngineEntity)
}
