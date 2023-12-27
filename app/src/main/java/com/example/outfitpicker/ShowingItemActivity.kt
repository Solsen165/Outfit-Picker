package com.example.outfitpicker

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContract
import androidx.core.net.toUri
import com.example.outfitpicker.databasefiles.Item
import org.w3c.dom.Text
import java.io.File

class ShowingItemActivity : AppCompatActivity() {
    lateinit var imageView: ImageView
    lateinit var textViewName : TextView
    lateinit var textViewtype : TextView
    lateinit var seasonLayout: LinearLayout
    lateinit var occasionLayout: LinearLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.showing_item_photo)

        imageView = findViewById(R.id.item_image_preview)
        textViewName = findViewById(R.id.item_name)
        textViewtype = findViewById(R.id.spinnerType)

        val id = intent.getIntExtra("id",0)
        val imageFile = File(filesDir,"Item#$id.png")
        imageView.setImageURI(imageFile.toUri())
        val name = intent.getStringExtra("name").orEmpty()
        val type = intent.getStringExtra("type").orEmpty()

        textViewName.setText(name)
        textViewtype.setText(type)


    }

    class ShowItemContract: ActivityResultContract<Item,Unit>() {
        override fun createIntent(context: Context, input: Item): Intent {
            return Intent(context, ShowingItemActivity::class.java)
                .putExtra("id",input.id)
                .putExtra("name",input.name)
                .putExtra("type",input.type)

        }

        override fun parseResult(resultCode: Int, intent: Intent?) {
            return Unit
        }

    }
}