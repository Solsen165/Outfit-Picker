package com.example.outfitpicker

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.core.net.toFile
import androidx.core.net.toUri
import androidx.core.view.children
import androidx.core.view.get
import androidx.core.view.indices
import com.example.outfitpicker.databasefiles.Item
import java.io.File
import java.io.FileOutputStream

class SavingClothesItemActivity : AppCompatActivity() {
    lateinit var editTextName: EditText
    lateinit var imageView: ImageView
    lateinit var spinnerType: Spinner
    lateinit var buttonSave: Button
    lateinit var buttonCamera: Button
    lateinit var seasonsLayout: LinearLayout
    lateinit var occasionsLayout: LinearLayout

    lateinit var imageUri: Uri
    lateinit var tempFileUri: Uri
    private val takePicture = registerForActivityResult(ActivityResultContracts.TakePicture()) {flag ->
        if (flag) {
            val oldFile = File(filesDir,"temp.png")
            val bytes = contentResolver.openInputStream(oldFile.toUri())?.use {
                it.readBytes()
            }
            val file = imageUri.toFile()
            FileOutputStream(file).use {
                it.write(bytes)
            }
            imageView.setImageURI(null)
            imageView.setImageURI(imageUri)
        }
    }

    // Contract to select from gallery
    private val takePictureFromGallery = registerForActivityResult(ActivityResultContracts.GetContent()) { selectedImage ->
        if (selectedImage != null) {
            val bytes = contentResolver.openInputStream(selectedImage)?.use {
                it.readBytes()
            }

            //val tempFile = File(filesDir, "temp.png")
            FileOutputStream(imageUri.toFile()).use {
                it.write(bytes)
            }

            imageView.setImageURI(null)
            imageView.setImageURI(imageUri)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.saving_photo_layout)

        editTextName = findViewById(R.id.item_name)
        imageView = findViewById(R.id.item_image_preview)
        spinnerType = findViewById(R.id.spinnerType)
        buttonSave = findViewById(R.id.buttonSave)
        buttonCamera = findViewById(R.id.button_camera)
        seasonsLayout = findViewById(R.id.seasonsLayout)
        occasionsLayout = findViewById(R.id.occasionsLayout)

        buttonSave.setOnClickListener{
            saveItem()
        }
        buttonCamera.setOnClickListener{
            showDialogBox(null)
        }
        //val imageUri = Uri.parse(intent.getStringExtra("imageUri"))
        if (intent.hasExtra("id")) {
            title = "Edit Item"
            val id = intent.getIntExtra("id",0)
            editTextName.setText(intent.getStringExtra("name"))
            imageUri = File(filesDir,"Item#$id.png").toUri()
            val type = intent.getStringExtra("type").orEmpty()
            spinnerType.setSelection(getSelectionIndexFromString(type))
            fillCheckBoxes(intent.getStringExtra("bools").orEmpty())
        }
        else {
            title = "Add Clothes Item"
            imageUri = File(filesDir,"temp.png").toUri()
        }
        imageView.setImageURI(imageUri)
    }

    fun saveItem() {
        val itemName: String = editTextName.text.toString()
        val itemType: String = spinnerType.selectedItem.toString()
        var bools: String = "00000000"
        val sb = StringBuilder(bools)

        if (itemName.trim().isEmpty()) {
            Toast.makeText(this,"Please insert a name for the item", Toast.LENGTH_SHORT).show()
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

        var newIntent = Intent().putExtra("name",itemName)
            .putExtra("type",itemType)
            .putExtra("bools",sb.toString())

        if (intent.hasExtra("id")) {
            newIntent.putExtra("id",intent.getIntExtra("id",0))
        }
        setResult(RESULT_OK,newIntent)
        finish()
    }

    fun fillCheckBoxes(bools: String) {
        for (i in bools.indices) {
            var box: CheckBox
            if (i < 4) {
                box = seasonsLayout.getChildAt(i) as CheckBox
            }
            else {
                box = occasionsLayout.getChildAt(i-4) as CheckBox
            }
            when(i) {
                0 -> {if(bools[i] == '1') box.isChecked = true}
                1 -> {if(bools[i] == '1') box.isChecked = true}
                2 -> {if(bools[i] == '1') box.isChecked = true}
                3 -> {if(bools[i] == '1') box.isChecked = true}
                4 -> {if(bools[i] == '1') box.isChecked = true}
                5 -> {if(bools[i] == '1') box.isChecked = true}
                6 -> {if(bools[i] == '1') box.isChecked = true}
                7 -> {if(bools[i] == '1') box.isChecked = true}
            }
        }
    }

    fun getSelectionIndexFromString(type: String): Int {
        for (i in spinnerType.indices) {
            if (spinnerType[i].toString() == type) {
                return i
            }
        }
        return 0
    }

    private fun showDialogBox(message: String?) {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_layout)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        // Set the layout parameters
        val window = dialog.window
        window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)

        val tvMessage: TextView = dialog.findViewById(R.id.select_txt)
        val fromgallerybutton: Button = dialog.findViewById(R.id.from_gallery_btn)
        val cambtn: Button = dialog.findViewById(R.id.camera_btn)
        val cancelbtn: Button = dialog.findViewById(R.id.cancel_btn)

        cambtn.setOnClickListener {

            val tempFile = File(filesDir,"temp.png")
            tempFileUri = FileProvider.getUriForFile(applicationContext, "com.example.outfitpicker.fileProvider",tempFile)

            takePicture.launch(tempFileUri)

            dialog.dismiss()
        }

        fromgallerybutton.setOnClickListener {
            takePictureFromGallery.launch("image/*")

            dialog.dismiss()
        }

        cancelbtn.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
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

            newItem.setBools(intent.getStringExtra("bools").orEmpty())
            return newItem
        }
    }

    class EditItemContract: ActivityResultContract<Item, Item?>() {
        override fun createIntent(context: Context, input: Item): Intent {
            return Intent(context, SavingClothesItemActivity::class.java)
                .putExtra("id",input.id)
                .putExtra("name",input.name)
                .putExtra("type",input.type)
                .putExtra("bools",input.getBools())
        }

        override fun parseResult(resultCode: Int, intent: Intent?): Item? {
            if (resultCode != RESULT_OK) {
                return null
            }

            val newItem = Item(
                id = intent!!.getIntExtra("id",0),
                name = intent!!.getStringExtra("name").orEmpty(),
                type = intent.getStringExtra("type").orEmpty()
            )
            newItem.setBools(intent.getStringExtra("bools").orEmpty())
            return newItem
        }

    }

}