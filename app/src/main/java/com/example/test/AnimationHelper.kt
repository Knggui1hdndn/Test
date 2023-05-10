package com.example.test

import android.util.Log
import android.view.animation.AlphaAnimation
import android.view.animation.RotateAnimation
import android.view.animation.ScaleAnimation
import android.view.animation.TranslateAnimation
import android.widget.ImageView
import android.widget.VideoView
import androidx.core.net.toUri
import com.example.test.model.Animation

class AnimationHelper {
companion object{
    fun startAnimationForImageOrVideo(anim: Animation,img: ImageView,vd :VideoView){
if (anim.type!="video"){
    img.setImageResource(anim.path.toInt() )

}
        when (anim.type) {
            "fadeAnimation" -> {
                val fadeAnimation = AlphaAnimation(0f, 1f)
                fadeAnimation.duration = 1000
                if (anim.repeat) {
                    fadeAnimation.repeatCount = android.view.animation.Animation.INFINITE
                }else{
                    fadeAnimation.repeatCount = 0

                }
                img.startAnimation(fadeAnimation)
            }

            "scaleAnimation" -> {
                val scaleAnimation = ScaleAnimation(0f, 1f, 0f, 1f)
                scaleAnimation.duration = 1000
                if (anim.repeat) {
                    scaleAnimation.repeatCount = android.view.animation.Animation.INFINITE
                }else{
                    scaleAnimation
                        .repeatCount = 0

                }
                img.startAnimation(scaleAnimation)

            }

            "translateAnimation" -> {
                val translateAnimation = TranslateAnimation(0f, 100f, 0f, 0f)
                translateAnimation.duration = 1000
                if (anim.repeat) {
                    translateAnimation.repeatCount = android.view.animation.Animation.INFINITE
                }else{
                    translateAnimation.repeatCount = 0

                }
                img.startAnimation(translateAnimation)

            }

            "rotateAnimation" -> {
                val rotateAnimation = RotateAnimation(0f, 360f)
                rotateAnimation.duration = 1000
                if (anim.repeat) {
                    rotateAnimation.repeatCount = android.view.animation.Animation.INFINITE
                }else{
                    rotateAnimation.repeatCount = 0

                }
                img.startAnimation(rotateAnimation)
            }

            else -> {
                Log.e("ddddddđ", anim.type+"  "+anim.path)
                vd.setVideoPath(anim.path )
                if (anim.repeat) {
                    vd.start()
                    vd.setOnCompletionListener { mp ->
                    vd.start()
                }} else{ vd.start()}
            }
        }

    }
}
}