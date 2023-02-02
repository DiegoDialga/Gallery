package com.example.gallery.Objects

import android.content.Context
import com.example.gallery.DataClass.Image

object CreateImages {
    fun createSpots(context: Context): List<Image> {
        return ImagesInStorage.getAllImages(context)
    }
}