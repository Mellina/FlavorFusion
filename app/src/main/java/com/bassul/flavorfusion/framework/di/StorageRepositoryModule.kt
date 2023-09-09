package com.bassul.flavorfusion.framework.di

import com.bassul.core.data.repository.StorageLocalDataSource
import com.bassul.core.data.repository.StorageRepository
import com.bassul.flavorfusion.framework.StorageRepositoryImpl
import com.bassul.flavorfusion.framework.local.DataSourcePreferencesDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface StorageRepositoryModule {

    @Binds
    fun bindStorageRepository(repository: StorageRepositoryImpl): StorageRepository

    @Singleton
    @Binds
    fun bindLocalDataSource(dataSource: DataSourcePreferencesDataSource): StorageLocalDataSource
}