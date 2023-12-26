package com.example.outfitpicker.databasefiles

import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("item_table")
data class Item(
    @PrimaryKey(autoGenerate = true)
    val id : Int = 0,
    val name : String,
    val type : String
    //val image : Bitmap
)
