package com.example.outfitpicker

import android.app.Application
import com.example.outfitpicker.databasefiles.ClothesDatabase
import com.example.outfitpicker.databasefiles.ClothesRepository

class ClothesApplication: Application() {
    val database: ClothesDatabase by lazy { ClothesDatabase.getDatabase(this)}
    val repository: ClothesRepository by lazy { ClothesRepository(database.clothesDao()) }
}