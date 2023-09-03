package com.bassul.flavorfusion.framework.di

import android.content.Context
import androidx.room.Room
import com.bassul.core.data.DbConstants.APP_DATABASE_NAME
import com.bassul.flavorfusion.framework.db.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    fun provideAppDatabase(
        @ApplicationContext context: Context
    ): AppDatabase = Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        APP_DATABASE_NAME
    ).build()

    @Provides
    fun provideFavoriteDao(appDatabase: AppDatabase) = appDatabase.favoriteDao()

    @Provides
    fun provideRecipeDao(appDatabase: AppDatabase) = appDatabase.recipeDao()
}