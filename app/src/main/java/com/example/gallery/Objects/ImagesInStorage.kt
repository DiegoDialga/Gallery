package com.example.gallery.Objects

import android.content.Context
import android.provider.MediaStore
import com.example.gallery.DataClass.Image

object ImagesInStorage {
    fun getAllImages(context: Context): ArrayList<Image> {
        val images = ArrayList<Image>()

        val allImageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        //val projection = arrayOf(MediaStore.Images.Media._ID, MediaStore.Images.Media.DATE_TAKEN)
        val sortOrder = "${MediaStore.Images.Media.DATE_ADDED} DESC"
        //val cursor = contentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection, null, null, sortOrder)

        val projection = arrayOf(MediaStore.Images.ImageColumns.DATA, MediaStore.Images.Media.DISPLAY_NAME)
        val cursor = context.contentResolver.query(allImageUri, projection, null, null, sortOrder)

        try{
            cursor!!.moveToFirst()
            do{

                val image = Image(
                    cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)),
                    cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME))
                )

                images.add(image)
            }while(cursor.moveToNext())
            cursor.close()

        }catch (e: Exception){
            e.printStackTrace()
        }
        return images
    }
}