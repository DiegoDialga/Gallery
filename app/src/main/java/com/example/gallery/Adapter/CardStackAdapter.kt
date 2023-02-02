package com.example.gallery.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.gallery.DataClass.Image
import com.example.gallery.R
import java.io.File

class CardStackAdapter(
    private var spots: List<Image> = emptyList()
) : RecyclerView.Adapter<CardStackAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(inflater.inflate(R.layout.item_spot, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val spot = spots[position]

        Glide.with(holder.image)
            .load(spot.imagePath)
            .into(holder.image)

        holder.itemView.setOnClickListener { v ->
            Toast.makeText(v.context, spot.imageName, Toast.LENGTH_SHORT).show()
            val file = spot.imagePath?.let { File(it) }
            if(file!!.exists()){
                file.delete()
                Toast.makeText(v.context, "The file has been  deleted", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun getItemCount(): Int {
        return spots.size
    }

    fun setSpots(spots: List<Image>) {
        this.spots = spots
    }

    fun getSpots(): List<Image> {
        return spots
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.item_name)
        var image: ImageView = view.findViewById(R.id.item_image)
    }

}
