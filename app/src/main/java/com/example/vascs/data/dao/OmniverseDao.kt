package com.example.vascs.data.dao

import androidx.room.*
import com.example.vascs.data.model.*
import kotlinx.coroutines.flow.Flow

@Dao
interface OmniverseCoreDao {
    @Query("SELECT * FROM omniverse_core ORDER BY coreId DESC")
    fun getAllOmniverseCore(): Flow<List<OmniverseCoreEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCore(core: OmniverseCoreEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCores(cores: List<OmniverseCoreEntity>)

    @Query("DELETE FROM omniverse_core")
    suspend fun clearCores()
}

@Dao
interface EconomyNetworkDao {
    @Query("SELECT * FROM economy_network ORDER BY economyId DESC")
    fun getAllEconomies(): Flow<List<EconomyNetworkEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEconomy(economy: EconomyNetworkEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEconomies(economies: List<EconomyNetworkEntity>)

    @Update
    suspend fun updateEconomy(economy: EconomyNetworkEntity)

    @Query("DELETE FROM economy_network")
    suspend fun clearEconomies()
}

@Dao
interface MarketMatrixDao {
    @Query("SELECT * FROM market_matrix ORDER BY marketId DESC")
    fun getAllMarkets(): Flow<List<MarketMatrixEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMarket(market: MarketMatrixEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMarkets(markets: List<MarketMatrixEntity>)

    @Update
    suspend fun updateMarket(market: MarketMatrixEntity)

    @Query("DELETE FROM market_matrix")
    suspend fun clearMarkets()
}

@Dao
interface TradeGridDao {
    @Query("SELECT * FROM trade_grid ORDER BY gridId DESC")
    fun getAllTradeGrids(): Flow<List<TradeGridEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTradeGrid(grid: TradeGridEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTradeGrids(grids: List<TradeGridEntity>)

    @Update
    suspend fun updateTradeGrid(grid: TradeGridEntity)

    @Query("DELETE FROM trade_grid")
    suspend fun clearTradeGrids()
}

@Dao
interface KnowledgeFabricDao {
    @Query("SELECT * FROM knowledge_fabric ORDER BY fabricId DESC")
    fun getAllKnowledgeFabrics(): Flow<List<KnowledgeFabricEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertKnowledge(knowledge: KnowledgeFabricEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertKnowledgeList(knowledgeList: List<KnowledgeFabricEntity>)

    @Query("DELETE FROM knowledge_fabric")
    suspend fun clearKnowledge()
}

@Dao
interface IndustryMatrixDao {
    @Query("SELECT * FROM industry_matrix ORDER BY industryId DESC")
    fun getAllIndustries(): Flow<List<IndustryMatrixEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertIndustry(industry: IndustryMatrixEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertIndustries(industries: List<IndustryMatrixEntity>)

    @Query("DELETE FROM industry_matrix")
    suspend fun clearIndustries()
}

@Dao
interface OpportunityUniverseDao {
    @Query("SELECT * FROM opportunity_universe ORDER BY opportunityId DESC")
    fun getAllOpportunities(): Flow<List<OpportunityUniverseEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOpportunity(opportunity: OpportunityUniverseEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOpportunities(opportunities: List<OpportunityUniverseEntity>)

    @Update
    suspend fun updateOpportunity(opportunity: OpportunityUniverseEntity)

    @Query("DELETE FROM opportunity_universe")
    suspend fun clearOpportunities()
}

@Dao
interface OmniverseHealthDao {
    @Query("SELECT * FROM omniverse_health ORDER BY healthId ASC")
    fun getAllHealth(): Flow<List<OmniverseHealthEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHealth(health: OmniverseHealthEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHealthList(healthList: List<OmniverseHealthEntity>)

    @Query("DELETE FROM omniverse_health")
    suspend fun clearHealth()
}

@Dao
interface OmniverseRiskDao {
    @Query("SELECT * FROM omniverse_risk ORDER BY riskId DESC")
    fun getAllRisks(): Flow<List<OmniverseRiskEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRisk(risk: OmniverseRiskEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRisks(risks: List<OmniverseRiskEntity>)

    @Query("DELETE FROM omniverse_risk")
    suspend fun clearRisks()
}

@Dao
interface OmniverseInnovationDao {
    @Query("SELECT * FROM omniverse_innovation ORDER BY innovationId DESC")
    fun getAllInnovations(): Flow<List<OmniverseInnovationEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertInnovation(innovation: OmniverseInnovationEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertInnovations(innovations: List<OmniverseInnovationEntity>)

    @Query("DELETE FROM omniverse_innovation")
    suspend fun clearInnovations()
}
