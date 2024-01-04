package com.example.naturalgem

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PlacesRecyclerAdapter(val context : Context, val places : List<Place> ):
    RecyclerView.Adapter<PlacesRecyclerAdapter.ViewHolder>(){

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


        if(place.imageResId!=null){holder.placeImage.setImageResource(place.imageResId!!)}else{
            holder.placeImage.setImageResource(R.drawable.flower)
        }


    }
    inner class ViewHolder(itemView : View):RecyclerView.ViewHolder(itemView){
        val nameTextView = itemView.findViewById<TextView>(R.id.costraintlayout)
        val categoryTextView = itemView.findViewById<TextView>(R.id.categoryTextView)
        val placeImage = itemView.findViewById<ImageView>(R.id.placeImage)

    }
}