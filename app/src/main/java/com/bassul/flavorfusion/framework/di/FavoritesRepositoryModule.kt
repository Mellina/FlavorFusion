package com.bassul.flavorfusion.framework.di

import com.bassul.core.data.repository.FavoriteLocalDataSource
import com.bassul.core.data.repository.FavoritesRepository
import com.bassul.flavorfusion.framework.FavoritesRepositoryImpl
import com.bassul.flavorfusion.framework.local.RoomFavoritesDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface FavoritesRepositoryModule {

    @Binds
    fun bindFavoritesRepository(repository: FavoritesRepositoryImpl): FavoritesRepository

    @Binds
    fun bindLocalDataSource(dataSource: RoomFavoritesDataSource): FavoriteLocalDataSource

}