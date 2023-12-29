package com.example.outfitpicker.databasefiles

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData

class ClothesRepository(private val clothesDao: ClothesDao) {
    private val allItems: LiveData<List<Item>> = clothesDao.getAllItems()
    private val allOutfits: LiveData<List<OutfitWithItems>> = clothesDao.getOutfitWithItems()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertItem(item: Item): Long {
        return clothesDao.insertItem(item)
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
    suspend fun insertOutfit(outfit: Outfit): Long {
        return clothesDao.insertOutfit(outfit)
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
    fun getAllOutfits() : LiveData<List<OutfitWithItems>> {
        return allOutfits
    }
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertItemOutfitCrossRef(itemOutfitCrossRef: ItemOutfitCrossRef) {
        clothesDao.insertItemOutfitCrossRef(itemOutfitCrossRef)
    }
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun deleteItemOutfitCrossRef(itemOutfitCrossRef: ItemOutfitCrossRef) {
        clothesDao.deleteItemOutfitCrossRef(itemOutfitCrossRef)
    }
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun getOutfitWithItems(outfitId: Int): OutfitWithItems {
        return clothesDao.getOutfitWithItems(outfitId)
    }



}