package com.example.outfitpicker

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("item_table")
data class Item(
    val name : String,
    val type : String
){
    @PrimaryKey(autoGenerate = true)
    val id : Int = 0
}
