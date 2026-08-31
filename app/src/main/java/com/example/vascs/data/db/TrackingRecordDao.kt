package com.example.vascs.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.vascs.data.model.TrackingRecordEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TrackingRecordDao {
    @Query("SELECT * FROM tracking_records WHERE dispatchId = :dispatchId ORDER BY id DESC")
    fun getTrackingRecordsForDispatch(dispatchId: Long): Flow<List<TrackingRecordEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTrackingRecord(record: TrackingRecordEntity): Long
}
