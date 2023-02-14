package com.example.gallery.Extras

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore.Files
import java.io.File

class ShareImages(context: Context) {
    private val ctx: Context = context
    fun shareImagesViaIntent(imageUrl: String){

        val imageUri = Uri.fromFile(File(imageUrl))
        val shareIntent = Intent().apply{
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_STREAM, imageUri)
            type = "image/*"
        }

     /*   val shareIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_STREAM, imageUrl)*/
           // type = "image/*"
      //  }
        ctx.startActivity(Intent.createChooser(shareIntent, "Share Image"))
    }
}