package com.example.gallery.Adapter

import android.content.Context
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import com.example.gallery.DataClass.Image
import com.example.gallery.R

class ImagePagerAdapter(private val context: Context, private val imageList: List<Image>) : PagerAdapter() {

    override fun getCount(): Int {
        return imageList.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val layoutInflater = LayoutInflater.from(context)
        val view = layoutInflater.inflate(R.layout.image_item_for_view_pager, container, false)

        val imageView = view.findViewById<ImageView>(R.id.imageViewForFullImage)
        val bitmapFactory = BitmapFactory.decodeFile(imageList[position].imagePath)
        imageView.setImageBitmap(bitmapFactory)
        container.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

}
