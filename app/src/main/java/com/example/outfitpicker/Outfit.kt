package com.example.outfitpicker

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("outfit_table")
data class Outfit(
    val name : String,
    val items : List<Item>
){
    @PrimaryKey(autoGenerate = true)
    val id : Int = 0
}
