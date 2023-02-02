package com.example.gallery.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.gallery.Activities.GridViewFullImageActivity
import com.example.gallery.DataClass.Image
import com.example.gallery.R


class GridViewAdapter(private var context: Context, var imageList: ArrayList<Image>) : RecyclerView.Adapter<GridViewAdapter.ImageViewHolder>() {

    class ImageViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var image: ImageView? = null

        init {
            image = itemView.findViewById(R.id.row_image)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.row_custom_recycler_view, parent, false)
        return ImageViewHolder(view)
    }

    override fun getItemCount(): Int {
        return imageList.size
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val currentImage = imageList[position]

        Glide.with(context)
            .load(currentImage.imagePath)
            .apply(RequestOptions().centerCrop())
            .into(holder.image!!)

        holder.image?.setOnClickListener{
            val intent = Intent(context, GridViewFullImageActivity::class.java)

            intent.putExtra("path", currentImage.imagePath)
            intent.putExtra("position", position)
            //intent.putExtra("imageList", imageList)
            context.startActivity(intent)
        }


    }

}
