package com.example.outfitpicker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels

class ItemsView : AppCompatActivity() {
    private val itemsViewModel: ItemsViewModel by viewModels {
        ItemsViewModelFactory((application as ClothesApplication).repository)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_items_view)
    }
}