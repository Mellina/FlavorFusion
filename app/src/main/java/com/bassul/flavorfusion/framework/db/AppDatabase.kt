package com.bassul.flavorfusion.framework.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.bassul.flavorfusion.framework.db.dao.FavoriteDao
import com.bassul.flavorfusion.framework.db.entity.FavoriteEntity

@Database(entities = [FavoriteEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun favoriteDao(): FavoriteDao
}