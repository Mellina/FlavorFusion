package com.bassul.flavorfusion.framework.di

import com.bassul.core.usecase.base.AppCoroutinesDispatchers
import com.bassul.core.usecase.base.CoroutinesDispatchers
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface CoroutinesModule {

    @Binds
    fun bindDispatchers(dispatchers: AppCoroutinesDispatchers): CoroutinesDispatchers
}