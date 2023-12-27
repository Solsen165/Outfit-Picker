package com.example.outfitpicker

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.content.FileProvider
import androidx.core.net.toUri
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.io.File
import java.io.FileOutputStream

class ItemListViewActivity : AppCompatActivity() {
    // Linking to the view model
    private val itemsViewModel: ItemsViewModel by viewModels {
        ItemsViewModelFactory((application as ClothesApplication).repository)
    }

    private val addItemContract = registerForActivityResult(SavingClothesItemActivity.AddItemContract()) {item ->
        if (item != null) {
            itemsViewModel.insert(item).observe(this, Observer { id ->
                val oldFile = File(filesDir,"temp.png")
                val bytes = contentResolver.openInputStream(oldFile.toUri())?.use {
                    it.readBytes()
                }
                val file = File(filesDir,"Item#$id.png")
                FileOutputStream(file).use {
                    it.write(bytes)
                }
            })
            Toast.makeText(this,"Item saved",Toast.LENGTH_SHORT).show()
        }
    }

    // Contract to take picture
    lateinit var tempFileUri: Uri
    private val takePicture = registerForActivityResult(ActivityResultContracts.TakePicture()) {
        if (it) {
            addItemContract.launch(tempFileUri)
        }
    }

    // Contract to select from gallery
    private val takePictureFromGallery = registerForActivityResult(ActivityResultContracts.GetContent()) {selectedImage ->
        if (selectedImage != null) {
            val bytes = contentResolver.openInputStream(selectedImage)?.use {
                it.readBytes()
            }
            val tempFile = File(filesDir, "temp.png")
            FileOutputStream(tempFile).use {
                it.write(bytes)
            }
            addItemContract.launch(tempFile.toUri())
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_items_view)

        title = "My Clothes"
        // RecyclerView
        val recyclerView: RecyclerView = findViewById(R.id.recycler_view_clothes_items)
        recyclerView.layoutManager = GridLayoutManager(this, 3)
        val adapter: ItemsAdapter = ItemsAdapter(filesDir)
        recyclerView.adapter = adapter

        val addButton: FloatingActionButton = findViewById(R.id.button_add_clothes_item)
        addButton.setOnClickListener{
            showDialogBox(null)
        }

        // Observing the view model so that when we add new items, the recycler view is updated
        itemsViewModel.getAllNotes().observe(this, Observer {items ->
            adapter.setItems(items)
        })

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

            //Toast.makeText(this, "gallery opened", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }

        cancelbtn.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }
}