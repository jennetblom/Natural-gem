package com.example.naturalgem

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class PlaceActivity : AppCompatActivity() {

    lateinit var recyclerView : RecyclerView
    lateinit var addButton : FloatingActionButton
    var db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_place)

        readFromFirebase()
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = PlacesRecyclerAdapter(this,PlaceManager.places)

       addButton = findViewById(R.id.floatingActionButton)
        addButton.setOnClickListener {
            val intent = Intent(this,LogInActivity::class.java)
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
                    recyclerView.adapter?.notifyDataSetChanged()
                } else {
                    Log.d("!!!", "Current data: null")
                }
            }
        }


    override fun onResume() {
        super.onResume()
        recyclerView.adapter?.notifyDataSetChanged()


    }
}