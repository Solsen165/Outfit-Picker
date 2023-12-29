package com.example.outfitpicker.databasefiles

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class OutfitWithItems(
    @Embedded val outfit: Outfit,
    @Relation(
        parentColumn = "outfitId",
        entityColumn = "itemId",
        associateBy = Junction(ItemOutfitCrossRef::class)
    )
    val items: List<Item>
)
