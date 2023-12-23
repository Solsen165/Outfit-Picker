package com.example.outfitpicker

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ItemsViewActivity : AppCompatActivity() {
    // Linking to the view model
    private val itemsViewModel: ItemsViewModel by viewModels {
        ItemsViewModelFactory((application as ClothesApplication).repository)
    }

    // Contract to take picture
    private val takePicture = registerForActivityResult(ActivityResultContracts.TakePicturePreview()) {
        if (it != null) {
            addItemContract.launch(it)
        }
    }

    private val addItemContract = registerForActivityResult(SavingClothesItemActivity.AddItemContract()) {
        if (it != null) {
            itemsViewModel.insert(it)
            Toast.makeText(this,"Item saved",Toast.LENGTH_SHORT).show()
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_items_view)

        // RecyclerView
        val recyclerView: RecyclerView = findViewById(R.id.recycler_view_clothes_items)
        recyclerView.layoutManager = GridLayoutManager(this, 3)
        val adapter: ItemsAdapter = ItemsAdapter()
        recyclerView.adapter = adapter


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
            takePicture.launch(null)
            //Toast.makeText(this, "camera opened", Toast.LENGTH_SHORT).show()
        }

        fromgallerybutton.setOnClickListener {
            Toast.makeText(this, "gallery opened", Toast.LENGTH_SHORT).show()
        }
        cancelbtn.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }
}