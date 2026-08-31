package com.example.vascs.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.vascs.data.model.*
import kotlinx.coroutines.flow.Flow

@Dao
interface SingularityPrimeCoreDao {
    @Query("SELECT * FROM singularity_prime_core ORDER BY coreId DESC LIMIT 1")
    fun observeLatestCore(): Flow<SingularityPrimeCoreEntity?>

    @Query("SELECT * FROM singularity_prime_core ORDER BY coreId DESC LIMIT 1")
    suspend fun getLatestCore(): SingularityPrimeCoreEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(core: SingularityPrimeCoreEntity): Long

    @Update
    suspend fun update(core: SingularityPrimeCoreEntity)
}

@Dao
interface CivilizationEngineDao {
    @Query("SELECT * FROM civilization_engine ORDER BY engineId DESC")
    fun observeAll(): Flow<List<CivilizationEngineEntity>>

    @Query("SELECT * FROM civilization_engine ORDER BY engineId DESC")
    suspend fun getAll(): List<CivilizationEngineEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: CivilizationEngineEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<CivilizationEngineEntity>)

    @Update
    suspend fun update(item: CivilizationEngineEntity)
}

@Dao
interface WealthGeneratorDao {
    @Query("SELECT * FROM wealth_generator ORDER BY wealthId DESC")
    fun observeAll(): Flow<List<WealthGeneratorEntity>>

    @Query("SELECT * FROM wealth_generator ORDER BY wealthId DESC")
    suspend fun getAll(): List<WealthGeneratorEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: WealthGeneratorEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<WealthGeneratorEntity>)

    @Update
    suspend fun update(item: WealthGeneratorEntity)
}

@Dao
interface OpportunityCreatorDao {
    @Query("SELECT * FROM opportunity_creator ORDER BY opportunityId DESC")
    fun observeAll(): Flow<List<OpportunityCreatorEntity>>

    @Query("SELECT * FROM opportunity_creator ORDER BY opportunityId DESC")
    suspend fun getAll(): List<OpportunityCreatorEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: OpportunityCreatorEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<OpportunityCreatorEntity>)

    @Update
    suspend fun update(item: OpportunityCreatorEntity)
}

@Dao
interface DemandCosmosDao {
    @Query("SELECT * FROM demand_cosmos ORDER BY demandId DESC")
    fun observeAll(): Flow<List<DemandCosmosEntity>>

    @Query("SELECT * FROM demand_cosmos ORDER BY demandId DESC")
    suspend fun getAll(): List<DemandCosmosEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: DemandCosmosEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<DemandCosmosEntity>)

    @Update
    suspend fun update(item: DemandCosmosEntity)
}

@Dao
interface CapitalAuthorityDao {
    @Query("SELECT * FROM capital_authority ORDER BY capitalId DESC")
    fun observeAll(): Flow<List<CapitalAuthorityEntity>>

    @Query("SELECT * FROM capital_authority ORDER BY capitalId DESC")
    suspend fun getAll(): List<CapitalAuthorityEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: CapitalAuthorityEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<CapitalAuthorityEntity>)

    @Update
    suspend fun update(item: CapitalAuthorityEntity)
}

@Dao
interface TradeSupremacyDao {
    @Query("SELECT * FROM trade_supremacy ORDER BY tradeId DESC")
    fun observeAll(): Flow<List<TradeSupremacyEntity>>

    @Query("SELECT * FROM trade_supremacy ORDER BY tradeId DESC")
    suspend fun getAll(): List<TradeSupremacyEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: TradeSupremacyEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<TradeSupremacyEntity>)

    @Update
    suspend fun update(item: TradeSupremacyEntity)
}

@Dao
interface RealityEngineDao {
    @Query("SELECT * FROM reality_engine ORDER BY realityId DESC")
    fun observeAll(): Flow<List<RealityEngineEntity>>

    @Query("SELECT * FROM reality_engine ORDER BY realityId DESC")
    suspend fun getAll(): List<RealityEngineEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: RealityEngineEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<RealityEngineEntity>)

    @Update
    suspend fun update(item: RealityEngineEntity)
}

@Dao
interface DecisionPrimeDao {
    @Query("SELECT * FROM decision_prime ORDER BY decisionId DESC")
    fun observeAll(): Flow<List<DecisionPrimeEntity>>

    @Query("SELECT * FROM decision_prime ORDER BY decisionId DESC")
    suspend fun getAll(): List<DecisionPrimeEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: DecisionPrimeEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<DecisionPrimeEntity>)

    @Update
    suspend fun update(item: DecisionPrimeEntity)
}

@Dao
interface KnowledgePrimeDao {
    @Query("SELECT * FROM knowledge_prime ORDER BY knowledgeId DESC")
    fun observeAll(): Flow<List<KnowledgePrimeEntity>>

    @Query("SELECT * FROM knowledge_prime ORDER BY knowledgeId DESC")
    suspend fun getAll(): List<KnowledgePrimeEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: KnowledgePrimeEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<KnowledgePrimeEntity>)

    @Update
    suspend fun update(item: KnowledgePrimeEntity)
}

@Dao
interface InnovationFactoryDao {
    @Query("SELECT * FROM innovation_factory ORDER BY factoryId DESC")
    fun observeAll(): Flow<List<InnovationFactoryEntity>>

    @Query("SELECT * FROM innovation_factory ORDER BY factoryId DESC")
    suspend fun getAll(): List<InnovationFactoryEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: InnovationFactoryEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<InnovationFactoryEntity>)

    @Update
    suspend fun update(item: InnovationFactoryEntity)
}

@Dao
interface RiskShieldPrimeDao {
    @Query("SELECT * FROM risk_shield_prime ORDER BY shieldId DESC")
    fun observeAll(): Flow<List<RiskShieldPrimeEntity>>

    @Query("SELECT * FROM risk_shield_prime ORDER BY shieldId DESC")
    suspend fun getAll(): List<RiskShieldPrimeEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: RiskShieldPrimeEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<RiskShieldPrimeEntity>)

    @Update
    suspend fun update(item: RiskShieldPrimeEntity)
}

@Dao
interface HealthPrimeDao {
    @Query("SELECT * FROM health_prime ORDER BY healthId DESC")
    fun observeAll(): Flow<List<HealthPrimeEntity>>

    @Query("SELECT * FROM health_prime ORDER BY healthId DESC")
    suspend fun getAll(): List<HealthPrimeEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: HealthPrimeEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<HealthPrimeEntity>)

    @Update
    suspend fun update(item: HealthPrimeEntity)
}

@Dao
interface PrimeCommandTowerDao {
    @Query("SELECT * FROM prime_command_tower ORDER BY towerId DESC")
    fun observeAll(): Flow<List<PrimeCommandTowerEntity>>

    @Query("SELECT * FROM prime_command_tower ORDER BY towerId DESC")
    suspend fun getAll(): List<PrimeCommandTowerEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: PrimeCommandTowerEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<PrimeCommandTowerEntity>)

    @Update
    suspend fun update(item: PrimeCommandTowerEntity)
}

@Dao
interface EvolutionAuthorityDao {
    @Query("SELECT * FROM evolution_authority ORDER BY evolutionId DESC")
    fun observeAll(): Flow<List<EvolutionAuthorityEntity>>

    @Query("SELECT * FROM evolution_authority ORDER BY evolutionId DESC")
    suspend fun getAll(): List<EvolutionAuthorityEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: EvolutionAuthorityEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<EvolutionAuthorityEntity>)

    @Update
    suspend fun update(item: EvolutionAuthorityEntity)
}
