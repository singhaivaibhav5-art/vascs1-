package com.example.vascs.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.vascs.data.model.FinishedGoodsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FinishedGoodsDao {
    @Query("SELECT * FROM finished_goods ORDER BY finishedId DESC")
    fun getAllFinishedGoods(): Flow<List<FinishedGoodsEntity>>

    @Query("SELECT * FROM finished_goods WHERE batchId = :batchId")
    fun getFinishedGoodsForBatch(batchId: Long): Flow<List<FinishedGoodsEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFinishedGoods(goods: FinishedGoodsEntity): Long

    @Update
    suspend fun updateFinishedGoods(goods: FinishedGoodsEntity)
}
