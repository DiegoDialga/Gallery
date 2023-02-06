package com.example.gallery.Extras

import android.content.Context
import android.content.Intent

class ShareImages(context: Context) {
    val ctx: Context = context
    fun shareImagesViaIntent(imageUrl: String){

        val shareIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_STREAM, imageUrl)
            type = "image/*"
        }
        ctx.startActivity(Intent.createChooser(shareIntent, "Share Image"))
    }
}