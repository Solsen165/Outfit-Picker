package com.example.outfitpicker.databasefiles

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.RawQuery
import androidx.room.Transaction
import androidx.room.Update
import androidx.sqlite.db.SimpleSQLiteQuery

@Dao
interface ClothesDao {
    @Insert
    suspend fun insertItem(item: Item): Long

    @Update
    suspend fun updateItem(item: Item)

    @Delete
    suspend fun deleteItem(item: Item)

    @Query("Select * FROM item_table")
    fun getAllItems(): LiveData<List<Item>>

    @Insert
    suspend fun insertOutfit(outfit: Outfit): Long

    @Update
    suspend fun updateOutfit(outfit: Outfit)

    @Delete
    suspend fun deleteOutfit(outfit: Outfit)

    @Query("Select * FROM outfit_table")
    fun getAllOutfits(): LiveData<List<Outfit>>

    @Insert
    suspend fun insertItemOutfitCrossRef(crossRef: ItemOutfitCrossRef)
    @Delete
    suspend fun deleteItemOutfitCrossRef(crossRef: ItemOutfitCrossRef)

    @Transaction
    @Query("SELECT * FROM outfit_table")
    fun getOutfitWithItems(): LiveData<List<OutfitWithItems>>

    @Transaction
    @Query("SELECT * FROM outfit_table WHERE outfitId = :outfitId")
    suspend fun getOutfitWithItems(outfitId: Int): OutfitWithItems

    @Transaction
    @Query("SELECT * FROM outfit_table WHERE outfitId IN (SELECT outfitId FROM ItemOutfitCrossRef WHERE itemId = :itemId)")
    suspend fun getOutfitsWithItemsId(itemId: Int): List<OutfitWithItems>

    @Transaction
    @RawQuery
    suspend fun getOutfitWithAttributes(query: SimpleSQLiteQuery): List<OutfitWithItems>
}