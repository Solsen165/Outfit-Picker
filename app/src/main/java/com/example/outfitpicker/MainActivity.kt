package com.example.outfitpicker

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.cardview.widget.CardView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.outfitpicker.databasefiles.OutfitWithItems

class MainActivity : AppCompatActivity() {
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
            Toast.makeText(this, "camera opened", Toast.LENGTH_SHORT).show()
        }

        fromgallerybutton.setOnClickListener {
            Toast.makeText(this, "gallery opened", Toast.LENGTH_SHORT).show()
        }
        cancelbtn.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }
    private val outfitViewModel : OutfitListViewModel by viewModels {
        OutfitViewModelFactory((application as ClothesApplication).repository)
    }
    private val showOutfitContract = registerForActivityResult(ShowingOutfitActivity.ShowOutfitContractWithoutMenu()) {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val itemButton: Button = findViewById(R.id.item_btn)
        val outfitButton: Button = findViewById(R.id.outfit_btn)
        val generateButton: Button = findViewById(R.id.button_generate)
        val seasonsSpinner: Spinner = findViewById(R.id.spinner_seasons)
        val occasionsSpinner: Spinner = findViewById(R.id.spinner_occasions)
        val recyclerCardView: CardView = findViewById(R.id.card_view_recycler_view)
        val recyclerView: RecyclerView = findViewById(R.id.recycler_view_filtered_outfits)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        val adapter: OutfitAdapter = OutfitAdapter(filesDir, object : OutfitAdapter.OnOutfitClickListener {
            override fun onOutfitClick(outfit: OutfitWithItems) {
                showOutfitContract.launch(outfit)
            }
        })
        recyclerView.adapter = adapter


        itemButton.setOnClickListener {
            startActivity(Intent(this,ItemListViewActivity::class.java))
        }
        outfitButton.setOnClickListener {
            startActivity(Intent(this,OutfitListViewActivity::class.java))
        }
        generateButton.setOnClickListener {
            val season = seasonsSpinner.selectedItem.toString().lowercase()
            val occasion = occasionsSpinner.selectedItem.toString().lowercase()
            outfitViewModel.getOutfitWithAttributes(season,occasion).observe(this, Observer {
                if (it.isEmpty()) {
                    Toast.makeText(this,"You do not have any outfits that match these attributes",Toast.LENGTH_SHORT).show()
                    recyclerCardView.visibility = View.INVISIBLE
                }
                else {
                    recyclerCardView.visibility = View.VISIBLE
                    adapter.setOutfits(it)
                }
            })

        }

    }

}