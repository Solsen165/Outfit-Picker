package com.example.outfitpicker

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [Item::class,Outfit::class], version = 1)
@TypeConverters(Converters::class)
abstract class ClothesDatabase: RoomDatabase() {
    abstract fun clothesDao() : ClothesDao

    companion object {
        @Volatile
        private var INSTANCE: ClothesDatabase? = null

        fun getDatabase(context: Context): ClothesDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ClothesDatabase::class.java,
                    "clothes_database"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }
}