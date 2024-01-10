package com.example.naturalgem

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView

class HomeActivity : AppCompatActivity() {

    lateinit var imageStrawberry : ImageView
    lateinit var welcomeText : TextView
    lateinit var placeButton : ImageButton
    lateinit var mapButton : ImageButton
    lateinit var placeText : TextView
    lateinit var mapText : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        imageStrawberry= findViewById(R.id.imageStrawberry)
        welcomeText = findViewById(R.id.welcomeText)
        placeButton = findViewById(R.id.placeButton)
        mapButton = findViewById(R.id.mapButton)
        placeText = findViewById(R.id.placeText)
        mapText = findViewById(R.id.mapText)

        placeButton.setOnClickListener {
            val intent = Intent(this, PlaceActivity::class.java)
            startActivity(intent)
        }


        mapButton.setOnClickListener {
            val intent = Intent(this, MapActivity::class.java)
            startActivity(intent)
        }
    }
}