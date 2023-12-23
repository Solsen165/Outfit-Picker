package com.example.outfitpicker

import android.app.Application

class ClothesApplication: Application() {
    val database: ClothesDatabase by lazy { ClothesDatabase.getDatabase(this)}
    val repository: ClothesRepository by lazy { ClothesRepository(database.clothesDao())}
}