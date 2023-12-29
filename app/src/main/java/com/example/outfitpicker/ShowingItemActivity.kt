package com.example.outfitpicker

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContract
import androidx.core.net.toUri
import com.example.outfitpicker.databasefiles.Item
import java.io.File

class ShowingItemActivity : AppCompatActivity() {
    lateinit var imageView: ImageView
    lateinit var textViewName : TextView
    lateinit var textViewtype : TextView
    lateinit var textViewSeasons: TextView
    lateinit var textViewOccasions: TextView
    lateinit var currItem: Item
    private val editItemContract = registerForActivityResult(SavingClothesItemActivity.EditItemContract()) {item ->
        if (item != null) {
            currItem = item
            populateFields()
            var newIntent = Intent().putExtra("name",item.name)
                .putExtra("type",item.type)
                .putExtra("bools",item.getBools())

            if (intent.hasExtra("id")) {
                newIntent.putExtra("id",intent.getIntExtra("id",0))
            }
            setResult(RESULT_OK,newIntent)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.showing_item_photo)

        imageView = findViewById(R.id.item_image_preview)
        textViewName = findViewById(R.id.item_name)
        textViewtype = findViewById(R.id.spinnerType)
        textViewOccasions = findViewById(R.id.item_occasion_info)
        textViewSeasons = findViewById(R.id.item_season_info)

        currItem = extractItem()
        populateFields()

    }

    @Override
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.showing_item_menu,menu)
        return true
    }

    @Override
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.edit_item -> {
                editItemContract.launch(currItem)
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    fun extractItem(): Item {
        val id = intent.getIntExtra("id",0)
        val name = intent.getStringExtra("name").orEmpty()
        val type = intent.getStringExtra("type").orEmpty()
        val item = Item(id,name,type)
        item.setBools(intent.getStringExtra("bools").orEmpty())
        return item
    }
    fun populateFields() {
        val imageFile = File(filesDir,"Item#${currItem.itemId}.png")
        imageView.setImageURI(null)
        imageView.setImageURI(imageFile.toUri())
        textViewName.setText(currItem.name)
        textViewtype.setText(currItem.type)
        fillSeasonsAndOccasions()
    }

    fun fillSeasonsAndOccasions() {
        val s = currItem.getBools()
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
    class ShowItemContract: ActivityResultContract<Item,Item?>() {
        override fun createIntent(context: Context, input: Item): Intent {
            return Intent(context, ShowingItemActivity::class.java)
                .putExtra("id",input.itemId)
                .putExtra("name",input.name)
                .putExtra("type",input.type)
                .putExtra("bools",input.getBools())
        }

        override fun parseResult(resultCode: Int, intent: Intent?): Item? {
            if (resultCode != RESULT_OK) {
                return null
            }
            val newItem = Item(
                itemId = intent!!.getIntExtra("id",0),
                name = intent!!.getStringExtra("name").orEmpty(),
                type = intent.getStringExtra("type").orEmpty()
            )
            newItem.setBools(intent.getStringExtra("bools").orEmpty())
            return newItem
        }

    }
}