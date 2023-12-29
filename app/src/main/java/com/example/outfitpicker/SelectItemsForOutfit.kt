package com.example.outfitpicker

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.outfitpicker.databasefiles.Item
import com.google.android.material.floatingactionbutton.FloatingActionButton

class SelectItemsForOutfit : AppCompatActivity() {
    var selectedItems: MutableList<Item> = mutableListOf()
    private val itemsViewModel: ItemsViewModel by viewModels {
        ItemsViewModelFactory((application as ClothesApplication).repository)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_items_for_outfit)
        title = "Select items for your outfit"

        val recyclerView: RecyclerView = findViewById(R.id.recycler_view_clothes_items)
        recyclerView.layoutManager = GridLayoutManager(this, 3)
        val adapter: ItemsAdapter = ItemsAdapter(filesDir, object : ItemsAdapter.OnItemClickListener {
            override fun onItemClick(item: Item) {
                return
            }

            override fun selectItem(item: Item) {
                selectedItems.add(item)
            }

            override fun deSelectItem(item: Item) {
                selectedItems.remove(item)
            }

        }, true, true)
        recyclerView.adapter = adapter

        itemsViewModel.getAllItems().observe(this, Observer { items ->
            adapter.setItems(items)
            recyclerView.invalidate()
        })

        val confirmButton: FloatingActionButton = findViewById(R.id.button_confirm_selection)
        confirmButton.setOnClickListener {
            saveSelection(selectedItems.toList())
        }
    }

    fun saveSelection(items : List<Item>) {
        val arraylist = ArrayList(items)
        var newIntent = Intent().putParcelableArrayListExtra("items", arraylist)

        setResult(RESULT_OK, newIntent)
        finish()
    }

    class SelectItemsContract: ActivityResultContract<List<Item>?,List<Item>?>() {
        override fun createIntent(context: Context, input: List<Item>?): Intent {
            val intent = Intent(context, SelectItemsForOutfit::class.java)
            if (input != null) {
                intent.putParcelableArrayListExtra("items",ArrayList(input))
            }
            return intent
        }

        override fun parseResult(resultCode: Int, intent: Intent?): List<Item>? {
            if (resultCode != RESULT_OK) {
                return null
            }
            val arrayList: ArrayList<Item> = intent!!.getParcelableArrayListExtra<Item>("items")!!

            return arrayList.toList()
        }

    }
}