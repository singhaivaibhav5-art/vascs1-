package com.example.vascs.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.vascs.data.model.BankBookEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BankBookDao {
    @Query("SELECT * FROM bank_book ORDER BY bankTxnId DESC")
    fun getAllBankTxns(): Flow<List<BankBookEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBankTxn(txn: BankBookEntity): Long
}
