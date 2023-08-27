package com.bassul.flavorfusion.framework.imageloader

import android.widget.ImageView
import androidx.annotation.DrawableRes

interface ImageLoader {
    fun load(image: ImageView, imageUrl: String, @DrawableRes fallback: Int)
}