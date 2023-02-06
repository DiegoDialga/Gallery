package com.example.gallery.Extras

import android.app.WallpaperManager
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.widget.Toast
import com.example.gallery.DataClass.Image
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SetWallpaper {
    @OptIn(DelicateCoroutinesApi::class)
    fun setWallPaper(imageList: ArrayList<Image>?, currentPosition: Int, context: Context){
        GlobalScope.launch(Dispatchers.IO){
            try {
                val pathOfImage = imageList?.get(currentPosition)?.imagePath
                val bitmap: Bitmap = BitmapFactory.decodeFile(pathOfImage)
                val wallpaperManager = WallpaperManager.getInstance(context)
                wallpaperManager.setBitmap(bitmap)
            }
            catch (e: Exception){
                e.printStackTrace()
            }
        }
    }
}