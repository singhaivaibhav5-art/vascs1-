package com.example.vascs.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.vascs.data.model.CapitalCivilizationEntity
import com.example.vascs.data.model.DecisionCosmosEntity
import com.example.vascs.data.model.DemandNetworkEntity
import com.example.vascs.data.model.EnterpriseCreatorEntity
import com.example.vascs.data.model.KnowledgeOceanEntity
import com.example.vascs.data.model.RealityCommerceEntity
import com.example.vascs.data.model.TranscendenceCoreEntity
import com.example.vascs.data.model.TranscendenceEvolutionEntity
import com.example.vascs.data.model.TranscendenceExpansionEntity
import com.example.vascs.data.model.TranscendenceHealthEntity
import com.example.vascs.data.model.TranscendenceInnovationEntity
import com.example.vascs.data.model.TranscendenceOpportunityEntity
import com.example.vascs.data.model.TranscendenceRealityTwinEntity
import com.example.vascs.data.model.TranscendenceRiskEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TranscendenceCoreDao {
    @Query("SELECT * FROM transcendence_core ORDER BY coreId DESC LIMIT 1")
    fun observeLatestCore(): Flow<TranscendenceCoreEntity?>

    @Query("SELECT * FROM transcendence_core ORDER BY coreId DESC LIMIT 1")
    suspend fun getLatestCore(): TranscendenceCoreEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(core: TranscendenceCoreEntity): Long

    @Update
    suspend fun update(core: TranscendenceCoreEntity)
}

@Dao
interface RealityCommerceDao {
    @Query("SELECT * FROM reality_commerce ORDER BY commerceId DESC")
    fun observeAll(): Flow<List<RealityCommerceEntity>>

    @Query("SELECT * FROM reality_commerce ORDER BY commerceId DESC")
    suspend fun getAll(): List<RealityCommerceEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: RealityCommerceEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<RealityCommerceEntity>)

    @Update
    suspend fun update(item: RealityCommerceEntity)
}

@Dao
interface EnterpriseCreatorDao {
    @Query("SELECT * FROM enterprise_creator ORDER BY creatorId DESC")
    fun observeAll(): Flow<List<EnterpriseCreatorEntity>>

    @Query("SELECT * FROM enterprise_creator ORDER BY creatorId DESC")
    suspend fun getAll(): List<EnterpriseCreatorEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: EnterpriseCreatorEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<EnterpriseCreatorEntity>)

    @Update
    suspend fun update(item: EnterpriseCreatorEntity)
}

@Dao
interface TranscendenceOpportunityDao {
    @Query("SELECT * FROM transcendence_opportunity ORDER BY opportunityId DESC")
    fun observeAll(): Flow<List<TranscendenceOpportunityEntity>>

    @Query("SELECT * FROM transcendence_opportunity ORDER BY opportunityId DESC")
    suspend fun getAll(): List<TranscendenceOpportunityEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: TranscendenceOpportunityEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<TranscendenceOpportunityEntity>)

    @Update
    suspend fun update(item: TranscendenceOpportunityEntity)
}

@Dao
interface DemandNetworkDao {
    @Query("SELECT * FROM demand_network ORDER BY networkId DESC")
    fun observeAll(): Flow<List<DemandNetworkEntity>>

    @Query("SELECT * FROM demand_network ORDER BY networkId DESC")
    suspend fun getAll(): List<DemandNetworkEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: DemandNetworkEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<DemandNetworkEntity>)

    @Update
    suspend fun update(item: DemandNetworkEntity)
}

@Dao
interface CapitalCivilizationDao {
    @Query("SELECT * FROM capital_civilization ORDER BY civilizationId DESC")
    fun observeAll(): Flow<List<CapitalCivilizationEntity>>

    @Query("SELECT * FROM capital_civilization ORDER BY civilizationId DESC")
    suspend fun getAll(): List<CapitalCivilizationEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: CapitalCivilizationEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<CapitalCivilizationEntity>)

    @Update
    suspend fun update(item: CapitalCivilizationEntity)
}

