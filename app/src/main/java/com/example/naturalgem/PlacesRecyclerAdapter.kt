package com.example.naturalgem

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class PlacesRecyclerAdapter(val context : Context, val places : List<Place> ):
    RecyclerView.Adapter<PlacesRecyclerAdapter.ViewHolder>(){

    var db = Firebase.firestore
    val layoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = layoutInflater.inflate(R.layout.place_item,parent,false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int = places.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val place = places[position]

        holder.nameTextView.text = place.name
        holder.categoryTextView.text = place.category
        holder.placePosition=position


        if(place.imageResId!=null){holder.placeImage.setImageResource(place.imageResId!!)}else{
            holder.placeImage.setImageResource(R.drawable.flower)
        }


    }
    fun removePlace(position: Int){
////        PlaceManager.places.removeAt(position)
//
//        notifyDataSetChanged()
        val place = PlaceManager.places[position]
        db.collection("places").document(place.documentId!!).delete()


    }
    inner class ViewHolder(itemView : View):RecyclerView.ViewHolder(itemView){
        val nameTextView = itemView.findViewById<TextView>(R.id.nameTextView)
        val categoryTextView = itemView.findViewById<TextView>(R.id.categoryTextView)
        val placeImage = itemView.findViewById<ImageView>(R.id.placeImage)
        val editButton= itemView.findViewById<ImageButton>(R.id.editButton)
        val deleteButton = itemView.findViewById<ImageButton>(R.id.deleteButton)
        var placePosition = 0

        init {

            itemView.setOnClickListener {
//                val intent = Intent(context, AddPlaceAndEditActivity::class.java)
//                intent.putExtra(PLACE_POSITION_KEY,placePosition)
//                context.startActivity(intent)
                val intent = Intent(context,DisplayPlaceActivity::class.java)
                intent.putExtra(PLACE_POSITION_KEY,placePosition)
                context.startActivity(intent)
            }
            editButton.setOnClickListener {

                val intent = Intent(context, AddPlaceAndEditActivity::class.java)
                intent.putExtra(PLACE_POSITION_KEY,placePosition)
                context.startActivity(intent)
            }
            deleteButton.setOnClickListener {
                removePlace(placePosition)
            }
        }

    }
}