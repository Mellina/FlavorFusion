package com.bassul.flavorfusion.framework.imageloader

import android.widget.ImageView

interface ImageLoader {
    fun load(image: ImageView, imageUrl: String)
}