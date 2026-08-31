package com.example.vascs.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.vascs.data.model.WhiteLabelConfigEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WhiteLabelConfigDao {
    @Query("SELECT * FROM white_label_configs ORDER BY configId DESC")
    fun getAllWhiteLabelConfigs(): Flow<List<WhiteLabelConfigEntity>>

    @Query("SELECT * FROM white_label_configs WHERE companyId = :companyId LIMIT 1")
    fun getWhiteLabelByCompany(companyId: Long): Flow<WhiteLabelConfigEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWhiteLabelConfig(config: WhiteLabelConfigEntity): Long

    @Update
    suspend fun updateWhiteLabelConfig(config: WhiteLabelConfigEntity)
}
