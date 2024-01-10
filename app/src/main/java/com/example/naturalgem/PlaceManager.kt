package com.example.naturalgem

import com.google.android.gms.maps.model.LatLng

object PlaceManager {
    var places = mutableListOf<Place>()

//    init {
//       createMockData()
//    }

    fun createMockData(){
        places.add(Place(null,"Hassafallsleden", "Promenadled",null, "En idyllisk rundslinga i ett trolskt och spännande naturområde med vattenfall"
        ))
        places.add(Place(null,"Visingsö", "En ö", null, "En ö där man kan njuta av ruiner från medeltiden, åka remmalag och cykel."))
        places.add(Place(null,"Hawaiihålan", "Badstrand", null, "En badplats med turkost vatten och höga sanddynor som för tankarna till hawaii"))
        places.add(Place(null,"Skomakarens kammare", "Grotta", null, "Besök ett liten grotta i en vacker och grön skog med stora stenblock"))
        places.add(Place(null,"Lummelundagrottan", "Grotta",null,"Upplev en av sveriges största grottor norr om Visby i Gotland som är 4 km lång och som består av kalksten. Finns guidade turer som man kan gå."))
    }
}