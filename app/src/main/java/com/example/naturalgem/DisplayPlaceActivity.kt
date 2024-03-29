package com.example.naturalgem

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import org.w3c.dom.Text


class DisplayPlaceActivity : AppCompatActivity() {

    lateinit var nameDisplay: TextView
    lateinit var categoryDisplay: TextView
    lateinit var imageDisplay: ImageView
    lateinit var infoDisplay: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_place)

        nameDisplay = findViewById(R.id.nameDisplayText)
        categoryDisplay = findViewById(R.id.categoryDisplayText)
        imageDisplay = findViewById(R.id.imageDisplay)
        infoDisplay = findViewById(R.id.infoDisplayText)

        val placePosition = intent.getIntExtra(PLACE_POSITION_KEY, POSITION_NOT_SET)

        displayPlace(placePosition)


    }

    override fun onResume() {
        super.onResume()

    }

    fun displayPlace(position: Int) {
        val place = PlaceManager.places[position]
        nameDisplay.setText(place.name)
        categoryDisplay.setText(place.category)
        infoDisplay.setText(place.info)


        if (place.imageUri != null) {
            Glide.with(this)
                .load(place.imageUri)
                .placeholder(R.drawable.flower)
                .into(imageDisplay)
        }
    }
}