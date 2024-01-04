package com.example.naturalgem

object PlaceManager {
    val places = mutableListOf<Place>()

    init {
        createMockData()
    }

    fun createMockData(){
        places.add(Place(null,"Hassafall", "Vattenfall",R.drawable.hassafall))
        places.add(Place(null,"Visingsö", "En ö", null))
        places.add(Place(null,"Hawaiihålan", "Badstrand", R.drawable.hawaiih_lan))
        places.add(Place(null,"Skomakarens kammare", "Grotta", R.drawable.skomakarens_kammare))
        places.add(Place(null,"Lummelundagrottan", "Grotta",R.drawable.lummelundagrottan))
    }
}