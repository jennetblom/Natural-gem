package com.example.naturalgem

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

const val PLACE_POSITION_KEY = "PLACE_POSITION"
const val POSITION_NOT_SET = -1
class AddPlaceAndEditActivity : AppCompatActivity() {

    lateinit var db : FirebaseFirestore
    lateinit var nameView : EditText
    lateinit var categoryView : EditText
    lateinit var imageEdit:ImageView
    lateinit var auth : FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_place)

        db = Firebase.firestore
        auth = Firebase.auth

        nameView = findViewById(R.id.nameEditText)
        categoryView = findViewById(R.id.categoryEditText)
        imageEdit = findViewById(R.id.imageEdit)

        val placePosition=intent.getIntExtra(PLACE_POSITION_KEY, POSITION_NOT_SET)
        if(placePosition!= POSITION_NOT_SET){
            displayPlace(placePosition)
        }

        val saveButton = findViewById<Button>(R.id.saveButton)

        saveButton.setOnClickListener {
            savePlace()
            addNewPlace()
        }

    }
    fun displayPlace(position:Int){
        val place = PlaceManager.places[position]
        nameView.setText(place.name)
        categoryView.setText(place.category)
    }
    fun savePlace(){
        val name = nameView.text.toString()
        val category = categoryView.text.toString()

        val place = Place(null,name, category, null)

        val user = auth.currentUser
        if(user==null){
            return
        }

        db.collection("users").document(user.uid)
            .collection("Places").add(place)

    }
    fun addNewPlace(){
        val name = nameView.text.toString()
        val category = categoryView.text.toString()
        val place = Place(null,name,category,null)
        PlaceManager.places.add(place)
        nameView.setText("")
        categoryView.setText("")
        val intent = Intent(this,PlaceActivity::class.java)
        startActivity(intent)
    }
}