package com.example.gallery.Activities

import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.provider.MediaStore.Files
import android.util.Log
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.widget.Button
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.example.gallery.Adapter.ImagePagerAdapter
import com.example.gallery.DataClass.Image
import com.example.gallery.Extras.SetWallpaper
import com.example.gallery.Extras.ShareImages
import com.example.gallery.Objects.ImagesInStorage
import com.example.gallery.R
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import java.io.File

class GridViewFullImageActivity : AppCompatActivity() {

    private var mScaleGestureDetector: ScaleGestureDetector? = null
    private var mViewPager: ViewPager? = null
    private var mSetWallPaperButton: Button? = null
    private var imageList: ArrayList<Image>? = null
    private var mShareImage: Button? = null


    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_grid_view_full_image)
        GlobalScope.launch(Dispatchers.IO){
            try {
                imageList = ImagesInStorage.getAllImages(this@GridViewFullImageActivity)
                runOnUiThread {
                    mSetWallPaperButton = findViewById(R.id.set_wallpaper_button_on_view_pager)
                    val currentPosition = intent.getIntExtra("position", 0)

                    mSetWallPaperButton?.setOnClickListener {
                        SetWallpaper().setWallPaper(imageList, currentPosition, this@GridViewFullImageActivity)
                    }

                    mShareImage = findViewById(R.id.share_button_on_view_pager)
                    mShareImage?.setOnClickListener {
                        imageList!![currentPosition].imagePath?.let { it1 ->
                            ShareImages(this@GridViewFullImageActivity).shareImagesViaIntent(
                                it1
                            )
                        }
                    }


                    mViewPager = findViewById(R.id.view_pager)
                    val adapter = ImagePagerAdapter(this@GridViewFullImageActivity, imageList!!)
                    mViewPager?.adapter = adapter
                    mViewPager?.setCurrentItem(currentPosition, false)
                    mViewPager?.pageMargin = 50
                    imageList!![currentPosition].imagePath?.let { Log.e("imagePath", it) }

                    mShareImage = findViewById(R.id.share_button_on_view_pager)
                    mShareImage?.setOnClickListener {
                        shareTheImage(imageList!![currentPosition].imagePath)
                    }
                }
            }
            catch(e: Exception){
                e.printStackTrace()
            }
        }

    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        mScaleGestureDetector?.onTouchEvent(event!! )
        return super.onTouchEvent(event)
    }

    private fun deleteTheImage(context: Context, imageList: ArrayList<Image>?, currentPosition: Int){
        val contentResolver: ContentResolver = contentResolver
        Toast.makeText(context, imageList?.get(currentPosition)?.imagePath, Toast.LENGTH_SHORT).show()
        contentResolver.delete(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "${MediaStore.Images.ImageColumns.DATA}=?",
            imageList?.get(currentPosition)?.let { arrayOf(it.imagePath) })
    }

    private fun shareTheImage(imagePath: String?){
        try {
            val bitmap: Bitmap = Glide.with(this@GridViewFullImageActivity)
                .asBitmap()
                .load(imagePath!!)
                .submit()
                .get()

            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                type = "image/jpeg"
                putExtra(Intent.EXTRA_STREAM, getImageUri(bitmap, this@GridViewFullImageActivity))
            }
            startActivity(Intent.createChooser(shareIntent, "Share Image:"))
        }
        catch (e: Exception){
            e.printStackTrace()
        }

    }

    private fun getImageUri(bitmap: Bitmap, context: Context): Uri{
        val bytes = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path = MediaStore.Images.Media.insertImage(context.contentResolver, bitmap, "Image Title", null)
        return Uri.parse(path)
    }
}