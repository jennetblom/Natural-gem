package com.example.naturalgem

import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class AddPlaceActivity : AppCompatActivity() {

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

        val saveButton = findViewById<Button>(R.id.saveButton)

        saveButton.setOnClickListener {
            savePlace()
        }

    }
    fun savePlace(){
        val name = nameView.text.toString()
        val category = categoryView.text.toString()

        val place = Place(null,name, category, null)
        nameView.setText("")
        categoryView.setText("")

        val user = auth.currentUser
        if(user==null){
            return
        }

        db.collection("users").document(user.uid)
            .collection("Places").add(place)

    }
}