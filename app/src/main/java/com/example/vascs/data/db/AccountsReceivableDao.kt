package com.example.vascs.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.vascs.data.model.AccountsReceivableEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AccountsReceivableDao {
    @Query("SELECT * FROM receivables ORDER BY id DESC")
    fun getAllReceivables(): Flow<List<AccountsReceivableEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReceivable(receivable: AccountsReceivableEntity): Long

    @Update
    suspend fun updateReceivable(receivable: AccountsReceivableEntity)
}
