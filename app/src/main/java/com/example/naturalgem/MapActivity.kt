package com.example.naturalgem

import android.content.Intent
import android.os.Bundle


import android.util.Log
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


        addMarkers()

    }

    fun addMarkers(){

        Log.d("!!!","${PlaceManager.places.size}")
            for (place in PlaceManager.places) {
                if (place.latitude != null && place.longitude != null) {
                    Log.d("!!!", "${place.latitude}")
                    mMap.addMarker(
                        MarkerOptions()
                            .position(LatLng(place.latitude!!, place.longitude!!))
                            .title(place.name)
                            .snippet(place.category.toString() ?: "")
                    )
                }
            }
            mMap.moveCamera(CameraUpdateFactory.newLatLng(LatLng(57.0,13.0)))
        }


    }

