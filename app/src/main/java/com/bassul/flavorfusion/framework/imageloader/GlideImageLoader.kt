package com.bassul.flavorfusion.framework.imageloader

import android.widget.ImageView
import com.bassul.flavorfusion.R
import com.bumptech.glide.Glide
import javax.inject.Inject

class GlideImageLoader @Inject constructor(): ImageLoader {
    override fun load(image: ImageView, imageUrl: String) {
        Glide.with(image.rootView)
            .load(imageUrl)
            .fallback( R.drawable.ic_broken_image)
            .into(image)
    }
}