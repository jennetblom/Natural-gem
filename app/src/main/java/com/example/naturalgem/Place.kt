package com.example.naturalgem

import com.google.android.gms.maps.model.LatLng
import com.google.firebase.firestore.DocumentId

data class Place(
    @DocumentId var documentId: String? =null,
    var name : String? = null , var category : String? = null , var imageResId: Int? = null, var info:String?=null ) {

}

