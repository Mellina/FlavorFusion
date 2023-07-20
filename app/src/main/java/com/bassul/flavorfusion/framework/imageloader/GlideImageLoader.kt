package com.bassul.flavorfusion.framework.imageloader

import android.widget.ImageView
import com.bumptech.glide.Glide
import javax.inject.Inject

class GlideImageLoader @Inject constructor(): ImageLoader {
    override fun load(image: ImageView, imageUrl: String, fallback: Int) {
        Glide.with(image.rootView)
            .load(imageUrl)
            .fallback(fallback) //provisório - colocar imagem de erro ao carregar
            .into(image)
    }
}