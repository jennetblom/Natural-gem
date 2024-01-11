package com.example.naturalgem

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class HomeActivity : AppCompatActivity() {

    lateinit var imageStrawberry : ImageView
    lateinit var welcomeText : TextView
    lateinit var placeButton : ImageButton
    lateinit var mapButton : ImageButton
    lateinit var placeText : TextView
    lateinit var mapText : TextView
    var db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        imageStrawberry= findViewById(R.id.imageStrawberry)
        welcomeText = findViewById(R.id.welcomeText)
        placeButton = findViewById(R.id.placeButton)
        mapButton = findViewById(R.id.mapButton)
        placeText = findViewById(R.id.placeText)
        mapText = findViewById(R.id.mapText)
        readFromFirebase()

        placeButton.setOnClickListener {
            val intent = Intent(this, PlaceActivity::class.java)
            startActivity(intent)
        }


        mapButton.setOnClickListener {
            Log.d("!!!","${PlaceManager.places}")
            val intent = Intent(this, MapActivity::class.java)
            startActivity(intent)
        }
    }

    fun readFromFirebase(){
        db.collection("places").addSnapshotListener{
                snapshot, e ->
            if (e != null) {
                Log.w("!!!", "Listen failed.", e)
                return@addSnapshotListener
            }

            if (snapshot != null ) {
                PlaceManager.places.clear()
                PlaceManager.places.addAll(snapshot.toObjects(Place::class.java))


            } else {
                Log.d("!!!", "Current data: null")
            }
        }
    }
}