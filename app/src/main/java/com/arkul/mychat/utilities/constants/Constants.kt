package com.arkul.mychat.utilities.constants

import android.content.Context
import android.graphics.Bitmap
import android.view.animation.AnimationUtils
import com.yalantis.ucrop.UCrop

object Constants {
    const val USERNAME_TAG = "#"
    const val MAX_SELECTABLE_PHOTOS_FROM_GALLERY_SUGGESTION_INT = 10
    const val MAX_SELECTABLE_PHOTOS_FROM_GALLERY_SUGGESTION_STRING =
        MAX_SELECTABLE_PHOTOS_FROM_GALLERY_SUGGESTION_INT.toString()

    fun getAnimationFadeIn(context: Context) =
        AnimationUtils.loadAnimation(context, androidx.appcompat.R.anim.abc_fade_in)

    fun getAnimationFadeOut(context: Context) =
        AnimationUtils.loadAnimation(context, androidx.appcompat.R.anim.abc_fade_out)
}

object UCropConstants {
    val WALLPAPER_SETTINGS = UCrop.Options().apply {
        this.withAspectRatio(2f, 1f)
        this.setCompressionFormat(Bitmap.CompressFormat.JPEG)
        this.withMaxResultSize(2000, 1000)
        this.setCompressionQuality(80)
    }
}


