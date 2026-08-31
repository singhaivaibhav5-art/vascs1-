package com.example.vascs.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.vascs.data.model.PlanetaryTradeRouteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PlanetaryTradeRouteDao {
    @Query("SELECT * FROM planetary_trade_routes ORDER BY routeId DESC")
    fun getAllRoutes(): Flow<List<PlanetaryTradeRouteEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRoute(route: PlanetaryTradeRouteEntity): Long

    @Update
    suspend fun updateRoute(route: PlanetaryTradeRouteEntity)
}
