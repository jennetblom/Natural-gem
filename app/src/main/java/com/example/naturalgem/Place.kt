package com.example.naturalgem

import android.net.Uri
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.firestore.DocumentId

data class Place(
    @DocumentId var documentId: String? =null,
    var name : String? = null, var category : String? = null, var imageUri: String?=null, var info:String?=null, var latitude:Double?=null, var longitude:Double?=null) {

}

