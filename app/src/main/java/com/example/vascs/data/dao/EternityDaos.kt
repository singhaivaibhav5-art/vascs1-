package com.example.vascs.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.vascs.data.model.*
import kotlinx.coroutines.flow.Flow

@Dao
interface EternityCoreDao {
    @Query("SELECT * FROM eternity_core ORDER BY coreId DESC")
    fun getAllEternityCore(): Flow<List<EternityCoreEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCore(core: EternityCoreEntity): Long
}

@Dao
interface WealthUniverseDao {
    @Query("SELECT * FROM wealth_universe ORDER BY wealthId DESC")
    fun getAllWealthUniverse(): Flow<List<WealthUniverseEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWealthList(list: List<WealthUniverseEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWealth(wealth: WealthUniverseEntity): Long
}

@Dao
interface DemandUniverseDao {
    @Query("SELECT * FROM demand_universe ORDER BY demandId DESC")
    fun getAllDemandUniverse(): Flow<List<DemandUniverseEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDemandList(list: List<DemandUniverseEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDemand(demand: DemandUniverseEntity): Long
}

@Dao
interface CapitalUniverseDao {
    @Query("SELECT * FROM capital_universe ORDER BY capitalId DESC")
    fun getAllCapitalUniverse(): Flow<List<CapitalUniverseEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCapitalList(list: List<CapitalUniverseEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCapital(capital: CapitalUniverseEntity): Long
}

@Dao
interface TradeInfinityDao {
    @Query("SELECT * FROM trade_infinity ORDER BY tradeId DESC")
    fun getAllTradeInfinity(): Flow<List<TradeInfinityEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTradeList(list: List<TradeInfinityEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTrade(trade: TradeInfinityEntity): Long
}

@Dao
interface KnowledgeEternityDao {
    @Query("SELECT * FROM knowledge_eternity ORDER BY knowledgeId DESC")
    fun getAllKnowledgeEternity(): Flow<List<KnowledgeEternityEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertKnowledgeList(list: List<KnowledgeEternityEntity>)
}

@Dao
interface RiskShieldDao {
    @Query("SELECT * FROM risk_shield ORDER BY riskId DESC")
    fun getAllRiskShield(): Flow<List<RiskShieldEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRiskList(list: List<RiskShieldEntity>)
}

@Dao
interface EternityHealthDao {
    @Query("SELECT * FROM eternity_health ORDER BY healthId DESC")
    fun getAllEternityHealth(): Flow<List<EternityHealthEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHealthList(list: List<EternityHealthEntity>)
}

@Dao
interface EternityInnovationDao {
    @Query("SELECT * FROM eternity_innovation ORDER BY innovationId DESC")
    fun getAllInnovations(): Flow<List<EternityInnovationEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertInnovations(list: List<EternityInnovationEntity>)
}
