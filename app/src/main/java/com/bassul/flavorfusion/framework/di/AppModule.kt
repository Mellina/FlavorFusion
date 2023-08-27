package com.bassul.flavorfusion.framework.di

import com.bassul.flavorfusion.framework.imageloader.GlideImageLoader
import com.bassul.flavorfusion.framework.imageloader.ImageLoader
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent

@Module
@InstallIn(FragmentComponent::class)
interface AppModule {

    @Binds
    fun bindImageLoader(imageLoader: GlideImageLoader): ImageLoader
}