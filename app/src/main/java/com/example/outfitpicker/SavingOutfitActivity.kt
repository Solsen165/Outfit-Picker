package com.example.outfitpicker

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.outfitpicker.databasefiles.Item
import com.example.outfitpicker.databasefiles.Outfit
import com.example.outfitpicker.databasefiles.OutfitWithItems
import java.lang.StringBuilder

class SavingOutfitActivity : AppCompatActivity() {
    lateinit var editTextName: EditText
    lateinit var seasonsLayout: LinearLayout
    lateinit var occasionsLayout: LinearLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_saving_outfit)
        title = "Add Outfit"

        editTextName = findViewById(R.id.outfit_name)
        seasonsLayout = findViewById(R.id.seasonsLayout)
        occasionsLayout = findViewById(R.id.occasionsLayout)

        val items = intent.getParcelableArrayListExtra<Item>("items")!!.toList()

        val recyclerView: RecyclerView = findViewById(R.id.recycler_view_clothes_items_for_outfit)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        val adapter: ItemsAdapter = ItemsAdapter(filesDir, object : ItemsAdapter.OnItemClickListener {
            override fun onItemClick(item: Item) {
                return
            }

            override fun selectItem(item: Item) {
                return
            }

            override fun deSelectItem(item: Item) {
                return
            }

        }, false, false)
        recyclerView.adapter = adapter

        val buttonSave : Button = findViewById(R.id.buttonSave)
        buttonSave.setOnClickListener {
            saveOutfit(items)
        }


        adapter.setItems(items)
        //recyclerView.invalidate()
    }

    fun saveOutfit(items: List<Item>) {
        val outfitName: String = editTextName.text.toString()
        var bools: String = "00000000"
        val sb = StringBuilder(bools)

        if (outfitName.trim().isEmpty()) {
            Toast.makeText(this,"Please insert a name for the outfit", Toast.LENGTH_SHORT).show()
            return
        }

        for (i in 0..<seasonsLayout.childCount) {
            val v = seasonsLayout.getChildAt(i) as CheckBox
            if (v.isChecked) {
                sb.setCharAt(i,'1')
            }
        }

        for (i in 0..<occasionsLayout.childCount) {
            val v = occasionsLayout.getChildAt(i) as CheckBox
            if (v.isChecked) {
                sb.setCharAt(i+4,'1')
            }
        }

        var newIntent = Intent().putExtra("name",outfitName)
            .putExtra("bools",sb.toString())
            .putParcelableArrayListExtra("items", ArrayList(items))

        setResult(RESULT_OK, newIntent)
        finish()
    }
    class AddOutfitContract: ActivityResultContract<List<Item>, OutfitWithItems?>() {
        override fun createIntent(context: Context, input: List<Item>): Intent {
            return Intent(context, SavingOutfitActivity::class.java)
                .putParcelableArrayListExtra("items", ArrayList(input))
        }

        override fun parseResult(resultCode: Int, intent: Intent?): OutfitWithItems? {
            if (resultCode != RESULT_OK) {
                return null
            }

            val outfit = Outfit(
                name = intent!!.getStringExtra("name").orEmpty()
            )
            outfit.setBools(intent.getStringExtra("bools").orEmpty())
            val items = intent.getParcelableArrayListExtra<Item>("items")!!.toList()
            val outfitWithItems = OutfitWithItems(outfit, items)

            return outfitWithItems
        }

    }
}