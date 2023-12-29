package com.example.outfitpicker.databasefiles

import androidx.room.Entity

@Entity(primaryKeys = ["itemId","outfitId"])
data class ItemOutfitCrossRef(
    val itemId: Int,
    val outfitId: Int
)
