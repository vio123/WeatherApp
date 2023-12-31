package com.example.weatherapp.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.weatherapp.data.local.entity.WeatherEntity

@Dao
interface TemperatureDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(temperature:WeatherEntity)

    @Delete
    suspend fun delete(temperature:WeatherEntity)

    @Query("Select * from Temperature order by id DESC")
    fun getAllTemperatures(): List<WeatherEntity>

    @Query("DELETE FROM Temperature")
    suspend fun deleteAll()
}