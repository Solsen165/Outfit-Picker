package com.example.outfitpicker

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData

class ClothesRepository(private val clothesDao: ClothesDao) {
    private val allItems: LiveData<List<Item>> = clothesDao.getAllItems()
    private val allOutfits: LiveData<List<Outfit>> = clothesDao.getAllOutfits()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertItem(item: Item) {
        clothesDao.insertItem(item)
    }
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun updateItem(item: Item) {
        clothesDao.updateItem(item)
    }
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun deleteItem(item: Item) {
        clothesDao.deleteItem(item)
    }
    fun getAllItems() : LiveData<List<Item>> {
        return allItems
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertOutfit(outfit: Outfit) {
        clothesDao.insertOutfit(outfit)
    }
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun updateOutfit(outfit: Outfit) {
        clothesDao.updateOutfit(outfit)
    }
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun deleteOutfit(outfit: Outfit) {
        clothesDao.deleteOutfit(outfit)
    }
    fun getAllOutfits() : LiveData<List<Outfit>> {
        return allOutfits
    }

}