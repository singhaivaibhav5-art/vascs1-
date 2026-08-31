package com.example.vascs.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.vascs.data.model.CashBookEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CashBookDao {
    @Query("SELECT * FROM cash_book ORDER BY cashTxnId DESC")
    fun getAllCashTxns(): Flow<List<CashBookEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCashTxn(txn: CashBookEntity): Long
}
