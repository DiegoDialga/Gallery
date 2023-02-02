package com.example.gallery

import androidx.recyclerview.widget.DiffUtil
import com.example.gallery.DataClass.Image

class SpotDiffCallback(
    private val old: List<Image>,
    private val new: List<Image>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return old.size
    }

    override fun getNewListSize(): Int {
        return new.size
    }

    override fun areItemsTheSame(oldPosition: Int, newPosition: Int): Boolean {
        return old[oldPosition].imagePath == new[newPosition].imagePath
    }

    override fun areContentsTheSame(oldPosition: Int, newPosition: Int): Boolean {
        return old[oldPosition] == new[newPosition]
    }

}
