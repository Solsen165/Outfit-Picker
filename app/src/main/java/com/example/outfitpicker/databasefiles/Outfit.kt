package com.example.outfitpicker.databasefiles

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("outfit_table")
data class Outfit(
    @PrimaryKey(autoGenerate = true)
    val id : Int = 0,
    val name : String,
    //val items : List<Item>
)