@Dao
interface DecisionCosmosDao {
    @Query("SELECT * FROM decision_cosmos ORDER BY decisionId DESC")
    fun observeAll(): Flow<List<DecisionCosmosEntity>>

    @Query("SELECT * FROM decision_cosmos ORDER BY decisionId DESC")
    suspend fun getAll(): List<DecisionCosmosEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: DecisionCosmosEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<DecisionCosmosEntity>)

    @Update
    suspend fun update(item: DecisionCosmosEntity)
}

@Dao
interface KnowledgeOceanDao {
    @Query("SELECT * FROM knowledge_ocean ORDER BY oceanId DESC")
    fun observeAll(): Flow<List<KnowledgeOceanEntity>>

    @Query("SELECT * FROM knowledge_ocean ORDER BY oceanId DESC")
    suspend fun getAll(): List<KnowledgeOceanEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: KnowledgeOceanEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<KnowledgeOceanEntity>)

    @Update
    suspend fun update(item: KnowledgeOceanEntity)
}

@Dao
interface TranscendenceEvolutionDao {
    @Query("SELECT * FROM transcendence_evolution ORDER BY evolutionId DESC")
    fun observeAll(): Flow<List<TranscendenceEvolutionEntity>>

    @Query("SELECT * FROM transcendence_evolution ORDER BY evolutionId DESC")
    suspend fun getAll(): List<TranscendenceEvolutionEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: TranscendenceEvolutionEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<TranscendenceEvolutionEntity>)

    @Update
    suspend fun update(item: TranscendenceEvolutionEntity)
}

@Dao
interface TranscendenceRealityTwinDao {
    @Query("SELECT * FROM transcendence_reality_twin ORDER BY twinId DESC")
    fun observeAll(): Flow<List<TranscendenceRealityTwinEntity>>

    @Query("SELECT * FROM transcendence_reality_twin ORDER BY twinId DESC")
    suspend fun getAll(): List<TranscendenceRealityTwinEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: TranscendenceRealityTwinEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<TranscendenceRealityTwinEntity>)

    @Update
    suspend fun update(item: TranscendenceRealityTwinEntity)
}

@Dao
interface TranscendenceInnovationDao {
    @Query("SELECT * FROM transcendence_innovation ORDER BY innovationId DESC")
    fun observeAll(): Flow<List<TranscendenceInnovationEntity>>

    @Query("SELECT * FROM transcendence_innovation ORDER BY innovationId DESC")
    suspend fun getAll(): List<TranscendenceInnovationEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: TranscendenceInnovationEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<TranscendenceInnovationEntity>)

    @Update
    suspend fun update(item: TranscendenceInnovationEntity)
}

@Dao
interface TranscendenceRiskDao {
    @Query("SELECT * FROM transcendence_risk ORDER BY riskId DESC")
    fun observeAll(): Flow<List<TranscendenceRiskEntity>>

    @Query("SELECT * FROM transcendence_risk ORDER BY riskId DESC")
    suspend fun getAll(): List<TranscendenceRiskEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: TranscendenceRiskEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<TranscendenceRiskEntity>)

    @Update
    suspend fun update(item: TranscendenceRiskEntity)
}

@Dao
interface TranscendenceHealthDao {
    @Query("SELECT * FROM transcendence_health ORDER BY healthId DESC")
    fun observeAll(): Flow<List<TranscendenceHealthEntity>>

    @Query("SELECT * FROM transcendence_health ORDER BY healthId DESC")
    suspend fun getAll(): List<TranscendenceHealthEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: TranscendenceHealthEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<TranscendenceHealthEntity>)

    @Update
    suspend fun update(item: TranscendenceHealthEntity)
}

@Dao
interface TranscendenceExpansionDao {
    @Query("SELECT * FROM transcendence_expansion ORDER BY expansionId DESC")
    fun observeAll(): Flow<List<TranscendenceExpansionEntity>>

    @Query("SELECT * FROM transcendence_expansion ORDER BY expansionId DESC")
    suspend fun getAll(): List<TranscendenceExpansionEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: TranscendenceExpansionEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<TranscendenceExpansionEntity>)

    @Update
    suspend fun update(item: TranscendenceExpansionEntity)
}
