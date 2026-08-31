package com.example.vascs.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.vascs.data.model.CountryMasterEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CountryMasterDao {
    @Query("SELECT * FROM country_master ORDER BY countryId DESC")
    fun getAllCountries(): Flow<List<CountryMasterEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCountry(country: CountryMasterEntity): Long

    @Update
    suspend fun updateCountry(country: CountryMasterEntity)
}
