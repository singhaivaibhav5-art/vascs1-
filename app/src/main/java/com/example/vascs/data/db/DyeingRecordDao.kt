package com.example.vascs.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.vascs.data.model.DyeingRecordEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DyeingRecordDao {
    @Query("SELECT * FROM dyeing_records ORDER BY dyeingId DESC")
    fun getAllDyeingRecords(): Flow<List<DyeingRecordEntity>>

    @Query("SELECT * FROM dyeing_records WHERE batchId = :batchId")
    fun getDyeingRecordsForBatch(batchId: Long): Flow<List<DyeingRecordEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDyeingRecord(record: DyeingRecordEntity): Long
}
