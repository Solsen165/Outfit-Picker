package com.example.outfitpicker

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContract
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.example.outfitpicker.databasefiles.Item
import com.example.outfitpicker.databasefiles.Outfit
import com.example.outfitpicker.databasefiles.OutfitWithItems

class ShowingOutfitActivity : AppCompatActivity() {
    lateinit var currOutfit: OutfitWithItems
    lateinit var textViewName: TextView
    lateinit var textViewSeasons: TextView
    lateinit var textViewOccasions: TextView
    lateinit var recyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_showing_outfit)

        currOutfit = extractOutfit()
        textViewName = findViewById(R.id.outfit_name)
        textViewSeasons = findViewById(R.id.outfit_season_info)
        textViewOccasions = findViewById(R.id.outfit_occasion_info)
        recyclerView = findViewById(R.id.recycler_view_clothes_items_for_outfit)

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
        adapter.setItems(currOutfit.items)

        textViewName.setText(currOutfit.outfit.name)
        fillSeasonsAndOccasions()
    }

    fun extractOutfit(): OutfitWithItems {
        val id = intent.getIntExtra("id",0)
        val name = intent.getStringExtra("name").orEmpty()
        val outfit = Outfit(id,name)
        outfit.setBools(intent.getStringExtra("bools").orEmpty())
        val items = intent.getParcelableArrayListExtra<Item>("items")!!.toList()
        return OutfitWithItems(outfit,items)
    }

    fun fillSeasonsAndOccasions() {
        val s = currOutfit.outfit.getBools()
        val seasons = StringBuilder()
        val occasions = StringBuilder()
        for (i in s.indices) {
            when(i) {
                0 -> {if(s[i] == '1') seasons.append("\u2022 Summer ")}
                1 -> {if(s[i] == '1') seasons.append("\u2022 Autumn ")}
                2 -> {if(s[i] == '1') seasons.append("\u2022 Winter ")}
                3 -> {if(s[i] == '1') seasons.append("\u2022 Spring ")}
                4 -> {if(s[i] == '1') occasions.append("\u2022 Casual ")}
                5 -> {if(s[i] == '1') occasions.append("\u2022 Formal ")}
                6 -> {if(s[i] == '1') occasions.append("\u2022 Sports ")}
                7 -> {if(s[i] == '1') occasions.append("\u2022 Home ")}
            }
        }
        textViewSeasons.setText(seasons.toString())
        textViewOccasions.setText(occasions.toString())
    }

    class ShowOutfitContract: ActivityResultContract<OutfitWithItems, OutfitWithItems?>() {
        override fun createIntent(context: Context, input: OutfitWithItems): Intent {
            return Intent(context, ShowingOutfitActivity::class.java)
                .putParcelableArrayListExtra("items", ArrayList(input.items))
                .putExtra("id",input.outfit.outfitId)
                .putExtra("name",input.outfit.name)
                .putExtra("bools",input.outfit.getBools())
        }

        override fun parseResult(resultCode: Int, intent: Intent?): OutfitWithItems? {
            if (resultCode != RESULT_OK) {
                return null
            }
            val outfit = Outfit(
                outfitId = intent!!.getIntExtra("id",0),
                name = intent.getStringExtra("name").orEmpty()
            )
            outfit.setBools(intent.getStringExtra("bools").orEmpty())
            val items = intent.getParcelableArrayListExtra<Item>("items")!!.toList()

            return OutfitWithItems(outfit,items)
        }

    }
}