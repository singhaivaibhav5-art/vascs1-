package com.example.vascs.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.vascs.data.model.AccountLedgerEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AccountLedgerDao {
    @Query("SELECT * FROM chart_of_accounts ORDER BY ledgerId DESC")
    fun getAllLedgers(): Flow<List<AccountLedgerEntity>>

    @Query("SELECT * FROM chart_of_accounts WHERE ledgerGroup = :group ORDER BY ledgerName ASC")
    fun getLedgersByGroup(group: String): Flow<List<AccountLedgerEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLedger(ledger: AccountLedgerEntity): Long

    @Update
    suspend fun updateLedger(ledger: AccountLedgerEntity)
}
