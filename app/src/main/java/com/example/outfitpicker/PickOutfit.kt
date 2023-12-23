package com.example.outfitpicker

import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast

class Activity2 : AppCompatActivity() {


    lateinit var imageView2: ImageView
    lateinit var button2: Button
    val Request_IMAGE_CAPTURE = 100



    private lateinit var button: Button
    private lateinit var imageView: ImageView

    companion object {

        val IMAGE_REQUEST_CODE = 100
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pick_outfit)

        imageView2 = findViewById(R.id.img_save)
        button2 = findViewById(R.id.btn_take_picture)
        button2.setOnClickListener {
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            try {
                startActivityForResult(takePictureIntent,Request_IMAGE_CAPTURE)
            }catch (e: ActivityNotFoundException){
                Toast.makeText(this,  "Error: " + e.localizedMessage, Toast.LENGTH_SHORT).show()
            }
        }

        button = findViewById(R.id.btn_pick_img)
        imageView = findViewById(R.id.img_save)

        button.setOnClickListener {
            pickImageGallery()
        }

    }

    private fun pickImageGallery() {
        TODO("Not yet implemented")
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == IMAGE_REQUEST_CODE && resultCode == RESULT_OK){
            imageView.setImageURI(data?.data)

        }
        if (requestCode == Request_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as Bitmap
            imageView2.setImageBitmap(imageBitmap)
        }
        else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}