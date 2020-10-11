package com.example.circleloading

import android.animation.ValueAnimator
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val downloadAnimator: ValueAnimator by lazy {
        ValueAnimator.ofFloat(0f, 1f).apply {
            duration = 2000
            addUpdateListener {
                mLoading.progress = it.animatedValue as Float
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        //改变圆环颜色
        //  mLoading.progressBgColor = Color.YELLOW

        mStart.setOnClickListener {
            if (downloadAnimator.isPaused) {
                downloadAnimator.resume()
            } else {
                downloadAnimator.start()
            }
        }

        mStop.setOnClickListener {
            downloadAnimator.pause()
        }
    }
}

