package com.example.naturalgem

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
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
    lateinit var infoEditText:EditText
    lateinit var latlngEditText:EditText
    lateinit var auth : FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_place)

        db = Firebase.firestore
        auth = Firebase.auth

        nameView = findViewById(R.id.nameEditText)
        categoryView = findViewById(R.id.categoryEditText)
        imageEdit = findViewById(R.id.imageEdit)
        infoEditText=findViewById(R.id.infoEditText)
        latlngEditText =findViewById(R.id.latLngEditText)

        val placePosition = intent.getIntExtra(PLACE_POSITION_KEY, POSITION_NOT_SET)
        val saveButton = findViewById<Button>(R.id.saveButton)

        if (placePosition != POSITION_NOT_SET) {
            displayPlace(placePosition)
            saveButton.setOnClickListener {
                editPlace(placePosition)
//                val intent = Intent(this,PlaceActivity::class.java)
//                startActivity(intent)

            }
        } else {
            saveButton.setOnClickListener {
                savePlace()
//                addNewPlace()
                val intent = Intent(this,PlaceActivity::class.java)
                startActivity(intent)
            }
        }
    }
    fun editPlace(position: Int){

        val updatedName = nameView.text.toString()
        val updatedCategory = categoryView.text.toString()
        val updatedInfo = infoEditText.text.toString()

        val place = PlaceManager.places[position]

        place.name=updatedName
        place.category=updatedCategory
        place.info=updatedInfo

        val user = auth.currentUser
        if(user!=null){
            val documentId = place.documentId

            if(documentId!=null){
                val placeRef = db.collection("places").document(documentId)

                placeRef.update(
                    mapOf(
                        "name" to updatedName,
                        "category" to updatedCategory,
                        "info" to updatedInfo
                    )
                ).addOnSuccessListener {
                    val intent = Intent(this,PlaceActivity::class.java)
                    startActivity(intent)
                }.addOnFailureListener {
                    Toast.makeText(this,"Något gick fel",Toast.LENGTH_SHORT).show()
                }
            }
        }
//        PlaceManager.places[position].name=nameView.text.toString()
//        PlaceManager.places[position].category=categoryView.text.toString()
//        PlaceManager.places[position].info=infoEditText.text.toString()




    }
    fun displayPlace(position:Int){
        val place = PlaceManager.places[position]
        nameView.setText(place.name)
        categoryView.setText(place.category)
        infoEditText.setText(place.info)
        if(place.imageResId!=null){
            imageEdit.setImageResource(place.imageResId!!)
        }
    }
    fun savePlace(){
        val name = nameView.text.toString()
        val category = categoryView.text.toString()
        val info=infoEditText.text.toString()

        val place = Place(null,name, category, null,info)

        val user = auth.currentUser
        if(user==null){
            return
        }

//        db.collection("users").document(user.uid)

            db.collection("places").add(place)




//        db.collection("users").document(user.uid)
//            .collection("Places").document(place.documentId!!).delete()


    }
//    fun addNewPlace(){
//        val name = nameView.text.toString()
//        val category = categoryView.text.toString()
//        val info = infoEditText.text.toString()
//        val place = Place(null,name,category,null, info)
//        PlaceManager.places.add(place)
//        nameView.setText("")
//        categoryView.setText("")
//        infoEditText.setText("")
//        val intent = Intent(this,PlaceActivity::class.java)
//        startActivity(intent)
//    }
}