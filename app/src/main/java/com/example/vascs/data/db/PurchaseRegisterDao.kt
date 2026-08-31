package com.example.vascs.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.vascs.data.model.PurchaseRegisterEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PurchaseRegisterDao {
    @Query("SELECT * FROM purchase_register ORDER BY purchaseId DESC")
    fun getAllPurchases(): Flow<List<PurchaseRegisterEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPurchase(purchase: PurchaseRegisterEntity): Long
}
