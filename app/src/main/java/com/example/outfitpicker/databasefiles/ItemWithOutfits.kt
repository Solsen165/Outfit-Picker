package com.example.outfitpicker.databasefiles

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class ItemWithOutfits(
    @Embedded val item: Item,
    @Relation(
        parentColumn = "itemId",
        entityColumn = "outfitId",
        associateBy = Junction(ItemOutfitCrossRef::class)
    )
    val outfits: List<Outfit>
)
