package com.example.gallery.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import androidx.viewpager.widget.ViewPager
import com.example.gallery.Adapter.ImagePagerAdapter
import com.example.gallery.DataClass.Image
import com.example.gallery.Objects.ImagesInStorage
import com.example.gallery.R

class GridViewFullImageActivity : AppCompatActivity() {

    private var mScaleGestureDetector: ScaleGestureDetector? = null
    private var mViewPager: ViewPager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_grid_view_full_image)
        val imageList: ArrayList<Image> =
            ImagesInStorage.getAllImages(this@GridViewFullImageActivity)

        val currentPosition = intent.getIntExtra("position", 0)

        mViewPager = findViewById(R.id.view_pager)
        val adapter = ImagePagerAdapter(this, imageList)
        mViewPager?.adapter = adapter
        mViewPager?.setCurrentItem(currentPosition, false)
        mViewPager?.pageMargin = 50

    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        mScaleGestureDetector?.onTouchEvent(event!! )
        return super.onTouchEvent(event)
    }

}