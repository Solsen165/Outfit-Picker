package com.example.outfitpicker

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface ClothesDao {
    @Insert
    suspend fun insertItem(item:Item)

    @Update
    suspend fun updateItem(item:Item)

    @Delete
    suspend fun deleteItem(item: Item)

    @Query("Select * FROM item_table")
    fun getAllItems(): LiveData<List<Item>>

    @Insert
    suspend fun insertOutfit(outfit:Outfit)

    @Update
    suspend fun updateOutfit(outfit:Outfit)

    @Delete
    suspend fun deleteOutfit(outfit: Outfit)

    @Query("Select * FROM outfit_table")
    fun getAllOutfits(): LiveData<List<Outfit>>
}