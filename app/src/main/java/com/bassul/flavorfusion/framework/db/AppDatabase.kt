package com.bassul.flavorfusion.framework.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.bassul.flavorfusion.framework.db.dao.FavoriteDao
import com.bassul.flavorfusion.framework.db.dao.RecipeDao
import com.bassul.flavorfusion.framework.db.dao.RemoteKeyDao
import com.bassul.flavorfusion.framework.db.entity.FavoriteEntity
import com.bassul.flavorfusion.framework.db.entity.RecipeEntity
import com.bassul.flavorfusion.framework.db.entity.RemoteKey

@Database(
    entities = [
        FavoriteEntity::class,
        RecipeEntity::class,
        RemoteKey::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun favoriteDao(): FavoriteDao
    abstract fun recipeDao(): RecipeDao

    abstract fun remoteKeyDao(): RemoteKeyDao
}