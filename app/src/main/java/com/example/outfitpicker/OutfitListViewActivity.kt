package com.example.outfitpicker

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.outfitpicker.databasefiles.Item
import com.example.outfitpicker.databasefiles.ItemOutfitCrossRef
import com.example.outfitpicker.databasefiles.OutfitWithItems
import com.google.android.material.floatingactionbutton.FloatingActionButton

class OutfitListViewActivity : AppCompatActivity() {

    private val outfitViewModel : OutfitListViewModel by viewModels {
        OutfitViewModelFactory((application as ClothesApplication).repository)
    }
    private val showOutfitContract = registerForActivityResult(ShowingOutfitActivity.ShowOutfitContract()) {outfitWithItems ->
        if (outfitWithItems != null) {
            // TODO update the outfit
        }
    }
    private val addOutfitContract = registerForActivityResult(SavingOutfitActivity.AddOutfitContract()) {outfitWithItems ->
        if (outfitWithItems != null) {
            outfitViewModel.insert(outfitWithItems.outfit).observe(this, Observer {id ->
                for (item in outfitWithItems.items) {
                    val itemOutfitCrossRef = ItemOutfitCrossRef(item.itemId, id.toInt())
                    outfitViewModel.insert(itemOutfitCrossRef)
                }
            })
        }
    }
    private val selectItemsContract = registerForActivityResult(SelectItemsForOutfit.SelectItemsContract()) {items ->
        if (items != null) {
            addOutfitContract.launch(items.toList())
        }
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
                showOutfitContract.launch(outfit)
            }
        })
        recyclerView.adapter = adapter

        val addButton: FloatingActionButton = findViewById(R.id.button_add_outfit)
        addButton.setOnClickListener {
            selectItemsContract.launch(null)
        }

        // Observing the view model so that when we add new items, the recycler view is updated
        outfitViewModel.getAllOutfits().observe(this, Observer { outfits ->
            adapter.setOutfits(outfits)
            recyclerView.invalidate()
        })
    }
}