package com.example.weatherappfragment.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.weatherappfragment.data.database.dao.CityDao
import com.example.weatherappfragment.model.roomEntity.Favorites
import com.example.weatherappfragment.model.roomEntity.Forecast

@Database(entities = [Favorites::class, Forecast::class], version = 3)
abstract class CityDatabase : RoomDatabase() {
    abstract fun cityDao(): CityDao

    companion object {
        // Singleton instance of the database
        @Volatile
        private var INSTANCE: CityDatabase? = null

        // Get the singleton instance of the database
        fun getInstance(context: Context): CityDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CityDatabase::class.java,
                    "city_database" // Database name
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }

}