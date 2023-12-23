package com.example.outfitpicker

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
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

        editTextName = findViewById(R.id.item_name)
        imageView = findViewById(R.id.item_image_preview)
        spinnerType = findViewById(R.id.spinnerType)
        buttonSave = findViewById(R.id.buttonSave)

        buttonSave.setOnClickListener{
            saveItem()
        }

        val bitmap = intent.getByteArrayExtra("image")?.let { toBitmap(it) }
        imageView.setImageBitmap(bitmap)

    }

    fun saveItem() {
        val itemName: String = editTextName.text.toString()
        val itemType: String = spinnerType.selectedItem.toString()

        //val outputStream = ByteArrayOutputStream()
        //val bitmap = imageView.drawable.toBitmap()
        //bitmap.compress(Bitmap.CompressFormat.PNG,100,outputStream)
        //val byteArray = outputStream.toByteArray()

        val image = fromBitmap(imageView.drawable.toBitmap())

        //TODO add data verification

        var newIntent = Intent().putExtra("name",itemName)
            .putExtra("type",itemType)
            .putExtra("image",image)

        setResult(RESULT_OK,newIntent)
        finish()
    }

    fun fromBitmap(bitmap: Bitmap): ByteArray {
        val outputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG,100,outputStream)
        return outputStream.toByteArray()
    }

    fun toBitmap(byteArray: ByteArray): Bitmap {
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
    }


    // Contract that connects this activity with the main one
    // It basically manages how the intents are processed
    class AddItemContract: ActivityResultContract<Bitmap, Item?>() {
        override fun createIntent(context: Context, input: Bitmap): Intent {
            val bitmap = input
            val outputStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG,100,outputStream)
            val byteArray = outputStream.toByteArray()

            return Intent(context, SavingClothesItemActivity::class.java)
                .putExtra("image",byteArray)
        }

        override fun parseResult(resultCode: Int, intent: Intent?): Item? {
            if (resultCode != RESULT_OK) {
                return null
            }
            val byteArray = intent?.getByteArrayExtra("image")
            val bitmap = BitmapFactory.decodeByteArray(byteArray,0,byteArray!!.size)

            val newItem = Item(
                name = intent!!.getStringExtra("name").orEmpty(),
                type = intent.getStringExtra("type").orEmpty(),
                image = bitmap)

            return newItem
        }
    }

}