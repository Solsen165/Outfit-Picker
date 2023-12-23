package com.example.outfitpicker

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("outfit_table")
data class Outfit(
    val Name : String
){
    @PrimaryKey(autoGenerate = true)
    val Id : Int = 0
}
