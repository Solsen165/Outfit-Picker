package com.example.outfitpicker

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import androidx.activity.result.contract.ActivityResultContract
import androidx.core.graphics.drawable.toBitmap
import androidx.room.TypeConverter
import java.io.ByteArrayOutputStream

class SavingClothesItemActivity : AppCompatActivity() {
    lateinit var editTextName: EditText
    lateinit var imageView: ImageView
    lateinit var spinnerType: Spinner
    lateinit var buttonSave: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.saving_photo_layout)
        title = "Add Clothes Item"

        editTextName = findViewById(R.id.item_name)
        imageView = findViewById(R.id.item_image_preview)
        spinnerType = findViewById(R.id.spinnerType)
        buttonSave = findViewById(R.id.buttonSave)

        buttonSave.setOnClickListener{
            saveItem()
        }
        val imageUri = Uri.parse(intent.getStringExtra("imageUri"))
        imageView.setImageURI(imageUri)

    }

    fun saveItem() {
        val itemName: String = editTextName.text.toString()
        val itemType: String = spinnerType.selectedItem.toString()

        //TODO add data verification

        var newIntent = Intent().putExtra("name",itemName)
            .putExtra("type",itemType)

        setResult(RESULT_OK,newIntent)
        finish()
    }

    // Contract that connects this activity with the main one
    // It basically manages how the intents are processed
    class AddItemContract: ActivityResultContract<Uri, Item?>() {
        override fun createIntent(context: Context, input: Uri): Intent {
            return Intent(context, SavingClothesItemActivity::class.java)
                .putExtra("imageUri",input.toString())
        }

        override fun parseResult(resultCode: Int, intent: Intent?): Item? {
            if (resultCode != RESULT_OK) {
                return null
            }

            val newItem = Item(
                name = intent!!.getStringExtra("name").orEmpty(),
                type = intent.getStringExtra("type").orEmpty())

            return newItem
        }
    }

}