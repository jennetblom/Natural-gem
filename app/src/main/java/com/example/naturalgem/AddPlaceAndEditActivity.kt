package com.example.naturalgem

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build.VERSION_CODES.P
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import com.bumptech.glide.Glide
import com.google.android.gms.maps.model.LatLng
import com.google.android.play.integrity.internal.l
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import java.util.UUID

const val PLACE_POSITION_KEY = "PLACE_POSITION"
const val POSITION_NOT_SET = -1
private val PICK_IMAGE_REQUEST_CODE = 1
class AddPlaceAndEditActivity : AppCompatActivity() {

    lateinit var db : FirebaseFirestore
    lateinit var nameView : EditText
    lateinit var categoryView : EditText
    lateinit var imageEdit:ImageView
    lateinit var infoEditText:EditText
    lateinit var latEditText:EditText
    lateinit var longEditText: EditText
    lateinit var auth : FirebaseAuth

//   private val PICK_IMAGE_REQUEST_CODE = 1
    private var selectedImageUri: Uri? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_place)

        db = Firebase.firestore
        auth = Firebase.auth

        nameView = findViewById(R.id.nameEditText)
        categoryView = findViewById(R.id.categoryEditText)
        imageEdit = findViewById(R.id.imageEdit)
        infoEditText = findViewById(R.id.infoEditText)
        latEditText =findViewById(R.id.latEditText)
        longEditText = findViewById(R.id.longEditText)

        val placePosition = intent.getIntExtra(PLACE_POSITION_KEY, POSITION_NOT_SET)
        val saveButton = findViewById<Button>(R.id.saveButton)

        if (placePosition != POSITION_NOT_SET) {
            displayPlace(placePosition)
            saveButton.setOnClickListener {
                editPlace(placePosition)


            }
        } else {
            saveButton.setOnClickListener {
                savePlace()
//                addNewPlace()
                val intent = Intent(this, PlaceActivity::class.java)
                startActivity(intent)
            }
        }
        imageEdit.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.putExtra(PLACE_POSITION_KEY, POSITION_NOT_SET)
            intent.type = "image/*"
            startActivityForResult(intent, PICK_IMAGE_REQUEST_CODE)
        }
    }

    fun editPlace(position: Int){


        val updatedName = nameView.text.toString()
        val updatedCategory = categoryView.text.toString()
        val updatedInfo = infoEditText.text.toString()
        val latString = latEditText.text.toString()
        val longString = longEditText.text.toString()

        val updatedLatitude: Double?
        val updatedLongitude: Double?

        if(latString.isNotEmpty()&& longString.isNotEmpty())
        try {
            updatedLatitude = latString.toDouble()
            updatedLongitude = longString.toDouble()
        } catch (e: NumberFormatException) {
            // Felaktig inmatning för latitud eller longitud. Hantera detta, t.ex., genom att visa ett felmeddelande.
            return
        }
        else{
            updatedLatitude=null
            updatedLongitude=null
        }

        Log.d("!!!","efter trycatch: $updatedLatitude")
        Log.d("!!!","$updatedLatitude")
        val place = PlaceManager.places[position]

        place.name=updatedName
        place.category=updatedCategory
        place.info=updatedInfo


        if (updatedLatitude != null && updatedLongitude != null) {
            place.latitude=updatedLatitude
            place.longitude=updatedLongitude
        } else {
            // Om latitud eller longitud är null, hantera det här, t.ex., genom att visa ett felmeddelande.
            return
        }


        if(selectedImageUri!=null){
            place.imageUri = selectedImageUri.toString()
            uploadImageToFirebaseStorage(selectedImageUri,place.documentId)
        }
        val user = auth.currentUser
        if(user!=null){
            val documentId = place.documentId

            if(documentId!=null){
                val placeRef = db.collection("places").document(documentId)

                placeRef.update(
                    mapOf(
                        "name" to updatedName,
                        "category" to updatedCategory,
                        "info" to updatedInfo,
                        "imageUri" to place.imageUri,
                        "latitude" to updatedLatitude,
                        "longitude" to updatedLongitude
                    )
                ).addOnSuccessListener {
                    val intent = Intent(this,PlaceActivity::class.java)
                    startActivity(intent)
                }.addOnFailureListener {
                    Toast.makeText(this,"Något gick fel",Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    fun displayPlace(position:Int){
        val place = PlaceManager.places[position]
        nameView.setText(place.name)
        categoryView.setText(place.category)
        infoEditText.setText(place.info)
//        latEditText.setText(place.latitude.toString())
//        longEditText.setText(place.longitude.toString())
            if (place.imageUri != null) {
                Glide.with(this)
                    .load(place.imageUri)
                    .placeholder(R.drawable.flower) // Visa en platsbild om URI är null eller bildladdningen misslyckas
                    .into(imageEdit)
            }
        latEditText.setText(place.latitude?.toString())
        longEditText.setText(place.longitude?.toString())

        }
    fun savePlace() {
        val name = nameView.text.toString()
        val category = categoryView.text.toString()
        val info = infoEditText.text.toString()
        //val
        val lat = latEditText.text.toString().toDouble()
        val long = longEditText.text.toString().toDouble()

        var imageUri: Uri? = selectedImageUri

        val place = Place(null, name, category, imageUri.toString(), info, lat,long)

        val user = auth.currentUser
        if (user == null) {
            return
        }


        val placesCollection = db.collection("places")

        // Lägg till platsen i Firestore
        placesCollection.add(place)
            .addOnSuccessListener { documentReference ->
                // Uppdatera den sparade platsen med rätt imageUri
                documentReference.update("imageUri", imageUri.toString())
                // Alternativt kan du använda en Map för att uppdatera flera fält:
                // val updateData = mapOf("imageUri" to imageUri.toString(), "otherField" to "otherValue")
                // documentReference.update(updateData)
            }
            .addOnFailureListener { exception ->
                // Hantera fel här
                Log.e("!!!", "Error adding document", exception)
            }

        Log.d("!!!","${PlaceManager.places}")
        PlaceManager.places.add(place)



    }

override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)


    if (requestCode == PICK_IMAGE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
        val selectedImageUri: Uri? = data?.data
        Log.d("!!!","$selectedImageUri")

        // Ladda upp bilden till Firebase Storage och uppdatera sedan Firestore
        if (selectedImageUri != null) {
            // Använd en unik bildfilnamn baserat på UUID
            val imageFileName = "images/${UUID.randomUUID()}.jpg"

            // Skapa en referens till bildfilen i Firebase Storage
            val imageRef = FirebaseStorage.getInstance().reference.child(imageFileName)

            // Ladda upp bilden till Firebase Storage
            imageRef.putFile(selectedImageUri)
                .addOnSuccessListener { taskSnapshot ->
                    // Hämta download URL för den uppladdade bilden
                    imageRef.downloadUrl
                        .addOnSuccessListener { downloadUrl ->
                            // Uppdatera platsens imageUri i Firestore med downloadUrl
                            val placePosition =
                                intent.getIntExtra(PLACE_POSITION_KEY, POSITION_NOT_SET)
                            //updatePlaceImageUri(selectedImageUri,placePosition)
                            updatePlaceImageUri(downloadUrl, placePosition)

                                            Glide.with(this)
            .load(selectedImageUri)
            .placeholder(R.drawable.flower)
            .into(imageEdit)
                        }
                        .addOnFailureListener { exception ->
                            Log.e(TAG, "Fel vid hämtning av download URL: $exception")
                        }
                }
                .addOnFailureListener { exception ->
                    Log.e(TAG, "Fel vid uppladdning av bild: $exception")
                }
        }
    }
}
    fun updatePlaceImageUri(imageUri:Uri?, position: Int){
        val currentPlace = PlaceManager.places[position]
        currentPlace?.imageUri = imageUri.toString()
        uploadImageToFirebaseStorage(imageUri, currentPlace?.documentId)


    }

    private fun uploadImageToFirebaseStorage(imageUri: Uri?, documentId: String?) {
        if (imageUri != null && documentId != null) {
            val storage = FirebaseStorage.getInstance()
            val storageRef = storage.reference
            val imageRef = storageRef.child("images/${UUID.randomUUID()}.jpg")

            val uploadTask = imageRef.putFile(imageUri)

            uploadTask.addOnSuccessListener { taskSnapshot ->
                // Bilden laddades upp framgångsrikt
                // Hämta nerladdnings-URL från taskSnapshot.metadata
                val downloadUrl = taskSnapshot.metadata?.reference?.downloadUrl

                // Uppdatera platsens bild-URI i Firestore
                val placeRef = db.collection("places").document(documentId)
                downloadUrl?.addOnSuccessListener { uri ->
                    placeRef.update("imageUri", uri.toString())
                        .addOnSuccessListener {
                            Log.d("!!!", "Platsens bild-URI uppdaterad i Firestore.")
                        }
                        .addOnFailureListener { e ->
                            Log.e("!!!", "Fel vid uppdatering av bild-URI i Firestore: $e")
                        }
                }
            }.addOnFailureListener { exception ->
                // Hantera fel vid uppladdning
                Log.e("!!!", "Fel vid uppladdning av bild: $exception")
            }
        }
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}