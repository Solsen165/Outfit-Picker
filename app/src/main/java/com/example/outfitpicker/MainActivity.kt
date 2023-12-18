package com.example.outfitpicker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btn1 : Button = findViewById(R.id.button)

        btn1.setOnClickListener(){
            Toast.makeText(this, "Jihad is nice", Toast.LENGTH_SHORT).show()
        }
    }
}