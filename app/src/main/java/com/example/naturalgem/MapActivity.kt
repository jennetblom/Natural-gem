package com.example.naturalgem

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MapActivity :FragmentActivity(),OnMapReadyCallback {

    private lateinit var mMap : GoogleMap
    lateinit var mapImage : ImageView
    lateinit var mapWelcomeText : TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        val mapFab = findViewById<FloatingActionButton>(R.id.mapFab)
        mapImage = findViewById(R.id.mapImage)
        mapWelcomeText = findViewById(R.id.mapWelcomeText)

        val mapFragment = supportFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment
        mapFragment.getMapAsync(this)

        mapFab.setOnClickListener {
            val intent = Intent(this,HomeActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        createMarkers()

    }
    fun createMarkers(){

        var hassafall = LatLng(57.7,14.1)
        var visingso = LatLng(58.0,14.3)
        var hawaiihålan = LatLng(58.1,12.625)
        var skomakarens = LatLng(57.57,14.299)
        var lummelunda = LatLng(57.738,18.409)

        var marker1 = mMap.addMarker(
            MarkerOptions()
                .position(hassafall)
                .title("Hassafallsleden")
                .snippet("Promenadled")
        )

        var marker2 = mMap.addMarker(
            MarkerOptions()
                .position(visingso)
                .title("Visingsö")
                .snippet("En ö")
        )

        var marker3 = mMap.addMarker(
            MarkerOptions()
                .position(hawaiihålan)
                .title("Hawaiihålan")
                .snippet("Badplats")
        )
        var marker4 = mMap.addMarker(
            MarkerOptions()
                .position(skomakarens)
                .title("Skomakarens kammare")
                .snippet("Grotta")
        )

        var marker5 = mMap.addMarker(
            MarkerOptions()
                .position(lummelunda)
                .title("Lummelundagrottan")
                .snippet("Grotta")
        )

        mMap.moveCamera(CameraUpdateFactory.newLatLng(hassafall))
    }
    fun addMarker(position:Int){
        var place = PlaceManager.places[position]


    }
}