package com.bassul.flavorfusion.framework.di

import com.bassul.core.data.repository.RecipesRemoteDataSource
import com.bassul.core.data.repository.RecipesRepository
import com.bassul.flavorfusion.framework.RecipesRepositoryImpl
import com.bassul.flavorfusion.framework.remote.RetrofitRecipesDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RecipesRepositoryModule {

    @Binds
    fun bindRecipeRepository(repository: RecipesRepositoryImpl): RecipesRepository

    @Binds
    fun bindRemoteDataSource(dataSource: RetrofitRecipesDataSource) : RecipesRemoteDataSource

}