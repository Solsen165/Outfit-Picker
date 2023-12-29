package com.example.outfitpicker.databasefiles

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.lang.StringBuilder
@Parcelize
@Entity("item_table")
data class Item(
    @PrimaryKey(autoGenerate = true)
    val itemId : Int = 0,
    val name : String,
    val type : String,
    var summer : Boolean = false,
    var autumn : Boolean = false,
    var winter : Boolean = false,
    var spring : Boolean = false,
    var casual : Boolean = false,
    var formal : Boolean = false,
    var sports : Boolean = false,
    var home : Boolean = false

    //val image : Bitmap
) : Parcelable {
    fun setBools(s : String) {
        for (i in s.indices) {
            when(i) {
                0 -> {this.summer = (s[i] == '1')}
                1 -> {this.autumn = (s[i] == '1')}
                2 -> {this.winter = (s[i] == '1')}
                3 -> {this.spring = (s[i] == '1')}
                4 -> {this.casual = (s[i] == '1')}
                5 -> {this.formal = (s[i] == '1')}
                6 -> {this.sports = (s[i] == '1')}
                7 -> {this.home = (s[i] == '1')}
            }
        }
    }
    fun getBools(): String {
        var s: String = "00000000"
        val sb = StringBuilder(s)
        for (i in s.indices) {
            when(i) {
                0 -> {if(this.summer) sb.setCharAt(i,'1')}
                1 -> {if(this.autumn) sb.setCharAt(i,'1')}
                2 -> {if(this.winter) sb.setCharAt(i,'1')}
                3 -> {if(this.spring) sb.setCharAt(i,'1')}
                4 -> {if(this.casual) sb.setCharAt(i,'1')}
                5 -> {if(this.formal) sb.setCharAt(i,'1')}
                6 -> {if(this.sports) sb.setCharAt(i,'1')}
                7 -> {if(this.home) sb.setCharAt(i,'1')}
            }
        }
        return sb.toString()
    }
}
