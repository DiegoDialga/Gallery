package com.example.gallery.Activities

import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.provider.MediaStore.Files
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.widget.Button
import android.widget.Toast
import androidx.viewpager.widget.ViewPager
import com.example.gallery.Adapter.ImagePagerAdapter
import com.example.gallery.DataClass.Image
import com.example.gallery.Extras.SetWallpaper
import com.example.gallery.Objects.ImagesInStorage
import com.example.gallery.R
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.File

class GridViewFullImageActivity : AppCompatActivity() {

    private var mScaleGestureDetector: ScaleGestureDetector? = null
    private var mViewPager: ViewPager? = null
    private var mSetWallPaperButton: Button? = null
    private var imageList: ArrayList<Image>? = null

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



                    mViewPager = findViewById(R.id.view_pager)
                    val adapter = ImagePagerAdapter(this@GridViewFullImageActivity, imageList!!)
                    mViewPager?.adapter = adapter
                    mViewPager?.setCurrentItem(currentPosition, false)
                    mViewPager?.pageMargin = 50
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

}