package com.example.outfitpicker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.outfitpicker.databasefiles.OutfitWithItems
import com.google.android.material.floatingactionbutton.FloatingActionButton

class OutfitListViewActivity : AppCompatActivity() {

    private val outfitViewModel : OutfitListViewModel by viewModels {
        OutfitViewModelFactory((application as ClothesApplication).repository)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_outfit_list_view)

        title = "My Outfits"
        // RecyclerView
        val recyclerView: RecyclerView = findViewById(R.id.recycler_view_outfits)
        recyclerView.layoutManager = GridLayoutManager(this, 3)
        val adapter: OutfitAdapter = OutfitAdapter(filesDir, object : OutfitAdapter.OnOutfitClickListener {
            override fun onOutfitClick(outfit: OutfitWithItems) {
                TODO("Not yet implemented")
            }
        })
        recyclerView.adapter = adapter

        val addButton: FloatingActionButton = findViewById(R.id.button_add_outfit)
        addButton.setOnClickListener {
            TODO("Not yet implemented")
        }

        // Observing the view model so that when we add new items, the recycler view is updated
        outfitViewModel.getAllOutfits().observe(this, Observer { outfits ->
            adapter.setOutfits(outfits)
            recyclerView.invalidate()
        })
    }
}